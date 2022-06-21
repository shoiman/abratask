package app.app.forum.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.app.forum.dto.MessageDto;
import app.app.forum.dto.exceptions.MessageNotFoundException;
import app.app.forum.model.Message;
import app.app.forum.repository.MessageRepository;

import org.modelmapper.ModelMapper;

@Service
public class MessageService implements MService {


	ModelMapper modelMapper;
	MessageRepository messageRepository;
	

	@Autowired
	public MessageService( ModelMapper modelMapper, MessageRepository messageRepository) {
		this.modelMapper = modelMapper;
		this.messageRepository = messageRepository;
	}

	@Transactional
	@Override
	public Message writeMessage(MessageDto messageDto, String sender, String receiver) {
		Message message = new Message(sender, receiver, messageDto.getMessage(), messageDto.getSubject());
		messageRepository.save(message);
		return message;
	}

	@Override
	public List<Message> getAllMessagesforUser(String user) {		
		return messageRepository.findAllBySenderOrReseiver(user, user);
	}

	@Override
	public List<Message> getAllUnreadMessagesforUser(String user) {
		return messageRepository.findAllBySenderOrReseiver(user,user)
				.stream()
				.filter(m -> m.getRead() == false)
				.collect(Collectors.toList());

	}

	@Override
	public Message readMessage(String user) {
		//Message message = messageRepository.findById(id).orElseThrow(() -> new MessageNotFoundExcieption());
		Message message = messageRepository.findFirstByReseiverOrderByDateDesc(user).orElseThrow(() -> new MessageNotFoundException()); 
		message.setRead(true);
		messageRepository.save(message);
		//return modelMapper.map(message, ShowMessageDto.class);
		return message;
	}

	@Transactional
	@Override
	public Message deleteMessage(String id) {
		Message message = messageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException());
		messageRepository.delete(message);
		return message;
	}

}
