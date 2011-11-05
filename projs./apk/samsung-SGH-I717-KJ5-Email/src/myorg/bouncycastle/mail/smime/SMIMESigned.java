package myorg.bouncycastle.mail.smime;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import javax.mail.internet.MimePart;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSProcessable;
import myorg.bouncycastle.cms.CMSSignedData;
import myorg.bouncycastle.mail.smime.CMSProcessableBodyPartInbound;
import myorg.bouncycastle.mail.smime.SMIMEException;
import myorg.bouncycastle.mail.smime.SMIMEUtil;

public class SMIMESigned extends CMSSignedData {

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

   public SMIMESigned(Part var1) throws MessagingException, CMSException, SMIMEException {
      InputStream var2 = getInputStream(var1);
      super(var2);
      this.message = var1;
      CMSProcessable var3 = this.getSignedContent();
      if(var3 != null) {
         MimeBodyPart var4 = SMIMEUtil.toMimeBodyPart((byte[])((byte[])var3.getContent()));
         this.content = var4;
      }
   }

   public SMIMESigned(MimeMultipart var1) throws MessagingException, CMSException {
      BodyPart var2 = var1.getBodyPart(0);
      CMSProcessableBodyPartInbound var3 = new CMSProcessableBodyPartInbound(var2);
      InputStream var4 = getInputStream(var1.getBodyPart(1));
      super((CMSProcessable)var3, var4);
      this.message = var1;
      MimeBodyPart var5 = (MimeBodyPart)var1.getBodyPart(0);
      this.content = var5;
   }

   public SMIMESigned(MimeMultipart var1, String var2) throws MessagingException, CMSException {
      BodyPart var3 = var1.getBodyPart(0);
      CMSProcessableBodyPartInbound var4 = new CMSProcessableBodyPartInbound(var3, var2);
      InputStream var5 = getInputStream(var1.getBodyPart(1));
      super((CMSProcessable)var4, var5);
      this.message = var1;
      MimeBodyPart var6 = (MimeBodyPart)var1.getBodyPart(0);
      this.content = var6;
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

   public MimeBodyPart getContent() {
      return this.content;
   }

   public MimeMessage getContentAsMimeMessage(Session var1) throws MessagingException, IOException {
      Object var2 = this.getSignedContent().getContent();
      byte[] var3;
      if(var2 instanceof byte[]) {
         var3 = (byte[])((byte[])var2);
      } else {
         if(!(var2 instanceof MimePart)) {
            String var9 = "<null>";
            if(var2 != null) {
               var9 = var2.getClass().getName();
            }

            String var10 = "Could not transfrom content of type " + var9 + " into MimeMessage.";
            throw new MessagingException(var10);
         }

         MimePart var6 = (MimePart)var2;
         ByteArrayOutputStream var8;
         if(var6.getSize() > 0) {
            int var7 = var6.getSize();
            var8 = new ByteArrayOutputStream(var7);
         } else {
            var8 = new ByteArrayOutputStream();
         }

         var6.writeTo(var8);
         var3 = var8.toByteArray();
      }

      MimeMessage var5;
      if(var3 != null) {
         ByteArrayInputStream var4 = new ByteArrayInputStream(var3);
         var5 = new MimeMessage(var1, var4);
      } else {
         var5 = null;
      }

      return var5;
   }

   public Object getContentWithSignature() {
      return this.message;
   }
}
