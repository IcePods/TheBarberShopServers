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

	public boolean deleteBarberByID(int barberId) {
		return barberDao.deleteBarberByID(barberId);
	}

	//添加店铺ID对应的理发师
	public boolean addBarber(String account, String password, int merchantId) {
		return barberDao.addBarber(account,password,merchantId);
	}
	
}
