package com.barbershop.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.barbershop.bean.Users;

import sun.misc.BASE64Decoder;

public class UploadPictureUtil {
	//用户头像图片存储的绝对路径
	private final String USER = "E://eclipse_workspace/theBarberShopServiceImageResource/user";
	//店铺用户图片存储的路径
	private final String MERCHANT = "E://eclipse_workspace/theBarberShopServiceImageResource/merchant";

	//访问用户图片的虚拟路径
	private final String USER_PATH = "resource/user";
	//访问店铺图片的虚拟路径 
	private final String MERCHANT_PATH = "resource/merchant";

	//头像
	private final String HEAD = "/head";
	//动态
	private final String DYNAMIC = "/dynamic";
	//作品图片
	private final String PRODUCTION = "/production";
	//店铺展示图片（轮播）
	private final String SHOP_IMAGE = "/shopImage";
	//默认头像路径
	private final String defaultHeader = "resource/user/head/barberHead.jpg";

	/**
	 * 初始化用户图片资源目录,用户注册时调用一次
	 * @param userAccount 用户账号
	 */
	public void initUserFileDirectory(String userAccount) {		
		File dirFile;

		//如果user文件夹没有存在，创建user文件夹
		dirFile = new File(USER);
		if(!dirFile.exists()) {
			createFolder(USER);
		}

		//如果用户文件夹没有存在，创建user文件夹
		String str = USER + "/" + userAccount;
		dirFile = new File(str);
		if(!dirFile.exists()) {
			createFolder(str);
		}

		//如果user/用户/头像文件夹没有存在，创建user文件夹
		str = USER + "/" + userAccount + HEAD;
		dirFile = new File(str);
		if(!dirFile.exists()) {
			createFolder(str);
		}

		//如果user/用户/动态文件夹没有存在，创建user文件夹
		str = USER + "/" + userAccount + DYNAMIC;
		dirFile = new File(str);
		if(!dirFile.exists()) {
			createFolder(str);
		}		
	}

	/**
	 * 初始化商家用户图片目录,注册调用一次
	 * @param merchantAccount 商家用户账号
	 */
	public void initMerchantFileDirectory(String merchantAccount) {
		File dirFile;

		//如果merchant文件夹不存在，创建merchant文件夹
		dirFile = new File(MERCHANT);
		if(!dirFile.exists()) {
			createFolder(MERCHANT);
		}

		//如果店铺用户文件夹没有存在，创建user文件夹
		String str = MERCHANT + "/" + merchantAccount;
		dirFile = new File(str);
		if(!dirFile.exists()) {
			createFolder(str);
		}

		//如果店铺用户动态文件夹没有存在，创建user文件夹
		str = MERCHANT + "/" + merchantAccount + PRODUCTION;
		dirFile = new File(str);
		if(!dirFile.exists()) {
			createFolder(str);
		}
		//如果店铺用户动态文件夹没有存在，创建user文件夹
		str = MERCHANT + "/" + merchantAccount + SHOP_IMAGE;
		dirFile = new File(str);
		if(!dirFile.exists()) {
			createFolder(str);
		}

	}

	/**
	 * 创建指定文件夹
	 * @param name 文件夹名称
	 * @return 创建成功返回true，失败返回false
	 */
	private boolean createFolder(String name) {
		File dirFile = new File(name);
		boolean dFile = dirFile.mkdir();
		if(dFile) {
			System.out.println(name+"创建成功");
			return true;
		}else {
			System.out.println(name+"创建失败");
			return false;
		}
	}

	/**
	 * 接收用户上传的头像图片
	 * @param pic 用户上传图片的字节流串
	 * @param picName 用户名（来给用户头像命名，具有唯一性）
	 * @return 返回用户头像所在的虚拟地址
	 */
	public String receiveUserHeadPic(String pic, Users user) {
		String path = new String();
		String str = user.getUserHeader().replaceAll(USER_PATH,"");
		String realPath = USER + str;
		File file = new File(realPath);
		Date date = new Date();
		if(file.exists() && file.isFile() && !user.getUserHeader().equals(defaultHeader)) {
			file.delete();
		}
		byte[] bs;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");	
			String name = dateFormat.format(date).toString();
			bs = new BASE64Decoder().decodeBuffer(pic);
			FileOutputStream fos = new FileOutputStream(USER + "/" + user.getUserAccount() + HEAD + "/" + name + ".png");

			fos.write(bs);
			fos.flush();
			fos.close();
			path = USER_PATH + "/" + user.getUserAccount() + HEAD + "/" + name + ".png";
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}		

