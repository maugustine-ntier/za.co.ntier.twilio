package za.ntier.mesibo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MSysConfig;

import com.google.gson.Gson;

public class MesiboRestCalls {

	private final static String BASE_URL = "https://api.mesibo.com/backend/";
	private HttpClient client;

	public MesiboRestCalls() {
		this.client = HttpClient.newHttpClient();


	}

	public static String send (Properties ctx, int ad_Client_ID,  String To_Number, String ticketNo,String respPerson,String priority,String updatedBy,
			String dateTimeUpdated,String summaryMsg,
			String latestResp,List<File> attachments) throws Exception {
		//(String To_Number, String summaryMsg,List<File> attachments) throws Exception {

		final String WEB_DIR = MSysConfig.getValue("WEB_DIR", ad_Client_ID);
		if (WEB_DIR == null || WEB_DIR.isBlank()) {
			return "Set WEB_DIR IN System Config";
		}
		final String PUBLIC_ATTACHMENT_URL = MSysConfig.getValue("PUBLIC_ATTACHMENT_URL", ad_Client_ID);
		if (PUBLIC_ATTACHMENT_URL == null || PUBLIC_ATTACHMENT_URL.isBlank()) {
			return "Set PUBLIC_ATTACHMENT_URL IN System Config";
		}
		String urlLists = null;
		List<String> listImgs = new ArrayList<String>();
		List<String> listTitles = new ArrayList<String>();
		sendMessageWithImage(To_Number, ticketNo, respPerson, priority, updatedBy, dateTimeUpdated, summaryMsg,
				latestResp, null,null );
		if (attachments != null && !attachments.isEmpty()) {
			for (File attachment: attachments) {
				File file = attachment.getAbsoluteFile();
				File newFile = new File(WEB_DIR + file.getName().replaceAll("\\s+", ""));
				FileInputStream inptStrm = new FileInputStream(file);
				FileOutputStream outStrm = new FileOutputStream(newFile);
				int info = 0;
				while( (info = inptStrm.read()) != -1) {
					outStrm.write(info); // writing to file2
				}
				//	listImgs.add(PUBLIC_ATTACHMENT_URL + file.getName().replaceAll("\\s+", ""));
				//	listTitles.add(file.getName().replaceAll("\\s+", ""));
				//	if (urlLists == null) {
				//		urlLists = PUBLIC_ATTACHMENT_URL + file.getName().replaceAll("\\s+", "");
				//	} else {
				//		urlLists = urlLists + "\n" + PUBLIC_ATTACHMENT_URL + file.getName().replaceAll("\\s+", "");
				//	}
				sendMessageWithImage(To_Number, ticketNo, respPerson, priority, updatedBy, dateTimeUpdated, null,
						null, PUBLIC_ATTACHMENT_URL + file.getName().replaceAll("\\s+", ""),file.getName().replaceAll("\\s+", "") );
			}
		} 

		return "sent";
	}

	private static void sendMessageWithImage(String To_Number, String ticketNo, String respPerson, String priority,
			String updatedBy, String dateTimeUpdated, String summaryMsg, String latestResp, String imageURL,String title) {
		/* if (summaryMsg != null) {
			String summaryMsg1 = summaryMsg.trim();			
			String summaryMsg2 = "";
			if (summaryMsg != null) {
				String[] strArray = summaryMsg.lines().toArray(String[]::new);
				if (strArray != null && strArray.length > 0) {
					summaryMsg1 = strArray[0];
					if (strArray.length > 1) {
						summaryMsg2 = String.join(", ", Arrays.copyOfRange(strArray, 1, strArray.length));
					}
				}
			}
			if (summaryMsg1 == null || summaryMsg1.equals("")) {
				summaryMsg1 = "___";
			}
			if (summaryMsg2 == null || summaryMsg2.equals("")) {
				summaryMsg2 = "___";
			}
			//latestResp = latestResp.replace("\n", ", ");
			String respCombined = "";
			String[] text_array = latestResp.split("[\n]");
			for (String txt:text_array) {
				respCombined = respCombined + "\n" + ( WordWrap.from(txt)
						.maxWidth(30)
						.insertHyphens(true) // true is the default
						.wrap());
			}
			String summ1Combined = "";
			String[] summ1_array = summaryMsg1.split("[\n]");
			for (String txt:summ1_array) {
				summ1Combined = summ1Combined + ( WordWrap.from(txt)
						.maxWidth(30)
						.insertHyphens(true) // true is the default
						.wrap());
			}
			String summ2Combined = "";
			String[] summ2_array = summaryMsg2.split("[\n]");
			for (String txt:summ2_array) {
				summ2Combined = summ2Combined + ( WordWrap.from(txt)
						.maxWidth(30)
						.insertHyphens(true) // true is the default
						.wrap());
			}
		} */
		dateTimeUpdated = dateTimeUpdated.substring(0,dateTimeUpdated.lastIndexOf(":"));
		String mess = "*Request Ref: {0}* \n " +
				"Responsible person: {1} \n" + 
				"Priority: {2} \n" +
				"Updated by {3} on : {4} \n" +
				"------------------------------------------\n" +
				"{5} \n" +
				//	"{6} \n" +
				"------------------------------------------ \n" +
				"Latest response: \n" +
				"{6}  \n \n" +
				"Sent by RGN ERP";
		String myText = " ";
		if (summaryMsg != null || latestResp != null) {
			myText = java.text.MessageFormat.format(mess.replaceAll("'", "''"), ticketNo,respPerson,priority,updatedBy,dateTimeUpdated,
					(summaryMsg != null) ? summaryMsg.trim() : "None",latestResp);
		}

		String AUTH_TOKEN = MSysConfig.getValue("MESIBO_AUTH_TOKEN", 0);
		MessageHeader messageHeader = new MessageHeader();
		Message message = new Message();
		message.setFrom("martinaugustine91@gmail.com");
		message.setTo(To_Number.substring(1));

		message.setMessage(myText);
		if (imageURL != null) {
			message.setUrl(imageURL);
			message.setTitle(title);
		}
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
		System.out.println(response.body());
	}

	public static void main(String[] args) {
		String AUTH_TOKEN = MSysConfig.getValue("MESIBO_AUTH_TOKEN", 0);
		MessageHeader messageHeader = new MessageHeader();
		Message message = new Message();
		message.setFrom("27832604474");
		message.setTo("27844627361");
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
