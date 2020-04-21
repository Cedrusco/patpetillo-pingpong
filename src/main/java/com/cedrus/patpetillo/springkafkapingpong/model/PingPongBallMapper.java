package com.cedrus.patpetillo.springkafkapingpong.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PingPongBallMapper implements RowMapper<PingPongBall> {

    public PingPongBall mapRow(ResultSet resultSet, int i) throws SQLException {
        PingPongBall pingPongBall = new PingPongBall();
        pingPongBall.setId(resultSet.getString("id"));
        pingPongBall.setPingPongTarget(PingPongTarget.valueOf(resultSet.getString("target")));
        pingPongBall.setColor((Color.valueOf(resultSet.getString("color"))));

        return pingPongBall;
    }
}
