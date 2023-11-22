package com.workshift.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.workshift.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer >{
   
	@Query("SELECT t FROM User t WHERE t.name = ?1")
	Optional<User> findByName(String name);
	
	@Query("SELECT t FROM User t WHERE t.userId= ?1")
	User findById(int userId);

}
