package com.cedrus.patpetillo.springkafkapingpong.kafka;

import com.cedrus.patpetillo.springkafkapingpong.avro.PingPongEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class AvroSender {

  private final KafkaTemplate<String, PingPongEvent> kafkaTemplate;

  public void send(String topic, String key, PingPongEvent PingPongEvent) {
    log.info("Sending Payload: {} with Key: {} to Topic: {}", PingPongEvent.toString(), key,
        topic);

    final ProducerRecord<String, PingPongEvent> record = new ProducerRecord<>(topic, key,
        PingPongEvent);

    kafkaTemplate.send(record);
  }
}
