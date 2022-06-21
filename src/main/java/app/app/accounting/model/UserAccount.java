package app.app.accounting.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = { "login" })
@NoArgsConstructor
@Document(collection = "users")
public class UserAccount {
	@Id
	String login;
	String password;

	
	public UserAccount(String login, String password) {
		this.login = login;
		this.password = password;

	}



}
