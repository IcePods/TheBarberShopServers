package com.barbershop.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.barbershop.bean.Merchant;

@Repository
public class MerchantDao {
	@Autowired
	private  SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	//判断用户名是否已存在
	public Merchant queryMerchantByAccount(String account) {
		Session session = this.getSession();
		Merchant merchant = (Merchant) session.createQuery("from Merchant where merchantAccount = ?")
				.setParameter(0, account)
				.uniqueResult();
		return merchant;
	}
	//插入店铺
	public boolean insertMerchant(Merchant merchant) {
		Session session = this.getSession();
		boolean flag = false;
		try {
			session.save(merchant);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

}
