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
    private String UserToken;
    //不进行持久化  只用来判断 用户状态 ：用户名密码错误、注册错误、 等   默认为True 用户合法
    private Boolean UserCondition = true;
    
	private Set<Collections> ShopCollectionSet = new HashSet<Collections>();
	
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

	@JsonProperty("UserToken")
	public String getUserToken() {
		return UserToken;
	}
	public void setUserToken(String userToken) {
		UserToken = userToken;
	}
	@JsonProperty("UserCondition")
	public Boolean getUserCondition() {
		return UserCondition;
	}
	public void setUserCondition(Boolean userCondition) {
		UserCondition = userCondition;
	}
	@JsonProperty("ShopCollectionSet")
	public Set<Collections> getShopCollectionSet() {
		return ShopCollectionSet;
	}
	public void setShopCollectionSet(Set<Collections> shopCollectionSet) {
		ShopCollectionSet = shopCollectionSet;
	}

	
	
}
