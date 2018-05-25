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
	
	public void insert(Users user) {
		 userDao.saveUsers(user);
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
	
}
