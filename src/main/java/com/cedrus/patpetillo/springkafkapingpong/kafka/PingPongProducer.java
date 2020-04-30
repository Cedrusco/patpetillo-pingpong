//package com.cedrus.patpetillo.springkafkapingpong.kafka;
//
//import com.cedrus.patpetillo.springkafkapingpong.config.AppConfig;
//import com.cedrus.patpetillo.springkafkapingpong.config.KafkaConfig;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.producer.*;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.stereotype.Component;
//
//import java.util.Properties;
//
//@Component
//@Slf4j
//@AllArgsConstructor
//public class PingPongProducer {
//    private final KafkaConfig kafkaConfig;
//    private final AppConfig appConfig;
//
//    public void sendMessage(String event, String key) {
//        log.debug("Sending event: {}", event);
//
//        final Properties props = new Properties();
//        props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaConfig.getKafkaAppId());
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//
//        final KafkaProducer<String, String> producer = new KafkaProducer<>(props);
//
//        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(appConfig.getTopicName(), key, event);
//        producer.send(producerRecord);
//        producer.close();
//
//
//    }
//}
