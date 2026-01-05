const params = new URLSearchParams(window.location.search);
const jobId = params.get("jobId");

if (!jobId) {
  alert("Job ID missing");
  throw new Error("jobId missing");
}

const BASE = "/JobPortalManagementSystem/page/admin";

document.addEventListener("DOMContentLoaded", () => {
  loadJobDetails();
  loadApplicants();
});

// ---------------- Job Info ----------------
function loadJobDetails() {
  fetch(`${BASE}/job/details?jobId=${jobId}`)
    .then(res => res.json())
    .then(job => {
      document.getElementById("jobInfo").innerText =
        `Job ID: ${job.jobId} | ${job.title} | ${job.location}`;
    });
}

// ---------------- Applicants ----------------
function loadApplicants() {
  fetch(`${BASE}/job/applications?jobId=${jobId}`)
    .then(res => res.json())
    .then(renderTable);
}

function renderTable(apps) {
  const body = document.getElementById("appBody");

  let html = "";

  apps.forEach(app => {
    html += `
      <tr data-app-id="${app.applicationId}">
        <td>${app.name}</td>
        <td>${app.email}</td>
        <td>${app.appliedAt}</td>
        <td>
          <span class="status-badge ${app.applicationStatus}">
            ${formatStatus(app.applicationStatus)}
          </span>
        </td>
        <td>
          <select id="sel-${app.applicationId}">
            <option>APPLIED</option>
            <option>SHORTLISTED</option>
            <option>INTERVIEW_SCHEDULED</option>
            <option>INTERVIEW_PASSED</option>
            <option>INTERVIEW_FAILED</option>
            <option>OFFERED</option>
            <option>REJECTED</option>
          </select>
          <button onclick="updateStatus(this, ${app.applicationId})">Update</button>
          <button onclick="viewHistory(${app.applicationId})">History</button>
        </td>
      </tr>
    `;
  });

  // ONE DOM PAINT ONLY
  body.innerHTML = html;

  // Set select values AFTER render
  apps.forEach(app => {
    const sel = document.getElementById(`sel-${app.applicationId}`);
    if (sel) sel.value = app.applicationStatus;
  });
}


// ---------------- Single Update ----------------
function updateStatus(btn, appId) {
  btn.disabled = true;

  const status = document.getElementById(`sel-${appId}`).value;
  const row = btn.closest("tr");
  const badge = row.querySelector(".status-badge");

  fetch(`${BASE}/update/status`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      applicationId: appId,
      applicationStatus: status
    })
  })
  .then(res => res.json())
  .then(resp => {
    if (resp.success) {
      //  Optimistic UI update (instant)
      badge.className = `status-badge ${status}`;
      badge.innerText = formatStatus(status);
    } else {
      alert(resp.message || "Update failed");
    }
  })
  .catch(() => alert("Server error"))
  .finally(() => btn.disabled = false);
}

// ---------------- Bulk Update ----------------
function bulkUpdateStatus(btn) {

  // ------------------------------
  // Button feedback
  // ------------------------------
  const originalText = btn.innerText;
  btn.disabled = true;
  btn.innerText = "Processing...";

  // ------------------------------
  // Get DOM elements
  // ------------------------------
  const fromStatusEl = document.getElementById("fromStatus");
  const toStatusEl   = document.getElementById("toStatus");
  const limitEl      = document.getElementById("limit");
  const minExpEl     = document.getElementById("minExp");
  const gradYearEl   = document.getElementById("gradYear");
  const appliedAfterEl = document.getElementById("appliedAfter");

  if (!fromStatusEl || !toStatusEl || !limitEl) {
    alert("Required fields missing");
    reset();
    return;
  }

  const fromStatus = fromStatusEl.value;
  const toStatus   = toStatusEl.value;
  const limitVal   = Number(limitEl.value);

  if (!fromStatus || !toStatus || !limitVal) {
    alert("Please select From Status, To Status and Limit");
    reset();
    return;
  }

  if (fromStatus === toStatus) {
    alert("From Status and To Status cannot be the same");
    reset();
    return;
  }

  // ------------------------------
  // Payload (matches backend)
  // ------------------------------
  const payload = {
    jobId: jobId,
    fromStatus: fromStatus,
    applicationStatus: toStatus,
    minExperience: minExpEl && minExpEl.value ? Number(minExpEl.value) : null,
    minGraduationYear: gradYearEl && gradYearEl.value ? Number(gradYearEl.value) : null,
    appliedAfter: appliedAfterEl && appliedAfterEl.value ? appliedAfterEl.value : null,
    limit: limitVal
  };

  if (!confirm(`Move top ${limitVal} candidates from ${fromStatus} → ${toStatus}?`)) {
    reset();
    return;
  }

  // ------------------------------
  // API call
  // ------------------------------
  fetch(`${BASE}/update/status/filter`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload)
  })
    .then(res => res.json())
    .then(resp => {
	console.log(resp);
      if (!resp.success) {
        alert(resp.message || "Bulk update failed");
        return;
      }
		
      // ------------------------------
      //  UPDATE UI USING BACKEND IDs
      // ------------------------------
      resp.updatedIds.forEach(id => {
        const row = document.querySelector(`tr[data-app-id="${id}"]`);
        if (!row) return;

        const badge = row.querySelector(".status-badge");
        if (!badge) return;

        badge.className = `status-badge ${toStatus}`;
        badge.innerText = formatStatus(toStatus);
      });

      alert(`Successfully updated ${resp.updatedCount} candidates`);
    })
    .catch(() => {
      alert("Server error during bulk update");
    })
    .finally(() => reset());

  // ------------------------------
  // Helper
  // ------------------------------
  function reset() {
    btn.disabled = false;
    btn.innerText = originalText;
  }
}



// ---------------- History ----------------
function viewHistory(id) {
  fetch(`${BASE}/application/history?applicationId=${id}`)
    .then(res => res.json())
    .then(data => {
      const body = document.getElementById("historyTableBody");
      body.innerHTML = "";

      data.forEach(h => {
        body.innerHTML += ` <tr>
    	   <td>${formatStatus(h.oldStatus)}</td>
            <td>${formatStatus(h.newStatus)}</td>
            <td>${h.changedBy}</td>
            <td>${new Date(h.changedAt).toLocaleString()}</td>
          </tr>`;
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

function formatStatus(s) {
  return s.replaceAll("_", " ");
}

