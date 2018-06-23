package com.barbershop.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.barbershop.bean.Merchant;
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
		//Transaction tran = session.beginTransaction();
		Shop shop = session.get(Shop.class, shopid);
		return shop;
	}
	/**根据shop获取merchant
	 * 
	 * @param shop
	 * @return
	 */
	public Merchant findMerchantByShop(Shop shop) {
		Merchant merchant = (Merchant) this.getSession()
				.createQuery("from Merchant where shop=?")
				.setParameter(0, shop).uniqueResult();
		return merchant;
	}
	/**
	 * 模糊匹配
	 * @param ShopName
	 * @return
	 */
	public List<Shop> SelectShopFuzzyMatching(String ShopName){
		Session session = this.getSession();
		String hql= "from Shop where shopName like '%"+ShopName+"%'";
		@SuppressWarnings("unchecked")
		Query<Shop> query = session.createQuery(hql);
		System.out.println(hql);
		List<Shop> list = query.list();
		return list;
	}
	
	/**
	 * 保存shop对象
	 * @param shop
	 */
	public void saveShop(Shop shop) {
		this.getSession().save(shop);
	}
	
	/**
	 * 更新shop信息
	 * @param shop
	 */
	public void updateShop(Shop shop) {
		this.getSession().update(shop);
	}

}
