package app.app.accounting.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.app.accounting.dto.UserAccountResponseDto;
import app.app.accounting.dto.UserRegisterDto;
import app.app.accounting.dto.exceptions.UserExistsException;
import app.app.accounting.model.UserAccount;
import app.app.accounting.repository.UserAccountRepository;

@Service
public class UserAccountServiceImpl implements UserAccountService {
	
	UserAccountRepository repository;
	ModelMapper modelMapper;
	PasswordEncoder passwordEncoder;

	@Autowired
	public UserAccountServiceImpl(UserAccountRepository repository, ModelMapper modelMapper, 
			PasswordEncoder passwordEncoder
			) {
		this.repository = repository;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserAccountResponseDto addUser(UserRegisterDto userRegisterDto) {
		if (repository.existsById(userRegisterDto.getLogin())) {
			throw new UserExistsException(userRegisterDto.getLogin());
		}
		UserAccount userAccount = modelMapper.map(userRegisterDto, UserAccount.class);
		String password = passwordEncoder.encode(userRegisterDto.getPassword());
		userAccount.setPassword(password);
		repository.save(userAccount);
		return modelMapper.map(userAccount, UserAccountResponseDto.class);

	}

}
