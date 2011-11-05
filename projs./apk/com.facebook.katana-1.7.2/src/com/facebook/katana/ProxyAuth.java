package com.facebook.katana;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import com.facebook.katana.Constants;
import com.facebook.katana.activity.PlatformDialogActivity;
import com.facebook.katana.util.Base64;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.URLQueryBuilder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

public class ProxyAuth extends PlatformDialogActivity {

   public ProxyAuth() {}

   private byte[] getCallingPackageSigHash() {
      PackageInfo var3;
      byte[] var7;
      try {
         PackageManager var1 = this.getPackageManager();
         String var2 = this.getCallingPackage();
         var3 = var1.getPackageInfo(var2, 64);
      } catch (NameNotFoundException var11) {
         Log.e("Facebook-ProxyAuth", "Failed to read calling package\'s signature.");
         var7 = null;
         return var7;
      }

      PackageInfo var4 = var3;

      MessageDigest var12;
      try {
         var12 = MessageDigest.getInstance("SHA-1");
      } catch (NoSuchAlgorithmException var10) {
         Log.e("Facebook-ProxyAuth", "Failed to instantiate SHA-1 algorithm. It is evidently missing from this system.");
         var7 = null;
         return var7;
      }

      byte[] var6 = var4.signatures[0].toByteArray();
      var12.update(var6);
      var7 = var12.digest();
      return var7;
   }

   protected void setupDialogURL() {
      Bundle var1 = new Bundle();
      Bundle var2 = this.getIntent().getExtras();
      Iterator var3 = var2.keySet().iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         Object var5 = var2.get(var4);
         if(var5 instanceof String) {
            String var6 = (String)var5;
            var1.putString(var4, var6);
         }
      }

      var1.putString("type", "user_agent");
      var1.putString("redirect_uri", "fbconnect://success");
      var1.putString("display", "touch");
      String var7 = Base64.encodeToString(this.getCallingPackageSigHash(), 3);
      var1.putString("android_key", var7);
      StringBuilder var8 = new StringBuilder();
      String var9 = Constants.URL.getOAuthUrl(this);
      StringBuilder var10 = var8.append(var9).append("?");
      StringBuilder var11 = URLQueryBuilder.buildQueryString(var1);
      String var12 = var10.append(var11).toString();
      this.mUrl = var12;
   }
}
