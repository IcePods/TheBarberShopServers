package com.barbershop.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Merchant {
	private int merchantId;
	private String merchantAccount;
	private String merchantPassword;
	private String merchantToken;
	private char openSuccessfully;//是否开店成功  0为不成功 1为成功
	//不进行持久化  只用来判断 用户状态 ：用户名密码错误、注册错误、 等   默认为True 用户合法
    private Boolean merchantCondition = true;
    
	//和shop类一对一映射  唯一外键映射
	private Shop shop;

	
	@JsonProperty("merchantId")
	public int getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}
	@JsonProperty("merchantAccount")
	public String getMerchantAccount() {
		return merchantAccount;
	}
	public void setMerchantAccount(String merchantAccount) {
		this.merchantAccount = merchantAccount;
	}
	@JsonProperty("merchantPassword")
	public String getMerchantPassword() {
		return merchantPassword;
	}
	public void setMerchantPassword(String merchantPassword) {
		this.merchantPassword = merchantPassword;
	}
	@JsonProperty("shop")
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
	public String getMerchantToken() {
		return merchantToken;
	}
	public void setMerchantToken(String merchantToken) {
		this.merchantToken = merchantToken;
	}
	public Boolean getMerchantCondition() {
		return merchantCondition;
	}
	public void setMerchantCondition(Boolean merchantCondition) {
		this.merchantCondition = merchantCondition;
	}
	public char isOpenSuccessfully() {
		return openSuccessfully;
	}
	public void setOpenSuccessfully(char openSuccessfully) {
		this.openSuccessfully = openSuccessfully;
	}
	@Override
	public String toString() {
		return "Merchant [merchantId=" + merchantId + ", merchantAccount=" + merchantAccount + ", merchantPassword="
				+ merchantPassword + ", merchantToken=" + merchantToken + ", openSuccessfully=" + openSuccessfully
				+ ", merchantCondition=" + merchantCondition + ", shop=" + shop + "]";
	}
	
	

	
}
