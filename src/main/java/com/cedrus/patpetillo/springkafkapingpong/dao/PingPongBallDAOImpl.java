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

    private final String SQL_GET_ALL = "select * from pingpong";
    private final String SQL_FIND_PINGPONGBALL = "select * from pingpong where id = ?";
    private final String SQL_INSERT_PINGPONGBALL = "insert into pingpong(id, target, color) values(?,?,?)";
    private final String SQL_UPDATE_PINGPONGBALL = "update pingpong set target = ?, color = ? where id = ?";
    private final String SQL_DELETE_PINGPONGBALL = "delete from pingpong where id = ?";

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
