package com.cedrus.patpetillo.springkafkapingpong;

import com.cedrus.patpetillo.springkafkapingpong.config.AppConfig;
import com.cedrus.patpetillo.springkafkapingpong.config.KafkaConfig;
import com.cedrus.patpetillo.springkafkapingpong.config.TopicConfig;
import com.cedrus.patpetillo.springkafkapingpong.stream.PingService;
import com.cedrus.patpetillo.springkafkapingpong.stream.PongService;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.apache.kafka.common.requests.FetchMetadata.log;

@SpringBootApplication
public class SpringKafkaPingPongApplication {

	public static final Marker FATAL_MARKER = MarkerFactory.getMarker("FATAL");

	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaPingPongApplication.class, args);
	}

	@Bean
	public CommandLineRunner pingPongRunner(ApplicationContext ctx) {
		try {
			return args -> {
				log.info("Ping stream starting...");
				((PingService) ctx.getBean("pingService")).startPingStream();
			};
		} catch (RuntimeException rex) {
			log.error(
					FATAL_MARKER,
					"RuntimeException encountered when trying to start the service using CommandLineRunner message={}",
					rex.getMessage());
			log.error("RuntimeException", rex);
			throw rex;
		}
	}

	@Bean
	public CommandLineRunner pongRunner(ApplicationContext ctx) {
		try {
			return args -> {
				log.info("Pong stream starting...");
				((PongService) ctx.getBean("pongService")).startPongStream();
			};
		} catch (RuntimeException rex) {
			log.error(
					FATAL_MARKER,
					"RuntimeException encountered when trying to start the service using CommandLineRunner message={}",
					rex.getMessage());
			log.error("RuntimeException", rex);
			throw rex;
		}
	}
}
