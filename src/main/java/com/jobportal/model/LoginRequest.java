package com.jobportal.model;

import com.jobportal.model.User.Role;

public class LoginRequest {
	private String email;
	private String password;
	private Role role;
	
	public LoginRequest(String email,String password) {
		this.email=email;
		this.password=password;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getemail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
}
