package gnu.inet.gopher;

import gnu.inet.gopher.GopherConnection;
import gnu.inet.gopher.GopherContentHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownServiceException;

public class GopherURLConnection extends URLConnection {

   protected GopherConnection connection;


   public GopherURLConnection(URL var1) {
      super(var1);
   }

   public void connect() throws IOException {
      if(!this.connected) {
         String var1 = this.url.getHost();
         int var2 = this.url.getPort();
         GopherConnection var3 = new GopherConnection(var1, var2);
         this.connection = var3;
      }
   }

   public Object getContent() throws IOException {
      return (new GopherContentHandler()).getContent(this);
   }

   public Object getContent(Class[] var1) throws IOException {
      return (new GopherContentHandler()).getContent(this, var1);
   }

   public InputStream getInputStream() throws IOException {
      if(!this.connected) {
         this.connect();
      }

      String var1 = this.url.getPath();
      String var2 = this.url.getFile();
      if(var1 == null && var2 == null) {
         throw new UnsupportedOperationException("not implemented");
      } else {
         String var3;
         if(var1 == null) {
            var3 = var2;
         } else {
            var3 = var1 + '/' + var2;
         }

         return this.connection.get(var3);
      }
   }

   public OutputStream getOutputStream() throws IOException {
      throw new UnknownServiceException();
   }
}
