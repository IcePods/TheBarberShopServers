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
import com.barbershop.utils.userPictureUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
public class DynamicAction {
	@Autowired
	private DynamicService DService;
	@Autowired
	private  UserService us;
	private userPictureUtil userPicUtil;
	
	@ResponseBody
	@RequestMapping(value = "/showAllDynamic", method = RequestMethod.GET)
	public List<Dynamic> showAllDynamic (){
		System.out.println("动态请求");
		List<Dynamic> DynamicList = DService.showAllDynamic();
		System.out.println("动态请求成功！");
		return DynamicList;
	}
	@ResponseBody
	@RequestMapping(value = "/SaveDynamic", method = RequestMethod.POST)
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
	 * 上传多张图片
	 * @param data 图片列表的json串
	 * @return 返回图片存储路径列表的json串
	 * @throws IOException
	 */
	@ResponseBody()
	@RequestMapping(value = "/uploadPictureList", method = RequestMethod.POST)
	public List<String> uploadPictureList( @RequestBody String data ) throws IOException {
		System.out.println("上传多图");
		
		//初始化文件目录
		userPicUtil = new userPictureUtil();
		userPicUtil.initUserFileDirectory("userAccount");
		//模拟多图上传
		List<String> picList = new ArrayList<String>();
		Gson gson = new GsonBuilder()
				.serializeNulls()
				.setPrettyPrinting()
				.create();
		picList = gson.fromJson(data, new TypeToken<List<String>>() {}.getType());
		List<String> picPath = userPicUtil.receiveDynamicPic(picList);
		for(int i=0;i<picPath.size();i++) {
			System.out.println(picPath.get(i));
		}
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
			System.out.println("数据库中查User---Dynamic---114");
			Users SQLUser2 = us.findUserByToken(UserToken);
			System.out.println("数据库中的token："+SQLUser2.getUserToken());
			System.out.println("前台传递的token："+UserToken);				
			//如果成功重新将User放入到session中
			session.setAttribute("SessionUser", SQLUser2);
			session.setAttribute("SessionUserToken", SQLUser2.getUserToken());
			//返回用户
			System.out.println("返回用户：：：：Dynamic：：：：：123");
			return SQLUser2;				
		}
		
	}
	
}
