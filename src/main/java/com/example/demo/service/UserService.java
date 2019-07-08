package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.untils.RecordNotFoundException;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserRepository repository;

	public User getUser(Integer id) {
		User user = repository.findById(id).get();
		if (user == null) {
			throw new RecordNotFoundException("User with id is" + id);
		}
		return user;
	}
}
