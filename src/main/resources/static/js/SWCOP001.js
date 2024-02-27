function AddColumn() {
	var inputDate = getInputDate();
	var dayOfWeek = inputDate.getDay();
	
	var endDate = new Date(inputDate);
	endDate.setMonth(endDate.getMonth() + 1);
	endDate.setDate(endDate.getDate() - 1);
		
	/*
	var date_text = document.getElementById("output");
	date_text.innerText = endDate;
	*/
	
	var table = document.getElementById("confHeader");
	
	var tbody = document.getElementById("confList");
	
	for(let i = 1;i <= 50;i++) {
		var tr = document.createElement("tr");
		
		var th_no = document.createElement("th");
		var no = document.createTextNode(i+1000);
		th_no.setAttribute("style","text-align: center");
		th_no.setAttribute("style","width: 50px; text-align: center");
		th_no.appendChild(no);
		tr.appendChild(th_no);
		
	    var th_name = document.createElement("th");
		var name = document.createElement("div");
		name.setAttribute("style","width: 100px; text-align: center");
		name.textContent = "Nexus社員" + i
		th_name.appendChild(name);
		tr.appendChild(th_name);
		
		var th_sum = document.createElement("th");
		var sum = document.createElement("div");
		sum.setAttribute("style","width: 70px; text-align: center");
		sum.textContent = 130+i + ".00";
		th_sum.appendChild(sum);
		tr.appendChild(th_sum);
		
        for(let j = 0;j < 6;j++){
			var th_time = document.createElement("th");
			var time = document.createElement("div");
			time.setAttribute("style","width: 70px; text-align: center");
			time.textContent = i + ".00";
			th_time.appendChild(time);
			tr.appendChild(th_time);
		}
		
		for(let j = 0;j < 9;j++){
			var th_day = document.createElement("th");
			var day = document.createElement("div");
			day.setAttribute("style","width: 40px; text-align: center");
			if(j==3 || j==8){
				day.textContent = 0;
			} else {
				day.textContent = i;
			}
			th_day.appendChild(day);
			tr.appendChild(th_day);
		}
		
		tbody.appendChild(tr);
	}
	
	table.appendChild(tbody);
}

function getInputDate() {
	var text = document.getElementById("inputDate").value;
	var year = text.substr(0,4);
	var month = text.substr(5,2);
	
	var inputDate = new Date(year + "/" + month + "/01");
	
	return inputDate;
}

function btnClickConf(){
	document.getElementById("confHeader").style.visibility = "visible";
	document.getElementById("confList").style.visibility = "visible";
	document.getElementById("paging").style.visibility = "visible";
	
	AddColumn();
}