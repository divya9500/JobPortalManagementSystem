package com.jobportal.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;



public class JobSeekerProfile {

    private Long userId;

    private String firstName;
    private String lastName;

    private Gender gender;

    private String degree;
    private Integer graduationYear;

    private String currentEmployer;
   
    private BigDecimal current_ctc;
    
  
    private BigDecimal expected_ctc;
    private Integer noticePeriodDays;

    private String currentLocation;
    private String preferredLocation;
    
    
    private String resumeName;
    private String resumeBase64;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<Long> skills;
    public BigDecimal getCurrent_ctc() {
		return current_ctc;
	}
	public void setCurrent_ctc(BigDecimal current_ctc) {
		this.current_ctc = current_ctc;
	}
	public BigDecimal getExpected_ctc() {
		return expected_ctc;
	}
	public void setExpected_ctc(BigDecimal expected_ctc) {
		this.expected_ctc = expected_ctc;
	}
	public String getResumeName() {
		return resumeName;
	}
	public void setResumeName(String resumeName) {
		this.resumeName = resumeName;
	}
	private int jobId;
    

    public int getJobId() { return jobId; }
    public void setJobId(int jobId) { this.jobId = jobId; }

public enum Gender {
    MALE,
    FEMALE,
    OTHER
}

private BigDecimal totalExperienceYears;

public BigDecimal getTotalExperienceYears() {
    return totalExperienceYears;
}

public void setTotalExperienceYears(BigDecimal totalExperienceYears) {
    this.totalExperienceYears = totalExperienceYears;
}

    // Getters and Setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Integer getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(Integer graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getCurrentEmployer() {
        return currentEmployer;
    }

    public void setCurrentEmployer(String currentEmployer) {
        this.currentEmployer = currentEmployer;
    }

  

  

  

    public Integer getNoticePeriodDays() {
        return noticePeriodDays;
    }

    public void setNoticePeriodDays(Integer noticePeriodDays) {
        this.noticePeriodDays = noticePeriodDays;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getPreferredLocation() {
        return preferredLocation;
    }

    public void setPreferredLocation(String preferredLocation) {
        this.preferredLocation = preferredLocation;
    }

    public String getResumeFilePath() {
        return resumeName;
    }

    public void setResumeFilePath(String resumeFilePath) {
        this.resumeName = resumeFilePath;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

	public List<Long> getSkills() {
		return skills;
	}

	public void setSkills(List<Long> skills) {
		this.skills = skills;
	}
	public String getResumeBase64() {
		return resumeBase64;
	}
	public void setResumeBase64(String resumeBase64) {
		this.resumeBase64 = resumeBase64;
	}
}
