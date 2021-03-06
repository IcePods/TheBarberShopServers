package com.barbershop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barbershop.bean.Merchant;
import com.barbershop.bean.Shop;
import com.barbershop.dao.ShopDao;

@Service
@Transactional
public class ShopService {
	@Autowired
	private ShopDao shopdao;
	//获取全部店铺信息
	public List<Shop> getAllShop(){
		return shopdao.getAllShop();
	}
	//根据ID获取店铺
	public Shop getShopByShopId(int shopid) {
		return shopdao.getShopByShopId(shopid);
	}
	public List<Shop> SelectShopFuzzyMatching(String ShopName){
		return shopdao.SelectShopFuzzyMatching(ShopName);
	}
	public Merchant findMerchantByShop(Shop shop) {
		return shopdao.findMerchantByShop(shop);
	}
	public void saveShop(Shop shop) {
		shopdao.saveShop(shop);
	}
	public void updateShop(Shop shop) {
		shopdao.updateShop(shop);
	}
	

}
