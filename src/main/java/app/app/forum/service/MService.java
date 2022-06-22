package app.app.forum.service;

import java.util.List;

import app.app.forum.dto.MessageDto;
import app.app.forum.dto.MessageRequestDto;
import app.app.forum.model.Message;


public interface MService {

	
	MessageRequestDto writeMessage(MessageDto messageDto, String sender, String receiver);
	
	List<MessageRequestDto> getAllMessagesforUser(String user);
	
	List<MessageRequestDto> getAllUnreadMessagesforUser(String user);
	
	Message readMessage(String user);
	
	MessageRequestDto deleteMessage(String id);
	

}
