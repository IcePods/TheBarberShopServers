package com.barbershop.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Barber {
	private int barberId;
	private String barberImg;
	private String barberName;
	//用户与理发师主键关联映射
	private Users user;
	//理发师与店铺 多对一关联映射  单项无意义 不用写 
	//private Shop shop;
	public int getBarberId() {
		return barberId;
	}
	public void setBarberId(int barberId) {
		this.barberId = barberId;
	}
	public String getBarberImg() {
		return barberImg;
	}
	public void setBarberImg(String barberImg) {
		this.barberImg = barberImg;
	}
	public String getBarberName() {
		return barberName;
	}
	public void setBarberName(String barberName) {
		this.barberName = barberName;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	/*public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}*/
	
	

}
