package com.cedrus.patpetillo.springkafkapingpong.kafka;

import com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class AvroSender {

  private final KafkaTemplate<String, PingPongBallEvent> kafkaTemplate;

  public void send(String topic, String key, PingPongBallEvent pingPongBallEvent) {
    log.info("Sending Payload: {} with Key: {} to Topic: {}", pingPongBallEvent, key, topic);

    final ProducerRecord<String, PingPongBallEvent> record =
        new ProducerRecord<>(topic, key, pingPongBallEvent);

    kafkaTemplate.send(record);
  }
}
