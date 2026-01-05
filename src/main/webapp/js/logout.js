
document.getElementById("logout").addEventListener("click",function(e){
	
	e.preventDefault();
	



fetch("/JobPortalManagementSystem/logout")
.then(res=>res.json())
.then(data=>{
	if(data.success){
		window.location.href=data.redirectUrl;
	}
}).catch(err=>
	console.error("logout failed:"+err)
);


});