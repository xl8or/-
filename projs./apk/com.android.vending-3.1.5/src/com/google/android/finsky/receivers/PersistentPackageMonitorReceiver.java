package com.google.android.finsky.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.FinskyInstance;
import com.google.android.finsky.local.LocalAsset;
import java.util.ListIterator;

public class PersistentPackageMonitorReceiver extends BroadcastReceiver {

   private static final String ACTION_INSTALL_REFERRER = "com.android.vending.INSTALL_REFERRER";


   public PersistentPackageMonitorReceiver() {}

   private void broadcastInstallReferrer(Context var1, Intent var2) {
      String var3 = var2.getAction();
      String var4 = this.getPackageName(var2);
      if("android.intent.action.PACKAGE_ADDED".equals(var3)) {
         if(var4 != null) {
            LocalAsset var5 = FinskyInstance.get().getAssetStore().getAsset(var4);
            if(var5 != null) {
               String var6 = var5.getExternalReferrer();
               if(!TextUtils.isEmpty(var6)) {
                  Intent var7 = new Intent("com.android.vending.INSTALL_REFERRER");
                  var7.putExtra("referrer", var6);
                  ListIterator var9 = var1.getPackageManager().queryBroadcastReceivers(var7, 0).listIterator();
                  String var10 = null;

                  while(var9.hasNext()) {
                     ResolveInfo var11 = (ResolveInfo)var9.next();
                     if(var11.activityInfo != null) {
                        String var12 = var11.activityInfo.packageName;
                        if(var4.equals(var12)) {
                           var10 = var11.activityInfo.name;
                           break;
                        }
                     }
                  }

                  if(var10 != null) {
                     var7.setClassName(var4, var10);
                     if(VERSION.SDK_INT >= 12) {
                        Intent var14 = var7.addFlags(32);
                     }

                     var1.sendBroadcast(var7);
                     var5.setExternalReferrer((String)null);
                  }
               }
            }
         }
      }
   }

   private String getPackageName(Intent var1) {
      Uri var2 = var1.getData();
      String var3;
      if(var2 != null) {
         var3 = var2.getSchemeSpecificPart();
      } else {
         var3 = null;
      }

      return var3;
   }

   public void onReceive(Context var1, Intent var2) {
      FinskyApp.get().getPackageMonitorReceiver().onReceive(var1, var2);
      this.broadcastInstallReferrer(var1, var2);
   }
}
