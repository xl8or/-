package com.broadcom.bt.app.settings;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.Log;
import com.broadcom.bt.app.settings.ServiceExtraSettings;
import com.broadcom.bt.service.framework.IServiceManager;
import com.broadcom.bt.service.framework.IServiceManager.Stub;
import java.util.HashMap;

public class ServiceCfgActivity extends PreferenceActivity implements OnPreferenceChangeListener, OnPreferenceClickListener, ServiceConnection {

   private static final boolean SHOW_UNSUPPORTED_SVCS = false;
   public static final String TAG = "ServiceCfgActivity";
   private static final int[] mSvcDisplayTitleId;
   private static final int[] mSvcExtraSettingsId;
   private static final String[] mSvcNames;
   private HashMap<String, Preference> mExtraSettingsPrefMap;
   private Handler mHandler;
   private HashMap<String, CheckBoxPreference> mPrefMap;
   private final BroadcastReceiver mReceiver;
   private IServiceManager mSvcMgr;


   static {
      String[] var0 = new String[]{"bluetooth_opp_service", "bluetooth_pbap", "bluetooth_sap", "bluetooth_test"};
      mSvcNames = var0;
      mSvcExtraSettingsId = new int[]{-1, -1, -1, -1};
      mSvcDisplayTitleId = new int[]{2131232605, 2131232601, 2131232602, 2131232607};
   }

   public ServiceCfgActivity() {
      HashMap var1 = new HashMap();
      this.mPrefMap = var1;
      HashMap var2 = new HashMap();
      this.mExtraSettingsPrefMap = var2;
      Handler var3 = new Handler();
      this.mHandler = var3;
      ServiceCfgActivity.1 var4 = new ServiceCfgActivity.1();
      this.mReceiver = var4;
   }

   private void initCheckboxPrefUI(CheckBoxPreference var1) {
      var1.setOnPreferenceChangeListener(this);
      var1.setOnPreferenceClickListener(this);
      var1.setPersistent((boolean)0);
   }

   private void initPrefs() {
      PreferenceScreen var1 = this.getPreferenceScreen();
      int var2 = var1.getPreferenceCount();
      int var3 = mSvcNames.length;
      int var4;
      if(var2 == var3) {
         var4 = 0;

         while(true) {
            int var5 = mSvcNames.length;
            if(var4 >= var5) {
               return;
            }

            String var6 = mSvcNames[var4];
            if(this.isServiceSupported(var6)) {
               CheckBoxPreference var7 = (CheckBoxPreference)var1.getPreference(var4);
               this.updateServicePref(var7, var6);
            }

            ++var4;
         }
      } else {
         if(var2 > 0) {
            var1.removeAll();
         }

         var4 = 0;

         while(true) {
            int var8 = mSvcNames.length;
            if(var4 >= var8) {
               return;
            }

            String var9 = mSvcNames[var4];
            if(this.isServiceSupported(var9)) {
               CheckBoxPreference var10 = new CheckBoxPreference(this);
               int var11 = mSvcDisplayTitleId[var4];
               var10.setTitle(var11);
               var10.setKey(var9);
               var1.addPreference(var10);
               this.mPrefMap.put(var9, var10);
               this.initCheckboxPrefUI(var10);
               this.updateServicePref(var10, var9);
               if(mSvcExtraSettingsId[var4] != -1) {
                  Preference var14 = new Preference(this);
                  int var15 = mSvcExtraSettingsId[var4];
                  var14.setTitle(var15);
                  Intent var16 = new Intent("android.intent.action.MAIN", (Uri)null, this, ServiceExtraSettings.class);
                  var14.setIntent(var16);
                  var1.addPreference(var14);
                  this.mExtraSettingsPrefMap.put(var9, var14);
                  if(!var10.isChecked()) {
                     var14.setEnabled((boolean)0);
                  }
               }
            }

            ++var4;
         }
      }
   }

