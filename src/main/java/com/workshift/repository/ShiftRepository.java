package com.workshift.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.workshift.model.Shift;
import com.workshift.model.Shop;
import com.workshift.model.User;

public interface ShiftRepository extends JpaRepository<Shift, Integer> {
   
	@Query("SELECT t FROM Shift t WHERE t.shiftId= ?1")
	Shift findById(int shiftId);
	
	@Query("SELECT t from Shift t WHERE  t.user = ?1 AND t.startTime >= ?2 AND t.endTime <= ?3 ")
	List<Shift> findByUserAndStartTimeAfterAndEndTimeBefore(User user, LocalDateTime start, LocalDateTime end);

	@Query("SELECT t FROM Shift t WHERE t.user = ?1 AND t.shop = ?2 and t.startTime BETWEEN ?3 AND ?4")
	List<Shift> findByUserAndShopOrderByStartTime(User user, Shop shop,LocalDateTime startTime,LocalDateTime endTime);

	@Query("SELECT t FROM Shift t WHERE t.user = ?1 AND t.shop = ?2 AND t.startTime > ?3 AND t.endTime < ?4")
	List<Shift> findByUserAndShopAndStartTimeBetween(User user, Shop shop, LocalDateTime minusDays,
			LocalDateTime endTime);
	
	@Query("SELECT t FROM Shift t WHERE t.shop = ?1 AND t.startTime >=?2 AND t.endTime <=?3")
	Shift findByShopAndStartTimeBetween(Shop shop, LocalDateTime startTime,
			LocalDateTime endTime);
}
