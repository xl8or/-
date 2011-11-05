package com.android.email.mail.transport;

import android.content.Context;
import android.util.Base64;
import com.android.email.Email;
import com.android.email.mail.Address;
import com.android.email.mail.AuthenticationFailedException;
import com.android.email.mail.MessagingException;
import com.android.email.mail.Sender;
import com.android.email.mail.Transport;
import com.android.email.mail.transport.EOLConvertingOutputStream;
import com.android.email.mail.transport.MailTransport;
import com.android.email.mail.transport.Rfc822Output;
import com.android.email.provider.EmailContent;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class SmtpSender extends Sender {

   private static final String TAG = "SmtpSender >>";
   Context mContext;
   String mPassword;
   private Transport mTransport;
   String mUsername;


   private SmtpSender(Context var1, String var2) throws MessagingException {
      this.mContext = var1;

      URI var3;
      try {
         var3 = new URI(var2);
      } catch (URISyntaxException var13) {
         throw new MessagingException("Invalid SmtpTransport URI", var13);
      }

      String var4 = var3.getScheme();
      if(var4 != null && var4.startsWith("smtp")) {
         byte var6 = 0;
         short var7 = 587;
         if(var4.contains("+ssl")) {
            var6 = 1;
            var7 = 465;
         } else if(var4.contains("+tls")) {
            var6 = 2;
         }

         boolean var8 = var4.contains("+trustallcerts");
         MailTransport var9 = new MailTransport("SMTP");
         this.mTransport = var9;
         this.mTransport.setUri(var3, var7);
         this.mTransport.setSecurity(var6, var8);
         String[] var10 = this.mTransport.getUserInfoParts();
         if(var10 != null) {
            String var11 = var10[0];
            this.mUsername = var11;
            if(var10.length > 1) {
               String var12 = var10[1];
               this.mPassword = var12;
            }
         }
      } else {
         throw new MessagingException("Unsupported protocol");
      }
   }

   private String executeSensitiveCommand(String var1, String var2) throws IOException, MessagingException {
      if(var1 != null) {
         this.mTransport.writeLine(var1, var2);
      }

      String var3 = this.mTransport.readLine();
      String var4 = var3;
      if(var3 != null) {
         while(var3.length() >= 4 && var3.charAt(3) == 45) {
            var3 = this.mTransport.readLine();
            StringBuilder var5 = (new StringBuilder()).append(var4);
            String var6 = var3.substring(3);
            var4 = var5.append(var6).toString();
         }

         if(var4.length() > 0) {
            char var7 = var4.charAt(0);
            if(var7 == 52 || var7 == 53) {
               throw new MessagingException(var4);
            }
         }
      }

      return var4;
   }

   private String executeSimpleCommand(String var1) throws IOException, MessagingException {
      return this.executeSensitiveCommand(var1, (String)null);
   }

   public static Sender newInstance(Context var0, String var1) throws MessagingException {
      return new SmtpSender(var0, var1);
   }

   private void saslAuthLogin(String var1, String var2) throws MessagingException, AuthenticationFailedException, IOException {
      try {
         String var3 = this.executeSimpleCommand("AUTH LOGIN");
         String var4 = Base64.encodeToString(var1.getBytes(), 2);
         this.executeSensitiveCommand(var4, "/username redacted/");
         String var6 = Base64.encodeToString(var2.getBytes(), 2);
         this.executeSensitiveCommand(var6, "/password redacted/");
      } catch (MessagingException var10) {
         if(var10.getMessage().length() > 1 && var10.getMessage().charAt(1) == 51) {
            String var9 = var10.getMessage();
            throw new AuthenticationFailedException(var9);
         } else {
            throw var10;
         }
      }
   }

   private void saslAuthPlain(String var1, String var2) throws MessagingException, AuthenticationFailedException, IOException {
      byte[] var3 = Base64.encode(("��" + var1 + "��" + var2).getBytes(), 2);

      try {
         StringBuilder var4 = (new StringBuilder()).append("AUTH PLAIN ");
         String var5 = new String(var3);
         String var6 = var4.append(var5).toString();
         this.executeSensitiveCommand(var6, "AUTH PLAIN /redacted/");
      } catch (MessagingException var10) {
         if(var10.getMessage().length() > 1 && var10.getMessage().charAt(1) == 51) {
            String var9 = var10.getMessage();
            throw new AuthenticationFailedException(var9);
         } else {
            throw var10;
         }
      }
   }

   public void close() {
      this.mTransport.close();
   }

   public void open() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public void sendMessage(long var1) throws MessagingException {
      Email.logd("EMAIL_PERFORMANCE", "sendMessage() BEFORE CLOSE");
      this.close();
      Email.logd("EMAIL_PERFORMANCE", "sendMessage() AFTER CLOSE AND OPEN");
      this.open();
      Email.logd("EMAIL_PERFORMANCE", "sendMessage() AFTER OPEN");
      Context var3 = this.mContext;
      EmailContent.Message var6 = EmailContent.Message.restoreMessageWithId(var3, var1);
      if(var6 == null) {
         StringBuilder var7 = (new StringBuilder()).append("Trying to send non-existent message id=");
         String var8 = Long.toString(var1);
         String var9 = var7.append(var8).toString();
         throw new MessagingException(var9);
      } else {
         Address var10 = Address.unpackFirst(var6.mFrom);
         Address[] var11 = Address.unpack(var6.mTo);
         Address[] var12 = Address.unpack(var6.mCc);
         Address[] var13 = Address.unpack(var6.mBcc);
         StringBuffer var14 = new StringBuffer();
         StringBuffer var15 = new StringBuffer();
         StringBuffer var16 = new StringBuffer();

         try {
            Email.logd("EMAIL_PERFORMANCE", "sendMessage() BEFORE executeSimpleCommand");
            if(var10 != null) {
               StringBuilder var17 = (new StringBuilder()).append("MAIL FROM: <");
               String var18 = var10.getAddress();
               String var19 = var17.append(var18).append(">").toString();
               String var22 = this.executeSimpleCommand(var19);
            }

            Address[] var23 = var11;
            int var24 = var11.length;

            int var25;
            for(var25 = 0; var25 < var24; ++var25) {
               Address var28 = var23[var25];
               String var30 = "RCPT TO: ";
               StringBuffer var31 = var14.append(var30).append('<');
               String var32 = var28.getAddress();
               StringBuffer var33 = var31.append(var32).append('>');
               String var34 = var14.toString();
               String var37 = this.executeSimpleCommand(var34);
               int var38 = var14.length();
               byte var40 = 0;
               var14.delete(var40, var38);
            }

            var23 = var12;
            var24 = var12.length;

            for(var25 = 0; var25 < var24; ++var25) {
               Address var45 = var23[var25];
               StringBuffer var46 = var15.append("RCPT TO: ").append('<');
               String var47 = var45.getAddress();
               StringBuffer var48 = var46.append(var47).append('>');
               String var49 = var15.toString();
               String var52 = this.executeSimpleCommand(var49);
               int var53 = var15.length();
               var15.delete(0, var53);
            }

            var23 = var13;
            var24 = var13.length;

            for(var25 = 0; var25 < var24; ++var25) {
               Address var57 = var23[var25];
               StringBuffer var58 = var16.append("RCPT TO: ").append('<');
               String var59 = var57.getAddress();
               StringBuffer var60 = var58.append(var59).append('>');
               String var61 = var16.toString();
               String var64 = this.executeSimpleCommand(var61);
               int var65 = var16.length();
               var16.delete(0, var65);
            }

            String var68 = "DATA";
            this.executeSimpleCommand(var68);
            Email.logd("EMAIL_PERFORMANCE", "sendMessage() AFTER executeSimpleCommand DATA");
            Rfc822Output.setIsExchangeAccount((boolean)0);
            Context var70 = this.mContext;
            OutputStream var71 = this.mTransport.getOutputStream();
            EOLConvertingOutputStream var72 = new EOLConvertingOutputStream(var71);
            Rfc822Output.writeTo(var70, var1, var72, (boolean)1, (boolean)0);
            Email.logd("EMAIL_PERFORMANCE", "sendMessage() AFTER Rfc822Output.writeTo");
            String var76 = "\r\n.";
            this.executeSimpleCommand(var76);
            StringBuilder var78 = (new StringBuilder()).append("open :: smtp Send OK !!!! ");
            String var80 = var78.append(var6).toString();
            Email.logs("SmtpSender >>", var80);
            Email.logd("EMAIL_PERFORMANCE", "sendMessage() END in SMTP Sender");
         } catch (IOException var86) {
            MessagingException var82 = new MessagingException;
            String var84 = "Unable to send message";
            var82.<init>(var84, var86);
            throw var82;
         }
      }
   }

   void setTransport(Transport var1) {
      this.mTransport = var1;
   }
}
