package com.cedrus.patpetillo.springkafkapingpong.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PingPongBall {
    private String id;
    private PingPongTarget pingPongTarget;

    public void returnBall() {
        this.pingPongTarget = pingPongTarget.equals(PingPongTarget.PING) ? PingPongTarget.PONG : PingPongTarget.PING;
    }
}
