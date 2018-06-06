package com.barbershop.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.barbershop.bean.Barber;
import com.barbershop.bean.Shop;
import com.barbershop.bean.Users;
import com.barbershop.service.BarberService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class BarberAction {
	@Autowired
	private BarberService barberService;
	
	
	/**
	 * 通过店铺查看理发师
	 * @param request
	 * @param response
	 * @param session
	 * @param ShopJson
	 * @return
	 */
	@RequestMapping(value = "/showBarberByShop", method = RequestMethod.POST)
	@ResponseBody
	public List<Barber> showBarberByShop(HttpServletRequest request,HttpServletResponse response,HttpSession session,@RequestBody String ShopJson) {
		System.out.println("通过店铺查看理发师");
		Gson gson = new GsonBuilder()
				.serializeNulls()
				.setPrettyPrinting()
				.create();
		Shop shop = gson.fromJson(ShopJson, Shop.class);
		List<Barber> list = barberService.showBarberByShop(shop);
		return list;
		
	}
}
