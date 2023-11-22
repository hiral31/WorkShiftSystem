package com.workshift.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.workshift.model.Shop;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
	
	@Query("SELECT t FROM Shop t WHERE t.name = ?1")
    Optional<Shop> findByName(String name);
	
	@Query("SELECT t FROM Shop t WHERE t.shopId = ?1")
	Shop findById(int shopId);
	

}
