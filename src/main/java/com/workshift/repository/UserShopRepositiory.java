package com.workshift.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.workshift.model.UserShop;

@Repository
public interface UserShopRepositiory extends JpaRepository<UserShop, Long>{
	
	@Query("SELECT t FROM UserShop t WHERE t.userId = ?1 AND t.shopId = ?2")
	UserShop findByUserAndShop(int userId, int shopId);

}
