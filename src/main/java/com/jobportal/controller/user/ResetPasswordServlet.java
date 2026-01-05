package com.jobportal.controller.user;

import java.io.IOException;

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

@WebServlet("/reset/password")
public class ResetPasswordServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        Gson gson = GsonUtil.createGson();
        JsonObject json =  JsonParser.parseReader(req.getReader()).getAsJsonObject();

        String token = json.get("token").getAsString();
        String newPassword = json.get("newPassword").getAsString();

        try {
            UserService userService = new UserService();
            userService.resetPassword(token, newPassword);

            res.getWriter().write( gson.toJson(new ApiResponse(true, "Password reset successful")) );

        } catch (ValidationException e) {
            res.getWriter().write( gson.toJson(new ApiResponse(false, e.getMessage())));
        }
    }
}

