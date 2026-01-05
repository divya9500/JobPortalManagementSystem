document.getElementById("forgotForm")
    .addEventListener("submit", function (e) {

    e.preventDefault();

    const email =
        document.querySelector("input[name='email']").value;

    fetch("/JobPortalManagementSystem/forgot/password", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ email: email })
    })
    .then(res => res.json())
    .then(data => {
        alert(data.message);
    })
    .catch(err => {
        alert("Something went wrong");
        console.error(err);
    });
});
/**
 * 
 */