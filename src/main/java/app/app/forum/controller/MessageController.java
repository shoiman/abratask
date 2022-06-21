package app.app.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.app.forum.dto.MessageDto;
import app.app.forum.model.Message;
import app.app.forum.service.MessageService;



@RestController
@RequestMapping("/messagebox")
public class MessageController {
	
	MessageService messageService;

	@Autowired
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}
	
		
	@PostMapping("/add/{sender}/{receiver}")
	public Message addMessage(@RequestBody MessageDto messageDto,@PathVariable String sender,@PathVariable String receiver) {
		return messageService.writeMessage(messageDto, sender, receiver);
	}
	
	@GetMapping("/get/{user}")
	public Message getMessage(@PathVariable String user) {
		return messageService.readMessage(user);
	}
	
	@GetMapping("/getAllForUser/{user}")
	public List<Message> getAllMessagesByUser(@PathVariable String user){
		return messageService.getAllMessagesforUser(user);
	}
	
	@GetMapping("/getAllUnreadMessageForUser/{user}")
	public List<Message> getAllUnreadMessagesByUser(@PathVariable String user){
		return messageService.getAllUnreadMessagesforUser(user);
	}

	@DeleteMapping("delete/{id}")
	public Message deleteMessage(@PathVariable String id) {
		return messageService.deleteMessage(id);
	}
}
