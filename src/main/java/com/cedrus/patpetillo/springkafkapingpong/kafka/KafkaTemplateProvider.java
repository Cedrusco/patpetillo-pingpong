package com.cedrus.patpetillo.springkafkapingpong.kafka;

import com.cedrus.cloud.streaming.kafka.kafkacommon.serialization.apicurio.AvroSerdeProvider;
import com.cedrus.patpetillo.springkafkapingpong.avro.PingPongEvent;
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
  private final AvroSerdeProvider<PingPongEvent> PingPongEventAvroSerdeProvider;

  @Autowired
  public KafkaTemplateProvider(
      KafkaConnectionUtil kafkaConnectionUtil,
      RegistryService registryService,
      AvroSerdeProvider<PingPongEvent> PingPongEventAvroSerdeProvider) {
    this.kafkaConnectionUtil = kafkaConnectionUtil;
    this.registryService = registryService;
    this.PingPongEventAvroSerdeProvider = PingPongEventAvroSerdeProvider;
  }

  @Bean
  public KafkaTemplate<String, PingPongEvent> createKafkaTemplate() {

    DefaultKafkaProducerFactory<String, PingPongEvent> defaultKafkaProducerFactory =
        new DefaultKafkaProducerFactory(
            kafkaConnectionUtil.getKafkaProperties(),
            new StringSerializer(),
            PingPongEventAvroSerdeProvider.getSerde(registryService, false).serializer());
    return new KafkaTemplate<>(defaultKafkaProducerFactory);
  }
}
