package com.cedrus.patpetillo.springkafkapingpong.stream;

import com.cedrus.cloud.streaming.kafka.kafkacommon.serialization.apicurio.AvroSerdeProvider;
import com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent;
import com.cedrus.patpetillo.springkafkapingpong.avro.TeamType;
import com.cedrus.patpetillo.springkafkapingpong.config.AppConfig;
import com.cedrus.patpetillo.springkafkapingpong.model.*;
import com.cedrus.patpetillo.springkafkapingpong.postgres.PingPongBallEntity;
import com.cedrus.patpetillo.springkafkapingpong.repository.PingPongBallEventRepository;
import io.apicurio.registry.client.RegistryService;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class TopologyProvider {

  private final AppConfig appConfig;
  private final AvroSerdeProvider<PingPongBallEvent> pingPongBallEventAvroSerdeProvider;
  private final RegistryService registryService;
  private final PingPongBallEventRepository PingPongEventRepository;

  public Topology getTopology(PingPongTeam pingPongTeam, Server server) {
    final StreamsBuilder builder = new StreamsBuilder();
    final Serde<PingPongBallEvent> pingPongBallEventSerde =
        pingPongBallEventAvroSerdeProvider.getSerde(registryService, false);

    log.info("Stream builder initialized");

    final KStream<String, PingPongBallEvent> incomingStream =
        builder.stream(
            appConfig.getTopicName(), Consumed.with(Serdes.String(), pingPongBallEventSerde));

    @SuppressWarnings("unchecked") // Branch - KStream
    final KStream<String, PingPongBallEvent>[] branches =
        incomingStream.branch(getTargetFilterPredicate(pingPongTeam));

    final KStream<String, PingPongBallEvent> pingPongStream = branches[0];

    final KStream<String, PingPongBallEvent> unmodifiedIncomingStream =
        pingPongStream.peek(
            (key, value) -> writeEventToDataStore(key, Action.RECEIVING_BALL, server));

    final KStream<String, PingPongBallEvent> loggedAndDelayedStream =
        unmodifiedIncomingStream.transformValues(getLogsAndDelay());

    final KStream<String, PingPongBallEvent> randomUUIDStream =
        loggedAndDelayedStream.selectKey((key, value) -> getRandomUUIDKey());

    final KStream<String, PingPongBallEvent> unmodifiedOutGoingStream =
        randomUUIDStream.peek(
            (key, value) -> writeEventToDataStore(key, Action.VOLLEYING_BALL, server));

    unmodifiedOutGoingStream.to(
        appConfig.getTopicName(), Produced.with(Serdes.String(), pingPongBallEventSerde));

    return builder.build();
  }

  private void writeEventToDataStore(String key, Action action, Server server) {
    final ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault());
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z");
    final String timestamp = formatter.format(zonedDateTime);

    final PingPongBallEntity pingPongBallEntity =
        new PingPongBallEntity(key, action, server, timestamp);
    PingPongEventRepository.save(pingPongBallEntity);
    log.info("Ping Pong event created: {}", pingPongBallEntity);
  }

  private String getRandomUUIDKey() {
    String uuid = UUID.randomUUID().toString();
    log.info("Generating random UUID for key: {}", uuid);
    return UUID.randomUUID().toString();
  }

  private Predicate<String, PingPongBallEvent> getTargetFilterPredicate(PingPongTeam pingPongTeam) {
    return (key, value) ->
        value.getCurrentTeamWithBall().equals(TeamType.valueOf(pingPongTeam.name()));
  }

  private ValueTransformerSupplier<PingPongBallEvent, PingPongBallEvent> getLogsAndDelay() {
    return () ->
        new ValueTransformer<PingPongBallEvent, PingPongBallEvent>() {
          @Override
          public void init(ProcessorContext context) {}

          @Override
          public PingPongBallEvent transform(PingPongBallEvent pingPongBallEvent) {
            log.debug("Ping pong ball received");
            log.info("Transforming ping pong ball: {}", pingPongBallEvent);
            final int minDelay = appConfig.getMinDelaySeconds();
            final int maxDelay = appConfig.getMaxDelaySeconds();

            final int sleepTime =
                ThreadLocalRandom.current().nextInt((maxDelay - minDelay) + minDelay);
            log.debug("Sleep for: {}", sleepTime);

            try {
              Thread.sleep(sleepTime * 1000L);
            } catch (InterruptedException e) {
              log.error("Sleep interrupted", e);
            }

            final TeamType currentTeam = pingPongBallEvent.getCurrentTeamWithBall();

            pingPongBallEvent.setTeamReceivingBall(returnBall(currentTeam));

            log.info("Returning ping pong ball: {}", pingPongBallEvent);
            return pingPongBallEvent;
          }

          @Override
          public void close() {}
        };
  }

  private TeamType returnBall(TeamType currentTeam) {
    log.info("Swapping TeamType in returnBall from: {}", currentTeam);
    return currentTeam.equals(TeamType.RED_TEAM) ? TeamType.BLUE_TEAM : TeamType.RED_TEAM;
  }
}
