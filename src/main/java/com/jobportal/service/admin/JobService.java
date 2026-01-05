package com.jobportal.service.admin;

import com.jobportal.exception.DataAccessException;

import com.jobportal.exception.ValidationException;
import com.jobportal.model.admin.JobOpening;


public interface JobService {
	void PostJob(JobOpening job)throws  ValidationException, DataAccessException;
	void updateJob(long jobId,JobOpening job) throws ValidationException, DataAccessException;
	void deleteJob(long jobId) throws DataAccessException;
	


}
