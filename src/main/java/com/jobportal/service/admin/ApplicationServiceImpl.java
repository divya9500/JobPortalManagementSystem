package com.jobportal.service.admin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.jobportal.dao.admin.ApplicatantDAO;
import com.jobportal.exception.DataAccessException;
import com.jobportal.exception.ValidationException;
import com.jobportal.model.User;
import com.jobportal.model.admin.ApplicationFilter;
import com.jobportal.model.admin.ApplicationModel;
import com.jobportal.model.admin.ApplicationModel.Status;
import com.jobportal.model.admin.MailContent;

public class ApplicationServiceImpl implements ApplicationService {
	private ApplicatantDAO applicatantDAO=new ApplicatantDAO();

	@Override
	public List<ApplicationModel> getApplicantsByJob(Long jobId) throws ValidationException, DataAccessException {
	   if(jobId==null) {
		   throw new ValidationException("JobId Required");
		   
	   }
	   
	   try {
		   return  applicatantDAO.getApplicantsByJob( jobId);
		 
	   }catch(Exception e) {
		   throw new DataAccessException("Get Applicants Failed",e);
	   }
		
	}
	


	private void updateStatusInternal(long applicationId,Status newStatus,User admin) throws ValidationException {

	    Status currentStatus = applicatantDAO.getCurrentStatus(applicationId);

	    if (!PipelineRules.isVaild(currentStatus, newStatus)) {
	        throw new ValidationException("Invalid Transition: " + currentStatus + " -> " + newStatus);
	    }

	    applicatantDAO.updateStatus(applicationId, newStatus);
	    applicatantDAO.insertAudit(applicationId, currentStatus, newStatus, admin.getId() );
	}
	public void updateStatus(long applicationId,String nextStatus,User admin) throws ValidationException {

	    Status newStatus = Status.valueOf(nextStatus);

	    updateStatusInternal(applicationId, newStatus, admin);

	  
	    new MailContent().update(applicationId,admin);
	}
	public Set<Long> updateStatusByFilter(ApplicationFilter filter,String newStatus,User admin) throws ValidationException {

	    List<Long> applicationIds = applicatantDAO.findApplicationIdsByFilter(filter);

	    if (applicationIds.isEmpty()) {
	        throw new ValidationException("No applications matched the selected criteria");
	    }

	    Status status = Status.valueOf(newStatus);
	    Set<Long> updatedIds = new LinkedHashSet<>();

	    for (Long applicationId : new LinkedHashSet<>(applicationIds)) {
	        updateStatusInternal(applicationId, status, admin);

	        //  send mail ONLY for this application
	        new MailContent().update(applicationId, admin);

	        updatedIds.add(applicationId);
	    }

	    return updatedIds;
	}



}
