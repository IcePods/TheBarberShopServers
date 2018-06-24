package com.barbershop.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
	@SuppressWarnings("unchecked")
	public List<HairStyle> getHairStyleByTyple(String type){
		return this.getSession().createQuery("from HairStyle hs where hs.hairstyleType = ?")
				.setParameter(0, type)
				.list();
	}
	
	// 从店铺  查看 发型 根据 店铺 获取 集合
	@SuppressWarnings("unchecked")
	public List<HairStyle> getHairStyleByShop(Shop shop,String type){
		return this.getSession().createQuery("from HairStyle hs where hs.shop=? and hs.hairstyleType=? ")
				.setParameter(0, shop)
				.setParameter(1, type)
				.list();
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
	
	//得到店铺作品
	@SuppressWarnings("unchecked")
	public List<HairStyle> getHairStyleByShop(Shop shop){
		return this.getSession().createQuery("from HairStyle where shop = ? order by hairstyleId desc")
				.setParameter(0, shop)
				.list();
	}
	
	//删除发型
	public void deleteHairStyle(HairStyle hairStyle) {
		this.getSession().delete(hairStyle);
	}
	//根据发型ID获取发型对象
	public HairStyle getHairStyleByID(int id) {
		return this.getSession().get(HairStyle.class, id);
	}
}
