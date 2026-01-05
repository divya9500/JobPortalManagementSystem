document.addEventListener("DOMContentLoaded", initPage);

function initPage() {
    const params = new URLSearchParams(window.location.search);
    const jobId = params.get("jobId");

    if (jobId) {
        // EDIT MODE
        document.getElementById("submitBtn").innerText = "Update Job";
        document.getElementById("jobId").value = jobId;
        loadJobForEdit(jobId);
    }
}

function loadJobForEdit(jobId) {
    fetch(`/JobPortalManagementSystem/page/admin/job/details?jobId=${jobId}`)
        .then(res => res.json())
        .then(job => {

            document.getElementById("title").value = job.title;
            document.getElementById("location").value = job.location;
            document.getElementById("empType").value = job.empType;

            document.getElementById("min_experience").value = job.min_experience;
            document.getElementById("max_experience").value = job.max_experience;

            document.getElementById("min_salary").value = job.min_salary;
            document.getElementById("max_salary").value = job.max_salary;

            document.getElementById("description").value = job.description;
            document.getElementById("status").value = job.status;
        });
}
