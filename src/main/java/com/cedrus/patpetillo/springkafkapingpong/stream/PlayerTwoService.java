package com.cedrus.patpetillo.springkafkapingpong.stream;

import com.cedrus.patpetillo.springkafkapingpong.kafka.KafkaConnectionUtil;
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongTeam;
import com.cedrus.patpetillo.springkafkapingpong.model.Server;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KafkaStreams;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class PlayerTwoService {

  private final TopologyProvider topologyProvider;
  private final KafkaConnectionUtil kafkaConnectionUtil;

  public void startPlayerTwoService() {
    final PingPongTeam pingPongTeam = PingPongTeam.BLUE_TEAM;

    log.info("Player Two on team: {}", pingPongTeam);

    final KafkaStreams playerTwoStream =
        new KafkaStreams(
            topologyProvider.getTopology(pingPongTeam, Server.PLAYER_TWO_SERVICE),
            kafkaConnectionUtil.getKafkaProperties(pingPongTeam));

    playerTwoStream.start();

    Runtime.getRuntime().addShutdownHook(new Thread(playerTwoStream::close));
  }
}
