package com.cedrus.patpetillo.springkafkapingpong.stream;

import com.cedrus.patpetillo.springkafkapingpong.config.AppConfig;
import com.cedrus.patpetillo.springkafkapingpong.config.TopicConfig;
import com.cedrus.patpetillo.springkafkapingpong.dao.PingPongBallDAO;
import com.cedrus.patpetillo.springkafkapingpong.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
public class TopologyProvider {

    private final TopicConfig topicConfig;
    private final AppConfig appConfig;
    private final ObjectMapper objectMapper;
    private final PingPongBallDAO pingPongBallDAO;

    @Autowired
    public TopologyProvider(TopicConfig topicConfig, AppConfig appConfig, ObjectMapper objectMapper, PingPongBallDAO pingPongBallDAO){
        this.topicConfig = topicConfig;
        this.appConfig = appConfig;
        this.objectMapper = objectMapper;
        this.pingPongBallDAO = pingPongBallDAO;
    }

    public Topology getTopology(PingPongTeam pingPongTeam, String server) {
        final StreamsBuilder builder = new StreamsBuilder();

        log.info("Stream builder initialized");

        final KStream<String, String> incomingStream = builder.stream(topicConfig.getTopicName(), Consumed.with(Serdes.String(), Serdes.String()));

        final KStream<String, String>[] branches = incomingStream.branch(getTargetFilterPredicate(pingPongTeam));

        final KStream<String, String> pingPongStream = branches[0];

        KStream<String, String> unmodifiedIncomingStream = writeEventToDataStore(pingPongStream, Action.RECEIVING_BALL, server);

        final KStream<String, String> loggedAndDelayedStream = unmodifiedIncomingStream.transformValues(getLogsAndDelay());

        final KStream<String, String> randomUUIDStream = getRandomUUID(loggedAndDelayedStream);

        KStream<String, String> unmodifiedOutGoingString = writeEventToDataStore(randomUUIDStream, Action.VOLLEYING_BALL, server);

        unmodifiedOutGoingString.to(topicConfig.getTopicName(), Produced.with(Serdes.String(), Serdes.String()));

        return builder.build();
    }

    private KStream<String, String> writeEventToDataStore(KStream<String, String> stream, Action action, String server) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z");
        final String timestamp = formatter.format(zonedDateTime);

        log.debug("zonedDateTime: {}", zonedDateTime);
        log.debug("formatter: {}", formatter);

        return stream.peek(((key, value) -> {
            PingPongEvent pingPongEvent = new PingPongEvent();
            final UUID uuid = UUID.randomUUID();

            pingPongEvent.setId(uuid);
            pingPongEvent.setKey(key);
            pingPongEvent.setAction(action);
            pingPongEvent.setServer(server);
            pingPongEvent.setTimeStamp(timestamp);
            PingPongBall pingPongBall = deserialize(value);
            pingPongEvent.setPingPongBall(pingPongBall);

            pingPongBallDAO.createPingPongEvent(pingPongEvent);
        }));
    }

    private KStream<String, String> getRandomUUID(KStream<String, String> stream) {
        UUID randomUUID = UUID.randomUUID();
        log.debug("randomUUID: {}", randomUUID);
        return stream.selectKey((key, value) -> randomUUID.toString());
    }

    private Predicate<String, String> getTargetFilterPredicate(PingPongTeam pingPongTeam) {
        return (key, value) -> {
            PingPongBall pingPongBall = deserialize(value);
            return pingPongBall.getPingPongTeam().equals(pingPongTeam);
        };
    }

    private PingPongBall deserialize(String pingPongBallString) {
        try {
            return objectMapper.readValue(pingPongBallString, PingPongBall.class);
        } catch (Exception e) {
            log.debug("Deserialize Ping Pong ball error: {}", pingPongBallString);
            throw new RuntimeException(e);
        }
    }

    private String serialize(PingPongBall pingPongBall) {
        try {
            return objectMapper.writeValueAsString(pingPongBall);
        } catch (Exception e) {
            log.debug("Serialize Ping Pong ball error: {}", pingPongBall);
            throw new RuntimeException(e);
        }
    }

    private ValueTransformerSupplier<String, String> getLogsAndDelay() {
        return () -> new ValueTransformer<String, String>() {
            @Override
            public void init(ProcessorContext context) {}

            @Override
            public String transform(String value) {
                log.debug("Ping pong ball received");
                log.info("Transforming ping pong ball: {}", value);
                final int minDelay = appConfig.getMinDelaySeconds();
                final int maxDelay = appConfig.getMaxDelaySeconds();

                final int sleepTime = ThreadLocalRandom.current().nextInt((maxDelay - minDelay) + minDelay);
                log.debug("Sleep for: {}", sleepTime);

                try {
                    Thread.sleep(sleepTime * 1000L);
                } catch (InterruptedException e) {
                    log.error("Sleep interrupted", e);
                }

                final PingPongBall pingPongBall = deserialize(value);

                log.info("Returning ping pong ball: {}", pingPongBall);
                pingPongBall.returnBall();
                return serialize(pingPongBall);
            }

            @Override
            public void close() {}
        };
    }
}
