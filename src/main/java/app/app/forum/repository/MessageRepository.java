package app.app.forum.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import app.app.forum.model.Message;

public interface MessageRepository extends MongoRepository<Message, String> {

	List<Message> findAllBySenderIgnoreCaseOrReseiverIgnoreCase(String sender, String reseiver);

	List<Message> findAllByReseiverIgnoreCase(String reseiver);

	Optional<Message> findFirstByReseiverIgnoreCaseOrderByDateDesc(String resiever);
}
