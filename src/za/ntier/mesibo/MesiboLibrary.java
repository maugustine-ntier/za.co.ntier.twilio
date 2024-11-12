package za.ntier.mesibo;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Platform;

@Platform(
	    include = "/usr/include/mesibo.h",
	    link = "mesibo",  // Link against libmesibo.so
	    compiler = "cpp11"
	)
public class MesiboLibrary {


	// Wrapper for the MesiboData structure
	@Name("MesiboData")
	public static class MesiboData extends Pointer {
		//public MesiboData() { allocate(); }
	//	private native void allocate();

		public native @Cast("char*") BytePointer data();
		public native MesiboData data(BytePointer data);

		public native @Cast("uint32_t") int len();
		public native MesiboData len(int len);

		public native @Cast("uint8_t") byte type();
		public native MesiboData type(byte type);
	}

	// Wrapper for the MesiboFile structure
	@Name("MesiboFile")
	public static class MesiboFile extends Pointer {
		//public MesiboFile() { allocate(); }
		//private native void allocate();

		public native @Cast("uint8_t") byte type();
		public native MesiboFile type(byte type);

		public native @Cast("uint8_t") byte subtype();
		public native MesiboFile subtype(byte subtype);

		public native @Cast("uint32_t") int size();
		public native MesiboFile size(int size);

		public native @Cast("const char*") BytePointer name();
		public native MesiboFile name(BytePointer name);

		// Other fields like path, url, mimeType, and thumbnail as seen before
	}


	// Wrapper for MesiboPresence
	@Name("MesiboPresence")
    public static class MesiboPresence extends Pointer {
        // Fields, accessed directly
        @MemberGetter public native @Cast("uint32_t") int presence();
        @MemberGetter public native @Cast("uint32_t") int value();
        @MemberGetter public native @Cast("uint32_t") int interval();
        @MemberGetter public native @Cast("uint32_t") int expiry();
        @MemberGetter public native @Cast("uint32_t") int groupid();
        @MemberGetter public native @Cast("const char*") BytePointer peer();

        // Presence-related methods
        public native int send();
        public native int send(@Cast("uint32_t") int presence);
        public native int sendTyping();
        public native int sendTypingCleared();
        public native int sendJoined();
        public native int sendChatting();
        public native int sendLeft();
        public native int sendOnline();
        public native int sendOffline();

        // Checking presence status
        public native int isRequest();
        public native int isOnline();
        public native int isOffline();
        public native int isTyping();
        public native int isTypingCleared();
        public native int isChatting();
        public native int hasJoined();
        public native int hasLeft();
    }

	// Wrapper for MesiboListener with all relevant methods
    @Name("MesiboListener")
    public static class MesiboListener extends Pointer {
        public native int Mesibo_onMessage(MesiboMessage m);
        public native int Mesibo_onMessageUpdate(MesiboMessage m);
        public native int Mesibo_onMessageStatus(MesiboMessage m);
        public native int Mesibo_onPresence(MesiboPresence p);
        public native int Mesibo_onConnectionStatus(int status);
        public native void Mesibo_onEndToEndEncryption(@Cast("const char*") String address, int status);
    }

    // Wrapper for MesiboReadSession with its methods
    @Name("MesiboReadSession")
    public static class MesiboReadSession extends Pointer {
        public native int read(int count);
        public native int start();
        public native void stop();
        public native int sync(int count);
        public native void enableReadReceipt(int enable);
        public native void enableSummary(int enable);
        public native void enableFifo(int enable);
        public native void enableFiles(int enable);
        public native void enableMessages(int enable);
        public native void enableMissedCalls(int enable);
        public native void enableIncomingCalls(int enable);
        public native void enableOutgoingCalls(int enable);
        public native void enableCalls(int enable);
    }

	// Wrapper for MesiboEndToEndEncryption
	@Name("MesiboEndToEndEncryption")
    public static class MesiboEndToEndEncryption extends Pointer {
        // Enable or disable encryption
        public native void enable(int enable);

