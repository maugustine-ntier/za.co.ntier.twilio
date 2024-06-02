// Install the Java helper library from twilio.com/docs/java/install
package za.co.ntier.twilio.examples;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class Example_SMS {
    // Find your Account SID and Auth Token at twilio.com/console
    // and set the environment variables. See http://twil.io/secure
    public static final String ACCOUNT_SID = "AC3594e4f0ebf53b71cd4b6747ade0418e";//System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = "6fe43a256c6ed85cb53cdc6dc7017c5b"; //System.getenv("TWILIO_AUTH_TOKEN");

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