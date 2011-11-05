package gnu.mail.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.ParseException;
import myjava.awt.datatransfer.DataFlavor;
import myjava.awt.datatransfer.UnsupportedFlavorException;

public abstract class Text implements DataContentHandler {

   protected DataFlavor flavor;


   protected Text(String var1, String var2) {
      ActivationDataFlavor var3 = new ActivationDataFlavor(String.class, var1, var2);
      this.flavor = var3;
   }

   protected static String getJavaCharset(String var0) {
      String var1 = "us-ascii";
      if(var0 != null) {
         String var2;
         try {
            var2 = (new ContentType(var0)).getParameter("charset");
         } catch (ParseException var5) {
            return MimeUtility.javaCharset(var1);
         }

         if(var2 != null) {
            var1 = var2;
         }
      }

      return MimeUtility.javaCharset(var1);
   }

   public Object getContent(DataSource var1) throws IOException {
      InputStream var2 = var1.getInputStream();
      String var3 = getJavaCharset(var1.getContentType());
      InputStreamReader var4 = new InputStreamReader(var2, var3);
      char[] var5 = new char[4096];
      StringBuffer var6 = new StringBuffer();

      while(true) {
         int var7 = var4.read(var5);
         if(var7 <= -1) {
            return var6.toString();
         }

         String var8 = new String(var5, 0, var7);
         var6.append(var8);
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
      String var4;
      if(var1 instanceof String) {
         var4 = (String)var1;
      } else if(var1 instanceof byte[]) {
         byte[] var7 = (byte[])((byte[])var1);
         var4 = new String(var7);
      } else if(var1 instanceof char[]) {
         char[] var8 = (char[])((char[])var1);
         var4 = new String(var8);
      } else {
         var4 = var1.toString();
      }

      String var5 = getJavaCharset(var2);
      OutputStreamWriter var6 = new OutputStreamWriter(var3, var5);
      var6.write(var4);
      var6.flush();
   }
}
