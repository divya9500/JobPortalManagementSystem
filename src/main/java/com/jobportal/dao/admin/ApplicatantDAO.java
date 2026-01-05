package com.jobportal.dao.admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.jobportal.model.admin.ApplicationFilter;
import com.jobportal.model.admin.ApplicationModel;
import com.jobportal.model.admin.ApplicationModel.Status;
import com.jobportal.util.DBUtil;

public class ApplicatantDAO {
	
	
	
	public String getJobTitleByApplicationId( long applicationId) {
		String title=null;
		
		String sql="select title from job_openings where id=?";
		try(Connection con=DBUtil.getConnection()){
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setLong(1, applicationId);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				title=rs.getString("title");
			}
		} catch (SQLException e) {
			
            throw new RuntimeException("Failed to fetch applicants", e);
		}
		return title;
		
	}

    /* ===============================
       Fetch applicants by job
       =============================== */
    public List<ApplicationModel> getApplicantsByJob(long jobId) {

        String sql =
            "SELECT app.id, app.user_id, u.full_name, u.email, " +
            "       app.application_status, app.applied_at " +
            "FROM job_applications app " +
            "JOIN users u ON app.user_id = u.id " +
            "WHERE app.job_id = ? " +
            "ORDER BY app.applied_at DESC";

        List<ApplicationModel> list = new ArrayList<>();

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, jobId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch applicants", e);
        }

        return list;
    }

    /* ===============================
       Get current status
       =============================== */
    public Status getCurrentStatus(long applicationId) {

        String sql =
            "SELECT application_status " +
            "FROM job_applications WHERE id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, applicationId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Status.valueOf(rs.getString(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch current status", e);
        }

        throw new RuntimeException("Application not found: " + applicationId);
    }

    /* ===============================
       Update status (enum-safe)
       =============================== */
    public void updateStatus(long applicationId, Status newStatus) {

        String sql =
            "UPDATE job_applications " +
            "SET application_status = ? " +
            "WHERE id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newStatus.name());
            ps.setLong(2, applicationId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update status", e);
        }
    }

    /* ===============================
       Insert audit history
       =============================== */
    public void insertAudit(long applicationId,
                            Status oldStatus,
                            Status newStatus,
                            long changedBy) {

        String sql =
            "INSERT INTO application_audit " +
            "(application_id, old_status, new_status, changed_by) " +
            "VALUES (?, ?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, applicationId);
            ps.setString(2, oldStatus.name());
            ps.setString(3, newStatus.name());
            ps.setLong(4, changedBy);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert audit record", e);
        }
    }

    /* ===============================
       Fetch single application
       =============================== */
    public ApplicationModel getApplicantById(long applicationId) {

        String sql =
            "SELECT app.id, app.user_id, u.full_name, u.email, " +
            "       app.application_status, app.applied_at " +
            "FROM job_applications app " +
            "JOIN users u ON app.user_id = u.id " +
            "WHERE app.id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, applicationId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch application", e);
        }

        return null;
    }

    /* ===============================
       Filter-based bulk selection
       =============================== */
    public List<Long> findApplicationIdsByFilter(ApplicationFilter filter) {

        StringBuilder sql = new StringBuilder(
            "SELECT app.id " +
            "FROM job_applications app " +
            "JOIN job_seeker_profiles p ON app.user_id = p.user_id " +
            "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (filter.getJobId() != null) {
            sql.append("AND app.job_id = ? ");
            params.add(filter.getJobId());
        }
        if (filter.getFromStatus() != null) {
            sql.append("AND app.application_status = ? ");
            params.add(filter.getFromStatus().name());
        }
        if (filter.getMinExperience() != null) {
            sql.append("AND p.total_experience_years >= ? ");
            params.add(filter.getMinExperience());
        }
        if (filter.getMaxExperience() != null) {
            sql.append("AND p.total_experience_years <= ? ");
            params.add(filter.getMaxExperience());
        }
        if (filter.getMinGraduationYear() != null) {
            sql.append("AND p.graduation_year >= ? ");
            params.add(filter.getMinGraduationYear());
        }
        if (filter.getAppliedAfter() != null) {
            sql.append("AND app.applied_at >= ? ");
            params.add(filter.getAppliedAfter());
        }

        sql.append("ORDER BY app.applied_at ASC ");

        if (filter.getLimit() != null) {
            sql.append("LIMIT ? ");
            params.add(filter.getLimit());
        }

        List<Long> ids = new ArrayList<>();

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ids.add(rs.getLong(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Filter query failed", e);
        }

        return ids;
    }

    /* ===============================
       Row mapper (DRY)
       =============================== */
    private ApplicationModel mapRow(ResultSet rs) throws SQLException {

        ApplicationModel app = new ApplicationModel();
        app.setApplicationId(rs.getLong("id"));
        app.setUserId(rs.getLong("user_id"));
        app.setName(rs.getString("full_name"));
        app.setEmail(rs.getString("email"));
        app.setApplicationStatus(
            Status.valueOf(rs.getString("application_status")));
        app.setAppliedAt(rs.getTimestamp("applied_at"));
        return app;
    }
}
