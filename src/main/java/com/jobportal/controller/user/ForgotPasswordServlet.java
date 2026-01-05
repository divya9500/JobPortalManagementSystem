package com.jobportal.controller.user;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jobportal.exception.ValidationException;
import com.jobportal.model.ApiResponse;
import com.jobportal.service.user.UserService;
import com.jobportal.util.GsonUtil;

@WebServlet("/forgot/password")
public class ForgotPasswordServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        Gson gson =GsonUtil.createGson();
    
        JsonObject json = JsonParser.parseReader(req.getReader()).getAsJsonObject();

        String email = json.get("email").getAsString();

        try {
            UserService userService = new UserService();
            userService.sendPasswordResetLink(email);

            res.getWriter().write( gson.toJson(new ApiResponse(true, "Password reset link sent to email")));

        } catch (ValidationException e) {
            res.getWriter().write( gson.toJson(new ApiResponse(false, e.getMessage())));
        } catch (SQLException e) {
			
            res.getWriter().write( gson.toJson(new ApiResponse(false, e.getMessage())));
		}
    }
}

