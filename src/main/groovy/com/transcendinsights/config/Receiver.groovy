package com.transcendinsights.config

import com.transcendinsights.Model.KafkaModel
import groovy.json.JsonSlurper

/**
 * @author Jai Modha
 * @since 02-17-2017
 */
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class Receiver {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(Receiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = "test")
    public void receiveMessage(String message) {
        LOGGER.info("received message='{}'", message);
        KafkaModel response = new JsonSlurper().parseText(message) as KafkaModel
        LOGGER.info("Id: $response.id")
        LOGGER.info("name: $response.name")
        LOGGER.info("${response.getClass()}")
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}