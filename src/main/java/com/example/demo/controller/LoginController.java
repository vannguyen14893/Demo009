package com.example.demo.controller;

import java.nio.channels.SeekableByteChannel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.LoginService;

@Controller
public class LoginController {
	@Autowired
	LoginService loginService;

	public String updatePage(HttpServletRequest request, ModelMap map) {
		if (isRememberMeAuthenticated()) {
			setRememberMeTargetUrlToSession(request);
			map.addAttribute("loginUpdate", true);
			return "login";
		} else {
			return "update";
		}
	}

	@GetMapping(value = "/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "error2", required = false) String error2,
			@RequestParam(value = "max_session", required = false) String max_session,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request, ModelMap map) {
		if (error != null) {
			map.addAttribute("message", "Username or password not recognised - please try again.");
			String targetUrl = getRememberMeTargetUrlFromSession(request);
			if (StringUtils.hasText(targetUrl)) {
				map.addAttribute("targetUrl", targetUrl);
				// map.addAttribute("loginUpdate", true);
			}

		}
		if (error2 != null) {
			map.addAttribute("message2", "222222.");
		}
		if (max_session != null) {
			map.addAttribute("message3", "333333");
		}
		if (logout != null) {
			map.addAttribute("msg", "You've been logged out successfully.");
		}
		return "login";
	}

	@GetMapping(value = "/home")
	public String home(ModelMap map) {
		String username = loginService.getPrincipal();
		map.addAttribute("name", username);
		return "home";
	}

	@GetMapping(value = "/admin/info")
	public String info() {
		return "info";
	}

	@GetMapping(value = "/db/info")
	public String info2() {
		return "info2";
	}

	private boolean isRememberMeAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return false;
		}
		return RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
	}

	private void setRememberMeTargetUrlToSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.setAttribute("targetUrl", "/admin/update");
		}
	}

	private String getRememberMeTargetUrlFromSession(HttpServletRequest request) {
		String targetUrl = "";
		HttpSession session = request.getSession(false);
		if (session != null) {
			targetUrl = session.getAttribute("targetUrl") == null ? "" : session.getAttribute("targetUrl").toString();
		}
		return targetUrl;
	}
}
