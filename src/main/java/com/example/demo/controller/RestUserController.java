package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.untils.BaseResponse;

@RestController
public class RestUserController {
	@Autowired
	private UserRepository repository;
	@Autowired
	private UserService service;

	@PostMapping(value = "/add-user")
	public ResponseEntity<?> addUser(@Valid @RequestBody User user, BindingResult bindingResult) {
		List<String> errors = new ArrayList<String>();
		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			for (FieldError error : fieldErrors) {
				errors.add(error.getField() + " " + error.getDefaultMessage());

			}
			return new ResponseEntity<BaseResponse>(new BaseResponse("" + errors), HttpStatus.BAD_REQUEST);
		}
		repository.save(user);
		return new ResponseEntity<BaseResponse>(new BaseResponse("ok"), HttpStatus.OK);
	}

	@GetMapping(value = "/users/{id}")
	public ResponseEntity<User> getOne(@PathVariable("id") Integer id) {
		User user = service.getUser(id);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

}