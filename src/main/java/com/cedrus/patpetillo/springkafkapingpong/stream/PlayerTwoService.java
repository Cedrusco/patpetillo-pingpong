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
public class PlayerTwoService {

  private final TopologyProvider topologyProvider;
  private final KafkaConnectionUtil kafkaConnectionUtil;

  public void startPlayerTwoService() {
    final PingPongTeam team = PingPongTeam.BLUETEAM;

    log.info("Player Two on team: {}", team);

    final KafkaStreams playerTwoStream = new KafkaStreams(
        topologyProvider.getTopology(PingPongTeam.BLUETEAM, PlayerTwoService.class.getSimpleName()),
        kafkaConnectionUtil.getKafkaProperties(team));

    playerTwoStream.start();

    Runtime.getRuntime().addShutdownHook(new Thread(playerTwoStream::close));
  }
}
