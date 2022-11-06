//Login Modal

 const openButton = document.getElementById("Loginbtn");
    var modal = document.querySelector(".modal");
    const overlay = modal.querySelector(".modal__overlay");

    const openModal = () =>{
     modal.classList.remove("hidden");
    }
    const closeModal = () =>{
     modal.classList.add("hidden");
    }
    overlay.addEventListener("click", closeModal);
    openButton.addEventListener("click", openModal);
