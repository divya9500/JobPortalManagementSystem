package com.jobportal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import com.jobportal.model.Job;
import com.jobportal.model.Job.EmploymentType;
import com.jobportal.model.Job.Status;
import com.jobportal.util.DBUtil;

public class JobDAO {
	public List<Job> getAllJobs(){
		List<Job> list=new ArrayList<>();
		String sql="SELECT * FROM job_openings";
		
		try(Connection con=DBUtil.getConnection()){
			
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			
			
			while(rs.next()) {
				Job job=new Job();
			
			job.setJobId(rs.getInt(1));
			job.setTitle(rs.getString(2));
			job.setDescription(rs.getString(3));
			job.setLocation(rs.getString(4));
			job.setMin_experience (rs.getDouble(5));
			job.setMax_experience(rs.getDouble(6));
			job.setMin_salary(rs.getDouble(7));
			job.setMax_salary(rs.getDouble(8));
			job.setEmpType(EmploymentType.valueOf(rs .getString(9)));
			job.setStatus(Status.valueOf(rs.getString(10)));
			job.setCreated_by(rs.getInt(11));
			job.setCreated_at(rs.getTimestamp(12));
			list.add(job);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public List<Job> getAllOpenJobs(){
		List<Job> list=new ArrayList<>();
		String sql="SELECT * FROM job_openings where status='OPEN'";
		
		try(Connection con=DBUtil.getConnection()){
			
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			
			
			while(rs.next()) {
				Job job=new Job();
			
			job.setJobId(rs.getInt(1));
			job.setTitle(rs.getString(2));
			job.setDescription(rs.getString(3));
			job.setLocation(rs.getString(4));
			job.setMin_experience (rs.getDouble(5));
			job.setMax_experience(rs.getDouble(6));
			job.setMin_salary(rs.getDouble(7));
			job.setMax_salary(rs.getDouble(8));
			job.setEmpType(EmploymentType.valueOf(rs .getString(9)));
			job.setStatus(Status.valueOf(rs.getString(10)));
			job.setCreated_by(rs.getInt(11));
			job.setCreated_at(rs.getTimestamp(12));
			list.add(job);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public List<Job> searchJobs(String role, String location) {
	    List<Job> list = new ArrayList<>();

	    StringBuilder sql = new StringBuilder( "SELECT * FROM job_openings WHERE 1=1" );

	    if (role != null && !role.isBlank()) {
	        sql.append(" AND LOWER(title) LIKE ?");
	    }
	    if (location != null && !location.isBlank()) {
	        sql.append(" AND LOWER(location) LIKE ?");
	    }

	    try (Connection con = DBUtil.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql.toString())) {

	        int index = 1;

	        if (role != null && !role.isBlank()) {
	            ps.setString(index++, "%" + role.toLowerCase() + "%");
	        }
	        if (location != null && !location.isBlank()) {
	            ps.setString(index++, "%" + location.toLowerCase() + "%");
	        }

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Job job = new Job();
	            job.setJobId(rs.getInt(1));
	            job.setTitle(rs.getString(2));
	            job.setDescription(rs.getString(3));
	            job.setLocation(rs.getString(4));
	            job.setMin_experience(rs.getDouble(5));
	            job.setMax_experience(rs.getDouble(6));
	            job.setMin_salary(rs.getDouble(7));
	            job.setMax_salary(rs.getDouble(8));
	            job.setEmpType(EmploymentType.valueOf(rs.getString(9)));
	            job.setStatus(Status.valueOf(rs.getString(10)));
	            job.setCreated_by(rs.getInt(11));
	            job.setCreated_at(rs.getTimestamp(12));
	            list.add(job);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}

		
	public Job getJobbyId(int  id){
		
		Job job=new Job();
		String sql="select * from job_openings where id=?";
		try(Connection con=DBUtil.getConnection()){
			
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			
			
			if(rs.next()) {
				
			
			job.setJobId(rs.getInt(1));
			job.setTitle(rs.getString(2));
			job.setDescription(rs.getString(3));
			job.setLocation(rs.getString(4));
			job.setMin_experience (rs.getDouble(5));
			job.setMax_experience(rs.getDouble(6));
			job.setMin_salary(rs.getDouble(7));
			job.setMax_salary(rs.getDouble(8));
			job.setEmpType(EmploymentType.valueOf(rs .getString(9)));
			job.setStatus(Status.valueOf(rs.getString(10)));
			job.setCreated_by(rs.getInt(11));
			job.setCreated_at(rs.getTimestamp(12));
			
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return job;
	}
		

}
