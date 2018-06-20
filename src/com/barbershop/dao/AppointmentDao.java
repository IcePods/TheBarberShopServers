package com.barbershop.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.barbershop.bean.Appointment;
import com.barbershop.bean.Collections;
import com.barbershop.bean.Shop;
import com.barbershop.bean.Users;

@Repository
public class AppointmentDao {
	@Autowired
	private  SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	/**
	 *  生成与预约
	 * @param app
	 * @return
	 */
	public Appointment saveAppointment(Appointment app) {
		Session session = this.getSession();
		session.merge(app);		
		return app;		
	}
	/**
	 * 查看全部订单
	 * @param user
	 * @return
	 */
	public List<Appointment> showAllAppointment(Users user,String state){
		Session session = this.getSession();
		Query<Appointment> q = session.createQuery("from Appointment where Appoint_users =? and Appoint_state = ? order by Appoint_id desc");
		q.setParameter(0, user);
		q.setParameter(1, state);
		List <Appointment> list = q.list();
		return list;		
	}
	/**
	 * 展示无效订单
	 * @param user
	 * @param state
	 * @return
	 */
	public List<Appointment> showNOUseAppointment(Users user,String state){
		Session session = this.getSession();
		Query<Appointment> q = session.createQuery("from Appointment where Appoint_users =? and Appoint_state != ? order by Appoint_id desc");
		q.setParameter(0, user);
		q.setParameter(1, state);
		List <Appointment> list = q.list();
		return list;		
	}
	
	/**
	 * 根据预约id获取预约对象
	 * @param id
	 * @return
	 */
	public Appointment getAppointmentById(int id ) {
		Session session = this.getSession();
		Appointment app =  session.get(Appointment.class, id);
		return app;		
	}
	/**
	 * 更新用户
	 * @param appointment
	 * @return
	 */
	public Appointment updateAppointment(Appointment appointment) {
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		System.out.println("更新状态dao "+ appointment.getAppoint_state());
		session.update(appointment);
		tran.commit();
		return appointment;
	}
	/**
	 * 查看店铺全部订单
	 * @param user
	 * @return
	 */
	public List<Appointment> showAllShopAppointment(Shop shop,String state){
		Session session = this.getSession();
		Query<Appointment> q = session.createQuery("from Appointment where Appoint_userShopDetail =? and Appoint_state = ? order by Appoint_id desc");
		q.setParameter(0, shop);
		q.setParameter(1, state);
		List <Appointment> list = q.list();
		return list;		
	}
	/**
	 * 展示店铺已完成订单
	 * @param user
	 * @param state
	 * @return
	 */
	public List<Appointment> showNOUseShopAppointment(Shop shop,String state){
		Session session = this.getSession();
		Query<Appointment> q = session.createQuery("from Appointment where Appoint_userShopDetail =? and Appoint_state != ? order by Appoint_id desc");
		q.setParameter(0, shop);
		q.setParameter(1, state);
		List <Appointment> list = q.list();
		return list;		
	}
}
