package myorg.bouncycastle.mail.smime.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import myjava.awt.datatransfer.DataFlavor;

public class x_pkcs7_signature implements DataContentHandler {

   private static final ActivationDataFlavor ADF = new ActivationDataFlavor(MimeBodyPart.class, "application/x-pkcs7-signature", "Signature");
   private static final DataFlavor[] ADFs;


   static {
      DataFlavor[] var0 = new DataFlavor[1];
      ActivationDataFlavor var1 = ADF;
      var0[0] = var1;
      ADFs = var0;
   }

   public x_pkcs7_signature() {}

   public Object getContent(DataSource var1) throws IOException {
      return var1.getInputStream();
   }

   public Object getTransferData(DataFlavor var1, DataSource var2) throws IOException {
      Object var3;
      if(ADF.equals(var1)) {
         var3 = this.getContent(var2);
      } else {
         var3 = null;
      }

      return var3;
   }

   public DataFlavor[] getTransferDataFlavors() {
      return ADFs;
   }

   public void writeTo(Object var1, String var2, OutputStream var3) throws IOException {
      if(var1 instanceof MimeBodyPart) {
         try {
            ((MimeBodyPart)var1).writeTo(var3);
         } catch (MessagingException var9) {
            String var4 = var9.getMessage();
            throw new IOException(var4);
         }
      } else if(var1 instanceof byte[]) {
         byte[] var5 = (byte[])((byte[])var1);
         var3.write(var5);
      } else if(!(var1 instanceof InputStream)) {
         String var8 = "unknown object in writeTo " + var1;
         throw new IOException(var8);
      } else {
         InputStream var6 = (InputStream)var1;

         while(true) {
            int var7 = var6.read();
            if(var7 < 0) {
               return;
            }

            var3.write(var7);
         }
      }
   }
}
