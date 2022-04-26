package com.tweetapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Generated;

@SpringBootApplication
@EnableMongoRepositories
@Generated
@EnableAspectJAutoProxy
@CrossOrigin(origins = "http://localhost:4200")
public class TweetAppServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TweetAppServiceApplication.class, args);
	}

	@Bean
	public TimedAspect timedAspect(MeterRegistry registry) {
		return new TimedAspect(registry);
	}

}
