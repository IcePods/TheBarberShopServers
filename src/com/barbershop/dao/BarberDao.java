package com.barbershop.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
		@SuppressWarnings("unchecked")
		List<Barber> list = this.getSession()
				.createQuery("from Barber where shop = ?")
				.setParameter(0, shop)
				.list();		
		return list;
		
	}

	//根据ID删除理发师
	public boolean deleteBarberByID(int barberId) {
		boolean flag = false;
		try {
			this.getSession()
			.createQuery("delete from Barber where barberId=? ")
			.setParameter(0, barberId)
			.executeUpdate();
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
		barber.setBarberName(user.getUserName());
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
	//判断用户是否在其他店铺存在 true表示 用户可添加 其他店铺不存在
	public Boolean JudgeUserAlreadyExistsInBarber(int id) {
		Session session = this.getSession();
		
		Barber barber = session.get(Barber.class, id);
		
		if(barber==null) {
			return true;
		}else {
			return false;
		}			
	}
 }
