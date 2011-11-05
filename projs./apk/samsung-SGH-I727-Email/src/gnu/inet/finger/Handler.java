package gnu.inet.finger;

import gnu.inet.finger.FingerURLConnection;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class Handler extends URLStreamHandler {

   public Handler() {}

   protected int getDefaultPort() {
      return 79;
   }

   protected URLConnection openConnection(URL var1) throws IOException {
      return new FingerURLConnection(var1);
   }
}