   private boolean isServiceSupported(String var1) {
      int var2;
      boolean var4;
      try {
         var2 = this.mSvcMgr.getServiceState(var1);
      } catch (Exception var8) {
         String var6 = "Unable to get service state for service " + var1;
         int var7 = Log.e("ServiceCfgActivity", var6, var8);
         var4 = false;
         return var4;
      }

      if(-1 != var2) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   private boolean setSvcState(Preference var1, String var2, boolean var3) {
      synchronized(this){}

      try {
         int var4 = Log.v("ServiceCfgActivity", "setSvcState");

         try {
            if(this.mSvcMgr != null) {
               Preference var5;
               if(var3) {
                  this.mSvcMgr.enableService(var2);
                  var5 = (Preference)this.mExtraSettingsPrefMap.get(var2);
                  if(var5 != null) {
                     var5.setEnabled((boolean)1);
                  }
               } else {
                  this.mSvcMgr.disableService(var2);
                  var5 = (Preference)this.mExtraSettingsPrefMap.get(var2);
                  if(var5 != null) {
                     var5.setEnabled((boolean)0);
                  }
               }
            }
         } catch (Exception var15) {
            String var7 = "ServiceCfgActivity";
            StringBuilder var8 = (new StringBuilder()).append("Error setting service ").append(var2).append(" to state ");
            String var9;
            if(var3) {
               var9 = "enabled";
            } else {
               var9 = "disabled";
            }

            String var10 = var8.append(var9).toString();
            Log.e(var7, var10);
         }
      } finally {
         ;
      }

      return false;
   }

   private void updateServicePref(CheckBoxPreference param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.addPreferencesFromResource(2130968585);
   }

   public void onDestroy() {
      super.onDestroy();
   }

   protected void onPause() {
      super.onPause();
      int var1 = Log.d("ServiceCfgActivity", "onPause()..Unbinding service...");

      try {
         BroadcastReceiver var2 = this.mReceiver;
         this.unregisterReceiver(var2);
      } catch (Exception var5) {
         int var4 = Log.e("ServiceCfgActivity", "Unable to unregister receiver", var5);
      }

      this.unbindService(this);
      super.onPause();
   }

   public boolean onPreferenceChange(Preference var1, Object var2) {
      int var3 = Log.v("ServiceCfgActivity", "onPreferenceChange");
      byte var4;
      if(var2 != null) {
         var4 = ((Boolean)var2).booleanValue();
      } else {
         var4 = 0;
      }

      String var5 = var1.getKey();
      boolean var6;
      if(var5 != null) {
         var6 = this.setSvcState(var1, var5, (boolean)var4);
      } else {
         var6 = false;
      }

      return var6;
   }

   public boolean onPreferenceClick(Preference var1) {
      return true;
   }

   protected void onResume() {
      super.onResume();
      Intent var1 = new Intent();
      Intent var2 = var1.setClassName("com.broadcom.bt.app.system", "com.broadcom.bt.app.system.ServiceManager");
      this.bindService(var1, this, 1);
   }

   public void onServiceConnected(ComponentName var1, IBinder var2) {
      int var3 = Log.v("ServiceCfgActivity", "onServiceConnected");
      IServiceManager var4 = Stub.asInterface(var2);
      this.mSvcMgr = var4;
      Handler var5 = this.mHandler;
      ServiceCfgActivity.2 var6 = new ServiceCfgActivity.2();
      var5.post(var6);
   }

   public void onServiceDisconnected(ComponentName var1) {}

   class 2 implements Runnable {

      2() {}

      public void run() {
         ServiceCfgActivity.this.initPrefs();
         ServiceCfgActivity var1 = ServiceCfgActivity.this;
         BroadcastReceiver var2 = ServiceCfgActivity.this.mReceiver;
         IntentFilter var3 = new IntentFilter("broadcom.bt.intent.action.BT_SVC_STATE_CHANGE");
         var1.registerReceiver(var2, var3);
      }
   }

   class 1 extends BroadcastReceiver {

      1() {}

      public void onReceive(Context var1, Intent var2) {
         int var3 = Log.v("ServiceCfgActivity", "onReceive");
         int var4 = var2.getIntExtra("bt_svc_state", -1);
         String var5 = var2.getStringExtra("bt_svc_name");
         CheckBoxPreference var6 = (CheckBoxPreference)ServiceCfgActivity.this.mPrefMap.get(var5);
         if(var6 != null) {
            switch(var4) {
            case 1:
               var6.setChecked((boolean)0);
               return;
            case 2:
               var6.setChecked((boolean)1);
               return;
            default:
            }
         }
      }
   }
}
