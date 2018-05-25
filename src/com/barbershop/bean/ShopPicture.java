package com.barbershop.bean;

public class ShopPicture {
	private int shoppicture_id;
	private String shoppicture_picture;
	public int getShoppicture_id() {
		return shoppicture_id;
	}
	public void setShoppicture_id(int shoppicture_id) {
		this.shoppicture_id = shoppicture_id;
	}
	public String getShoppicture_picture() {
		return shoppicture_picture;
	}
	public void setShoppicture_picture(String shoppicture_picture) {
		this.shoppicture_picture = shoppicture_picture;
	}
	
	
	//单项一对多
	//商店与商店图片单项一对多 商店图片不用有任何定义
	
}
