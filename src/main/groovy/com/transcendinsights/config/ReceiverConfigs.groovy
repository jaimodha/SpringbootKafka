package com.transcendinsights.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer

/**
 * @author Jai Modha
 * @since 02-17-2017
 */
@EnableKafka
@Configuration
class ReceiverConfigs {

  @Value("${kafka.bootstrap.server}")
  String bootstrapServer

  @Bean
  Map consumerConfigs() {
    def properties = [:]
    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer)
    properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false)
    properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
    properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
    properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    properties
  }

  @Bean
  ConsumerFactory<String, String> consumerFactory() {
    new DefaultKafkaConsumerFactory(consumerConfigs())
  }

  @Bean
  KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaMessageListenerContainer() {
    ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>()
    factory.setConsumerFactory(consumerFactory())
    factory.setConcurrency(3)
    factory.getContainerProperties().setPollTimeout(3000)
    factory
  }
}
