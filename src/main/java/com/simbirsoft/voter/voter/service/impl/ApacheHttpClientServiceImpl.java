package com.simbirsoft.voter.voter.service.impl;

import com.simbirsoft.voter.voter.dto.ProxyType;
import com.simbirsoft.voter.voter.service.ApacheHttpClientService;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

@Service
public class ApacheHttpClientServiceImpl implements ApacheHttpClientService {

    private String ip = null;
    private Integer port = null;
    private ProxyType proxyType = null;

    @Override
    public ApacheHttpClientService setProxy(String ip, Integer port, ProxyType proxyType) {
        this.ip = ip;
        this.port = port;
        this.proxyType = proxyType;
        return this;
    }

    @Override
    public HttpClient build() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(5 * 1000)
                .setConnectionRequestTimeout(5 * 1000)
                .setSocketTimeout(5 * 1000).build();
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create().setDefaultRequestConfig(config);
        if (ip != null && port != null && proxyType != null) {
            HttpHost proxyHost = null;
            if (proxyType.equals(ProxyType.HTTP)) {
                proxyHost = new HttpHost(ip, port);
            }
            httpClientBuilder.setProxy(proxyHost);
        }
        return httpClientBuilder.build();
    }
}
