package com.cedrus.patpetillo.springkafkapingpong.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ApiResponse {
    @JsonProperty("successInd")
    private boolean successInd;

    @JsonProperty
    private String message;

    @JsonProperty
    private String code;

    public ApiResponse(boolean successInd, String message, String code) {
        this.successInd = successInd;
        this. message = message;
        this.code = code;
    }
}
