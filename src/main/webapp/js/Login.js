import { loadCsrfToken } from './csrf.js';


document.addEventListener("DOMContentLoaded", function () {

    document.getElementById("signinForm").addEventListener("submit", function (e) {
        e.preventDefault();

        const formData = {};
        new FormData(this).forEach((value, key) => {
            formData[key] = value;
        });

        fetch("/JobPortalManagementSystem/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)
        })
        .then(res => res.json())
        .then(async  data => {

            const messageDiv = document.getElementById("message");

            if (data.success ) {
                messageDiv.innerHTML =
                    `<span style="color:green">${data.message}</span>`;

             
                await loadCsrfToken();
               
                    window.location.href = data.redirectUrl;
               

            } else {
                messageDiv.innerHTML =
                    `<span style="color:red">${data.message}</span>`;
            }
        })
        .catch(err => {
            document.getElementById("message").innerHTML =
                `<span style="color:red">Server error. Try again.</span>`;
            console.error(err);
        });
    });

});