        // Encryption status and level settings
        public native int getStatus(@Cast("const char*") String address);
        public native int isActive(@Cast("const char*") String address);
        public native int setLevel(int level);
        public native int setCiphers(@Cast("uint32_t") int supported, @Cast("uint32_t") int preferred);
        public native int setPassword(@Cast("const char*") String address, @Cast("const char*") String password, int len);
        public native int setAuthenticationTaglen(int len);
        public native int setAuthenticationData(@Cast("const char*") String address, @Cast("const char*") String password, int len);

        // Certificate handling
        public native int getPublicCertificate(@Cast("const char*") String filename);
        public native int setPrivateCertificate(@Cast("const char*") String filename);
        public native int setPeerCertificate(@Cast("const char*") String address, @Cast("const char*") String filename);

        // Certificate info retrieval
        public native @Cast("const char*") BytePointer getPeerCertificateOrg(@Cast("const char*") String address);
        public native @Cast("const char*") BytePointer getPeerCertificateCommonName(@Cast("const char*") String address);

        // Fingerprint retrieval
        public native @Cast("const char*") BytePointer getFingerprint(@Cast("const char*") String address);
        public native @Cast("const char*") BytePointer getUserFingerprint(@Cast("const char*") String address);

        // Configuration
        public native int setConfig(int level, @Cast("uint32_t") int minops, @Cast("uint32_t") int maxops, @Cast("uint32_t") int mindur, @Cast("uint32_t") int maxdur);
    }


	@Name("MesiboInstance")
    public static native Mesibo MesiboInstance(@Cast("uint32_t") int bufsize);

	@Name("Mesibo")
	public static class Mesibo extends Pointer {
		// Abstract class: no allocate() method

		// Methods for Mesibo
		public native int start();
		public native int stop();
		public native int reconnect();

		@Name("wait") 
		public native int waitFor();

		// Configuration methods
		public native int setAccessToken(@Cast("const char*") String token);
		public native int setPath(@Cast("const char*") String path);
		public native int setDatabase(@Cast("const char*") String dbNameWithPath, int resetTables);
		public native void setAppId(@Cast("const char*") String name);

		// Create message and presence instances
		public native MesiboMessage newMessage(@Cast("const char*") String to, @Cast("uint32_t") int gid);
		public native MesiboMessage newMessage(@Cast("const char*") String to);
		public native MesiboMessage newMessage(@Cast("uint32_t") int gid);
	}

	@Name("MesiboMessage")
	public static class MesiboMessage extends Pointer {
	    // No allocate() method here because MesiboMessage is abstract and cannot be instantiated directly

	    // Set message properties
	    public native void setMessage(@Cast("const char*") String message);
	    public native void setTitle(@Cast("const char*") String title);
	    public native void setSubtitle(@Cast("const char*") String subtitle);
	    public native void setContent(@Cast("const char*") String fileOrUrl);
	    public native void setContentType(int type);
	    public native void setThumbnail(@Cast("const char*") String tn, @Cast("uint16_t") short len);
	    public native void setLatitude(double latitude);
	    public native void setLongitude(double longitude);

	    // Get message properties
	    public native @Cast("const char*") BytePointer getMessage();
	    public native @Cast("const char*") BytePointer getTitle();
	    public native @Cast("const char*") BytePointer getSubtitle();
	    public native double getLatitude();
	    public native double getLongitude();

	    // Send and manage the message
	    public native int send();
	    public native int resend();
	    public native int deleteMessage();
	    public native int wipe();
	    public native int recall();
	    public native int wipeAndRecall();
	    public native int mayBeRetracted();

	    // Forward the message
	    public native MesiboMessage forward(@Cast("const char*") String peer);
	    public native MesiboMessage forward(@Cast("uint32_t") int gid);

	    // Free the message resources
	    public native void free();
	}


}

