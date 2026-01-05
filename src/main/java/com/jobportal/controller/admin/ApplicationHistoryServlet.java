package com.jobportal.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jobportal.model.User;
import com.jobportal.model.admin.ApplicationStatusHistory;
import com.jobportal.service.admin.ApplicationHistoryService;

@WebServlet("/page/admin/application/history")
public class ApplicationHistoryServlet extends HttpServlet {

    private final ApplicationHistoryService service = new ApplicationHistoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException {
    	
        User user = (User) req.getSession(false).getAttribute("user");
        if (user == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        long applicationId = Long.parseLong(req.getParameter("applicationId"));

        List<ApplicationStatusHistory> history = service.getHistory(applicationId);

        resp.getWriter().write( new Gson().toJson(history) );
    }
}

