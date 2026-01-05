package com.jobportal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.naming.spi.DirStateFactory.Result;

import com.jobportal.exception.DataAccessException;
import com.jobportal.model.User;
import com.jobportal.model.User.Role;
import com.jobportal.util.DBUtil;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean registerUser(User user) throws DataAccessException {

        String sql = "INSERT INTO users "
                   + "(full_name, email, mob_num, password_hash, role, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getMobNum());
            ps.setString(4, user.getPasswordHash());
            ps.setString(5, user.getRole().name());
            ps.setString(6, user.getStatus().name());

           

            ps.executeUpdate();
            return true;

        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            throw new DataAccessException("Email already exists", e);

        } catch (SQLException e) {
            throw new DataAccessException("Database error while registering user", e);
        }
      
    }
    
    public User login(String email,String password) throws DataAccessException {

        String sql = "select * from users where"
                   + " email=? and password_hash=?";
                   

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

           
            ps.setString(1,email);
           
            ps.setString(2, password);
          ResultSet set=  ps.executeQuery();
           if(set.next()) {
        	   User user=new User();
        	   user.setId(set.getLong(1));
        	  user.setFullName(set.getString("full_name"));
        	  user.setEmail(set.getString("email"));
        	  user.setMobNum(set.getString("mob_num"));
        	  user.setRole(Role.valueOf(set.getString("role")));
        	  return user;
        	   
           }

        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            throw new DataAccessException("Login Failed",e);
          
        }
        return null;
      
      
    }
    
    
    public void updatePassword(long userId, String hashedPassword)
            throws SQLException {

        String sql = "UPDATE users SET password_hash=? WHERE id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, hashedPassword);
            ps.setLong(2, userId);
            ps.executeUpdate();
        }
    }
    
    
    public User findByEmail(String email) {
        String sql = "SELECT id, email FROM users WHERE email=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setEmail(rs.getString("email"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

  

    
}
