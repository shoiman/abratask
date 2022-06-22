package app.app.security.service;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthorizationConfiguration extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic();
		http.csrf().disable();
		http.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/account/register/**")
					.permitAll()
				.antMatchers(HttpMethod.GET,"/messagebox/get/{reseiver}")
					.access("#reseiver == authentication.name")
				.antMatchers(HttpMethod.POST, "/messagebox/add/{sender}/**")
					.access("#sender == authentication.name")
				.antMatchers(HttpMethod.GET, "messagebox/getAllForUser/{user}")
					.access("#user == authentication.name")
				.antMatchers(HttpMethod.GET, "/messagebox/getAllUnreadMessageForUser/{user}")
					.access("#user == authentication.name")				
				.antMatchers(HttpMethod.DELETE, "/messagebox/delete/{id}")
					.access("@customSecurity.checkPostAuthority(#id, authentication.name)")	
				.anyRequest()
					.authenticated();
	}
}
