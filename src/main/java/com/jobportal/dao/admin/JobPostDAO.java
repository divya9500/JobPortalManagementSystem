package com.jobportal.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jobportal.exception.DataAccessException;
import com.jobportal.model.admin.JobOpening;
import com.jobportal.util.DBUtil;

public class JobPostDAO {
	public void save(JobOpening job) throws DataAccessException {
		String sql="Insert into job_openings (title, description, "
				+ "location, experience_min, experience_max, salary_min,"
				+ " salary_max, employment_type,created_by) values(?,?,?,?,?,?,?,?,?)";
		try(Connection con=DBUtil.getConnection()){
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, job.getTitle());
			ps.setString(2, job.getDescription());
			ps.setString(3, job.getLocation());
			ps.setDouble(4, job.getMin_experience());
			ps.setDouble(5, job.getMax_experience());
			ps.setDouble(6,job.getMin_salary());
			ps.setDouble(7, job.getMax_salary());
			ps.setString(8, job.getEmpType().name());
			ps.setInt(9,job.getCreated_by());
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new DataAccessException("Error while Saving Job", e);
		}
				
	}
	public void update(Long jobId,JobOpening job) throws DataAccessException {
		String sql="update job_openings set title=?, description=?,"
				+"location=?, experience_min=?, experience_max=?, salary_min=?,"
				+" salary_max=?, employment_type=? ,status=? where id=?";
		try(Connection con=DBUtil.getConnection()){
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, job.getTitle());
			ps.setString(2, job.getDescription());
			ps.setString(3, job.getLocation());
			ps.setDouble(4, job.getMin_experience());
			ps.setDouble(5, job.getMax_experience());
			ps.setDouble(6,job.getMin_salary());
			ps.setDouble(7, job.getMax_salary());
			ps.setString(8, job.getEmpType().name());
			ps.setString(9,job.getStatus().name());
			ps.setLong(10, jobId);
			ps.executeUpdate();
		} catch (Exception e) {
			throw new DataAccessException("Update Failed", e);
		}
		
	}
	
	public void deleteJob(long jobId) throws DataAccessException {
		String sql="delete from job_openings where id=?";
		try(Connection con=DBUtil.getConnection()){
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setLong(1, jobId);
			stmt.executeUpdate();
			
		} catch (Exception e) {
			throw new DataAccessException("Delete Failed", e);

		}
	}
}
