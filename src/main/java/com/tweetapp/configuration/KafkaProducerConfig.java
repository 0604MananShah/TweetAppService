package com.tweetapp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.tweetapp.util.TweetConstant;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaProducerConfig {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String message) {
		log.info(String.format("Message Received-> %s", message));
		this.kafkaTemplate.send(TweetConstant.TOPIC_NAME, message);
	}

}