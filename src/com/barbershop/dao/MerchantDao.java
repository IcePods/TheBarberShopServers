package com.barbershop.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.barbershop.bean.Activity;
import com.barbershop.bean.Barber;
import com.barbershop.bean.Merchant;
import com.barbershop.bean.Shop;

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

	// 通过用户账户和密码返回Merchant对象
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

	/**
	 * 根据Token返回店铺
	 * @return
	 */
	public Merchant findMerchantByToken(String token) {
		System.out.println("token"+ token);
		Merchant merchant = (Merchant)this.getSession()
				.createQuery("from Merchant where merchantToken=?")
				.setParameter(0, token)
				.uniqueResult();
		if(merchant==null) {
			System.out.println("merchant 为空");
		}else {
			System.out.println("merchant 不为空");
		}
		return merchant;
	}
	
	//根据用户名和密码返回活动列表
	public List<Activity> getActivityList(String account,String pwd){
		Merchant merchant = (Merchant) this.getSession()
				.createQuery("from Merchant where merchantAccount = ? and merchantPassword = ?")
				.setParameter(0, account).setParameter(1, pwd).uniqueResult();
		List<Activity> list = this.getSession()
				.createSQLQuery( " select * from activity where shop_id = ? " )
				.setParameter(0, merchant.getShop().getShopId())
				.addEntity(Activity.class )
				.list();
		return list;
	}
	
	/**
	 * 通过token获取店铺对象
	 * @param token
	 * @return
	 */
	public Merchant getMerchantByToken(String token) {
		return (Merchant) this.getSession().createQuery("from Merchant where merchantToken = ?")
				.setParameter(0, token)
				.uniqueResult();
	}

}
