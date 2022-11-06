 const openModify = document.getElementById("modify_comment_btn");
    var modal = document.querySelector(".modal");
    const Modify_overlay = modal.querySelector(".modal__overlay");

    const openMainModify = () =>{
        modal.classList.remove("hidden");
    }
    const closeMainModify = () =>{
        modal.classList.add("hidden");
    }
    Modify_overlay.addEventListener("click", closeMainModify);
    openModify.addEventListener("click", openMainModify);
