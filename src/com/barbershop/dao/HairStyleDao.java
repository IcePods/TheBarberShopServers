package com.barbershop.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.barbershop.bean.HairStyle;
import com.barbershop.bean.Shop;

@Repository
public class HairStyleDao {
	@Autowired
	private  SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	//根据类型HairStyleTyple 获取集合
	public List<HairStyle> getHairStyleByTyple(String type){
		Session session = this.getSession();
		Query query = session.createQuery("from HairStyle hs where hs.hairstyleType = ?");
		query.setParameter(0, type);
		List<HairStyle> list = query.list();
		return list;
	}
	// 从店铺  查看 发型 根据 店铺 获取 集合
	public List<HairStyle> getHairStyleByShop(Shop shop,String type){
		Session session = this.getSession();
		Query<HairStyle> query = session.createQuery("from HairStyle hs where hs.shop=? and hs.hairstyleType=? ");
		query.setParameter(0, shop);
		query.setParameter(1, type);
		List<HairStyle> list = query.list();
		return list;
	}
	
	
	//保存新发型的方法
	public void addNewHairStyle(HairStyle hs) {
		this.getSession().save(hs);
	}
	//保存店铺作品 更新店铺
	public void updateShopByHairstyle(Shop shop) {
		Session session = this.getSession();
		//Transaction tran = session.beginTransaction();
		session.update(shop);
		//tran.commit();
	}
}
