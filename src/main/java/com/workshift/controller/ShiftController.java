package com.workshift.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workshift.model.Shift;
import com.workshift.services.ShiftServiceImpl;
import com.workshift.vo.ShiftVo;
import com.workshift.vo.UserShiftVo;

@RestController
@RequestMapping("/api/workshift")
public class ShiftController {

	@Autowired
	private ShiftServiceImpl shiftService;

	@GetMapping("/shifts")
	public ResponseEntity<List<Shift>> getShifts() {

		List<Shift> shifts = new ArrayList<Shift>();

		shifts = shiftService.getShifts();

		if (shifts.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(shifts, HttpStatus.OK);
	}

	@PostMapping("/shift")
	public ResponseEntity<Shift> createShift(@RequestBody ShiftVo shiftVo) {
		try {

			Shift shift = shiftService.createShift(shiftVo.getShop().getShopId(),
					shiftVo.getStartTime(), shiftVo.getEndTime());

			return new ResponseEntity<>(shift, HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/addUserToShift")
	public ResponseEntity<Shift> addUserToShift(@RequestBody UserShiftVo userShiftVo) {
		try {

			Shift shift = shiftService.addUserToShift(userShiftVo.getUserId(), userShiftVo.getShiftId());

			return new ResponseEntity<>(shift, HttpStatus.CREATED);
		
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
