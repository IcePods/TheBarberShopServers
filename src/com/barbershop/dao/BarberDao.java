package com.barbershop.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.barbershop.bean.Barber;
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
}
