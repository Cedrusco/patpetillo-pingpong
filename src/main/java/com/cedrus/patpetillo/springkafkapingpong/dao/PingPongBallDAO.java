package com.cedrus.patpetillo.springkafkapingpong.dao;

<<<<<<< HEAD
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongEventMapper;
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongEvent;
=======
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongBall;
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongBallMapper;
>>>>>>> 1ae2a5539a4a010828fb4e5cb9695eb7d419e5ee
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class PingPongBallDAO {
    @Autowired
<<<<<<< HEAD
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

=======
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PingPongBallMapper pingPongBallMapper;

    private final static String SQL_INSERT_PINGPONGBALL = "INSERT INTO PINGPONG(ID, TARGET, COLOR) VALUES(?,?,?)";
    private final static String SQL_GET_ALL = "SELECT * FROM PINGPONG";
    private final static String SQL_FIND_PINGPONGBALL = "SELECT * FROM PINGPONG WHERE ID = ?";
    private final static String SQL_UPDATE_PINGPONGBALL = "UPDATE PINGPONG SET TARGET = ?, COLOR = ? WHERE ID = ?";
    private final static String SQL_DELETE_PINGPONGBALL = "DELETE FROM PINGPONG WHERE ID = ?";

    public int createPingPongBall(PingPongBall pingPongBall) {
        return jdbcTemplate.update(SQL_INSERT_PINGPONGBALL, pingPongBall.getId(), pingPongBall.getPingPongTeam().name(), pingPongBall.getColor().name());
    }

    public List<PingPongBall> getAllPingPongBalls() {
        return jdbcTemplate.query(SQL_GET_ALL, pingPongBallMapper);
    }

    public PingPongBall getPingPongBallById(String id) {
        return jdbcTemplate.queryForObject(SQL_FIND_PINGPONGBALL, new Object[]{ id }, pingPongBallMapper);
    }

    public int updatePingPongBall(PingPongBall pingPongBall) {
        return jdbcTemplate.update(SQL_UPDATE_PINGPONGBALL, pingPongBall.getPingPongTeam().name(), pingPongBall.getColor().name(), pingPongBall.getId());
    }

    public int deletePingPongBall(PingPongBall pingPongBall) {
        return jdbcTemplate.update(SQL_DELETE_PINGPONGBALL, pingPongBall.getId());
    }

}
>>>>>>> 1ae2a5539a4a010828fb4e5cb9695eb7d419e5ee
