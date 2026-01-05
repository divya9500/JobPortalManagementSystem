package com.jobportal.service.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import com.jobportal.dao.PasswordResetTokenDAO;
import com.jobportal.dao.UserDAO;
import com.jobportal.dao.UserDAOImpl;
import com.jobportal.exception.ApplicationException;
import com.jobportal.exception.DataAccessException;
import com.jobportal.exception.JobPoratlException;
import com.jobportal.exception.ValidationException;
import com.jobportal.model.LoginRequest;
import com.jobportal.model.User;
import com.jobportal.util.MailUtil;

public class UserService {
	public boolean validatePassword(String pwd) throws ValidationException {
		if(pwd==null || pwd.isEmpty()) {
			throw new ValidationException("Password is Required");
		}
		if(pwd.length()<6) {
			throw new ValidationException("Password Must Be Atleast 8 Characters ");
		}
		return true;
	}
	
	public boolean validateMail(String mail) throws ValidationException {
		  if (mail == null ||mail.isEmpty()) {
		        throw new ValidationException("Email is required");
		    }

		    if (!mail.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
		        throw new ValidationException("Invalid email format");
		    }
		  return true;
	}
	
	public boolean valiadateMobNum(String mobNum) throws ValidationException {
		 if (!mobNum.matches("^[6-9]\\d{9}$")) {
		        throw new ValidationException("Invalid mobile number");
		    }
		return true;
		  
	}

	
	public boolean register(User user) throws   ValidationException, JobPoratlException {
		
		if(user.getFullName()==null || user.getFullName().trim().isEmpty()) {
			throw new ValidationException("Full Name is Required");
		}
		if(user.getFullName().length()<3) {
			throw new ValidationException("Full Name Must Be Atleast 3 Characters");
		}
		
		boolean pwd=validatePassword(user.getPasswordHash());
		boolean email=validateMail(user.getEmail());
		boolean mobNum=valiadateMobNum(user.getMobNum());
		
		
	   
	    UserDAO userDAOImpl=new UserDAOImpl();
	    try {
	    	return userDAOImpl.registerUser(user);
			
		} catch (DataAccessException e) {
			throw new JobPoratlException("Registration Failed. Please Try Again");
		} 
	}
	public User login(LoginRequest login) throws ValidationException, JobPoratlException {
		validateMail(login.getemail());
		validatePassword(login.getPassword());
		UserDAOImpl userDAO=new UserDAOImpl();
		try {
			User user= userDAO.login(login.getemail(),login.getPassword());
			if(user==null) {
				throw new ValidationException("Invalid Credentials");
			}
			return user;
		}
		catch (ValidationException | DataAccessException  e) {
			throw new JobPoratlException(e.getMessage());

		}
		
	}
	
	public void sendPasswordResetLink(String email)
	        throws ValidationException, SQLException {
		UserDAOImpl userDAO=new UserDAOImpl();
		PasswordResetTokenDAO tokenDAO=new PasswordResetTokenDAO();
	    User user = userDAO.findByEmail(email);
	    
	    if (user == null) {
	        throw new ValidationException("If email exists, link will be sent");
	    }

	    String token = UUID.randomUUID().toString();
	    Timestamp expiry =
	        Timestamp.valueOf(LocalDateTime.now().plusMinutes(30));

	    tokenDAO.save(user.getId(), token, expiry);

	   
	    String link =
	    		 "http://localhost:8080/JobPortalManagementSystem/auth/resetPassword.html?token=" + token;

	    		String subject = "Reset Your JobPortal Password";

	    		String body =
	    		 "Hello,\n\n" +
	    		 "Click the link below to reset your password:\n\n" +
	    		 link + "\n\n" +
	    		 "This link is valid for 30 minutes.\n\n" +
	    		 "If you did not request this, ignore this email.";

	    		MailUtil.sendMail(email, subject, body);

	   
	}
	
	
	public void resetPassword(String token, String newPassword)
	        throws ValidationException {
		UserDAOImpl userDAO=new UserDAOImpl();
		PasswordResetTokenDAO tokenDAO=new PasswordResetTokenDAO();
	    try {
	        ResultSet rs = tokenDAO.findValidToken(token);

	        if (!rs.next()) {
	            throw new ValidationException("Invalid or expired token");
	        }

	        long tokenId = rs.getLong("id");
	        long userId = rs.getLong("user_id");

	       // String hashed = PasswordUtil.hash(newPassword);

	        userDAO.updatePassword(userId, newPassword);
	        tokenDAO.markUsed(tokenId);

	    } catch (SQLException e) {
	        throw new ValidationException("Unable to reset password");
	    }
	}


}
