//ATTN-BEGIN: Modify this code after integration.
//var results= [];
var results = [
	{ id: "1", descriptor: "Software Architecture Wikipedia", url: "https://en.wikipedia.org/wiki/Software_architecture" },
	{ id: "2", descriptor: "What is a Software Architecture?", url: "https://www.ibm.com/developerworks/rational/library/feb06/eeles/index.html" },
	{ id: "3", descriptor: "Software Architecture Coursera", url: "https://www.coursera.org/learn/software-architecture" },
	{ id: "4", descriptor: "Software Architecture", url: "https://www.sei.cmu.edu/research-capabilities/all-work/display.cfm?customel_datapageid_4050=21328" },
	{ id: "5", descriptor: "How to choose the right software architecture", url: "https://techbeacon.com/top-5-software-architecture-patterns-how-make-right-choice" },
	{ id: "6", descriptor: "What is software architecture & software design?", url: "https://www.synopsys.com/software-integrity/resources/knowledge-database/software-architecture.html" },
	{ id: "7", descriptor: "Types of Software Architects", url: "https://medium.com/@nvashanin/types-of-software-architects-aa03e359d192" }
];
//ATTN-END

$(document).ready(function() {
	getAll();
});

function getAll() {
	//ATTN-BEGIN: Modify this code after integration.
	/*$.ajax({
		type: "GET",
		url: "",
		cache: false,
		success: function(response) {
		  handleResponse(response);
		}
	});*/
	handleResponse();
	//ATTN-END
}

function handleResponse(response) {
	//ATTN-BEGIN: Modify this code after integration.
	//responseObj = JSON.parse(response);
	//resultSet = responseObj.results;
	resultSet = results;
	//ATTN-END
	
	for(var i = 0; i < resultSet.length; i++) {
		$("#resultsContainer").append(
			"<div class='row justify-content-center border col-md-9 mx-auto p-2 mb-2 align-items-center'>" +
				"<div class='col-md-11'>" +
					"<h6>" + resultSet[i].descriptor + "</h6>" +
					"<a href='" + resultSet[i].url + "'>" + resultSet[i].url + "</a>" +
				"</div>" +
				"<div class='col-md-1 text-right'>" +
					"<i id='delete-" + resultSet[i].id + "' class='fas fa-trash text-primary'></i>" +
				"</div>" +
			"</div>"
		);
	}
	
	$('[id*="delete-"]').click(deleteClicked);
}

function deleteClicked() {
	deletePartId = $(this).attr("id").substring(7);
  
	//ATTN-BEGIN: Modify this code after integration.
	/*var input = "id=" + deletePartId;
	
	$.ajax({
		type: "POST",
		url: "",
		data: input,
		cache: false,
		success: function() {
		  getAll();
		}
	});*/
	alert(deletePartId);
	//ATTN-END
}