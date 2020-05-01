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
public class PlayerOneService {

  private final TopologyProvider topologyProvider;
  private final KafkaConnectionUtil kafkaConnectionUtil;

  public void startPlayerOneService() {
    final PingPongTeam team = PingPongTeam.REDTEAM;

    log.info("Player One on team: {}", team);

    final KafkaStreams playerOneStream = new KafkaStreams(
        topologyProvider.getTopology(PingPongTeam.REDTEAM, PlayerOneService.class.getSimpleName()),
        kafkaConnectionUtil.getKafkaProperties(team));

    playerOneStream.start();

    Runtime.getRuntime().addShutdownHook(new Thread(playerOneStream::close));
  }
}
