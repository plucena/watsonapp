package com.ibm.cloudoe.samples;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/search")
public class SearchResource {
	
@GET
@Path("{id}")
@Produces("application/json")
public String getSearchResult(@PathParam(value="id") String id){
	WatsonAnswers service = new WatsonAnswers();
	String answersJson = service.askQuestion(id); 
	return answersJson;
}


}
