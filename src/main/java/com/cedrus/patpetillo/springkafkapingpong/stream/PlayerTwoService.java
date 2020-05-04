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
public class PlayerTwoService {

  private final TopologyProvider topologyProvider;
  private final KafkaConnectionUtil kafkaConnectionUtil;

  public void startPlayerTwoService() {
    final PingPongTeam pingPongTeam = PingPongTeam.BLUETEAM;

    log.info("Player Two on team: {}", pingPongTeam);

    final KafkaStreams playerTwoStream = new KafkaStreams(
        topologyProvider.getTopology(pingPongTeam, PlayerTwoService.class.getSimpleName()),
        kafkaConnectionUtil.getKafkaProperties(pingPongTeam));

    playerTwoStream.start();

    Runtime.getRuntime().addShutdownHook(new Thread(playerTwoStream::close));
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
public class PlayerTwoService {

    private final KafkaConfig kafkaConfig;
    private final TopologyProvider topologyProvider;

    @Autowired
    public PlayerTwoService(KafkaConfig kafkaConfig, TopologyProvider topologyProvider){
        this.topologyProvider = topologyProvider;
        this.kafkaConfig = kafkaConfig;
    }

    public void startPlayerTwoService() {
        final Properties props = new Properties();
        final PingPongTeam team = PingPongTeam.BLUETEAM;
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfig.getKafkaAppId() + team);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConfig.getAutoOffsetReset());

        log.info("Player Two on team: {}", team);

        final KafkaStreams pongStream = new KafkaStreams(topologyProvider.getTopology(PingPongTeam.BLUETEAM), props);

        pongStream.start();

        Runtime.getRuntime().addShutdownHook(new Thread(pongStream::close));
    }
>>>>>>> 1ae2a5539a4a010828fb4e5cb9695eb7d419e5ee
}
