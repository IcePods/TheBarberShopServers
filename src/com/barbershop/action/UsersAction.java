package com.barbershop.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	
	/**
	 * 登录验证
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
	public Users loginCheck(HttpServletRequest request,HttpServletResponse response,@RequestBody Users user) {
		String account = user.getUserAccount();
		String pwd = user.getUserPassword();
		if(us.isEmpty(account)) {
			return null;
		}else {
			Users mUser = (Users)us.queryUser(account);
			if(pwd.equals(mUser.getUserPassword())) {
				return mUser;
			}else {
				return null;
			}
		}
	}
	
	/**
	 * 注册
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(HttpServletRequest request,HttpServletResponse response,@RequestBody Users user) {
		String account = user.getUserAccount();
		if(us.isEmpty(account)) {
			us.insert(user);
			return "success";
		}else {
			return "failure";
		}
	}
	
}
