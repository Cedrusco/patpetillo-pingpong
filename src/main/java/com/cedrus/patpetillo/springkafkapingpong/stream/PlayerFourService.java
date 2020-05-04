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
public class PlayerFourService {

    private final KafkaConfig kafkaConfig;
    private final TopologyProvider topologyProvider;

    @Autowired
    public PlayerFourService(KafkaConfig kafkaConfig, TopologyProvider topologyProvider){
        this.topologyProvider = topologyProvider;
        this.kafkaConfig = kafkaConfig;
    }

    public void startPlayerFourService() {
        Properties props = new Properties();
        PingPongTeam team = PingPongTeam.BLUETEAM;
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfig.getKafkaAppId() + team);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConfig.getAutoOffsetReset());

        log.info("Player Four on team: {}", team);

        KafkaStreams pingStream = new KafkaStreams(topologyProvider.getTopology(PingPongTeam.BLUETEAM), props);

        pingStream.start();

        Runtime.getRuntime().addShutdownHook(new Thread(pingStream::close));
    }
>>>>>>> 1ae2a5539a4a010828fb4e5cb9695eb7d419e5ee
}
