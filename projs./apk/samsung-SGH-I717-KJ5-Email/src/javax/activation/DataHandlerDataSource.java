package javax.activation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.DataHandler;
import javax.activation.DataSource;

final class DataHandlerDataSource implements DataSource {

   final DataHandler dh;


   DataHandlerDataSource(DataHandler var1) {
      this.dh = var1;
   }

   public String getContentType() {
      return this.dh.getContentType();
   }

   public InputStream getInputStream() throws IOException {
      return this.dh.getInputStream();
   }

   public String getName() {
      return this.dh.getName();
   }

   public OutputStream getOutputStream() throws IOException {
      return this.dh.getOutputStream();
   }
}
