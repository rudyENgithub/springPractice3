package com.spring_cookbook.domain;

import java.util.Date;

public class Post {
	private long id;
	private String title;
	private Date date;
	private UsersJdbc user;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public UsersJdbc getUser() {
		return user;
	}
	public void setUser(UsersJdbc user) {
		this.user = user;
	}

}
