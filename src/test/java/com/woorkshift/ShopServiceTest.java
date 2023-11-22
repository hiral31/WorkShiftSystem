package com.woorkshift;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.workshift.model.Shop;
import com.workshift.model.User;
import com.workshift.repository.ShopRepository;
import com.workshift.repository.UserRepository;
import com.workshift.services.ShopServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ShopServiceTest {
	
	@Mock
    private UserRepository userRepository;
	
	@Mock
    private ShopRepository shopRepository;
	
	
	@InjectMocks
    private ShopServiceImpl shopService;
	
	
	
	@Test
    void testCreateShop() {
        // Arrange
        String shopName = "Example Shop";
        Shop mockShop = new Shop();
        mockShop.setShopId(1);
        mockShop.setName(shopName);

        // Mocking the behavior of shopRepository
        when(shopRepository.findByName(shopName)).thenReturn(Optional.empty());
       
        when(shopRepository.save(any(Shop.class))).thenReturn(mockShop);

        // Act
        Shop createdShop = shopService.createShop(shopName);

        // Assert
        assertNotNull(createdShop);
        assertEquals(1L, createdShop.getShopId()); // Assuming ID assignment during saving
        assertEquals(shopName, createdShop.getName());

        // Verify that shopRepository methods were called
        verify(shopRepository, times(1)).findByName(shopName);
        verify(shopRepository, times(1)).save(any(Shop.class));
    }
	
	@Test
    void testCreateShopWithExistingName() {
        // Arrange
        String existingShopName = "Existing Shop";
        Shop existingShop = new Shop();
        existingShop.setShopId(1);
        existingShop.setName(existingShopName);

        // Mocking the behavior of shopRepository to return an existing shop
        when(shopRepository.findByName(existingShopName)).thenReturn(Optional.of(existingShop));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> shopService.createShop(existingShopName));

        // Verify that shopRepository methods were called
        verify(shopRepository, times(1)).findByName(existingShopName);
        verify(shopRepository, never()).save(any(Shop.class));
    }
	
	@Test
    public void testAddUserToShop() {
        // Mock data
		String shopName = "Example Shop";
        Shop shop = new Shop();
        shop.setShopId(1);
        shop.setName(shopName);

        String userName = "John Doe";
        User user = new User();
        user.setUserId(1);
        user.setName(userName);

        // Mocking the behavior of shopRepository
        when(shopRepository.findByName(shopName)).thenReturn(Optional.of(shop));

        // Mocking the behavior of userService
        when(userRepository.findByName(userName)).thenReturn(Optional.of(user));

        // Act
        shopService.addUserToShop(user.getUserId(), shop.getShopId());

        // Assert
        assertTrue(shop.getUsers().contains(user), "User should be added to the shop");

        // Verify that shopRepository and userService methods were called
        verify(shopRepository, times(1)).findByName(shopName);
        verify(userRepository, times(1)).findByName(userName);
        verify(shopRepository, times(1)).save(shop);
    }


}
