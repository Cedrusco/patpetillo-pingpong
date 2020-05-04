package com.cedrus.patpetillo.springkafkapingpong.kafka;

import com.cedrus.cloud.streaming.kafka.kafkacommon.serialization.apicurio.AvroSerdeProvider;
import com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent;
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongTeam;
import io.apicurio.registry.client.RegistryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@Configuration
public class KafkaTemplateProvider {
  private final KafkaConnectionUtil kafkaConnectionUtil;
  private final RegistryService registryService;
  private final AvroSerdeProvider<PingPongBallEvent> pingPongBallEventAvroSerdeProvider;

  @Autowired
  public KafkaTemplateProvider(
      KafkaConnectionUtil kafkaConnectionUtil,
      RegistryService registryService,
      AvroSerdeProvider<PingPongBallEvent> pingPongBallEventAvroSerdeProvider) {
    this.kafkaConnectionUtil = kafkaConnectionUtil;
    this.registryService = registryService;
    this.pingPongBallEventAvroSerdeProvider = pingPongBallEventAvroSerdeProvider;
  }

  @Bean
  public KafkaTemplate<String, PingPongBallEvent> createKafkaTemplate() {

    DefaultKafkaProducerFactory<String, PingPongBallEvent> defaultKafkaProducerFactory =
        new DefaultKafkaProducerFactory(
            kafkaConnectionUtil.getKafkaProperties(),
            new StringSerializer(),
            pingPongBallEventAvroSerdeProvider.getSerde(registryService, false).serializer());
    return new KafkaTemplate<>(defaultKafkaProducerFactory);
  }
}
