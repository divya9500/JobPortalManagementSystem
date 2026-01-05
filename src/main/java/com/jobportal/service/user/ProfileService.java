package com.jobportal.service.user;

import java.util.List;

import com.jobportal.dao.ProfileDAO;
import com.jobportal.model.JobSeekerProfile;

public class ProfileService {

    private ProfileDAO profileDAO = new ProfileDAO();

    public JobSeekerProfile getProfileByUserId(long userId) {
        return profileDAO.getProfileByUserId(userId);
    }
    
    public List<Long> getSkillIdsByUserId(long userId) {
        return profileDAO.getSkillIdsByUserId(userId);
    }
    
    
}
