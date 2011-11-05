package gnu.inet.gopher;

import gnu.inet.gopher.GopherURLConnection;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class Handler extends URLStreamHandler {

   public Handler() {}

   protected int getDefaultPort() {
      return 80;
   }

   protected URLConnection openConnection(URL var1) throws IOException {
      return new GopherURLConnection(var1);
   }
}
