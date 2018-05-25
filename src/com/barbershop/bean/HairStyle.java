package com.barbershop.bean;

import java.util.HashSet;
import java.util.Set;

public class HairStyle {
	private int hairstyleId;//发型id
    private String hairstylePicture;//发型图片地址
    private String hairstyleName;//发型名称
    private String hairstyleIntroduce;//发型简介
	//发型和店铺多对一双向映射
	private Shop shop;
	//发型和 发现详情图片 单项一 对多影视//发型图片集合
	private Set<HairStyleDetail> HairStyleDetailSet = new HashSet<>();
	public int getHairstyleId() {
		return hairstyleId;
	}
	public void setHairstyleId(int hairstyleId) {
		this.hairstyleId = hairstyleId;
	}
	public String getHairstylePicture() {
		return hairstylePicture;
	}
	public void setHairstylePicture(String hairstylePicture) {
		this.hairstylePicture = hairstylePicture;
	}
	public String getHairstyleName() {
		return hairstyleName;
	}
	public void setHairstyleName(String hairstyleName) {
		this.hairstyleName = hairstyleName;
	}
	public String getHairstyleIntroduce() {
		return hairstyleIntroduce;
	}
	public void setHairstyleIntroduce(String hairstyleIntroduce) {
		this.hairstyleIntroduce = hairstyleIntroduce;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public Set<HairStyleDetail> getHairStyleDetailSet() {
		return HairStyleDetailSet;
	}
	public void setHairStyleDetailSet(Set<HairStyleDetail> hairStyleDetailSet) {
		HairStyleDetailSet = hairStyleDetailSet;
	}
	
	
	

}
