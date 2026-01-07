document.getElementById("signupForm").addEventListener("submit", function(e){
    e.preventDefault();

    const formData = {};
    new FormData(this).forEach((value, key) => formData[key] = value);

    fetch("/JobPortalManagementSystem/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData)
    })
    .then(res => res.json())
    .then(data => {
        document.getElementById("message").innerHTML =
            `<span style="color:green">${data.message}</span>`;
            

        this.reset();
        
        if(data.success){
		window.location.href="signinPage.html";
		}
    })
    .catch(err => {
        document.getElementById("message").innerHTML =
            `<span style="color:red">${err}</span>`;
    });
});
