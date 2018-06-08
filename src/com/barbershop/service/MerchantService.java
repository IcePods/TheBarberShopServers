package com.barbershop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barbershop.bean.Merchant;
import com.barbershop.dao.MerchantDao;

@Service
@Transactional
public class MerchantService {
	@Autowired
	private MerchantDao merchantdao;
	
	//判断用户名是否已存在
	public boolean isEmpty(String account) {
		Merchant merchant = merchantdao.queryMerchantByAccount(account);
		if(merchant == null) {
			return true;
		} else {
			return false;
		}
	}
	//插入店铺
	public boolean insert(Merchant merchant) {
		 return merchantdao.insertMerchant(merchant);
	}

}
