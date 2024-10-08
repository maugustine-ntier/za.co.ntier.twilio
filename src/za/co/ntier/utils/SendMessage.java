package za.co.ntier.utils;

import java.util.Arrays;
import java.util.Properties;

import org.compiere.model.MSysConfig;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import za.co.ntier.twilio.models.X_TW_Message;

public class SendMessage {



	public static String send(Properties ctx, int ad_Client_ID, String type, String To_Number, String ticketNo,String respPerson,String priority,String updatedBy,
			String dateTimeUpdated,String summaryMsg,
			String latestResp) throws Exception {
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

			//	Message message = Message
			//			.creator(new com.twilio.type.PhoneNumber("whatsapp:" + To_Number),
			//				new com.twilio.type.PhoneNumber("whatsapp:+27635808075"), "HXd8240621afe2fd9249c1594f3e43ef02")
			//		
			//          .setContentVariables(ContentVariables)
			//			.create();

			Message message = Message.creator(
					new com.twilio.type.PhoneNumber("whatsapp:" + To_Number),
					new com.twilio.type.PhoneNumber("whatsapp:+27635808075"),"")
					.setContentSid("HXf15d314f26c5429d6adf1b4c5f766d17")
					.setMessagingServiceSid("MG1baf22d07179a7e13242a15e63216545")
					.setContentVariables(ContentVariables)
					.create();


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


