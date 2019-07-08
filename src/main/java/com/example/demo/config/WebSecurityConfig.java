package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService service;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private PersistentTokenRepository persistentTokenRepository;
	@Autowired
	DataSource dataSource;

	@Bean
	public UserDetailsService userDeatilService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withDefaultPasswordEncoder().username("user").password("123").roles("USER").build());
		manager.createUser(
				User.withDefaultPasswordEncoder().username("admin").password("123").roles("ADMIN", "DBA").build());
		return manager;

	}

	@Bean
	public BCryptPasswordEncoder passwordEnconder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}

	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
		SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
		auth.setTargetUrlParameter("targetUrl");
		return auth;
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

//	@Bean
//	public SpringSessionRememberMeServices rememberMeServices() {
//		SpringSessionRememberMeServices rememberMeServices =
//				new SpringSessionRememberMeServices();
//		rememberMeServices.setAlwaysRemember(true);
//		return rememberMeServices;
//	}
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(encoder);
	}

	protected MethodSecurityExpressionHandler createExpressionHandler() {
		DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
		expressionHandler.setPermissionEvaluator(new CustomPermissionEvaluator());
		return expressionHandler;
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
				.permitAll().antMatchers("/resources/**", "/home", "/login", "/logout","/add-user","/users/**").permitAll()
				.antMatchers("/admin/**").hasRole("DBA").antMatchers("/db/**")
				.access("hasRole('ADMIN') and hasRole('DBA')").anyRequest().authenticated().and().formLogin()
				.loginPage("/login").defaultSuccessUrl("/home").failureUrl("/login?error").permitAll().and().logout()
				.logoutUrl("/logout").logoutSuccessUrl("/login?logout").invalidateHttpSession(true)
				.deleteCookies("JSESSIONID").and().rememberMe().key("uniqueAndSecret")
				.rememberMeParameter("remember-new").tokenValiditySeconds(86400)
				.tokenRepository(persistentTokenRepository).userDetailsService(service);
		http.sessionManagement().sessionFixation().newSession()
	    .invalidSessionUrl("/login?timeout").maximumSessions(1).expiredUrl("/login?max_session").maxSessionsPreventsLogin(true);
		super.configure(http);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/assets/**", "/css/**", "/js/**", "/swf/**",
				"/images/**", "/fonts/**", "/font-awesome/**");
	}
}
