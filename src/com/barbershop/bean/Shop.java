package com.barbershop.bean;

import java.util.HashSet;
import java.util.Set;

public class Shop {
	private int shopId;
    private String shopPicture;
    private String shopName;
    private String shopAddress;
    private String shopPhone;
    private String shopIntroduce;//店铺简介
	//收藏表 用户和商店多对多
	private Set<Users> UsersSet =new HashSet<Users>();
	//商店与商店图片表单向一对多
	private Set<ShopPicture> ShopPictureSet = new HashSet<ShopPicture>();
	
	//店铺与发型 一对多双向映射
	private Set<HairStyle> HairStyleSet = new HashSet<HairStyle>();

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public String getShopPicture() {
		return shopPicture;
	}

	public void setShopPicture(String shopPicture) {
		this.shopPicture = shopPicture;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getShopPhone() {
		return shopPhone;
	}

	public void setShopPhone(String shopPhone) {
		this.shopPhone = shopPhone;
	}

	public String getShopIntroduce() {
		return shopIntroduce;
	}

	public void setShopIntroduce(String shopIntroduce) {
		this.shopIntroduce = shopIntroduce;
	}

	public Set<Users> getUsersSet() {
		return UsersSet;
	}

	public void setUsersSet(Set<Users> usersSet) {
		UsersSet = usersSet;
	}

	public Set<ShopPicture> getShopPictureSet() {
		return ShopPictureSet;
	}

	public void setShopPictureSet(Set<ShopPicture> shopPictureSet) {
		ShopPictureSet = shopPictureSet;
	}

	public Set<HairStyle> getHairStyleSet() {
		return HairStyleSet;
	}

	public void setHairStyleSet(Set<HairStyle> hairStyleSet) {
		HairStyleSet = hairStyleSet;
	}
	
	
}
