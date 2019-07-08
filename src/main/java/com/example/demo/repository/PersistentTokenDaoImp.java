package com.example.demo.repository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.PersistentLogins;

@Service
@Transactional
public class PersistentTokenDaoImp implements PersistentTokenRepository {

	@Autowired
	private PersistentTokenDao repository;

	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		PersistentLogins logins = new PersistentLogins();
		logins.setUsername(token.getUsername());
		logins.setSeries(token.getSeries());
		logins.setToken(token.getTokenValue());
		logins.setLastUsed(token.getDate());
		repository.save(logins);
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String series) {
		PersistentLogins logins = repository.findBySeries(series);
		if (logins != null) {
			return new PersistentRememberMeToken(logins.getUsername(), logins.getSeries(), logins.getToken(),
					logins.getLastUsed());
		}
		return null;
	}

	@Override
	public void removeUserTokens(String username) {
		PersistentLogins logins = repository.findByUsername(username);
		if (logins != null) {
			repository.delete(logins);
		}
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		PersistentLogins logins = repository.findBySeries(series);
		logins.setSeries(series);
		logins.setLastUsed(lastUsed);
		logins.setToken(tokenValue);
		repository.save(logins);
	}
}
