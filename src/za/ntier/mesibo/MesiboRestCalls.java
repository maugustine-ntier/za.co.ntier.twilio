package za.ntier.mesibo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.compiere.model.MSysConfig;

import com.google.gson.Gson;

public class MesiboRestCalls {
	
	private final static String BASE_URL = "https://api.mesibo.com/backend/";
	private HttpClient client;
	
	public MesiboRestCalls() {
		this.client = HttpClient.newHttpClient();
	
		
	}
	
	public static void main(String[] args) {
		String AUTH_TOKEN = MSysConfig.getValue("MESIBO_AUTH_TOKEN", 0);
		MessageHeader messageHeader = new MessageHeader();
		Message message = new Message();
		message.setFrom("27832604474");
		message.setTo("27844627361");
		message.setGid(0);
		message.setType(0);
		message.setExpiry(3600);
		message.setFlags(null);
		message.setForced(true);
		message.setMessage("Test Message From Eclipse");
		messageHeader.setOp("message");
	    messageHeader.setToken(AUTH_TOKEN);
	    messageHeader.setMessageDetail(message);
		Gson gson = new Gson();
		String jsonRequest = gson.toJson(messageHeader);
		System.out.println(jsonRequest);
		
		MesiboRestCalls mesiboRestCalls = new MesiboRestCalls(); 
		HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .setHeader("content-type", "application/json")
                .POST(BodyPublishers.ofString(jsonRequest))
                .build();
			    
		HttpResponse<String> response = null;
				
		try {
			response = mesiboRestCalls.client.send(postRequest,BodyHandlers.ofString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(response.body());
		}
       // return objectMapper.readValue(response.body(), new TypeReference<>() {});
		System.out.println(response.body());
		
	}

}
