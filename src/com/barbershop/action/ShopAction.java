package com.barbershop.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.barbershop.bean.Shop;
import com.barbershop.service.ShopService;

@Controller
public class ShopAction {
	@Autowired
	private ShopService ss;
	
	@ResponseBody
	@RequestMapping(value = "/AllShop", method = RequestMethod.GET)
	public List<Shop> showAllShop() {
		System.out.println("店铺请求");
		List<Shop> Shoplist = ss.getAllShop();
		System.out.println("店铺加载成功！");
		return Shoplist;
	}
//	@ResponseBody
//	@RequestMapping(value = "/ShopDetail", method = RequestMethod.POST);
//	public Shop getShopByShopId() {};
	
}
