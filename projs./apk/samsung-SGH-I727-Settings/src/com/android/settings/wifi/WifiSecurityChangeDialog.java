package com.android.settings.wifi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class WifiSecurityChangeDialog extends Activity {

   private static final String TAG = "WifiSecurityChangeDialog";


   public WifiSecurityChangeDialog() {}

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      int var2 = Log.d("WifiSecurityChangeDialog", "WifiSecurityChangeDialog");
      Toast.makeText(this, 2131232163, 0).show();
      this.finish();
   }
}
