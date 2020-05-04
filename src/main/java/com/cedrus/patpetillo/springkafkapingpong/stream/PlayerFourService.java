package com.cedrus.patpetillo.springkafkapingpong.stream;

import com.cedrus.patpetillo.springkafkapingpong.kafka.KafkaConnectionUtil;
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongTeam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KafkaStreams;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class PlayerFourService {

  private final TopologyProvider topologyProvider;
  private final KafkaConnectionUtil kafkaConnectionUtil;

  public void startPlayerFourService() {
    final PingPongTeam pingPongTeam = PingPongTeam.BLUETEAM;

    log.info("Player Four on team: {}", pingPongTeam);

    KafkaStreams playerFourStream = new KafkaStreams(topologyProvider
        .getTopology(pingPongTeam, PlayerFourService.class.getSimpleName()),
        kafkaConnectionUtil.getKafkaProperties(pingPongTeam));

    playerFourStream.start();

    Runtime.getRuntime().addShutdownHook(new Thread(playerFourStream::close));
  }
}
