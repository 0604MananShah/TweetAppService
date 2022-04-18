package com.tweetapp.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

@EnableKafka
@Configuration
@Slf4j
@Generated
public class KafkaConsumerConfig {
	
	public static final String TWEET_APP = "tweet-app";
	
//	@Value(value = "${kafka.message}")
	public static final String MESSAGE = "message";
	
	@Value(value = "${kafka.serverConfig}")
	private String serverConfig;

	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, serverConfig);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, TWEET_APP);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(props);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	@KafkaListener(topics = MESSAGE, groupId = TWEET_APP)
	public void listen(String message) {
		log.info("Received Messasge in tweet-app " + message);
	}
}