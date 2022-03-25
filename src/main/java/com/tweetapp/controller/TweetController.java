/**
 * @author Manan Shah
 *
 */
package com.tweetapp.controller;

import static com.tweetapp.util.TweetConstant.ROOT_URL;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.model.Tweet;
import com.tweetapp.request.TweetRequest;
import com.tweetapp.service.TweetService;
import com.tweetapp.util.Envelope;

import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

@RequestMapping(value = ROOT_URL)
@RestController
@Slf4j
@Generated
public class TweetController {

	@Autowired
	TweetService tweetService;

	@PostMapping("/add/{userName}")
	public ResponseEntity<Envelope<String>> postTweet(@PathVariable("userName") String userName,
			@RequestBody @Valid TweetRequest tweet) {
		log.info("In {} UserName {} ", "postTweet", userName);
		return tweetService.postTweet(userName, tweet);
	}

	@GetMapping("/all")
	public ResponseEntity<Envelope<List<Tweet>>> getAllTweet() {
		log.info("In {}", "getAllTweet");
		return tweetService.getAllTweet();
	}

	@GetMapping("/{userName}")
	public ResponseEntity<Envelope<List<Tweet>>> getAllUserTweet(@PathVariable String userName) {
		log.info("In {} UserName {} ", "getAllUserTweet", userName);
		return tweetService.getAllUserTweet(userName);
	}

	@PutMapping("/{userName}/update/{tweetId}")
	public ResponseEntity<Envelope<String>> updateTweet(@PathVariable("userName") String userName,
			@PathVariable("tweetId") int tweetId, @RequestBody @Valid TweetRequest tweetRequest) {
		log.info("In {} UserName {} ", "updateTweet", userName);
		return tweetService.updateTweet(userName, tweetId, tweetRequest);
	}

	@DeleteMapping("/{userName}/delete/{tweetId}")
	public ResponseEntity<Envelope<String>> deleteTweet(@PathVariable("userName") String userName,
			@PathVariable("tweetId") int tweetId) {
		log.info("In {} UserName {} ", "deleteTweet", userName);
		return tweetService.deleteTweet(userName, tweetId);
	}

	@PutMapping("/{userName}/like/{tweetId}")
	public ResponseEntity<Envelope<String>> likeTweet(@PathVariable("userName") String userName,
			@PathVariable("tweetId") int tweetId) {
		log.info("In {} UserName {} ", "likeTweet", userName);
		return tweetService.likeTweet(userName, tweetId);
	}

	@PostMapping("/{userName}/reply/{tweetId}/{reply}")
	public ResponseEntity<Envelope<String>> replyTweet(@PathVariable("userName") String userName,
			@PathVariable("tweetId") int tweetId, @PathVariable("reply") String reply) {
		log.info("In {} UserName {} ", "replyTweet", userName);
		return tweetService.replyTweet(userName, tweetId, reply);
	}

}
