package za.co.iherridge0.rest.webservices.todorestfulwebservices.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UserAlreadyExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3066087432998525700L;

	public UserAlreadyExistException(String message) {
		super(message);
	}

}
