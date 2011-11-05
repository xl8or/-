package com.google.android.gsf;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gsf.Gservices;
import java.util.Locale;

public class HelpUrl {

   private static final String DEFAULT_HELP_URL = "http://www.google.com/support/mobile/?hl=%locale%";
   private static final String SMART_HELP_LINK_APP_VERSION = "version";
   private static final String SMART_HELP_LINK_PARAMETER_NAME = "p";
   private static final String TAG = "HelpUrl";


   public HelpUrl() {}

   public static Uri getHelpUrl(Context var0, String var1) {
      if(TextUtils.isEmpty(var1)) {
         throw new IllegalArgumentException("getHelpUrl(): fromWhere must be non-empty");
      } else {
         Builder var2 = Uri.parse(replaceLocale(Gservices.getString(var0.getContentResolver(), "context_sensitive_help_url", "http://www.google.com/support/mobile/?hl=%locale%"))).buildUpon();
         var2.appendQueryParameter("p", var1);

         try {
            PackageManager var4 = var0.getPackageManager();
            String var5 = var0.getApplicationInfo().packageName;
            String var6 = String.valueOf(var4.getPackageInfo(var5, 0).versionCode);
            var2.appendQueryParameter("version", var6);
         } catch (NameNotFoundException var13) {
            StringBuilder var9 = (new StringBuilder()).append("Error finding package ");
            String var10 = var0.getApplicationInfo().packageName;
            String var11 = var9.append(var10).toString();
            int var12 = Log.e("HelpUrl", var11);
         }

         return var2.build();
      }
   }

   private static String replaceLocale(String var0) {
      if(var0.contains("%locale%")) {
         Locale var1 = Locale.getDefault();
         StringBuilder var2 = new StringBuilder();
         String var3 = var1.getLanguage();
         StringBuilder var4 = var2.append(var3).append("-");
         String var5 = var1.getCountry().toLowerCase();
         String var6 = var4.append(var5).toString();
         var0 = var0.replace("%locale%", var6);
      }

      return var0;
   }
}
