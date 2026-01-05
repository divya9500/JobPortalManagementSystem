package com.jobportal.controller;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jobportal.service.JobService;
@WebServlet("/search/jobs")
public class JobSearchServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String role=(request.getParameter("role"));
		String location=request.getParameter("location");
			JobService jobService=new JobService();
			response.getWriter().print(new Gson().toJson(jobService.searchJobs(role,location)));
	}
}
