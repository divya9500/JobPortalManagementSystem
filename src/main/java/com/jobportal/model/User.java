package com.jobportal.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class User {
	 	private long id;
	    private String fullName;
	    private String email;
	    private String mobNum;
	    private String passwordHash;
	    private Role role;
	    private Status status;
	    private int failedAttempts;
	   private Timestamp accountLockedUntil;      

	    
	   
	    public int getFailedAttempts() {
		return failedAttempts;
	}

	public void setFailedAttempts(int failedAttempts) {
		this.failedAttempts = failedAttempts;
	}

	public Timestamp getAccountLockedUntil() {
		return accountLockedUntil;
	}

	public void setAccountLockedUntil(Timestamp accountLockedUntil) {
		this.accountLockedUntil = accountLockedUntil;
	}

		// Enum for Role
	    public enum Role {
	        USER,
	        HR,
	        ADMIN,
	        SUPER_ADMIN
	    }

	    // Enum for Status
	    public enum Status {
	        ACTIVE,
	        INACTIVE,
	        BLOCKED
	    }
	    
	    public User() {
	        this.role = Role.USER;
	        this.status = Status.ACTIVE;
	       
	    }

	    // Parameterized constructor
	    public User(String fullName, String email,String mobNum, String passwordHash, Role role, Status status) {
	        this.fullName = fullName;
	        this.email = email;
	        this.mobNum=mobNum;
	        this.passwordHash = passwordHash;
	        this.role = role != null ? role : Role.USER;
	        this.status = status != null ? status : Status.ACTIVE;
	       
	    }

	    public void setId(long id) {
			this.id = id;
		}

		// Getters and Setters
	    public long getId() {
	        return id;
	    }

	    public String getFullName() {
	        return fullName;
	    }

	    public void setFullName(String fullName) {
	        this.fullName = fullName;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }
	    public String getMobNum() {
	    	return mobNum;
	    }
	    public void setMobNum(String mobNum) {
	    	this.mobNum=mobNum;
	    }

	    public String getPasswordHash() {
	        return passwordHash;
	    }

	    public void setPasswordHash(String passwordHash) {
	        this.passwordHash = passwordHash;
	    }

	    public Role getRole() {
	        return role;
	    }

	    public void setRole(Role role) {
	        this.role = role;
	    }

	    public Status getStatus() {
	        return status;
	    }

	    public void setStatus(Status status) {
	        this.status = status;
	    }

		/*
		 * public LocalDateTime getCreatedAt() { return createdAt; }
		 * 
		 * public void setCreatedAt(LocalDateTime createdAt) { this.createdAt =
		 * createdAt; }
		 * 
		 * public LocalDateTime getLastLoginAt() { return lastLoginAt; }
		 * 
		 * public void setLastLoginAt(LocalDateTime lastLoginAt) { this.lastLoginAt =
		 * lastLoginAt; }
		 */
}
