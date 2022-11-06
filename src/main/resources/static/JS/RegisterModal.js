//Register Modal
window.onload = function(){
    const Registerbtn = document.getElementById("Registerbtn");
    var modal = document.querySelector(".Registermodal");
    const Registeroverlay = modal.querySelector(".Registermodal__overlay");

    const openRegister = () =>{
        modal.classList.remove("hidden");
    }
    const closeRegister = () =>{
        modal.classList.add("hidden");
    }
    Registeroverlay.addEventListener("click", closeRegister);

    Registerbtn.addEventListener("click", openRegister);


}
