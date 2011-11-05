package com.htc.android.mail.mimemessage;

import com.htc.android.mail.mimemessage.Body;
import com.htc.android.mail.mimemessage.BodyPart;
import com.htc.android.mail.mimemessage.MessagingException;
import com.htc.android.mail.mimemessage.MimeHeader;
import com.htc.android.mail.mimemessage.MimeUtility;
import com.htc.android.mail.mimemessage.Multipart;
import com.htc.android.mail.mimemessage.TextBody;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.regex.Pattern;

public class MimeBodyPart extends BodyPart {

   private static final Pattern END_OF_LINE = Pattern.compile("\r?\n");
   private static final Pattern REMOVE_OPTIONAL_BRACKETS = Pattern.compile("^<?([^>]+)>?$");
   protected Body mBody;
   protected MimeHeader mExtendedHeader;
   protected MimeHeader mHeader;
   protected int mSize;


   public MimeBodyPart() throws MessagingException {
      this((Body)null);
   }

   public MimeBodyPart(Body var1) throws MessagingException {
      this(var1, (String)null);
   }

   public MimeBodyPart(Body var1, String var2) throws MessagingException {
      MimeHeader var3 = new MimeHeader();
      this.mHeader = var3;
      if(var2 != null) {
         this.setHeader("Content-Type", var2);
      }

      this.setBody(var1);
   }

   public void addHeader(String var1, String var2) throws MessagingException {
      this.mHeader.addHeader(var1, var2);
   }

   public Body getBody() throws MessagingException {
      return this.mBody;
   }

   public String getContentId() throws MessagingException {
      String var1 = this.getFirstHeader("Content-ID");
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = REMOVE_OPTIONAL_BRACKETS.matcher(var1).replaceAll("$1");
      }

      return var2;
   }

   public String getContentType() throws MessagingException {
      String var1 = this.getFirstHeader("Content-Type");
      String var2;
      if(var1 == null) {
         var2 = "text/plain";
      } else {
         var2 = var1;
      }

      return var2;
   }

   public String getDisposition() throws MessagingException {
      String var1 = this.getFirstHeader("Content-Disposition");
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1;
      }

      return var2;
   }

   public String getExtendedHeader(String var1) throws MessagingException {
      String var2;
      if(this.mExtendedHeader == null) {
         var2 = null;
      } else {
         var2 = this.mExtendedHeader.getFirstHeader(var1);
      }

      return var2;
   }

   protected String getFirstHeader(String var1) throws MessagingException {
      return this.mHeader.getFirstHeader(var1);
   }

   public String[] getHeader(String var1) throws MessagingException {
      return this.mHeader.getHeader(var1);
   }

   public String getMimeType() throws MessagingException {
      return MimeUtility.getHeaderParameter(this.getContentType(), (String)null);
   }

   public int getSize() throws MessagingException {
      return this.mSize;
   }

   public boolean isMimeType(String var1) throws MessagingException {
      return this.getMimeType().equals(var1);
   }

   public void removeHeader(String var1) throws MessagingException {
      this.mHeader.removeHeader(var1);
   }

   public void setBody(Body var1) throws MessagingException {
      this.mBody = var1;
      if(var1 instanceof Multipart) {
         Multipart var2 = (Multipart)var1;
         var2.setParent(this);
         String var3 = var2.getContentType();
         this.setHeader("Content-Type", var3);
      } else if(var1 instanceof TextBody) {
         Object[] var4 = new Object[1];
         String var5 = this.getMimeType();
         var4[0] = var5;
         String var6 = String.format("%s;\n charset=utf-8", var4);
         String var7 = MimeUtility.getHeaderParameter(this.getContentType(), "name");
         if(var7 != null) {
            StringBuilder var8 = (new StringBuilder()).append(var6);
            Object[] var9 = new Object[]{var7};
            String var10 = String.format(";\n name=\"%s\"", var9);
            var6 = var8.append(var10).toString();
         }

         this.setHeader("Content-Type", var6);
         this.setHeader("Content-Transfer-Encoding", "base64");
      }
   }

   public void setExtendedHeader(String var1, String var2) throws MessagingException {
      if(var2 == null) {
         if(this.mExtendedHeader != null) {
            this.mExtendedHeader.removeHeader(var1);
         }
      } else {
         if(this.mExtendedHeader == null) {
            MimeHeader var3 = new MimeHeader();
            this.mExtendedHeader = var3;
         }

         MimeHeader var4 = this.mExtendedHeader;
         String var5 = END_OF_LINE.matcher(var2).replaceAll("");
         var4.setHeader(var1, var5);
      }
   }

   public void setHeader(String var1, String var2) throws MessagingException {
      this.mHeader.setHeader(var1, var2);
   }

   public void setSize(int var1) {
      this.mSize = var1;
   }

   public void writeTo(OutputStream var1) throws IOException, MessagingException {
      OutputStreamWriter var2 = new OutputStreamWriter(var1);
      BufferedWriter var3 = new BufferedWriter(var2, 1024);
      this.mHeader.writeTo(var1);
      var3.write("\r\n");
      var3.flush();
      if(this.mBody != null) {
         this.mBody.writeTo(var1);
      }
   }
}
