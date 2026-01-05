package com.jobportal.model;

import java.sql.Timestamp;

public class Job {
	private int jobId;
	private String title;
	private String description;
	private String location;
	private double min_salary;
	private double max_salary;
	private double min_experience;
	private double max_experience;
	private  EmploymentType empType;
	private int created_by;
	private Timestamp created_at;
	private Status status;
	
	public enum EmploymentType {
		FULL_TIME ,
		PART_TIME ,
		CONTRACT
	}
	   public  enum Status{
		   OPEN,
		   CLOSED,
		   ON_HOLD
      
	   }
	   
	
	
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public double getMin_salary() {
		return min_salary;
	}
	public void setMin_salary(double min_salary) {
		this.min_salary = min_salary;
	}
	public double getMax_salary() {
		return max_salary;
	}
	public void setMax_salary(double max_salary) {
		this.max_salary = max_salary;
	}
	public double getMin_experience() {
		return min_experience;
	}
	public void setMin_experience(double min_experience) {
		this.min_experience = min_experience;
	}
	public double getMax_experience() {
		return max_experience;
	}
	public void setMax_experience(double max_experience) {
		this.max_experience = max_experience;
	}
	public EmploymentType getEmpType() {
		return empType;
	}
	public void setEmpType(EmploymentType empType) {
		this.empType = empType;
	}
	public int getCreated_by() {
		return created_by;
	}
	public void setCreated_by(int created_by) {
		this.created_by = created_by;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	

	
}
