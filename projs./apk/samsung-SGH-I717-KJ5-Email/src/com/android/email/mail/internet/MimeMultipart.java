package com.android.email.mail.internet;

import android.content.Context;
import com.android.email.mail.BodyPart;
import com.android.email.mail.MessagingException;
import com.android.email.mail.Multipart;
import com.android.email.mail.internet.MimeUtility;
import com.android.email.provider.EmailContent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class MimeMultipart extends Multipart {

   protected String mBoundary;
   protected String mContentType;
   protected String mPreamble;
   protected String mSubType;


   public MimeMultipart() throws MessagingException {
      String var1 = this.generateBoundary();
      this.mBoundary = var1;
      this.setSubType("mixed");
   }

   public MimeMultipart(String var1) throws MessagingException {
      this.mContentType = var1;

      try {
         String var2 = MimeUtility.getHeaderParameter(var1, (String)null).split("/")[1];
         this.mSubType = var2;
         String var3 = MimeUtility.getHeaderParameter(var1, "boundary");
         this.mBoundary = var3;
         if(this.mBoundary == null) {
            String var4 = "MultiPart does not contain boundary: " + var1;
            throw new MessagingException(var4);
         }
      } catch (Exception var7) {
         String var6 = "Invalid MultiPart Content-Type; must contain subtype and boundary. (" + var1 + ")";
         throw new MessagingException(var6, var7);
      }
   }

   private String generateBoundary() {
      StringBuffer var1 = new StringBuffer();
      StringBuffer var2 = var1.append("----");

      for(int var3 = 0; var3 < 30; ++var3) {
         String var4 = Integer.toString((int)(Math.random() * 35.0D), 36);
         var1.append(var4);
      }

      return var1.toString().toUpperCase();
   }

   private static void writeHeader(Writer var0, String var1, String var2) throws IOException {
      if(var2 != null) {
         if(var2.length() > 0) {
            var0.append(var1);
            Writer var4 = var0.append(": ");
            var0.append(var2);
            Writer var6 = var0.append("\r\n");
         }
      }
   }

   private void writeOneAttachment(Context param1, Writer param2, OutputStream param3, EmailContent.Attachment param4) throws IOException, MessagingException {
      // $FF: Couldn't be decompiled
   }

   public String getContentType() throws MessagingException {
      return this.mContentType;
   }

   public InputStream getInputStream() throws MessagingException {
      return null;
   }

   public String getPreamble() throws MessagingException {
      return this.mPreamble;
   }

   public void setPreamble(String var1) throws MessagingException {
      this.mPreamble = var1;
   }

   public void setSubType(String var1) throws MessagingException {
      this.mSubType = var1;
      Object[] var2 = new Object[]{var1, null};
      String var3 = this.mBoundary;
      var2[1] = var3;
      String var4 = String.format("multipart/%s; boundary=\"%s\"", var2);
      this.mContentType = var4;
   }

   public void writeTo(Context param1, long param2, OutputStream param4) throws IOException, MessagingException {
      // $FF: Couldn't be decompiled
   }

   public void writeTo(OutputStream var1) throws IOException, MessagingException {
      OutputStreamWriter var2 = new OutputStreamWriter(var1);
      BufferedWriter var3 = new BufferedWriter(var2, 1024);
      StringBuffer var4 = new StringBuffer();
      if(this.mPreamble != null) {
         StringBuilder var5 = new StringBuilder();
         String var6 = this.mPreamble;
         String var7 = var5.append(var6).append("\r\n").toString();
         var3.write(var7);
      }

      int var8 = 0;

      for(int var9 = this.mParts.size(); var8 < var9; ++var8) {
         BodyPart var10 = (BodyPart)this.mParts.get(var8);
         StringBuffer var11 = var4.append("--");
         String var12 = this.mBoundary;
         StringBuffer var13 = var11.append(var12).append("\r\n");
         String var14 = var4.toString();
         var3.write(var14);
         var3.flush();
         var10.writeTo(var1);
         var3.write("\r\n");
         int var15 = var4.length();
         var4.delete(0, var15);
      }

      StringBuilder var17 = (new StringBuilder()).append("--");
      String var18 = this.mBoundary;
      String var19 = var17.append(var18).append("--\r\n").toString();
      var3.write(var19);
      var3.flush();
   }
}
