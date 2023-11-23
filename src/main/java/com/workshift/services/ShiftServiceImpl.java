package com.workshift.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.workshift.model.Shift;
import com.workshift.model.Shop;
import com.workshift.model.User;
import com.workshift.repository.ShiftRepository;
import com.workshift.repository.ShopRepository;
import com.workshift.repository.UserRepository;

@Service
public class ShiftServiceImpl implements ShiftService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ShopRepository shopRepository;

	@Autowired
	private ShiftRepository shiftRepository;

	public Shift createShift(int shopId, LocalDateTime startTime, LocalDateTime endTime) {

		Shop shop = shopRepository.findById(shopId);

		// check if shop available or not
		if (shop == null) {
			throw new RuntimeException("Shop is not available");
		}

		if (shiftRepository.findByShopAndStartTimeBetween(shop, startTime, endTime) != null) {

			throw new RuntimeException("shop is already added to shift during this time");
		}

		// Create shift
		Shift shift = new Shift();
		shift.setShop(shop);
		shift.setStartTime(startTime);
		shift.setEndTime(endTime);

		return shiftRepository.save(shift);
	}

	public Shift addUserToShift(int userId, int shiftId) {

		User user = userRepository.findById(userId);

		Shift shift = shiftRepository.findById(shiftId);

		// Check if the user is already associated with the shift
		if (shift.getUser() != null && shift.getUser().equals(user)) {

			throw new RuntimeException("User is already associated with the shift");
		}

		validateShift(user, shift.getShop(), shift.getStartTime(), shift.getEndTime());

		// Add user to shift
		shift.setUser(user);

		return shiftRepository.save(shift);
	}

	public boolean hasExceededHoursInShop(User user, Shop shop, LocalDateTime startTime, LocalDateTime endTime) {

		// Implement logic to check if the user has exceeded 8 hours in the same shop
		// within a 24-hour window
		List<Shift> userShiftsInShop = shiftRepository.findByUserAndShopAndStartTimeBetween(user, shop,
				startTime.minusDays(1), endTime);

		// Calculate the total hours the user has worked in the same shop within the
		// 24-hour window
		long totalHoursWorked = 0;

		if (userShiftsInShop != null && userShiftsInShop.size() > 0) {
			totalHoursWorked = userShiftsInShop.stream()
					.mapToLong(shift -> Duration.between(shift.getStartTime(), shift.getEndTime()).toHours()).sum();
		}
		// Check if the total hours exceed 8
		return totalHoursWorked + Duration.between(startTime, endTime).toHours() > 8;
	}

	public boolean hasExceededDaysInShop(User user, Shop shop, LocalDateTime startTime) {
		// Implement logic to check if the user is working more than 5 days in a row in
		// the same shop

		List<Shift> userShiftsInShop = shiftRepository.findByUserAndShopOrderByStartTime(user, shop,
				startTime.minusDays(5), startTime);

		int consecutiveDaysWorked = 1;

		if (userShiftsInShop.size() > 2 && userShiftsInShop != null) {

			for (int i = 1; i < userShiftsInShop.size(); i++) {

				LocalDateTime previousEndTime = userShiftsInShop.get(i - 1).getEndTime();

				LocalDateTime currentStartTime = userShiftsInShop.get(i).getStartTime();

				if (previousEndTime.plusDays(1).isBefore(currentStartTime)) {
					consecutiveDaysWorked = 1;
					return false;
				}

				consecutiveDaysWorked++;
			}

			if (consecutiveDaysWorked > 4) {
				return true;
			}
		}
		return false;

	}

	public boolean isWorkingInMultipleShops(User user, LocalDateTime startTime, LocalDateTime endTime) {

		// Implement logic to check if the user is working in multiple shops at the same
		// time
		List<Shift> overlappingShifts = shiftRepository.findByUserAndStartTimeAfterAndEndTimeBefore(user, startTime,
				endTime);

		// Check if there are shifts in different shops during the same time range
		return overlappingShifts.stream().map(Shift::getShop).distinct().count() > 0;
	}

	private void validateShift(User user, Shop shop, LocalDateTime startTime, LocalDateTime endTime) {

		// Rule: Check if the user has exceeded 8 hours in the same shop within a
		// 24-hour window

		if (hasExceededHoursInShop(user, shop, startTime, endTime)) {
			throw new RuntimeException("User has exceeded 8 hours in the same shop within a 24-hour window");
		}

		// Rule : Check if the user is working more than 5 days in a row in the same
		// shop
		if (hasExceededDaysInShop(user, shop, startTime)) {
			throw new RuntimeException("User is working more than 5 days in a row in the same shop");
		}

		// Rule 3: Check if the user is working in multiple shops at the same time
		if (isWorkingInMultipleShops(user, startTime, endTime)) {
			throw new RuntimeException("User is working in multiple shops at the same time");
		}

	}

	public List<Shift> getShifts() {

		return shiftRepository.findAll();
	}

}
