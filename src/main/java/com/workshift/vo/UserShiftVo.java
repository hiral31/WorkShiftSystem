package com.workshift.vo;

import java.io.Serializable;
import java.util.Objects;

public class UserShiftVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int shiftId;
	
	private int userId;

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
		return Objects.hash(shiftId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserShiftVo other = (UserShiftVo) obj;
		return shiftId == other.shiftId && userId == other.userId;
	}

	@Override
	public String toString() {
		return "UserShiftVo [shiftId=" + shiftId + ", userId=" + userId + "]";
	}

	public UserShiftVo() {
		
	}
	/**
	 * @param shiftId
	 * @param userId
	 */
	public UserShiftVo(int shiftId, int userId) {
		super();
		this.shiftId = shiftId;
		this.userId = userId;
	}
	
	

}
