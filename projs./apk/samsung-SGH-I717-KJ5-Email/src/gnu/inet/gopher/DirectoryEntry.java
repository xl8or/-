package gnu.inet.gopher;


public final class DirectoryEntry {

   public static final int BINARY = 57;
   public static final int BINHEX = 52;
   public static final int CSO_PHONE_BOOK = 50;
   public static final int DIRECTORY = 49;
   public static final int DOS_ARCHIVE = 53;
   public static final int ERROR = 51;
   public static final int FILE = 48;
   public static final int GIF = 103;
   public static final int IMAGE = 73;
   public static final int INDEX_SEARCH = 55;
   public static final int REDUNDANT = 43;
   public static final int TELNET = 56;
   public static final int TN3270 = 84;
   public static final int UUENCODED = 54;
   final String hostname;
   final int port;
   final String selector;
   final String title;
   final int type;


   DirectoryEntry(int var1, String var2, String var3, String var4, int var5) {
      this.type = var1;
      this.title = var2;
      this.selector = var3;
      this.hostname = var4;
      this.port = var5;
   }

   public String getHostname() {
      return this.hostname;
   }

   public int getPort() {
      return this.port;
   }

   public String getSelector() {
      return this.selector;
   }

   public String getTitle() {
      return this.title;
   }

   public int getType() {
      return this.type;
   }
}
