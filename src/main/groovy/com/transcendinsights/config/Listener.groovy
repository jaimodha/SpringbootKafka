package com.transcendinsights.config

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener

import java.util.concurrent.CountDownLatch

/**
 * @author Jai Modha
 * @since 02-17-2017
 */
class Listener {

  CountDownLatch countDownLatch = new CountDownLatch(1)

  @KafkaListener
  void listen(ConsumerRecord<?, ?> record) {
    println(record)
    countDownLatch.countDown()
  }
}
