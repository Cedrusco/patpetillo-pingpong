package com.cedrus.patpetillo.springkafkapingpong.model;

import com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PingPongEvent {
    String key;
    Action action;
    String server;
    String timeStamp;
    PingPongBallEvent pingPongBallEvent;
}
