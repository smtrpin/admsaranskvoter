package com.simbirsoft.voter.voter.proxy;

import com.simbirsoft.voter.voter.dto.ProxyDto;
import org.openqa.selenium.WebDriver;

import java.util.Queue;

public interface ProxySiteParser {
    Queue<ProxyDto> getProxy();
    WebDriver getPage(WebDriver driver, String url);
}
