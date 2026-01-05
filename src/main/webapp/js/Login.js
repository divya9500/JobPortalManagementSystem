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
        .then(data => {

            const messageDiv = document.getElementById("message");

            if (data.success ) {
                messageDiv.innerHTML =
                    `<span style="color:green">${data.message}</span>`;

                // redirect after short delay
                setTimeout(() => {
                    window.location.href = data.redirectUrl;
                }, 1000);

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
