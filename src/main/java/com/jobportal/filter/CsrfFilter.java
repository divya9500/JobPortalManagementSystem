package com.jobportal.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class CsrfFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String method = request.getMethod();
        String path = request.getRequestURI();

        //  Allow GET always
        if ("GET".equalsIgnoreCase(method)) {
            chain.doFilter(req, res);
            return;
        }

        //  Exclude public endpoints
        if (path.endsWith("/login") ||
            path.endsWith("/register") ||
            path.endsWith("/forgot-password") ||
            path.endsWith("/csrf-token")) {

            chain.doFilter(req, res);
            return;
        }

        HttpSession session = request.getSession(false);

        //  Handle missing session
        if (session == null) {
            sendJsonError(response, 403, "Session expired");
            return;
        }

        String tokenSession = (String) session.getAttribute("CSRF_TOKEN");
        String tokenRequest = request.getHeader("X-CSRF-TOKEN");

        if (tokenSession == null || tokenRequest == null ||
            !tokenSession.equals(tokenRequest)) {

            sendJsonError(response, 403, "Invalid CSRF token");
            return;
        }

        chain.doFilter(req, res);
    }

    // JSON error helper
    private void sendJsonError(HttpServletResponse response,
                               int status,
                               String message) throws IOException {

        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(
            "{\"success\":false,\"message\":\"" + message + "\"}"
        );
    }
}
