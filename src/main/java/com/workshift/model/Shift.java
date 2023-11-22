package com.workshift.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Shift {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shift_id")
	private int shiftId;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "shop_id", nullable = false)
	private Shop shop;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "start_time", nullable = false)
	private LocalDateTime startTime;

	@Column(name = "end_time", nullable = false)
	private LocalDateTime endTime;

	/**
	 * @return the shiftId
	 */
	public int getShiftId() {
		return shiftId;
	}

	/**
	 * @param shiftId the shiftId to set
	 */
	public void setShiftId(int shiftId) {
		this.shiftId = shiftId;
	}

	/**
	 * @return the shop
	 */
	public Shop getShop() {
		return shop;
	}

	/**
	 * @param shop the shop to set
	 */
	public void setShop(Shop shop) {
		this.shop = shop;
	}

	/**
	 * @return the startTime
	 */
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public LocalDateTime getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	public Shift() {

	}

	/**
	 * @param shiftId
	 * @param shop
	 * @param user
	 * @param startTime
	 * @param endTime
	 */
	public Shift(int shiftId, Shop shop, User user, LocalDateTime startTime, LocalDateTime endTime) {
		super();
		this.shiftId = shiftId;
		this.shop = shop;
		this.user = user;
		this.startTime = startTime;
		this.endTime = endTime;
	}

}
