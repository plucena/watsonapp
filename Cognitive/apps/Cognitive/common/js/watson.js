$("#searchbtn" ).click(function() {
  searchvalue = $("#searchterm").val();	
  
  var gbWS ="http://pergunte.mybluemix.net/watson/search/"+searchvalue;
	// faz chamda ao WS REST via ajax	
	  $.ajax({
		type: 'GET',
		url: gbWS,
		crossDomain: true,
		dataType: "json", 
		success: renderList2, // função de retorno do WS
		error: function(xhr, status, error) {
			  var err = eval("(" + xhr.responseText + ")");
			  alert(err.Message);
		}
	});	  
});

function renderList2(data) {
	alert(data);
}