package gnu.mail.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import myjava.awt.datatransfer.DataFlavor;
import myjava.awt.datatransfer.UnsupportedFlavorException;

public abstract class Application implements DataContentHandler {

   protected DataFlavor flavor;


   protected Application(String var1, String var2) {
      ActivationDataFlavor var3 = new ActivationDataFlavor(byte[].class, var1, var2);
      this.flavor = var3;
   }

   public Object getContent(DataSource var1) throws IOException {
      InputStream var2 = var1.getInputStream();
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      byte[] var4 = new byte[4096];

      while(true) {
         int var5 = var2.read(var4);
         if(var5 <= -1) {
            return var3.toByteArray();
         }

         var3.write(var4, 0, var5);
      }
   }

   public Object getTransferData(DataFlavor var1, DataSource var2) throws UnsupportedFlavorException, IOException {
      Object var3;
      if(this.flavor.equals(var1)) {
         var3 = this.getContent(var2);
      } else {
         var3 = null;
      }

      return var3;
   }

   public DataFlavor[] getTransferDataFlavors() {
      DataFlavor[] var1 = new DataFlavor[1];
      DataFlavor var2 = this.flavor;
      var1[0] = var2;
      return var1;
   }

   public void writeTo(Object var1, String var2, OutputStream var3) throws IOException {
      if(var1 instanceof byte[]) {
         byte[] var4 = (byte[])((byte[])var1);
         var3.write(var4);
         var3.flush();
      } else if(!(var1 instanceof InputStream)) {
         StringBuilder var8 = (new StringBuilder()).append("Unsupported data type: ");
         String var9 = var1.getClass().getName();
         String var10 = var8.append(var9).toString();
         throw new IOException(var10);
      } else {
         InputStream var5 = (InputStream)var1;
         byte[] var6 = new byte[4096];

         for(int var7 = var5.read(var6); var7 != -1; var7 = var5.read(var6)) {
            var3.write(var6, 0, var7);
         }

         var3.flush();
      }
   }
}
