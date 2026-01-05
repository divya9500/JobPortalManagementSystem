package com.jobportal.controller.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.jobportal.exception.JobPoratlException;
import com.jobportal.exception.ValidationException;
import com.jobportal.model.ApiResponse;
import com.jobportal.model.User;
import com.jobportal.service.user.UserService;
import com.jobportal.util.GsonUtil;
import com.jobportal.util.LocalDateTimeAdapter;
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private UserService userService=new UserService();
	

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws IOException {

	   
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");

	    Gson gson =GsonUtil.createGson();
	            
	    try {
	        BufferedReader reader = request.getReader();
	        User user = gson.fromJson(reader, User.class);

	       

	        boolean status = userService.register(user);

	        ApiResponse responseObj = status
	                ? new ApiResponse(true, "Registration Successful")
	                : new ApiResponse(false, "Registration Failed");

	        response.getWriter().write(gson.toJson(responseObj));

	    } catch (Exception e) {
	        response.getWriter().write(gson.toJson(new ApiResponse(false, e.getMessage())));
	    }
	}

		
}
