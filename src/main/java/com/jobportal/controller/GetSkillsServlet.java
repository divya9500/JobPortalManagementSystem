package com.jobportal.controller;


import com.google.gson.Gson;
import com.jobportal.util.DBUtil;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/getSkills")
public class GetSkillsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        
        List<Map<String, Object>> skills = new ArrayList<>();

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps =con.prepareStatement("SELECT id, name FROM skills");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> skill = new HashMap<>();
                skill.put("id", rs.getLong("id"));
                skill.put("skillName", rs.getString("name"));
                skills.add(skill);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.getWriter().write(new Gson().toJson(skills));
    }
}
