package com.htc.android.mail.providerinfo;

import android.util.Base64;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;

public class ProviderInfo {

   protected HashMap mSetting;


   public ProviderInfo() {
      HashMap var1 = new HashMap();
      this.mSetting = var1;
   }

   public HashMap getSetting() {
      return this.mSetting;
   }

   public Object[] getSettingKey() {
      return (Object[])this.mSetting.keySet().toArray();
   }

   public String getSettingString(String var1) {
      String var2;
      if(this.mSetting == null) {
         var2 = null;
      } else {
         if(var1 == null) {
            var1 = "";
         }

         Iterator var3 = this.mSetting.keySet().iterator();
         StringBuilder var4 = new StringBuilder();
         int var5 = 0;

         while(var3.hasNext()) {
            if(var5 != 0) {
               StringBuilder var6 = var4.append(" ");
            }

            ++var5;
            String var7 = var3.next().toString();
            if(var7 != null) {
               if(var7.equalsIgnoreCase("aguid")) {
                  try {
                     MessageDigest var8 = MessageDigest.getInstance("SHA-1");
                     byte[] var9 = this.mSetting.get(var7).toString().getBytes();
                     var8.update(var9);
                     byte[] var10 = var1.getBytes();
                     var8.update(var10);
                     String var11 = Base64.encodeToString(var8.digest(), 2);
                     String var12 = "\"" + var7 + "\"";
                     StringBuilder var13 = var4.append(var12).append(" ");
                     String var14 = "\"" + var11 + "\"";
                     var13.append(var14);
                  } catch (Exception var28) {
                     var28.printStackTrace();
                     String var16 = "\"" + var7 + "\"";
                     StringBuilder var17 = var4.append(var16).append(" ");
                     StringBuilder var18 = (new StringBuilder()).append("\"");
                     Object var19 = this.mSetting.get(var7);
                     String var20 = var18.append(var19).append("\"").toString();
                     var17.append(var20);
                  }
               } else {
                  String var22 = "\"" + var7 + "\"";
                  StringBuilder var23 = var4.append(var22).append(" ");
                  StringBuilder var24 = (new StringBuilder()).append("\"");
                  Object var25 = this.mSetting.get(var7);
                  String var26 = var24.append(var25).append("\"").toString();
                  var23.append(var26);
               }
            }
         }

         var2 = var4.toString();
      }

      return var2;
   }

   protected void loadSetting() {}

   protected void loadSettingFromXML() {}
}
