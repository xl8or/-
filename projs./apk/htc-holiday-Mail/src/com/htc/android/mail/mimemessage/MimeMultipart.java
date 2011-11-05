package com.htc.android.mail.mimemessage;

import com.htc.android.mail.mimemessage.BodyPart;
import com.htc.android.mail.mimemessage.MessagingException;
import com.htc.android.mail.mimemessage.MimeUtility;
import com.htc.android.mail.mimemessage.Multipart;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

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

   public String generateBoundary() {
      StringBuffer var1 = new StringBuffer();
      StringBuffer var2 = var1.append("----");

      for(int var3 = 0; var3 < 30; ++var3) {
         String var4 = Integer.toString((int)(Math.random() * 35.0D), 36);
         var1.append(var4);
      }

      return var1.toString().toUpperCase();
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

   public void writeTo(OutputStream var1) throws IOException, MessagingException {
      OutputStreamWriter var2 = new OutputStreamWriter(var1);
      BufferedWriter var3 = new BufferedWriter(var2, 1024);
      if(this.mPreamble != null) {
         StringBuilder var4 = new StringBuilder();
         String var5 = this.mPreamble;
         String var6 = var4.append(var5).append("\r\n").toString();
         var3.write(var6);
      }

      int var7 = 0;

      for(int var8 = this.mParts.size(); var7 < var8; ++var7) {
         BodyPart var9 = (BodyPart)this.mParts.get(var7);
         StringBuilder var10 = (new StringBuilder()).append("--");
         String var11 = this.mBoundary;
         String var12 = var10.append(var11).append("\r\n").toString();
         var3.write(var12);
         var3.flush();
         var9.writeTo(var1);
         var3.write("\r\n");
      }

      StringBuilder var13 = (new StringBuilder()).append("--");
      String var14 = this.mBoundary;
      String var15 = var13.append(var14).append("--\r\n").toString();
      var3.write(var15);
      var3.flush();
   }
}
