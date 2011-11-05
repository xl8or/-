package com.htc.android.mail.providerinfo;

import android.os.SystemProperties;
import com.htc.android.mail.providerinfo.ProviderInfo;
import java.util.HashMap;

public class SprintYahooImapInfo extends ProviderInfo {

   public SprintYahooImapInfo() {
      this.loadSetting();
   }

   protected void loadSetting() {
      super.loadSetting();
      Object var1 = this.mSetting.put("aclid", "Sprint");
      HashMap var2 = this.mSetting;
      String var3 = SystemProperties.get("ro.serialno", "HT0123456789");
      var2.put("aguid", var3);
      Object var5 = this.mSetting.put("os", "android");
      HashMap var6 = this.mSetting;
      StringBuilder var7 = new StringBuilder();
      String var8 = SystemProperties.get("ro.build.version.release", "2.2");
      StringBuilder var9 = var7.append(var8).append("; ");
      String var10 = SystemProperties.get("ro.build.id", "EMPTY");
      String var11 = var9.append(var10).toString();
      var6.put("os-version", var11);
      Object var13 = this.mSetting.put("vendor", "HTC");
      HashMap var14 = this.mSetting;
      String var15 = SystemProperties.get("ro.product.model", "HTC Phone");
      var14.put("device", var15);
      Object var17 = this.mSetting.put("carrier", "Sprint");
   }
}
