package com.simbirsoft.voter.voter.service.impl;

import com.simbirsoft.voter.voter.dto.CaptchaDto;
import com.simbirsoft.voter.voter.dto.ProxyDto;
import com.simbirsoft.voter.voter.dto.VoteStatus;
import com.simbirsoft.voter.voter.service.AntiCaptchaService;
import com.simbirsoft.voter.voter.service.ApacheHttpClientService;
import com.simbirsoft.voter.voter.service.VoterService;
import com.simbirsoft.voter.voter.util.UserAgentUtil;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdmSaranskVoterService implements VoterService {

    @Autowired
    ApacheHttpClientService apacheHttpClientService;

    @Autowired
    UserAgentUtil userAgentUtil;

    @Autowired
    AntiCaptchaService antiCaptchaService;

    public VoteStatus vote(ProxyDto proxy) {
        HttpClient httpClient = apacheHttpClientService.setProxy(proxy.getIp(), proxy.getPort(), proxy.getProxyType())
                .build();
        String userAgent = userAgentUtil.getRandomUserAgent();
        String page = getPage(httpClient, userAgent);
        if (!page.isEmpty()) {
            Document doc = Jsoup.parse(page);
            if (!isVote(doc)) {
                HttpPost request = new HttpPost("http://adm-saransk.ru/");
                setHeader(request, userAgent);
                List<NameValuePair> urlParameters = new ArrayList<>();
                urlParameters.add(new BasicNameValuePair("vote", "Y"));
                urlParameters.add(new BasicNameValuePair("PUBLIC_VOTE_ID", "31"));
                urlParameters.add(new BasicNameValuePair("VOTE_ID", "31"));
                urlParameters.add(new BasicNameValuePair("vote_radio_44", "2317")); //2315 - Центр, 2316 - Химмаш
                CaptchaDto captchaUrl = getCaptchaUrl(doc);
                if (captchaUrl != null) {
                    InputStream img = getImgByUrl(httpClient, captchaUrl.getUrl(), userAgent);
                    if (img != null) {
                        try {
                            String captchaWord = antiCaptchaService.getCaptchaTextFromImg(img);
                            if (!captchaWord.isEmpty()) {
                                urlParameters.add(new BasicNameValuePair("captcha_word", captchaWord));
                                urlParameters.add(new BasicNameValuePair("captcha_code", captchaUrl.getCode()));
                                try {
                                    request.setEntity(new UrlEncodedFormEntity(urlParameters));
                                } catch (UnsupportedEncodingException ignored) {
                                }
                                try {
                                    HttpResponse httpResponse = httpClient.execute(request);
                                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                                        Document executePage = Jsoup.parse(EntityUtils.toString(httpResponse.getEntity()));
                                        if (executePage.select(".notetext").size() > 0) {
                                            return VoteStatus.SUCCESS;
                                        } else {
                                            System.out.println(captchaUrl.getUrl());
                                            return VoteStatus.CAPTCHA_FAILED;
                                        }
                                    }
                                } catch (IOException e) {
                                    return VoteStatus.FAILED;
                                }
                            }
                        } catch (InterruptedException e) {
                            return VoteStatus.FAILED;
                        }
                    }
                }
            } else {
                return VoteStatus.REPEAT;
            }
        }
        return VoteStatus.FAILED;
    }

    private InputStream getImgByUrl(HttpClient httpClient, String url, String userAgent) {
        HttpGet request = new HttpGet(url);
        setHeader(request, userAgent);
        try {
            HttpResponse httpResponse = httpClient.execute(request);
            return httpResponse.getEntity().getContent();
        } catch (IOException e) {
            return null;
        }
    }

    private Element getVoteForm(Document doc) {
        Elements forms = doc.select("form:contains(Голосование по выбору маршрута в микрорайон Юбилейный)");
        if (forms.size() > 0) {
            return forms.first();
        }
        return null;
    }

    private CaptchaDto getCaptchaUrl(Document doc) {
        Element form = getVoteForm(doc);
        if (form != null && form.select(".captchaImg").size() > 0) {
            String url = "http://adm-saransk.ru/" + form.select(".captchaImg").attr("src");
            String code = form.select(".captchaSid").attr("value");
            return new CaptchaDto(url, code);
        }
        return null;
    }

    private String getPage(HttpClient httpClient, String userAgent) {
        HttpGet request = new HttpGet("http://adm-saransk.ru/");
        setHeader(request, userAgent);
        try {
            HttpResponse httpResponse = httpClient.execute(request);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (IOException e) {
            return "";
        }
        return "";
    }

    private void setHeader(HttpMessage request, String userAgent) {
        request.setHeader("User-Agent", userAgent);
        request.setHeader("Content-type", "application/x-www-form-urlencoded");
        request.setHeader("Host", "adm-saransk.ru");
        request.setHeader("Referer", "http://adm-saransk.ru/0");
    }

    private Boolean isVote(Document doc) {
        Element form = getVoteForm(doc);
        if (form != null) {
            return form.select("input[value='Голосовать']").size() == 0;
        }
        return false;
    }
}
