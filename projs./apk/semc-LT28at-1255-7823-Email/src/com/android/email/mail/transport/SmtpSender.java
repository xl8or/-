package com.android.email.mail.transport;

import android.content.Context;
import android.util.Base64;
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

   private final Context mContext;
   private String mPassword;
   private Transport mTransport;
   private String mUsername;


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
      String var4;
      if(var3 != null && var3.length() != 0) {
         String var5;
         StringBuilder var6;
         String var7;
         for(var5 = var3; var3.length() >= 4 && var3.charAt(3) == 45; var5 = var6.append(var7).toString()) {
            var3 = this.mTransport.readLine();
            var6 = (new StringBuilder()).append(var5);
            var7 = var3.substring(3);
         }

         if(var5.length() > 0) {
            char var8 = var5.charAt(0);
            if(var8 == 52 || var8 == 53) {
               throw new MessagingException(var5);
            }
         }

         var4 = var5;
      } else {
         var4 = "";
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
      if(var1 < 0L) {
         StringBuilder var3 = (new StringBuilder()).append("Invalid message Id=");
         String var6 = var3.append(var1).toString();
         throw new MessagingException(var6);
      } else {
         this.close();
         this.open();
         Context var7 = this.mContext;
         EmailContent.Message var10 = EmailContent.Message.restoreMessageWithId(var7, var1);
         if(var10 == null) {
            StringBuilder var11 = (new StringBuilder()).append("Trying to send non-existent message id=");
            String var12 = Long.toString(var1);
            String var13 = var11.append(var12).toString();
            throw new MessagingException(var13);
         } else {
            Address var14 = Address.unpackFirst(var10.mFrom);
            Address[] var15 = Address.unpack(var10.mTo);
            Address[] var16 = Address.unpack(var10.mCc);
            Address[] var17 = Address.unpack(var10.mBcc);

            try {
               StringBuilder var18 = (new StringBuilder()).append("MAIL FROM: <");
               String var19 = var14.getAddress();
               String var20 = var18.append(var19).append(">").toString();
               String var23 = this.executeSimpleCommand(var20);
               Address[] var24 = var15;
               int var25 = var15.length;

               int var26;
               for(var26 = 0; var26 < var25; ++var26) {
                  Address var29 = var24[var26];
                  StringBuilder var30 = (new StringBuilder()).append("RCPT TO: <");
                  String var31 = var29.getAddress();
                  String var32 = var30.append(var31).append(">").toString();
                  String var35 = this.executeSimpleCommand(var32);
               }

               var24 = var16;
               var25 = var16.length;

               for(var26 = 0; var26 < var25; ++var26) {
                  Address var38 = var24[var26];
                  StringBuilder var39 = (new StringBuilder()).append("RCPT TO: <");
                  String var40 = var38.getAddress();
                  String var41 = var39.append(var40).append(">").toString();
                  String var44 = this.executeSimpleCommand(var41);
               }

               var24 = var17;
               var25 = var17.length;

               for(var26 = 0; var26 < var25; ++var26) {
                  Address var47 = var24[var26];
                  StringBuilder var48 = (new StringBuilder()).append("RCPT TO: <");
                  String var49 = var47.getAddress();
                  String var50 = var48.append(var49).append(">").toString();
                  String var53 = this.executeSimpleCommand(var50);
               }

               String var55 = "DATA";
               this.executeSimpleCommand(var55);
               Context var57 = this.mContext;
               OutputStream var58 = this.mTransport.getOutputStream();
               EOLConvertingOutputStream var59 = new EOLConvertingOutputStream(var58);
               Rfc822Output.writeTo(var57, var1, var59, (boolean)0, (boolean)0, (boolean)1);
               String var63 = "\r\n.";
               this.executeSimpleCommand(var63);
            } catch (IOException var70) {
               this.close();
               MessagingException var66 = new MessagingException;
               String var68 = "Unable to send message";
               var66.<init>(var68, var70);
               throw var66;
            }
         }
      }
   }

   void setTransport(Transport var1) {
      this.mTransport = var1;
   }
}
