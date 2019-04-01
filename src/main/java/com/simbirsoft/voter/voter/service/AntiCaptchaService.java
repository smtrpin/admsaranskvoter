package com.simbirsoft.voter.voter.service;

import java.io.InputStream;

public interface AntiCaptchaService {
    String getCaptchaTextFromImg(InputStream captcha) throws InterruptedException;
}
