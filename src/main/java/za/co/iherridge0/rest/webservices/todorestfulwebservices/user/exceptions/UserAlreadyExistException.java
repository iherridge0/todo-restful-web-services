package za.co.iherridge0.rest.webservices.todorestfulwebservices.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserAlreadyExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 982357509535486169L;

	public UserAlreadyExistException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
