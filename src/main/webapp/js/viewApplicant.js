import { csrfFetch, loadCsrfToken } from "./csrf.js";

/* =========================
   READ JOB ID
========================= */
const params = new URLSearchParams(window.location.search);
const jobId = params.get("jobId");

if (!jobId) {
  alert("Job ID missing");
  throw new Error("jobId missing");
}

const BASE = "/JobPortalManagementSystem/page/admin";

/* =========================
   INIT
========================= */
document.addEventListener("DOMContentLoaded", async () => {

  // 🔐 Ensure CSRF token exists (important on refresh)
  if (!sessionStorage.getItem("CSRF_TOKEN")) {
    await loadCsrfToken();
  }

  loadJobDetails();
  loadApplicants();

  // Bulk update button binding
  const bulkBtn = document.getElementById("bulkUpdateBtn");
  if (bulkBtn) {
    bulkBtn.addEventListener("click", () => bulkUpdateStatus(bulkBtn));
  }
});

/* =========================
   JOB INFO
========================= */
function loadJobDetails() {
  fetch(`${BASE}/job/details?jobId=${jobId}`)
    .then(res => res.json())
    .then(job => {
      const info = document.getElementById("jobInfo");
      if (info) {
        info.innerText =
          `Job ID: ${job.jobId} | ${job.title} | ${job.location}`;
      }
    });
}

/* =========================
   LOAD APPLICANTS
========================= */
function loadApplicants() {
  fetch(`${BASE}/job/applications?jobId=${jobId}`)
    .then(res => res.json())
    .then(renderTable)
    .catch(console.error);
}

/* =========================
   RENDER TABLE
========================= */
function renderTable(apps) {
  const body = document.getElementById("appBody");
  if (!body) return;

  body.innerHTML = "";

  apps.forEach(app => {
    const row = document.createElement("tr");
    row.dataset.appId = app.applicationId;

    row.innerHTML = `
      <td>${app.name}</td>
      <td>${app.email}</td>
      <td>${app.appliedAt}</td>
      <td>
        <span class="status-badge ${app.applicationStatus}">
          ${formatStatus(app.applicationStatus)}
        </span>
      </td>
      <td>
        <select class="status-select">
          <option>APPLIED</option>
          <option>SHORTLISTED</option>
          <option>INTERVIEW_SCHEDULED</option>
          <option>INTERVIEW_PASSED</option>
          <option>INTERVIEW_FAILED</option>
          <option>OFFERED</option>
          <option>REJECTED</option>
        </select>
        <button class="update-btn">Update</button>
        <button class="history-btn">History</button>
      </td>
    `;

    body.appendChild(row);
    row.querySelector(".status-select").value = app.applicationStatus;
  });

  bindRowActions();
}

/* =========================
   BIND ROW ACTIONS
========================= */
function bindRowActions() {

  document.querySelectorAll(".update-btn").forEach(btn => {
    btn.addEventListener("click", () => {
      const row = btn.closest("tr");
      updateStatus(btn, row.dataset.appId);
    });
  });

  document.querySelectorAll(".history-btn").forEach(btn => {
    btn.addEventListener("click", () => {
      const row = btn.closest("tr");
      viewHistory(row.dataset.appId);
    });
  });
}

/* =========================
   SINGLE STATUS UPDATE
========================= */
function updateStatus(btn, appId) {
  btn.disabled = true;

  const row = btn.closest("tr");
  const status = row.querySelector(".status-select").value;
  const badge = row.querySelector(".status-badge");

  csrfFetch(`${BASE}/update/status`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      applicationId: appId,
      applicationStatus: status
    })
  })
    .then(async res => {
      const text = await res.text();
      return text ? JSON.parse(text) : {};
    })
    .then(resp => {
      if (resp.success) {
        badge.className = `status-badge ${status}`;
        badge.innerText = formatStatus(status);
      } else {
        alert(resp.message || "Update failed");
      }
    })
    .catch(err => {
      console.error(err);
      alert("Server error");
    })
    .finally(() => btn.disabled = false);
}

/* =========================
   BULK UPDATE
========================= */
function bulkUpdateStatus(btn) {

  const fromStatus = document.getElementById("fromStatus")?.value;
  const toStatus   = document.getElementById("toStatus")?.value;
  const limit      = Number(document.getElementById("limit")?.value);

  if (!fromStatus || !toStatus || !limit) {
    alert("Please fill From Status, To Status and Limit");
    return;
  }

  if (fromStatus === toStatus) {
    alert("From and To status cannot be same");
    return;
  }

  if (!confirm(`Move top ${limit} candidates from ${fromStatus} → ${toStatus}?`)) {
    return;
  }

  btn.disabled = true;
  const oldText = btn.innerText;
  btn.innerText = "Processing...";

  csrfFetch(`${BASE}/update/status/filter`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      jobId: jobId,
      fromStatus: fromStatus,
      applicationStatus: toStatus,
      limit: limit
    })
  })
    .then(res => res.json())
    .then(resp => {
      if (!resp.success) {
        alert(resp.message || "Bulk update failed");
        return;
      }

      resp.updatedIds.forEach(id => {
        const row = document.querySelector(`tr[data-app-id="${id}"]`);
        if (!row) return;

        const badge = row.querySelector(".status-badge");
        badge.className = `status-badge ${toStatus}`;
        badge.innerText = formatStatus(toStatus);
      });

      alert(`Updated ${resp.updatedCount} applications`);
    })
    .catch(() => alert("Server error"))
    .finally(() => {
      btn.disabled = false;
      btn.innerText = oldText;
    });
}

/* =========================
   HISTORY
========================= */
function viewHistory(appId) {
  fetch(`${BASE}/application/history?applicationId=${appId}`)
    .then(res => res.json())
    .then(data => {
      const body = document.getElementById("historyTableBody");
      if (!body) return;

      body.innerHTML = "";

      data.forEach(h => {
        body.innerHTML += `
          <tr>
            <td>${formatStatus(h.oldStatus)}</td>
            <td>${formatStatus(h.newStatus)}</td>
            <td>${h.changedBy}</td>
            <td>${new Date(h.changedAt).toLocaleString()}</td>
          </tr>
        `;
      });

      openHistoryModal();
    });
}

function openHistoryModal() {
  document.getElementById("historyModal").style.display = "block";
}

function closeHistoryModal() {
  document.getElementById("historyModal").style.display = "none";
}

/* =========================
   UTIL
========================= */
function formatStatus(s) {
  return s.replaceAll("_", " ");
}
