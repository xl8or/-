package myorg.bouncycastle.mail.smime.handlers;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import myjava.awt.datatransfer.DataFlavor;
import myorg.bouncycastle.mail.smime.SMIMEStreamingProcessor;

public class PKCS7ContentHandler implements DataContentHandler {

   private final ActivationDataFlavor _adf;
   private final DataFlavor[] _dfs;


   PKCS7ContentHandler(ActivationDataFlavor var1, DataFlavor[] var2) {
      this._adf = var1;
      this._dfs = var2;
   }

   public Object getContent(DataSource var1) throws IOException {
      return var1.getInputStream();
   }

   public Object getTransferData(DataFlavor var1, DataSource var2) throws IOException {
      Object var3;
      if(this._adf.equals(var1)) {
         var3 = this.getContent(var2);
      } else {
         var3 = null;
      }

      return var3;
   }

   public DataFlavor[] getTransferDataFlavors() {
      return this._dfs;
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
         if(var1 instanceof SMIMEStreamingProcessor) {
            ((SMIMEStreamingProcessor)var1).write(var3);
         } else {
            String var8 = "unknown object in writeTo " + var1;
            throw new IOException(var8);
         }
      } else {
         Object var6 = (InputStream)var1;
         if(!(var6 instanceof BufferedInputStream)) {
            var6 = new BufferedInputStream((InputStream)var6);
         }

         while(true) {
            int var7 = ((InputStream)var6).read();
            if(var7 < 0) {
               return;
            }

            var3.write(var7);
         }
      }
   }
}
