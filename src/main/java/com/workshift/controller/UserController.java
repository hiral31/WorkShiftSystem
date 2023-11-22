package com.workshift.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workshift.model.User;
import com.workshift.services.UserService;
import com.workshift.vo.UserVo;

@RestController
@RequestMapping("/api/workshift")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public ResponseEntity<List<User>> getUser() {
		List<User> users = new ArrayList<User>();

		users = userService.getUsers();

		if (users.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@PostMapping("/user")
	public ResponseEntity<User> createUser(@RequestBody UserVo userVo) {

		try {

			User user = userService.createUser(userVo.getName());

			return new ResponseEntity<>(user, HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}
	}

}
