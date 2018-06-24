package com.barbershop.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

import com.barbershop.bean.Barber;
import com.barbershop.bean.Collections;
import com.barbershop.bean.Merchant;
import com.barbershop.bean.Shop;
import com.barbershop.bean.Users;
import com.barbershop.service.BarberService;
import com.barbershop.service.CollectionService;
import com.barbershop.service.MerchantService;
import com.barbershop.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class BarberAction {
	@Autowired
	private BarberService barberService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private UserService userService;
	@Autowired
	private CollectionService collectionService;
	
	/**
	 * 通过店铺查看理发师
	 * @param request
	 * @param response
	 * @param session
	 * @param ShopJson
	 * @return
	 */
	@RequestMapping(value = "/showBarberByShop", method = RequestMethod.POST)
	@ResponseBody
	public List<Barber> showBarberByShop(HttpServletRequest request,HttpServletResponse response,HttpSession session,@RequestBody String ShopJson) {
		System.out.println("通过店铺查看理发师");
		Gson gson = new GsonBuilder()
				.serializeNulls()
				.setPrettyPrinting()
				.create();
		Shop shop = gson.fromJson(ShopJson, Shop.class);
		List<Barber> list = barberService.showBarberByShop(shop);
		return list;
		
	}
	
	/**
	 * 通过用户名密码获得店铺店员列表
	 */
	@ResponseBody
	@RequestMapping(value="/getBarberByMerchant", method = RequestMethod.POST)
	public List<Barber> getBarberListByMerchant(@RequestBody String merchantJson, @RequestHeader(value="MerchantToken") String token){
		Merchant merchant = merchantService.getMerchantByToken(token);
		List<Barber> list = barberService.getBarberListByMerchant(merchant.getShop().getShopId());
		
		System.out.println("理发师个数"+list.size());
		return list;
	}
	/**
	 * 通过id删除理发师   没有判断登录状态
	 */
	@ResponseBody
	@RequestMapping(value="/deleteBarber", method = RequestMethod.POST)
	public void deleteBarberByID(HttpServletRequest request,HttpServletResponse response,@RequestBody String barberJson){
		System.out.println("删除店员");
		//通过键值对的方式获取店员ID
		String barberId = request.getParameter("barberId");
		barberService.deleteBarberByID(Integer.parseInt(barberId));
	}
	/**
	 * 通过用户的账号密码给店铺添加理发师
	 */
	@ResponseBody
	@RequestMapping(value="/addBarber",method = RequestMethod.POST)
	public String  addBarber(HttpServletRequest request,HttpServletResponse response,HttpSession session,@RequestBody String barberJson) {
		System.out.println("添加理发师");
		String account = request.getParameter("UserAccount");
		String password = request.getParameter("UserPassword");
		String merchantaccount = request.getParameter("merchantAccount");//店铺账号
		String merchantpassword = request.getParameter("merchantPassword");
		//判断理发师的用户是否存在
		Users user = userService.checkLoginUser(account, password);
		//判断用户是否存在
		if(user != null) {
			//判断用户的收藏 是否存在收藏对象 如果没有收藏可以添加 否则不允许添加
			List<Collections> list = collectionService.findListCollectionsByUser(user);
			
			if(list.size()==0) {
				//判断用户是否在其他店铺存在 true表示 用户可添加 其他店铺不存在
				if(barberService.JudgeUserAlreadyExistsInBarber(user.getUserId())) {
					Merchant m = merchantService.checkMerchantLogin(merchantaccount, merchantpassword);
					boolean result = barberService.addBarber(account,password,m.getMerchantId());
					if(result) {
						//添加成功
						System.out.println("理发师添加成功");
						return "success";
					}else {
						//添加失败
						System.out.println("理发师添加失败");
						return "AddFalse";
					}
				}else {//返回用户不可添加 已经存在在其他店铺中
					System.out.println("返回用户不可添加 已经存在在其他店铺中");
					return "AlreadyExistsInBarber";
				}
			}else {
				System.out.println("用户存在收藏 ");
				return "userExistsCollection";
			}
			
			
		}else {
			//理发师的用户或密码错误
			System.out.println("理发师的用户或密码错误");
			return "AccountOrPassWordFalse";
		}
	}

	
	/**
	 * 通过用户的账号密码给店铺添加理发师
	 *//*
	@ResponseBody
	@RequestMapping(value="/addBarber",method = RequestMethod.POST)
	public boolean addBarber(HttpServletRequest request,HttpServletResponse response,HttpSession session,@RequestBody String barberJson,@RequestHeader(value="MerchantTokenSQL") String MerchantToken) {
		System.out.println("添加理发师");
		String sessionMerchantToken = (String) session.getAttribute("SessionMerchantToken");
		System.out.println("SessionMerchantToken::BarberAction:::80:"+sessionMerchantToken);
		String account = request.getParameter("UserAccount");
		String password = request.getParameter("UserPassword");
		String merchantaccount = request.getParameter("merchantAccount");//店铺账号
		String merchantpassword = request.getParameter("merchantPassword");
		
		//判断用户的登录状态
		Merchant merchant  = JudgeMerchantIsKeepLogin(merchantaccount, merchantpassword, sessionMerchantToken, MerchantToken, session);
		if(merchant.getMerchantCondition()) {
			System.out.println("店铺添加理发师，merchantaccount："+merchantaccount);
			Merchant m = merchantService.checkMerchantLogin(merchantaccount, merchantpassword);
			boolean result = barberService.addBarber(account,password,m.getMerchantId());
			if(result) {
				//添加成功
				System.out.println("理发师添加成功");
				return true;
			}else {
				//添加失败
				System.out.println("理发师添加失败");
				return false;
			}
		}else{
			System.out.println("店铺添加理发师:::::店铺登录失效::::返回无效");
			return false;
		}
	}*/

	/**
	 * 判断店铺是否是登录状态
	 * @param account
	 * @param pwd
	 * @param sessionMerchantToken
	 * @param MerchantToken
	 * @param session
	 * @return
	 */
	private Merchant JudgeMerchantIsKeepLogin(String account,String pwd,String sessionMerchantToken,String MerchantToken ,HttpSession session) {
		//如果session 中sessionMerchantToken 不为空 则判sessionMerchantToken是否等于客户端传过来的Merchanttoken
		if(sessionMerchantToken!=null) {
			//则判sessionMerchantToken是否等于客户端穿过来的Merchanttoken
			if(sessionMerchantToken.equals(MerchantToken)) { //相等
				//从session中获取 之前登录是存的SessionMerchant
				Merchant sessionMerchant = (Merchant) session.getAttribute("SessionMerchant");
				//判断Merchant是否相同
				if(sessionMerchant.getMerchantAccount()==account && sessionMerchant.getMerchantPassword()==pwd) {
					System.out.println("session中的Merchant");
					//如果成功重新将Merchant放入到session中
					session.setAttribute("SessionMerchant", sessionMerchant);
					session.setAttribute("SessionMerchantToken", sessionMerchantToken);
					return sessionMerchant;
				}else {//如果不同通过客户端的用户账号Account和密码pwd去数据库中查询
					System.out.println("数据库中查Merchant------133");
					Merchant SQLMerchant1 = merchantService.checkMerchantLogin(account, pwd); 
					//如果成功重新将Merchant放入到session中
					session.setAttribute("SessionMerhcant", SQLMerchant1);
					session.setAttribute("SessionMerchantToken", SQLMerchant1.getMerchantToken());
					return SQLMerchant1;
				}
			}else {//如果 token不相等用户登陆失效 可能该用户再其他设备上登录过
				Merchant NoKeepMerchant = new Merchant();
				NoKeepMerchant.setMerchantCondition(false);
				return NoKeepMerchant;
			}
		}else {//如果为空则通过客户端的用户账号Account和密码pwd去数据库中查询
			System.out.println("数据库中查Merchant------146");
			Merchant SQLMerchant2 = merchantService.checkMerchantLogin(account, pwd); 
			System.out.println("数据库中的token："+SQLMerchant2.getMerchantToken());
			System.out.println("前台传递的token："+MerchantToken);
			if(SQLMerchant2.getMerchantToken().equals(MerchantToken)) {
				//如果成功重新将Merchant放入到session中
				session.setAttribute("SessionMerchant", SQLMerchant2);
				session.setAttribute("SessionMerchantToken", SQLMerchant2.getMerchantToken());
				//返回店铺
				System.out.println("返回店铺：：：：：：：：：159");
				return SQLMerchant2;
			}else {
				System.out.println("查询错误：：：：：163");
				Merchant NoKeepMerchant2 = new Merchant();
				NoKeepMerchant2.setMerchantCondition(false);
				return NoKeepMerchant2;
			}
		}
	}
}
