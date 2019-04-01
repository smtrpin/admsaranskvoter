package com.simbirsoft.voter.voter.api.anticaptcha;

import com.simbirsoft.voter.voter.api.anticaptcha.response.TaskResultResponse;
import org.json.JSONObject;

public interface IAnticaptchaTaskProtocol {
    JSONObject getPostData();

    TaskResultResponse.SolutionData getTaskSolution();
}
