package com.barbershop.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DynamicPicture {
	private int DynamicPicture_id;
	private String DynamicPicture;
	
	@JsonProperty("DynamicPicture_id")
	public int getDynamicPicture_id() {
		return DynamicPicture_id;
	}
	public void setDynamicPicture_id(int dynamicPicture_id) {
		DynamicPicture_id = dynamicPicture_id;
	}
	@JsonProperty("DynamicPicture")
	public String getDynamicPicture() {
		return DynamicPicture;
	}
	public void setDynamicPicture(String dynamicPicture) {
		DynamicPicture = dynamicPicture;
	}
	
}
