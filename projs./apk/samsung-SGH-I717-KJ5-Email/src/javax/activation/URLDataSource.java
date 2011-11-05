package javax.activation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.activation.DataSource;

public class URLDataSource implements DataSource {

   private URLConnection connection;
   private URL url;


   public URLDataSource(URL var1) {
      this.url = var1;
   }

   public String getContentType() {
      try {
         if(this.connection == null) {
            URLConnection var1 = this.url.openConnection();
            this.connection = var1;
         }
      } catch (IOException var5) {
         ;
      }

      Object var2 = null;
      if(this.connection != null) {
         String var3 = this.connection.getContentType();
      }

      if(var2 == null) {
         ;
      }

      return (String)var2;
   }

   public InputStream getInputStream() throws IOException {
      URLConnection var1 = this.url.openConnection();
      this.connection = var1;
      InputStream var2;
      if(this.connection != null) {
         this.connection.setDoInput((boolean)1);
         var2 = this.connection.getInputStream();
      } else {
         var2 = null;
      }

      return var2;
   }

   public String getName() {
      return this.url.getFile();
   }

   public OutputStream getOutputStream() throws IOException {
      URLConnection var1 = this.url.openConnection();
      this.connection = var1;
      OutputStream var2;
      if(this.connection != null) {
         this.connection.setDoOutput((boolean)1);
         var2 = this.connection.getOutputStream();
      } else {
         var2 = null;
      }

      return var2;
   }

   public URL getURL() {
      return this.url;
   }
}
