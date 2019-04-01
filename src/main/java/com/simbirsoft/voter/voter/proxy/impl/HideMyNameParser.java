package com.simbirsoft.voter.voter.proxy.impl;

import com.simbirsoft.voter.voter.dto.ProxyDto;
import com.simbirsoft.voter.voter.dto.ProxyType;
import com.simbirsoft.voter.voter.proxy.ProxySiteParser;
import com.simbirsoft.voter.voter.service.PhantomJsService;
import com.simbirsoft.voter.voter.util.UserAgentUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class HideMyNameParser implements ProxySiteParser {

    @Value("${hidemyname.url}")
    private String URL;

    private final String HOST = "hidemyna.me";
    private Queue<ProxyDto> proxies = new ConcurrentLinkedQueue<>();

    @Autowired
    PhantomJsService phantomJsService;

    @Autowired
    UserAgentUtil userAgentUtil;

    @Override
    public Queue<ProxyDto> getProxy() {
        WebDriver driver = phantomJsService.setHost(HOST)
                .setJavaScriptEnable(true)
                .setUserAgent(userAgentUtil.getRandomUserAgent())
                .build();
        WebDriver webPage = getPage(driver, URL);
        WebElement pagination = getPagination(webPage);
        if (pagination.isDisplayed()) {
            pagination = getPagination(webPage).findElement(By.cssSelector("ul > li:last-child"));
            int lastPage = 1;
            if (pagination.isDisplayed()) {
                lastPage = Integer.parseInt(pagination.getText());
            }
            for (int i = 1; i <= lastPage; i++ ) {
                int nextPage = i + 1;
                Thread thread = new Thread(() -> {
                    parseProxiesFromPage(driver.getPageSource());
                    try {
                        WebElement nextPageElement = getPagination(driver).findElement(By.xpath("//a[contains(text(),'" + nextPage + "')]"));
                        if (nextPageElement.isDisplayed()) {
                            nextPageElement.click();
                        }
                    } catch (NoSuchElementException ignored) {}
                });
                thread.run();
            }
            driver.close();
            return proxies;
        }
        return null;
    }

    @Override
    public WebDriver getPage(WebDriver driver, String url) {
        driver.get(url);
        driver.manage().window().setSize(new Dimension(1400, 900));
        try {
            Thread.sleep(8000);
        } catch (InterruptedException ignored) {}
        return driver;
    }

    private void parseProxiesFromPage(String page) {
        Document doc = Jsoup.parse(page);
        if (doc.select(".proxy__t").size() > 0) {
            Element table = doc.select(".proxy__t").get(0).select("tbody").get(0);
            Elements rows = table.select("tr");
            rows.forEach(it -> {
                String ip = it.select("td").get(0).text();
                Integer port = Integer.valueOf(it.select("td").get(1).text());
                proxies.add(new ProxyDto(ip, port, ProxyType.HTTP));
            });
        }
    }

    private WebElement getPagination(WebDriver driver) {
        return driver.findElement(By.cssSelector(".proxy__pagination"));
    }
}
