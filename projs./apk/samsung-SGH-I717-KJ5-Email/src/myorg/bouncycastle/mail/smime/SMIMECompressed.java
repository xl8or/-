package myorg.bouncycastle.mail.smime;

import java.io.IOException;
import java.io.InputStream;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimePart;
import myorg.bouncycastle.cms.CMSCompressedData;
import myorg.bouncycastle.cms.CMSException;

public class SMIMECompressed extends CMSCompressedData {

   MimePart message;


   public SMIMECompressed(MimeBodyPart var1) throws MessagingException, CMSException {
      InputStream var2 = getInputStream(var1);
      super(var2);
      this.message = var1;
   }

   public SMIMECompressed(MimeMessage var1) throws MessagingException, CMSException {
      InputStream var2 = getInputStream(var1);
      super(var2);
      this.message = var1;
   }

   private static InputStream getInputStream(Part var0) throws MessagingException {
      try {
         InputStream var1 = var0.getInputStream();
         return var1;
      } catch (IOException var4) {
         String var3 = "can\'t extract input stream: " + var4;
         throw new MessagingException(var3);
      }
   }

   public MimePart getCompressedContent() {
      return this.message;
   }
}
