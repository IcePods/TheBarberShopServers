package com.barbershop.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.barbershop.bean.Users;


@Repository
public class UserDao {
	@Autowired
	private  SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	
	public void saveUsers(Users user) {		
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		session.save(user);
		tran.commit();
	}
	
	public Users queryUser(String account) {
		Users user = (Users)this.getSession()
				.createQuery("from Users where UserAccount = ?")
				.setParameter(0, account)
				.uniqueResult();
		return user;
	}
}
