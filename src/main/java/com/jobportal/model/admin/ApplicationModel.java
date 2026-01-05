package com.jobportal.model.admin;

import java.sql.Timestamp;




public class ApplicationModel {
	// user_id, job_id, application_status, applied_at, updated_at
	//String sql="select app.id,app.user_id,user.name,user.email,app.status,app.applied_at "

		private long applicationId;
	 private long userId;
	 private long jobId;
	 private String name;
	 private String email;
	
	 
	 private Status applicationStatus;
	 private Timestamp appliedAt;
	 private Timestamp updatedAt;
	 
	 
	 public enum Status {
		    APPLIED,
		    SHORTLISTED,
		    INTERVIEW_SCHEDULED,
		    INTERVIEW_PASSED,
		    INTERVIEW_FAILED,
		    OFFERED,
		    HIRED,
		    REJECTED
		}
	public long getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getJobId() {
		return jobId;
	}
	public void setJobId(long jobId) {
		this.jobId = jobId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Status getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(Status applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public Timestamp getAppliedAt() {
		return appliedAt;
	}
	public void setAppliedAt(Timestamp appliedAt) {
		this.appliedAt = appliedAt;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	 
		
}
