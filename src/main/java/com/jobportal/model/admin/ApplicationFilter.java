package com.jobportal.model.admin;

import java.sql.Date;

import com.jobportal.model.admin.ApplicationModel.Status;


public class ApplicationFilter {

    private Long jobId;
    private Integer minExperience;
    private Integer maxExperience;
    private Integer minGraduationYear;
    private Date appliedAfter;
    private Integer limit;
    private Status fromStatus;

    public Status getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(Status fromStatus) {
        this.fromStatus = fromStatus;
    }

	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public Integer getMinExperience() {
		return minExperience;
	}
	public void setMinExperience(Integer minExperience) {
		this.minExperience = minExperience;
	}
	public Integer getMaxExperience() {
		return maxExperience;
	}
	public void setMaxExperience(Integer maxExperience) {
		this.maxExperience = maxExperience;
	}
	public Integer getMinGraduationYear() {
		return minGraduationYear;
	}
	public void setMinGraduationYear(Integer minGraduationYear) {
		this.minGraduationYear = minGraduationYear;
	}
	public Date getAppliedAfter() {
		return appliedAfter;
	}
	public void setAppliedAfter(Date appliedAfter) {
		this.appliedAfter = appliedAfter;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

    

}
