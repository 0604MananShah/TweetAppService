package com.tweetapp.configuration;

import org.springframework.kafka.annotation.KafkaListener;

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