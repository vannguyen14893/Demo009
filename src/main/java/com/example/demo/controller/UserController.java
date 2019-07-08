package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@RestController
public class UserController {
	@Autowired
	private UserRepository repository;
	@Autowired
	private RoleRepository roleRepository;

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "/user/{id}")
	public User findById(@PathVariable("id") Integer id) {
		User user = repository.getOne(id);
		System.out.println(user.getEmail());
		return user;
	}

	@PreAuthorize("hasRole('ADMIN') and hasPermission(#id,'Role', 'read')")
	@GetMapping(value = "/role/{id}")
	public Role findByRoleId(@PathVariable("id") Integer id) {
		Role role = roleRepository.getOne(id);
		return role;
	}
}
