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

	//注册用户
	public Users saveUsers(Users user) {		
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
 
		session.save(user);
		tran.commit();
		return user;
	}
	//判断用户名是否已经存在
	public Users queryUser(String account) {
		Users user = (Users)this.getSession()
				.createQuery("from Users where UserAccount = ?")
				.setParameter(0, account)
				.uniqueResult();
		return user;
	}
	//验证用户账户和密码
	public Users checkLoginUser(String account,String pwd) {
		Users user = (Users)this.getSession()
				.createQuery("from Users where UserAccount = ? and UserPassword = ?")
				.setParameter(0, account)
				.setParameter(1, pwd)
				.uniqueResult();
		return user;
	}
	//如果用户账户和密码 匹配成功则添加登录Token
	public Users LoginAddToken(Users user) {
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		session.update(user);
		tran.commit();
		return user;		
	}
	//更新用户属性
	public Users UpdateUseAttribute(Users user) {
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		session.update(user);
		tran.commit();
		return user;	
	}
	/**
	 * 根据token返回用户
	 * @param token
	 * @return
	 */
	public Users findUserByToken(String token) {
		System.out.println("token"+ token);
		Users user = (Users)this.getSession()
				.createQuery("from Users where UserToken=?")
				.setParameter(0, token)
				.uniqueResult();
		if(user==null) {
			System.out.println("user 为空");
		}else {
			System.out.println("usre 不为空");
		}
		return user;
		
	}
	/**
	 * 根据用户Account 查询用户昵称
	 * @param UserAccount
	 * @return
	 */

	public String findUserNameByUserAccount(String userAccount) {
		return (String)this.getSession()
				.createQuery("select UserName from Users where UserAccount = ?")
				.setParameter(0, userAccount)
				.uniqueResult();
	}
}
