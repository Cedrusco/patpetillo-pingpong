package com.cedrus.patpetillo.springkafkapingpong.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PingPongBallMapper implements RowMapper<PingPongBall> {

    public PingPongBall mapRow(ResultSet resultSet, int i) throws SQLException {
        final PingPongBall pingPongBall = new PingPongBall();

        pingPongBall.setId(resultSet.getInt("id"));
        pingPongBall.setCurrentTeamWithBall(PingPongTeam.valueOf(resultSet.getString("current_team")));
        pingPongBall.setReceivingTeamForBall(PingPongTeam.valueOf(resultSet.getString("target_team")));
        pingPongBall.setServer((Server.valueOf(resultSet.getString("server"))));
        pingPongBall.setColor((Color.valueOf(resultSet.getString("color"))));

        return pingPongBall;
    }
}
