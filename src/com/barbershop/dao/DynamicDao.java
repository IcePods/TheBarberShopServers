package com.barbershop.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.barbershop.bean.Dynamic;
import com.barbershop.bean.Users;

@Repository
public class DynamicDao {
	@Autowired
	private  SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	/**
	 * 查询展示全部的动态信息 根据DynamicId 倒叙排列
	 * @return
	 */
	public List<Dynamic> showAllDynamic (){
		Session session = this.getSession();
		@SuppressWarnings("unchecked")
		List<Dynamic> list= session.createQuery("from Dynamic order by DynamicId desc")
								.list();	
		return list;		
	}
	/**
	 * 添加动态
	 * @param dynamic
	 * @return
	 */
	public Dynamic SaveDynamic(Dynamic dynamic) {
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
 
		session.save(dynamic);
		session.get(Dynamic.class,dynamic.getDynamicId());
		tran.commit();
		return dynamic;
	}
	
	/**
	 * 通过用户获取用户动态数据
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Dynamic> getDynamicByUser(Users user) {
		return this.getSession().createQuery("from Dynamic where user = ? order by DynamicId desc")
			.setParameter(0, user)
			.list();
	}
} 
