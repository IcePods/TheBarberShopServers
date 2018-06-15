package com.barbershop.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.barbershop.bean.Barber;
import com.barbershop.bean.Merchant;
import com.barbershop.bean.Shop;

@Repository
public class BarberDao {
	@Autowired
	private  SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	/**
	 * 根据店铺查找对应的理发师 发型师
	 * @param shop
	 * @return
	 */
	public List<Barber> showBarberByShop(Shop shop) {
		Session session = this.getSession();
		Query<Barber> query = session.createQuery("from Barber where shop = ?");
		query.setParameter(0, shop);
		List<Barber> list = query.list();		
		return list;
		
	}

	//根据用户名和密码返回店员列表
	public List<Barber> getBarberListByMerchant(String account,String pwd){
		Merchant merchant = (Merchant) this.getSession()
				.createQuery("from Merchant where merchantAccount = ? and merchantPassword = ?")
				.setParameter(0, account).setParameter(1, pwd).uniqueResult();
		List<Barber> list = this.getSession()
				.createSQLQuery( " select * from barber where shop_id = ? " )
				.setParameter(0, merchant.getShop().getShopId())
				.addEntity(Barber.class )
				.list();
		return list;
	}
}
