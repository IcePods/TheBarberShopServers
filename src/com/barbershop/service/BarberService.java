package com.barbershop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barbershop.bean.Barber;
import com.barbershop.bean.Shop;
import com.barbershop.dao.BarberDao;

@Service
@Transactional
public class BarberService {
	@Autowired
	private BarberDao barberDao;
	
	public List<Barber> showBarberByShop(Shop shop) {
		return barberDao.showBarberByShop(shop);
	}

	//通过用户名和密码获取店员列表
	public List<Barber> getBarberListByMerchant(String account, String pwd){
		return barberDao.getBarberListByMerchant(account,pwd);
	}
	
}
