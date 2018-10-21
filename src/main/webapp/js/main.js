$(document).ready(function() {
	makeRequest();
});

function makeRequest() {
	$.ajax({
    	type: "POST",
        url: "/hello",
        dataType: 'text',
        success: function(response) {
        	$("#welcome").text(response);
        }
    });
}