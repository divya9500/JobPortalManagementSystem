package com.jobportal.controller;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jobportal.model.ApiResponse;
import com.jobportal.model.JobSeekerProfile;
import com.jobportal.model.User;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.user.ProfileService;
import com.jobportal.util.GsonUtil;


@WebServlet("/user/profile/update")
@MultipartConfig
public class UserProfileUpdateServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	
			
		    User user = (User) request.getSession().getAttribute("user");

		    Gson gson=GsonUtil.createGson();
		    
		    
		 JobSeekerProfile profile=   gson.fromJson(request.getReader(), JobSeekerProfile.class);
		// ---- EXPERIENCE HANDLING ----
  		if (profile.getTotalExperienceYears() == null) {
  		    // Fresher or empty input
  			profile.setTotalExperienceYears(BigDecimal.ZERO);
  		}
  		profile.setUserId(user.getId());
  	  ApplicationService service = new ApplicationService();
      boolean success = service.updateProfile( profile);

      ApiResponse apiResponse =
          success
              ? new ApiResponse(true, "Updated Successfully")
              : new ApiResponse(false, "Update failed");

     
      response.getWriter().print(gson.toJson(apiResponse));
	
	
	}

}
