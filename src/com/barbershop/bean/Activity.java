package com.barbershop.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 店铺活动  和店铺多对一
 * @author shan
 *
 */
public class Activity {
	private int activityId;
	private String activityName;
	private String activityContent;//活动内容
	private String activityStartTime;//活动开始时间
	private String activityEndTime;//活动结束时间
	//与店铺双向多对一
	@JsonIgnore
	private Shop shop;

	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getActivityContent() {
		return activityContent;
	}
	public void setActivityContent(String activityContent) {
		this.activityContent = activityContent;
	}
	
	public String getActivityStartTime() {
		return activityStartTime;
	}

	public void setActivityStartTime(String activityStartTime) {
		this.activityStartTime = activityStartTime;
	}

	public String getActivityEndTime() {
		return activityEndTime;
	}

	public void setActivityEndTime(String activityEndTime) {
		this.activityEndTime = activityEndTime;
	}

	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
}
