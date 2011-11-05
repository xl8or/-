package com.android.settings.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.preference.Preference.OnPreferenceChangeListener;
import android.text.TextUtils;
import android.util.Log;
import com.android.settings.bluetooth.CachedBluetoothDevice;
import com.android.settings.bluetooth.LocalBluetoothManager;
import com.android.settings.bluetooth.LocalBluetoothProfileManager;
import java.util.Iterator;

public class ConnectSpecificProfilesActivity extends PreferenceActivity implements CachedBluetoothDevice.Callback, OnPreferenceChangeListener {

   public static final String EXTRA_DEVICE = "device";
   private static final String KEY_ONLINE_MODE = "online_mode";
   private static final String KEY_PROFILE_CONTAINER = "profile_container";
   private static final String KEY_TITLE = "title";
   private static final String TAG = "ConnectSpecificProfilesActivity";
   private CachedBluetoothDevice mCachedDevice;
   private LocalBluetoothManager mManager;
   private boolean mOnlineMode;
   private CheckBoxPreference mOnlineModePreference;
   private PreferenceGroup mProfileContainer;


   public ConnectSpecificProfilesActivity() {}

   private void addPreferencesForProfiles() {
      Iterator var1 = this.mCachedDevice.getConnectableProfiles().iterator();

      while(var1.hasNext()) {
         LocalBluetoothProfileManager.Profile var2 = (LocalBluetoothProfileManager.Profile)var1.next();
         CheckBoxPreference var3 = this.createProfilePreference(var2);
         this.mProfileContainer.addPreference(var3);
      }

   }

   private CheckBoxPreference createProfilePreference(LocalBluetoothProfileManager.Profile var1) {
      CheckBoxPreference var2 = new CheckBoxPreference(this);
      String var3 = var1.toString();
      var2.setKey(var3);
      int var4 = var1.localizedString;
      var2.setTitle(var4);
      var2.setPersistent((boolean)0);
      var2.setOnPreferenceChangeListener(this);
      LocalBluetoothProfileManager.getProfileManager(this.mManager, var1);
      byte var6;
      if(!this.mCachedDevice.isBusy()) {
         var6 = 1;
      } else {
         var6 = 0;
      }

      var2.setEnabled((boolean)var6);
      this.refreshProfilePreference(var2, var1);
      return var2;
   }

   private LocalBluetoothProfileManager.Profile getProfileOf(Preference var1) {
      LocalBluetoothProfileManager.Profile var2;
      if(!(var1 instanceof CheckBoxPreference)) {
         var2 = null;
      } else if(TextUtils.isEmpty(var1.getKey())) {
         var2 = null;
      } else {
         LocalBluetoothProfileManager.Profile var3;
         try {
            var3 = LocalBluetoothProfileManager.Profile.valueOf(var1.getKey());
         } catch (IllegalArgumentException var5) {
            var2 = null;
            return var2;
         }

         var2 = var3;
      }

      return var2;
   }

   private static int getProfileSummary(LocalBluetoothProfileManager var0, LocalBluetoothProfileManager.Profile var1, BluetoothDevice var2, int var3, boolean var4) {
      int var5;
      if(var4 && var3 != 4) {
         var5 = var0.getSummary(var2);
      } else {
         var5 = getProfileSummaryForSettingPreference(var1);
      }

      return var5;
   }

   private static final int getProfileSummaryForSettingPreference(LocalBluetoothProfileManager.Profile var0) {
      int[] var1 = ConnectSpecificProfilesActivity.1.$SwitchMap$com$android$settings$bluetooth$LocalBluetoothProfileManager$Profile;
      int var2 = var0.ordinal();
      int var3;
      switch(var1[var2]) {
      case 1:
         var3 = 2131231046;
         break;
      case 2:
         var3 = 2131231047;
         break;
      case 3:
         var3 = 2131232617;
         break;
      default:
         var3 = 0;
      }

      return var3;
   }

   private void onOnlineModeCheckedStateChanged(boolean var1) {
      this.setOnlineMode(var1, (boolean)1);
   }

