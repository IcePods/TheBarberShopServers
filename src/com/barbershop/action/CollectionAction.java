package com.barbershop.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.barbershop.bean.Collections;
import com.barbershop.bean.Shop;
import com.barbershop.bean.Users;
import com.barbershop.service.CollectionService;
import com.barbershop.service.ShopService;
import com.barbershop.service.UserService;

@Controller
public class CollectionAction {
	@Autowired
	private CollectionService cService;
	@Autowired
	private  UserService us;
	@Autowired
	private ShopService shopservice;
	
	@RequestMapping(value = "/CheckIsCollected", method = RequestMethod.POST)
	@ResponseBody
	public Collections  CheckIsCollected(HttpServletRequest request,HttpServletResponse response,HttpSession session,@RequestHeader(value="UserTokenSQL") String UserToken) {
		System.out.println("检查判断店铺是否被收藏");
		//从session中获取Token
		String sessionUserToken = (String) session.getAttribute("SessionUserToken");
		System.out.println("sessionUserToken::CollectionAction:::45:::"+sessionUserToken);
		String account = request.getParameter("UserAccount");
		String pwd = request.getParameter("UserPassword");
		//获取店铺id 
		String Id = request.getParameter("shopId");		
		 //判断token 获取用户 用户为 true时继续判断 否则返回一个 非法的collection；
		Users user  = JudgeUserIsKeepLogin(account, pwd, sessionUserToken, UserToken, session);
		if(user.getUserCondition()) {
			//店铺Id转换成Int类型 通过ShopID 获取对应店铺
			System.out.println("检查判断店铺是否被收藏:::::用户可用已登录::::判断店铺是否被收藏");
			System.out.println("Id::::::::"+Id);
			int Shopid = Integer.parseInt(Id);
			Shop shop = shopservice.getShopByShopId(Shopid);
			Collections collections = cService.CheckIsCollected(user, shop);
				if(collections!=null && collections.getCollectionCondition()) {
					System.out.println("检查判断店铺是否被收藏:::::用户可用已登录::::判断店铺是否被收藏:::已经收藏 返回正确收藏");
					return collections;
				}else{
					System.out.println("检查判断店铺是否被收藏:::::用户可用已登录::::判断店铺是否被收藏:::为收藏 返回错误收藏");
					collections = new Collections();
					collections.setCollectionCondition(false);
					return collections;
				}
		}else {
			System.out.println("检查判断店铺是否被收藏:::::用户登录失效::::返回无效");
			Collections noUserCollections = new Collections();
			noUserCollections.setCollectionCondition(false);
			return noUserCollections;
		}		
		
	}
	/**
	 * 用户点击收藏店铺
	 * @param request
	 * @param response
	 * @param session
	 * @param UserToken
	 * @return
	 */
	@RequestMapping(value = "/SaveOrDeleteCollection", method = RequestMethod.POST)
	@ResponseBody
	public Collections  SaveOrDeleteCollection(HttpServletRequest request,HttpServletResponse response,HttpSession session,@RequestHeader(value="UserTokenSQL") String UserToken) {
		System.out.println("用户点击收藏店铺");
		//从session中获取Token
		String sessionUserToken = (String) session.getAttribute("SessionUserToken");
		System.out.println("sessionUserToken::CollectionAction:::80:"+sessionUserToken);
		String account = request.getParameter("UserAccount");
		String pwd = request.getParameter("UserPassword");
		//获取店铺id 
		String Id = request.getParameter("shopId");		
		 //判断token 获取用户 用户为 true时继续判断 否则返回一个 非法的collection；
		Users user  = JudgeUserIsKeepLogin(account, pwd, sessionUserToken, UserToken, session);
		if(user.getUserCondition()) {
			//店铺Id转换成Int类型 通过ShopID 获取对应店铺
			System.out.println("用户点击收藏店铺:::::用户可用已登录::::添加收藏");
			System.out.println("Id::::::::"+Id);
			int Shopid = Integer.parseInt(Id);
			Shop shop = shopservice.getShopByShopId(Shopid);
			//判断用户是否已经收藏用户
			Collections collections  = cService.CheckIsCollected(user, shop);
			if(collections!=null) {//如果不为空表示已经收藏 删除收藏
				System.out.println("::::104行");
				cService.deleteCollection(user, shop);
				//删除返回收藏false 表示已经删除 改变图片有 深色改成浅色
				return returnFalseCollections();
			}else {//如果为空表示还未收藏 保存收藏
				System.out.println("::::108行");
				return cService.saveCollection(user, shop);
			}
				
		}else {
			System.out.println("用户点击收藏店铺:::::用户登录失效::::返回无效");
			
			return returnFalseCollections();
		}
		
	}
	/**
	 * 用户查找收藏店铺
	 * @param request
	 * @param response
	 * @param session
	 * @param UserToken
	 * @return
	 */
	@RequestMapping(value = "/findCollectionByUser", method = RequestMethod.POST)
	@ResponseBody
	public List<Collections>  findCollectionByUser(HttpServletRequest request,HttpServletResponse response,HttpSession session,@RequestHeader(value="UserTokenSQL") String UserToken) {
		System.out.println("用户查找收藏店铺");
		//从session中获取Token
		String sessionUserToken = (String) session.getAttribute("SessionUserToken");
		System.out.println("sessionUserToken::CollectionAction:::126:::"+sessionUserToken);
		String account = request.getParameter("UserAccount");
		String pwd = request.getParameter("UserPassword");
		 //判断token 获取用户 用户为 true时继续判断 否则返回一个 非法的collection；
		Users user  = JudgeUserIsKeepLogin(account, pwd, sessionUserToken, UserToken, session);
		List<Collections> list = cService.findListCollectionsByUser(user);
		return list;
		
	}
	
