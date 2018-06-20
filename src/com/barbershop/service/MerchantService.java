package com.barbershop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barbershop.bean.Activity;
import com.barbershop.bean.Merchant;
import com.barbershop.dao.MerchantDao;

@Service
@Transactional
public class MerchantService {
	@Autowired
	private MerchantDao merchantdao;

	// 判断用户名是否已存在
	public boolean isEmpty(String account) {
		Merchant merchant = merchantdao.queryMerchantByAccount(account);
		if (merchant == null) {
			return true;
		} else {
			return false;
		}
	}

	// 插入店铺
	public boolean insert(Merchant merchant) {
		return merchantdao.insertMerchant(merchant);
	}

	// --登录--
	// 验证用户账户与密码是否匹配
	public Merchant checkMerchantLogin(String account, String pwd) {
		return merchantdao.checkMerchantLogin(account, pwd);
	}

	// 登录操作 添加token
	public Merchant merchantLoginAddToken(Merchant merchant) {
		return merchantdao.merchantLoginAddToken(merchant);
	}

	public Merchant findMerchantByToken(String merchantToken) {
		return merchantdao.findMerchantByToken(merchantToken);
	}
	
	//通过用户名和密码获取活动列表
	public List<Activity> getActivityList(String account, String pwd){
		return merchantdao.getActivityList(account,pwd);
	}
	
	/**
	 * 通过token获取merchant对象
	 * @param token
	 * @return
	 */
	public Merchant getMerchantByToken(String token) {
		return merchantdao.getMerchantByToken(token);
	}

}
