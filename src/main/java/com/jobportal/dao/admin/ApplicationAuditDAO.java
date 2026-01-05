package com.jobportal.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jobportal.model.admin.ApplicationStatusHistory;
import com.jobportal.util.DBUtil;

public class ApplicationAuditDAO {

    public List<ApplicationStatusHistory> getHistoryByApplicationId(long applicationId) {

        String sql =
            "SELECT a.old_status, a.new_status, u.full_name, a.changed_at " +
            "FROM application_audit a " +
            "JOIN users u ON a.changed_by = u.id " +
            "WHERE a.application_id = ? " +
            "ORDER BY a.changed_at DESC";

        List<ApplicationStatusHistory> list = new ArrayList<>();

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, applicationId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ApplicationStatusHistory h = new ApplicationStatusHistory();
                h.setOldStatus(rs.getString(1));
                h.setNewStatus(rs.getString(2));
                h.setChangedBy(rs.getString(3));
                h.setChangedAt(rs.getTimestamp(4));
                list.add(h);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch status history", e);
        }

        return list;
    }
}
