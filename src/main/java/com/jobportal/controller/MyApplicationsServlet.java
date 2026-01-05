package com.jobportal.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.jobportal.model.Application;
import com.jobportal.model.User;
import com.jobportal.service.ApplicationService;
import com.jobportal.util.GsonUtil;
@WebServlet("/myApplications")
public class MyApplicationsServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HttpSession session=request.getSession();
		
		User user=(User)session.getAttribute("user");
		int  userId=(int)user.getId();
		
		ApplicationService service=new ApplicationService();
		List<Application> success =service.getMyApplications(userId);
		response.getWriter().print(GsonUtil.createGson().toJson(success));
	}
}
