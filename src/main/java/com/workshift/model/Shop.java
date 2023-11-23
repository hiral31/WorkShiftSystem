package com.workshift.model;



import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;

@Entity
public class Shop {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shop_id")
    private int shopId;

    
	@Column(unique = true, nullable = false,length = 30)
	@Size(min = 3, max = 30)
    private String name;
    
    
	// Relationships
    @OneToMany(mappedBy = "shop")
    private Set<Shift> shifts = new HashSet<>();

    @ManyToMany(mappedBy = "shops")
    @JsonIgnore
    private Set<User> users = new HashSet<>();
    
    /**
	 * @return the shopId
	 */
	public int getShopId() {
		return shopId;
	}

	/**
	 * @param shopId the shopId to set
	 */
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the shifts
	 */
	public Set<Shift> getShifts() {
		return shifts;
	}

	/**
	 * @param shifts the shifts to set
	 */
	public void setShifts(Set<Shift> shifts) {
		this.shifts = shifts;
	}

	
	/**
	 * @return the users
	 */
	public Set<User> getUsers() {
		
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	/**
	 * 
	 */
	public Shop() {
		
	}

	/**
	 * @param shopId
	 * @param name
	 * @param shifts
	 * @param users
	 */
	public Shop(int shopId, String name, Set<Shift> shifts, Set<User> users) {
		super();
		this.shopId = shopId;
		this.name = name;
		this.shifts = shifts;
		this.users = users;
	}

	
    

}
