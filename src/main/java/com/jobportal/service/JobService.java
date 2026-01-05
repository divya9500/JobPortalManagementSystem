package com.jobportal.service;

import java.util.List;

import com.jobportal.dao.JobDAO;
import com.jobportal.model.Job;

public class JobService {
	private JobDAO jobDAO=new JobDAO();
	public List<Job> getAllJobs(){
		return jobDAO.getAllJobs();
	}

	public List<Job> getAllOpenJobs(){
		return jobDAO.getAllOpenJobs();
	}
	public List<Job> searchJobs(String role,String location){
		return jobDAO.searchJobs(role,location);
	}
	
	public Job getJobByIJob(int jobId) {
		return jobDAO.getJobbyId(jobId);
	}
}
