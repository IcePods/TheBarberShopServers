package com.barbershop.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.barbershop.bean.Activity;
import com.barbershop.bean.Merchant;
import com.barbershop.bean.Shop;
import com.barbershop.service.ActivityService;
import com.barbershop.service.MerchantService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class ActivityAction {
	@Autowired
	private MerchantService mService;
	@Autowired
	private ActivityService aService;
	
	/**
	 * 获得店铺活动列表
	 */
	@ResponseBody
	@RequestMapping(value="/getActivityList", method = RequestMethod.POST)
	public List<Activity> getActivityList(@RequestBody String merchantJson, @RequestHeader(value="MerchantToken") String merchantToken){
		System.out.println("活动列表");
		Merchant merchant = mService.getMerchantByToken(merchantToken);
		Shop shop = merchant.getShop();
		List<Activity> list = aService.getActivityByShop(shop);
		return list;
	}
	
	/**
	 * 添加活动
	 * @param data Activity对象的json串
	 * @param token 商家用户token
	 */
	@RequestMapping(value="/addActivity", method=RequestMethod.POST)
	public void addActivity(@RequestBody String data, @RequestHeader(value="MerchantToken") String token) {
		System.out.println("添加活动");
		Merchant merchant = mService.getMerchantByToken(token);
		Shop shop = merchant.getShop();
		Gson gson = new GsonBuilder()
				.serializeNulls()
				.setPrettyPrinting()
				.create();
		Activity activity = gson.fromJson(data, Activity.class);
		activity.setShop(shop);
		aService.saveActivity(activity);
	}
	
	/**
	 * 删除活动
	 * @param data
	 */
	@RequestMapping(value="/deleteActivity", method=RequestMethod.POST)
	public void deleteActivity(@RequestBody String data) {
		Gson gson = new GsonBuilder()
				.serializeNulls()
				.setPrettyPrinting()
				.create();
		Activity activity = gson.fromJson(data, Activity.class);
		aService.deleteActivity(activity);
	}
	
}
