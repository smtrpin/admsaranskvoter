package com.simbirsoft.voter.voter.util.impl;

import com.simbirsoft.voter.voter.util.UserAgentUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class UserAgentUtilImpl implements UserAgentUtil {
    private static List<String> USER_AGENTS = new ArrayList<>();

    static {
        USER_AGENTS.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0");
        USER_AGENTS.add("Mozilla/5.0 (X11; CrOS x86_64 8172.45.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.64 Safari/537.36");
        USER_AGENTS.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit/601.3.9 (KHTML, like Gecko) Version/9.0.2 Safari/601.3.9");
        USER_AGENTS.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.111 Safari/537.36");
        USER_AGENTS.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
        USER_AGENTS.add("Opera/9.80 (Windows NT 5.1; WOW64) Presto/2.12.388 Version/12.17");
        USER_AGENTS.add("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36 OPR/48.0.2685.52");
        USER_AGENTS.add("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.73 Safari/537.36 OPR/34.0.2036.25");
        USER_AGENTS.add("Opera/9.80 (Windows NT 6.1; WOW64) Presto/2.12.388 Version/12.18");
        USER_AGENTS.add("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
        USER_AGENTS.add("Mozilla/5.0 (iPhone; CPU iPhone OS 11_4_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/11.0 Mobile/15E148 Safari/604.1");
        USER_AGENTS.add("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.1)");
        USER_AGENTS.add("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; FunWebProducts)");
        USER_AGENTS.add("Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; GTB7.5; OfficeLiveConnector.1.5; OfficeLivePatch.1.3; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET4.0C)");
        USER_AGENTS.add("Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; MDDRJS; rv:11.0) like Gecko");
        USER_AGENTS.add("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; .NET CLR 1.1.4322; InfoPath.1; .NET CLR 2.0.50727)");
        USER_AGENTS.add("Mozilla/4.0 (compatible; MSIE 5.16; Mac_PowerPC)");
        USER_AGENTS.add("Mozilla/5.0 (Windows; U; MSIE 9.0; Windows NT 9.0; en-US");
        USER_AGENTS.add("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.3; WOW64; Trident/7.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.30729; .NET CLR 3.5.30729)");
        USER_AGENTS.add("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; InfoPath.1; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30)");
    }

    @Override
    public String getRandomUserAgent() {
        Collections.shuffle(USER_AGENTS);
        return USER_AGENTS.get(0);
    }
}