   private void onProfileCheckedStateChanged(LocalBluetoothProfileManager.Profile var1, boolean var2) {
      LocalBluetoothProfileManager var3 = LocalBluetoothProfileManager.getProfileManager(this.mManager, var1);
      BluetoothDevice var4 = this.mCachedDevice.getDevice();
      var3.setPreferred(var4, var2);
      if(this.mOnlineMode) {
         if(var2) {
            if(this.mCachedDevice.isHid() && this.mCachedDevice.getDevice().getBondState() == 10) {
               this.mCachedDevice.pair();
            } else {
               this.mCachedDevice.connect(var1);
            }
         } else if(this.mCachedDevice.isHid()) {
            this.mCachedDevice.unpair();
         } else {
            this.mCachedDevice.disconnect(var1);
         }
      }
   }

   private void refresh() {
      byte var1;
      if(!this.mCachedDevice.isConnected() && !this.mCachedDevice.isBusy()) {
         var1 = 0;
      } else {
         var1 = 1;
      }

      this.setOnlineMode((boolean)var1, (boolean)0);
      this.refreshProfiles();
   }

   private void refreshOnlineModePreference() {
      CheckBoxPreference var1 = this.mOnlineModePreference;
      boolean var2 = this.mOnlineMode;
      var1.setChecked(var2);
      if(this.mCachedDevice.isHid() && !this.mOnlineMode && this.mCachedDevice.getBondState() != 10 && !this.mCachedDevice.shouldHostConnect()) {
         this.mOnlineModePreference.setEnabled((boolean)0);
      } else {
         CheckBoxPreference var5 = this.mOnlineModePreference;
         byte var6;
         if(!this.mCachedDevice.isBusy()) {
            var6 = 1;
         } else {
            var6 = 0;
         }

         var5.setEnabled((boolean)var6);
      }

      CheckBoxPreference var3;
      int var4;
      if(this.mCachedDevice.isHid()) {
         var3 = this.mOnlineModePreference;
         if(this.mOnlineMode) {
            var4 = this.mCachedDevice.getSummary();
         } else {
            var4 = 2131232621;
         }

         var3.setSummary(var4);
      } else {
         var3 = this.mOnlineModePreference;
         if(this.mOnlineMode) {
            var4 = this.mCachedDevice.getSummary();
         } else {
            var4 = 2131231040;
         }

         var3.setSummary(var4);
      }
   }

   private void refreshProfilePreference(CheckBoxPreference var1, LocalBluetoothProfileManager.Profile var2) {
      BluetoothDevice var3 = this.mCachedDevice.getDevice();
      LocalBluetoothProfileManager var4 = LocalBluetoothProfileManager.getProfileManager(this.mManager, var2);
      int var5 = var4.getConnectionStatus(var3);
      byte var6;
      if(!this.mCachedDevice.isBusy()) {
         var6 = 1;
      } else {
         var6 = 0;
      }

      var1.setEnabled((boolean)var6);
      boolean var7 = this.mOnlineMode;
      int var8 = getProfileSummary(var4, var2, var3, var5, var7);
      var1.setSummary(var8);
      boolean var9 = var4.isPreferred(var3);
      var1.setChecked(var9);
   }

   private void refreshProfiles() {
      Iterator var1 = this.mCachedDevice.getConnectableProfiles().iterator();

      while(var1.hasNext()) {
         LocalBluetoothProfileManager.Profile var2 = (LocalBluetoothProfileManager.Profile)var1.next();
         String var3 = var2.toString();
         CheckBoxPreference var4 = (CheckBoxPreference)this.findPreference(var3);
         if(var4 == null) {
            CheckBoxPreference var5 = this.createProfilePreference(var2);
            this.mProfileContainer.addPreference(var5);
         } else {
            this.refreshProfilePreference(var4, var2);
         }
      }

   }

