package com.jobportal.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.jobportal.dao.ApplicationDAO;
import com.jobportal.dao.ProfileDAO;
import com.jobportal.dao.admin.ApplicatantDAO;
import com.jobportal.model.Application;
import com.jobportal.model.JobSeekerProfile;
import com.jobportal.model.admin.MailContent;
import com.jobportal.util.DBUtil;

public class ApplicationService {

    private ProfileDAO profileDAO = new ProfileDAO();
    private ApplicationDAO applicationDAO = new ApplicationDAO();
    private ResumeService resumeService = new ResumeService();
    private MailContent emailService=new MailContent();
    private ApplicatantDAO jobDAO=new ApplicatantDAO();
    public boolean applyJob(int jobId, JobSeekerProfile profile,String email) {

        Connection con = null;
        String resumePath = null;
        boolean applied=false;

        try {
            con = DBUtil.getConnection();
            con.setAutoCommit(false); //  START TX

            // Save resume 
            resumePath =resumeService.saveResume(profile.getResumeBase64(),profile.getResumeFilePath()   );

            profile.setResumeFilePath(resumePath);
       
            //  Save / update profile
            profileDAO.saveOrUpdate(con, profile);
            
            // save skills
            profileDAO.saveSkills(con,profile.getUserId(),profile.getSkills());

            //  Insert application
            applicationDAO.insertApplication(con, jobId, profile.getUserId() );

            con.commit(); // ALL GOOD
            applied=true;
        } catch (SQLIntegrityConstraintViolationException e) {
            rollback(con);
            deleteResume(resumePath); //  cleanup
            return false;

        } catch (Exception e) {
            rollback(con);
            deleteResume(resumePath);
            throw new RuntimeException(e);

        } finally {
            close(con);
        }
        
        if (applied) {
        	
            emailService.sendApplyConfirmationMail(profile.getFirstName() ,email,jobDAO.getJobTitleByApplicationId(jobId));
        }
        return true;

    }

    private void deleteResume(String path) {
        try {
            if (path != null) Files.deleteIfExists(Paths.get(path));
        } catch (IOException ignored) {}
    }

    private void rollback(Connection con) {
        try {
            if (con != null) con.rollback();
        } catch (SQLException ignored) {}
    }

    private void close(Connection con) {
        try {
            if (con != null) con.close();
        } catch (SQLException ignored) {}
    }
    
    
    public List<Application> getMyApplications(int userId){
		return applicationDAO.getApplicationByUser(userId);
	}
    
    public boolean updateProfile(JobSeekerProfile profile) {
    	  Connection con = null;
          String resumePath = null;
        

          try {
              con = DBUtil.getConnection();
              con.setAutoCommit(false); //  START TX

              // Save resume 
              if (profile.getResumeBase64() != null && !profile.getResumeBase64().isEmpty()) {
              resumePath =resumeService.saveResume(profile.getResumeBase64(),profile.getResumeFilePath()   );
              
              profile.setResumeFilePath(resumePath);
              
              }

              //  Save / update profile
              profileDAO.saveOrUpdate(con, profile);
              
              // save skills
              profileDAO.saveSkills(con,profile.getUserId(),profile.getSkills());

             

              con.commit(); // ALL GOOD
            
          } catch (SQLIntegrityConstraintViolationException e) {
              rollback(con);
              deleteResume(resumePath); //  cleanup
              return false;

          } catch (Exception e) {
              rollback(con);
              deleteResume(resumePath);
              throw new RuntimeException(e);

          } finally {
              close(con);
          }
          
        
          return true;
    }
}
