document.addEventListener("DOMContentLoaded", loadJobs);

function loadJobs() {
    fetch("/JobPortalManagementSystem/page/admin/jobs")
        .then(response => response.json())
        .then(data => displayJobs(data))
        .catch(error => console.error("Error fetching jobs:", error));
}

function displayJobs(jobs) {
    const tableBody = document.getElementById("jobTableBody");
    tableBody.innerHTML = "";

    jobs.forEach(job => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${job.jobId}</td>
            <td>${job.title}</td>
            <td>${job.location}</td>
            <td>
                <button class="action-btn edit" onclick="editJob(${job.jobId})">
                    Edit
                </button>
                <button class="action-btn delete" onclick="deleteJob(${job.jobId})">
                    Delete
                </button>
            </td>
        `;

        tableBody.appendChild(row);
    });
}

/*CORRECT EDIT FUNCTION */
function editJob(jobId) {
    // redirect to postJob.html with jobId
    window.location.href =
        "/JobPortalManagementSystem/page/admin/jobPost.html?jobId=" + jobId;
}

/*DELETE FUNCTION */
function deleteJob(jobId) {
    if (confirm("Are you sure you want to delete this job?")) {
        fetch(`/JobPortalManagementSystem/delete/job?jobId=${jobId}`, {
            method: "DELETE"
        })
        .then(response => response.text())
        .then(result => {
            alert(result);
            loadJobs();
        })
        .catch(error => console.error(error));
    }
}
