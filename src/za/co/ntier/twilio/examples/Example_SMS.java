// Install the Java helper library from twilio.com/docs/java/install
package za.co.ntier.twilio.examples;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class Example_SMS {
    // Find your Account SID and Auth Token at twilio.com/console
    // and set the environment variables. See http://twil.io/secure
    public static final String ACCOUNT_SID = "";//System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = "null"; //System.getenv("TWILIO_AUTH_TOKEN");

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+27844627361"),
                new com.twilio.type.PhoneNumber("+14012082693"),
                "Where's Wallace?")
            .create();

        System.out.println(message.getSid());
    }
}