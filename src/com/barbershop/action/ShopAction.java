package com.barbershop.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
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
	@ResponseBody
	@RequestMapping(value = "/SelectShopFuzzyMatching", method = RequestMethod.POST)
	public List<Shop> SelectShopFuzzyMatching(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		System.out.println("模糊匹配查询");
		String ShopName = request.getParameter("ShopName");
		System.out.println("模糊搜索词："+ShopName);
		if(ShopName.isEmpty()) {
			return new ArrayList<Shop>();
		}else {
			List<Shop> list = new ArrayList<Shop>();
			list = ss.SelectShopFuzzyMatching(ShopName);
			System.out.println("迷糊匹配查询的个数："+list.size());
			return list;
		}
		
		
	}
	
}
