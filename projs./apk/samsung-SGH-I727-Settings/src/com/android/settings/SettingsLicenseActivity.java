package com.android.settings;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;

public class SettingsLicenseActivity extends AlertActivity {

   private static final String DEFAULT_LICENSE_PATH = "/system/etc/NOTICE.html.gz";
   private static final boolean LOGV = false;
   private static final String PROPERTY_LICENSE_PATH = "ro.config.license_path";
   private static final String TAG = "SettingsLicenseActivity";


   public SettingsLicenseActivity() {}

   private void showErrorAndFinish() {
      Toast.makeText(this, 2131231534, 1).show();
      this.finish();
   }

   protected void onCreate(Bundle param1) {
      // $FF: Couldn't be decompiled
   }

   class 1 extends WebViewClient {

      1() {}

      public void onPageFinished(WebView var1, String var2) {
         AlertController var3 = SettingsLicenseActivity.this.mAlert;
         String var4 = SettingsLicenseActivity.this.getString(2131231533);
         var3.setTitle(var4);
      }
   }
}
