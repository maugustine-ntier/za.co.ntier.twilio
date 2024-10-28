package za.co.ntier.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MSysConfig;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import za.co.ntier.twilio.models.X_TW_Message;

public class SendMessage {



	public static String send(Properties ctx, int ad_Client_ID, String type, String To_Number, String ticketNo,String respPerson,String priority,String updatedBy,
			String dateTimeUpdated,String summaryMsg,
			String latestResp,List<File> attachments) throws Exception {
		String ACCOUNT_SID = MSysConfig.getValue("TWILIO_ACCOUNT_SID", ad_Client_ID);
		String AUTH_TOKEN = MSysConfig.getValue("TWILIO_AUTH_TOKEN", ad_Client_ID);
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		if (type.equals(X_TW_Message.TWILIO_MESSAGE_TYPE_SMS)) {
			Message message = Message.creator(
					new com.twilio.type.PhoneNumber(To_Number),
					new com.twilio.type.PhoneNumber("+14012082693"), 
					summaryMsg).create();

			System.out.println(message.getSid());
		} else { // Assume Whatsapp
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
			latestResp = latestResp.replace("\n", ", ");
			dateTimeUpdated = dateTimeUpdated.substring(0,dateTimeUpdated.lastIndexOf(":"));
			String ContentVariables="{\"1\": \"" + ticketNo + "\",\"2\": \"" + respPerson + "\""
					+ ",\"3\": \"" + priority + "\""
					+ ",\"4\": \"" + updatedBy + "\""
					+ ",\"5\": \"" + dateTimeUpdated + "\",\"6\": \"" + summaryMsg1 + "\",\"7\": \"" +  summaryMsg2 + "\",\"8\": \"" + latestResp + "\"" + "}";
			
			
			String mess = "*Request Ref: {0}* /n " +

                          "Responsible person: {1} /n" + 
                          "Priority: {2} /n" +
                          "Updated by {3} on : {4} /n" +
                          "------------------------------------------/n" +
                          "{5} /n" +
                          "{6} /n" +
                          "------------------------------------------ /n" +
                          "Latest response: /n" +
                          "{7}  /n /n" +
                          "Sent by RGN ERP";
			String myText = java.text.MessageFormat.format(mess.replaceAll("'", "''"), ticketNo,respPerson,priority,updatedBy,dateTimeUpdated,summaryMsg1,summaryMsg2,latestResp);

			//	Message message = Message
			//			.creator(new com.twilio.type.PhoneNumber("whatsapp:" + To_Number),
			//				new com.twilio.type.PhoneNumber("whatsapp:+27635808075"), "HXd8240621afe2fd9249c1594f3e43ef02")
			//		
			//          .setContentVariables(ContentVariables)
			//			.create();
			URI fileUri = null;
			List<URI> listImg = null;
			if (attachments != null && !attachments.isEmpty()) {
				File file = attachments.get(0).getAbsoluteFile();
				File newFile = new File("/home/martin/web/" + file.getName());
				FileInputStream inptStrm = new FileInputStream(file);
		        FileOutputStream outStrm = new FileOutputStream(newFile);
		        int info = 0;
		         // reading the given file1
		         while( (info = inptStrm.read()) != -1) {
		            outStrm.write(info); // writing to file2
		         }
			     fileUri = new URI("https://cf13-41-10-17-40.ngrok-free.app/"+file.getName());
				 listImg = new ArrayList<URI>();
				 listImg.add(fileUri);
			}
			
			URI uri = new URI("https://cf13-41-10-17-40.ngrok-free.app/" + TextToGraphics.convert(summaryMsg1, ticketNo));
			List<URI> listImg2 = new ArrayList<URI>();
			listImg2.add(uri);

			Message message = Message.creator(
					new com.twilio.type.PhoneNumber("whatsapp:" + To_Number),
					new com.twilio.type.PhoneNumber("whatsapp:+27635808075"),"")
				//	.setContentSid("HXf15d314f26c5429d6adf1b4c5f766d17")
					.setMessagingServiceSid("MG1baf22d07179a7e13242a15e63216545")
				//	.setContentVariables(ContentVariables)
					 .setMediaUrl(listImg2)
					.create();

			if (listImg != null && !listImg.isEmpty()) {
				Message message2 = Message.creator(
						new com.twilio.type.PhoneNumber("whatsapp:" + To_Number),
						new com.twilio.type.PhoneNumber("whatsapp:+27635808075"),"")
					//	.setContentSid("HXf15d314f26c5429d6adf1b4c5f766d17")
						.setMessagingServiceSid("MG1baf22d07179a7e13242a15e63216545")
					//	.setContentVariables(ContentVariables)
						.setMediaUrl(listImg)
						.create();
			}

			System.out.println(message.getSid());
		}
		return "Message Sent";
	}
	
	
	public static String sendOptInMessage(Properties ctx, int ad_Client_ID, String type, String To_Number) throws Exception {
		String ACCOUNT_SID = MSysConfig.getValue("TWILIO_ACCOUNT_SID", ad_Client_ID);
		String AUTH_TOKEN = MSysConfig.getValue("TWILIO_AUTH_TOKEN", ad_Client_ID);
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		if (type.equals(X_TW_Message.TWILIO_MESSAGE_TYPE_SMS)) {
			Message message = Message.creator(
					new com.twilio.type.PhoneNumber(To_Number),
					new com.twilio.type.PhoneNumber("+14012082693"), 
					" ").create();

			System.out.println(message.getSid());
		} else { // Assume Whatsapp
		

			Message message = Message.creator(
					new com.twilio.type.PhoneNumber("whatsapp:" + To_Number),
					new com.twilio.type.PhoneNumber("whatsapp:+27635808075"),"")
					.setContentSid("HXb725d56546a66a01f5856cd887226e40")
					.setMessagingServiceSid("MG1baf22d07179a7e13242a15e63216545")
					.create();


			System.out.println(message.getSid());
		}
		return "Message Sent";
	}

}


