$(document).ready(function() {
	$("#descriptor").val("");
	$("#url").val("");
	$("#payment").val("");
	
	$("#descriptor").focus();
	
	$("#addBtn").click(add);
	
	$("#descriptor").change(function() {
        $("#descriptor").removeClass("invalidField");
    });
	
	$("#url").change(function() {
        $("#url").removeClass("invalidField");
    });
	
	$("#payment").change(function() {
        $("#payment").removeClass("invalidField");
    });
});

function add() {
	var formValid = true;
	
	if(!$("#descriptor").val()) {
        $("#descriptor").addClass("invalidField");
		formValid = false;
    }
	
	if(!$("#url").val()) {
        $("#url").addClass("invalidField");
		formValid = false;
    }
	
	if(!$("#payment").val()) {
        $("#payment").addClass("invalidField");
		formValid = false;
    }
	
	if(!formValid) {
		return;
	}
	
	var requestData =
	{
		"descriptor": $("#descriptor").val(),
		"url": $("#url").val(),
		"payment": parseInt($("#payment").val())
	};
  
	$.ajax({
		type: "POST",
		url: "/add",
		dataType: "text",
		contentType: 'application/json',
        data: JSON.stringify(requestData),
		success: function(response) {
			alert(response);
		},
		error: function(xhr, exception) {
			alert(exception);
		}
	});
	
	$("#descriptor").val("");
	$("#url").val("");
	$("#payment").val("");
}