package za.co.ntier.utils;

import java.util.Properties;

import org.compiere.model.MSysConfig;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import za.co.ntier.twilio.models.X_TW_Message;

public class SendMessage {



	public static String send(Properties ctx, int ad_Client_ID, String type, String To_Number,
			String msgToSend) throws Exception {
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
			Message message = Message
					.creator(new com.twilio.type.PhoneNumber("whatsapp:" + To_Number),
							new com.twilio.type.PhoneNumber("whatsapp:+14155238886"), msgToSend)

					.create();

			System.out.println(message.getSid());
		}
		return "Message Sent";
	}

}


