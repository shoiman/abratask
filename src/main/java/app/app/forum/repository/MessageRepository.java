package app.app.forum.repository;


import java.util.List;
import java.util.Optional;


import org.springframework.data.mongodb.repository.MongoRepository;

import app.app.forum.model.Message;

public interface MessageRepository extends MongoRepository<Message, String> {


List<Message> findAllBySenderOrReseiver(String sender, String reseiver);
	
	List<Message> findAllByReseiver(String reseiver);
	
	Optional<Message> findFirstByReseiverOrderByDateDesc(String resiever);
}
