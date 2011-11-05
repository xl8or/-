package myorg.bouncycastle.mail.smime;

import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.activation.DataHandler;
import javax.crypto.KeyGenerator;
import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import myorg.bouncycastle.cms.CMSEnvelopedGenerator;
import myorg.bouncycastle.mail.smime.SMIMEException;
import myorg.bouncycastle.util.Strings;

public class SMIMEGenerator {

   private static Map BASE_CIPHER_NAMES = new HashMap();
   protected String encoding = "base64";
   protected boolean useBase64 = 1;


   static {
      Map var0 = BASE_CIPHER_NAMES;
      String var1 = CMSEnvelopedGenerator.DES_EDE3_CBC;
      var0.put(var1, "DESEDE");
      Map var3 = BASE_CIPHER_NAMES;
      String var4 = CMSEnvelopedGenerator.AES128_CBC;
      var3.put(var4, "AES");
      Map var6 = BASE_CIPHER_NAMES;
      String var7 = CMSEnvelopedGenerator.AES192_CBC;
      var6.put(var7, "AES");
      Map var9 = BASE_CIPHER_NAMES;
      String var10 = CMSEnvelopedGenerator.AES256_CBC;
      var9.put(var10, "AES");
   }

   protected SMIMEGenerator() {}

   private KeyGenerator createKeyGenerator(String var1, Provider var2) throws NoSuchAlgorithmException {
      KeyGenerator var3;
      if(var2 != null) {
         var3 = KeyGenerator.getInstance(var1, var2);
      } else {
         var3 = KeyGenerator.getInstance(var1);
      }

      return var3;
   }

   private void extractHeaders(MimeBodyPart var1, MimeMessage var2) throws MessagingException {
      Enumeration var3 = var2.getAllHeaders();

      while(var3.hasMoreElements()) {
         Header var4 = (Header)var3.nextElement();
         String var5 = var4.getName();
         String var6 = var4.getValue();
         var1.setHeader(var5, var6);
      }

   }

   protected KeyGenerator createSymmetricKeyGenerator(String var1, Provider var2) throws NoSuchAlgorithmException {
      KeyGenerator var3;
      KeyGenerator var4;
      try {
         var3 = this.createKeyGenerator(var1, var2);
      } catch (NoSuchAlgorithmException var9) {
         label41: {
            try {
               String var6 = (String)BASE_CIPHER_NAMES.get(var1);
               if(var6 == null) {
                  break label41;
               }

               var3 = this.createKeyGenerator(var6, var2);
            } catch (NoSuchAlgorithmException var8) {
               break label41;
            }

            var4 = var3;
            return var4;
         }

         if(var2 == null) {
            throw var9;
         }

         var4 = this.createSymmetricKeyGenerator(var1, (Provider)null);
         return var4;
      }

      var4 = var3;
      return var4;
   }

   protected MimeBodyPart makeContentBodyPart(MimeBodyPart var1) throws SMIMEException {
      try {
         Session var2 = (Session)false;
         MimeMessage var3 = new MimeMessage(var2);
         Enumeration var4 = var1.getAllHeaders();
         DataHandler var5 = var1.getDataHandler();
         var3.setDataHandler(var5);

         while(var4.hasMoreElements()) {
            Header var6 = (Header)var4.nextElement();
            String var7 = var6.getName();
            String var8 = var6.getValue();
            var3.setHeader(var7, var8);
         }

         var3.saveChanges();
         var4 = var3.getAllHeaders();

         while(var4.hasMoreElements()) {
            Header var10 = (Header)var4.nextElement();
            if(Strings.toLowerCase(var10.getName()).startsWith("content-")) {
               String var11 = var10.getName();
               String var12 = var10.getValue();
               var1.setHeader(var11, var12);
            }
         }

         return var1;
      } catch (MessagingException var13) {
         throw new SMIMEException("exception saving message state.", var13);
      }
   }

   protected MimeBodyPart makeContentBodyPart(MimeMessage param1) throws SMIMEException {
      // $FF: Couldn't be decompiled
   }

   public void setContentTransferEncoding(String var1) {
      this.encoding = var1;
      boolean var2 = Strings.toLowerCase(var1).equals("base64");
      this.useBase64 = var2;
   }
}
