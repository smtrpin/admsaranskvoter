package com.simbirsoft.voter.voter.service.impl;

import com.simbirsoft.voter.voter.service.PhantomJsService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.stereotype.Service;

@Service
public class PhantomJsServiceImpl implements PhantomJsService {

    private String host = null;
    private String userAgent = null;
    private Boolean javaScriptEnable = false;

    @Override
    public PhantomJsServiceImpl setHost(String hostName) {
        this.host = hostName;
        return this;
    }

    @Override
    public PhantomJsServiceImpl setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    @Override
    public PhantomJsService setJavaScriptEnable(Boolean javaScriptEnable) {
        this.javaScriptEnable = javaScriptEnable;
        return this;
    }

    @Override
    public WebDriver build() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(javaScriptEnable);
        if (host != null) {
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX + "Host", host);
        }
        if (userAgent != null) {
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX + "User-Agent", userAgent);
        }
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "phantomjs.exe");
        return new PhantomJSDriver(caps);
    }
}
