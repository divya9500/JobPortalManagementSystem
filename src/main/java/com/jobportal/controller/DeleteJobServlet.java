package com.jobportal.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jobportal.exception.DataAccessException;
import com.jobportal.model.ApiResponse;
import com.jobportal.service.admin.JobService;
import com.jobportal.service.admin.JobServiceImpl;
import com.jobportal.util.GsonUtil;
@WebServlet("/delete/job")
public class DeleteJobServlet extends HttpServlet {

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String id=request.getParameter("jobId");
		Gson gson=GsonUtil.createGson();
		String apiResponse=null;
		if(id!=null) {
		  long jobId=Long.valueOf(id);
		  JobService jobService=new JobServiceImpl();
		  try {
			jobService.deleteJob(jobId);
			apiResponse=gson.toJson(new ApiResponse(true,"Delete Successful"));
		} catch (Exception e) {
			apiResponse=gson.toJson(new ApiResponse(false,e.getMessage()));

		}
		}else {
			apiResponse=gson.toJson(new ApiResponse(false,"JobId Not Found"));

		}
		response.getWriter().write(apiResponse);
	}

}
