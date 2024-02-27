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

	var table = document.getElementById("timeCard");

	var tbody = document.getElementById("timeThead");

	const divList = [
		{value: 0, name: ""},
		{value: 1, name: "有給"},
		{value: 2, name: "代休"},
		{value: 3, name: "特休"},
		{value: 4, name: "臨時"},
		{value: 5, name: "前半"},
		{value: 6, name: "後半"},
		{value: 7, name: "欠勤"}
	];

	for(let i = 1;i <= endDate.getDate();i++) {
		var tr = document.createElement("tr");

		var th_day = document.createElement("th");
		var day = document.createTextNode(i);
		th_day.setAttribute("style","text-align: center");
		th_day.appendChild(day);
		tr.appendChild(th_day);

		var th_weekend = document.createElement("th");
		if(dayOfWeek >= 7){
			dayOfWeek = 0;
		}
		var dayOfWeekStr = ["日", "月", "火", "水", "木", "金", "土"][dayOfWeek];
		var weekend = document.createTextNode(dayOfWeekStr);
		th_weekend.setAttribute("style","text-align: center");
		th_weekend.appendChild(weekend);
		tr.appendChild(th_weekend);

		var th_vacation = document.createElement("th");
		var vacation = document.createElement("input");
		vacation.setAttribute("class","form-check-input");
		vacation.setAttribute("type","checkbox");
		vacation.setAttribute("value","");
		vacation.setAttribute("id","flexCheckDefault");
		//vacation.setAttribute("style","width: 30px; text-align: center");
		if(dayOfWeek == 6){
			tr.setAttribute("bgcolor","lightskyblue");
			vacation.checked = true;
		} else if(dayOfWeek == 0){
			tr.setAttribute("bgcolor","pink");
			vacation.checked = true;
		}
		th_vacation.appendChild(vacation);
		tr.appendChild(th_vacation);

		var th_div = document.createElement("th");
		var selectWrap = document.createElement("div");
		var select = document.createElement("select");
		select.setAttribute("style","width: 60px; text-align: center");
		divList.forEach((v) => {
			var option = document.createElement("option");
			option.value = v.value;
			option.textContent = v.name;
			select.appendChild(option);
		});
		selectWrap.appendChild(select);
		th_div.appendChild(selectWrap);
		tr.appendChild(th_div);

		for(let j = 0;j < 11;j++){
			var th_time = document.createElement("th");
			var time = document.createElement("input");
			time.setAttribute("type","text");
			time.setAttribute("style","width: 75px; text-align: center");
			th_time.appendChild(time);
			tr.appendChild(th_time);
		}

		var th_content = document.createElement("th");
		var content = document.createElement("input");
		content.setAttribute("type","text");
		content.setAttribute("style","width: 150px");
		th_content.appendChild(content);
		tr.appendChild(th_content);

		dayOfWeek = dayOfWeek + 1;

		tbody.appendChild(tr);
	}
	var tr = document.createElement("tr");
	tr.setAttribute("bgcolor","lightsalmon");

	var th_day = document.createElement("th");
	var day = document.createTextNode("合計");
	th_day.setAttribute("style","text-align: center; font-size: 1.2rem");
	th_day.appendChild(day);
	th_day.colSpan = "6";
	tr.appendChild(th_day);

	for(let j = 0;j < 9;j++){
		var th_time = document.createElement("th");
		var time = document.createElement("input");
		time.setAttribute("type","text");
		time.setAttribute("style","width: 75px; text-align: center");
		time.readOnly = true;
		th_time.appendChild(time);
		tr.appendChild(th_time);
	}

	var th_content = document.createElement("th");
	tr.appendChild(th_content);

	dayOfWeek = dayOfWeek + 1;

	tbody.appendChild(tr);

	table.appendChild(tbody);
}

function getInputDate() {
	var text = document.getElementById("inputDate").value;
	var year = text.substr(0,4);
	var month = text.substr(5,2);

	var inputDate = new Date(year + "/" + month + "/01");

	return inputDate;
}

function inputChange(){
	document.getElementById("btn-calc").style.visibility = "visible";
	document.getElementById("btn-pdf").style.visibility = "visible";
	document.getElementById("timeCard").style.visibility = "visible";
	document.getElementById("btn-tmpsave").style.visibility = "visible";
	document.getElementById("btn-regist").style.visibility = "visible";

	AddColumn();

		}

//タイトルをクリックしたときに伸び縮み（タイトル名のhタグにidを付与、
//伸び縮みする箇所を囲むdivにclass="accordion"を付与）
/*リストの行数を固定してスクロールバー表示（tableタグを囲むdivタグに付与）*/
$(document).ready(function(){
//画面が読み込まれた時はcss適用
	$('#listLimit').css({
    'max-height': 'calc(33px * 12)',
    'overflow-y': 'scroll'
    });

	$('#folding').click(function(){
	var accordion = $('.accordion');
	accordion.slideToggle(500, function() {
    if (!accordion.is(':visible')) {
     // .accordionが非表示の場合、20行分表示させる
     $('#listLimit').css({
     'max-height': '',
     'overflow-y': ''
      });
      } else {
      // .accordionが表示されている場合、10行分表示してスクロール
      $('#listLimit').css({
      'max-height': 'calc(33px * 12)',
      'overflow-y': 'scroll'
      });
      }
      });
    });
});

//tdの何処をクリックしても遷移する(trタグにclass="trAction"を付与
//input(checkbox)に、class="tdAction"を付与
//th:value="${list.client_id}"を次の通り修正
//th:attr="data-calendar-seq-id=${item.calendar_seq_id_id}")
//下記メソッドを複製してURLをそれぞれカスタム
$(document).ready(function(){
	$('.trAction').dblclick(function(event){
	
		        event.preventDefault();
		var seqId = $(this).find('.form-check-input').data('calendar-seq-id');
		var status = 'view';

		var url = '/SMSCD002?seqId=' + seqId + '&eventStatus=' + status;
	window.location.href = url;
	});
});