package app.app.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.app.forum.model.Message;
import app.app.forum.repository.MessageRepository;

@Service("customSecurity")
public class CustomWebSecurity {
	
	MessageRepository messageRepository;

	@Autowired
	public CustomWebSecurity(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}
	
	public boolean checkPostAuthority(String postId, String userName) {
		Message message = messageRepository.findById(postId).orElse(null);
		return ((message != null) && 
				(userName.equals(message.getReseiver()) || userName.equals(message.getSender())
						));
	}
	

}
