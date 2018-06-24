package com.barbershop.action;

import java.util.ArrayList;
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

import com.barbershop.bean.Merchant;
import com.barbershop.bean.Shop;
import com.barbershop.bean.ShopPicture;
import com.barbershop.service.MerchantService;
import com.barbershop.service.ShopService;
import com.barbershop.utils.UploadPictureUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
public class ShopAction {
	@Autowired
	private ShopService ss;
	@Autowired
	private MerchantService mService;
	UploadPictureUtil util = new UploadPictureUtil();
	
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
	
	/**
	 * 接收商家上传店铺图片
	 * @param data
	 * @param merchantToken
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/uploadShopPic", method=RequestMethod.POST)
	public List<String> uploadPicList(@RequestBody String data,  @RequestHeader(value="MerchantToken") String merchantToken){
		//根据token获取shop对象
		System.out.println("执行上传发型图片方法");
		Merchant merchant = mService.getMerchantByToken(merchantToken);
		
		//初始化文件目录
		util.initMerchantFileDirectory(merchant.getMerchantAccount());
		//模拟多图上传
		List<String> picList = new ArrayList<String>();
		Gson gson = new GsonBuilder()
			.serializeNulls()
			.setPrettyPrinting()
			.create();		
		picList = gson.fromJson(data, new TypeToken<List<String>>() {}.getType());
		List<String> picPathList = util.receiveMerchantShopImagePic(picList, merchant.getMerchantAccount());
		
		return picPathList;
	}
	
	/**
	 * 只上传头像
	 * @param data
	 * @param token
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/uploadHeader", method=RequestMethod.POST)
	public String uploadHeader(@RequestBody String data, @RequestHeader(value="MerchantToken") String token) {
		Merchant merchant = mService.getMerchantByToken(token);
		String path = util.receiveMerchantHeadPic(data, merchant.getMerchantAccount());
		return path;
	}
	
	/**
	 * 更新店铺信息的响应方法
	 * @param data
	 * @param merchantToken
	 */
	@RequestMapping(value="/updateShopInformation", method=RequestMethod.POST)
	public void updateShopInformation(@RequestBody String data) {
		Gson gson = new GsonBuilder()
				.serializeNulls()
				.setPrettyPrinting()
				.create();
		List<Shop> shopList = gson.fromJson(data, new TypeToken<List<Shop>>() {}.getType());
		Shop oldShop = shopList.get(0);
		Shop newShop = shopList.get(1);
		ss.updateShop(newShop);
		//如果新的店铺信息中头像已经更换，则删除原有图片
		if(!oldShop.getShopPicture().equals(newShop.getShopPicture())) {
			util.deleteMerchantPic(oldShop.getShopPicture());
		}
		//删除店铺图片中去掉的图片
		for(ShopPicture sp: oldShop.getShopPictureSet()) {
			for(ShopPicture sp2: newShop.getShopPictureSet()) {
				if(!sp.getShoppicture_picture().equals(sp2.getShoppicture_picture())) {
					util.deleteMerchantPic(sp.getShoppicture_picture());
				}
			}
		}	
	}
	
	/**
	 * 获取shop信息
	 * @param data
	 * @param token
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getShop", method=RequestMethod.POST)
	public Shop getShopInformation(@RequestBody String data, @RequestHeader(value="MerchantToken") String token) {
		Shop shop;
		Merchant merchant = mService.getMerchantByToken(token);
		shop = merchant.getShop();
		return shop;
	}
	
	/**
	 * 添加店铺信息
	 * @param data
	 * @param token
	 */
	@RequestMapping(value="/addShop", method=RequestMethod.POST)
	public void addShop(@RequestBody String data, @RequestHeader(value="MerchantToken") String token) {
		Merchant merchant = mService.getMerchantByToken(token);
		Gson gson = new GsonBuilder()
				.serializeNulls()
				.setPrettyPrinting()
				.create();
		Shop shop = gson.fromJson(data, Shop.class);
		merchant.setShop(shop);
		mService.updateMerchant(merchant);
	}
	
}
