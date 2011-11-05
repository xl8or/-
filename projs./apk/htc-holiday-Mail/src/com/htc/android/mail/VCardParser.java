package com.htc.android.mail;

import com.htc.android.mail.VCardException;
import com.htc.android.mail.VCardParser_V21;
import com.htc.android.mail.VCardParser_V30;
import com.htc.android.mail.VDataBuilder;
import com.htc.android.mail.VParser;
import com.htc.android.mail.ll;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class VCardParser {

   private static final String TAG = "VCardParser";
   private static final String VBEGIN = "BEGIN:VCALENDAR";
   private static final String VEND = "END:VCALENDAR";
   public static final String VERSION_VCARD21 = "vcard2.1";
   public static final int VERSION_VCARD21_INT = 1;
   public static final String VERSION_VCARD30 = "vcard3.0";
   public static final int VERSION_VCARD30_INT = 2;
   VParser mParser = null;
   String mVersion = null;


   public VCardParser() {}

   private void judgeVersion(String var1) {
      if(this.mVersion == null) {
         int var2 = var1.indexOf("\nVERSION:");
         if(var2 == -1) {
            this.mVersion = "vcard2.1";
         } else {
            int var5 = var2 + 1;
            int var6 = var1.indexOf("\n", var5);
            String var7 = var1.substring(var2, var6);
            if(var7.indexOf("2.1") > 0) {
               this.mVersion = "vcard2.1";
            } else if(var7.indexOf("3.0") > 0) {
               this.mVersion = "vcard3.0";
            } else {
               this.mVersion = "vcard2.1";
            }
         }
      }

      if(this.mVersion.equals("vcard2.1")) {
         VCardParser_V21 var3 = new VCardParser_V21();
         this.mParser = var3;
      }

      if(this.mVersion.equals("vcard3.0")) {
         VCardParser_V30 var4 = new VCardParser_V30();
         this.mParser = var4;
      }
   }

   private void setVersion(String var1) {
      this.mVersion = var1;
   }

   private String verifyVCard(String var1) {
      this.judgeVersion(var1);
      String[] var2 = var1.replaceAll("\r\n", "\n").split("\n");
      StringBuilder var3 = new StringBuilder("");
      int var4 = 0;

      while(true) {
         int var5 = var2.length;
         if(var4 >= var5) {
            return var3.toString();
         }

         if(var2[var4].indexOf(":") < 0) {
            label18: {
               if(var2[var4].length() == 0) {
                  int var6 = var4 + 1;
                  if(var2[var6].indexOf(":") > 0) {
                     String var7 = var2[var4];
                     StringBuilder var8 = var3.append(var7).append("\r\n");
                     break label18;
                  }
               }

               StringBuilder var9 = var3.append(" ");
               String var10 = var2[var4];
               StringBuilder var11 = var9.append(var10).append("\r\n");
            }
         } else {
            String var12 = var2[var4];
            StringBuilder var13 = var3.append(var12).append("\r\n");
         }

         ++var4;
      }
   }

   public boolean parse(String var1, VDataBuilder var2) throws VCardException, IOException {
      String var3 = this.verifyVCard(var1);
      VParser var4 = this.mParser;
      byte[] var5 = var3.getBytes();
      ByteArrayInputStream var6 = new ByteArrayInputStream(var5);
      boolean var7;
      if(!var4.parse(var6, "US-ASCII", var2)) {
         if(!this.mVersion.equals("vcard2.1")) {
            throw new VCardException("parse failed.(even use 3.0 parser)");
         }

         ll.d("VCardParser", "Parse failed for vCard 2.1 parser. Try to use 3.0 parser.");
         this.setVersion("vcard3.0");
         var7 = this.parse(var3, var2);
      } else {
         var7 = true;
      }

      return var7;
   }
}
