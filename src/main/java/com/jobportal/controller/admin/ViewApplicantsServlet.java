package com.jobportal.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jobportal.exception.DataAccessException;
import com.jobportal.exception.ValidationException;
import com.jobportal.model.ApiResponse;
import com.jobportal.model.admin.ApplicationModel;
import com.jobportal.service.admin.ApplicationService;
import com.jobportal.service.admin.ApplicationServiceImpl;
import com.jobportal.util.GsonUtil;
@WebServlet("/page/admin/job/applications")
public class ViewApplicantsServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id=req.getParameter("jobId");
		String apiResponse=null;
		Gson gson=GsonUtil.createGson();
		if(id!=null) {
			long jobId= Long.parseLong(id);
			ApplicationService app=new ApplicationServiceImpl();
			
			try {
				List<ApplicationModel> a=app.getApplicantsByJob(jobId);
				apiResponse=gson.toJson(a);
			} catch (ValidationException | DataAccessException e) {
				apiResponse=gson.toJson(e.getMessage());
			}
		}
		else {
			apiResponse=gson.toJson("jobId Required");
		}
		resp.getWriter().write(apiResponse);
	}

}
