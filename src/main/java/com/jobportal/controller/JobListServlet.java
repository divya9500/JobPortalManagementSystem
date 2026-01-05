package com.jobportal.controller;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.jobportal.model.User;
import com.jobportal.model.User.Role;
import com.jobportal.service.JobService;
import com.jobportal.util.GsonUtil;
@WebServlet(name = "jobs", urlPatterns = { "/page/secure/jobs", "/page/admin/jobs"})
public class JobListServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		HttpSession session=request.getSession(false);
		User user=(User) session.getAttribute("user");
		JobService jobService=new JobService();
		if(user.getRole()==Role.USER) {
			response.getWriter().print(GsonUtil.createGson().toJson(jobService.getAllOpenJobs()));

		}else {
			response.getWriter().print(GsonUtil.createGson().toJson(jobService.getAllJobs()));

		}
			
			
	}

}
