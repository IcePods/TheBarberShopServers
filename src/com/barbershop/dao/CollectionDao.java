package com.barbershop.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.barbershop.bean.Shop;
import com.barbershop.bean.Users;

@Repository
public class CollectionDao {
	@Autowired
	private  SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public Boolean saveCollection(Users user,Shop shop) {
		
		Session session = this.getSession();
		
		return null;
		
	}
}
