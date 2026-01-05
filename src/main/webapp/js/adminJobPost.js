document.getElementById("jobPostForm").addEventListener("submit", function (e) {
    e.preventDefault();

    //  READ jobId from hidden field
    const jobId = document.getElementById("jobId").value;

    //  BUILD jobData object from form
    const jobData = {};
    new FormData(this).forEach((value, key) => {
        jobData[key] = value;
    });

    let url;
    let method;
      let isUpdate = false;

    if (jobId) {
        // UPDATE
        jobData.jobId = jobId;
        console.log(jobId);
        url = "/JobPortalManagementSystem/update/job";
        method = "PUT";
        isUpdate = true;
    } else {
        // POST
         delete jobData.jobId; 
        url = "/JobPortalManagementSystem/admin/jobPost";
        method = "POST";
    }
        console.log("Job Data JSON:", JSON.stringify(jobData));

    fetch(url, {
        method: method,
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(jobData),

    })
    .then(res => res.text())
    .then(msg => {
        alert(msg);
         if (isUpdate) {
            // UPDATE  redirect
            window.location.href =
                "/JobPortalManagementSystem/page/admin/manageJob.html";
        } else {
            // POST  stay on same page
            document.getElementById("jobPostForm").reset();
        }
       
    })
    .catch(err => console.error("Submit error:", err));
});
