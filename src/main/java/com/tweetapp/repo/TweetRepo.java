/**
 * @author Manan Shah
 *
 */
package com.tweetapp.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.tweetapp.model.Tweet;

@Repository
public interface TweetRepo extends MongoRepository<Tweet, Integer> {

	@Query("{ userName : ?0}")
	List<Tweet> findByUserName(String userName);

}
