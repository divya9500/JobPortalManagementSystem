package com.jobportal.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.jobportal.model.ApiResponse;

public class AuthenticationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		
		HttpSession session=req.getSession(false);
		
		boolean login=(session!=null  &&   session.getAttribute("user")!=null);
		boolean loginRequest=req.getRequestURI().endsWith("SigninPage.html")||
				req.getRequestURI().endsWith("/signin");
		
		  if (login || loginRequest) {
	            chain.doFilter(req, res);
	        } else {
	            
	            res.setContentType("application/json");
	            res.setCharacterEncoding("UTF-8");
	            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

	             Gson gson=new Gson();
	          

	            response.getWriter().write(gson.toJson(new ApiResponse(false,"User not authenticated")));
	        }
		

	}

}
