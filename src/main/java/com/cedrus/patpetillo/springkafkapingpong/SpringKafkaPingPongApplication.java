package com.cedrus.patpetillo.springkafkapingpong;

<<<<<<< HEAD
<<<<<<< HEAD
=======
import com.cedrus.patpetillo.springkafkapingpong.stream.PlayerFourService;
import com.cedrus.patpetillo.springkafkapingpong.stream.PlayerOneService;
import com.cedrus.patpetillo.springkafkapingpong.stream.PlayerThreeService;
import com.cedrus.patpetillo.springkafkapingpong.stream.PlayerTwoService;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.boot.CommandLineRunner;
>>>>>>> 1ae2a5539a4a010828fb4e5cb9695eb7d419e5ee
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.apache.kafka.common.requests.FetchMetadata.log;

@SpringBootApplication
public class SpringKafkaPingPongApplication {

<<<<<<< HEAD
=======
import com.cedrus.patpetillo.springkafkapingpong.stream.PlayerFourService;
import com.cedrus.patpetillo.springkafkapingpong.stream.PlayerOneService;
import com.cedrus.patpetillo.springkafkapingpong.stream.PlayerThreeService;
import com.cedrus.patpetillo.springkafkapingpong.stream.PlayerTwoService;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import static org.apache.kafka.common.requests.FetchMetadata.log;

@ComponentScan(
		basePackages = {
				"com.cedrus.patpetillo.springkafkapingpong",
				"com.cedrus.cloud.streaming.kafka.kafkacommon"
		})
@SpringBootApplication
public class SpringKafkaPingPongApplication {

	public static final Marker FATAL_MARKER = MarkerFactory.getMarker("FATAL");

>>>>>>> b5d784298fa0736c5821cf4e4f4328cbf8b371fe
=======
	public static final Marker FATAL_MARKER = MarkerFactory.getMarker("FATAL");

>>>>>>> 1ae2a5539a4a010828fb4e5cb9695eb7d419e5ee
	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaPingPongApplication.class, args);
	}

<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> 1ae2a5539a4a010828fb4e5cb9695eb7d419e5ee
	@Bean
	public CommandLineRunner playerOneRunner(ApplicationContext ctx) {
		try {
			return args -> {
				log.info("Player One stream starting...");
				((PlayerOneService) ctx.getBean("playerOneService")).startPlayerOneService();
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
	public CommandLineRunner playerTwoRunner(ApplicationContext ctx) {
		try {
			return args -> {
				log.info("Player Two Stream starting...");
				((PlayerTwoService) ctx.getBean("playerTwoService")).startPlayerTwoService();
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
	public CommandLineRunner playerThreeRunner(ApplicationContext ctx) {
		try {
			return args -> {
				log.info("Player Three stream starting...");
				((PlayerThreeService) ctx.getBean("playerThreeService")).startPlayerThreeService();
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
	public CommandLineRunner playerFourRunner(ApplicationContext ctx) {
		try {
			return args -> {
				log.info("Ping stream starting...");
				((PlayerFourService) ctx.getBean("playerFourService")).startPlayerFourService();
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
<<<<<<< HEAD
>>>>>>> b5d784298fa0736c5821cf4e4f4328cbf8b371fe
=======
>>>>>>> 1ae2a5539a4a010828fb4e5cb9695eb7d419e5ee
}
