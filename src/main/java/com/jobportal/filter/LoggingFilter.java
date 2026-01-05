package com.jobportal.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class LoggingFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		long startTime=System.currentTimeMillis();
		chain.doFilter(request, response);
		long endTime=System.currentTimeMillis();
		System.out.println(req.getMethod()+" "+req.getRequestURI()+" Time :"+(endTime-startTime)+"ms");

	}

}
