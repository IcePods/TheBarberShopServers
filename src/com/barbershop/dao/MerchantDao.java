package com.barbershop.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.barbershop.bean.Merchant;

@Repository
public class MerchantDao {
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	// 判断用户名是否已存在
	public Merchant queryMerchantByAccount(String account) {
		Session session = this.getSession();
		Merchant merchant = (Merchant) session.createQuery("from Merchant where merchantAccount = ?")
				.setParameter(0, account).uniqueResult();
		return merchant;
	}

	// 插入店铺
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

	// 验证用户账户和密码
	public Merchant checkMerchantLogin(String account, String pwd) {
		Merchant merchant = (Merchant) this.getSession()
				.createQuery("from Merchant where merchantAccount = ? and merchantPassword = ?")
				.setParameter(0, account).setParameter(1, pwd).uniqueResult();
		return merchant;
	}

	// 如果用户账户和密码 匹配成功则添加登录Token
	public Merchant merchantLoginAddToken(Merchant merchant) {
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		session.update(merchant);
		tran.commit();
		return merchant;
	}

}