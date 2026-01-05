package com.jobportal.controller.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jobportal.model.ApiResponse;
import com.jobportal.service.admin.DashboardCountService;
import com.jobportal.util.GsonUtil;
@WebServlet("/admin/dashboard/counts")
public class DashboardCountServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Gson gson=GsonUtil.createGson();
		Map<String, Integer> count=new HashMap<>();
		DashboardCountService service=new DashboardCountService();
		try {
		count.put("totalJobs", service.totalJobs());
		count.put("totalApplicants",service.totalApplicants());
		count.put("totalAccepted", service.totalAccpted());
		count.put("pendingApplications", service.pendingApplications());
		resp.getWriter().write(gson.toJson(count));
		}catch (Exception e) {
			resp.getWriter().write(gson.toJson(new ApiResponse(false,e.getMessage())));
		}
	}

}
