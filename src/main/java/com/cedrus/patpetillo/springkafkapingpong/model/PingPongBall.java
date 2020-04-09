package com.cedrus.patpetillo.springkafkapingpong.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PingPongBall {
    private final String id;
    private PingPongTarget pingPongTarget;

    public void returnBall() {
        this.pingPongTarget = pingPongTarget.equals(PingPongTarget.PING) ? PingPongTarget.PONG : PingPongTarget.PING;
    }
}
