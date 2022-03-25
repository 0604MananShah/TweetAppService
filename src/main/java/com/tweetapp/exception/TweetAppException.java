/**
 * @author Manan Shah
 *
 */
package com.tweetapp.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author 896953
 *
 */
@AllArgsConstructor
@Getter
public class TweetAppException extends RuntimeException {
	private static final long serialVersionUID = 1558149957272449535L;
	private int statusCode;
	private HttpStatus status;
	private String data;
}
