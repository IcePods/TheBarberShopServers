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
	private Date activityStartTime;//活动开始时间
	private Date activityEndTime;//活动结束时间
	//与店铺双向多对一
//	@JsonIgnore
	private Shop shop;
	
	public Activity() {}
	public Activity(int activityId, String activityName, String activityContent, Date activityStartTime,
			Date activityEndTime, Shop shop) {
		this.activityId = activityId;
		this.activityName = activityName;
		this.activityContent = activityContent;
		this.activityStartTime = activityStartTime;
		this.activityEndTime = activityEndTime;
		this.shop = shop;
	}
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
	public Date getActivityStartTime() {
		return activityStartTime;
	}
	public void setActivityStartTime(Date activityStartTime) {
		this.activityStartTime = activityStartTime;
	}
	public Date getActivityEndTime() {
		return activityEndTime;
	}
	public void setActivityEndTime(Date activityEndTime) {
		this.activityEndTime = activityEndTime;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
}
