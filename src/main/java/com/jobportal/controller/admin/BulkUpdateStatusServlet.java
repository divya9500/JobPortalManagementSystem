package com.jobportal.controller.admin;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jobportal.exception.ValidationException;
import com.jobportal.model.ApiResponse;
import com.jobportal.model.User;
import com.jobportal.model.admin.ApplicationFilter;
import com.jobportal.model.admin.ApplicationModel.Status;
import com.jobportal.service.admin.ApplicationServiceImpl;
import com.jobportal.util.GsonUtil;

@WebServlet("/page/admin/update/status/filter")
public class BulkUpdateStatusServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        JsonObject json =  JsonParser.parseReader(req.getReader()).getAsJsonObject();

        ApplicationFilter filter = new ApplicationFilter();

        filter.setJobId(json.get("jobId").getAsLong());

        filter.setFromStatus( Status.valueOf(json.get("fromStatus").getAsString()));

        if (json.has("minExperience") && !json.get("minExperience").isJsonNull()) {
            filter.setMinExperience(json.get("minExperience").getAsInt());
        }

        if (json.has("minGraduationYear") && !json.get("minGraduationYear").isJsonNull()) {
            filter.setMinGraduationYear(json.get("minGraduationYear").getAsInt());
        }

        filter.setLimit(json.get("limit").getAsInt());

        String newStatus = json.get("applicationStatus").getAsString();
        User admin =(User) req.getSession().getAttribute("user");
        ApplicationServiceImpl service = new ApplicationServiceImpl();
   	    Gson gson=GsonUtil.createGson();

        try {
        	 Set<Long> updatedIds=  service.updateStatusByFilter(filter,newStatus,admin);
        
            resp.getWriter().write(gson.toJson(new BulkUpdateResponse(true,updatedIds.size(),updatedIds)));
          
        } catch (ValidationException e) {
         resp.getWriter().write(gson.toJson(new ApiResponse(false,e.getMessage())));
        }
    }
}
