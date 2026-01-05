package com.jobportal.controller.admin;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.jobportal.exception.DataAccessException;
import com.jobportal.exception.ValidationException;
import com.jobportal.model.ApiResponse;
import com.jobportal.model.User;
import com.jobportal.model.admin.JobOpening;
import com.jobportal.service.admin.JobService;
import com.jobportal.service.admin.JobServiceImpl;
import com.jobportal.util.GsonUtil;
@WebServlet("/admin/jobPost")
public class AdminJobPostServlet extends HttpServlet {

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		BufferedReader reader=req.getReader();
		Gson gson=GsonUtil.createGson();
		JobOpening jobOpening=gson.fromJson(reader, JobOpening.class);
		JobService jobService=new JobServiceImpl();
		String apiResponse=null;
		try {
			 HttpSession session=req.getSession();
			 User user=(User) session.getAttribute("user");
			 if(user==null) {
					apiResponse=gson.toJson(new ApiResponse(false,"Invaild admin"));
					resp.getWriter().write(apiResponse);

					return;
			 }
			long createdBy=user.getId();
			
			jobOpening.setCreated_by((int) createdBy);
			jobService.PostJob(jobOpening);
			apiResponse=gson.toJson(new ApiResponse(true,"Post Successful"));
		} catch (ValidationException | DataAccessException e) {
			apiResponse=gson.toJson(new ApiResponse(false,e.getMessage()));

		}
		resp.getWriter().write(apiResponse);
	}
	
}
