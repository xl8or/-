package com.htc.android.mail.pim.vcalendar;

import android.util.Log;
import com.htc.android.mail.pim.VDataBuilder;
import com.htc.android.mail.pim.VParser;
import com.htc.android.mail.pim.vcalendar.VCalException;
import com.htc.android.mail.pim.vcalendar.VCalParser_V10;
import com.htc.android.mail.pim.vcalendar.VCalParser_V20;

public class VCalParser {

   private static final String TAG = "VCalParser";
   public static final String VERSION_VCALENDAR10 = "vcalendar1.0";
   public static final String VERSION_VCALENDAR20 = "vcalendar2.0";
   private VParser mParser = null;
   private String mVersion = null;


   public VCalParser() {}

   private void judgeVersion(String var1) {
      if(this.mVersion == null) {
         int var2 = var1.indexOf("\nVERSION:");
         this.mVersion = "vcalendar1.0";
         if(var2 != -1) {
            int var3 = var2 + 1;
            int var4 = var1.indexOf("\n", var3);
            if(var1.substring(var2, var4).indexOf("2.0") > 0) {
               this.mVersion = "vcalendar2.0";
            }
         }
      }

      if(this.mVersion.equals("vcalendar1.0")) {
         VCalParser_V10 var5 = new VCalParser_V10();
         this.mParser = var5;
      }

      if(this.mVersion.equals("vcalendar2.0")) {
         VCalParser_V20 var6 = new VCalParser_V20();
         this.mParser = var6;
      }
   }

   private String verifyVCal(String var1) {
      this.judgeVersion(var1);
      String[] var2 = var1.replaceAll("\r\n", "\n").split("\n");
      StringBuilder var3 = new StringBuilder();
      int var4 = 0;

      while(true) {
         int var5 = var2.length;
         if(var4 >= var5) {
            StringBuilder var14 = (new StringBuilder()).append("After verify:\r\n");
            String var15 = var3.toString();
            String var16 = var14.append(var15).toString();
            int var17 = Log.d("VCalParser", var16);
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

   public boolean parse(String param1, VDataBuilder param2) throws VCalException {
      // $FF: Couldn't be decompiled
   }
}
