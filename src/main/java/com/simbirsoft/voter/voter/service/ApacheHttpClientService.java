package com.simbirsoft.voter.voter.service;

import com.simbirsoft.voter.voter.dto.ProxyType;
import org.apache.http.client.HttpClient;

public interface ApacheHttpClientService {
    ApacheHttpClientService setProxy(String ip, Integer port, ProxyType proxyType);
    HttpClient build();
}
