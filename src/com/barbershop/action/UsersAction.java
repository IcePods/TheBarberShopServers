package com.barbershop.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.barbershop.bean.Users;
import com.barbershop.service.UserService;

@Controller
public class UsersAction {
	@Autowired
	private  UserService us;
	
	@RequestMapping("/save")
	public void  insert() {
		Users user = new Users();
		user.setUserAccount("张三");
		user.setUserPassword("45545");		
		us.insert(user);
		 
	}
}
