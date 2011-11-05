package myorg.bouncycastle.mail.smime;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimePart;
import myorg.bouncycastle.cms.CMSCompressedDataParser;
import myorg.bouncycastle.cms.CMSException;

public class SMIMECompressedParser extends CMSCompressedDataParser {

   private final MimePart message;


   public SMIMECompressedParser(MimeBodyPart var1) throws MessagingException, CMSException {
      this(var1, 0);
   }

   public SMIMECompressedParser(MimeBodyPart var1, int var2) throws MessagingException, CMSException {
      InputStream var3 = getInputStream(var1, var2);
      super(var3);
      this.message = var1;
   }

   public SMIMECompressedParser(MimeMessage var1) throws MessagingException, CMSException {
      this(var1, 0);
   }

   public SMIMECompressedParser(MimeMessage var1, int var2) throws MessagingException, CMSException {
      InputStream var3 = getInputStream(var1, var2);
      super(var3);
      this.message = var1;
   }

   private static InputStream getInputStream(Part var0, int var1) throws MessagingException {
      try {
         InputStream var2 = var0.getInputStream();
         BufferedInputStream var3;
         if(var1 == 0) {
            var3 = new BufferedInputStream(var2);
         } else {
            var3 = new BufferedInputStream(var2, var1);
         }

         return var3;
      } catch (IOException var6) {
         String var5 = "can\'t extract input stream: " + var6;
         throw new MessagingException(var5);
      }
   }

   public MimePart getCompressedContent() {
      return this.message;
   }
}
