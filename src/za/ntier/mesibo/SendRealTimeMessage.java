package za.ntier.mesibo;

public class SendRealTimeMessage {

	public SendRealTimeMessage() {
		// TODO Auto-generated constructor stub
	}


	public static void main(String[] args) {
		MesiboLibrary.Mesibo mesibo = new MesiboLibrary.Mesibo();

		// Set your access token
		String accessToken = "7p2zvhmmqsddzzb7fbosm5moejzwkwy8uldtbt9dty2cjk378i5yr392jmyc2wv1";
		if (mesibo.setAccessToken(accessToken) != 0) {
			System.out.println("Failed to set access token");
			return;
		}

		// Start the Mesibo instance
		if (mesibo.start() != 0) {
			System.out.println("Failed to start Mesibo");
			return;
		}

		// Define the recipient address
		String recipientAddress = "27844627361"; // Replace with the actual recipient's address

		// Create a new message for the recipient
		MesiboLibrary.MesiboMessage message = mesibo.newMessage(recipientAddress);

		// Set the message content
		message.setMessage("Hello from Mesibo!");

		// Send the message
		int sendStatus = message.send();
		if (sendStatus != 0) {
			System.out.println("Failed to send message");
		} else {
			System.out.println("Message sent successfully to " + recipientAddress);
		}

		// Stop Mesibo when done
		mesibo.stop();
	}



}
