package com.cedrus.patpetillo.springkafkapingpong.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PingPongEvent {

    private String key;
    private Action action;
    private Server server;
    private String timeStamp;
}
