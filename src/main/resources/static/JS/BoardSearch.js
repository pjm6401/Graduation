const openBoardbtn = document.getElementById("BoardSearch");
    console.log(openBoardbtn);
    var opneB = document.querySelector(".BoardBtn");


    const openBoard = () =>{
     opneB.classList.remove("show");
    }
    const closeBoard = () =>{
     opneB.classList.add("show");
    }

    openBoardbtn.addEventListener("click", openBoard);

