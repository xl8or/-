package com.facebook.katana.binding;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.facebook.katana.service.UploadManager;

public class UploadManagerConnectivity extends BroadcastReceiver {

   public UploadManagerConnectivity() {}

   public static boolean isConnected(Context var0) {
      NetworkInfo var1 = ((ConnectivityManager)var0.getSystemService("connectivity")).getActiveNetworkInfo();
      boolean var2;
      if(var1 != null && var1.isConnectedOrConnecting()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public void onReceive(Context var1, Intent var2) {
      if(isConnected(var1)) {
         Intent var3 = new Intent(var1, UploadManager.class);
         Intent var4 = var3.putExtra("type", 1);
         var1.startService(var3);
      }
   }
}
