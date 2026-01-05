package com.jobportal.filter;



import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.jobportal.model.ApiResponse;
import com.jobportal.model.User;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        //  STEP 1: PUBLIC REQUEST  ALLOW AND EXIT
        if (isPublic(uri)) {
            chain.doFilter(request, response);
            return; //  THIS IS CRITICAL
        }

        //  STEP 2: AUTHENTICATION CHECK
        HttpSession session = req.getSession(false);
        User user = (session != null)
                ? (User) session.getAttribute("user")
                : null;

        if (user == null) {
            res.sendRedirect(req.getContextPath()
                    + "/auth/signinPage.html");
            return;
        }

        // STEP 3: AUTHORIZATION (ROLE CHECK)
        User.Role role = user.getRole();
        if (uri.contains("/page/admin/")
                &&!(role == User.Role.ADMIN
                        || role == User.Role.SUPER_ADMIN
                        || role == User.Role.HR)) {
            res.sendRedirect(req.getContextPath()
                    + "/unauthorized.html");
            return;
        }

        if (uri.contains("/page/secure/")
                && !"USER".equals(user.getRole().name())) {
            res.sendRedirect(req.getContextPath()
                    + "/unauthorized.html");
            return;
        }

        // STEP 4: ALLOW
        chain.doFilter(request, response);
    }

    private boolean isPublic(String uri) {
        return uri.contains("/auth/") ||              // signin, forgot, reset pages
               uri.contains("/login") ||               // login API
               uri.contains("/forgot/password") ||     // forgot API
               uri.contains("/reset/password") ||      // reset API
               uri.contains("/css/") ||
               uri.contains("/js/") ||
               uri.contains("/images/");
    }
}

