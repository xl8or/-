package com.android.email.service;

import android.content.Context;
import android.util.Log;
import com.sonyericsson.email.utils.customization.AccountData;
import com.sonyericsson.email.utils.customization.CustomizationFactory;
import java.util.HashMap;
import java.util.Iterator;

public final class MailPushSecurity {

   private static final int CHECK_NO_1 = 1;
   private static final String SCHEME_APPID = "application-id";
   private static final String SCHEME_MPS = "source_mps";
   private static final String SCHEME_PPG = "source_ppg";
   private static final String SYS_PROP_MAILPUSH = "ro.semc.push_email";
   private static final String TAG = "MailPushSecurity";
   private static final String VALUE_MPS_DOCOMO = "+819054214";
   private static final String VALUE_PPG_DOCOMO = "NTT DoCoMo";
   private static final String VALUE_WAP_APPID_EMN_UA = "9";
   private static final String VENDOR_DOCOMO = "docomo";
   private static MailPushSecurity mSecurity = null;
   private HashMap<String, String> mSecurityMap;


   public MailPushSecurity() {
      HashMap var1 = new HashMap();
      this.mSecurityMap = var1;
   }

   private boolean checkAppid(int var1, String var2) {
      int var3 = Integer.parseInt(var2);
      boolean var5;
      if(var1 == var3) {
         int var4 = Log.d("MailPushSecurity", "checkAppid OK");
         var5 = true;
      } else {
         String var6 = "checkAppid NG : " + var1;
         int var7 = Log.d("MailPushSecurity", var6);
         var5 = false;
      }

      return var5;
   }

   private boolean checkMps(String var1, String var2) {
      boolean var4;
      if(var1 == null) {
         int var3 = Log.e("MailPushSecurity", "MPS(Service Center Address) null");
         var4 = false;
      } else if(var1.startsWith(var2)) {
         int var5 = Log.d("MailPushSecurity", "checkMps OK");
         var4 = true;
      } else {
         String var6 = "checkMps NG : " + var1;
         int var7 = Log.d("MailPushSecurity", var6);
         var4 = false;
      }

      return var4;
   }

   private boolean checkPpg(String var1, String var2) {
      boolean var4;
      if(var1 == null) {
         int var3 = Log.e("MailPushSecurity", "PPG(Push Proxy Gateway Originating Address) null");
         var4 = false;
      } else if(var1 != null && var1.equals(var2)) {
         int var5 = Log.d("MailPushSecurity", "checkPpg OK");
         var4 = true;
      } else {
         String var6 = "checkPpg NG : " + var1;
         int var7 = Log.d("MailPushSecurity", var6);
         var4 = false;
      }

      return var4;
   }

   public static MailPushSecurity getMailPushSecurity(Context var0) {
      if(mSecurity == null) {
         mSecurity = new MailPushSecurity();
         AccountData var1 = CustomizationFactory.getInstance().getCustomization(var0).getDefaultSettings();
         int var2 = 0;
         if(var1 != null) {
            var2 = var1.getSysPropMailpush();
         }

         mSecurity.setSecurityMap(var2);
      }

      return mSecurity;
   }

   private void setSecurityMap(int var1) {
      String var2 = "setSecurityMap CheckNo : " + var1;
      int var3 = Log.d("MailPushSecurity", var2);
      this.mSecurityMap.clear();
      switch(var1) {
      case 1:
         Object var4 = this.mSecurityMap.put("source_mps", "+819054214");
         Object var5 = this.mSecurityMap.put("source_ppg", "NTT DoCoMo");
         Object var6 = this.mSecurityMap.put("application-id", "9");
         return;
      default:
      }
   }

   public boolean checkSecurity(String var1, String var2, int var3) {
      byte var4 = 0;
      Iterator var5 = this.mSecurityMap.keySet().iterator();

      while(var5.hasNext()) {
         String var6 = (String)var5.next();
         var4 = 0;
         if(var6.equals("source_mps")) {
            String var7 = (String)this.mSecurityMap.get("source_mps");
            var4 = this.checkMps(var1, var7);
         } else if(var6.equals("source_ppg")) {
            String var10 = (String)this.mSecurityMap.get("source_ppg");
            var4 = this.checkPpg(var2, var10);
         } else if(var6.equals("application-id")) {
            String var11 = (String)this.mSecurityMap.get("application-id");
            var4 = this.checkAppid(var3, var11);
         }

         if(var4 == 0) {
            break;
         }
      }

      String var8 = "checkSecurity Result : " + var4;
      int var9 = Log.d("MailPushSecurity", var8);
      return (boolean)var4;
   }
}
