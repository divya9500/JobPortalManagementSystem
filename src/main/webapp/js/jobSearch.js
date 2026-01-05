(function () {
    // ================= GLOBAL CONFIG =================
    const contextPath = "/JobPortalManagementSystem";

    // ================= API ENDPOINTS =================
    const JOB_API = contextPath + "/page/secure/jobs";
    const SEARCH_API = contextPath + "/search/jobs";
    const JOB_DETAILS_API = contextPath + "/page/secure/job/details";
    const MY_APPLICATIONS_API = contextPath + "/myApplications";

    // ================= STATE =================
    const appliedJobs = new Set();

    // ================= INIT =================
    document.addEventListener("DOMContentLoaded", function () {
        loadMyApplications();
        loadAllJobs();
    });

    // ================= SAFE JSON PARSER =================
    function safeJSON(res) {
        return res.text().then(text => {
            try {
                return JSON.parse(text);
            } catch (e) {
                console.error("Expected JSON but got:", text);
                return null;
            }
        });
    }

    // ================= LOAD ALL JOBS =================
    function loadAllJobs() {
        fetch(JOB_API)
            .then(safeJSON)
            .then(data => {
                if (data) renderJobs(data);
            })
            .catch(err => console.error("Load jobs error:", err));
    }

    // ================= SEARCH JOBS =================
    window.searchJobs = function () {
        const role = document.getElementById("jobRole").value.trim();
        const location = document.getElementById("jobLocation").value.trim();

        const params = new URLSearchParams();
        if (role) params.append("role", role);
        if (location) params.append("location", location);

        fetch(`${SEARCH_API}?${params.toString()}`)
            .then(safeJSON)
            .then(data => {
                if (data) renderJobs(data);
            })
            .catch(err => console.error("Search error:", err));
    };

    // ================= RENDER JOB CARDS =================
    function renderJobs(jobs) {
        const list = document.querySelector(".job-list");
        if (!list) return;
        list.innerHTML = "";

        if (!jobs || jobs.length === 0) {
            list.innerHTML = "<p>No jobs found</p>";
            return;
        }

        jobs.forEach(job => {
            const applied = appliedJobs.has(job.jobId);

            const card = document.createElement("div");
            card.className = "job-card";

            card.innerHTML = `
                <h3>${job.title}</h3>
                <p><b>Company:</b> ${job.company || ""}</p>
                <p><b>Location:</b> ${job.location}</p>

                <div class="job-actions">
                    <button class="btn btn-view" onclick="viewJobDetails(${job.jobId})">
                        View Details
                    </button>
                    <button type="button" class="btn btn-apply" 
                            onclick="openApplicationForm(${job.jobId})">
                        Apply
                    </button>
                </div>
            `;

            list.appendChild(card);
        });
    }

    // ================= OPEN APPLICATION FORM =================
    window.openApplicationForm = function (jobId) {
        // Store jobId in session storage to pass to form.html
        sessionStorage.setItem("jobId", jobId);
        // Redirect to form page
        window.location.href = "form.html";
    };

    // ================= LOAD APPLICATION STATUS =================
   function loadMyApplications() {
        fetch(MY_APPLICATIONS_API)
            .then(safeJSON)
            .then(apps => {
                if (!apps) return;

                const table = document.getElementById("statusTable");
                if (!table) return;

                table.innerHTML = "";
                appliedJobs.clear();

                if (apps.length === 0) {
                    table.innerHTML = "<tr><td colspan='2'>No applications</td></tr>";
                    return;
                }

                apps.forEach(app => {
                    appliedJobs.add(app.jobId);

                    table.innerHTML += `
                        <tr>
                            <td>${app.title}</td>
                            <td class="status ${app.status.toLowerCase()}">${app.status}</td>
                        </tr>
                    `;
                });
            })
            .catch(err => console.error("Status error:", err));
    }

    // ================= JOB DETAILS MODAL =================
    window.viewJobDetails = function (jobId) {
        fetch(`${JOB_DETAILS_API}?jobId=${jobId}`)
            .then(safeJSON)
            .then(job => {
                if (!job) return;

                document.getElementById("mTitle").innerText = job.title;
                document.getElementById("mLocation").innerText = job.location;
                document.getElementById("mExperience").innerText =
                    `${job.min_experience || 0} - ${job.max_experience || 0} years`;
                document.getElementById("mSalary").innerText =
                    `${job.min_salary || 0} - ${job.max_salary || 0}`;
                document.getElementById("mDescription").innerText =
                    job.description || "";

                document.getElementById("jobModal").style.display = "block";
            })
            .catch(err => console.error("Details error:", err));
    };

    window.closeModal = function () {
        document.getElementById("jobModal").style.display = "none";
    };

})();
