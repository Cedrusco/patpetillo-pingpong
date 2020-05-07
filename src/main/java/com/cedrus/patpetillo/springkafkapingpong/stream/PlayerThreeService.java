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
public class PlayerThreeService {

  private final TopologyProvider topologyProvider;
  private final KafkaConnectionUtil kafkaConnectionUtil;

  public void startPlayerThreeService() {
    final PingPongTeam pingPongTeam = PingPongTeam.RED_TEAM;

    log.info("Player Three on team: {}", pingPongTeam);

    KafkaStreams playerThreeStream = new KafkaStreams(topologyProvider
        .getTopology(pingPongTeam, Server.PLAYER_THREE_SERVICE),
        kafkaConnectionUtil.getKafkaProperties(pingPongTeam));

    playerThreeStream.start();

    Runtime.getRuntime().addShutdownHook(new Thread(playerThreeStream::close));
  }
}
