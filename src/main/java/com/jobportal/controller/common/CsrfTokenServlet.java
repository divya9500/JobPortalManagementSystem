package com.jobportal.controller.common;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/csrf-token")
public class CsrfTokenServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession(false);
        String token = "";

        if (session != null) {
            token = (String) session.getAttribute("CSRF_TOKEN");
        }

        
       
        resp.getWriter().write("{\"csrfToken\":\"" + token + "\"}");
    }
}

