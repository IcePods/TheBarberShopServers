package com.barbershop.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sun.misc.BASE64Decoder;

public class userPictureUtil {
	//用户头像图片存储的绝对路径
	private final String USER_HEAD = "E://eclipse_workspace/theBarberShopServiceImageResource/user/head";
	//用户动态图片存储的绝对路径
	private final String USER_DYNAMIC = "E://eclipse_workspace/theBarberShopServiceImageResource/user/dynamic";
	//访问用户头像图片的虚拟路径
	private final String USER_HEAD_PATH = "/theBarberShopServers/resource/user/head";
	//访问用户动态图片的虚拟路径
	private final String USER_DYNAMIC_PATH = "/theBarberShopServers/resource/user/dynamic";
	/**
	 * 初始化用户图片资源目录,用户注册时调用一次
	 */
	public void initUserFileDirectory() {
		File dirFile;
		try {
			dirFile = new File(USER_HEAD);
			//head文件夹不存在，创建head文件夹
			if(!dirFile.exists()) {
				System.out.println("创建head目录");
				createFild(USER_HEAD);
			}
			dirFile = new File(USER_DYNAMIC);
			//dynamic文件夹不存在，创建dynamic文件夹
			if(!dirFile.exists()) {
				System.out.println("创建dynamic目录");
				createFild(USER_DYNAMIC);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建指定文件夹
	 * @param name 文件夹名称
	 * @return 创建成功返回true，失败返回false
	 */
	private boolean createFild(String name) {
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
	 * 接收上传的动态图片，保存到服务器图片资源目录
	 * @param picList
	 */
	public List<String> receivePicture(List<String> picList) {
		List<String> picPathList = new ArrayList<String>();
		Date date = new Date();
		int i = 1;
		for(String pic: picList) {
			try {
				byte[] bs = new BASE64Decoder().decodeBuffer(pic);
				// 写到D盘Img文件夹下的a.jpg文件。注：Img文件夹一定要存在
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
				
				String name = dateFormat.format(date).toString() + i;
				i++;
				FileOutputStream fos = new FileOutputStream(USER_DYNAMIC+"/"+name+".png");
				
				fos.write(bs);
				fos.flush();
				fos.close();
				String path = USER_DYNAMIC_PATH + "/" + name + ".png";
				picPathList.add(path);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
		}
		return picPathList;
	}
	
}
