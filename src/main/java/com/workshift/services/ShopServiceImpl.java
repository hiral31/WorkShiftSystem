package com.workshift.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.workshift.model.Shop;
import com.workshift.model.User;
import com.workshift.repository.ShopRepository;
import com.workshift.repository.UserRepository;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ShopRepository shopRepository;

	public Shop createShop(String name) {

		// Validate if the name is not null or empty
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Shop name cannot be null or empty");
		}

		// Check if the shop already exists

		Optional<Shop> existingShop = shopRepository.findByName(name);

		if (existingShop.isPresent()) {
			throw new RuntimeException("Shop with the same name already exists");
		}

		// Validate if the name meets certain criteria (e.g., minimum length)
		if (name.length() < 3) {
			throw new IllegalArgumentException("User name must have at least 3 characters");
		}

		Shop shop = new Shop();

		shop.setName(name);

		return shopRepository.save(shop);
	}

	public void addUserToShop(int userId, int shopId) {

		// Retrieve user and shop
		User user = userRepository.findById(userId);

		Shop shop = shopRepository.findById(shopId);
		
		if(user == null) {
			throw new RuntimeException("User is not available");
		}
		if(shop == null) {
			throw new RuntimeException("Shop is not available");
		}

		// Check if the user is already associated with the shop
		if (user.getShops().contains(shop)) {
			
			throw new RuntimeException("User is already associated with the shop");
		}

		// Add the shop to the user's list of associated shops
		user.getShops().add(shop);

		// Add the user to the shop's list of associated users
		shop.getUsers().add(user);

		// Save changes to the database
		userRepository.save(user);

		shopRepository.save(shop);

	}

	public List<Shop> getShops() {

		return shopRepository.findAll();
	}

}
