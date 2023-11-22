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

import com.workshift.model.Shop;
import com.workshift.services.ShopService;
import com.workshift.vo.ShopVo;
import com.workshift.vo.UserShopVo;

@RestController
@RequestMapping("/api/workshift")
public class ShopController {

	@Autowired
	private ShopService shopService;

	@GetMapping("/shops")
	public ResponseEntity<List<Shop>> getShops() {
		List<Shop> shops = new ArrayList<Shop>();

		shops = shopService.getShops();

		if (shops.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(shops, HttpStatus.OK);
	}

	@PostMapping("/shop")
	public ResponseEntity<Shop> createShop(@RequestBody ShopVo shopVo) {
		try {

			Shop shop = shopService.createShop(shopVo.getName());

			return new ResponseEntity<>(shop, HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/addUserToShop")
	public ResponseEntity<Void> addUserToShop(@RequestBody UserShopVo userShopVo) {
		try {

			shopService.addUserToShop(userShopVo.getUserId(), userShopVo.getShopId());

			return new ResponseEntity<>(HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
