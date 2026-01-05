/**
 * 
 */
 fetch("/JobPortalManagementSystem/admin/dashboard/counts")
 .then(response=>response.json())
 .then(data=>{
	document.getElementById("totalJobs").innerText=data.totalJobs;
	document.getElementById("TotalApplicants").innerText=data.totalApplicants;
	document.getElementById("PendingApplications").innerText=data.pendingApplications;
	document.getElementById("Accepted").innerText=data.totalAccepted;
})
.catch(err => console.error(err));