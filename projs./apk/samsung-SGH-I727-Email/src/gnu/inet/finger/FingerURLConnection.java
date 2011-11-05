package gnu.inet.finger;

import gnu.inet.finger.FingerConnection;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

class FingerURLConnection extends URLConnection {

   FingerConnection connection;
   String response;


   FingerURLConnection(URL var1) throws IOException {
      super(var1);
   }

   public void connect() throws IOException {
      if(this.connection == null) {
         String var1 = this.url.getHost();
         int var2 = this.url.getPort();
         FingerConnection var3 = new FingerConnection(var1, var2);
         this.connection = var3;
         FingerConnection var4 = this.connection;
         String var5 = this.url.getUserInfo();
         String var6 = var4.finger(var5);
         this.response = var6;
      }
   }

   public InputStream getInputStream() throws IOException {
      if(!this.connected) {
         this.connect();
      }

      byte[] var1 = this.response.getBytes("US-ASCII");
      return new ByteArrayInputStream(var1);
   }
}
