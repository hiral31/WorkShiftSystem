package com.workshift.services;

import java.time.LocalDateTime;
import java.util.List;

import com.workshift.model.Shift;

public interface ShiftService {
	
	
	public Shift createShift(int shopId, LocalDateTime startTime, LocalDateTime endTime);
	
	public Shift addUserToShift(int userId, int shiftId);
	
	public List<Shift> getShifts();
	 
	
}
