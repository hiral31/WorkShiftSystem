package com.workshift.util;

import com.workshift.model.Shift;
import com.workshift.model.Shop;
import com.workshift.model.User;
import com.workshift.vo.ShiftVo;
import com.workshift.vo.ShopVo;
import com.workshift.vo.UserVo;

public class ConvertVoToModel {
	
	public void UserVoToModel(User user,UserVo userVo) {
		
		user.setUserId(userVo.getUserId());
	
		user.setName(userVo.getName());
		
	}
	public void ShopVoToModel(Shop shop,ShopVo shopVo) {
		
		shop.setShopId(shopVo.getShopId());
		
		shop.setName(shopVo.getName());
		
		
	}
	
	public void ShiftVoToModel(Shift shift,ShiftVo shiftVo) {
		
		shift.setShiftId(shiftVo.getShiftId());
		
		shift.setUser(shiftVo.getUser());
		
		shift.setShop(shiftVo.getShop());
		
		shift.setStartTime(shiftVo.getStartTime());
		
		shift.setEndTime(shiftVo.getEndTime());
		
	}

}
