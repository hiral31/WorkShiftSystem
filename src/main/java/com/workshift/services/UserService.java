package com.workshift.services;

import java.util.List;

import com.workshift.model.User;

public interface UserService {
	
	public User createUser(String name);
	
	public List<User> getUsers();

}
