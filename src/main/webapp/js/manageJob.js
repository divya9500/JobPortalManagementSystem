import { csrfFetch } from "./csrf.js";

document.addEventListener("DOMContentLoaded", () => {
  const tableBody = document.getElementById("jobTableBody");
  if (!tableBody) return; // not this page

  loadJobs();

  function loadJobs() {
    fetch("/JobPortalManagementSystem/page/admin/jobs")
      .then(res => res.json())
      .then(displayJobs)
      .catch(err => console.error("Error fetching jobs:", err));
  }

  function displayJobs(jobs) {
    tableBody.innerHTML = "";

    jobs.forEach(job => {
      const row = document.createElement("tr");

      // columns
      row.innerHTML = `
        <td>${job.jobId}</td>
        <td>${job.title}</td>
        <td>${job.location}</td>
        <td></td>
      `;

      // action buttons
      const actionCell = row.children[3];

      const editBtn = document.createElement("button");
      editBtn.className = "action-btn edit";
      editBtn.textContent = "Edit";
      editBtn.addEventListener("click", () => editJob(job.jobId));

      const deleteBtn = document.createElement("button");
      deleteBtn.className = "action-btn delete";
      deleteBtn.textContent = "Delete";
      deleteBtn.addEventListener("click", () => deleteJob(job.jobId));

      actionCell.append(editBtn, deleteBtn);
      tableBody.appendChild(row);
    });
  }

  function editJob(jobId) {
    window.location.href =
      `/JobPortalManagementSystem/page/admin/jobPost.html?jobId=${jobId}`;
  }

  function deleteJob(jobId) {
    if (!confirm("Are you sure you want to delete this job?")) return;

    csrfFetch(`/JobPortalManagementSystem/delete/job?jobId=${jobId}`, {
      method: "DELETE"
    })
      .then(() => loadJobs())
      .catch(console.error);
  }
});
