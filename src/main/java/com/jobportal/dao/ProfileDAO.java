package com.jobportal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jobportal.model.JobSeekerProfile;
import com.jobportal.util.DBUtil;

public class ProfileDAO {

    public void saveOrUpdate(Connection con, JobSeekerProfile p) throws SQLException {
		/*
		 * String sql = "INSERT INTO job_seeker_profiles (" +
		 * "user_id, first_name, last_name, gender, degree, graduation_year,total_experience_years,"
		 * + "current_employer, current_ctc, expected_ctc, notice_period_days," +
		 * "current_location, preferred_location, resume_file_path)" +
		 * " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		 */


    	String sql =
    		    "INSERT INTO job_seeker_profiles ( " +
    		    "user_id, first_name, last_name, gender, degree, graduation_year,total_experience_years, " +
    		    "current_employer, current_ctc, expected_ctc, notice_period_days, " +
    		    "current_location, preferred_location, resume_file_path " +
    		    ") VALUES ( " +
    		    "?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? " +
    		    ") AS new " +
    		    "ON DUPLICATE KEY UPDATE " +
    		    "first_name = new.first_name, " +
    		    "last_name = new.last_name, " +
    		    "gender = new.gender, " +
    		    "degree = new.degree, " +
    		    "graduation_year = new.graduation_year, " +
    		    "total_experience_years=new.total_experience_years,"+
    		    "current_employer = new.current_employer, " +
    		    "current_ctc = new.current_ctc, " +
    		    "expected_ctc = new.expected_ctc, " +
    		    "notice_period_days = new.notice_period_days, " +
    		    "current_location = new.current_location, " +
    		    "preferred_location = new.preferred_location, " +
    		    "resume_file_path = new.resume_file_path";


        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, p.getUserId());
            ps.setString(2, p.getFirstName());
            ps.setString(3, p.getLastName());
            ps.setString(4, p.getGender().name());
            ps.setString(5, p.getDegree());
            ps.setInt(6, p.getGraduationYear());
            ps.setBigDecimal(7, p.getTotalExperienceYears());
            ps.setString(8, p.getCurrentEmployer());
            ps.setBigDecimal(9, p.getCurrent_ctc());
            ps.setBigDecimal(10, p.getExpected_ctc());
            ps.setInt(11, p.getNoticePeriodDays());
            ps.setString(12, p.getCurrentLocation());
            ps.setString(13, p.getPreferredLocation());
            ps.setString(14, p.getResumeFilePath());

            ps.executeUpdate();
        }
    }
    
    public void saveSkills(Connection con, long userId, List<Long> skills) throws SQLException {

        // Remove old skills
        try (PreparedStatement deletePS = con.prepareStatement("DELETE FROM job_seeker_skills WHERE user_id = ?")) {

            deletePS.setLong(1, userId);
            deletePS.executeUpdate();
        }

        //  Insert new skills
        if (skills == null || skills.isEmpty())
        	return;

        try (PreparedStatement insertPS =con.prepareStatement("INSERT INTO job_seeker_skills (user_id, skill_id) VALUES (?, ?)")) {

            for (Long skillId : skills) {
                insertPS.setLong(1, userId);
                insertPS.setLong(2, skillId);
                insertPS.addBatch();
            }
            insertPS.executeBatch();
        }
    }
    
    
    public JobSeekerProfile getProfileByUserId(long userId) {

        String sql = "SELECT * FROM job_seeker_profiles WHERE user_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JobSeekerProfile p = new JobSeekerProfile();

                p.setUserId(userId);
                p.setFirstName(rs.getString("first_name"));
                p.setLastName(rs.getString("last_name"));
                p.setGender(rs.getString("gender") != null? JobSeekerProfile.Gender.valueOf(rs.getString("gender")): null );
                p.setDegree(rs.getString("degree"));
                p.setGraduationYear(rs.getInt("graduation_year"));
                p.setTotalExperienceYears(rs.getBigDecimal("total_experience_years"));
                p.setCurrent_ctc(rs.getBigDecimal("current_ctc"));
                p.setExpected_ctc(rs.getBigDecimal("expected_ctc"));
                p.setNoticePeriodDays(rs.getInt("notice_period_days"));
                p.setCurrentEmployer(rs.getString("current_employer"));
                p.setCurrentLocation(rs.getString("current_location"));
                p.setPreferredLocation(rs.getString("preferred_location"));
                p.setResumeFilePath(rs.getString("resume_file_path"));

                return p;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null; // profile not created yet
    }
    
    public List<Long> getSkillIdsByUserId(long userId) {

        String sql = "SELECT skill_id FROM job_seeker_skills WHERE user_id = ?";
        List<Long> skills = new ArrayList<>();

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                skills.add(rs.getLong("skill_id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return skills;
    }


    
    
}

