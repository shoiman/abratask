package app.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import app.app.accounting.repository.UserAccountRepository;

@SpringBootApplication
public class ForumServiceApplication {

	@Autowired
	UserAccountRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(ForumServiceApplication.class, args);
	}



}
