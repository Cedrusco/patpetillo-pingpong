package com.cedrus.patpetillo.springkafkapingpong.kafka;

import com.cedrus.cloud.streaming.kafka.kafkacommon.KafkaConfigProperties;
import com.cedrus.cloud.streaming.kafka.kafkacommon.KafkaUtils;
import com.cedrus.patpetillo.springkafkapingpong.config.AppConfig;
import com.cedrus.patpetillo.springkafkapingpong.config.ServerConfigProperties;
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongTeam;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConnectionUtil {

  private final KafkaConfigProperties kafkaConfigProperties;
  private final ServerConfigProperties serverConfigProperties;
  private final AppConfig appConfig;
  private final Environment environment;

  @Autowired
  public KafkaConnectionUtil(
      KafkaConfigProperties kafkaConfigProperties,
      ServerConfigProperties serverConfigProperties,
      AppConfig appConfig,
      Environment environment) {
    this.kafkaConfigProperties = kafkaConfigProperties;
    this.serverConfigProperties = serverConfigProperties;
    this.appConfig = appConfig;
    this.environment = environment;
  }

  public Properties getKafkaProperties(PingPongTeam pingPongTeam) {
    log.info("Get Kafka Properties called with PingPongTeam: {}", pingPongTeam);
    return new KafkaUtils(kafkaConfigProperties, environment, serverConfigProperties.getPort())
        .buildKafkaProperties(appConfig.getKafkaApplicationName() + pingPongTeam.name(),
            appConfig.getKafkaApplicationName() + pingPongTeam.name());
  }
}
