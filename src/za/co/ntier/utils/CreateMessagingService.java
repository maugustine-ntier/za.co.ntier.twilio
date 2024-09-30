package za.co.ntier.utils;



import org.compiere.model.MSysConfig;

import com.twilio.Twilio;
import com.twilio.rest.messaging.v1.Service;

public class CreateMessagingService {
	

	public CreateMessagingService() {
		
	}
	
    // Find your Account SID and Auth Token at twilio.com/console

    // and set the environment variables. See http://twil.io/secure


    public static String create(int ad_Client_ID) {
    	
    	String ACCOUNT_SID = MSysConfig.getValue("TWILIO_ACCOUNT_SID", ad_Client_ID);
		String AUTH_TOKEN = MSysConfig.getValue("TWILIO_AUTH_TOKEN", ad_Client_ID);

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Service service = Service.creator("RGN Messaging Service").create();


        System.out.println(service.getSid());
        return service.getSid();

    }


}
