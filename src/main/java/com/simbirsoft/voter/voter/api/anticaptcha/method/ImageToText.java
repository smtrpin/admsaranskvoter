package com.simbirsoft.voter.voter.api.anticaptcha.method;

import com.simbirsoft.voter.voter.api.anticaptcha.AnticaptchaBase;
import com.simbirsoft.voter.voter.api.anticaptcha.IAnticaptchaTaskProtocol;
import com.simbirsoft.voter.voter.api.anticaptcha.helper.DebugHelper;
import com.simbirsoft.voter.voter.api.anticaptcha.helper.StringHelper;
import com.simbirsoft.voter.voter.api.anticaptcha.response.TaskResultResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;

@Component
public class ImageToText extends AnticaptchaBase implements IAnticaptchaTaskProtocol {
    private Boolean phrase;
    private Boolean case_;
    private Boolean numeric;
    private Integer math;
    private Integer minLenght;
    private Integer maxLength;
    private String bodyBase64;

    public void setFilePath(String filePath) {
        File f = new File(filePath);

        if (!f.exists() || f.isDirectory()) {
            DebugHelper.out("File " + filePath + " not found", DebugHelper.Type.ERROR);
        } else {
            bodyBase64 = StringHelper.imageFileToBase64String(filePath);

            if (bodyBase64 == null) {
                DebugHelper.out(
                        "Could not convert the file \" + value + \" to base64. Is this an image file?",
                        DebugHelper.Type.ERROR
                );
            }
        }
    }

    public void setFile(InputStream file) {
        if (file == null) {
            DebugHelper.out("Input stream not found", DebugHelper.Type.ERROR);
        } else {
            bodyBase64 = StringHelper.imageFileToBase64String(file);

            if (bodyBase64 == null) {
                DebugHelper.out(
                        "Could not convert the file \" + value + \" to base64. Is this an image file?",
                        DebugHelper.Type.ERROR
                );
            }
        }
    }

    public Boolean getPhrase() {
        return phrase;
    }

    public Boolean getCase_() {
        return case_;
    }

    public Boolean getNumeric() {
        return numeric;
    }

    public Integer getMath() {
        return math;
    }

    public Integer getMinLenght() {
        return minLenght;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    @Override
    public JSONObject getPostData() {

        if (bodyBase64 == null || bodyBase64.length() == 0) {
            return null;
        }

        JSONObject postData = new JSONObject();

        try {
            postData.put("type", "ImageToTextTask");
            postData.put("body", bodyBase64.replace("\r", "").replace("\n", ""));
            postData.put("phrase", phrase);
            postData.put("case", case_);
            postData.put("numeric", numeric);
            postData.put("math", math);
            postData.put("minLength", minLenght);
            postData.put("maxLength", maxLength);
        } catch (JSONException e) {
            DebugHelper.out("JSON compilation error: " + e.getMessage(), DebugHelper.Type.ERROR);

            return null;
        }

        return postData;
    }

    @Override
    public TaskResultResponse.SolutionData getTaskSolution() {
        return taskInfo.getSolution();
    }

    public void setBodyBase64(String bodyBase64) {
        this.bodyBase64 = bodyBase64;
    }
}
