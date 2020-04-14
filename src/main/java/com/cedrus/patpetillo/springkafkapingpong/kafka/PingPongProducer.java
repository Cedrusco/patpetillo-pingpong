package com.cedrus.patpetillo.springkafkapingpong.kafka;

import com.cedrus.patpetillo.springkafkapingpong.config.KafkaConfig;
import com.cedrus.patpetillo.springkafkapingpong.config.TopicConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Slf4j
public class PingPongProducer {
    private final KafkaConfig kafkaConfig;
    private final TopicConfig topicConfig;

    @Autowired
    public PingPongProducer(KafkaConfig kafkaConfig, TopicConfig topicConfig) {
        this.kafkaConfig = kafkaConfig;
        this.topicConfig = topicConfig;
    }

    public void sendMessage(String event, String key) {
        log.debug("Sending event: {}", event);

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfig.getKafkaAppId());
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer producer = new KafkaProducer(props);

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicConfig.getTopicName(), key, event);
        producer.send(producerRecord);
        producer.close();
    }
}
