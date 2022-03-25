/**
 * @author Manan Shah
 *
 */
package com.tweetapp.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "USER")
public class User {
	@Transient
	public static final String SEQUENCE_NAME = "users_sequence";
	@Id
	@Min(1)
	public int userId;

	@NotBlank(message = "firstName cannot be null")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Invalid firstName")
	public String firstName;

	@NotBlank(message = "lastName cannot be null")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Invalid LastName")
	public String lastName;

	@Pattern(regexp = "male|female", message = "Invalid Gender")
	@NotBlank(message = "gender cannot be null")
	public String gender;

	@Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "Date OF birth Should be in YYYY-MM-DD format")
	public String dob;

	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid EmailId")
	@NotBlank(message = "Enter Valid Email Id")
	public String emailId;

	@NotBlank(message = "password cannot be null")
//	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&(){}[]:;<>,.?/~_+-=|\\]).{8,32}$", message = "Password Should Atleast contain one lowerCase,one UpperCase, one Special Char, one digit and length should be greater than 8.")
	public String password;
}
