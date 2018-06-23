package com.barbershop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barbershop.bean.Activity;
import com.barbershop.bean.Shop;
import com.barbershop.dao.ActivityDao;

@Service
@Transactional
public class ActivityService {
	@Autowired
	private ActivityDao ad;
	
	/**
	 * 通过shop获取Activity对象列表
	 * @param shop
	 * @return
	 */
	public List<Activity> getActivityByShop(Shop shop){
		return ad.getActivityListByShop(shop);
	}
	
	/**
	 * 保存Activity对象到数据库
	 * @param activity
	 */
	public void saveActivity(Activity activity) {
		ad.saveActivity(activity);
	}
	
	/**
	 * 从数据库删除activity对象
	 * @param activity
	 */
	public void deleteActivity(Activity activity) {
		ad.deleteActivity(activity);
	}

}
