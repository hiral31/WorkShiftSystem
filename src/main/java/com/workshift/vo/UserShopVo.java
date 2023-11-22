package com.workshift.vo;

import java.io.Serializable;
import java.util.Objects;

public class UserShopVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int shopId;
	
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
	public int hashCode() {
		return Objects.hash(shopId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserShopVo other = (UserShopVo) obj;
		return shopId == other.shopId && userId == other.userId;
	}

	public UserShopVo() {
		
	}
	
}
