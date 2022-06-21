package app.app.accounting.dto;

import lombok.Getter;
import lombok.NonNull;

@Getter
@NonNull
public class UserRegisterDto {
	String login;
	String password;

}
