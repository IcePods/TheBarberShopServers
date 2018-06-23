package com.barbershop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barbershop.bean.Users;
import com.barbershop.dao.UserDao;


@Service
@Transactional
public class UserService {
	@Autowired
	private UserDao userDao;
	//注册用户 返回用户
	public Users insert(Users user) {
		 userDao.saveUsers(user);
		 return user;
	}
	
	//获取用户信息
	public Users queryUser(String account) {
		return (Users)userDao.queryUser(account);
	}
	
	//判断用户是否存在
	public boolean isEmpty(String account) {
		Users user = userDao.queryUser(account);
		if(user == null) {
			return true;
		} else {
			return false;
		}
	}
	//验证用户账户与密码是否匹配
	public Users checkLoginUser(String account,String pwd) {
		return userDao.checkLoginUser(account, pwd);
	}
	//登录操作 添加token
	public Users LoginAddToken(Users user) {
		return userDao.LoginAddToken(user);
	}
	//更新用户属性
	public Users UpdateUseAttribute(Users user) {
		return userDao.UpdateUseAttribute(user);
	}
	
	public Users findUserByToken(String token) {
		return userDao.findUserByToken(token);
	}
	
	public String findUserNameByUserAccount(String UserAccount) {
		return userDao.findUserNameByUserAccount(UserAccount);
	}
}
