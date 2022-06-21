package app.app.security.filter;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import app.app.accounting.model.UserAccount;
import app.app.accounting.repository.UserAccountRepository;

///////////////////////////////// yes
@Service
@Order(10)
public class AuthenticationFilter implements Filter {

	UserAccountRepository repository;

	@Autowired
	public AuthenticationFilter(UserAccountRepository repository) {
		this.repository = repository;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		if (checkEndPoints(request.getMethod(), request.getServletPath())) {
			String token = request.getHeader("Authorization");
			if (token == null) {
				response.sendError(401, "Header Authorization not found");
				return;
			}
			String[] credentials = getCredentials(token);
			UserAccount userAccount = repository.findById(credentials[0]).orElse(null);
			if (userAccount == null) {
				response.sendError(401, "User not found");
				return;
			}
			if (!BCrypt.checkpw(credentials[1], userAccount.getPassword())) {
				response.sendError(401, "User or password not valid");
				return;
			}
			request = new WrapperRequest(request, userAccount.getLogin());
		}
		chain.doFilter(request, response);
	}
	
	private boolean checkEndPoints(String method, String path) {
		return !( ("POST".equalsIgnoreCase(method) && path.matches("/account/register"))||
				("GET".equalsIgnoreCase(method) && path.matches("/messagebox/get/\\w+")) || 
				("GET".equalsIgnoreCase(method) && path.matches("/messagebox/getAllUnreadMessageForUser/\\w+")) ||
				("GET".equalsIgnoreCase(method) && path.matches("/messagebox/getAllForUser/\\w+")));
	}

	private String[] getCredentials(String token) {
		token = token.split(" ")[1];
		String decode = new String(Base64.getDecoder().decode(token));
		String[] credentials = decode.split(":");
		return credentials;
	}
	
	private class WrapperRequest extends HttpServletRequestWrapper{
		String login;

		public WrapperRequest(HttpServletRequest request, String login) {
			super(request);
			this.login = login;
		}
		
		@Override
		public Principal getUserPrincipal() {
			return () -> login;
		}
		
	}

}