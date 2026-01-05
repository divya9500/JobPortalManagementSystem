package com.jobportal.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.jobportal.model.ApiResponse;
import com.jobportal.model.JobSeekerProfile;
import com.jobportal.model.User;
import com.jobportal.service.ApplicationService;
import com.jobportal.util.GsonUtil;



@WebServlet("/apply/job")
public class ApplyJobservlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        Gson gson = GsonUtil.createGson();
        
        JobSeekerProfile profile =gson.fromJson(request.getReader(), JobSeekerProfile.class);

     //  EXPERIENCE HANDLING
     		if (profile.getTotalExperienceYears() == null) {
     		    // Fresher or empty input
     			profile.setTotalExperienceYears(BigDecimal.ZERO);
     		}
        
        
        profile.setUserId(user.getId());

        int jobId = profile.getJobId();

        ApplicationService service = new ApplicationService();
        boolean success = service.applyJob(jobId, profile,user.getEmail());

        ApiResponse apiResponse =success? new ApiResponse(true, "Applied Successfully"): new ApiResponse(false, "Already Applied");


        response.getWriter().print(gson.toJson(apiResponse));
    }
}

