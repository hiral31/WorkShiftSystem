package com.workshift.model;



import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "user_detail")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	@Column(unique = true, nullable = false,length = 30)
	@Size(min = 3, max = 30)
	private String name;
	
	  // Relationships
	@ManyToMany
	@JoinTable(
            name = "UserShop", 
            joinColumns = { @JoinColumn(name = "user_id") }, 
            inverseJoinColumns = { @JoinColumn(name = "shop_id") }
        )
    private Set<Shop> shops = new HashSet<>();

    @OneToMany()
    private Set<Shift> shifts = new HashSet<>();


	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	

	public User() {
		
	}

	/**
	 * @return the shops
	 */
	public Set<Shop> getShops() {
		
		return shops;
	}

	/**
	 * @param shops the shops to set
	 */
	public void setShops(Set<Shop> shops) {
		this.shops = shops;
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
	 * @param userId
	 * @param name
	 * @param shops
	 * @param shifts
	 */
	public User(int userId, String name, Set<Shop> shops, Set<Shift> shifts) {
		super();
		this.userId = userId;
		this.name = name;
		this.shops = shops;
		this.shifts = shifts;
	}
	
	public void addshop(Shop shop) {
	    this.shops.add(shop);
	    shop.getUsers().add(this);
	  }
	
	
}
