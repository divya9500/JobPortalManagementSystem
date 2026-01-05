package com.jobportal.service.admin;

import java.util.List;

import com.jobportal.exception.DataAccessException;
import com.jobportal.exception.ValidationException;
import com.jobportal.model.admin.ApplicationModel;

public interface ApplicationService {
		
		List<ApplicationModel> getApplicantsByJob(Long jobId) throws ValidationException, DataAccessException;
}
