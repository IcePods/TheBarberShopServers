package com.barbershop.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.barbershop.bean.Dynamic;

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
		Query<Dynamic> query = session.createQuery("from Dynamic order by DynamicId desc");
		List<Dynamic> list = query.list();		
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
		tran.commit();
		return dynamic;
	}
} 
