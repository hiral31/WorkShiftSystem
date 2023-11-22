package com.workshift.model;

import com.workshift.vo.UserShopVo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(UserShopVo.class)
public class UserShop {

	@Id
	@Column(name = "shop_id")
	private int shopId;
	
	@Id
	@Column(name = "user_id")
	private int userId;

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
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserShop [shopId=" + shopId + ", userId=" + userId + "]";
	}

	/**
	 * @param shopId
	 * @param userId
	 */
	public UserShop(int shopId, int userId) {
		super();
		this.shopId = shopId;
		this.userId = userId;
	}
	public UserShop() {
		
	}
	

}
