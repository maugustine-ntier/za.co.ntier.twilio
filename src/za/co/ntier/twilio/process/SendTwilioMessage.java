package za.co.ntier.twilio.process;

import org.compiere.process.SvrProcess;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import za.co.ntier.twilio.models.X_TW_Message;


@org.adempiere.base.annotation.Process
public class SendTwilioMessage extends SvrProcess {
	public static final String ACCOUNT_SID = "AC3594e4f0ebf53b71cd4b6747ade0418e";//System.getenv("TWILIO_ACCOUNT_SID");
	public static final String AUTH_TOKEN = "1775159b2f070d11b46b3f0181d763b9"; //System.getenv("TWILIO_AUTH_TOKEN");
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String doIt() throws Exception {
		int record_ID = getRecord_ID();
		X_TW_Message x_TW_Message = new X_TW_Message(getCtx(), record_ID, get_TrxName());
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		if (x_TW_Message.getTwilio_Message_Type().equals(X_TW_Message.TWILIO_MESSAGE_TYPE_SMS)) {			
	        Message message = Message.creator(
	                new com.twilio.type.PhoneNumber("+27844627361"),
	                new com.twilio.type.PhoneNumber("+14012082693"),
	                x_TW_Message.getMessage())
	            .create();

	        System.out.println(message.getSid());
		} else {  // Assume Whatsapp
			    Message message = Message.creator(
			      new com.twilio.type.PhoneNumber("whatsapp:+27844627361"),
			      new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
			      x_TW_Message.getMessage())

			    .create();

			    System.out.println(message.getSid());
		}
		return "Message Sent";
	}

}
