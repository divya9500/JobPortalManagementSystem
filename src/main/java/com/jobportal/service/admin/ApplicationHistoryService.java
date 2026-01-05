package com.jobportal.service.admin;

import java.util.List;

import com.jobportal.dao.admin.ApplicationAuditDAO;
import com.jobportal.model.admin.ApplicationStatusHistory;

public class ApplicationHistoryService {

    private final ApplicationAuditDAO auditDAO = new ApplicationAuditDAO();

    public List<ApplicationStatusHistory> getHistory(long applicationId) {
        return auditDAO.getHistoryByApplicationId(applicationId);
    }
}

