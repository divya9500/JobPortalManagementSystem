import { csrfFetch } from './csrf.js';


document.addEventListener("DOMContentLoaded", () => {

  /* =========================
     READ MODE & PARAMS
  ========================= */
  const params = new URLSearchParams(window.location.search);
  const mode = params.get("mode") || "apply";

  //  FIX 1: jobId from sessionStorage (OLD WORKING WAY)
  const jobId = sessionStorage.getItem("jobId");

  const form = document.getElementById("jobForm");
  const submitBtn = document.getElementById("submitButton"); // ✅ match HTML id

  /* =========================
     MODE SETUP
  ========================= */
  if (mode === "profile") {
    submitBtn.textContent = "Save Profile";
    document.getElementById("userInfoBox").style.display = "block";

    fetch("/JobPortalManagementSystem/user/me")
      .then(res => res.json())
      .then(user => {
        document.getElementById("email").value = user.email;
        document.getElementById("mobile").value = user.mobNum;
      });
  }

  /* =========================
     LOAD SKILLS FIRST
  ========================= */
  fetch("/JobPortalManagementSystem/getSkills")
    .then(res => res.json())
    .then(skills => {
      const select = document.getElementById("skills");
      select.innerHTML = "";

      skills.forEach(skill => {
        const option = document.createElement("option");
        option.value = skill.id;
        option.textContent = skill.skillName;
        select.appendChild(option);
      });

      loadProfile(); // load profile AFTER skills
    });

  /* =========================
     LOAD PROFILE
  ========================= */
  function loadProfile() {
    fetch("/JobPortalManagementSystem/user/profile")
      .then(res => res.json())
      .then(p => {
        if (!p) return;

        setValue("first_name", p.firstName);
        setValue("last_name", p.lastName);
        setValue("degree", p.degree);
        setValue("graduation_year", p.graduationYear);
        setValue("current_employer", p.currentEmployer);
        setValue("current_ctc", p.current_ctc);
        setValue("expected_ctc", p.expected_ctc);
        setValue("notice_period_days", p.noticePeriodDays);
        setValue("current_location", p.currentLocation);
        setValue("preferred_location", p.preferredLocation);

        if (p.gender) {
          document.querySelector(
            `input[name="gender"][value="${p.gender}"]`
          ).checked = true;
        }

        if (p.totalExperienceYears > 0) {
          document.querySelector('input[name="has_experience"][value="YES"]').checked = true;
          document.getElementById("experienceBox").style.display = "block";
          setValue("totalExperienceYears", p.totalExperienceYears);
        } else {
          document.querySelector('input[name="has_experience"][value="NO"]').checked = true;
        }

        if (p.skills) {
          const ids = p.skills.map(Number);
          [...document.getElementById("skills").options]
            .forEach(o => o.selected = ids.includes(Number(o.value)));
        }
      });
  }

  /* =========================
     SUBMIT HANDLER (ONLY ONE)
  ========================= */
  form.addEventListener("submit", e => {
    e.preventDefault();

    /* -------- PROFILE MODE (FormData) -------- */
    if (mode === "profile") {
      const fd = new FormData(form);

      csrfFetch("/JobPortalManagementSystem/user/profile/update", {
        method: "POST",
        body: fd
      })
      .then(() => alert("Profile updated successfully"));

      return;
    }

    /* -------- APPLY MODE (JSON + BASE64) -------- */
    if (!jobId) {
      alert("Invalid job. Please apply from job list.");
      return;
    }

    const resumeInput = document.querySelector('input[name="resume"]');
    const resumeFile = resumeInput && resumeInput.files.length > 0
      ? resumeInput.files[0]
      : null;

    const selectedSkills = Array.from(
      document.getElementById("skills").selectedOptions
    ).map(o => Number(o.value));

    const basePayload = {
      jobId: Number(jobId),
      firstName: document.querySelector('[name="first_name"]').value,
      lastName: document.querySelector('[name="last_name"]').value,
      gender: document.querySelector('input[name="gender"]:checked')?.value,
      degree: document.querySelector('[name="degree"]').value,
      graduationYear: Number(document.querySelector('[name="graduation_year"]').value),
      currentEmployer: document.querySelector('[name="current_employer"]').value,

      // FIX 2: camelCase (Gson-compatible)
      current_ctc: Number(document.querySelector('[name="current_ctc"]').value) || 0,
      expected_ctc: Number(document.querySelector('[name="expected_ctc"]').value) || 0,

      noticePeriodDays: Number(document.querySelector('[name="notice_period_days"]').value) || 0,
      currentLocation: document.querySelector('[name="current_location"]').value,
      preferredLocation: document.querySelector('[name="preferred_location"]').value,
      skills: selectedSkills,
      resumeName: resumeFile ? resumeFile.name : null
    };

    const sendRequest = (resumeBase64) => {
      csrfFetch("/JobPortalManagementSystem/apply/job", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          ...basePayload,
          resumeBase64: resumeBase64
        })
      })
      .then(res => res.json())
      .then(data => {
        alert(data.message || "Applied successfully");
        sessionStorage.removeItem("jobId");
      });
    };

    if (resumeFile) {
      const reader = new FileReader();
      reader.onload = () => {
        const base64 = reader.result.split(",")[1];
        sendRequest(base64);
      };
      reader.readAsDataURL(resumeFile);
    } else {
      sendRequest(null);
    }
  });
});

/* =========================
   HELPER
========================= */
function setValue(name, value) {
  if (value !== null && value !== undefined) {
    const el = document.querySelector(`[name="${name}"]`);
    if (el) el.value = value;
  }
}
