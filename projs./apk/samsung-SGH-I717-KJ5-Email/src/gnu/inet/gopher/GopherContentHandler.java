package gnu.inet.gopher;

import gnu.inet.gopher.GopherConnection;
import gnu.inet.gopher.GopherURLConnection;
import java.io.IOException;
import java.net.ContentHandler;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownServiceException;

public class GopherContentHandler extends ContentHandler {

   public GopherContentHandler() {}

   public Object getContent(URLConnection var1) throws IOException {
      if(!(var1 instanceof GopherURLConnection)) {
         throw new UnknownServiceException();
      } else {
         GopherURLConnection var2 = (GopherURLConnection)var1;
         GopherConnection var3 = var2.connection;
         URL var4 = var2.getURL();
         String var5 = var4.getPath();
         String var6 = var4.getFile();
         Object var7;
         if(var5 == null && var6 == null) {
            var7 = var3.list();
         } else {
            var7 = var2.getInputStream();
         }

         return var7;
      }
   }
}
