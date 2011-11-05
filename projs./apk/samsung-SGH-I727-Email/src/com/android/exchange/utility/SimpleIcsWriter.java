package com.android.exchange.utility;

import android.text.TextUtils;
import com.android.email.Utility;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SimpleIcsWriter {

   private static final int CHAR_MAX_BYTES_IN_UTF8 = 4;
   private static final int MAX_LINE_LENGTH = 75;
   private final ByteArrayOutputStream mOut;


   public SimpleIcsWriter() {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      this.mOut = var1;
   }

   static String escapeTextValue(String var0) {
      int var1 = var0.length();
      StringBuilder var2 = new StringBuilder(var1);
      int var3 = 0;

      while(true) {
         int var4 = var0.length();
         if(var3 >= var4) {
            return var2.toString();
         }

         char var5 = var0.charAt(var3);
         if(var5 == 10) {
            StringBuilder var6 = var2.append("\\n");
         } else if(var5 != 13) {
            if(var5 != 44 && var5 != 59 && var5 != 92) {
               var2.append(var5);
            } else {
               StringBuilder var7 = var2.append('\\');
               var2.append(var5);
            }
         }

         ++var3;
      }
   }

   public static String quoteParamValue(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         StringBuilder var2 = (new StringBuilder()).append("\"");
         String var3 = var0.replace("\"", "\'");
         var1 = var2.append(var3).append("\"").toString();
      }

      return var1;
   }

   public byte[] getBytes() {
      try {
         this.mOut.flush();
      } catch (IOException var2) {
         ;
      }

      return this.mOut.toByteArray();
   }

   public String toString() {
      return Utility.fromUtf8(this.getBytes());
   }

   void writeLine(String var1) {
      int var2 = 0;
      byte[] var3 = Utility.toUtf8(var1);
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         byte var6 = var3[var5];
         if(var2 > 71 && Utility.isFirstUtf8Byte(var6)) {
            this.mOut.write(13);
            this.mOut.write(10);
            this.mOut.write(9);
            var2 = 1;
         }

         this.mOut.write(var6);
         ++var2;
      }

      this.mOut.write(13);
      this.mOut.write(10);
   }

   public void writeTag(String var1, String var2) {
      if(!TextUtils.isEmpty(var2)) {
         if("CALSCALE".equals(var1) || "METHOD".equals(var1) || "PRODID".equals(var1) || "VERSION".equals(var1) || "CATEGORIES".equals(var1) || "CLASS".equals(var1) || "COMMENT".equals(var1) || "DESCRIPTION".equals(var1) || "LOCATION".equals(var1) || "RESOURCES".equals(var1) || "STATUS".equals(var1) || "SUMMARY".equals(var1) || "TRANSP".equals(var1) || "TZID".equals(var1) || "TZNAME".equals(var1) || "CONTACT".equals(var1) || "RELATED-TO".equals(var1) || "UID".equals(var1) || "ACTION".equals(var1) || "REQUEST-STATUS".equals(var1) || "X-LIC-LOCATION".equals(var1)) {
            var2 = escapeTextValue(var2);
         }

         String var3 = var1 + ":" + var2;
         this.writeLine(var3);
      }
   }
}
