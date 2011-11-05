package com.google.android.finsky.download.obb;

import android.os.Build.VERSION;
import com.google.android.finsky.download.obb.ObbFactory;
import com.google.android.finsky.receivers.PackageMonitorReceiver;
import java.io.File;

public class ObbPackageTracker implements PackageMonitorReceiver.PackageStatusListener {

   private final int GINGERBREAD_MR1 = 10;


   public ObbPackageTracker() {}

   public void onPackageAdded(String var1) {}

   public void onPackageAvailabilityChanged(String[] var1, boolean var2) {}

   public void onPackageChanged(String var1) {}

   public void onPackageRemoved(String var1, boolean var2) {
      if(VERSION.SDK_INT <= 10) {
         if(!var2) {
            File var3 = ObbFactory.getParentDirectory(var1);
            if(var3.exists()) {
               File[] var4 = var3.listFiles();
               int var5 = var4.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  boolean var7 = var4[var6].delete();
               }

               boolean var8 = var3.delete();
            }
         }
      }
   }
}
