package com.barbershop.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.barbershop.bean.Barber;
import com.barbershop.bean.Merchant;
import com.barbershop.bean.Shop;
import com.barbershop.bean.Users;

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
	//根据ID删除理发师
	public boolean deleteBarberByID(int barberId) {
		Session session = this.getSession();
		Query q = session.createQuery("delete from Barber where barberId=? ");
		q.setParameter(0, barberId);
		boolean flag = false;
		try {
			q.executeUpdate();
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	//通过店铺ID添加对应的理发师
	public boolean addBarber(String account, String password, int merchantId) {
		Session session = this.getSession();
		Users user = (Users) session
				.createQuery("from Users where UserAccount = ? and UserPassword = ?")
				.setParameter(0, account).setParameter(1, password).uniqueResult();
		Merchant merchant = session.get(Merchant.class, merchantId);
		Shop shop = merchant.getShop();
		Barber barber = new Barber();
		//barber与user设置关联
		barber.setUser(user);
		shop.getBarberSet().add(barber);
		boolean flag = false;
		try {
			session.update(shop);
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
}
