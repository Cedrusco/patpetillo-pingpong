package com.cedrus.patpetillo.springkafkapingpong.dao;

import com.cedrus.patpetillo.springkafkapingpong.model.PingPongBall;

import java.util.List;

public interface PingPongBallDAO {
    List<PingPongBall> getAllPingPongBalls();

    PingPongBall getPingPongBallById(String id);

    int createPingPongBall(PingPongBall pingPongBall);

    int updatePingPongBall(PingPongBall pingPongBall);

    int deletePingPongBall(PingPongBall pingPongBall);
}
