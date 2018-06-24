package com.barbershop.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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

import com.barbershop.bean.Users;
import com.barbershop.service.UserService;
import com.barbershop.utils.UploadPictureUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class UsersAction {
	@Autowired
	private  UserService us;
	
	private UploadPictureUtil uploadPicUtil = new UploadPictureUtil();
	
	/**
	 * 上传头像的请求处理方法，未完善，需要命名的格式
	 * @param data 上传的图片字符串
	 */
	@ResponseBody
	@RequestMapping(value="/uploadHead", method = RequestMethod.POST)
	public String uploadUserHeadPicture(@RequestBody String pic, HttpSession session, @RequestHeader(value="UserTokenSQL") String UserToken) {
		//从session中获取Token
		String sessionUserToken = (String) session.getAttribute("SessionUserToken");
		Users user = findUserByToken(sessionUserToken,UserToken,session);
		uploadPicUtil.initUserFileDirectory(user.getUserAccount());
		String userHeadPath = uploadPicUtil.receiveUserHeadPic(pic, user);
		user.setUserHeader(userHeadPath);
		us.UpdateUseAttribute(user);
		System.out.println("李垚"+userHeadPath);
		
		return userHeadPath;	
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
	public Users loginCheck(HttpServletRequest request,HttpServletResponse response, HttpSession session,@RequestBody String userJson) {
		//使用Json串传递用户账户 与密码 
		/*Gson gson = new GsonBuilder()
				.serializeNulls()
				.setPrettyPrinting()
				.create();		
		Users user = gson.fromJson(userJson, Users.class);
		String account = user.getUserAccount();
		String pwd = user.getUserPassword();*/
		System.out.println("登录");
		//通过键值对的方式获取用户名和密码
		String account = request.getParameter("UserAccount");
		String pwd = request.getParameter("UserPassword");
		//如果用和户名为空 无法登陆
		if(us.isEmpty(account)) {
			//无法登陆 返回一个UserCondition 状态 为False的对象
			System.out.println("用户名不存在 返回不合法用户：：：：：：：81附近");
			Users FalseUser = new Users();
			FalseUser.setUserCondition(false);
			return FalseUser;
		}else {
			//验证用户账号与密码是否匹配			
			Users mUser = (Users)us.checkLoginUser(account, pwd);
			if(mUser!=null) {
				System.out.println("用户登陆用户账号与密码是否匹配：：：88附近");
				//获取当前时间 与用户账号密码创建时间 构成Token
				//获取当前创建时间 
				Date date = new Date();
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");		
				String AccountGreateTime = dateFormat.format(date).toString();
				//生成用户Token :用户账户 + 用户密码 + 创建时间
				String Token = mUser.getUserAccount()+mUser.getUserPassword()+AccountGreateTime;
				mUser.setUserToken(Token);
				//执行登录操作: 添加token
				us.LoginAddToken(mUser);
				//登录成功后将token 放入session 方便用户保持登录
				session.setAttribute("SessionUserToken", Token);
				session.setAttribute("SessionUser", mUser);
				System.out.println("session中的User::::::::::"+mUser.getUserAccount()+":"+mUser.getUserToken());
				String sessionUserToken = (String) session.getAttribute("SessionUserToken");
				System.out.println("sessionUserToken::105::::"+sessionUserToken);
				//PushExample.SendForUserPush();
				return mUser;
			}else {
				System.out.println("用户登陆不合::::::法 109附近");
				//如果验证的用户名和密码 为则更改用户合法状态 
				mUser = new Users();
				mUser.setUserCondition(false);
				return mUser;
			}
		}
	}
	/**
	 * 根据全台传递的用户名和密码  查找数据库中的正确的User;
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/FindTokenByAccountAndPwd", method = RequestMethod.POST)
	@ResponseBody
	public Users FindTokenByAccountAndPwd(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		System.out.println("查找token");
		String Account = request.getParameter("UserAccount");
		String Pwd = request.getParameter("UserPassword");
		Users user = us.checkLoginUser(Account, Pwd); 
		return user;
	}
	/**
	 * 维持登录状态
	 */
	
	@RequestMapping(value = "/keepLogin", method = RequestMethod.POST)
	@ResponseBody
	public Users KeepLogin(HttpServletRequest request,HttpServletResponse response,HttpSession session,@RequestHeader(value="UserTokenSQL") String UserToken) {
		System.out.println("保持登录:头信息中的token:"+UserToken);
		String sessionUserToken = (String) session.getAttribute("SessionUserToken");
		System.out.println("sessionUserToken::123::::"+sessionUserToken);
		String account = request.getParameter("UserAccount");
		String pwd = request.getParameter("UserPassword");
		System.out.println("account:"+account+"  "+"pwd:"+pwd);
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
			}else {//如果不相等
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
	
	/**
	 * 更改用户属性信息 （性别、昵称、电话）
	 * @param request
	 * @param response
	 * @param session
	 * @param UserToken
	 * @param userJson
	 * @return
	 */
	@RequestMapping(value = "/changeUserAttribute", method = RequestMethod.POST)
	@ResponseBody
	public Users ChangeUserAttributeeepLogin(HttpServletRequest request,HttpServletResponse response,HttpSession session,@RequestHeader(value="UserTokenSQL") String UserToken) {
		System.out.println("更新用户属性信息");
		String userName = request.getParameter("userName");
		String userSex= request.getParameter("userSex");
		String userPhone = request.getParameter("userPhone");
	
		String sessionUserToken = (String) session.getAttribute("SessionUserToken");
		//通过token查找用户
		Users user = findUserByToken(sessionUserToken,UserToken,session);
		
		if(userName!=null) {
			System.out.println("更新用户名"+userName);
			user.setUserName(userName);
			user = us.UpdateUseAttribute(user);
			return user;
		}else if(userSex!=null) {
			System.out.println("更新用户性别"+userSex);
			
			user.setUserSex(userSex);
			user = us.UpdateUseAttribute(user);
			return user;
		}else {
			System.out.println("更新用户电话"+userPhone);
			user.setUserPhone(userPhone);
			user = us.UpdateUseAttribute(user);
			return user;
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
	public Users register(HttpServletRequest request,HttpServletResponse response,HttpSession session, @RequestBody String data) {
		System.out.println("注册验证"+data);
		Gson gson = new GsonBuilder()
				.serializeNulls()
				.setPrettyPrinting()
				.create();
		
		Users user = gson.fromJson(data, Users.class);
		//生成随机用户名
		String username= "吾理头用户"+getCharAndNumr(6);
		user.setUserName(username);
		//默认头像
		user.setUserHeader("resource/user/head/barberHead.jpg");
		//用户名可用 可以注册用户
		if(us.isEmpty(user.getUserAccount())) {
			
			//执行插入操作
			us.insert(user);
			//初始化该用户的资源目录
			uploadPicUtil.initUserFileDirectory(user.getUserAccount());

			return user;
		}else {
			//当用户名已被注册 无法注册
			user= new Users();
			user.setUserCondition(false);
			return user;
		}		
	}
	/**
     * java生成随机数字和字母组合
     * 
     * @param length[生成随机数的长度]
     * 
     */
    public static String getCharAndNumr(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
    
    /**
	 * 根据token查询用户User
	 * @param token
	 * @return
	 */
	private Users findUserByToken(String sessionUserToken,String UserToken ,HttpSession session) {
		
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
	/**
	 * 根据用户account 查找用户昵称
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/queryUserName", method = RequestMethod.POST)
	@ResponseBody
	public Users  QueryUserNameByUserAccount(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		System.out.println("根据用户account 查找用户昵称");
		String UserAccount = request.getParameter("BarberAccount");
		System.out.println("BarberAccount:"+UserAccount);
		String UserName =us.findUserNameByUserAccountss(UserAccount);
		System.out.println(UserName);
		Users user = new Users();
		user.setUserName(UserName);
		return user;	
	}
	
	
}
