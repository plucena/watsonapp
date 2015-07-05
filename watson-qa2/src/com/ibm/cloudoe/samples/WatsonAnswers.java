package com.ibm.cloudoe.samples;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

public class WatsonAnswers {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(WatsonAnswers.class.getName());


	private String serviceName = "question_and_answer";

	// If running locally complete the variables below with the information in VCAP_SERVICES
	private String baseURL = "https://gateway.watsonplatform.net/question-and-answer-beta/api";
	private String username = "404f79a7-a750-457c-847c-7a4035389312";
	private String password = "TZPDo1tQiwNR";
   
	
	public String askQuestion(String question) {
		JSONObject questionJson = new JSONObject();
		questionJson.put("questionText",question);
		JSONObject evidenceRequest = new JSONObject();
		evidenceRequest.put("items",5);
		questionJson.put("evidenceRequest",evidenceRequest);

		JSONObject postData = new JSONObject();
    	postData.put("question",questionJson);

    	try {
    		Executor executor = Executor.newInstance().auth(username, password);
    		URI serviceURI = new URI(baseURL+ "/v1/question/travel").normalize();
    		String answersJson = executor.execute(Request.Post(serviceURI)
			    .addHeader("Accept", "application/json")
			    .addHeader("X-SyncTimeout", "30")
			    .bodyString(postData.toString(), ContentType.APPLICATION_JSON)
			    ).returnContent().asString();
    		return answersJson;
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return "ERROR";
    	}
	}
	
	/**
	 * Format answers from a json string to a key-value list
	 *
	 * @param resultJson the answers in json
	 * @return the key-value answer list
	 */
	public List<Map<String,String>> formatAnswers(String resultJson) {
		List<Map<String,String>> ret = new ArrayList<Map<String,String>>();
		try {
			JSONArray pipelines = JSONArray.parse(resultJson);
			// the response has two pipelines, lets use the first one
			JSONObject answersJson = (JSONObject) pipelines.get(0);
			JSONArray answers = (JSONArray) ((JSONObject) answersJson.get("question")).get("evidencelist");

			for(int i = 0; i < answers.size();i++) {
				JSONObject answer = (JSONObject) answers.get(i);
				Map<String, String> map = new HashMap<String, String>();

				double p = Double.parseDouble((String)answer.get("value"));
				p = Math.floor(p * 100);
				map.put("confidence",  Double.toString(p) + "%");
				map.put("text", (String)answer.get("text"));

				ret.add(map);
			}
		} catch (IOException e) {
    	 logger.log(Level.SEVERE, "Error parsing the response: "+e.getMessage(), e);
       }
		return ret;
	}

	
	  /**
     * If exists, process the VCAP_SERVICES environment variable in order to get the 
     * username, password and baseURL
     */
    public  void processVCAP_Services() {
    	logger.info("Processing VCAP_SERVICES");
        JSONObject sysEnv = getVcapServices();
        if (sysEnv == null) return;
        logger.info("Looking for: "+ serviceName );

        if (sysEnv.containsKey(serviceName)) {
			JSONArray services = (JSONArray)sysEnv.get(serviceName);
			JSONObject service = (JSONObject)services.get(0);
			JSONObject credentials = (JSONObject)service.get("credentials");
			baseURL = (String)credentials.get("url");
			username = (String)credentials.get("username");
			password = (String)credentials.get("password");
			logger.info("baseURL  = "+baseURL);
			logger.info("username   = "+username);
			logger.info("password = "+password);
    	} else {
        	logger.warning(serviceName + " is not available in VCAP_SERVICES, "
        			+ "please bind the service to your application");
        }
    }

    /**
     * Gets the <b>VCAP_SERVICES</b> environment variable and return it
     *  as a JSONObject.
     *
     * @return the VCAP_SERVICES as Json
     */
    public  JSONObject getVcapServices() {
        String envServices = System.getenv("VCAP_SERVICES");
        if (envServices == null) return null;
        JSONObject sysEnv = null;
        try {
        	 sysEnv = JSONObject.parse(envServices);
        } catch (IOException e) {
        	// Do nothing, fall through to defaults
        	logger.log(Level.SEVERE, "Error parsing VCAP_SERVICES: "+e.getMessage(), e);
        }
        return sysEnv;
    }
	
}
