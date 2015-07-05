
/* JavaScript content from js/watson.js in folder common */
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
   var sresults;
   $.each( data.evidencelist, function() {
      sresults = sresults + data.evidencelists[i].text + "<BR>";
      alert(data.evidencelists[i].text); 
   }
	
}