package com.cedrus.patpetillo.springkafkapingpong.stream;

import com.cedrus.cloud.streaming.kafka.kafkacommon.serialization.apicurio.AvroSerdeProvider;
import com.cedrus.patpetillo.springkafkapingpong.avro.PingPongBallEvent;
import com.cedrus.patpetillo.springkafkapingpong.config.AppConfig;
import com.cedrus.patpetillo.springkafkapingpong.dao.PingPongBallDAO;
import com.cedrus.patpetillo.springkafkapingpong.model.*;
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
    private final PingPongBallDAO pingPongBallDAO;
    private final AvroSerdeProvider<PingPongBallEvent> pingPongBallEventAvroSerdeProvider;
    private final RegistryService registryService;

    public Topology getTopology(PingPongTeam pingPongTeam, String server) {
        final StreamsBuilder builder = new StreamsBuilder();
        final Serde<PingPongBallEvent> pingPongBallEventSerde = pingPongBallEventAvroSerdeProvider.getSerde(registryService, false);

        log.info("Stream builder initialized");

        final KStream<String, PingPongBallEvent> incomingStream = builder.stream(appConfig.getTopicName(), Consumed.with(Serdes.String(), pingPongBallEventSerde));

        @SuppressWarnings("unchecked") //Branch - KStream
        final KStream<String, PingPongBallEvent>[] branches = incomingStream.branch(getTargetFilterPredicate(pingPongTeam));

        final KStream<String, PingPongBallEvent> pingPongStream = branches[0];

        KStream<String, PingPongBallEvent> unmodifiedIncomingStream = writeEventToDataStore(pingPongStream, Action.RECEIVING_BALL, server);

        final KStream<String, PingPongBallEvent> loggedAndDelayedStream = unmodifiedIncomingStream.transformValues(getLogsAndDelay());

        final KStream<String, PingPongBallEvent> randomUUIDStream = getRandomUUID(loggedAndDelayedStream);

        KStream<String, PingPongBallEvent> unmodifiedOutGoingString = writeEventToDataStore(randomUUIDStream, Action.VOLLEYING_BALL, server);

        unmodifiedOutGoingString.to(appConfig.getTopicName(), Produced.with(Serdes.String(), pingPongBallEventSerde));

        return builder.build();
    }

    private KStream<String, PingPongBallEvent> writeEventToDataStore(KStream<String, PingPongBallEvent> stream, Action action, String server) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z");
        final String timestamp = formatter.format(zonedDateTime);

        return stream.peek(((key, value) -> {
            PingPongEvent pingPongEvent = new PingPongEvent();

            pingPongEvent.setKey(key);
            pingPongEvent.setAction(action);
            pingPongEvent.setServer(server);
            pingPongEvent.setTimeStamp(timestamp);
            pingPongEvent.setPingPongBallEvent(value);

            pingPongBallDAO.createPingPongEvent(pingPongEvent);
            log.info("Ping Pong event created: {}", pingPongEvent);
        }));
    }

    private KStream<String, PingPongBallEvent> getRandomUUID(KStream<String, PingPongBallEvent> stream) {
        UUID randomUUID = UUID.randomUUID();

        return stream.selectKey((key, value) -> randomUUID.toString());
    }

    private Predicate<String, PingPongBallEvent> getTargetFilterPredicate(PingPongTeam pingPongTeam) {
        return (key, value) -> value.getPingPongTeam().equals(pingPongTeam.name());
    }

    private ValueTransformerSupplier<PingPongBallEvent, PingPongBallEvent> getLogsAndDelay() {
        return () -> new ValueTransformer<PingPongBallEvent, PingPongBallEvent>() {
            @Override
            public void init(ProcessorContext context) {}

            @Override
            public PingPongBallEvent transform(PingPongBallEvent pingPongBallEvent) {
                log.debug("Ping pong ball received");
                log.info("Transforming ping pong ball: {}", pingPongBallEvent);
                final int minDelay = appConfig.getMinDelaySeconds();
                final int maxDelay = appConfig.getMaxDelaySeconds();

                final int sleepTime = ThreadLocalRandom.current().nextInt((maxDelay - minDelay) + minDelay);
                log.debug("Sleep for: {}", sleepTime);

                try {
                    Thread.sleep(sleepTime * 1000L);
                } catch (InterruptedException e) {
                    log.error("Sleep interrupted", e);
                }

                final PingPongTeam currentTeam = PingPongTeam.valueOf(pingPongBallEvent.getPingPongTeam());

                pingPongBallEvent.setPingPongTeam(returnBall(currentTeam).toString());

                log.info("Returning ping pong ball: {}", pingPongBallEvent);
                return pingPongBallEvent;
            }

            @Override
            public void close() {}
        };
    }

    private PingPongTeam returnBall(PingPongTeam currentTeam) {
        return currentTeam.equals(PingPongTeam.REDTEAM) ? PingPongTeam.BLUETEAM : PingPongTeam.REDTEAM;
    }
}
