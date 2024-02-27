var applicationKindEnum = {
	paid: "1",
	am: "2",
	pm: "3",
	compensatory: "4",
	late: "5",
	early: "6",
	absence: "7",
	special: "8"
}

var classEnum = {
	application: "1",
	contact: "2",
	change: "3"
}

var division = {
	place: "1",
	address: "2",
	return: "3",
	medical: "4",
	time: "5",
	compensatory: "6",
	joinLeave: "7",
	tempRetire: "8",
	perDay: "9",
	tax: "10",
	etcSalary: "11",
	etc: "12"
}

function changeClass() {
	var classes = document.getElementById("classes");

	switch (classes.value) {
		case classEnum.application:
			document.getElementById("appliDay").style.display = "flex";
			document.getElementById("appliKind").style.display = "flex";
			document.getElementById("appliNote").style.display = "flex";
			document.getElementById("appliNote").style.display = "flex";

			document.getElementById("contact").style.display = "none";
			document.getElementById("contents").style.display = "none";
			document.getElementById("healthCheck").style.display = "none";

			document.getElementById("changeDiv").style.display = "none";

			break;

		case classEnum.contact:
			document.getElementById("appliDay").style.display = "none";
			document.getElementById("appliKind").style.display = "none";
			document.getElementById("appliNote").style.display = "none";

			document.getElementById("contact").style.display = "flex";
			document.getElementById("contents").style.display = "flex";
			document.getElementById("healthCheck").style.display = "flex";

			document.getElementById("changeDiv").style.display = "none";

			break;

		case classEnum.change:
			document.getElementById("appliDay").style.display = "none";
			document.getElementById("appliKind").style.display = "none";
			document.getElementById("appliNote").style.display = "none";

			document.getElementById("contact").style.display = "none";
			document.getElementById("contents").style.display = "none";
			document.getElementById("healthCheck").style.display = "none";

			document.getElementById("changeDiv").style.display = "flex";

			break;
	}
}

function changeApplicationKind() {

	var applicationKind = document.getElementById("applicationKind");

	switch (applicationKind.value) {
		case applicationKindEnum.late:
			document.getElementById("startTime").style.display = "flex";
			document.getElementById("reason").style.display = "flex";

			document.getElementById("endTime").style.display = "none";
			document.getElementById("targetDay").style.display = "none";
			document.getElementById("targetHour").style.display = "none";

			break;

		case applicationKindEnum.early:
			document.getElementById("reason").style.display = "flex";
			document.getElementById("endTime").style.display = "flex";

			document.getElementById("startTime").style.display = "none";
			document.getElementById("targetDay").style.display = "none";
			document.getElementById("targetHour").style.display = "none";
			break;	

		case applicationKindEnum.compensatory:
			document.getElementById("targetDay").style.display = "flex";
			document.getElementById("targetHour").style.display = "flex";

			document.getElementById("startTime").style.display = "none";
			document.getElementById("endTime").style.display = "none";
			document.getElementById("reason").style.display = "none";
			break;	
			
		default:
			document.getElementById("startTime").style.display = "none";
			document.getElementById("endTime").style.display = "none";
			document.getElementById("targetDay").style.display = "none";
			document.getElementById("targetHour").style.display = "none";
			document.getElementById("reason").style.display = "none";
	}
}

function changeHealthCheck() {
	var health = document.getElementById("health");

	if (health.checked) {
		document.getElementById("healthDetails").style.display = "flex";
	} else {
		document.getElementById("healthDetails").style.display = "none";
	}
}

function changeDiv() {
	var div = document.getElementById("div");

	switch (div.value) {
		case division.place:
		case division.address:
		case division.return:
			document.getElementById("changeDay").style.display = "flex";
			document.getElementById("changeNote").style.display = "flex";

			document.getElementById("changeDayOff").style.display = "none";
			document.getElementById("changeStartDay").style.display = "none";
			document.getElementById("changeEndDay").style.display = "none";
			document.getElementById("changeTime").style.display = "none";
			document.getElementById("changePlan").style.display = "none";

			break;
	
		case division.medical:
			document.getElementById("changeDay").style.display = "flex";

			document.getElementById("changeDayOff").style.display = "none";
			document.getElementById("changeStartDay").style.display = "none";
			document.getElementById("changeEndDay").style.display = "none";
			document.getElementById("changeTime").style.display = "none";
			document.getElementById("changeNote").style.display = "none";
			document.getElementById("changePlan").style.display = "none";

			break;

		case division.time:
			document.getElementById("changeStartDay").style.display = "flex";
			document.getElementById("changeEndDay").style.display = "flex";
			document.getElementById("changeTime").style.display = "flex";

			document.getElementById("changeDay").style.display = "none";
			document.getElementById("changeDayOff").style.display = "none";
			document.getElementById("changeNote").style.display = "none";
			document.getElementById("changePlan").style.display = "none";

			break;

		case division.compensatory:
			document.getElementById("changeDayOff").style.display = "flex";
			document.getElementById("changeNote").style.display = "flex";
			document.getElementById("changePlan").style.display = "flex";

			document.getElementById("changeDay").style.display = "none";
			document.getElementById("changeStartDay").style.display = "none";
			document.getElementById("changeEndDay").style.display = "none";
			document.getElementById("changeTime").style.display = "none";

			break;
		
		case division.joinLeave:
			document.getElementById("changeDay").style.display = "flex";
			document.getElementById("changeNote").style.display = "flex";

			document.getElementById("changeDayOff").style.display = "none";
			document.getElementById("changeStartDay").style.display = "none";
			document.getElementById("changeEndDay").style.display = "none";
			document.getElementById("changeTime").style.display = "none";
			document.getElementById("changePlan").style.display = "none";

			break;

		case division.tempRetire:
			document.getElementById("changeStartDay").style.display = "flex";
			document.getElementById("changeEndDay").style.display = "flex";
			document.getElementById("changeNote").style.display = "flex";

			document.getElementById("changeDay").style.display = "none";
			document.getElementById("changeDayOff").style.display = "none";
			document.getElementById("changeTime").style.display = "none";
			document.getElementById("changePlan").style.display = "none";

			break;
		
		case division.perDay:
			document.getElementById("changeStartDay").style.display = "flex";
			document.getElementById("changeEndDay").style.display = "flex";
			
			document.getElementById("changeDay").style.display = "none";
			document.getElementById("changeDayOff").style.display = "none";
			document.getElementById("changeTime").style.display = "none";
			document.getElementById("changeNote").style.display = "none";
			document.getElementById("changePlan").style.display = "none";

			break;

		case division.tax:
		case division.etcSalary:
		case division.etc:
			document.getElementById("changeNote").style.display = "flex";
			
			document.getElementById("changeDay").style.display = "none";
			document.getElementById("changeDayOff").style.display = "none";
			document.getElementById("changeStartDay").style.display = "none";
			document.getElementById("changeEndDay").style.display = "none";
			document.getElementById("changeTime").style.display = "none";
			document.getElementById("changePlan").style.display = "none";

			break;
			
		default:
			document.getElementById("changeDay").style.display = "none";
			document.getElementById("changeDayOff").style.display = "none";
			document.getElementById("changeStartDay").style.display = "none";
			document.getElementById("changeEndDay").style.display = "none";
			document.getElementById("changeTime").style.display = "none";
			document.getElementById("changeNote").style.display = "none";
			document.getElementById("changePlan").style.display = "none";
	}
}