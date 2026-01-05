document.getElementById("resetForm")
    .addEventListener("submit", function (e) {

    e.preventDefault();

    const params = new URLSearchParams(window.location.search);
    const token = params.get("token");

    const newPassword =
        document.querySelector("input[name='newPassword']").value;
    const confirmPassword =
        document.querySelector("input[name='confirmPassword']").value;

    if (newPassword !== confirmPassword) {
        alert("Passwords do not match");
        return;
    }

    fetch("/JobPortalManagementSystem/reset/password", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            token: token,
            newPassword: newPassword
        })
    })
    .then(res => res.json())
    .then(data => {
        alert(data.message);

        if (data.success) {
            window.location.href = "signinPage.html";
        }
    })
    .catch(err => {
        alert("Reset failed");
        console.error(err);
    });
});
/**
 * 
 */