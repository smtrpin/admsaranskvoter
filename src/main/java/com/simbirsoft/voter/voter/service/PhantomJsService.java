package com.simbirsoft.voter.voter.service;

import com.simbirsoft.voter.voter.service.impl.PhantomJsServiceImpl;
import org.openqa.selenium.WebDriver;

public interface PhantomJsService {
    PhantomJsServiceImpl setHost(String hostName);
    PhantomJsService setUserAgent(String userAgent);
    PhantomJsService setJavaScriptEnable(Boolean javaScriptEnable);
    WebDriver build();
}
