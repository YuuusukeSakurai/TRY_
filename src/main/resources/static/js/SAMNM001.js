var table = document.querySelector("table");
var tr = table.querySelectorAll("tr");
table.addEventListener("click", function(e) {
  if(e.target.tagName.toLowerCase() === "td") {
    //まずは全て背景色白
    for(var i = 1; i < tr.length; i++) {
      // alert(i +': '+ tr[i].style.backgroundColor);
      tr[i].style.backgroundColor = "white";
    }
    //選択行だけ色を変える
    e.target.parentNode.style.backgroundColor = "#eef";
  }
}, false);