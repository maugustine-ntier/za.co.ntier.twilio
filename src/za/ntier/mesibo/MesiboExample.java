package za.ntier.mesibo;

public class MesiboExample {

    public static void main(String[] args) {
        // Step 1: Obtain a Mesibo instance
    	System.out.println("Library path: " + System.getProperty("java.library.path"));
    	System.loadLibrary("jniMesiboLibrary"); // Adjust as needed based on actual filename
    	System.out.println("Library loaded successfully.");


        MesiboLibrary.Mesibo mesibo = MesiboLibrary.MesiboInstance(0);

        // Step 2: Set the access token and start Mesibo
        //mesibo.setAccessToken("7p2zvhmmqsddzzb7fbosm5moejzwkwy8uldtbt9dty2cjk378i5yr392jmyc2wv1");
        mesibo.setAccessToken("MIIbWLAJbrFmSbJnuwvqIXuhQJRjDWfdsPTbYRzR4rfr9mN_AKCTTUv2-eA3vGIUhBXitsIeKf1we4Ll4TbRIrhEC0Rlba1WtWfccrY0vp8h8Xff75oQpJjb");
      //  mesibo.setAppId("com.mesibo.mesiboapplication");
        int status = mesibo.start();
        if (status != 0) {
            System.out.println("Failed to start Mesibo.");
            return;
        }
      //  System.out.println("Mesibo started successfully.");

        // Step 3: Initialize and configure a MesiboReadSession manually
      //  MesiboLibrary.MesiboReadSession readSession = new MesiboLibrary.MesiboReadSession();

        // Step 4: Set up a custom MesiboListener
      //  MesiboLibrary.MesiboListener listener = new CustomMesiboListener();

        // Configure the read session
    //    readSession.enableMessages(1);
     //   readSession.enableReadReceipt(1);
    //    readSession.start();
        
    //    String recipientAddress = "27844627361"; // Replace with recipient's address or ID
     //   MesiboLibrary.MesiboMessage message = mesibo.newMessage(recipientAddress);

        // Step 4: Set the message text
       /* String textMessage = "Hello from Mesibo!";
        message.setMessage(textMessage);

        // Step 5: Send the message
        int sendStatus = message.send();
        if (sendStatus != 0) {
            System.out.println("Failed to send message.");
        } else {
            System.out.println("Message sent successfully to " + recipientAddress);
        } */


        // Remember to stop Mesibo when done
      //  readSession.stop();
       mesibo.stop();
    }
    
    

    // Custom listener implementation
    public static class CustomMesiboListener extends MesiboLibrary.MesiboListener {

        @Override
        public int Mesibo_onMessage(MesiboLibrary.MesiboMessage m) {
            System.out.println("Received a message: " + m.getMessage().getString());
            return 0;
        }

        @Override
        public int Mesibo_onMessageUpdate(MesiboLibrary.MesiboMessage m) {
            System.out.println("Message updated.");
            return 0;
        }

        @Override
        public int Mesibo_onMessageStatus(MesiboLibrary.MesiboMessage m) {
            System.out.println("Message status updated.");
            return 0;
        }

        @Override
        public int Mesibo_onPresence(MesiboLibrary.MesiboPresence p) {
            System.out.println("Presence update received.");
            return 0;
        }

        @Override
        public int Mesibo_onConnectionStatus(int status) {
            System.out.println("Connection status: " + status);
            return 0;
        }

        @Override
        public void Mesibo_onEndToEndEncryption(String address, int status) {
            System.out.println("Encryption status for " + address + ": " + status);
        }
    }
}

