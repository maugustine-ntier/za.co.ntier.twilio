package za.co.ntier.twilio.process;

import org.compiere.model.MSysConfig;
import org.compiere.process.SvrProcess;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import za.co.ntier.twilio.models.X_TW_Message;


@org.adempiere.base.annotation.Process
public class SendTwilioMessage extends SvrProcess {
	public String ACCOUNT_SID = null;
	public String AUTH_TOKEN = null; //System.getenv("TWILIO_AUTH_TOKEN");
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String doIt() throws Exception {
		ACCOUNT_SID = MSysConfig.getValue("TWILIO_ACCOUNT_SID", getAD_Client_ID());
		AUTH_TOKEN  = MSysConfig.getValue("TWILIO_AUTH_TOKEN", getAD_Client_ID());
		int record_ID = getRecord_ID();
		X_TW_Message x_TW_Message = new X_TW_Message(getCtx(), record_ID, get_TrxName());
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		if (x_TW_Message.getTwilio_Message_Type().equals(X_TW_Message.TWILIO_MESSAGE_TYPE_SMS)) {			
	        Message message = Message.creator(
	                new com.twilio.type.PhoneNumber(x_TW_Message.getPhone()),
	                new com.twilio.type.PhoneNumber("+14012082693"),
	                x_TW_Message.getMessage())
	            .create();

	        System.out.println(message.getSid());
		} else {  // Assume Whatsapp
			    Message message = Message.creator(
			      new com.twilio.type.PhoneNumber("whatsapp:" + x_TW_Message.getPhone()),
			      new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
			      x_TW_Message.getMessage())

			    .create();

			    System.out.println(message.getSid());
		}
		return "Message Sent";
	}

}
