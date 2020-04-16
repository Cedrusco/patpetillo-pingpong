package com.cedrus.patpetillo.springkafkapingpong.dao;

import com.cedrus.patpetillo.springkafkapingpong.model.PingPongBall;
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongBallMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.List;

@Component
public class PingPongBallDAOImpl implements PingPongBallDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PingPongBallDAOImpl(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final String SQL_GET_ALL = "SELECT * FROM PINGPONG";
    private final String SQL_FIND_PINGPONGBALL = "SELECT * FROM PINGPONG WHERE ID = ?";
    private final String SQL_INSERT_PINGPONGBALL = "INSERT INTO PINGPONG(ID, TARGET, COLOR) VALUES(?,?,?)";
    private final String SQL_UPDATE_PINGPONGBALL = "UPDATE PINGPONG SET TARGET = ?, COLOR = ? WHERE ID = ?";
    private final String SQL_DELETE_PINGPONGBALL = "DELETE FROM PINGPONG WHERE ID = ?";

    public List<PingPongBall> getAllPingPongBalls() {
        return jdbcTemplate.query(SQL_GET_ALL, new PingPongBallMapper());
    }

    public PingPongBall getPingPongBallById(String id) {
        return jdbcTemplate.queryForObject(SQL_FIND_PINGPONGBALL, new Object[] { id }, new PingPongBallMapper());
    }

    public int createPingPongBall(PingPongBall pingPongBall) {
        return jdbcTemplate.update(SQL_INSERT_PINGPONGBALL, pingPongBall.getId(), pingPongBall.getPingPongTarget().name(), pingPongBall.getColor().name());
    }

    public int updatePingPongBall(PingPongBall pingPongBall) {
        return jdbcTemplate.update(SQL_UPDATE_PINGPONGBALL, pingPongBall.getPingPongTarget().name(), pingPongBall.getColor().name(), pingPongBall.getId());
    }

    public int deletePingPongBall(PingPongBall pingPongBall) {
        return jdbcTemplate.update(SQL_DELETE_PINGPONGBALL, pingPongBall.getId());
    }

}
