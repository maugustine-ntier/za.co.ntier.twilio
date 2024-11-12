package za.ntier.mesibo;

public class MessageHeader {

	String op;
	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	String token;
	Message message;
	
	public Message getMessageDetail() {
		return message;
	}

	public void setMessageDetail(Message messageDetail) {
		this.message = messageDetail;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public MessageHeader() {
		// TODO Auto-generated constructor stub
	}

}

class Message {
	String from;
	String to;
	String message;
	String url;
	String title;
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Message() {
		
	}
}
