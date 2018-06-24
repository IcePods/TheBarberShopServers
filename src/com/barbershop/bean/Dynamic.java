package com.barbershop.bean;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Dynamic {
	private int DynamicId;
//	@JsonIgnore
    private Users user;//用户
    private String DynamicContent;  // 动态文字
    private String DynamicTime;//动态发布时间
    private Set<DynamicPicture> DynamicImagePathSet= new HashSet<>(); //动态图片列表
    private boolean DynamicCondition = true;
    @JsonProperty("DynamicId")
	public int getDynamicId() {
		return DynamicId;
	}
	public void setDynamicId(int dynamicId) {
		DynamicId = dynamicId;
	}
	 @JsonProperty("user")
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	@JsonProperty("DynamicContent")
	public String getDynamicContent() {
		return DynamicContent;
	}
	public void setDynamicContent(String dynamicContent) {
		DynamicContent = dynamicContent;
	}
	@JsonProperty("DynamicImagePathSet")
	public Set<DynamicPicture> getDynamicImagePathSet() {
		return DynamicImagePathSet;
	}
	public void setDynamicImagePathSet(Set<DynamicPicture> dynamicImagePathSet) {
		DynamicImagePathSet = dynamicImagePathSet;
	}
	public boolean isDynamicCondition() {
		return DynamicCondition;
	}
	public void setDynamicCondition(boolean dynamicCondition) {
		DynamicCondition = dynamicCondition;
	}
	@JsonProperty("DynamicTime")
	public String getDynamicTime() {
		return DynamicTime;
	}
	public void setDynamicTime(String dynamicTime) {
		DynamicTime = dynamicTime;
	}
	
    
}
