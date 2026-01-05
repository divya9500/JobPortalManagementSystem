package com.jobportal.controller.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.jobportal.model.ApiResponse;
import com.jobportal.model.User;
import com.jobportal.model.User.Role;
import com.jobportal.util.GsonUtil;


@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		HttpSession session=request.getSession(false);
		
		User user=(User)session.getAttribute("user");
		
	
	        
	        
	        String contextPath=request.getContextPath();
	        Role r= user.getRole();
            
            
        	session.invalidate();// destroy the session
    		
    		//prevent  back button cache 
    	      response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    	        response.setHeader("Pragma", "no-cache");
    	        response.setDateHeader("Expires", 0);
    	        
    	        String redirectUrl =((Role.ADMIN ==r)||(Role.SUPER_ADMIN==r)||(Role.HR==r))
                        ? contextPath + "/auth/signinPage.html?type=admin"
                        : contextPath + "/auth/signinPage.html";
                

		
		
		
		response.getWriter().write(GsonUtil.createGson().toJson(new ApiResponse(true,"Logout Successfull",redirectUrl)));
	}

	

}
