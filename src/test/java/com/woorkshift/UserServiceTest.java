package com.woorkshift;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.workshift.model.User;
import com.workshift.repository.UserRepository;
import com.workshift.services.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	void testCreateUser() {

		String userName = "John Doe";

		User mockUser = new User();
		mockUser.setUserId(1);
		mockUser.setName(userName);

		when(userRepository.findByName(userName)).thenReturn(Optional.empty());

		when(userRepository.save(any(User.class))).thenReturn(mockUser);

		User createdUser = userService.createUser(userName);

		assertNotNull(createdUser);
		assertEquals(1, createdUser.getUserId()); // Assuming ID assignment during saving
		assertEquals(userName, createdUser.getName());

		verify(userRepository, times(1)).findByName(userName);
		verify(userRepository, times(1)).save(any(User.class));
	}

	@Test
	void testCreateUserWithExistingName() {

		String existingUserName = "John Doe";
		User existingUser = new User();
		existingUser.setUserId(1);
		existingUser.setName(existingUserName);

		// Mocking the behavior of userRepository to return an existing user
		when(userRepository.findByName(existingUserName)).thenReturn(Optional.of(existingUser));

		assertThrows(RuntimeException.class, () -> userService.createUser(existingUserName));

		verify(userRepository, times(1)).findByName(existingUserName);
		verify(userRepository, never()).save(any(User.class));
	}

}
