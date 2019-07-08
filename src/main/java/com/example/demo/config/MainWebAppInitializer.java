package com.example.demo.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;

public class MainWebAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		servletContext.getSessionCookieConfig().setHttpOnly(true);
		servletContext.getSessionCookieConfig().setSecure(true);
		
	}

}
