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
		// Retrieve user and shop
		//User user = userRepository.findById(userId);

		Shop shop = shopRepository.findById(shopId);

		// check if shop available or not
		if(shop ==null ) {
			throw new RuntimeException("Shop is not available");
		}
		
		// Check if the user is already working in another shop at the same time
		if (shiftRepository.findByShopAndStartTimeBetween(shop, startTime, endTime)!= null) {

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

		// Retrieve user and shift
		User user = userRepository.findById(userId);

		Shift shift = shiftRepository.findById(shiftId);

		
		
		// Check if the user is already associated with the shift
		if (shift.getUser() != null && shift.getUser().equals(user)) {

			throw new RuntimeException("User is already associated with the shift");
		}

		// Validate business rules
		// validateShiftBusinessRules(shift);
		validateShift(user, shift.getShop(), shift.getStartTime(), shift.getEndTime());

		// Add user to shift
		shift.setUser(user);

		return shiftRepository.save(shift);
	}

//    private void validateShiftBusinessRules(Shift shift) {
//        
//    	// Check if the user is working in the same shop for more than 8 hours within a 24-hour window
//        List<Shift> shiftsInWindow = shiftRepository.findByUserAndStartTimeAfterAndEndTimeBefore(
//                
//        		shift.getUser(),
//                
//        		shift.getStartTime().minusHours(8),
//                
//        		shift.getEndTime().plusHours(8)
//        );
//
//        for (Shift existingShift : shiftsInWindow) {
//        	
//            if (existingShift.getShop().equals(shift.getShop())) {
//            	
//                throw new RuntimeException("User cannot work in the same shop for more than 8 hours within a 24-hour window");
//            }
//        }
//
//        // Check if the user is working more than 5 days in a row in the same shop
//        List<Shift> shiftsInShop = shiftRepository.findByUserAndShopOrderByStartTime(shift.getUser().getUsers().get(0), shift.getShop());
//
//        int consecutiveDays = 1;
//        
//        for (int i = 1; i < shiftsInShop.size(); i++) {
//           
//        	if (shiftsInShop.get(i).getStartTime().toLocalDate().equals(
//            		shiftsInShop.get(i - 1).getStartTime().toLocalDate().plusDays(1))) {
//
//            	consecutiveDays++;
//            
//            } else {
//                consecutiveDays = 1;
//            }
//
//            if (consecutiveDays > 5) {
//                throw new RuntimeException("User cannot work more than 5 days in a row in the same shop");
//            }
//        }
//    }

	public boolean hasExceededHoursInShop(User user, Shop shop, LocalDateTime startTime, LocalDateTime endTime) {
		// Implement logic to check if the user has exceeded 8 hours in the same shop
		// within a 24-hour window
		// Query the database to fetch the user's previous shifts in the same shop
		System.out.println("startTime.minusDays(1)-->"+startTime.minusDays(1));
		List<Shift> userShiftsInShop = shiftRepository.findByUserAndShopAndStartTimeBetween(user, shop,
				startTime.minusDays(1), endTime);

		// Calculate the total hours the user has worked in the same shop within the
		// 24-hour window
		long totalHoursWorked=0;
		if(userShiftsInShop !=null && userShiftsInShop.size()>0) {
			totalHoursWorked = userShiftsInShop.stream()
				.mapToLong(shift -> Duration.between(shift.getStartTime(), shift.getEndTime()).toHours()).sum();
		}
		System.out.println("totalHoursWorked--->" + totalHoursWorked 
				+"Duration.between(startTime, endTime).toHours() ->"
				+ Duration.between(startTime, endTime).toHours());
		// Check if the total hours exceed 8
		return totalHoursWorked + Duration.between(startTime, endTime).toHours() > 8;
	}

	public boolean hasExceededDaysInShop(User user, Shop shop, LocalDateTime startTime) {
		// Implement logic to check if the user is working more than 5 days in a row in
		
		
		// the same shop
		// Query the database to fetch the user's previous shifts in the same shop
		
		System.out.println("startTime.minusDays(5)--->" +startTime.minusDays(5));
		
		System.out.println("startTime--->"+ startTime);
		List<Shift> userShiftsInShop = shiftRepository.findByUserAndShopOrderByStartTime(user, shop,startTime.minusDays(5),startTime);

		System.out.println("userShiftsInShop.size()--->"+userShiftsInShop.size());
		
		int consecutiveDaysWorked = 1;
		
		if(userShiftsInShop.size() > 2 && userShiftsInShop !=null) {

			for (int i = 1; i < userShiftsInShop.size(); i++) {
            
			LocalDateTime previousEndTime = userShiftsInShop.get(i - 1).getEndTime();
            
            System.out.println("previousEndTime-->" + previousEndTime);
			
			LocalDateTime currentStartTime = userShiftsInShop.get(i).getStartTime();
			
			System.out.println("currentStartTime--->" + currentStartTime);

            if (previousEndTime.plusDays(1).isBefore(currentStartTime)) {
                // There is a gap of more than 1 day between shifts, reset the count
            	consecutiveDaysWorked = 1;
                return false;
            }
            
            consecutiveDaysWorked++;
        }

        // If the loop completes without returning, there are consecutive shifts
			System.out.println("consecutiveDaysWorked--->" + consecutiveDaysWorked);
			if (consecutiveDaysWorked > 4) {
//				// User has worked more than 5 days in a row
				return true;
			}
		}
		return false;
		
//		int consecutiveDaysWorked = 0;
//		
//		
//		
//		for (Shift shift : userShiftsInShop) {
//		
//			System.out.println("consecutiveDaysWorked---> " +consecutiveDaysWorked);
//			
//			if (shift.getStartTime().toLocalDate().isEqual(startTime.toLocalDate())) {
//				// Skip shifts on the same day as the new shift
//				continue;
//			}
//			System.out.println("shift.getStartTime().plusDays(1).toLocalDate()---> " +
//					shift.getStartTime().plusDays(1).toLocalDate());
//			System.out.println("startTime.toLocalDate()--->" + shift.getStartTime().toLocalDate());
//			
//			System.out.println(" compare date-->" +shift.getStartTime().plusDays(1).toLocalDate().compareTo((shift.getStartTime().toLocalDate())));
//			
//			if (shift.getEndTime().plusDays(1).toLocalDate().compareTo((shift.getStartTime().toLocalDate())) == 0) {
//				// If the next shift is on the next day, increment the consecutive days counter
//				System.out.println("in if..");
//				consecutiveDaysWorked++;
//			} else {
//				// Reset the counter if there is a gap between shifts
//				System.out.println("in else");
//				consecutiveDaysWorked = 0;
//			}
//			System.out.println("consecutiveDaysWorked---> " +consecutiveDaysWorked);
//			if (consecutiveDaysWorked >= 4) {
//				// User has worked more than 5 days in a row
//				return true;
//			}
//		}
//
//		return false;
		
		
	}

	public boolean isWorkingInMultipleShops(User user, LocalDateTime startTime, LocalDateTime endTime) {
		// Implement logic to check if the user is working in multiple shops at the same
		// time
		// Query the database to fetch the user's shifts that overlap with the given
		// time range
		List<Shift> overlappingShifts = shiftRepository.findByUserAndStartTimeAfterAndEndTimeBefore(user, startTime,
				endTime);

		// Check if there are shifts in different shops during the same time range
		System.out.println("isWorkingInMultipleShops--> " + overlappingShifts.stream().map(Shift::getShop).distinct().count());
		return overlappingShifts.stream().map(Shift::getShop).distinct().count() > 0;
	}

	private void validateShift(User user, Shop shop, LocalDateTime startTime, LocalDateTime endTime) {
//		for (User user : users) {

			// Rule 1: Check if the user has exceeded 8 hours in the same shop within a
			// 24-hour window
			if (hasExceededHoursInShop(user, shop, startTime, endTime)) {
				throw new RuntimeException("User has exceeded 8 hours in the same shop within a 24-hour window");
			}

			// Rule 2: Check if the user is working more than 5 days in a row in the same
			// shop
			if (hasExceededDaysInShop(user, shop, startTime)) {
				throw new RuntimeException("User is working more than 5 days in a row in the same shop");
			}

			// Rule 3: Check if the user is working in multiple shops at the same time
			if (isWorkingInMultipleShops(user, startTime, endTime)) {
				throw new RuntimeException("User is working in multiple shops at the same time");
			}
//		}
	}

	public List<Shift> getShifts() {

		return shiftRepository.findAll();
	}

}
