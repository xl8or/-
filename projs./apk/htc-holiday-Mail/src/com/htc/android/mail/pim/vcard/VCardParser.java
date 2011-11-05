package com.htc.android.mail.pim.vcard;

import android.util.Log;
import com.htc.android.mail.pim.VDataBuilder;
import com.htc.android.mail.pim.VParser;
import com.htc.android.mail.pim.vcard.VCardException;
import com.htc.android.mail.pim.vcard.VCardParser_V21;
import com.htc.android.mail.pim.vcard.VCardParser_V30;
import java.io.IOException;

public class VCardParser {

   private static final String TAG = "VCardParser";
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
      return var1;
   }

   public boolean parse(String var1, VDataBuilder var2) throws VCardException, IOException {
      String var3 = this.verifyVCard(var1);
      boolean var5;
      if(!this.mParser.parse(var3, var2)) {
         if(!this.mVersion.equals("vcard2.1")) {
            throw new VCardException("parse failed.(even use 3.0 parser)");
         }

         int var4 = Log.d("VCardParser", "Parse failed for vCard 2.1 parser. Try to use 3.0 parser.");
         this.setVersion("vcard3.0");
         var5 = this.parse(var3, var2);
      } else {
         var5 = true;
      }

      return var5;
   }
}
