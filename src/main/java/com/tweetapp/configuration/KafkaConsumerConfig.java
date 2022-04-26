package com.tweetapp.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.tweetapp.util.TweetConstant;

import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Generated
public class KafkaConsumerConfig {

	@KafkaListener(topics = TweetConstant.TOPIC_NAME, groupId = TweetConstant.GROUP_ID)
	public void consume(String message) {
		log.info(String.format("Message recieved -> %s", message));
	}
}