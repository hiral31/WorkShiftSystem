package com.workshift.util;

import com.workshift.model.Shift;
import com.workshift.model.Shop;
import com.workshift.model.User;
import com.workshift.vo.ShiftVo;
import com.workshift.vo.ShopVo;
import com.workshift.vo.UserVo;

public class ConvertModelToVo {

	public void UserModelToVo(User user,UserVo userVo) {
		
		userVo.setUserId(user.getUserId());
		
		userVo.setName(user.getName());
		
	}
	public void ShopModelToVo(Shop shop,ShopVo shopVo) {
		
		shopVo.setShopId(shop.getShopId());
		
		shopVo.setName(shop.getName());
		
	
	}
	
	public void ShiftModelToVo(Shift shift,ShiftVo shiftVo) {
		
		shiftVo.setShiftId(shift.getShiftId());
		
		shiftVo.setUser(shift.getUser());
		
		shiftVo.setShop(shift.getShop());
		
		shiftVo.setStartTime(shift.getStartTime());
		
		shiftVo.setEndTime(shift.getEndTime());
		
	}
}