   private void setOnlineMode(boolean var1, boolean var2) {
      this.mOnlineMode = var1;
      if(var2) {
         if(var1) {
            if(this.mCachedDevice.isHid()) {
               if(this.mCachedDevice.getBondState() == 10) {
                  this.mCachedDevice.pair();
               } else {
                  this.mCachedDevice.connect();
               }
            } else {
               this.mCachedDevice.connect();
            }
         } else if(this.mCachedDevice.isHid()) {
            this.mCachedDevice.unpair();
         } else {
            this.mCachedDevice.disconnect();
         }
      }

      this.refreshOnlineModePreference();
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      BluetoothDevice var2;
      if(var1 != null) {
         var2 = (BluetoothDevice)var1.getParcelable("device");
      } else {
         var2 = (BluetoothDevice)this.getIntent().getParcelableExtra("device");
      }

      if(var2 == null) {
         int var3 = Log.w("ConnectSpecificProfilesActivity", "Activity started without a remote Bluetooth device");
         this.finish();
      } else {
         LocalBluetoothManager var4 = LocalBluetoothManager.getInstance(this);
         this.mManager = var4;
         CachedBluetoothDevice var5 = this.mManager.getCachedDeviceManager().findDevice(var2);
         this.mCachedDevice = var5;
         if(this.mCachedDevice == null) {
            int var6 = Log.w("ConnectSpecificProfilesActivity", "Device not found, cannot connect to it");
            this.finish();
         } else {
            this.addPreferencesFromResource(2130968583);
            PreferenceGroup var7 = (PreferenceGroup)this.findPreference("profile_container");
            this.mProfileContainer = var7;
            Preference var8 = this.findPreference("title");
            Object[] var9 = new Object[1];
            String var10 = this.mCachedDevice.getName();
            var9[0] = var10;
            String var11 = this.getString(2131231038, var9);
            var8.setTitle(var11);
            CheckBoxPreference var12 = (CheckBoxPreference)this.findPreference("online_mode");
            this.mOnlineModePreference = var12;
            this.mOnlineModePreference.setOnPreferenceChangeListener(this);
            this.addPreferencesForProfiles();
         }
      }
   }

   public void onDeviceAttributesChanged(CachedBluetoothDevice var1) {
      this.refresh();
   }

   protected void onPause() {
      super.onPause();
      this.mCachedDevice.unregisterCallback(this);
      this.mManager.setForegroundActivity((Activity)null);
   }

   public boolean onPreferenceChange(Preference var1, Object var2) {
      String var3 = var1.getKey();
      boolean var4;
      if(!TextUtils.isEmpty(var3) && var2 != null) {
         if(var3.equals("online_mode")) {
            boolean var5 = ((Boolean)var2).booleanValue();
            this.onOnlineModeCheckedStateChanged(var5);
         } else {
            LocalBluetoothProfileManager.Profile var6 = this.getProfileOf(var1);
            if(var6 == null) {
               var4 = false;
               return var4;
            }

            boolean var7 = ((Boolean)var2).booleanValue();
            this.onProfileCheckedStateChanged(var6, var7);
         }

         var4 = true;
      } else {
         var4 = true;
      }

      return var4;
   }

   protected void onResume() {
      super.onResume();
      this.mManager.setForegroundActivity(this);
      this.mCachedDevice.registerCallback(this);
      this.refresh();
   }

   protected void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      BluetoothDevice var2 = this.mCachedDevice.getDevice();
      var1.putParcelable("device", var2);
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$android$settings$bluetooth$LocalBluetoothProfileManager$Profile = new int[LocalBluetoothProfileManager.Profile.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$android$settings$bluetooth$LocalBluetoothProfileManager$Profile;
            int var1 = LocalBluetoothProfileManager.Profile.A2DP.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var11) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$android$settings$bluetooth$LocalBluetoothProfileManager$Profile;
            int var3 = LocalBluetoothProfileManager.Profile.HEADSET.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var10) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$android$settings$bluetooth$LocalBluetoothProfileManager$Profile;
            int var5 = LocalBluetoothProfileManager.Profile.HID.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var9) {
            ;
         }
      }
   }
}
