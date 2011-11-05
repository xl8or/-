package gnu.inet.ftp;

import gnu.inet.ftp.FTPURLConnection;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class Handler extends URLStreamHandler {

   public Handler() {}

   protected int getDefaultPort() {
      return 21;
   }

   public URLConnection openConnection(URL var1) throws IOException {
      return new FTPURLConnection(var1);
   }
}
