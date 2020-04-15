package com.cedrus.patpetillo.springkafkapingpong.stream;

import com.cedrus.patpetillo.springkafkapingpong.config.KafkaConfig;
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongTarget;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class PingService {

    private final KafkaConfig kafkaConfig;
    private final TopologyProvider topologyProvider;

    @Autowired
    public PingService(KafkaConfig kafkaConfig, TopologyProvider topologyProvider){
        this.topologyProvider = topologyProvider;
        this.kafkaConfig = kafkaConfig;
    }

    public void startPingStream() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfig.getKafkaAppId() + PingPongTarget.PING);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put("AUTO_OFFSET_RESET_CONFIG", kafkaConfig.getAutoOffsetReset());

        KafkaStreams pingStream = new KafkaStreams(topologyProvider.getTopology(PingPongTarget.PING), props);

        pingStream.start();

        Runtime.getRuntime().addShutdownHook(new Thread(pingStream::close));
    }
}
