package com.cedrus.patpetillo.springkafkapingpong.stream;

import com.cedrus.patpetillo.springkafkapingpong.config.AppConfig;
import com.cedrus.patpetillo.springkafkapingpong.config.TopicConfig;
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongBall;
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongTarget;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
public class TopologyProvider {

    private final TopicConfig topicConfig;
    private final AppConfig appConfig;
    private final ObjectMapper objectMapper;

    @Autowired
    public TopologyProvider(TopicConfig topicConfig, AppConfig appConfig, ObjectMapper objectMapper){
        this.topicConfig = topicConfig;
        this.appConfig = appConfig;
        this.objectMapper = objectMapper;
    }

    public Topology getTopology(PingPongTarget pingPongTarget) {
        final StreamsBuilder builder = new StreamsBuilder();
        log.info("Stream builder initialized");
        log.info(pingPongTarget.toString());

        final KStream<String, String> incomingStream = builder.stream(topicConfig.getTopicName(), Consumed.with(Serdes.String(), Serdes.String()));

        final KStream<String, String> pingPongStream = getPingPongStream(incomingStream, pingPongTarget);

        final KStream<String, String> loggedAndDelayedStream = pingPongStream.transformValues(getLogsAndDelay());

        loggedAndDelayedStream.to(topicConfig.getTopicName(), Produced.with(Serdes.String(), Serdes.String()));

        return builder.build();
    }

    private KStream<String, String> getPingPongStream(KStream<String, String> incomingStream, PingPongTarget pingPongTarget) {
        final KStream<String, String> pingPongStream = incomingStream
                // Ensure we aren't processing volleyCount stream
                .filter((key, value) -> value.contains("pingPongTarget"))
                .filter((key, value) -> {
                    PingPongBall pingPongBall = deserialize(value);
                    log.info("deserialize value: {}", pingPongBall);
                    return pingPongBall.getPingPongTarget().equals(pingPongTarget);
                });

        return pingPongStream;
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
