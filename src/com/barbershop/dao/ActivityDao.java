package com.barbershop.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.barbershop.bean.Activity;
import com.barbershop.bean.Shop;

@Repository
public class ActivityDao {
	@Autowired
	private  SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * 获取店铺活动
	 * @param shop 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Activity> getActivityListByShop(Shop shop){
		return this.getSession().createQuery("from Activity where shop = ? order by activityId desc")
				.setParameter(0, shop)
				.list();
	}
	
	/**
	 * 保存活动对象到数据库
	 * @param activity 店铺活动对象
	 */
	public void saveActivity(Activity activity) {
		this.getSession().save(activity);
	}
	
	/**
	 * 从数据库删除店铺活动对象
	 * @param activity
	 */
	public void deleteActivity(Activity activity) {
		this.getSession().delete(activity);
	}
}
