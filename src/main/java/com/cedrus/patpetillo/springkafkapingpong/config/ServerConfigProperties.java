package com.cedrus.patpetillo.springkafkapingpong.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "server")
public class ServerConfigProperties {
  private String port;
}
