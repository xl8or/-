package com.android.settings.bluetooth;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import com.android.settings.bluetooth.DockService;

public class DockEventReceiver extends BroadcastReceiver {

   public static final String ACTION_DOCK_SHOW_UI = "com.android.settings.bluetooth.action.DOCK_SHOW_UI";
   private static final boolean DEBUG = false;
   private static final int EXTRA_INVALID = 64302;
   private static final String TAG = "DockEventReceiver";
   private static final long WAKELOCK_TIMEOUT = 5000L;
   private static WakeLock mStartingService;
   private static final Object mStartingServiceSync = new Object();


   public DockEventReceiver() {}

   public static void beginStartingService(Context var0, Intent var1) {
      Object var2 = mStartingServiceSync;
      synchronized(var2) {
         if(mStartingService == null) {
            mStartingService = ((PowerManager)var0.getSystemService("power")).newWakeLock(1, "StartingDockService");
         }

         mStartingService.acquire(5000L);
         if(var0.startService(var1) == null) {
            int var3 = Log.e("DockEventReceiver", "Can\'t start DockService");
         }

      }
   }

   public static void finishStartingService(Service var0, int var1) {
      Object var2 = mStartingServiceSync;
      synchronized(var2) {
         if(mStartingService != null) {
            var0.stopSelfResult(var1);
         }

      }
   }

   public void onReceive(Context var1, Intent var2) {
      if(var2 != null) {
         int var3 = var2.getIntExtra("android.bluetooth.adapter.extra.STATE", '\ufb2e');
         int var4 = var2.getIntExtra("android.intent.extra.DOCK_STATE", var3);
         BluetoothDevice var5 = (BluetoothDevice)var2.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
         String var6 = var2.getAction();
         if(!"android.intent.action.DOCK_EVENT".equals(var6)) {
            String var7 = var2.getAction();
            if(!"com.android.settings.bluetooth.action.DOCK_SHOW_UI".endsWith(var7)) {
               String var10 = var2.getAction();
               if("android.bluetooth.headset.action.STATE_CHANGED".equals(var10)) {
                  if(var5 == null) {
                     return;
                  }

                  if(var2.getIntExtra("android.bluetooth.headset.extra.STATE", 2) != 0) {
                     return;
                  }

                  if(var2.getIntExtra("android.bluetooth.headset.extra.DISCONNECT_INITIATOR", 1) != 0) {
                     return;
                  }

                  Intent var11 = new Intent(var2);
                  var11.setClass(var1, DockService.class);
                  beginStartingService(var1, var11);
                  return;
               }

               String var13 = var2.getAction();
               if("android.bluetooth.a2dp.action.SINK_STATE_CHANGED".equals(var13)) {
                  if(var5 == null) {
                     return;
                  }

                  int var14 = var2.getIntExtra("android.bluetooth.a2dp.extra.SINK_STATE", 0);
                  int var15 = var2.getIntExtra("android.bluetooth.a2dp.extra.PREVIOUS_SINK_STATE", 0);
                  if(var14 != 0) {
                     return;
                  }

                  if(var15 == 3) {
                     return;
                  }

                  Intent var16 = new Intent(var2);
                  var16.setClass(var1, DockService.class);
                  beginStartingService(var1, var16);
                  return;
               }

               String var18 = var2.getAction();
               if(!"android.bluetooth.adapter.action.STATE_CHANGED".equals(var18)) {
                  return;
               }

               if(var2.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE) == 11) {
                  return;
               }

               Intent var19 = new Intent(var2);
               var19.setClass(var1, DockService.class);
               beginStartingService(var1, var19);
               return;
            }
         }

         if(var5 != null) {
            switch(var4) {
            case 0:
            case 1:
            case 2:
               Intent var8 = new Intent(var2);
               var8.setClass(var1, DockService.class);
               beginStartingService(var1, var8);
               return;
            default:
            }
         }
      }
   }
}
