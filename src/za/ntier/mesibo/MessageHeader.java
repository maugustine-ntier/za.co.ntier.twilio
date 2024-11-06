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
	int gid;
	int type;
	int expiry;
	String flags;
	boolean forced;
	String message;
	
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

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getExpiry() {
		return expiry;
	}

	public void setExpiry(int expiry) {
		this.expiry = expiry;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	public boolean isForced() {
		return forced;
	}

	public void setForced(boolean forced) {
		this.forced = forced;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Message() {
		
	}
}
