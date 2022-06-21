package app.app.forum.service;

import java.util.List;

import app.app.forum.dto.MessageDto;
import app.app.forum.model.Message;





public interface MService {
//	- Write message
//	- Get all messages for a specific user
//	- Get all unread messages for a specific user
//	- Read message (return one message)
//	- Delete message (as owner or as receiver)
	
	Message writeMessage(MessageDto messageDto, String sender, String receiver);
	
	List<Message> getAllMessagesforUser(String user);
	
	List<Message> getAllUnreadMessagesforUser(String user);
	
	Message readMessage(String user);
	
	Message deleteMessage(String id);
	

}
