package app.app.forum.model;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
@ToString
@Document(collection = "messages")
public class Message {

	@Id
	String id;
	String sender;
	String reseiver;
	String message;
	String subject;
	LocalDateTime date;
	Boolean read;

	public Message(String sender, String receiver, String message, String subject) {
		super();
		this.sender = sender;
		this.reseiver = receiver;
		this.message = message;
		this.subject = subject;
		this.date = LocalDateTime.now();
		read = false;
	}

}
