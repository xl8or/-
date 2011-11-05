package com.htc.android.mail.providerinfo;

import android.os.SystemProperties;
import com.htc.android.mail.providerinfo.ProviderInfo;
import java.util.HashMap;

public class TMOYahooImapInfo extends ProviderInfo {

   public TMOYahooImapInfo() {
      this.loadSetting();
   }

   protected void loadSetting() {
      super.loadSetting();
      Object var1 = this.mSetting.put("name", "com.htc.android.mail");
      Object var2 = this.mSetting.put("os", "android");
      HashMap var3 = this.mSetting;
      StringBuilder var4 = new StringBuilder();
      String var5 = SystemProperties.get("ro.build.version.release", "2.2");
      StringBuilder var6 = var4.append(var5).append("; ");
      String var7 = SystemProperties.get("ro.build.id", "EMPTY");
      String var8 = var6.append(var7).toString();
      var3.put("os-version", var8);
      Object var10 = this.mSetting.put("vendor", "HTC");
      HashMap var11 = this.mSetting;
      String var12 = SystemProperties.get("ro.product.model", "HTC Phone");
      var11.put("x-android-device-model", var12);
      Object var14 = this.mSetting.put("x-android-mobile-net-operator", "t-mobile");
      HashMap var15 = this.mSetting;
      String var16 = SystemProperties.get("ro.serialno", "HT0123456789");
      var15.put("aguid", var16);
      Object var18 = this.mSetting.put("aclid", "TMoUS");
   }
}
