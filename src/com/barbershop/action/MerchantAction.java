package com.barbershop.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.barbershop.bean.Merchant;
import com.barbershop.service.MerchantService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class MerchantAction {
	@Autowired
	private MerchantService ms;
	
	//店铺登录  店铺输入
	@ResponseBody
	@RequestMapping(value = "/merchantRegister",method = RequestMethod.POST)
	public boolean register(HttpServletRequest request,HttpServletResponse response,HttpSession session,@RequestBody String data) {
		System.out.println("店铺注册-----------"+data);
		Gson gson = new GsonBuilder()
				.serializeNulls()
				.setPrettyPrinting()
				.create();
		Merchant merchant = gson.fromJson(data, Merchant.class);
		//用户名是否可用
		if(ms.isEmpty(merchant.getMerchantAccount())) {
			//用户名未使用，执行插入操作
			ms.insert(merchant);
			return true;
		}else {
			//用户名已注册 无法注册
			System.out.println("店铺已被注册-----------"+merchant.toString());
			return false;
		}
	}
	
}
