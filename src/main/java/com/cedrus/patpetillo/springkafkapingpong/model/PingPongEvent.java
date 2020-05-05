package com.cedrus.patpetillo.springkafkapingpong.model;

import com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
