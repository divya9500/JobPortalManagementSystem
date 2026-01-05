package com.jobportal.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import com.jobportal.model.Application;
import com.jobportal.model.JobSeekerProfile;
import com.jobportal.util.DBUtil;

public class ApplicationDAO {
	
	public void insertApplication(Connection con, int jobId, long userId)
	        throws SQLException {

	    String sql = "INSERT INTO job_applications (user_id, job_id) VALUES (?, ?)";

	    try (PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setLong(1, userId);
	        ps.setInt(2, jobId);
	        ps.executeUpdate();
	    }
	}

	

	
	
				
	
		
	public List<Application> getApplicationByUser(int userId){
		List<Application> list=new ArrayList<>();
		String sql =
			    "SELECT a.application_status, j.title " +
			    "FROM job_applications a " +
			    "JOIN job_openings j ON a.job_id = j.id " +
			    "WHERE a.user_id = ?";
		
		try(Connection con=DBUtil.getConnection()){
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs=ps .executeQuery();
			while(rs.next()) {
				Application application=new Application();
				application.setTitle(rs.getString("title"));
				application.setStatus(rs.getString("application_status"));
				list.add(application);
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
