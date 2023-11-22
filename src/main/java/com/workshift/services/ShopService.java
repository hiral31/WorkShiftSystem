package com.workshift.services;

import java.util.List;

import com.workshift.model.Shop;

public interface ShopService {
	
	public Shop createShop(String name);
	
	public void addUserToShop(int userId, int shopId);
	
	public List<Shop> getShops();

}
