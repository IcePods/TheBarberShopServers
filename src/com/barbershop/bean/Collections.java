package com.barbershop.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Collections {
	private int collection_id;
	@JsonIgnore
	private Users user;
	private Shop shop;
	//辨别Collection的合法性 不进行持久化 
	private Boolean CollectionCondition = true;
	
	public int getCollection_id() {
		return collection_id;
	}
	public void setCollection_id(int collection_id) {
		this.collection_id = collection_id;
	}
	
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	@JsonProperty("CollectionCondition")
	public Boolean getCollectionCondition() {
		return CollectionCondition;
	}
	public void setCollectionCondition(Boolean collectionCondition) {
		CollectionCondition = collectionCondition;
	}
	
}
