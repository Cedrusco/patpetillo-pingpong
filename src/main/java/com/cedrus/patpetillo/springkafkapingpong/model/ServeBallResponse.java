package com.cedrus.patpetillo.springkafkapingpong.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ServeBallResponse {
    @JsonProperty
    private ApiResponse apiResponse;

    public ServeBallResponse(boolean successInd) {
        if (successInd) {
            this.apiResponse = new ApiResponse();
            this.apiResponse.setCode("OK: 200");
            this.apiResponse.setMessage("SUCCESS");
            this.apiResponse.setSuccessInd(true);
        } else {
            this.apiResponse = new ApiResponse();
            this.apiResponse.setCode("INTERNAL_SERVER_ERROR: 500");
            this.apiResponse.setMessage("SUCCESS");
            this.apiResponse.setSuccessInd(true);
        }
    }

    public ServeBallResponse(boolean successInd, String message, String code) {
        this.apiResponse = new ApiResponse();
        this.apiResponse.setCode(code);
        this.apiResponse.setMessage(message);
        this.apiResponse.setSuccessInd(successInd);
    }

}
