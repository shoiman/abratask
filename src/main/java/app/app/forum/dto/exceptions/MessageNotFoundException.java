package app.app.forum.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MessageNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6952550028444204207L;

	public MessageNotFoundException(String id) {
		super("Message with id = " + id + " not found");

	}

}
