/**
 * 
 */
function applyJob(jobId) {
    fetch("/JobPortalManagementSystem/applyJob", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `jobId=${jobId}`
    })
    .then(res => res.text())
    .then(msg => {
        alert(msg);              // backend message
        loadApplicationStatus(); // real-time update
    });
}

function loadApplicationStatus() {
    fetch("/JobPortalManagementSystem/myApplications")
        .then(res => res.json())
        .then(data => {
            const table = document.getElementById("statusTable");
            table.innerHTML = "";

            if (data.length === 0) {
                table.innerHTML =
                    "<tr><td colspan='2'>No applications</td></tr>";
                return;
            }

            data.forEach(app => {
                table.innerHTML += `
                    <tr>
                        <td>${app.title}</td>
                        <td>${app.status}</td>
                    </tr>
                `;
            });
        });
}

window.onload = loadApplicationStatus;
