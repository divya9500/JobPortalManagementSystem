package com.jobportal.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class ExceptionFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain)
                         throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;

        try {
            chain.doFilter(req, res);

        } catch (Throwable t) {   // catch ALL errors

            t.printStackTrace(); // replace with logger in real apps

            if (!response.isCommitted()) {
                req.setAttribute("errorMessage", t.getMessage());
                req.getRequestDispatcher("/error.html")
                   .forward(req, res);
            }
        }
    }
}

