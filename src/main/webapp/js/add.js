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
	
	//ATTN-BEGIN: Modify this code after integration.
	/*var input = "descriptor=" + $("#descriptor").val() +
				  "&url=" + $("#url").val() +
				  "&payment=" + $("#payment").val() +;
	
	$.ajax({
		type: "POST",
		url: "",
		data: input,
		cache: false,
		success: function() {
		  getAll();
		}
	});*/
	alert("Part Added!");
	//ATTN-END
	
	$("#descriptor").val("");
	$("#url").val("");
	$("#payment").val("");
}