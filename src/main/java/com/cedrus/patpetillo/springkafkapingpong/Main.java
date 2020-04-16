package com.cedrus.patpetillo.springkafkapingpong;

import com.cedrus.patpetillo.springkafkapingpong.config.DatabaseConfig;
import com.cedrus.patpetillo.springkafkapingpong.dao.PingPongBallDAO;
import com.cedrus.patpetillo.springkafkapingpong.model.Color;
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongBall;
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongTarget;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);

        PingPongBallDAO pingPongBallDAO = context.getBean(PingPongBallDAO.class);

        System.out.println("Create a few mock ping pong balls...");
        for (int i = 1; i < 10; i++) {
            PingPongTarget randomTarget = getRandomTarget();
            Color randomColor = getRandomColor();
            PingPongBall pingPongBall = new PingPongBall(Integer.toString(i), randomTarget, randomColor);
            pingPongBallDAO.createPingPongBall(pingPongBall);
        }

        System.out.println("List of PingPongBalls is:");

        for (PingPongBall p : pingPongBallDAO.getAllPingPongBalls()) {
            System.out.println(p);
        }

        System.out.println("\nGet PingPongBall with ID 7");

        PingPongBall pingPongBallById = pingPongBallDAO.getPingPongBallById("7");
        System.out.println(pingPongBallById);

        System.out.println("\nDeleting PingPongBall with ID 7");
        pingPongBallDAO.deletePingPongBall(pingPongBallById);

        System.out.println("\nUpdate PingPongBall with ID 1");

        PingPongBall pingPongBall1 = pingPongBallDAO.getPingPongBallById("1");
        pingPongBall1.setColor(Color.RED);
        pingPongBall1.setPingPongTarget(PingPongTarget.PONG);
        pingPongBallDAO.updatePingPongBall(pingPongBall1);

        System.out.println("\nList of PingPongBalls is:");
        for (PingPongBall p : pingPongBallDAO.getAllPingPongBalls()) {
            System.out.println(p);
        }

        context.close();
    }

    final static Color[] colors = Color.values();
    final static PingPongTarget[] pingPongTargets = PingPongTarget.values();
    final static Random random = new Random();

    private static Color getRandomColor() {
        return colors[random.nextInt(colors.length)];
    }

    private static PingPongTarget getRandomTarget() {
        return pingPongTargets[random.nextInt(pingPongTargets.length)];
    }
}
