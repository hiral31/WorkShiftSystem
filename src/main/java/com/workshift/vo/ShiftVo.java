package com.workshift.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.workshift.model.Shop;
import com.workshift.model.User;

public class ShiftVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int shiftId;
	  private User user;
	  private Shop shop;
	  private LocalDateTime startTime;
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
	@Override
	public String toString() {
		return "ShiftVo [shiftId=" + shiftId + ", user=" + user + ", shop=" + shop + ", startTime=" + startTime
				+ ", endTime=" + endTime + "]";
	}
	/**
	 * @param shiftId
	 * @param user
	 * @param shop
	 * @param startTime
	 * @param endTime
	 */
	public ShiftVo(int shiftId, User user, Shop shop, LocalDateTime startTime, LocalDateTime endTime) {
		super();
		this.shiftId = shiftId;
		this.user = user;
		this.shop = shop;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	  
	public ShiftVo() {
		
	}
}
