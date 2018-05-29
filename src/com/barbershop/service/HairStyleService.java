package com.barbershop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barbershop.bean.HairStyle;
import com.barbershop.bean.Shop;
import com.barbershop.dao.HairStyleDao;

@Service
@Transactional
public class HairStyleService {
	@Autowired
	private HairStyleDao hsDao;
	
	//根据类型HairStyleTyple 获取集合
	public List<HairStyle> getHairStyleByTyple(String type){
		return hsDao.getHairStyleByTyple(type);
	}
		
	// 从店铺  查看 发型 根据 店铺 获取 集合
	public List<HairStyle> getHairStyleByShop(Shop shop){
		return hsDao.getHairStyleByShop(shop);
	}
}
