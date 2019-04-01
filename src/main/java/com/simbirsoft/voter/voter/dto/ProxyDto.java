package com.simbirsoft.voter.voter.dto;

public class ProxyDto {
    private String ip;
    private Integer port;
    private ProxyType proxyType;

    public ProxyDto(String ip, Integer port, ProxyType proxyType) {
        this.ip = ip;
        this.port = port;
        this.proxyType = proxyType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public ProxyType getProxyType() {
        return proxyType;
    }

    public void setProxyType(ProxyType proxyType) {
        this.proxyType = proxyType;
    }
}
