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
   long mServerLimit = 52428800L;
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
         Context var10 = this.mContext;
         EmailContent.Attachment[] var13 = EmailContent.Attachment.restoreAttachmentsWithMessageId(var10, var1);
         if(var13 != null) {
            int var14 = 0;
            int var15 = 0;

            while(true) {
               int var16 = var13.length;
               if(var15 >= var16) {
                  long var23 = (long)(var14 * 4 / 3);
                  StringBuilder var25 = (new StringBuilder()).append("allAttachSize :").append(var14).append(" mServerLimit :");
                  long var26 = this.mServerLimit;
                  String var28 = var25.append(var26).toString();
                  Email.logd("Email.LOG_TAG", var28);
                  long var29 = this.mServerLimit;
                  if(var23 >= var29) {
                     throw new MessagingException("Unable to attach");
                  }
                  break;
               }

               long var19 = (long)var14;
               long var21 = var13[var15].mSize;
               var14 = (int)(var19 + var21);
               ++var15;
            }
         }

         Address var31 = Address.unpackFirst(var6.mFrom);
         Address[] var32 = Address.unpack(var6.mTo);
         Address[] var33 = Address.unpack(var6.mCc);
         Address[] var34 = Address.unpack(var6.mBcc);
         StringBuffer var35 = new StringBuffer();
         StringBuffer var36 = new StringBuffer();
         StringBuffer var37 = new StringBuffer();

         try {
            Email.logd("EMAIL_PERFORMANCE", "sendMessage() BEFORE executeSimpleCommand");
            if(var31 != null) {
               StringBuilder var38 = (new StringBuilder()).append("MAIL FROM: <");
               String var39 = var31.getAddress();
               String var40 = var38.append(var39).append(">").toString();
               String var43 = this.executeSimpleCommand(var40);
            }

            Address[] var44 = var32;
            int var45 = var32.length;

            int var46;
            for(var46 = 0; var46 < var45; ++var46) {
               Address var49 = var44[var46];
               String var51 = "RCPT TO: ";
               StringBuffer var52 = var35.append(var51).append('<');
               String var53 = var49.getAddress();
               StringBuffer var54 = var52.append(var53).append('>');
               String var55 = var35.toString();
               String var58 = this.executeSimpleCommand(var55);
               int var59 = var35.length();
               byte var61 = 0;
               var35.delete(var61, var59);
            }

            var44 = var33;
            var45 = var33.length;

            for(var46 = 0; var46 < var45; ++var46) {
               Address var66 = var44[var46];
               String var68 = "RCPT TO: ";
               StringBuffer var69 = var36.append(var68).append('<');
               String var70 = var66.getAddress();
               StringBuffer var71 = var69.append(var70).append('>');
               String var72 = var36.toString();
               String var75 = this.executeSimpleCommand(var72);
               int var76 = var36.length();
               byte var78 = 0;
               var36.delete(var78, var76);
            }

            var44 = var34;
            var45 = var34.length;

            for(var46 = 0; var46 < var45; ++var46) {
               Address var83 = var44[var46];
               StringBuffer var84 = var37.append("RCPT TO: ").append('<');
               String var85 = var83.getAddress();
               StringBuffer var86 = var84.append(var85).append('>');
               String var87 = var37.toString();
               String var90 = this.executeSimpleCommand(var87);
               int var91 = var37.length();
               var37.delete(0, var91);
            }

            String var94 = "DATA";
            this.executeSimpleCommand(var94);
            Email.logd("EMAIL_PERFORMANCE", "sendMessage() AFTER executeSimpleCommand DATA");
            Rfc822Output.setIsExchangeAccount((boolean)0);
            Context var96 = this.mContext;
            OutputStream var97 = this.mTransport.getOutputStream();
            EOLConvertingOutputStream var98 = new EOLConvertingOutputStream(var97);
            Rfc822Output.writeTo(var96, var1, var98, (boolean)1, (boolean)0);
            Email.logd("EMAIL_PERFORMANCE", "sendMessage() AFTER Rfc822Output.writeTo");
            String var102 = "\r\n.";
            this.executeSimpleCommand(var102);
            StringBuilder var104 = (new StringBuilder()).append("open :: smtp Send OK !!!! ");
            String var106 = var104.append(var6).toString();
            Email.logs("SmtpSender >>", var106);
            Email.logd("EMAIL_PERFORMANCE", "sendMessage() END in SMTP Sender");
         } catch (MessagingException var130) {
            if(var130 != null && var130.getMessage() != null && var130.getMessage().contains("552") == 1) {
               MessagingException var108 = new MessagingException;
               String var110 = "Unable to attach";
               var108.<init>(var110, var130);
               throw var108;
            } else {
               MessagingException var112 = new MessagingException;
               String var114 = "Unable to send message";
               var112.<init>(var114, var130);
               throw var112;
            }
         } catch (Exception var131) {
            String var117 = null;

            label58: {
               String var118;
               try {
                  var118 = this.mTransport.readLine();
               } catch (IOException var129) {
                  var129.printStackTrace();
                  break label58;
               }

               var117 = var118;
            }

            if(var117 != null) {
               String var120 = "552";
               if(var117.contains(var120) == 1) {
                  MessagingException var121 = new MessagingException;
                  String var123 = "Unable to attach";
                  var121.<init>(var123, var131);
                  throw var121;
               }
            }

            MessagingException var125 = new MessagingException;
            String var127 = "Unable to send message";
            var125.<init>(var127, var131);
            throw var125;
         }
      }
   }

   void setTransport(Transport var1) {
      this.mTransport = var1;
   }
}
