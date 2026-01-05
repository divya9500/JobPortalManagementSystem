package com.jobportal.model.admin;

import com.jobportal.dao.admin.ApplicatantDAO;
import com.jobportal.model.User;
import com.jobportal.model.admin.ApplicationModel.Status;
import com.jobportal.util.MailUtil;

public class MailContent {

      // APPLY CONFIRMATION MAIL

    public void sendApplyConfirmationMail(String candidateName, String email,  String jobTitle) {

        String body = buildApplyConfirmationBody(candidateName, jobTitle);

        // Async send
        new Thread(() -> MailUtil.sendMail( email,"Application Submitted Successfully", body  )).start();
    }

    private String buildApplyConfirmationBody( String candidateName, String jobTitle) {
    
        return """
            Dear %s,

            Thank you for applying for the position "%s".

            We have successfully received your application.
            Our recruitment team will review your profile and
            contact you if you are shortlisted.

            You can track your application status from
            the "My Applications" section.

            Best regards,
            HR Team
            
            """.formatted(candidateName, jobTitle);
    }

      // STATUS UPDATE MAIL (ADMIN ACTION)

    public void sendStatusUpdateMail( String candidateName, String email, String jobTitle,Status status,   String adminName) {
 
        String mailBody =  buildStatusUpdateBody(candidateName, jobTitle, status, adminName);

        // Async send
        new Thread(() -> MailUtil.sendMail( email,"Update on Your Job Application", mailBody  ) ).start();
    }

    private String buildStatusUpdateBody(String candidateName,String jobTitle, Status status, String adminName) {

        return """
            Dear %s,

            We hope this message finds you well.

            This is to inform you that the status of your application
            for the position "%s" has been updated.

            Current Application Status:
            %s

            %s

            Warm regards,
            %s
            HR Team
          
            """.formatted( candidateName,jobTitle,status.name().replace("_", " "),getStatusMessage(status), adminName );
    }

    private String getStatusMessage(Status status) {

        switch (status) {

            case SHORTLISTED:
                return "Congratulations! Your profile has been shortlisted.";

            case INTERVIEW_SCHEDULED:
                return "Your interview has been scheduled. Our team will contact you shortly.";

            case INTERVIEW_PASSED:
                return "You have successfully cleared the interview.";

            case INTERVIEW_FAILED:
                return "Thank you for attending the interview. We wish you success ahead.";

            case OFFERED:
                return "We are pleased to offer you the position.";

            case REJECTED:
                return "Thank you for your interest. Unfortunately, we will not proceed further.";

            default:
                return "";
        }
    }

    
      // ENTRY POINT FROM ADMIN (STATUS CHANGE)
      
    public void update(long applicationId,User admin) {

        ApplicatantDAO dao = new ApplicatantDAO();
        ApplicationModel app = dao.getApplicantById(applicationId);

        String jobTitle = dao.getJobTitleByApplicationId(applicationId);

        sendStatusUpdateMail(app.getName(),app.getEmail(), jobTitle, app.getApplicationStatus(),  admin.getFullName());
    }
}
