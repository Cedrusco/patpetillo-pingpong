package com.cedrus.patpetillo.springkafkapingpong.stream;

import com.cedrus.patpetillo.springkafkapingpong.kafka.PingPongProducer;
import com.cedrus.patpetillo.springkafkapingpong.model.Action;
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongBall;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
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
            final String pingPongBallJSON = objectMapper.writeValueAsString(pingPongBall);
            final UUID uuid = UUID.randomUUID();
            final String key = uuid.toString();

            pingPongProducer.sendMessage(pingPongBallJSON, key);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
