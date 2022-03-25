package com.tweetapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import lombok.Generated;

@SpringBootApplication
@EnableMongoRepositories
@Generated
public class TweetAppServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TweetAppServiceApplication.class, args);
	}

}
