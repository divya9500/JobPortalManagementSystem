package com.jobportal.controller;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jobportal.model.User;
import com.jobportal.util.GsonUtil;

@WebServlet("/user/me")
public class UserMeServlet extends HttpServlet {

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    User user = (User) req.getSession().getAttribute("user");

    
    resp.getWriter().write(GsonUtil.createGson().toJson(user));
  }
}

