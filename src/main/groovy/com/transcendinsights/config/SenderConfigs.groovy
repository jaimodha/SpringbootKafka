package com.transcendinsights.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

/**
 * @author Jai Modha
 * @since 02-17-2017
 */
@EnableKafka
@Configuration
class SenderConfigs {

  @Value("${kafka.bootstrap.server}")
  String bootstrapServer

  @Bean
  Map producerConfigs() {
    def properties = [:]
    // list of host:port pairs used for establishing the initial connections to the kafka cluster
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer)
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class)
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class)
    properties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000)
    properties.put(ProducerConfig.RETRIES_CONFIG, 0);
    properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
    properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
    properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
    properties
  }

  @Bean
  ProducerFactory<String, String> producerFactory() {
    new DefaultKafkaProducerFactory<>(producerConfigs())
  }

  @Bean
  KafkaTemplate kafkaTemplate() {
    new KafkaTemplate<String, String>(producerFactory())
  }

}
