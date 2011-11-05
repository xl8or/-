package com.android.settings.wifi;

import android.app.Activity;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;
import java.util.List;

public class WifiConfigInfo extends Activity {

   private static final String TAG = "WifiConfigInfo";
   private TextView mConfigList;
   private WifiManager mWifiManager;


   public WifiConfigInfo() {}

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      WifiManager var2 = (WifiManager)this.getSystemService("wifi");
      this.mWifiManager = var2;
      this.setContentView(2130903142);
      TextView var3 = (TextView)this.findViewById(2131427665);
      this.mConfigList = var3;
   }

   protected void onResume() {
      super.onResume();
      List var1 = this.mWifiManager.getConfiguredNetworks();
      StringBuffer var2 = new StringBuffer();

      for(int var3 = var1.size() - 1; var3 >= 0; var3 += -1) {
         Object var4 = var1.get(var3);
         var2.append(var4);
      }

      this.mConfigList.setText(var2);
   }
}
