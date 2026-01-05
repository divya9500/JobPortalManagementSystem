package com.jobportal.controller.user;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import com.google.gson.Gson;
import com.jobportal.exception.JobPoratlException;
import com.jobportal.exception.ValidationException;
import com.jobportal.model.ApiResponse;
import com.jobportal.model.LoginRequest;
import com.jobportal.model.User;
import com.jobportal.model.User.Role;
import com.jobportal.service.user.UserService;
import com.jobportal.util.GsonUtil;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("application/json");

        UserService userService = new UserService();
        Gson gson = GsonUtil.createGson();

        try {
            LoginRequest login = gson.fromJson(req.getReader(), LoginRequest.class);

            User user = userService.login(login);

         
            if (user == null) {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                ApiResponse response =  new ApiResponse(false, "Invalid email or password");
                res.getWriter().write(gson.toJson(response));
                return;
            }

            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);

            String contextPath = req.getContextPath();
           Role r= user.getRole();
            String redirectUrl =((Role.ADMIN ==r)||(Role.SUPER_ADMIN==r)||(Role.HR==r))
                    ? contextPath + "/page/admin/dashboard.html"
                    : contextPath + "/page/secure/userHomePage.html";

            ApiResponse response =new ApiResponse(true, "Login Successful", redirectUrl);

            res.getWriter().write(gson.toJson(response));

        } catch (JobPoratlException | ValidationException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ApiResponse response = new ApiResponse(false, e.getMessage());
            res.getWriter().write(gson.toJson(response));
        }
    }
}
