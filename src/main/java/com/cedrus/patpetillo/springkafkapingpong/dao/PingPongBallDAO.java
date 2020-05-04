package com.cedrus.patpetillo.springkafkapingpong.dao;

import com.cedrus.patpetillo.springkafkapingpong.model.PingPongEventMapper;
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongEvent;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class PingPongBallDAO {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private final PingPongEventMapper pingPongEventMapper;

    private final static String SQL_INSERT_PING_PONG_EVENT = "INSERT INTO ping_pong_event(key, action, server, time_stamp, ball_id, target, color) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private final static String SQL_UPDATE_PING_PONG_EVENT = "UPDATE ping_pong_event SET key = ?, action = ?, server = ?, time_stamp = ?, ball_id, = ?, target = ?, color WHERE id = ?";
    private final static String SQL_GET_ALL = "SELECT * FROM ping_pong_event";
    private final static String SQL_FIND_PING_PONG_EVENT = "SELECT * FROM ping_pong_event WHERE id = ?";
    private final static String SQL_DELETE_PING_PONG_EVENT = "DELETE FROM ping_pong_event WHERE id = ?";

    public void createPingPongEvent(PingPongEvent pingPongEvent) {
        jdbcTemplate.update(SQL_INSERT_PING_PONG_EVENT,
                pingPongEvent.getKey(),
                pingPongEvent.getAction().name(),
                pingPongEvent.getServer(),
                pingPongEvent.getTimeStamp(),
                pingPongEvent.getPingPongBallEvent().getId(),
                pingPongEvent.getPingPongBallEvent().getPingPongTeam(),
                pingPongEvent.getPingPongBallEvent().getColor());
    }

    public void updatePingPongEvent(PingPongEvent pingPongEvent) {
        jdbcTemplate.update(SQL_UPDATE_PING_PONG_EVENT,
                pingPongEvent.getKey(),
                pingPongEvent.getAction().name(),
                pingPongEvent.getServer(),
                pingPongEvent.getTimeStamp(),
                pingPongEvent.getPingPongBallEvent().getId(),
                pingPongEvent.getPingPongBallEvent().getPingPongTeam(),
                pingPongEvent.getPingPongBallEvent().getColor());
    }

    public List<PingPongEvent> getAllPingPongEvents() {
        return jdbcTemplate.query(SQL_GET_ALL, pingPongEventMapper);
    }

    public PingPongEvent getPingPongEventById(String id) {
        return jdbcTemplate.queryForObject(SQL_FIND_PING_PONG_EVENT, new Object[]{ id }, pingPongEventMapper);
    }

    public int deletePingPongEvent(PingPongEvent pingPongEvent) {
        return jdbcTemplate.update(SQL_DELETE_PING_PONG_EVENT, pingPongEvent.getId());
    }

}

