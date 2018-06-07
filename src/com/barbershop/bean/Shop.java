package com.barbershop.bean;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Shop {
	private int shopId;
    private String shopPicture;
    private String shopName;
    private String shopAddress;
    private String shopPhone;
    private String shopIntroduce;//店铺简介
	//收藏表 用户和商店 多对多 用两个一对多表示 单项不需要定义
	//private Set<Collections> UsersSet =new HashSet<Collections>();
	//商店与商店图片表单向一对多
	private Set<ShopPicture> ShopPictureSet = new HashSet<ShopPicture>();
	
	//店铺与发型 一对多双向映射
	private Set<HairStyle> HairStyleSet = new HashSet<HairStyle>();
	
	//店铺与 理发师 一对多 单项映射
	private Set<Barber> BarberSet = new HashSet<Barber>();
	@JsonProperty("shopId")
	public int getShopId() {
		return shopId;
	}
	
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	@JsonProperty("shopPicture")
	public String getShopPicture() {
		return shopPicture;
	}

	public void setShopPicture(String shopPicture) {
		this.shopPicture = shopPicture;
	}
	@JsonProperty("shopName")
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	@JsonProperty("shopAddress")
	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}
	@JsonProperty("shopPhone")
	public String getShopPhone() {
		return shopPhone;
	}

	public void setShopPhone(String shopPhone) {
		this.shopPhone = shopPhone;
	}
	@JsonProperty("shopIntroduce")
	public String getShopIntroduce() {
		return shopIntroduce;
	}

	public void setShopIntroduce(String shopIntroduce) {
		this.shopIntroduce = shopIntroduce;
	}
	
	@JsonProperty("ShopPictureSet")
	public Set<ShopPicture> getShopPictureSet() {
		return ShopPictureSet;
	}

	public void setShopPictureSet(Set<ShopPicture> shopPictureSet) {
		ShopPictureSet = shopPictureSet;
	}
	
	@JsonProperty("HairStyleSet")
	public Set<HairStyle> getHairStyleSet() {
		return HairStyleSet;
	}

	public void setHairStyleSet(Set<HairStyle> hairStyleSet) {
		HairStyleSet = hairStyleSet;
	}
	@JsonProperty("BarberSet")
	public Set<Barber> getBarberSet() {
		return BarberSet;
	}

	public void setBarberSet(Set<Barber> barberSet) {
		BarberSet = barberSet;
	}
	
	
}
