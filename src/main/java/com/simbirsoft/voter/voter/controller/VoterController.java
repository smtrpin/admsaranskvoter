package com.simbirsoft.voter.voter.controller;

import com.simbirsoft.voter.voter.dto.ProxyDto;
import com.simbirsoft.voter.voter.dto.VoteStatus;
import com.simbirsoft.voter.voter.proxy.ProxySiteParser;
import com.simbirsoft.voter.voter.service.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Queue;

@Controller
public class VoterController {

    @Autowired
    ProxySiteParser proxySiteParser;

    @Autowired
    VoterService voterService;

    @GetMapping("/vote")
    public String vote() {
        Queue<ProxyDto> proxies = proxySiteParser.getProxy();
        proxies.forEach(it -> {
            VoteStatus vote = voterService.vote(it);
            String result;
            if (vote.equals(VoteStatus.SUCCESS)) {
                result = "Проголосовал " + it.getIp();
            } else if (vote.equals(VoteStatus.REPEAT)) {
                result = "Уже голосовал " + it.getIp();
            } else if (vote.equals(VoteStatus.CAPTCHA_FAILED)) {
                result = "Ошибка капчи " + it.getIp();
            } else {
                result = "Проблема голосования с " + it.getIp();
            }
            System.out.println(result);
        });
        return "Done.";
    }
}
