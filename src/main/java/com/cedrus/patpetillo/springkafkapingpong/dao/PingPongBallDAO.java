package com.cedrus.patpetillo.springkafkapingpong.dao;

import com.cedrus.patpetillo.springkafkapingpong.model.PingPongBall;
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongBallMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class PingPongBallDAO {
    @Autowired
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
