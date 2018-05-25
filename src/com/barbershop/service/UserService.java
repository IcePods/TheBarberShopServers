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

}
