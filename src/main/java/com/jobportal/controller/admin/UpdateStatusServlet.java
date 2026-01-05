package com.jobportal.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jobportal.exception.ValidationException;
import com.jobportal.model.ApiResponse;
import com.jobportal.model.User;
import com.jobportal.service.admin.ApplicationService;
import com.jobportal.service.admin.ApplicationServiceImpl;
import com.jobportal.util.GsonUtil;

@WebServlet("/page/admin/update/status")
public class UpdateStatusServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			JsonObject json=JsonParser.parseReader(req.getReader()).getAsJsonObject();
			long appId=json.get("applicationId").getAsLong();
			String status=json.get("applicationStatus").getAsString();
			User user=(User) req.getSession().getAttribute("user");
			long adminId=user.getId() ;
			resp.setContentType("application/json");
			ApplicationServiceImpl app=new ApplicationServiceImpl();
			try {
				app.updateStatus(appId, status, user);
			} catch (ValidationException e) {
				
				resp.getWriter().write(GsonUtil.createGson().toJson(new ApiResponse(false,e.getMessage())));
			}
			
			resp.getWriter().write(GsonUtil.createGson().toJson(new ApiResponse(true,"Success")));
	}

}
