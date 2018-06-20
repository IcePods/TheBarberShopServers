package com.barbershop.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import com.barbershop.bean.Merchant;
import com.barbershop.bean.Shop;
import com.barbershop.service.HairStyleService;
import com.barbershop.service.MerchantService;
import com.barbershop.service.ShopService;
import com.barbershop.utils.UploadPictureUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
public class HairStyleAction {
	@Autowired
	private HairStyleService hsService;
	@Autowired
	private ShopService shopservice;
	@Autowired
	private MerchantService merchantService;
	private UploadPictureUtil util = new UploadPictureUtil();
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
	/**
	 * 接收商家上传作品图片
	 * @param data
	 * @param merchantToken
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/uploadProductionPic", method=RequestMethod.POST)
	public List<String> uploadPicList(@RequestBody String data,  @RequestHeader(value="MerchantToken") String merchantToken){
		//根据token获取shop对象
		System.out.println("执行上传发型图片方法");
		Merchant merchant = merchantService.getMerchantByToken(merchantToken);
		
		//初始化文件目录
		util.initMerchantFileDirectory(merchant.getMerchantAccount());
		//模拟多图上传
		List<String> picList = new ArrayList<String>();
		Gson gson = new GsonBuilder()
			.serializeNulls()
			.setPrettyPrinting()
			.create();		
		picList = gson.fromJson(data, new TypeToken<List<String>>() {}.getType());
		List<String> picPathList = util.receiveMerchantProductionPic(picList, merchant.getMerchantAccount());
		
		return picPathList;
	}
	
	/**
	 * 上传新作品的响应方法
	 * @param data
	 * @param merchantToken
	 */
	@RequestMapping(value="/createNewProduction", method=RequestMethod.POST)
	public void createNewProduction(@RequestBody String data, @RequestHeader(value="MerchantToken") String merchantToken) {
		//根据token获取shop对象
		System.out.println("执行上传发型对象方法");
		System.out.println(merchantToken);
		Merchant merchant = merchantService.getMerchantByToken(merchantToken);
		System.out.println("店铺          token"+merchant.getMerchantToken());
		System.out.println("传递过来的token"+merchantToken);
		Gson gson = new GsonBuilder()
				.serializeNulls()
				.setPrettyPrinting()
				.create();
		
		HairStyle hairStyle = gson.fromJson(data, HairStyle.class);
		Shop shop = merchant.getShop();
		System.out.println("店铺名："+shop.getShopName());
		System.out.println("店铺作品个数："+shop.getHairStyleSet().size());
		Set<HairStyle> set = shop.getHairStyleSet();
 		set.add(hairStyle);
		hsService.updateShopByHairstyle(shop);
		
	}
}
