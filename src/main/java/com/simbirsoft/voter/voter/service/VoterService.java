package com.simbirsoft.voter.voter.service;

import com.simbirsoft.voter.voter.dto.ProxyDto;
import com.simbirsoft.voter.voter.dto.VoteStatus;

public interface VoterService {
    VoteStatus vote(ProxyDto proxy);
}
