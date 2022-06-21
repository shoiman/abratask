package app.app.accounting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import app.app.accounting.model.UserAccount;

public interface UserAccountRepository extends MongoRepository<UserAccount, String> {

}
