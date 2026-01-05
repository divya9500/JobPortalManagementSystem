package com.jobportal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.jobportal.util.DBUtil;

public class PasswordResetTokenDAO {

    public void save(long userId, String token, Timestamp expiry)
            throws SQLException {

        String sql ="INSERT INTO password_reset_tokens(user_id, token, expiry_time) " +
            "VALUES (?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ps.setString(2, token);
            ps.setTimestamp(3, expiry);
            ps.executeUpdate();
        }
    }

    public ResultSet findValidToken(String token) throws SQLException {

        String sql ="SELECT * FROM password_reset_tokens " +
            "WHERE token=? AND used=FALSE AND expiry_time > NOW()";

        Connection con = DBUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, token);
        return ps.executeQuery();
    }

    public void markUsed(long id) throws SQLException {

        String sql ="UPDATE password_reset_tokens SET used=TRUE WHERE id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}

