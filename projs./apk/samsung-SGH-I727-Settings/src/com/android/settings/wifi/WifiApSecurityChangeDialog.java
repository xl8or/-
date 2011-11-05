package com.android.settings.wifi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class WifiApSecurityChangeDialog extends Activity {

   private static final String TAG = "WifiApSecurityChangeDialog";


   public WifiApSecurityChangeDialog() {}

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      int var2 = Log.d("WifiApSecurityChangeDialog", "WifiApSecurityChangeDialog");
      Toast.makeText(this, 2131232164, 0).show();
      this.finish();
   }
}
