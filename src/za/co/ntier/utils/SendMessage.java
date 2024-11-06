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
import org.davidmoten.text.utils.WordWrap;

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
			final String WEB_DIR = MSysConfig.getValue("WEB_DIR", ad_Client_ID);
			if (WEB_DIR == null || WEB_DIR.isBlank()) {
				return "Set WEB_DIR IN System Config";
			}
			final String PUBLIC_ATTACHMENT_URL = MSysConfig.getValue("PUBLIC_ATTACHMENT_URL", ad_Client_ID);
			if (PUBLIC_ATTACHMENT_URL == null || PUBLIC_ATTACHMENT_URL.isBlank()) {
				return "Set PUBLIC_ATTACHMENT_URL IN System Config";
			}
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
			dateTimeUpdated = dateTimeUpdated.substring(0,dateTimeUpdated.lastIndexOf(":"));
			String ContentVariables="{\"1\": \"" + ticketNo + "\",\"2\": \"" + respPerson + "\""
					+ ",\"3\": \"" + priority + "\""
					+ ",\"4\": \"" + updatedBy + "\""
					+ ",\"5\": \"" + dateTimeUpdated + "\",\"6\": \"" + summaryMsg1 + "\",\"7\": \"" +  summaryMsg2 + "\",\"8\": \"" + latestResp + "\"" + "}";


			String mess = "*Request Ref: {0}* \n " +
					"Responsible person: {1} \n" + 
					"Priority: {2} \n" +
					"Updated by {3} \n on : {4} \n" +
					"------------------------------------------\n" +
					"{5} \n" +
					"{6} \n" +
					"------------------------------------------ \n" +
					"Latest response: \n" +
					"{7}  \n \n" +
					"Sent by RGN ERP";
			String myText = java.text.MessageFormat.format(mess.replaceAll("'", "''"), ticketNo,respPerson,priority,updatedBy,dateTimeUpdated,summ1Combined,summ2Combined,respCombined);

			//	Message message = Message
			//			.creator(new com.twilio.type.PhoneNumber("whatsapp:" + To_Number),
			//				new com.twilio.type.PhoneNumber("whatsapp:+27635808075"), "HXd8240621afe2fd9249c1594f3e43ef02")
			//		
			//          .setContentVariables(ContentVariables)
			//			.create();
			URI fileUri = null;
			List<URI> listImgs = new ArrayList<URI>();;
			if (attachments != null && !attachments.isEmpty()) {
				for (File attachment: attachments) {
					File file = attachment.getAbsoluteFile();
					File newFile = new File(WEB_DIR + file.getName().replaceAll("\\s+", ""));
					//File newFile = new File("/opt/idempiere-server/jettyhome/work/jetty-0_0_0_0-8443-org_adempiere_server_12_0_0_202405220808_jar-_-any-/webapp/" + file.getName().replaceAll("\\s+", ""));
					//File newFile = new File("/home/martin/web/" + file.getName().replaceAll("\\s+", ""));
					FileInputStream inptStrm = new FileInputStream(file);
					FileOutputStream outStrm = new FileOutputStream(newFile);
					int info = 0;
					// reading the given file1
					while( (info = inptStrm.read()) != -1) {
						outStrm.write(info); // writing to file2
					}
					fileUri = new URI(PUBLIC_ATTACHMENT_URL + file.getName().replaceAll("\\s+", ""));
					//fileUri = new URI("https://ntierdev.thruhere.net:1443/"+file.getName().replaceAll("\\s+", ""));
					//fileUri = new URI("https://61ab-41-10-17-40.ngrok-free.app/"+newFile.getName());

					listImgs.add(fileUri);
				}
			}

			URI uri = new URI(PUBLIC_ATTACHMENT_URL + TextToGraphics.convert(myText, ticketNo,WEB_DIR));
			//URI uri = new URI("https://ntierdev.thruhere.net:1443/" + TextToGraphics.convert(myText, ticketNo,WEB_DIR));
			//	URI uri = new URI("https://61ab-41-10-17-40.ngrok-free.app/" + TextToGraphics.convert(myText, ticketNo));
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

			if (listImgs != null && !listImgs.isEmpty()) {
				for (URI listImg:listImgs) {
					Message message2 = Message.creator(
							new com.twilio.type.PhoneNumber("whatsapp:" + To_Number),
							new com.twilio.type.PhoneNumber("whatsapp:+27635808075"),"")
							//	.setContentSid("HXf15d314f26c5429d6adf1b4c5f766d17")
							.setMessagingServiceSid("MG1baf22d07179a7e13242a15e63216545")
							//	.setContentVariables(ContentVariables)
							.setMediaUrl(listImg)
							.create();
				}
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
					.setContentSid("HXf4d112c700bf17a81b78619d70755c4e")
					.setMessagingServiceSid("MG1baf22d07179a7e13242a15e63216545")
					.create();


			System.out.println(message.getSid());
		}
		return "Message Sent";
	}

}


