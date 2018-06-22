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
		
	// 从店铺和类型参数  查看 发型 根据 店铺 获取 集合
	public List<HairStyle> getHairStyleByShop(Shop shop,String type){
		return hsDao.getHairStyleByShop(shop,type);
	}
	
	/**
	 * 上传新发型
	 * @param hs
	 * @return
	 */
	public void createNewHairSytle(HairStyle hs) {
		hsDao.addNewHairStyle(hs);
	}
	public void updateShopByHairstyle(Shop shop) {
		hsDao.updateShopByHairstyle(shop);
	}
	
	//得到店铺作品
	public List<HairStyle> getHairStyleByShop(Shop shop){
		return hsDao.getHairStyleByShop(shop);
	}
	
	public void deleteHairStyle(HairStyle hairStyle) {
		hsDao.deleteHairStyle(hairStyle);
	}
	public HairStyle getHairStyleByID(int id) {
		return hsDao.getHairStyleByID(id);
	}
}
