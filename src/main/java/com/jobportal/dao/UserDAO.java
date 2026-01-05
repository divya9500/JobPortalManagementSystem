package com.jobportal.dao;

import java.sql.SQLException;

import com.jobportal.exception.DataAccessException;
import com.jobportal.model.User;

public interface UserDAO {
	boolean registerUser(User user) throws DataAccessException;
}
