package com.workshift.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.workshift.model.User;
import com.workshift.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
    private UserRepository userRepository;
	
	 public User createUser(String name) {
	       
	    	// Validate if the name is not null or empty
	        if (name == null || name.trim().isEmpty()) {
	            throw new IllegalArgumentException("User name cannot be null or empty");
	        }
	    	
	        // Check if the user already exists
	        Optional<User> existingUser = userRepository.findByName(name);
	    	
	        if (existingUser.isPresent()) {
	            
	        	throw new RuntimeException("User with the same name already exists");
	        }
	        
	        // Validate if the name meets certain criteria (e.g., minimum length)
	        if (name.length() < 3) {
	            throw new IllegalArgumentException("User name must have at least 3 characters");
	        }

	        User user = new User();
	        
	        user.setName(name);
	        
	        return userRepository.save(user);
	    }
	 
	 public List<User> getUsers(){
	    	
	    	return userRepository.findAll();
	    
	    }

}
