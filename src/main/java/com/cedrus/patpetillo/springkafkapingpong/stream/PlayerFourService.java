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
    final PingPongTeam team = PingPongTeam.BLUETEAM;

    log.info("Player Four on team: {}", team);

    KafkaStreams playerFourStream = new KafkaStreams(topologyProvider
        .getTopology(PingPongTeam.BLUETEAM, PlayerFourService.class.getSimpleName()),
        kafkaConnectionUtil.getKafkaProperties(team));

    playerFourStream.start();

    Runtime.getRuntime().addShutdownHook(new Thread(playerFourStream::close));
  }
}
