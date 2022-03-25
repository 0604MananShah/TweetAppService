/**
 * @author Manan Shah
 *
 */
package com.tweetapp.unit.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tweetapp.exception.TweetAppException;
import com.tweetapp.model.Tweet;
import com.tweetapp.model.User;
import com.tweetapp.repo.TweetRepo;
import com.tweetapp.repo.UserRepo;
import com.tweetapp.request.TweetRequest;
import com.tweetapp.service.TweetService;
import com.tweetapp.util.Envelope;
import com.tweetapp.util.TweetConstant;

/**
 * @author 896953
 *
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TweetServiceTest {

	@InjectMocks
	TweetService tweetService;

	@Mock
	UserRepo userRepository;

	@Mock
	TweetRepo tweetRepository;

	@Mock
	MongoOperations mongoperation;

	@Test
	void postTweet() {
		TweetRequest tweetReq = getTweetRequest();
		Mockito.when(tweetRepository.findById(1)).thenReturn(Optional.empty());
		Mockito.when(userRepository.findByEmailIdName("manan.shah403@gmail.com")).thenReturn(Optional.of(new User()));
		ResponseEntity<Envelope<String>> postTweet = tweetService.postTweet("manan.shah403@gmail.com", tweetReq);
		Assertions.assertEquals(ResponseEntity.ok(new Envelope<>(HttpStatus.OK.value(), HttpStatus.OK, "Saved")),
				postTweet);
	}

	@Test
	void postTweetUserNameException() {
		TweetRequest tweetReq = getTweetRequest();
		Mockito.when(tweetRepository.findById(1)).thenReturn(Optional.of(new Tweet()));
		Mockito.when(userRepository.findByEmailIdName("manan.shah403@gmail.com")).thenReturn(Optional.empty());
		TweetAppException exceptionResponse = Assertions.assertThrows(TweetAppException.class,
				() -> tweetService.postTweet("manan.shah403@gmail.com", tweetReq));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exceptionResponse.getStatus());
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), exceptionResponse.getStatusCode());
		Assertions.assertEquals("userName not Present", exceptionResponse.getData());
	}

	@Test
	void postTweetNoTweetFoundException() {
		TweetRequest tweetReq = getTweetRequest();
		Mockito.when(tweetRepository.findById(1)).thenReturn(Optional.of(new Tweet()));
		Mockito.when(userRepository.findByEmailIdName("manan.shah403@gmail.com")).thenReturn(Optional.of(new User()));
		TweetAppException exceptionResponse = Assertions.assertThrows(TweetAppException.class,
				() -> tweetService.postTweet("manan.shah403@gmail.com", tweetReq));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exceptionResponse.getStatus());
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), exceptionResponse.getStatusCode());
		Assertions.assertEquals("Tweet Already Present", exceptionResponse.getData());
	}

	@Test
	void getAllTweets() {
		List<Tweet> tweetList = Arrays.asList(new Tweet(1, "Hello", "manan.shah403@gmail.com", null, null, null),
				new Tweet(2, "manan@gmail.com", "Shah", null, null, null));
		Mockito.when(tweetRepository.findAll()).thenReturn(tweetList);
		ResponseEntity<Envelope<List<Tweet>>> allTweet = tweetService.getAllTweet();
		Assertions.assertEquals(ResponseEntity.ok(new Envelope<>(HttpStatus.OK.value(), HttpStatus.OK, tweetList)),
				allTweet);
	}

	@Test
	void getAllTweetsThrowsException() {
		TweetAppException exceptionResponse = Assertions.assertThrows(TweetAppException.class,
				() -> tweetService.getAllTweet());
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exceptionResponse.getStatus());
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), exceptionResponse.getStatusCode());
		Assertions.assertEquals("No Tweets Found", exceptionResponse.getData());
	}

	@Test
	void getAllUserTweet() {
		Mockito.when(tweetRepository.findByUserName("manan.shah403@gmail.com"))
				.thenReturn(Arrays.asList(new Tweet(1, "manan.shah403@gmail.com", "Hello", null, null, null),
						new Tweet(1, "manan.shah403@gmail.com", "Hi", null, null, null)));
		ResponseEntity<Envelope<List<Tweet>>> allUserTweet = tweetService.getAllUserTweet("manan.shah403@gmail.com");
		Assertions
				.assertEquals(
						ResponseEntity.ok(new Envelope<>(HttpStatus.OK.value(), HttpStatus.OK,
								Arrays.asList(new Tweet(1, "manan.shah403@gmail.com", "Hello", null, null, null),
										new Tweet(1, "manan.shah403@gmail.com", "Hi", null, null, null)))),
						allUserTweet);
	}

	@Test
	void getAllUserTweetThrowsException() {
		TweetAppException exceptionResponse = Assertions.assertThrows(TweetAppException.class,
				() -> tweetService.getAllUserTweet("manan.shah403@gmail.com"));
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exceptionResponse.getStatus());
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), exceptionResponse.getStatusCode());
		Assertions.assertEquals("No Tweets Found", exceptionResponse.getData());
	}

	@Test
	void updateTweet() {
		Mockito.when(tweetRepository.findById(1)).thenReturn(Optional.of(new Tweet()));
		Mockito.when(userRepository.findByEmailIdName("manan.shah403@gmail.com")).thenReturn(Optional.of(new User()));
		Query query = new Query();
		query.addCriteria(Criteria.where(TweetConstant.TWEET_ID).is(1));
		Update update = new Update();
		update.set(TweetConstant.TWEET, "Hello All");
		Mockito.when(mongoperation.findAndModify(query, update, Tweet.class)).thenReturn(new Tweet());
		ResponseEntity<Envelope<String>> updateTweet = tweetService.updateTweet("manan.shah403@gmail.com", 1,
				getTweetRequest());
		Assertions.assertEquals(
				ResponseEntity.ok(new Envelope<>(HttpStatus.OK.value(), HttpStatus.OK, "Tweet Updated")), updateTweet);
	}

	@Test
	void deleteTweet() {
		Mockito.when(tweetRepository.findById(1)).thenReturn(Optional.of(new Tweet()));
		Mockito.when(userRepository.findByEmailIdName("manan.shah403@gmail.com")).thenReturn(Optional.of(new User()));
		ResponseEntity<Envelope<String>> deleteTweet = tweetService.deleteTweet("manan.shah403@gmail.com", 1);
		Assertions.assertEquals(
				ResponseEntity.ok(new Envelope<>(HttpStatus.OK.value(), HttpStatus.OK, "Tweet Deleted")), deleteTweet);
	}

	@Test
	void replyTweet() {
		Mockito.when(tweetRepository.findById(1)).thenReturn(Optional.of(new Tweet()));
		Mockito.when(userRepository.findByEmailIdName("manan.shah403@gmail.com")).thenReturn(Optional.of(new User()));
		Map<String, List<String>> newReplyList = new HashMap<>();
		newReplyList.put("manan.shah403@gmail.com", Arrays.asList("Hello manan"));
		Query query = new Query();
		query.addCriteria(Criteria.where("tweetId").is(1));
		Update update = new Update();
		update.set("replies", newReplyList);
		Mockito.when(mongoperation.findAndModify(query, update, Tweet.class)).thenReturn(new Tweet());
		ResponseEntity<Envelope<String>> replyTweet = tweetService.replyTweet("manan.shah403@gmail.com", 1,
				"Hello manan");
		Assertions.assertEquals(
				ResponseEntity.ok(new Envelope<>(HttpStatus.OK.value(), HttpStatus.OK, "Replied to tweet")),
				replyTweet);
	}

	@Test
	void replyOldTweet() {
		Map<String, List<String>> oldTweet = new HashMap<>();
		oldTweet.put("manan.shah403@gmail.com", Arrays.asList("Hi"));
		Mockito.when(tweetRepository.findById(1)).thenReturn(Optional.of(
				new Tweet(1, "manan.shah403@gmail.com", "1234", new Date(System.currentTimeMillis()), null, oldTweet)));
		Mockito.when(userRepository.findByEmailIdName("manan.shah403@gmail.com")).thenReturn(Optional.of(new User()));
		Map<String, List<String>> newReplyList = new HashMap<>();
		newReplyList.put("manan.shah403@gmail.com", Arrays.asList("Hi", "Hello manan"));
		Query query = new Query();
		query.addCriteria(Criteria.where("tweetId").is(1));
		Update update = new Update();
		update.set("replies", newReplyList);
		Mockito.when(mongoperation.findAndModify(query, update, Tweet.class)).thenReturn(new Tweet());
		ResponseEntity<Envelope<String>> replyTweet = tweetService.replyTweet("manan.shah403@gmail.com", 1,
				"Hello manan");
		Assertions.assertEquals(
				ResponseEntity.ok(new Envelope<>(HttpStatus.OK.value(), HttpStatus.OK, "Replied to tweet")),
				replyTweet);
	}

	@Test
	void replyOldTweetWithOtherUserName() {
		Map<String, List<String>> oldTweet = new HashMap<>();
		oldTweet.put("manan.shah403@gmail.com", Arrays.asList("Hi"));
		Mockito.when(tweetRepository.findById(1)).thenReturn(Optional
				.of(new Tweet(1, "hah403@gmail.com", "1234", new Date(System.currentTimeMillis()), null, oldTweet)));
		Mockito.when(userRepository.findByEmailIdName("shah403@gmail.com")).thenReturn(Optional.of(new User()));
		Map<String, List<String>> newReplyList = new HashMap<>();
		newReplyList.put("shah403@gmail.com", Arrays.asList("Hello manan"));
		newReplyList.putAll(oldTweet);
		Query query = new Query();
		query.addCriteria(Criteria.where("tweetId").is(1));
		Update update = new Update();
		update.set("replies", newReplyList);
		Mockito.when(mongoperation.findAndModify(query, update, Tweet.class)).thenReturn(new Tweet());
		ResponseEntity<Envelope<String>> replyTweet = tweetService.replyTweet("shah403@gmail.com", 1, "Hello manan");
		Assertions.assertEquals(
				ResponseEntity.ok(new Envelope<>(HttpStatus.OK.value(), HttpStatus.OK, "Replied to tweet")),
				replyTweet);
	}

	@Test
	void replyTweetException() {
		Mockito.when(tweetRepository.findById(1)).thenReturn(Optional.of(new Tweet()));
		Mockito.when(userRepository.findByEmailIdName("manan.shah403@gmail.com")).thenReturn(Optional.of(new User()));
		TweetAppException exceptionResponse = Assertions.assertThrows(TweetAppException.class,
				() -> tweetService.replyTweet("manan.shah403@gmail.com", 1, "Hello manan"));
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exceptionResponse.getStatus());
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), exceptionResponse.getStatusCode());
		Assertions.assertEquals("Error While replying", exceptionResponse.getData());

	}

	@Test
	void likeTweetException() {
		Mockito.when(tweetRepository.findById(1)).thenReturn(Optional.of(new Tweet()));
		Mockito.when(userRepository.findByEmailIdName("manan.shah403@gmail.com")).thenReturn(Optional.of(new User()));
		TweetAppException exceptionResponse = Assertions.assertThrows(TweetAppException.class,
				() -> tweetService.likeTweet("manan.shah403@gmail.com", 1));
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exceptionResponse.getStatus());
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), exceptionResponse.getStatusCode());
		Assertions.assertEquals("Error While Liking", exceptionResponse.getData());
	}

	@Test
	void likeTweet() {
		Mockito.when(tweetRepository.findById(1)).thenReturn(Optional.of(new Tweet()));
		Mockito.when(userRepository.findByEmailIdName("manan.shah403@gmail.com")).thenReturn(Optional.of(new User()));
		Map<String, Integer> updatedLikesMap = new HashMap<>();
		updatedLikesMap.put("manan.shah403@gmail.com", 1);
		Query query = new Query();
		query.addCriteria(Criteria.where("tweetId").is(1));
		Update update = new Update();
		update.set("likes", updatedLikesMap);
		Mockito.when(mongoperation.findAndModify(query, update, Tweet.class)).thenReturn(new Tweet());
		ResponseEntity<Envelope<String>> replyTweet = tweetService.likeTweet("manan.shah403@gmail.com", 1);
		Assertions.assertEquals(ResponseEntity.ok(new Envelope<>(HttpStatus.OK.value(), HttpStatus.OK, "Liked tweet")),
				replyTweet);
	}

	@Test
	void likeMultipleTweet() {
		Map<String, Integer> oldLikesMap = new HashMap<>();
		oldLikesMap.put("manan.shah403@gmail.com", 1);
		Mockito.when(tweetRepository.findById(1)).thenReturn(Optional
				.of(new Tweet(1, "hah403@gmail.com", "1234", new Date(System.currentTimeMillis()), oldLikesMap, null)));
		Mockito.when(userRepository.findByEmailIdName("manan.shah403@gmail.com")).thenReturn(Optional.of(new User()));
		Map<String, Integer> updatedLikesMap = new HashMap<>();
		updatedLikesMap.put("manan.shah403@gmail.com", 1);
		Query query = new Query();
		query.addCriteria(Criteria.where("tweetId").is(1));
		Update update = new Update();
		update.set("likes", updatedLikesMap);
		Mockito.when(mongoperation.findAndModify(query, update, Tweet.class)).thenReturn(new Tweet());
		ResponseEntity<Envelope<String>> replyTweet = tweetService.likeTweet("manan.shah403@gmail.com", 1);
		Assertions.assertEquals(ResponseEntity.ok(new Envelope<>(HttpStatus.OK.value(), HttpStatus.OK, "Liked tweet")),
				replyTweet);
	}

	private TweetRequest getTweetRequest() {
		TweetRequest tweetReq = new TweetRequest();
		tweetReq.setUserName("manan.shah403@gmail.com");
		tweetReq.setTweetId(1);
		tweetReq.setTweet("Hello All");
		tweetReq.setCreated(new Date(System.currentTimeMillis()));
		return tweetReq;
	}

}
