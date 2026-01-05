package com.jobportal.service.admin;

import com.jobportal.dao.admin.DashboardCountDAO;
import com.jobportal.exception.DataAccessException;

public class DashboardCountService {
	DashboardCountDAO dashboardCountDAO=new DashboardCountDAO();
	
	public int totalJobs() throws DataAccessException {
		return dashboardCountDAO.totalJobs();
	}
	
	public int totalApplicants() throws DataAccessException {
		return dashboardCountDAO.totalApplicants();
	}
	
	public int totalAccpted() throws DataAccessException {
		return dashboardCountDAO.totalAccepted();
	}
	public int pendingApplications() throws DataAccessException {
		return dashboardCountDAO.pendingApplications();
	}
	
}
