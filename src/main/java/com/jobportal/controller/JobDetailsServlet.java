package com.jobportal.controller;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jobportal.service.JobService;
import com.jobportal.util.GsonUtil;
//@WebServlet("/job/details")
@WebServlet(name = "job/details",urlPatterns = {"/page/secure/job/details","/page/admin/job/details"})
public class JobDetailsServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		int jobId=Integer.valueOf(request.getParameter("jobId"));
			JobService jobService=new JobService();
			response.getWriter().print(GsonUtil.createGson().toJson(jobService.getJobByIJob(jobId)));
	}
}
