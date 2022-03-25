/**
 * @author Manan Shah
 *
 */
package com.tweetapp.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.tweetapp.model.User;

@Repository
public interface UserRepo extends MongoRepository<User, Long> {
	@Query("{ emailId : ?0,password: ?1 }")
	Optional<User> findByemailIdAndPassword(String emailId, String password);

	@Query("{ emailId : ?0}")
	Optional<User> findByEmailIdName(String userName);

}
