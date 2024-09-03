package za.co.ntier.utils;

import java.util.Properties;

import org.compiere.model.MSysConfig;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import za.co.ntier.twilio.models.X_TW_Message;

public class SendMessage {



	public static String send(Properties ctx, int ad_Client_ID, String type, String To_Number, String ticketNo,String respPerson,String dateTimeUpdated,String msgToSend,
			String latestResp) throws Exception {
		String ACCOUNT_SID = MSysConfig.getValue("TWILIO_ACCOUNT_SID", ad_Client_ID);
		String AUTH_TOKEN = MSysConfig.getValue("TWILIO_AUTH_TOKEN", ad_Client_ID);
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		if (type.equals(X_TW_Message.TWILIO_MESSAGE_TYPE_SMS)) {
			Message message = Message.creator(
					new com.twilio.type.PhoneNumber(To_Number),
					new com.twilio.type.PhoneNumber("+14012082693"), 
					msgToSend).create();

			System.out.println(message.getSid());
		} else { // Assume Whatsapp
			String ContentVariables="{\"1\": \"" + ticketNo + "\",\"2\": \"" + msgToSend + "\"}";

		//	Message message = Message
		//			.creator(new com.twilio.type.PhoneNumber("whatsapp:" + To_Number),
			//				new com.twilio.type.PhoneNumber("whatsapp:+27635808075"), "HXd8240621afe2fd9249c1594f3e43ef02")
			//		
          //          .setContentVariables(ContentVariables)
		//			.create();
			
	        Message message = Message.creator(
                    new com.twilio.type.PhoneNumber("whatsapp:" + To_Number),
                    new com.twilio.type.PhoneNumber("whatsapp:+27635808075"),"").setContentSid("HXf0f98dd69db9ee8b48295bb7d89aa329")
             .setContentVariables(ContentVariables)
             .create();


			System.out.println(message.getSid());
		}
		return "Message Sent";
	}

}


