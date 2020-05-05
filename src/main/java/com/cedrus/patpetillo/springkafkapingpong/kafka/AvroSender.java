package com.cedrus.patpetillo.springkafkapingpong.kafka;

import com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AvroSender {

  @Autowired
  private KafkaTemplate<String, PingPongBallEvent> kafkaTemplate;

  public void send(String topic, String key, PingPongBallEvent pingPongBallEvent) {
    log.info("Sending Payload: {} with Key: {} to Topic: {}", pingPongBallEvent.toString(), key,
        topic);
    ProducerRecord<String, PingPongBallEvent> record = new ProducerRecord<>(topic, key,
        pingPongBallEvent);
    kafkaTemplate.send(record);
  }
}
