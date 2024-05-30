package za.co.ntier.twilio.examples;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class Example_WhatsApp {
  // Find your Account Sid and Token at twilio.com/console
  public static final String ACCOUNT_SID = "AC3594e4f0ebf53b71cd4b6747ade0418e";
  public static final String AUTH_TOKEN = "1775159b2f070d11b46b3f0181d763b9";

  public static void main(String[] args) {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message = Message.creator(
      new com.twilio.type.PhoneNumber("whatsapp:+27844627361"),
      new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
      "Your appointment is coming up on July 21 at 3PM")

    .create();

    System.out.println(message.getSid());
  }
}