package com.barbershop.action;

import java.io.FileOutputStream;
import java.io.IOException;

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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import sun.misc.BASE64Decoder;

@Controller
public class UsersAction {
	@Autowired
	private  UserService us;
	
	@RequestMapping(value = "/asc", method = RequestMethod.POST)
	public void  insert( @RequestBody String data ) throws IOException {
		System.out.println("测试数据");
		System.out.println(data);
		byte[] bs = new BASE64Decoder().decodeBuffer(data);
        // 写到D盘Img文件夹下的a.jpg文件。注：Img文件夹一定要存在
        FileOutputStream fos = new FileOutputStream("D:/Img/abc.png");
        fos.write(bs);
        fos.flush();
        fos.close();

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
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public Users register(HttpServletRequest request,HttpServletResponse response, @RequestBody String data) {
		System.out.println("注册验证"+data);
		Gson gson = new GsonBuilder()
				.serializeNulls()
				.setPrettyPrinting()
				.create();
		Users user = gson.fromJson(data, Users.class);
		if(us.isEmpty(user.getUserAccount())) {
			us.insert(user);
			return user;
		}else {
			return null;
		}
		
	}
	
}
