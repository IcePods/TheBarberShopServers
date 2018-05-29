package com.barbershop.bean;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Users {
	private int UserId;
	private String UserAccount;
    private String UserPassword;
    private String UserName;
    private String UserSex;
    private String UserPhone;
    private String UserHeader;
	private Set<Shop> ShopSet = new HashSet<Shop>();
	
	@JsonProperty("UserId")
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int userId) {
		UserId = userId;
	}
	@JsonProperty("UserAccount")
	public String getUserAccount() {
		return UserAccount;
	}
	public void setUserAccount(String userAccount) {
		UserAccount = userAccount;
	}
	@JsonProperty("UserPassword")
	public String getUserPassword() {
		return UserPassword;
	}
	public void setUserPassword(String userPassword) {
		UserPassword = userPassword;
	}
	@JsonProperty("UserName")
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	@JsonProperty("UserSex")
	public String getUserSex() {
		return UserSex;
	}
	public void setUserSex(String userSex) {
		UserSex = userSex;
	}
	@JsonProperty("UserPhone")
	public String getUserPhone() {
		return UserPhone;
	}
	public void setUserPhone(String userPhone) {
		UserPhone = userPhone;
	}
	@JsonProperty("UserHeader")
	public String getUserHeader() {
		return UserHeader;
	}
	public void setUserHeader(String userHeader) {
		UserHeader = userHeader;
	}
	public Set<Shop> getShopSet() {
		return ShopSet;
	}
	@JsonProperty("ShopSet")
	public void setShopSet(Set<Shop> shopSet) {
		ShopSet = shopSet;
	}
	
	
}