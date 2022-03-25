/**
 * @author Manan Shah
 *
 */
package com.tweetapp.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "TWEET")
@Component
public class Tweet {
	@Transient
	public static final String SEQUENCE_NAME = "tweet_sequence";
	@Id
	private int tweetId;
	private String userName;
	private String tweet;
	private Date created;
	private Map<String, Integer> likes;
	private Map<String, List<String>> replies;
}
