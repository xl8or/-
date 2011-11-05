package com.android.email;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.android.email.provider.EmailContent;
import com.broadcom.map.email.MAPEmailProvider;

public class BluetoothMAPService extends Service {

   private static final String ACTION_REGISTER_MAP = "com.android.email.REGISTER_PROVIDER";
   private static final String TAG = "BluetoothMAPService";
   private MAPEmailProvider mMapProvider;
   private BluetoothMAPService.MapAccountObserver mObserver;


   public BluetoothMAPService() {}

   public static void registerMAPProvider(Context var0) {
      int var1 = Log.v("BluetoothMAPService", "MAP Service registerMAPProvider");
      Intent var2 = new Intent();
      var2.setClass(var0, BluetoothMAPService.class);
      Intent var4 = var2.setAction("com.android.email.REGISTER_PROVIDER");
      var0.startService(var2);
   }

   public static void unregisterMAPProvider(Context var0) {
      int var1 = Log.v("BluetoothMAPService", "MAP Service unregisterMAPProvider");
      Intent var2 = new Intent();
      var2.setClass(var0, BluetoothMAPService.class);
      var0.stopService(var2);
   }

   public IBinder onBind(Intent var1) {
      int var2 = Log.v("BluetoothMAPService", "MAP Service onBind");
      return null;
   }

   public void onCreate() {
      super.onCreate();
      int var1 = Log.v("BluetoothMAPService", "MAP Service onCreate");
   }

   public void onDestroy() {
      int var1 = Log.v("BluetoothMAPService", "MAP Service onDestroy");
      super.onDestroy();
      if(this.mObserver != null) {
         ContentResolver var2 = this.getContentResolver();
         BluetoothMAPService.MapAccountObserver var3 = this.mObserver;
         var2.unregisterContentObserver(var3);
      }

      if(this.mMapProvider != null) {
         this.mMapProvider.finish();
         this.mMapProvider = null;
      }
   }

   public int onStartCommand(Intent var1, int var2, int var3) {
      int var4 = Log.v("BluetoothMAPService", "MAP Service onStartCommand");
      super.onStartCommand(var1, var2, var3);
      String var6 = var1.getAction();
      if(var6.equals("com.android.email.REGISTER_PROVIDER")) {
         String var7 = " Processing command " + var6;
         int var8 = Log.d("BluetoothMAPService", var7);
         if(this.mMapProvider == null) {
            int var9 = Log.d("BluetoothMAPService", " MAp provider is null.  Creating new one");
            if(EmailContent.Account.getDefaultAccountId(this) >= 0L) {
               MAPEmailProvider var10 = new MAPEmailProvider();
               this.mMapProvider = var10;
               this.mMapProvider.init(this, (boolean)1);
            } else if(this.mObserver == null) {
               int var11 = Log.d("BluetoothMAPService", " MAP register AccountObserver");
               BluetoothMAPService.MapAccountObserver var12 = new BluetoothMAPService.MapAccountObserver();
               this.mObserver = var12;
               ContentResolver var13 = this.getContentResolver();
               Uri var14 = EmailContent.Account.CONTENT_URI;
               BluetoothMAPService.MapAccountObserver var15 = this.mObserver;
               var13.registerContentObserver(var14, (boolean)1, var15);
            }
         }
      }

      return 3;
   }

   private class MapAccountObserver extends ContentObserver {

      public MapAccountObserver() {
         Handler var2 = new Handler();
         super(var2);
      }

      public void onChange(boolean var1) {
         int var2 = Log.v("BluetoothMAPService", "MapAccountObserver. onChange");
         if(EmailContent.Account.getDefaultAccountId(BluetoothMAPService.this) >= 0L) {
            BluetoothMAPService.registerMAPProvider(BluetoothMAPService.this);
            if(BluetoothMAPService.this.mObserver != null) {
               ContentResolver var3 = BluetoothMAPService.this.getContentResolver();
               BluetoothMAPService.MapAccountObserver var4 = BluetoothMAPService.this.mObserver;
               var3.unregisterContentObserver(var4);
               BluetoothMAPService.MapAccountObserver var5 = BluetoothMAPService.this.mObserver = null;
            }
         }
      }
   }
}
