package com.barbershop.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.barbershop.bean.HairStyle;
import com.barbershop.bean.Shop;
import com.barbershop.service.HairStyleService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class HairStyleAction {
	@Autowired
	private HairStyleService hsService;
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
	public List<HairStyle> getHairStyleByShopInShop(@RequestBody String data){
		System.out.println("在店铺中查询发型（店铺作品）");
		Gson gson = new GsonBuilder()
				.serializeNulls()
				.setPrettyPrinting()
				.create();
		Shop shop = gson.fromJson(data, Shop.class);
		List<HairStyle> list = hsService.getHairStyleByShop(shop);
		return list;
	}
}
