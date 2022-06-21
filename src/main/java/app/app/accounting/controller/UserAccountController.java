package app.app.accounting.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.app.accounting.dto.UserAccountResponseDto;
import app.app.accounting.dto.UserRegisterDto;
import app.app.accounting.service.UserAccountService;

@RestController
@RequestMapping("/account")
public class UserAccountController {

	UserAccountService accountService;

	@Autowired
	public UserAccountController(UserAccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping("/register")
	public UserAccountResponseDto register(@RequestBody UserRegisterDto userRegisterDto) {
		return accountService.addUser(userRegisterDto);
	}
}
