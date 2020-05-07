package com.cedrus.patpetillo.springkafkapingpong.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ServeBallRequest {
    @JsonProperty private Integer id;
    @JsonProperty private String color;
}
