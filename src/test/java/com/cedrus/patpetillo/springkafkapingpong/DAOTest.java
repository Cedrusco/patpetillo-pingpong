package com.cedrus.patpetillo.springkafkapingpong;

import com.cedrus.patpetillo.springkafkapingpong.config.DatabaseConfig;
import com.cedrus.patpetillo.springkafkapingpong.dao.PingPongBallDAO;
import com.cedrus.patpetillo.springkafkapingpong.model.Color;
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongBall;
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongTarget;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Random;

@Slf4j
public class DAOTest {

    @Test
    public void testDAO() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);

        PingPongBallDAO pingPongBallDAO = context.getBean(PingPongBallDAO.class);

        log.info("\nCreate a few mock ping pong balls...");
        for (int i = 1; i < 10; i++) {
            PingPongTarget randomTarget = getRandomTarget();
            Color randomColor = getRandomColor();
            PingPongBall pingPongBall = new PingPongBall(Integer.toString(i), randomTarget, randomColor);
            pingPongBallDAO.createPingPongBall(pingPongBall);
        }

        log.info("\nList of PingPongBalls is:");
        for (PingPongBall p : pingPongBallDAO.getAllPingPongBalls()) {
            log.info("{}", p);
        }

        log.info("\nGet PingPongBall with ID 7");
        PingPongBall pingPongBallById = pingPongBallDAO.getPingPongBallById("7");
        log.info("{}", pingPongBallById);

        log.info("\nDeleting PingPongBall with ID 7");
        pingPongBallDAO.deletePingPongBall(pingPongBallById);

        log.info("\nUpdate PingPongBall with ID 1");
        PingPongBall pingPongBall1 = pingPongBallDAO.getPingPongBallById("1");
        pingPongBall1.setColor(Color.RED);
        pingPongBall1.setPingPongTarget(PingPongTarget.PONG);
        pingPongBallDAO.updatePingPongBall(pingPongBall1);

        log.info("\nList of PingPongBalls is:");
        for (PingPongBall p : pingPongBallDAO.getAllPingPongBalls()) {
            log.info("{}", p);
        }

        log.info("\nCleaning up database:");
        for (PingPongBall p : pingPongBallDAO.getAllPingPongBalls()) {
            log.info("\nDeleting PingPongBall: {}", p);
            pingPongBallDAO.deletePingPongBall((p));
        }

        log.info("\nCleanup complete...");

        context.close();
    }

    final Color[] colors = Color.values();
    final PingPongTarget[] pingPongTargets = PingPongTarget.values();
    final Random random = new Random();

    private Color getRandomColor() {
        return colors[random.nextInt(colors.length)];
    }

    private PingPongTarget getRandomTarget() {
        return pingPongTargets[random.nextInt(pingPongTargets.length)];
    }
}
