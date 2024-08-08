package com.example.comunity.post;

import java.time.LocalDate;

public class Post {
	private int id;
	private String username;
	private String description;
	private LocalDate targetDate;
	private boolean isManager;
	
	public Post(int id, String username, String description, LocalDate targetDate, boolean isManager) {
		super();
		this.id = id;
		this.username = username;
		this.description = description;
		this.targetDate = targetDate;
		this.isManager = isManager;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(LocalDate targetDate) {
		this.targetDate = targetDate;
	}

	public boolean isManager() {
		return isManager;
	}

	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", username=" + username + ", description=" + description + ", targetDate="
				+ targetDate + ", isManager=" + isManager + "]";
	}
	
}
