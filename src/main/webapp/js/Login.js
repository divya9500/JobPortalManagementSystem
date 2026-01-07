import { loadCsrfToken } from './csrf.js';

document.addEventListener("DOMContentLoaded", function () {

    const params = new URLSearchParams(window.location.search);
    const type = params.get("type");

    // Default USER
    const loginRole = (type =="admin") ? "ADMIN" : "USER";
    document.body.dataset.role = loginRole;

    document.getElementById("signinForm").addEventListener("submit", function (e) {
        e.preventDefault();

        const formData = {};
        new FormData(this).forEach((value, key) => {
            formData[key] = value;
        });

        // 🔐 Login context
        formData.role = loginRole;

        fetch("/JobPortalManagementSystem/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)
        })
        .then(res => res.json())
        .then(async data => {

            const messageDiv = document.getElementById("message");

            if (data.success) {
                await loadCsrfToken();
                window.location.href = data.redirectUrl;
            } else {
                messageDiv.innerHTML =
                    `<span style="color:red">${data.message}</span>`;
            }
        })
        .catch(() => {
            document.getElementById("message").innerHTML =
                `<span style="color:red">Server error. Try again.</span>`;
        });
    });

});
