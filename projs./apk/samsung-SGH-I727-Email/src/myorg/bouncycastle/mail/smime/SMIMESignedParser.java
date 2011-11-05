package myorg.bouncycastle.mail.smime;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSSignedDataParser;
import myorg.bouncycastle.cms.CMSTypedStream;
import myorg.bouncycastle.mail.smime.SMIMEException;
import myorg.bouncycastle.mail.smime.SMIMEUtil;
import myorg.bouncycastle.mail.smime.util.FileBackedMimeBodyPart;

public class SMIMESignedParser extends CMSSignedDataParser {

   MimeBodyPart content;
   Object message;


   static {
      MailcapCommandMap var0 = (MailcapCommandMap)CommandMap.getDefaultCommandMap();
      var0.addMailcap("application/pkcs7-signature;; x-java-content-handler=myorg.bouncycastle.mail.smime.handlers.pkcs7_signature");
      var0.addMailcap("application/pkcs7-mime;; x-java-content-handler=myorg.bouncycastle.mail.smime.handlers.pkcs7_mime");
      var0.addMailcap("application/x-pkcs7-signature;; x-java-content-handler=myorg.bouncycastle.mail.smime.handlers.x_pkcs7_signature");
      var0.addMailcap("application/x-pkcs7-mime;; x-java-content-handler=myorg.bouncycastle.mail.smime.handlers.x_pkcs7_mime");
      var0.addMailcap("multipart/signed;; x-java-content-handler=myorg.bouncycastle.mail.smime.handlers.multipart_signed");
      CommandMap.setDefaultCommandMap(var0);
   }

   public SMIMESignedParser(Part var1) throws MessagingException, CMSException, SMIMEException {
      InputStream var2 = getInputStream(var1);
      super(var2);
      this.message = var1;
      CMSTypedStream var3 = this.getSignedContent();
      if(var3 != null) {
         FileBackedMimeBodyPart var4 = SMIMEUtil.toWriteOnceBodyPart(var3);
         this.content = var4;
      }
   }

   public SMIMESignedParser(Part var1, File var2) throws MessagingException, CMSException, SMIMEException {
      InputStream var3 = getInputStream(var1);
      super(var3);
      this.message = var1;
      CMSTypedStream var4 = this.getSignedContent();
      if(var4 != null) {
         FileBackedMimeBodyPart var5 = SMIMEUtil.toMimeBodyPart(var4, var2);
         this.content = var5;
      }
   }

   public SMIMESignedParser(MimeMultipart var1) throws MessagingException, CMSException {
      File var2 = getTmpFile();
      this(var1, var2);
   }

   public SMIMESignedParser(MimeMultipart var1, File var2) throws MessagingException, CMSException {
      this(var1, "7bit", var2);
   }

   public SMIMESignedParser(MimeMultipart var1, String var2) throws MessagingException, CMSException {
      File var3 = getTmpFile();
      this(var1, var2, var3);
   }

   public SMIMESignedParser(MimeMultipart var1, String var2, File var3) throws MessagingException, CMSException {
      CMSTypedStream var4 = getSignedInputStream(var1.getBodyPart(0), var2, var3);
      InputStream var5 = getInputStream(var1.getBodyPart(1));
      super(var4, var5);
      this.message = var1;
      MimeBodyPart var6 = (MimeBodyPart)var1.getBodyPart(0);
      this.content = var6;
      this.drainContent();
   }

   private void drainContent() throws CMSException {
      try {
         CMSTypedStream var1 = this.getSignedContent();
         if(var1 != null) {
            var1.drain();
         }
      } catch (IOException var4) {
         String var3 = "unable to read content for verification: " + var4;
         throw new CMSException(var3, var4);
      }
   }

   private static InputStream getInputStream(Part var0) throws MessagingException {
      try {
         if(var0.isMimeType("multipart/signed")) {
            throw new MessagingException("attempt to create signed data object from multipart content - use MimeMultipart constructor.");
         } else {
            InputStream var3 = var0.getInputStream();
            return var3;
         }
      } catch (IOException var4) {
         String var2 = "can\'t extract input stream: " + var4;
         throw new MessagingException(var2);
      }
   }

   private static CMSTypedStream getSignedInputStream(BodyPart var0, String var1, File var2) throws MessagingException {
      try {
         FileOutputStream var3 = new FileOutputStream(var2);
         BufferedOutputStream var4 = new BufferedOutputStream(var3);
         SMIMEUtil.outputBodyPart(var4, var0, var1);
         var4.close();
         SMIMESignedParser.TemporaryFileInputStream var5 = new SMIMESignedParser.TemporaryFileInputStream(var2);
         CMSTypedStream var6 = new CMSTypedStream(var5);
         return var6;
      } catch (IOException var9) {
         String var8 = "can\'t extract input stream: " + var9;
         throw new MessagingException(var8);
      }
   }

   private static File getTmpFile() throws MessagingException {
      try {
         File var0 = File.createTempFile("bcMail", ".mime");
         return var0;
      } catch (IOException var3) {
         String var2 = "can\'t extract input stream: " + var3;
         throw new MessagingException(var2);
      }
   }

   public MimeBodyPart getContent() {
      return this.content;
   }

   public MimeMessage getContentAsMimeMessage(Session var1) throws MessagingException, IOException {
      MimeMessage var3;
      if(this.message instanceof MimeMultipart) {
         InputStream var2 = ((MimeMultipart)this.message).getBodyPart(0).getInputStream();
         var3 = new MimeMessage(var1, var2);
      } else {
         CMSTypedStream var4 = this.getSignedContent();
         var3 = new MimeMessage;
         InputStream var5;
         if(var4 == null) {
            var5 = null;
         } else {
            var5 = var4.getContentStream();
         }

         var3.<init>(var1, var5);
      }

      return var3;
   }

   public Object getContentWithSignature() {
      return this.message;
   }

   private static class TemporaryFileInputStream extends BufferedInputStream {

      private final File _file;


      TemporaryFileInputStream(File var1) throws FileNotFoundException {
         FileInputStream var2 = new FileInputStream(var1);
         super(var2);
         this._file = var1;
      }

      public void close() throws IOException {
         super.close();
         boolean var1 = this._file.delete();
      }
   }
}
