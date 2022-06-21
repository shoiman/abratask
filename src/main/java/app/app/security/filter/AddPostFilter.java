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

@Service
@Order(50)
public class AddPostFilter implements Filter {

	UserAccountRepository repository;

	@Autowired
	public AddPostFilter(UserAccountRepository repository) {
		this.repository = repository;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;		
		HttpServletResponse response = (HttpServletResponse) resp;
		
		if (checkEndPoints(request.getMethod(), request.getServletPath())) {
			UserAccount userAccount = repository.findById(request.getUserPrincipal().getName()).get();
			String[] arr = request.getServletPath().split("/");
			if (!arr[3].equals(userAccount.getLogin())) {
				response.sendError(403, "Log in before this");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private boolean checkEndPoints(String method, String path) {
		if ("POST".equalsIgnoreCase(method) && path.matches("/messagebox/add/\\w+/\\w+")) {
			return true;
		}
		return false;
	}

}
