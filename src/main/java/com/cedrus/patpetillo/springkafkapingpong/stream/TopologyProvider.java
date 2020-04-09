package com.cedrus.patpetillo.springkafkapingpong.stream;

import com.cedrus.patpetillo.springkafkapingpong.config.AppConfig;
import com.cedrus.patpetillo.springkafkapingpong.config.TopicConfig;
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongBall;
import com.cedrus.patpetillo.springkafkapingpong.model.PingPongTarget;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

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

        final KStream<String, String> filteredStream = incomingStream.branch((key, value) -> {
            PingPongBall pingPongBall = deserialize(value);
            log.info("deserialized value: " + pingPongBall.toString());
            return pingPongBall.getPingPongTarget().equals(pingPongTarget);
        })[0];

        final KStream<String, String> loggedAndDelayedStream = filteredStream.transformValues(getLogsAndDelay());

        loggedAndDelayedStream.to(topicConfig.getTopicName(), Produced.with(Serdes.String(), Serdes.String()));

        return builder.build();
    }

    private PingPongBall deserialize(String pingPongBallString) {
        try {
            return objectMapper.readValue(pingPongBallString, PingPongBall.class);
        } catch (Exception e) {
            log.debug("Deserialize error " + pingPongBallString);
            throw new RuntimeException(e);
        }
    }

    private String serialize(PingPongBall pingPongBall) {
        try {
            return objectMapper.writeValueAsString(pingPongBall);
        } catch (Exception e) {
            log.debug("Serialize error " + pingPongBall);
            throw new RuntimeException(e);
        }
    }

    private ValueTransformerSupplier<String, String> getLogsAndDelay() {
        return () -> new ValueTransformer<String, String>() {
            @Override
            public void init(ProcessorContext context) {}

            @Override
            public String transform(String value) {
                log.info("Transforming ping pong ball: ", value);
                log.debug("Ping pong ball received");
                final int minDelay = appConfig.getMinDelaySeconds();
                final int maxDelay = appConfig.getMaxDelaySeconds();

                final Random random = new Random();
                final int sleepTime = random.nextInt((maxDelay - minDelay) + minDelay);
                log.debug("Sleep for: ", sleepTime);

                try {
                    Thread.sleep(sleepTime * 1000L);
                    return value;
                } catch (InterruptedException e) {
                    log.error("Sleep interrupted", e);
                }

                final PingPongBall pingPongBall = deserialize(value);
                pingPongBall.returnBall();
                return serialize(pingPongBall);
            }

            @Override
            public void close() {}
        };
    }
}
