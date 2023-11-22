package com.workshift.vo;

import java.io.Serializable;

import com.workshift.model.User;

public class ShopVo implements Serializable {

	 private static final long serialVersionUID = 1L;
	private int shopId;
	 private String name;
	 private User user;
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
	 
}
