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
    private Color color;

    public void returnBall() {
        this.pingPongTarget = pingPongTarget.equals(PingPongTarget.PING) ? PingPongTarget.PONG : PingPongTarget.PING;
    }

    @Override
    public String toString() {
        return "Ping Pong Ball: {" + "id=" + id + ", target=" + pingPongTarget + ", color='" + color + '}';
    }
}
