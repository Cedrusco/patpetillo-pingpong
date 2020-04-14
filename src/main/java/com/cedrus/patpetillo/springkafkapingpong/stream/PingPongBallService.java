package com.cedrus.patpetillo.springkafkapingpong.stream;

import com.cedrus.patpetillo.springkafkapingpong.kafka.PingPongProducer;
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongBall;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class PingPongBallService {
    private final PingPongProducer pingPongProducer;
    private final ObjectMapper objectMapper;

    public PingPongBallService(PingPongProducer pingPongProducer, ObjectMapper objectMapper) {
        this.pingPongProducer = pingPongProducer;
        this.objectMapper = objectMapper;
    }

    public void serveBall(PingPongBall pingPongBall) {
        try {
            String ballId = pingPongBall.getId();
            String pingPongBallJSON = objectMapper.writeValueAsString(pingPongBall);
            pingPongProducer.sendMessage(pingPongBallJSON, ballId);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
