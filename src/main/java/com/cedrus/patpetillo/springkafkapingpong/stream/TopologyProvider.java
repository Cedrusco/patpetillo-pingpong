package com.cedrus.patpetillo.springkafkapingpong.stream;

import com.cedrus.cloud.streaming.kafka.kafkacommon.serialization.apicurio.AvroSerdeProvider;
import com.cedrus.patpetillo.springkafkapingpong.avro.PingPongEvent;
import com.cedrus.patpetillo.springkafkapingpong.avro.TeamType;
import com.cedrus.patpetillo.springkafkapingpong.config.AppConfig;
import com.cedrus.patpetillo.springkafkapingpong.model.*;
import com.cedrus.patpetillo.springkafkapingpong.postgres.PingPongBallEntity;
import com.cedrus.patpetillo.springkafkapingpong.repository.PingPongBallEventRepository;
import io.apicurio.registry.client.RegistryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
@AllArgsConstructor
public class TopologyProvider {

  private final AppConfig appConfig;
  private final AvroSerdeProvider<PingPongEvent> PingPongEventAvroSerdeProvider;
  private final RegistryService registryService;
  private final PingPongBallEventRepository PingPongEventRepository;

  public Topology getTopology(PingPongTeam pingPongTeam, Server server) {
    final StreamsBuilder builder = new StreamsBuilder();
    final Serde<PingPongEvent> PingPongEventSerde = PingPongEventAvroSerdeProvider
        .getSerde(registryService, false);

    log.info("Stream builder initialized");

    final KStream<String, PingPongEvent> incomingStream = builder
        .stream(appConfig.getTopicName(), Consumed.with(Serdes.String(), PingPongEventSerde));

    @SuppressWarnings("unchecked") //Branch - KStream
    final KStream<String, PingPongEvent>[] branches = incomingStream
        .branch(getTargetFilterPredicate(pingPongTeam));

    final KStream<String, PingPongEvent> pingPongStream = branches[0];

    final KStream<String, PingPongEvent> unmodifiedIncomingStream = pingPongStream
        .peek((key, value) -> writeEventToDataStore(key, value, Action.RECEIVING_BALL, server));

    final KStream<String, PingPongEvent> loggedAndDelayedStream = unmodifiedIncomingStream
        .transformValues(getLogsAndDelay());

    final KStream<String, PingPongEvent> randomUUIDStream = loggedAndDelayedStream
        .selectKey((key, value) -> getRandomUUIDKey());

    final KStream<String, PingPongEvent> unmodifiedOutGoingStream = randomUUIDStream
        .peek((key, value) -> writeEventToDataStore(key, value, Action.VOLLEYING_BALL, server));

    unmodifiedOutGoingStream
        .to(appConfig.getTopicName(), Produced.with(Serdes.String(), PingPongEventSerde));

    return builder.build();
  }

  private void writeEventToDataStore(String key,
      PingPongEvent value, Action action, Server server) {
    final ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault());
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z");
    final String timestamp = formatter.format(zonedDateTime);

    final PingPongBallEntity pingPongBallEntity = new PingPongBallEntity(key, action, server, timestamp);
    PingPongEventRepository.save(pingPongBallEntity);
    log.info("Ping Pong event created: {}", pingPongBallEntity);
  }

  private String getRandomUUIDKey() {
    String uuid = UUID.randomUUID().toString();
    log.info("Generating random UUID for key: {}", uuid);
    return UUID.randomUUID().toString();
  }

  private Predicate<String, PingPongEvent> getTargetFilterPredicate(PingPongTeam pingPongTeam) {
    return (key, value) -> value.getTeamWithBallCurrently().equals(TeamType.valueOf(pingPongTeam.name()));
  }

  private ValueTransformerSupplier<PingPongEvent, PingPongEvent> getLogsAndDelay() {
    return () -> new ValueTransformer<PingPongEvent, PingPongEvent>() {
      @Override
      public void init(ProcessorContext context) {
      }

      @Override
      public PingPongEvent transform(PingPongEvent PingPongEvent) {
        log.debug("Ping pong ball received");
        log.info("Transforming ping pong ball: {}", PingPongEvent);
        final int minDelay = appConfig.getMinDelaySeconds();
        final int maxDelay = appConfig.getMaxDelaySeconds();

        final int sleepTime = ThreadLocalRandom.current().nextInt((maxDelay - minDelay) + minDelay);
        log.debug("Sleep for: {}", sleepTime);

        try {
          Thread.sleep(sleepTime * 1000L);
        } catch (InterruptedException e) {
          log.error("Sleep interrupted", e);
        }

        final TeamType currentTeam = PingPongEvent.getTeamWithBallCurrently();

        final TeamType receivingTeam = returnBall(currentTeam);

        PingPongEvent.setTeamReceivingVolley(TeamType.valueOf(receivingTeam.name()));

        log.info("Returning ping pong ball: {}", PingPongEvent);
        return PingPongEvent;
      }

      @Override
      public void close() {
      }
    };
  }

  private TeamType returnBall(TeamType currentTeam) {
    log.info("Swapping TeamType in returnBall from: {}", currentTeam);
    return currentTeam.equals(TeamType.RED_TEAM) ? TeamType.BLUE_TEAM : TeamType.RED_TEAM;
  }
}
