
serialize = function(form){
	return JSON.stringify($("#"+form).serializeJSON());
}



postForm = function(page, form, redirect){
	$.ajax({
	    url: page, // url where to submit the request
	    type : "POST", // type of action POST || GET
	    dataType : 'json', // data type
	    contentType: "application/json; charset=utf-8",
	    data : serialize(form), // post data || get data
dataType: "text",
	    success : function(result) {
	        console.log(result);
			window.location.href=redirect;
	    },
	    error: function(xhr, resp, text) {
	        console.log(xhr, resp, text);
	    }
	})
}