		return path;
	}

	/**
	 * 接收用户上传的动态图片，保存到服务器图片资源目录
	 * @param picList
	 */
	public List<String> receiveUserDynamicPic(List<String> picList, String userAccount) {
		List<String> picPathList = new ArrayList<String>();
		Date date = new Date();
		int i = 1;
		byte[] bs; 
		for(String pic: picList) {
			try {
				bs = new BASE64Decoder().decodeBuffer(pic);
				//获取当前时间，精确到毫秒
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");	
				String name = dateFormat.format(date).toString() + i;
				i++;
				FileOutputStream fos = new FileOutputStream( USER + "/" + userAccount + DYNAMIC + "/" + name + ".png");

				fos.write(bs);
				fos.flush();
				fos.close();
				String path = USER_PATH + "/" + userAccount +  DYNAMIC + "/" + name + ".png";
				picPathList.add(path);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}

		}
		return picPathList;
	}


	/**
	 * 接收店铺上传的头像图片
	 * @param pic 店铺上传图片的字节流串
	 * @param picName 店铺用户名
	 * @return 返回头像所在的虚拟地址
	 */
	public String receiveMerchantHeadPic(String pic, String merchantAccount) {
		String path = new String();
		byte[] bs;
		try {
			bs = new BASE64Decoder().decodeBuffer(pic);
			FileOutputStream fos = new FileOutputStream(MERCHANT + "/" + merchantAccount + SHOP_IMAGE + "/" + merchantAccount + ".png");

			fos.write(bs);
			fos.flush();
			fos.close();
			path = MERCHANT_PATH + "/" + merchantAccount + SHOP_IMAGE + "/" + merchantAccount + ".png";
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}		

		return path;
	}

	/**
	 * 接收店铺上传的店铺作品图片，保存到服务器图片资源目录
	 * @param picList
	 */
	public List<String> receiveMerchantProductionPic(List<String> picList, String merchantAccount) {
		List<String> picPathList = new ArrayList<String>();
		Date date = new Date();
		int i = 1;
		byte[] bs;
		for(String pic: picList) {
			try {
				bs = new BASE64Decoder().decodeBuffer(pic);
				//获取当前时间，精确到毫秒
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");	
				String name = dateFormat.format(date).toString() + i;
				i++;
				FileOutputStream fos = new FileOutputStream( MERCHANT + "/" + merchantAccount + PRODUCTION + "/" + name + ".png");

				fos.write(bs);
				fos.flush();
				fos.close();
				String path = MERCHANT_PATH + "/" + merchantAccount + PRODUCTION + "/" + name + ".png";
				picPathList.add(path);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return picPathList;
	}

	/**
	 * 接收店铺上传的店铺展示图片，保存到服务器图片资源目录
	 * @param picList
	 */
	public List<String> receiveMerchantShopImagePic(List<String> picList, String merchantAccount) {
		List<String> picPathList = new ArrayList<String>();
		Date date = new Date();
		int i = 1;
		byte[] bs;
		for(String pic: picList) {
			try {
				bs = new BASE64Decoder().decodeBuffer(pic);
				//获取当前时间，精确到毫秒
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");	
				String name = dateFormat.format(date).toString() + i;
				i++;
				FileOutputStream fos = new FileOutputStream( MERCHANT + "/" + merchantAccount + SHOP_IMAGE + "/" + name + ".png");

				fos.write(bs);
				fos.flush();
				fos.close();
				String path = MERCHANT_PATH + "/" + merchantAccount + SHOP_IMAGE + "/" + name + ".png";
				picPathList.add(path);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return picPathList;
	}

	/**
	 * 删除用户端图片
	 * @param picPath 图片的虚拟路径
	 */
	public void deleteUserPic(String picPath) {
		//得到真实路径
		String path = picPath.replaceAll(USER_PATH, "");
		String realPath = USER + path;

		File file = new File(realPath);
		if(file.exists() && file.isFile()) {
			file.delete();
		}
	}

	/**
	 * 删除店铺端图片
	 * @param picPath 图片的虚拟路径
	 */
	public void deleteMerchantPic(String picPath) {
		//得到真实路径
		System.out.println("删除图片" + picPath);
		String path = picPath.replaceAll(MERCHANT_PATH, "");
		String realPath = MERCHANT + path;

		File file = new File(realPath);
		if(file.exists() && file.isFile()) {
			file.delete();
		}
	}
}
