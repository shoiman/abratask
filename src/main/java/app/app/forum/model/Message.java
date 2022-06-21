package app.app.forum.model;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.annotation.JsonFormat;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
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
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
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
