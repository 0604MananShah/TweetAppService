package com.tweetapp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import com.tweetapp.util.TweetConstant;

import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Generated
@Configuration
public class KafkaConsumerConfig {

	@KafkaListener(topics = "message", groupId = TweetConstant.GROUP_ID)
	public void consume(String message) {
		System.out.println("message received" + message);
		log.info(String.format("Message received -> %s", message));
	}
}