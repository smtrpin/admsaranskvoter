package com.simbirsoft.voter.voter.service.impl;

import com.simbirsoft.voter.voter.api.anticaptcha.helper.DebugHelper;
import com.simbirsoft.voter.voter.api.anticaptcha.method.ImageToText;
import com.simbirsoft.voter.voter.service.AntiCaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class AntiCaptchaServiceImpl implements AntiCaptchaService {

    @Value("${anticaptcha.client.secret}")
    String clientSecret;

    @Autowired
    ImageToText api;

    @Override
    public String getCaptchaTextFromImg(InputStream captcha) throws InterruptedException {
        api.setClientKey(clientSecret);
        api.setFile(captcha);

        if (!api.createTask()) {
            DebugHelper.out(
                    "API v2 send failed. " + api.getErrorMessage(),
                    DebugHelper.Type.ERROR
            );
        } else if (!api.waitForResult()) {
            DebugHelper.out("Could not solve the captcha.", DebugHelper.Type.ERROR);
        } else {
            return api.getTaskSolution().getText();
        }
        return "";
    }

    private Long createTask(byte[] img) {
        return null;
    }
}
