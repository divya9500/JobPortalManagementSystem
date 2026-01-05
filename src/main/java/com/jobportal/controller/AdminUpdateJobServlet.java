package com.jobportal.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jobportal.exception.DataAccessException;
import com.jobportal.exception.ValidationException;
import com.jobportal.model.ApiResponse;
import com.jobportal.model.admin.JobOpening;
import com.jobportal.service.admin.JobService;
import com.jobportal.service.admin.JobServiceImpl;
import com.jobportal.util.GsonUtil;
@WebServlet("/update/job")
public class AdminUpdateJobServlet extends HttpServlet {
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	   
	    Gson gson = GsonUtil.createGson();

	   
	    BufferedReader reader = request.getReader();
	    JobOpening job = gson.fromJson(reader, JobOpening.class);

	    long jobId = job.getJobId(); 
	   
	    if (jobId == 0) {
	        response.getWriter().write(gson.toJson(new ApiResponse(false, "JobId Not Found")) );
	        return;
	    }

	    JobService service = new JobServiceImpl();

	    try {
	        service.updateJob(jobId, job);
	        response.getWriter().write(gson.toJson(new ApiResponse(true, "Successfully Updated")) );
	    } catch (Exception e) {
	        response.getWriter().write(gson.toJson(new ApiResponse(false, e.getMessage())) );
	    }
	}

}
