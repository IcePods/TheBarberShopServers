package com.barbershop.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.barbershop.bean.Activity;
import com.barbershop.bean.Barber;
import com.barbershop.bean.Merchant;
import com.barbershop.bean.Users;
import com.barbershop.service.MerchantService;
import com.barbershop.utils.UploadPictureUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class MerchantAction {
	@Autowired
	private MerchantService ms;
	
	
	//店铺注册  店铺输入
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
			//初始化用户资源目录
			//new UploadPictureUtil().initMerchantFileDirectory(merchant.getMerchantAccount());
			return true;
		}else {
			//用户名已注册 无法注册
			System.out.println("店铺已被注册-----------"+merchant.toString());
			return false;
		}
	}
	
	/**
	 * 商家端登录
	 * @param request
	 * @param response
	 * @param session
	 * @param merchantJson
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/merchantLoginCheck", method = RequestMethod.POST)
	public Merchant loginCheck(HttpServletRequest request,HttpServletResponse response, HttpSession session,@RequestBody String merchantJson) {
		
		System.out.println("登录");
		//通过键值对的方式获取用户名和密码
		String account = request.getParameter("merchantAccount");
		String pwd = request.getParameter("merchantPassword");
		//如果用和户名为空 无法登陆
		if(ms.isEmpty(account)) {
			//无法登陆 返回一个MerchantCondition 状态 为False的对象
			System.out.println("80附近");
			Merchant falseMerchant = new Merchant();
			falseMerchant.setMerchantCondition(false);
			return falseMerchant;
		}else {
			//验证用户账号与密码是否匹配			
			Merchant merchant = (Merchant)ms.checkMerchantLogin(account, pwd);
			if(merchant!=null) {
				System.out.println("88附近");
				//获取当前时间 与用户账号密码创建时间 构成Token
				//获取当前创建时间 
				Date date = new Date();
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");		
				String AccountGreateTime = dateFormat.format(date).toString();
				//生成用户Token :用户账户 + 用户密码 + 创建时间
				String Token = merchant.getMerchantAccount()+merchant.getMerchantPassword()+AccountGreateTime;
				merchant.setMerchantToken(Token);
				//执行登录操作: 添加token
				ms.merchantLoginAddToken(merchant);
				//登录成功后将token 放入session 方便用户保持登录
				session.setAttribute("SessionMerchantToken", Token);
				session.setAttribute("SessionMerchant", merchant);
				System.out.println("102:"+merchant.getMerchantAccount()+":"+merchant.getMerchantToken());
				String sessionUserToken = (String) session.getAttribute("SessionMerchantToken");
				System.out.println("sessionMerchantToken::105::::"+sessionUserToken);
				return merchant;
			}else {
				System.out.println("用户合法");
				//如果验证的用户名和密码 为则更改用户合法状态 
				merchant = new Merchant();
				merchant.setMerchantCondition(false);
				return merchant;
			}
		}
	}

	/**
	 * 通过用户名密码获得店铺活动列表
	 */
	@ResponseBody
	@RequestMapping(value="/merchantGetMerchantActivity", method = RequestMethod.POST)
	public List<Activity> getActivityList(HttpServletRequest request,HttpServletResponse response,@RequestBody String merchantJson){
		System.out.println("活动列表");
		//通过键值对的方式获取用户名和密码
		String account = request.getParameter("merchantAccount");
		String pwd = request.getParameter("merchantPassword");
		List<Activity> list = ms.getActivityList(account, pwd);
		return list;
	}
	

	
	
}
