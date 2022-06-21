package app.app.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import app.app.accounting.model.UserAccount;
import app.app.accounting.repository.UserAccountRepository;
import app.app.forum.dto.exceptions.MessageNotFoundException;
import app.app.forum.model.Message;
import app.app.forum.repository.MessageRepository;

///////////////////////////////// yes
@Service
@Order(70)
public class DeletePostFilter implements Filter {
	
	UserAccountRepository repository;
	MessageRepository messageRepository;

	@Autowired
	public DeletePostFilter(UserAccountRepository repository, MessageRepository messageRepository) {
		this.repository = repository;
		this.messageRepository = messageRepository;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;		
		HttpServletResponse response = (HttpServletResponse) resp;

		if (checkEndPoints(request.getMethod(), request.getServletPath())) {
			UserAccount userAccount = repository.findById(request.getUserPrincipal().getName()).get();
			String[] arr = request.getServletPath().split("/");
			Message message = messageRepository.findById(arr[3]).orElseThrow(() -> new MessageNotFoundException((arr[3])));
			if(!(message.getSender().equals(userAccount.getLogin()) || 
					message.getReseiver().equals(userAccount.getLogin()) ) ) {
				response.sendError(403, "you cant do this");
				return;
			}			
		}
									
						
		chain.doFilter(request, response);

	}
	
	private boolean checkEndPoints(String method, String path) {
		return ("DELETE".equalsIgnoreCase(method) && path.matches("/messagebox/delete/\\w+"));
	}

}
