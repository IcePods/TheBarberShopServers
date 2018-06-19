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

import com.barbershop.bean.HairStyle;
import com.barbershop.bean.Shop;
import com.barbershop.service.HairStyleService;
import com.barbershop.service.ShopService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class HairStyleAction {
	@Autowired
	private HairStyleService hsService;
	@Autowired
	private ShopService shopservice;
	////////////////////////////////////////////////////////////////////////////////
	////////////////////////客户端操作////////////////////////////////////////////////
	//首页根据类型展示发型信息
	@ResponseBody
	@RequestMapping(value = "/getHairStyleByTypeInHome", method = RequestMethod.POST)
	public List<HairStyle> getHairStyleByTypeInHome(@RequestBody String type){
		System.out.println("首页根据类型展示发型信息");
		List<HairStyle> list = hsService.getHairStyleByTyple(type);
		return list;
	}
	//在店铺中查询发型（店铺作品）
	@ResponseBody
	@RequestMapping(value = "/getHairStyleByShopInShopDetail", method = RequestMethod.POST)
	public List<HairStyle> getHairStyleByShopInShop(HttpServletRequest request,HttpServletResponse response, HttpSession session){
		System.out.println("在店铺中查询发型（店铺作品）");
		/*Gson gson = new GsonBuilder()
				.serializeNulls()
				.setPrettyPrinting()
				.create();
		Shop shop = gson.fromJson(data, Shop.class);*/
		//获取店铺id 转换成int类型 通过ShopID 获取对应店铺
		String Id = request.getParameter("shopId");
		System.out.println("Id::::::::"+Id);
		int Shopid = Integer.parseInt(Id);
		Shop shop = shopservice.getShopByShopId(Shopid);
		//获取发型类型参数
		String hairStyleType = request.getParameter("HairStyleType");
		
		List<HairStyle> list = hsService.getHairStyleByShop(shop,hairStyleType);
		return list;
	}
	////////////////////////////////////////////////////////////////////////////////
	////////////////////////商家端操作/////////////////////////////////////////////////
	@ResponseBody
	@RequestMapping(value = "/SaveNewHairStyleByShopInShop", method = RequestMethod.POST)
	public List<HairStyle> SaveNewHairStyleByShopInShop(@RequestHeader(value="MerchantToken") String MerchantToken ,@RequestBody String HairStyleJson,HttpServletRequest request,HttpServletResponse response, HttpSession session){
		
		
		return null;
		
	}
}
