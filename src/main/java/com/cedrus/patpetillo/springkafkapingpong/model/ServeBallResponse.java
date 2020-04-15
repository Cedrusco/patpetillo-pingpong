package com.cedrus.patpetillo.springkafkapingpong.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ServeBallResponse {
    @JsonProperty
    private ApiResponse apiResponse;

    public ServeBallResponse(boolean successInd) {
        if (successInd) {
            this.apiResponse = new ApiResponse();
            this.apiResponse.setCode(HttpStatus.OK);
            this.apiResponse.setMessage("SUCCESS");
            this.apiResponse.setSuccessInd(true);
        } else {
            this.apiResponse = new ApiResponse();
            this.apiResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
            this.apiResponse.setMessage("INTERNAL_SERVER_ERROR");
            this.apiResponse.setSuccessInd(false);
        }
    }

    public ServeBallResponse(boolean successInd, String message, HttpStatus code) {
        this.apiResponse = new ApiResponse();
        this.apiResponse.setCode(code);
        this.apiResponse.setMessage(message);
        this.apiResponse.setSuccessInd(successInd);
    }

}
