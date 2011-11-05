package com.htc.android.mail.mimemessage;

import com.htc.android.mail.mimemessage.MessagingException;
import com.htc.android.mail.mimemessage.Utility;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class MimeHeader {

   public static final String HEADER_ANDROID_ATTACHMENT_STORE_DATA = "X-Android-Attachment-StoreData";
   public static final String HEADER_ANDROID_BODY_QUOTED_PART = "X-Android-Body-Quoted-Part";
   public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
   public static final String HEADER_CONTENT_ID = "Content-ID";
   public static final String HEADER_CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
   public static final String HEADER_CONTENT_TYPE = "Content-Type";
   private static final String[] writeOmitFields;
   protected ArrayList<MimeHeader.Field> mFields;


   static {
      String[] var0 = new String[]{"X-Android-Attachment-StoreData"};
      writeOmitFields = var0;
   }

   public MimeHeader() {
      ArrayList var1 = new ArrayList();
      this.mFields = var1;
   }

   public void addHeader(String var1, String var2) throws MessagingException {
      ArrayList var3 = this.mFields;
      MimeHeader.Field var4 = new MimeHeader.Field(var1, var2);
      var3.add(var4);
   }

   public void clear() {
      this.mFields.clear();
   }

   public String getFirstHeader(String var1) throws MessagingException {
      String[] var2 = this.getHeader(var1);
      String var3;
      if(var2 == null) {
         var3 = null;
      } else {
         var3 = var2[0];
      }

      return var3;
   }

   public String[] getHeader(String var1) throws MessagingException {
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.mFields.iterator();

      while(var3.hasNext()) {
         MimeHeader.Field var4 = (MimeHeader.Field)var3.next();
         if(var4.name.equalsIgnoreCase(var1)) {
            String var5 = var4.value;
            var2.add(var5);
         }
      }

      String[] var7;
      if(var2.size() == 0) {
         var7 = null;
      } else {
         String[] var8 = new String[0];
         var7 = (String[])var2.toArray(var8);
      }

      return var7;
   }

   public void removeHeader(String var1) throws MessagingException {
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.mFields.iterator();

      while(var3.hasNext()) {
         MimeHeader.Field var4 = (MimeHeader.Field)var3.next();
         if(var4.name.equalsIgnoreCase(var1)) {
            var2.add(var4);
         }
      }

      this.mFields.removeAll(var2);
   }

   public void setHeader(String var1, String var2) throws MessagingException {
      if(var1 != null) {
         if(var2 != null) {
            this.removeHeader(var1);
            this.addHeader(var1, var2);
         }
      }
   }

   public String toString() {
      String var1;
      if(this.mFields == null) {
         var1 = null;
      } else {
         var1 = this.mFields.toString();
      }

      return var1;
   }

   public void writeTo(OutputStream var1) throws IOException, MessagingException {
      OutputStreamWriter var2 = new OutputStreamWriter(var1);
      BufferedWriter var3 = new BufferedWriter(var2, 1024);
      Iterator var4 = this.mFields.iterator();

      while(var4.hasNext()) {
         MimeHeader.Field var5 = (MimeHeader.Field)var4.next();
         String[] var6 = writeOmitFields;
         String var7 = var5.name;
         if(!Utility.arrayContains(var6, var7)) {
            StringBuilder var8 = new StringBuilder();
            String var9 = var5.name;
            StringBuilder var10 = var8.append(var9).append(": ");
            String var11 = var5.value;
            String var12 = var10.append(var11).append("\r\n").toString();
            var3.write(var12);
         }
      }

      var3.flush();
   }

   public String writeToString() {
      String var1;
      if(this.mFields.size() == 0) {
         var1 = null;
      } else {
         StringBuilder var2 = new StringBuilder();
         Iterator var3 = this.mFields.iterator();

         while(var3.hasNext()) {
            MimeHeader.Field var4 = (MimeHeader.Field)var3.next();
            String[] var5 = writeOmitFields;
            String var6 = var4.name;
            if(!Utility.arrayContains(var5, var6)) {
               StringBuilder var7 = new StringBuilder();
               String var8 = var4.name;
               StringBuilder var9 = var7.append(var8).append(": ");
               String var10 = var4.value;
               String var11 = var9.append(var10).append("\r\n").toString();
               var2.append(var11);
            }
         }

         var1 = var2.toString();
      }

      return var1;
   }

   class Field {

      String name;
      String value;


      public Field(String var2, String var3) {
         this.name = var2;
         this.value = var3;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         String var2 = this.name;
         StringBuilder var3 = var1.append(var2).append("=");
         String var4 = this.value;
         return var3.append(var4).toString();
      }
   }
}
