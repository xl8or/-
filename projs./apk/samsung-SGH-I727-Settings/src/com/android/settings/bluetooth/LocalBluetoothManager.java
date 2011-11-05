package com.android.settings.bluetooth;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Toast;
import com.android.settings.bluetooth.BluetoothEventRedirector;
import com.android.settings.bluetooth.CachedBluetoothDevice;
import com.android.settings.bluetooth.CachedBluetoothDeviceManager;
import com.android.settings.bluetooth.LocalBluetoothProfileManager;
import com.broadcom.bt.service.framework.BluetoothServiceConfig;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class LocalBluetoothManager {

   static final boolean D = true;
   private static long GRACE_PERIOD_TO_SHOW_DIALOGS_IN_FOREGROUND = 60000L;
   private static LocalBluetoothManager INSTANCE;
   private static final int SCAN_EXPIRATION_MS = 300000;
   public static final String SHARED_PREFERENCES_KEY_DISCOVERING_TIMESTAMP = "last_discovering_time";
   private static final String SHARED_PREFERENCES_KEY_DOCK_AUTO_CONNECT = "auto_connect_to_dock";
   private static final String SHARED_PREFERENCES_KEY_LAST_SELECTED_DEVICE = "last_selected_device";
   private static final String SHARED_PREFERENCES_KEY_LAST_SELECTED_DEVICE_TIME = "last_selected_device_time";
   private static final String SHARED_PREFERENCES_NAME = "bluetooth_settings";
   private static final String TAG = "LocalBluetoothManager";
   static final boolean V;
   private BluetoothAdapter mAdapter;
   private BluetoothA2dp mBluetoothA2dp;
   private CachedBluetoothDeviceManager mCachedDeviceManager;
   private List<LocalBluetoothManager.Callback> mCallbacks;
   private Context mContext;
   private AlertDialog mErrorDialog = null;
   private BluetoothEventRedirector mEventRedirector;
   private Activity mForegroundActivity;
   private boolean mInitialized;
   private long mLastScan;
   private int mState = Integer.MIN_VALUE;


   public LocalBluetoothManager() {
      ArrayList var1 = new ArrayList();
      this.mCallbacks = var1;
   }

   private void dispatchScanningStateChanged(boolean var1) {
      List var2 = this.mCallbacks;
      synchronized(var2) {
         Iterator var3 = this.mCallbacks.iterator();

         while(var3.hasNext()) {
            ((LocalBluetoothManager.Callback)var3.next()).onScanningStateChanged(var1);
         }

      }
   }

   public static LocalBluetoothManager getInstance(Context var0) {
      Class var1 = LocalBluetoothManager.class;
      synchronized(var1) {
         if(INSTANCE == null) {
            INSTANCE = new LocalBluetoothManager();
         }

         LocalBluetoothManager var5;
         if(!INSTANCE.init(var0)) {
            var5 = null;
         } else {
            LocalBluetoothProfileManager.init(INSTANCE);
            LocalBluetoothManager var2 = INSTANCE;
            var5 = var2;
         }

         return var5;
      }
   }

   private boolean init(Context var1) {
      boolean var2;
      if(this.mInitialized) {
         var2 = true;
      } else {
         this.mInitialized = (boolean)1;
         Context var3 = var1.getApplicationContext();
         this.mContext = var3;
         BluetoothAdapter var4 = BluetoothAdapter.getDefaultAdapter();
         this.mAdapter = var4;
         if(this.mAdapter == null) {
            var2 = false;
         } else {
            CachedBluetoothDeviceManager var5 = new CachedBluetoothDeviceManager(this);
            this.mCachedDeviceManager = var5;
            BluetoothEventRedirector var6 = new BluetoothEventRedirector(this);
            this.mEventRedirector = var6;
            this.mEventRedirector.start();
            BluetoothA2dp var7 = new BluetoothA2dp(var1);
            this.mBluetoothA2dp = var7;
            var2 = true;
         }
      }

      return var2;
   }

   private void syncBluetoothState() {
      int var1;
      if(this.mAdapter != null) {
         if(this.mAdapter.isEnabled()) {
            var1 = 12;
         } else {
            var1 = 10;
         }
      } else {
         var1 = Integer.MIN_VALUE;
      }

      this.setBluetoothStateInt(var1);
   }

   public BluetoothAdapter getBluetoothAdapter() {
      return this.mAdapter;
   }

   public int getBluetoothState() {
      if(this.mState == Integer.MIN_VALUE) {
         this.syncBluetoothState();
      }

      return this.mState;
   }

   public CachedBluetoothDeviceManager getCachedDeviceManager() {
      return this.mCachedDeviceManager;
   }

   List<LocalBluetoothManager.Callback> getCallbacks() {
      return this.mCallbacks;
   }

   public Context getContext() {
      return this.mContext;
   }

   public boolean getDockAutoConnectSetting(String var1) {
      SharedPreferences var2 = this.getSharedPreferences();
      String var3 = "auto_connect_to_dock" + var1;
      return var2.getBoolean(var3, (boolean)0);
   }

   public Activity getForegroundActivity() {
      return this.mForegroundActivity;
   }

   public SharedPreferences getSharedPreferences() {
      return this.mContext.getSharedPreferences("bluetooth_settings", 0);
   }

   public boolean hasDockAutoConnectSetting(String var1) {
      SharedPreferences var2 = this.getSharedPreferences();
      String var3 = "auto_connect_to_dock" + var1;
      return var2.contains(var3);
   }

   void onScanningStateChanged(boolean var1) {
      this.mCachedDeviceManager.onScanningStateChanged(var1);
      this.dispatchScanningStateChanged(var1);
   }

   void persistSelectedDeviceInPicker(String var1) {
      Editor var2 = this.getSharedPreferences().edit();
      var2.putString("last_selected_device", var1);
      long var4 = System.currentTimeMillis();
      var2.putLong("last_selected_device_time", var4);
      var2.apply();
   }

   public void registerCallback(LocalBluetoothManager.Callback var1) {
      List var2 = this.mCallbacks;
      synchronized(var2) {
         this.mCallbacks.add(var1);
      }
   }

   public void removeDockAutoConnectSetting(String var1) {
      Editor var2 = this.getSharedPreferences().edit();
      String var3 = "auto_connect_to_dock" + var1;
      var2.remove(var3);
      var2.apply();
   }

   public void saveDockAutoConnectSetting(String var1, boolean var2) {
      Editor var3 = this.getSharedPreferences().edit();
      String var4 = "auto_connect_to_dock" + var1;
      var3.putBoolean(var4, var2);
      var3.apply();
   }

   public void setBluetoothEnabled(boolean var1) {
      boolean var2;
      if(var1) {
         var2 = this.mAdapter.enable();
      } else {
         var2 = this.mAdapter.disable();
      }

      if(var2) {
         byte var3;
         if(var1) {
            var3 = 11;
         } else {
            var3 = 13;
         }

         this.setBluetoothStateInt(var3);
      } else {
         this.syncBluetoothState();
      }
   }

   void setBluetoothStateInt(int var1) {
      this.mState = var1;
      if(var1 == 12 || var1 == 10) {
         CachedBluetoothDeviceManager var2 = this.mCachedDeviceManager;
         byte var3;
         if(var1 == 12) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         var2.onBluetoothStateChanged((boolean)var3);
      }
   }

   public void setForegroundActivity(Activity var1) {
      if(this.mErrorDialog != null) {
         this.mErrorDialog.dismiss();
         this.mErrorDialog = null;
      }

      this.mForegroundActivity = var1;
   }

   public boolean shouldShowDialogInForeground(String var1) {
      boolean var2;
      if(this.mForegroundActivity != null) {
         var2 = true;
      } else {
         long var3 = System.currentTimeMillis();
         SharedPreferences var5 = this.getSharedPreferences();
         long var6 = var5.getLong("discoverable_end_timestamp", 0L);
         if(GRACE_PERIOD_TO_SHOW_DIALOGS_IN_FOREGROUND + var6 > var3) {
            var2 = true;
         } else if(this.mAdapter != null && this.mAdapter.isDiscovering()) {
            var2 = true;
         } else {
            long var8 = var5.getLong("last_discovering_time", 0L);
            long var10 = GRACE_PERIOD_TO_SHOW_DIALOGS_IN_FOREGROUND;
            if(var8 + var10 > var3) {
               var2 = true;
            } else {
               if(var1 != null) {
                  String var12 = var5.getString("last_selected_device", (String)null);
                  if(var1.equals(var12)) {
                     long var13 = var5.getLong("last_selected_device_time", 0L);
                     if(GRACE_PERIOD_TO_SHOW_DIALOGS_IN_FOREGROUND + var13 > var3) {
                        var2 = true;
                        return var2;
                     }
                  }
               }

               var2 = false;
            }
         }
      }

      return var2;
   }

   public void showError(BluetoothDevice var1, int var2, int var3) {
      CachedBluetoothDevice var4 = this.mCachedDeviceManager.findDevice(var1);
      String var5 = null;
      if(var4 == null) {
         if(var1 != null) {
            var5 = var1.getName();
         }

         if(var5 == null) {
            var5 = this.mContext.getString(2131231013);
         }
      } else {
         var5 = var4.getName();
      }

      Context var6 = this.mContext;
      Object[] var7 = new Object[]{var5};
      String var8 = var6.getString(var3, var7);
      if(this.mForegroundActivity != null) {
         Activity var9 = this.mForegroundActivity;
         AlertDialog var10 = (new Builder(var9)).setIcon(17301543).setTitle(var2).setMessage(var8).setPositiveButton(17039370, (OnClickListener)null).show();
         this.mErrorDialog = var10;
      } else {
         Toast.makeText(this.mContext, var8, 1).show();
      }
   }

   public void startScanning(boolean var1) {
      if(!BluetoothServiceConfig.isScanAllowed(this.getContext())) {
         Toast.makeText(this.getContext(), 2131232613, 1).show();
      } else if(this.mAdapter.isDiscovering()) {
         this.dispatchScanningStateChanged((boolean)1);
      } else {
         if(!var1) {
            long var2 = this.mLastScan + 300000L;
            long var4 = System.currentTimeMillis();
            if(var2 > var4) {
               return;
            }

            Set var6 = this.mBluetoothA2dp.getConnectedSinks();
            if(var6 != null) {
               Iterator var7 = var6.iterator();

               while(var7.hasNext()) {
                  BluetoothDevice var8 = (BluetoothDevice)var7.next();
                  if(this.mBluetoothA2dp.getSinkState(var8) == 4) {
                     return;
                  }
               }
            }
         }

         if(this.mAdapter.startDiscovery()) {
            long var9 = System.currentTimeMillis();
            this.mLastScan = var9;
         }
      }
   }

   public void startScanning(boolean var1, int var2) {
      if(this.mAdapter.isDiscovering()) {
         this.dispatchScanningStateChanged((boolean)1);
      } else {
         if(!var1) {
            long var3 = this.mLastScan + 300000L;
            long var5 = System.currentTimeMillis();
            if(var3 > var5) {
               return;
            }

            Set var7 = this.mBluetoothA2dp.getConnectedSinks();
            if(var7 != null) {
               Iterator var8 = var7.iterator();

               while(var8.hasNext()) {
                  BluetoothDevice var9 = (BluetoothDevice)var8.next();
                  if(this.mBluetoothA2dp.getSinkState(var9) == 4) {
                     return;
                  }
               }
            }
         }

         String var10 = "startDiscoveryByCod: Cod = " + var2;
         int var11 = Log.i("LocalBluetoothManager", var10);
         if(this.mAdapter.startDiscoveryByCod(var2)) {
            long var12 = System.currentTimeMillis();
            this.mLastScan = var12;
         }
      }
   }

   public void stopScanning() {
      if(this.mAdapter.isDiscovering()) {
         boolean var1 = this.mAdapter.cancelDiscovery();
      }
   }

   public void unregisterCallback(LocalBluetoothManager.Callback var1) {
      List var2 = this.mCallbacks;
      synchronized(var2) {
         this.mCallbacks.remove(var1);
      }
   }

   public interface Callback {

      void onDeviceAdded(CachedBluetoothDevice var1);

      void onDeviceDeleted(CachedBluetoothDevice var1);

      void onScanningStateChanged(boolean var1);
   }
}
