package com.barbershop.action;

import java.io.IOException;
import java.util.ArrayList;
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

import com.barbershop.bean.Collections;
import com.barbershop.bean.Dynamic;
import com.barbershop.bean.Shop;
import com.barbershop.bean.Users;
import com.barbershop.service.DynamicService;
import com.barbershop.service.UserService;
import com.barbershop.utils.UploadPictureUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
public class DynamicAction {
	@Autowired
	private DynamicService DService;
	@Autowired
	private  UserService us;
	private UploadPictureUtil uploadPicUtil = new UploadPictureUtil();
	
	/**
	 * 显示所有动态消息
	 * @return 动态列表
	 */
	@ResponseBody
	@RequestMapping(value = "/showAllDynamic", method = RequestMethod.POST)
	public List<Dynamic> getAllDynamic (){
		System.out.println("动态请求");
		List<Dynamic> DynamicList = DService.showAllDynamic();
		System.out.println("动态请求成功！");
		return DynamicList;
	}
	
	/**
	 * 发布新动态
	 * @param request 
	 * @param response
	 * @param session
	 * @param UserToken 用户token
	 * @param DynamicJson 动态对象的json串
	 * @return 成功返回该动态对象，失败返回空动态对象
	 */
	@ResponseBody
	@RequestMapping(value = "/createDynamic", method = RequestMethod.POST)
	public Dynamic SaveDynamic(HttpServletRequest request,HttpServletResponse response,HttpSession session,@RequestHeader(value="UserTokenSQL") String UserToken,@RequestBody String DynamicJson) {
		System.out.println("添加动态");
		//从session中获取Token
		String sessionUserToken = (String) session.getAttribute("SessionUserToken");
		Gson gson = new GsonBuilder()
				.serializeNulls()
				.setPrettyPrinting()
				.create();
		Dynamic dynamic = gson.fromJson(DynamicJson, Dynamic.class);
		Users user = findUserByToken(sessionUserToken,UserToken,session);
		if(user.getUserCondition()) {
			dynamic.setUser(user);
			dynamic = DService.SaveDynamic(dynamic);
			System.out.println("返回一个正确的的状态的Dynamic");
			return dynamic;
		}else {
			System.out.println("返回一个错的状态的Dynamic");
			return returnFalseDynamic();
		}
			
	}
	
	/**
	 * 返回用户发布的动态
	 * @param UserToken
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getUserDynamic", method=RequestMethod.POST)
	public List<Dynamic> getUserDynamic(HttpSession session, @RequestHeader(value="UserTokenSQL") String UserToken){
		List<Dynamic> list = new ArrayList<>();
		System.out.println("添加动态");
		//从session中获取Token
		String sessionUserToken = (String) session.getAttribute("SessionUserToken");
		Users user = findUserByToken(sessionUserToken,UserToken,session);
		list = DService.getDynamicByUser(user);
		return list;
	}
	
	/**
	 * 上传多张图片
	 * @param data 图片列表的json串
	 * @return 返回图片存储路径列表的json串
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadPictureList", method = RequestMethod.POST)
	public List<String> uploadPictureList( @RequestBody String data, HttpSession session, @RequestHeader(value="UserTokenSQL") String UserToken) throws IOException {
		//从session中获取Token
		String sessionUserToken = (String) session.getAttribute("SessionUserToken");
		Users user = findUserByToken(sessionUserToken,UserToken,session);
		
		//初始化文件目录
		uploadPicUtil.initUserFileDirectory(user.getUserAccount());
		
		//模拟多图上传
		List<String> picList = new ArrayList<String>();
		Gson gson = new GsonBuilder()
				.serializeNulls()
				.setPrettyPrinting()
				.create();
		picList = gson.fromJson(data, new TypeToken<List<String>>() {}.getType());
		List<String> picPath = uploadPicUtil.receiveUserDynamicPic(picList, user.getUserAccount());
		return picPath;
	}
	
	/**
	 * 生成一个错的状态的Dynamic
	 * @return
	 */
	private Dynamic returnFalseDynamic() {
		Dynamic falseDynamic = new Dynamic();
		falseDynamic.setDynamicCondition(false);
		return falseDynamic;
	}
	
	/**
	 * 根据token查询用户User
	 * @param token
	 * @return
	 */
	public Users findUserByToken(String sessionUserToken,String UserToken ,HttpSession session) {
		
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
			System.out.println("数据库中查User---Dynamic---114");
			if(us==null) {
				System.out.println("us 为空");
			}
			Users SQLUser2 = us.findUserByToken(UserToken);
			System.out.println("数据库中的token："+SQLUser2.getUserToken());
			System.out.println("前台传递的token："+UserToken);				
			//如果成功重新将User放入到session中
			session.setAttribute("SessionUser", SQLUser2);
			session.setAttribute("SessionUserToken", SQLUser2.getUserToken());
			//返回用户
			System.out.println("返回用户：：：：工具类：：：：：123");
			return SQLUser2;				
		}
		
	}
	
}
