package com.barbershop.action;

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

import com.barbershop.bean.Appointment;
import com.barbershop.bean.Dynamic;
import com.barbershop.bean.Merchant;
import com.barbershop.bean.Shop;
import com.barbershop.bean.Users;
import com.barbershop.service.AppointmentService;
import com.barbershop.service.MerchantService;
import com.barbershop.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class AppointmentAction {
	@Autowired
	private AppointmentService AppointService;
	@Autowired
	private  UserService us;
	@Autowired
	private MerchantService ms;
	
	
	@RequestMapping(value = "/saveAppointment", method = RequestMethod.POST)
	@ResponseBody
	private Appointment saveAppointment(HttpServletRequest request,HttpServletResponse response,HttpSession session,@RequestHeader(value="UserTokenSQL") String UserToken, @RequestBody String AppJson) {
		System.out.println("生成预约"+AppJson);
		Gson gson = new GsonBuilder()
				.serializeNulls()
				.setPrettyPrinting()
				.create();
		//前台穿过来的Json 转成对象
		
		Appointment appoint = gson.fromJson(AppJson, Appointment.class);
		
		System.out.println("shopid:"+ appoint.getAppoint_userShopDetail().getShopId());
		//获取session中的Token
		String sessionUserToken = (String) session.getAttribute("SessionUserToken");
		Users user = findUserByToken(sessionUserToken,UserToken,session);
		if(user.getUserCondition()) {
			//将用户放入到 预约订单中
			System.out.println("将用户放入到 预约订单中");
			System.out.println("前台穿过来的Json 转成对象");
			System.out.println("预约时间:"+ appoint.getAppoint_time());
			appoint.setAppoint_users(user);
			appoint.setAppoint_state("进行中");
			Appointment app = AppointService.saveAppointment(appoint);
			return app;
		}else {
			System.out.println("token 登录失效错误 返回一个非法 Appointment");
			return returnFalseAppointment();
		}
		
	}

	@RequestMapping(value = "/showAllAppointment", method = RequestMethod.POST)
	@ResponseBody
	private List<Appointment> showAllAppointment(HttpServletRequest request,HttpServletResponse response,HttpSession session,@RequestHeader(value="UserTokenSQL") String UserToken) {
		System.out.println("根据用户展示预约信息");
		//获取订单状态信息
		
		String state = request.getParameter("Appointment_state");
		System.out.println("获取订单状态信息::展示预约信息:::"+state);
		String sessionUserToken = (String) session.getAttribute("SessionUserToken");
		Users user = findUserByToken(sessionUserToken,UserToken,session);
		if(state.equals("进行中")) {
			List<Appointment> list = AppointService.showAllAppointment(user,state);
			return list;
		}else{
			List<Appointment> list = AppointService.showNOUseAppointment(user, "进行中");
			return list;
		}
		
		
	}
	@RequestMapping(value = "/UpdateAppointmentState", method = RequestMethod.POST)
	@ResponseBody
	private Appointment UpdateAppointmentState(HttpServletRequest request,HttpServletResponse response,HttpSession session,@RequestHeader(value="UserTokenSQL") String UserToken) {
		System.out.println("更新预定状态");
		String state = request.getParameter("Appointment_state");
		String Id = request.getParameter("Appointment_id");	
		System.out.println("预约对象的ID " + Id);
		System.out.println("获取预定状态信息：：更新预定状态：：:"+state);
		String sessionUserToken = (String) session.getAttribute("SessionUserToken");
		Users user = findUserByToken(sessionUserToken,UserToken,session);
		if(state.equals("已完成")) {
			System.out.println("更新预定状态:::::已完成");
			int Appointment_id = Integer.parseInt(Id);
			Appointment app = AppointService.getAppointmentById(Appointment_id);
			app.setAppoint_state(state);
			app=  AppointService.updateAppointment(app);
			return app;
		}else{
			System.out.println("更新预定状态:::::已删除");
			int Appointment_id = Integer.parseInt(Id);
			Appointment app = AppointService.getAppointmentById(Appointment_id);
			app.setAppoint_state(state);
			app=  AppointService.updateAppointment(app);
			return app;
		}
		
	}
		
	/**
	 * 生成一个错的状态的Dynamic
	 * @return
	 */
	private Appointment returnFalseAppointment() {
		Appointment falseAppointment = new Appointment();
		falseAppointment.setAppointmentCondition(false);
		return falseAppointment;
	}
	/**
	 * 根据token查询用户User
	 * @param token
	 * @return
	 */
	private Users  findUserByToken(String sessionUserToken,String UserToken ,HttpSession session) {
		
		//如果session 中sessionUserToken 不为空 则判sessionUserToken是否等于客户端穿过来的Usertoken
		if(sessionUserToken!=null) {
			//则判sessionUserToken是否等于客户端穿过来的Usertoken
			if(sessionUserToken.equals(UserToken)) { //相等
				//从session中获取 之前登录是存的SessionUser
				Users sessionUser = (Users) session.getAttribute("SessionUser");
				//如果成功重新将User放入到session中
				System.out.println("如果成功重新将User放入到session中");
				session.setAttribute("SessionUser", sessionUser);
				session.setAttribute("SessionUserToken", sessionUserToken);
				return sessionUser;										
			}else {//如果 token不相等用户登陆失效 可能该用户再其他设备上登录过
				Users NoKeepUser = new Users();
				NoKeepUser.setUserCondition(false);
				return NoKeepUser;
			}
		}else {//如过为空则通过客户端的用户账号Account和密码pwd去数据库中查询
			System.out.println("数据库中查User---appointment---146");
			Users SQLUser2 = us.findUserByToken(UserToken);
			System.out.println("数据库中的token："+SQLUser2.getUserToken());
			System.out.println("前台传递的token："+UserToken);				
			//如果成功重新将User放入到session中
			session.setAttribute("SessionUser", SQLUser2);
			session.setAttribute("SessionUserToken", SQLUser2.getUserToken());
			//返回用户
			System.out.println("返回用户：：：：appointment：：：：：123");
			return SQLUser2;				
		}
	}
	/**
	 * 根据token查询店铺Merchant
	 * @param token
	 * @return
	 */
	private Merchant findMerchantByToken(String sessionMerchantToken,String MerchantToken ,HttpSession session) {
		
		//如果session 中sessionMerchantToken 不为空 则判sessionMerchantToken是否等于客户端穿过来的Merchanttoken
		if(sessionMerchantToken!=null) {
			//则判sessionMerchantToken是否等于客户端穿过来的Merchanttoken
			if(sessionMerchantToken.equals(MerchantToken)) { //相等
				//从session中获取 之前登录是存的SessionMerchant
				Merchant sessionMerchant = (Merchant) session.getAttribute("SessionMerchant");
				//如果成功重新将Merchant放入到session中
				System.out.println("如果成功重新将Merchant放入到session中");
				session.setAttribute("SessionMerchant", sessionMerchant);
				session.setAttribute("SessionMerchantToken", sessionMerchantToken);
				return sessionMerchant;										
			}else {//如果 token不相等用户登陆失效 可能该用户再其他设备上登录过
				Merchant NoKeepMerchant = new Merchant();
				NoKeepMerchant.setMerchantCondition(false);
				return NoKeepMerchant;
			}
		}else {//如过为空则通过客户端的用户账号Account和密码pwd去数据库中查询
			System.out.println("数据库中查Merchant---appointment---146");
			Merchant SQLMerchant2 = ms.findMerchantByToken(MerchantToken);
			System.out.println("数据库中的token："+SQLMerchant2.getMerchantToken());
			System.out.println("前台传递的token："+MerchantToken);				
			//如果成功重新将Merchant放入到session中
			session.setAttribute("SessionMerchant", SQLMerchant2);
			session.setAttribute("SessionMerchantToken", SQLMerchant2.getMerchantToken());
			//返回用户
			System.out.println("返回店铺：：：：appointment：：：：：123");
			return SQLMerchant2;				
		}
	}
	/**
	 * 商家获取预约列表
	 * @param request
	 * @param response
	 * @param session
	 * @param MerchantToken
	 * @return
	 */
	@RequestMapping(value = "/getAppointmentByMerchant", method = RequestMethod.POST)
	@ResponseBody
	private List<Appointment> showAllShopAppointment(HttpServletRequest request,HttpServletResponse response,HttpSession session,@RequestHeader(value="MerchantTokenSQL") String MerchantToken) {
		System.out.println("根据店铺展示预约信息");
		//获取订单状态信息
		String state = request.getParameter("Appointment_state");
		System.out.println("获取订单状态信息::展示预约信息:::"+state);
		String sessionMerchantToken = (String) session.getAttribute("SessionMerchantToken");
		Merchant merchant = findMerchantByToken(sessionMerchantToken,MerchantToken,session);
		Shop shop = merchant.getShop();
		if(state.equals("进行中")) {
			List<Appointment> list = AppointService.showAllMerchantAppointment(shop,state);
			return list;
		}else{
			List<Appointment> list = AppointService.showNOUseMerchantAppointment(shop, "进行中");
			return list;
		}
		
		
	}
}
