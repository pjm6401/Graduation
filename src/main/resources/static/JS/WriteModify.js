//modify comment

 const openWriteModify = document.getElementById("WriteModify_comment_btn");
    var modal = document.querySelector(".modal");
    const WriteModify_overlay = modal.querySelector(".modal__overlay");

    const openBoardModify = () =>{
        modal.classList.remove("hidden");
    }
    const closeBoardModify = () =>{
        modal.classList.add("hidden");
    }
    WriteModify_overlay.addEventListener("click", closeBoardModify);
    openWriteModify.addEventListener("click", openBoardModify);
