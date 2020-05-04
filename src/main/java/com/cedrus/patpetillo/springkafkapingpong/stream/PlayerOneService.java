package com.cedrus.patpetillo.springkafkapingpong.stream;

<<<<<<< HEAD
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
    final PingPongTeam pingPongTeam = PingPongTeam.REDTEAM;

    log.info("Player One on team: {}", pingPongTeam);

    final KafkaStreams playerOneStream = new KafkaStreams(
        topologyProvider.getTopology(pingPongTeam, PlayerOneService.class.getSimpleName()),
        kafkaConnectionUtil.getKafkaProperties(pingPongTeam));

    playerOneStream.start();

    Runtime.getRuntime().addShutdownHook(new Thread(playerOneStream::close));
  }
=======
import com.cedrus.patpetillo.springkafkapingpong.config.KafkaConfig;
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongTeam;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class PlayerOneService {

    private final KafkaConfig kafkaConfig;
    private final TopologyProvider topologyProvider;

    @Autowired
    public PlayerOneService(KafkaConfig kafkaConfig, TopologyProvider topologyProvider){
        this.topologyProvider = topologyProvider;
        this.kafkaConfig = kafkaConfig;
    }

    public void startPlayerOneService() {
        Properties props = new Properties();
        PingPongTeam team = PingPongTeam.REDTEAM;
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfig.getKafkaAppId() + team);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConfig.getAutoOffsetReset());

        log.info("Player One on team: {}", team);

        KafkaStreams pingStream = new KafkaStreams(topologyProvider.getTopology(PingPongTeam.REDTEAM), props);

        pingStream.start();

        Runtime.getRuntime().addShutdownHook(new Thread(pingStream::close));
    }
>>>>>>> 1ae2a5539a4a010828fb4e5cb9695eb7d419e5ee
}
