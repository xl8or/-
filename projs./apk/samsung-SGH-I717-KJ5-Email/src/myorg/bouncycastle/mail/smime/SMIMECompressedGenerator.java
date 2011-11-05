package myorg.bouncycastle.mail.smime;

import java.io.IOException;
import java.io.OutputStream;
import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import myorg.bouncycastle.cms.CMSCompressedDataStreamGenerator;
import myorg.bouncycastle.mail.smime.SMIMEException;
import myorg.bouncycastle.mail.smime.SMIMEGenerator;
import myorg.bouncycastle.mail.smime.SMIMEStreamingProcessor;

public class SMIMECompressedGenerator extends SMIMEGenerator {

   private static final String COMPRESSED_CONTENT_TYPE = "application/pkcs7-mime; name=\"smime.p7z\"; smime-type=compressed-data";
   public static final String ZLIB = "1.2.840.113549.1.9.16.3.8";


   static {
      MailcapCommandMap var0 = (MailcapCommandMap)CommandMap.getDefaultCommandMap();
      var0.addMailcap("application/pkcs7-mime;; x-java-content-handler=myorg.bouncycastle.mail.smime.handlers.pkcs7_mime");
      var0.addMailcap("application/x-pkcs7-mime;; x-java-content-handler=myorg.bouncycastle.mail.smime.handlers.x_pkcs7_mime");
      CommandMap.setDefaultCommandMap(var0);
   }

   public SMIMECompressedGenerator() {}

   private MimeBodyPart make(MimeBodyPart var1, String var2) throws SMIMEException {
      try {
         MimeBodyPart var3 = new MimeBodyPart();
         SMIMECompressedGenerator.ContentCompressor var4 = new SMIMECompressedGenerator.ContentCompressor(var1, var2);
         var3.setContent(var4, "application/pkcs7-mime; name=\"smime.p7z\"; smime-type=compressed-data");
         var3.addHeader("Content-Type", "application/pkcs7-mime; name=\"smime.p7z\"; smime-type=compressed-data");
         var3.addHeader("Content-Disposition", "attachment; filename=\"smime.p7z\"");
         var3.addHeader("Content-Description", "S/MIME Compressed Message");
         String var5 = this.encoding;
         var3.addHeader("Content-Transfer-Encoding", var5);
         return var3;
      } catch (MessagingException var7) {
         throw new SMIMEException("exception putting multi-part together.", var7);
      }
   }

   public MimeBodyPart generate(MimeBodyPart var1, String var2) throws SMIMEException {
      MimeBodyPart var3 = this.makeContentBodyPart(var1);
      return this.make(var3, var2);
   }

   public MimeBodyPart generate(MimeMessage var1, String var2) throws SMIMEException {
      try {
         var1.saveChanges();
      } catch (MessagingException var5) {
         throw new SMIMEException("unable to save message", var5);
      }

      MimeBodyPart var3 = this.makeContentBodyPart(var1);
      return this.make(var3, var2);
   }

   private class ContentCompressor implements SMIMEStreamingProcessor {

      private final String _compressionOid;
      private final MimeBodyPart _content;


      ContentCompressor(MimeBodyPart var2, String var3) {
         this._content = var2;
         this._compressionOid = var3;
      }

      public void write(OutputStream var1) throws IOException {
         CMSCompressedDataStreamGenerator var2 = new CMSCompressedDataStreamGenerator();
         String var3 = this._compressionOid;
         OutputStream var4 = var2.open(var1, var3);

         try {
            this._content.writeTo(var4);
            var4.close();
         } catch (MessagingException var6) {
            String var5 = var6.toString();
            throw new IOException(var5);
         }
      }
   }
}
