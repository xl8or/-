package gnu.inet.finger;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FingerConnection {

   public static final int DEFAULT_PORT = 79;
   private static final String US_ASCII = "US-ASCII";
   protected Socket socket;
   protected boolean verbose;


   public FingerConnection(String var1) throws IOException {
      this(var1, 79);
   }

   public FingerConnection(String var1, int var2) throws IOException {
      Socket var3 = new Socket(var1, var2);
      this.socket = var3;
   }

   public String finger(String var1) throws IOException {
      return this.finger(var1, (String)null);
   }

   public String finger(String var1, String var2) throws IOException {
      OutputStream var3 = this.socket.getOutputStream();
      BufferedOutputStream var4 = new BufferedOutputStream(var3);
      if(this.verbose) {
         var4.write(47);
         var4.write(87);
         if(var1 != null || var2 != null) {
            var4.write(32);
         }
      }

      if(var1 != null) {
         byte[] var5 = var1.getBytes("US-ASCII");
         var4.write(var5);
      }

      if(var2 != null) {
         var4.write(64);
         byte[] var6 = var2.getBytes("US-ASCII");
         var4.write(var6);
      }

      var4.write(13);
      var4.write(10);
      var4.flush();
      InputStream var7 = this.socket.getInputStream();
      ByteArrayOutputStream var8 = new ByteArrayOutputStream();
      byte[] var9 = new byte[4096];

      for(int var10 = var7.read(var9); var10 != -1; var10 = var7.read(var9)) {
         var8.write(var9, 0, var10);
      }

      return var8.toString("US-ASCII");
   }

   public boolean isVerbose() {
      return this.verbose;
   }

   public String list() throws IOException {
      return this.finger((String)null, (String)null);
   }

   public void setVerbose(boolean var1) {
      this.verbose = var1;
   }
}