	/**
	 * 生成一个condition为false的collection对象
	 * @return
	 */
	private Collections returnFalseCollections() {
		Collections falseCollections = new Collections();
		falseCollections.setCollectionCondition(false);
		return falseCollections;
	}
	/**
	 * 判断用户是否被是登录状态 
	 * 验证token
	 * @param account
	 * @param pwd
	 * @param sessionUserToken
	 * @param UserToken
	 * @param session
	 * @return
	 */
	private Users JudgeUserIsKeepLogin(String account,String pwd,String sessionUserToken,String UserToken ,HttpSession session) {
		//如果session 中sessionUserToken 不为空 则判sessionUserToken是否等于客户端穿过来的Usertoken
		if(sessionUserToken!=null) {
			//则判sessionUserToken是否等于客户端穿过来的Usertoken
			if(sessionUserToken.equals(UserToken)) { //相等
				//从session中获取 之前登录是存的SessionUser
				Users sessionUser = (Users) session.getAttribute("SessionUser");
				//判断User是否相同
				if(sessionUser.getUserAccount()==account && sessionUser.getUserPassword()==pwd) {
					System.out.println("session中的User");
					//如果成功重新将User放入到session中
					session.setAttribute("SessionUser", sessionUser);
					session.setAttribute("SessionUserToken", sessionUserToken);
					return sessionUser;
				}else {//如果不同通过客户端的用户账号Account和密码pwd去数据库中查询
					System.out.println("数据库中查User------133");
					Users SQLUser1 = us.checkLoginUser(account, pwd); 
					//如果成功重新将User放入到session中
					session.setAttribute("SessionUser", SQLUser1);
					session.setAttribute("SessionUserToken", SQLUser1.getUserToken());
					return SQLUser1;
				}
			}else {//如果 token不相等用户登陆失效 可能该用户再其他设备上登录过
				Users NoKeepUser = new Users();
				NoKeepUser.setUserCondition(false);
				return NoKeepUser;
			}
		}else {//如过为空则通过客户端的用户账号Account和密码pwd去数据库中查询
			System.out.println("数据库中查User------146");
			Users SQLUser2 = us.checkLoginUser(account, pwd); 
			System.out.println("数据库中的token："+SQLUser2.getUserToken());
			System.out.println("前台传递的token："+UserToken);
			if(SQLUser2.getUserToken().equals(UserToken)) {
				//如果成功重新将User放入到session中
				session.setAttribute("SessionUser", SQLUser2);
				session.setAttribute("SessionUserToken", SQLUser2.getUserToken());
				//返回用户
				System.out.println("返回用户：：：：：：：：：159");
				return SQLUser2;
			}else {
				System.out.println("查询错误：：：：：163");
				Users NoKeepUser2 = new Users();
				NoKeepUser2.setUserCondition(false);
				return NoKeepUser2;
			}
		}
	}
}
