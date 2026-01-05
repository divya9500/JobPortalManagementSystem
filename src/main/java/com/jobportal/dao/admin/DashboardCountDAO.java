package com.jobportal.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jobportal.exception.DataAccessException;
import com.jobportal.util.DBUtil;

public class DashboardCountDAO {
	public int totalJobs() throws DataAccessException {
		String sql="SELECT COUNT(*) as totalJobs FROM job_openings";
		try(Connection con=DBUtil.getConnection()){
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet set= ps.executeQuery();
			if(set.next()) {
				return set.getInt("totalJobs");
			}
			
		} catch (SQLException e) {
		throw new DataAccessException("total job fetch Failed", e);
		}
		return 0;
	}
	
	public int totalApplicants() throws DataAccessException {
		String sql="SELECT COUNT(*) as totalApplicants FROM job_applications";
		try(Connection con=DBUtil.getConnection()){
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet set= ps.executeQuery();
			if(set.next()) {
				return set.getInt("totalApplicants");
			}
			
		} catch (SQLException e) {
		throw new DataAccessException("total applications fetch Failed", e);
		}
		return 0;
	}
	
	
	public int pendingApplications() throws DataAccessException {
		String sql="SELECT COUNT(*) as pendingApplications FROM job_applications where  application_status='APPLIED'";
		try(Connection con=DBUtil.getConnection()){
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet set= ps.executeQuery();
			if(set.next()) {
				return set.getInt("pendingApplications");
			}
			
		} catch (SQLException e) {
		throw new DataAccessException("Pending Applications fetch Failed", e);
		}
		return 0;
	}
	
	public int totalAccepted() throws DataAccessException {
		String sql="SELECT COUNT(*) as totalAccepted FROM job_applications where application_status <> 'APPLIED' ";
		try(Connection con=DBUtil.getConnection()){
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet set= ps.executeQuery();
			if(set.next()) {
				return set.getInt("totalAccepted");
			}
			
		} catch (SQLException e) {
		throw new DataAccessException("total Accepted fetch Failed", e);
		}
		return 0;
	}
}
