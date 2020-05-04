package com.cedrus.patpetillo.springkafkapingpong.stream;

<<<<<<< HEAD
import com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent;
import com.cedrus.patpetillo.springkafkapingpong.config.AppConfig;
import com.cedrus.patpetillo.springkafkapingpong.kafka.AvroSender;
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongBall;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Slf4j
@Service
public class PingPongBallService {

  private final AvroSender avroSender;
  private final AppConfig appConfig;

  public void serveBall(PingPongBall pingPongBall) {
    try {
      final UUID uuid = UUID.randomUUID();
      final String key = uuid.toString();
      final PingPongBallEvent pingPongBallEvent = new PingPongBallEvent(pingPongBall.getId(),
          pingPongBall.getPingPongTeam().name(), pingPongBall.getColor().name());

      avroSender.send(appConfig.getTopicName(), key, pingPongBallEvent);
    } catch (Exception e) {
      log.error("AvroSender exception: {}", e.getMessage());
      throw new RuntimeException(e);
    }
  }
=======
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
            final String pingPongBallJSON = objectMapper.writeValueAsString(pingPongBall);

            pingPongProducer.sendMessage(pingPongBallJSON, null);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
>>>>>>> 1ae2a5539a4a010828fb4e5cb9695eb7d419e5ee
}
