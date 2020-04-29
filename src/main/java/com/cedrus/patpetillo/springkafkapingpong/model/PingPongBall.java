package com.cedrus.patpetillo.springkafkapingpong.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PingPongBall {
    private String id;
    private PingPongTeam pingPongTeam;
    private Color color;

    public void returnBall() {
        this.pingPongTeam = pingPongTeam.equals(PingPongTeam.REDTEAM) ? PingPongTeam.BLUETEAM : PingPongTeam.REDTEAM;
    }
}
