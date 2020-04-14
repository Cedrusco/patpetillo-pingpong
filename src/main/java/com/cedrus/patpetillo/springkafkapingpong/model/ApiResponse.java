package com.cedrus.patpetillo.springkafkapingpong.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    @JsonProperty("successInd")
    private boolean successInd;

    @JsonProperty
    private String message;

    @JsonProperty
    private String code;
}
