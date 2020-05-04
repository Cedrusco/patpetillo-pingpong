package com.cedrus.patpetillo.springkafkapingpong.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    private Integer minDelaySeconds;
    private Integer maxDelaySeconds;
<<<<<<< HEAD
    private String topicName;
    private String kafkaApplicationIdConfig;
    private String kafkaApplicationName;
=======
>>>>>>> 1ae2a5539a4a010828fb4e5cb9695eb7d419e5ee
}
