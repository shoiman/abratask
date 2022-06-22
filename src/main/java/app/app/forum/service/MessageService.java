package app.app.forum.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.app.forum.dto.MessageDto;
import app.app.forum.dto.MessageRequestDto;
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
	public MessageRequestDto writeMessage(MessageDto messageDto, String sender, String receiver) {
		Message message = new Message(sender, receiver, messageDto.getMessage(), messageDto.getSubject());
		messageRepository.save(message);
		return modelMapper.map(message, MessageRequestDto.class);
	}

	@Override
	public List<MessageRequestDto> getAllMessagesforUser(String user) {		
		return messageRepository.findAllBySenderIgnoreCaseOrReseiverIgnoreCase(user, user)
				.stream()
				.map(m -> modelMapper.map(m, MessageRequestDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<MessageRequestDto> getAllUnreadMessagesforUser(String user) {
		return messageRepository.findAllBySenderIgnoreCaseOrReseiverIgnoreCase(user,user)
				.stream()
				.filter(m -> m.getRead() == false)
				.map(m -> modelMapper.map(m, MessageRequestDto.class))
				.collect(Collectors.toList());

	}

	@Override
	public Message readMessage(String user) {
		Message message = messageRepository.findFirstByReseiverIgnoreCaseOrderByDateDesc(user).orElseThrow(() -> new MessageNotFoundException()); 
		message.setRead(true);
		messageRepository.save(message);
		return message;
	}

	@Transactional
	@Override
	public MessageRequestDto deleteMessage(String id) {
		Message message = messageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException());
		messageRepository.delete(message);
		return modelMapper.map(message, MessageRequestDto.class);
	}

}
