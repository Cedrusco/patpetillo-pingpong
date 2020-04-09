package com.cedrus.patpetillo.springkafkapingpong.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class ServeBallResponse {
    @JsonProperty
    private ApiResponse apiResponse;

    public ServeBallResponse(boolean successInd) {
        if (successInd) {
            this.apiResponse = new ApiResponse();
        }
    }


}
