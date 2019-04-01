package com.simbirsoft.voter.voter.dto;

public class CaptchaDto {
    private String url;
    private String code;

    public CaptchaDto(String url, String code) {
        this.url = url;
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
