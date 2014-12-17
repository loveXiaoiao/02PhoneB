package com.example.phonebook.entity;

import android.graphics.Bitmap;

public class Contact {
	
	//联系人id
	private Long id;
	//联系人姓名
	private String name;
	//联系人电话
	private String tel;
	//联系人头像
	private Bitmap photo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Bitmap getPhoto() {
		return photo;
	}
	public void setPhoto(Bitmap photo) {
		this.photo = photo;
	}
	

}
