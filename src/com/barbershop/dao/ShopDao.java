package com.barbershop.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.barbershop.bean.Shop;

@Repository
public class ShopDao {
	@Autowired
	private  SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	//获取店铺信息
	public List<Shop> getAllShop() {
		@SuppressWarnings("unchecked")
		List<Shop> listShop = (List<Shop>)this.getSession().createQuery("from Shop").list();
		return listShop;
	}
	//
	//根据ID 获取店铺
	public Shop getShopByShopId(int shopid) {
		Session session = this.getSession();
//		Query query = session.createQuery("from Shop shop where shop.shopId=?");
//		query.setParameter(0, shopid);
		Transaction tran = session.beginTransaction();
		Shop shop = session.get(Shop.class, shopid);
		return shop;
	}
	

}
