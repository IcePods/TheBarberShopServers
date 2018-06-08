package com.barbershop.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Merchant {
	private int merchantId;
	private String merchantAccount;
	private String merchantPassword;
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
	@Override
	public String toString() {
		return "Merchant [merchantId=" + merchantId + ", merchantAccount=" + merchantAccount + ", merchantPassword="
				+ merchantPassword + "]";
	}
	
}
