package com.woorkshift;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.workshift.model.Shift;
import com.workshift.model.Shop;
import com.workshift.model.User;
import com.workshift.repository.ShiftRepository;
import com.workshift.repository.ShopRepository;
import com.workshift.repository.UserRepository;
import com.workshift.services.ShiftServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ShiftServiceTest {
	
	 	@InjectMocks
	    private ShiftServiceImpl shiftService;

	    @Mock
	    private ShiftRepository shiftRepository;
	    
	    @Mock 
	    private ShopRepository shopRepository;
	    
	    @Mock 
	    private UserRepository userRepository;

	    @Test
	    void testCreateShift() {
	        // Arrange
	        int shopId = 1;
	        LocalDateTime startTime = LocalDateTime.now();
	        LocalDateTime endTime = startTime.plusHours(8);

	        Shop mockShop = new Shop();
	        mockShop.setShopId(shopId);

	        when(shopRepository.findById(shopId)).thenReturn(mockShop);
	      
	        when(shiftRepository.findByShopAndStartTimeBetween(mockShop, startTime, endTime)).thenReturn(null);

	        // Act
	        Shift createdShift = shiftService.createShift(shopId, startTime, endTime);

	        // Assert
	        assertNotNull(createdShift);
	        assertEquals(mockShop, createdShift.getShop());
	        assertEquals(startTime, createdShift.getStartTime());
	        assertEquals(endTime, createdShift.getEndTime());

	        verify(shiftRepository, times(1)).save(any(Shift.class));
	    }
	    
	    @Test
	    void testCreateShiftWithInvalidShop() {
	        // Arrange
	        int invalidShopId = 999; // An invalid shop ID
	        LocalDateTime startTime = LocalDateTime.now();
	        LocalDateTime endTime = startTime.plusHours(8);

	        when(shopRepository.findById(invalidShopId)).thenReturn(any(Shop.class));

	        // Act and Assert
	        assertThrows(RuntimeException.class, () -> shiftService.createShift(invalidShopId, startTime, endTime));

	        verify(shiftRepository, never()).save(any(Shift.class));
	    }
	    
	    @Test
	    void testCreateShiftWithOverlappingShift() {
	        // Arrange
	        int shopId = 1;
	        LocalDateTime startTime = LocalDateTime.now();
	        LocalDateTime endTime = startTime.plusHours(8);

	        Shop mockShop = new Shop();
	        mockShop.setShopId(shopId);

	        when(shopRepository.findById(shopId)).thenReturn(mockShop);
	        when(shiftRepository.findByShopAndStartTimeBetween(mockShop, startTime, endTime))
	                .thenReturn(new Shift());

	        // Act and Assert
	        assertThrows(RuntimeException.class, () -> shiftService.createShift(shopId, startTime, endTime));

	        verify(shiftRepository, never()).save(any(Shift.class));
	    }
	    
	    @Test
	    void testAddUserToShift() {
	        // Arrange
	        int userId = 1;
	        int shiftId = 1;

	        User mockUser = new User();
	        mockUser.setUserId(userId);

	        Shift mockShift = new Shift();
	        mockShift.setShiftId(shiftId);

	        when(userRepository.findById(userId)).thenReturn(mockUser);
	        when(shiftRepository.findById(shiftId)).thenReturn(mockShift);
	        when(shiftRepository.findByUserAndShopAndStartTimeBetween(any(User.class), any(Shop.class), any(LocalDateTime.class), any(LocalDateTime.class)))
	                .thenReturn(Arrays.asList(new Shift()));

	        // Act
	        Shift updatedShift = shiftService.addUserToShift(userId, shiftId);

	        // Assert
	        assertNotNull(updatedShift);
	        assertEquals(mockUser, updatedShift.getUser());

	        verify(shiftRepository, times(1)).save(any(Shift.class));
	    }

	    @Test
	    void testAddUserToShiftWithInvalidUser() {
	        // Arrange
	        int invalidUserId = 999; // An invalid user ID
	        int shiftId = 1;

	        when(userRepository.findById(invalidUserId)).thenReturn(any(User.class));

	        // Act and Assert
	        assertThrows(RuntimeException.class, () -> shiftService.addUserToShift(invalidUserId, shiftId));

	        verify(shiftRepository, never()).save(any(Shift.class));
	    }
	    
	    @Test
	    void testAddUserToShiftWithInvalidShift() {
	        // Arrange
	        int userId = 1;
	        int invalidShiftId = 999; // An invalid shift ID

	        User mockUser = new User();
	        mockUser.setUserId(userId);

	        when(userRepository.findById(userId)).thenReturn(mockUser);
	        when(shiftRepository.findById(invalidShiftId)).thenReturn(any(Shift.class));

	        // Act and Assert
	        assertThrows(RuntimeException.class, () -> shiftService.addUserToShift(userId, invalidShiftId));

	        verify(shiftRepository, never()).save(any(Shift.class));
	    }




}
