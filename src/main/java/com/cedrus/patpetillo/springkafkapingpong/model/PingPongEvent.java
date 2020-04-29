package com.cedrus.patpetillo.springkafkapingpong.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PingPongEvent {
    UUID id;
    String key;
    Action action;
    String server;
    String timeStamp;
    PingPongBall pingPongBall;
}
