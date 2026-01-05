package com.jobportal.service.admin;

import com.jobportal.dao.admin.JobPostDAO;
import com.jobportal.exception.DataAccessException;
import com.jobportal.exception.ValidationException;
import com.jobportal.model.admin.JobOpening;

public class JobServiceImpl implements JobService{
	private JobPostDAO jobPostDAO=new JobPostDAO();
	
	public void validate(JobOpening job) throws ValidationException {
		  if(job.getTitle()==null || job.getTitle().trim().isEmpty()) {
			  throw new ValidationException("Job Title Is Required");
		  }
		  
		  if(job.getLocation()==null || job.getLocation().trim().isEmpty()) {
			  throw new ValidationException("Location Is Required");
		  }
		  if(job.getMin_experience()>job.getMax_experience()) {
			  throw new ValidationException("Minimum Experience Cannot be Greater Than Maximum Experience");
		  }
		  if(job.getMin_salary()>job.getMax_salary()) {
			  throw new ValidationException("Minimum Salary Cannot Be Greater Than Maximum Salary");
			  
		  }
	}

	@Override
	public void PostJob(JobOpening job) throws ValidationException, DataAccessException {
	
	  try {
		  validate(job);
		  
		  jobPostDAO.save(job);
	  }catch (Exception e) {
		throw new DataAccessException("Unable To Post Job", e);
	}
		
	}

	@Override
	public void updateJob(long jobId, JobOpening job) throws ValidationException, DataAccessException {
			validate(job);
			try {
				jobPostDAO.update(jobId,job);
			}catch(Exception e) {
				throw new DataAccessException("Unable To update Job", e);
			}
	}

	@Override
	public void deleteJob(long jobId) throws DataAccessException {
		try {
			jobPostDAO.deleteJob(jobId);
		}catch(Exception e) {
			throw new DataAccessException("Unable To Close Job", e);
		}
	}

}
