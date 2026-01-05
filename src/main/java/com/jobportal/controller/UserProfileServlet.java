package com.jobportal.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jobportal.model.JobSeekerProfile;
import com.jobportal.model.User;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.user.ProfileService;
import com.jobportal.util.GsonUtil;

@WebServlet("/user/profile")
@MultipartConfig
public class UserProfileServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
       ProfileService profileService=new ProfileService();

		
	    User user = (User) request.getSession().getAttribute("user");

        JobSeekerProfile profile =profileService.getProfileByUserId(user.getId());
        if (profile != null) {
            profile.setSkills( profileService.getSkillIdsByUserId(user.getId()) );
        }
		response.getWriter().write( GsonUtil.createGson().toJson(profile));
	}

	

}
