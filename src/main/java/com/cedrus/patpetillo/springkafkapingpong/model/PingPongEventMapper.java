package com.cedrus.patpetillo.springkafkapingpong.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Slf4j
@Component
public class PingPongEventMapper implements RowMapper<PingPongEvent> {

    public PingPongEvent mapRow(ResultSet resultSet, int i) throws SQLException {
        log.debug("resultSet: {}", resultSet);
        PingPongEvent pingPongEvent = new PingPongEvent();
        pingPongEvent.setId(resultSet.getObject("id", UUID.class));
        pingPongEvent.setKey(resultSet.getString("key"));
        pingPongEvent.setAction(resultSet.getObject("action", Action.class));
        pingPongEvent.setServer(resultSet.getString("server"));
        pingPongEvent.setTimeStamp(resultSet.getString("time_stamp"));
        pingPongEvent.pingPongBall.setId(resultSet.getString("ball_id"));
        pingPongEvent.pingPongBall.setPingPongTeam(resultSet.getObject("target", PingPongTeam.class));
        pingPongEvent.pingPongBall.setColor(resultSet.getObject("color", Color.class));

        return pingPongEvent;
    }
}
