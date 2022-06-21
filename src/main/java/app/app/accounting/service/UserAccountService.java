package app.app.accounting.service;

import app.app.accounting.dto.UserAccountResponseDto;
import app.app.accounting.dto.UserRegisterDto;

public interface UserAccountService {
	
	UserAccountResponseDto addUser(UserRegisterDto userRegisterDto);
}
