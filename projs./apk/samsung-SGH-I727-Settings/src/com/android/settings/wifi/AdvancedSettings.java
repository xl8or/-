package com.android.settings.wifi;

import android.content.ContentResolver;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.SystemProperties;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings.System;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.android.settings.wifi.IwlanDialog;
import com.android.settings.wifi.IwlanEnabler;
import com.android.settings.wifi.IwlanNetwork;
import java.util.regex.Pattern;

public class AdvancedSettings extends PreferenceActivity implements OnPreferenceChangeListener {

   private static int DEBUGGABLE;
   private static final String IP_REGEX = "(([2][5][0-5]|[2][0-4][0-9]|[0-1][0-9][0-9]|[0-9][0-9]|[0-9])\\.){3}([2][5][0-5]|[2][0-4][0-9]|[0-1][0-9][0-9]|[0-9][0-9]|[0-9])";
   private static final String KEY_ADD_IWLAN_NETWORK = "add_other_iwlan";
   private static final String KEY_CURRENT_IP_ADDRESS = "current_ip_address";
   private static final String KEY_MAC_ADDRESS = "mac_address";
   private static final String KEY_MWLAN = "mwlan_network";
   private static final String KEY_MWLAN_CATEGORY = "mwlan_enabled_category";
   private static final String KEY_NUM_CHANNELS = "num_channels";
   private static final String KEY_PROXY_PORT = "wifi_http_port";
   private static final String KEY_SLEEP_POLICY = "sleep_policy";
   private static final String KEY_USE_STATIC_IP = "use_static_ip";
   private static final int MENU_ITEM_CANCEL = 2;
   private static final int MENU_ITEM_SAVE = 1;
   private Preference mAddIwlanNetwork;
   private IwlanDialog mIwlanDialog;
   private IwlanEnabler mIwlanEnabler;
   private String[] mPreferenceKeys;
   private IwlanNetwork mSelectedIwlanNetwork;
   private String[] mSettingNames;
   private CheckBoxPreference mUseStaticIpCheckBox;


   public AdvancedSettings() {
      String[] var1 = new String[]{"wifi_http_proxy", "wifi_static_ip", "wifi_static_gateway", "wifi_static_netmask", "wifi_static_dns1", "wifi_static_dns2"};
      this.mSettingNames = var1;
      String[] var2 = new String[]{"wifi_http_proxy", "ip_address", "gateway", "netmask", "dns1", "dns2"};
      this.mPreferenceKeys = var2;
   }

   private void initNumChannelsPreference() {
      ListPreference var1 = (ListPreference)this.findPreference("num_channels");
      var1.setOnPreferenceChangeListener(this);
      WifiManager var2 = (WifiManager)this.getSystemService("wifi");
      int[] var3 = var2.getValidChannelCounts();
      if(var3 == null) {
         Toast.makeText(this, 2131231146, 0).show();
         var1.setEnabled((boolean)0);
      } else {
         String[] var4 = new String[var3.length];
         String[] var5 = new String[var3.length];
         int var6 = 0;

         while(true) {
            int var7 = var3.length;
            if(var6 >= var7) {
               var1.setEntries(var4);
               var1.setEntryValues(var5);
               var1.setEnabled((boolean)1);
               int var12 = var2.getNumAllowedChannels();
               if(var12 < 0) {
                  return;
               } else {
                  String var13 = String.valueOf(var12);
                  var1.setValue(var13);
                  return;
               }
            }

            String var8 = String.valueOf(var3[var6]);
            var5[var6] = var8;
            Object[] var9 = new Object[1];
            Integer var10 = Integer.valueOf(var3[var6]);
            var9[0] = var10;
            String var11 = this.getString(2131231147, var9);
            var4[var6] = var11;
            ++var6;
         }
      }
   }

   private void initSleepPolicyPreference() {
      ListPreference var1 = (ListPreference)this.findPreference("sleep_policy");
      var1.setOnPreferenceChangeListener(this);
      String var2 = String.valueOf(System.getInt(this.getContentResolver(), "wifi_sleep_policy", 0));
      var1.setValue(var2);
   }

   private boolean isIpAddress(String var1) {
      byte var2;
      if(var1.length() == 0) {
         var2 = 1;
      } else {
         var2 = Pattern.compile("(([2][5][0-5]|[2][0-4][0-9]|[0-1][0-9][0-9]|[0-9][0-9]|[0-9])\\.){3}([2][5][0-5]|[2][0-4][0-9]|[0-1][0-9][0-9]|[0-9][0-9]|[0-9])").matcher(var1).matches();
      }

      return (boolean)var2;
   }

   private void refreshWifiInfo() {
      android.net.wifi.WifiInfo var1 = ((WifiManager)this.getSystemService("wifi")).getConnectionInfo();
      Preference var2 = this.findPreference("mac_address");
      String var3;
      if(var1 == null) {
         var3 = null;
      } else {
         var3 = var1.getMacAddress();
      }

      String var4;
      if(!TextUtils.isEmpty(var3)) {
         var4 = var3;
      } else {
         var4 = this.getString(2131231375);
      }

      var2.setSummary(var4);
      Preference var5 = this.findPreference("current_ip_address");
      String var6 = null;
      if(var1 != null) {
         long var7 = (long)var1.getIpAddress();
         if(var7 != 0L) {
            if(var7 < 0L) {
               var7 += 4294967296L;
            }

            Object[] var9 = new Object[4];
            Long var10 = Long.valueOf(255L & var7);
            var9[0] = var10;
            Long var11 = Long.valueOf(var7 >> 8 & 255L);
            var9[1] = var11;
            Long var12 = Long.valueOf(var7 >> 16 & 255L);
            var9[2] = var12;
            Long var13 = Long.valueOf(var7 >> 24 & 255L);
            var9[3] = var13;
            var6 = String.format("%d.%d.%d.%d", var9);
         }
      }

      String var14;
      if(var6 == null) {
         var14 = this.getString(2131231375);
      } else {
         var14 = var6;
      }

      var5.setSummary(var14);
   }

   private void showIwlanDialog(IwlanNetwork var1, boolean var2) {
      if(this.mIwlanDialog != null) {
         this.mIwlanDialog.dismiss();
      }

      IwlanDialog var3 = new IwlanDialog(this, var1, var2);
      this.mIwlanDialog = var3;
      this.mIwlanDialog.show();
   }

   private void updateSettingsProvider() {
      ContentResolver var1 = this.getContentResolver();
      byte var2;
      if(this.mUseStaticIpCheckBox.isChecked()) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      System.putInt(var1, "wifi_use_static_ip", var2);
      EditTextPreference var4 = (EditTextPreference)this.findPreference("wifi_http_port");
      ContentResolver var5 = this.getContentResolver();
      String var6 = var4.getText();
      System.putString(var5, "wifi_http_port", var6);
      int var8 = 0;

      while(true) {
         int var9 = this.mSettingNames.length;
         if(var8 >= var9) {
            return;
         }

         String var10 = this.mPreferenceKeys[var8];
         EditTextPreference var11 = (EditTextPreference)this.findPreference(var10);
         String var12 = this.mSettingNames[var8];
         String var13 = var11.getText();
         System.putString(var1, var12, var13);
         ++var8;
      }
   }

   private void updateUi() {
      ContentResolver var1 = this.getContentResolver();
      CheckBoxPreference var2 = this.mUseStaticIpCheckBox;
      byte var3;
      if(System.getInt(var1, "wifi_use_static_ip", 0) != 0) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      var2.setChecked((boolean)var3);
      EditTextPreference var4 = (EditTextPreference)this.findPreference("wifi_http_port");
      String var5 = System.getString(var1, "wifi_http_port");
      var4.setText(var5);
      var4.setSummary(var5);
      int var6 = 0;

      while(true) {
         int var7 = this.mSettingNames.length;
         if(var6 >= var7) {
            return;
         }

         String var8 = this.mPreferenceKeys[var6];
         EditTextPreference var9 = (EditTextPreference)this.findPreference(var8);
         String var10 = this.mSettingNames[var6];
         String var11 = System.getString(var1, var10);
         var9.setText(var11);
         var9.setSummary(var11);
         ++var6;
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.addPreferencesFromResource(2130968628);
      PreferenceScreen var2 = this.getPreferenceScreen();
      Preference var3 = this.findPreference("mwlan_enabled_category");
      var2.removePreference(var3);
      PreferenceScreen var5 = this.getPreferenceScreen();
      Preference var6 = this.findPreference("mwlan_network");
      var5.removePreference(var6);
      CheckBoxPreference var8 = (CheckBoxPreference)this.findPreference("use_static_ip");
      this.mUseStaticIpCheckBox = var8;
      this.mUseStaticIpCheckBox.setOnPreferenceChangeListener(this);
      Preference var9 = this.findPreference("wifi_http_port");
      if(var9 != null) {
         var9.setOnPreferenceChangeListener(this);
      }

      int var10 = 0;

      while(true) {
         int var11 = this.mPreferenceKeys.length;
         if(var10 >= var11) {
            DEBUGGABLE = SystemProperties.getInt("ro.debuggable", 0);
            PreferenceScreen var13 = this.getPreferenceScreen();
            Preference var14 = var13.findPreference("iwlan_enabled_category");
            if(var14 != null) {
               var13.removePreference(var14);
            }

            Preference var16 = var13.findPreference("iwlan_networks");
            if(var16 != null) {
               var13.removePreference(var16);
            }

            Preference var18 = var13.findPreference("add_other_iwlan");
            if(var18 != null) {
               var13.removePreference(var18);
            }

            if(DEBUGGABLE == 1) {
               this.initNumChannelsPreference();
               return;
            } else {
               Preference var20 = this.findPreference("num_channels");
               if(var20 == null) {
                  return;
               } else {
                  boolean var21 = this.getPreferenceScreen().removePreference(var20);
                  return;
               }
            }
         }

         String var12 = this.mPreferenceKeys[var10];
         this.findPreference(var12).setOnPreferenceChangeListener(this);
         ++var10;
      }
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      MenuItem var2 = var1.add(0, 1, 0, 2131231154).setIcon(17301582);
      MenuItem var3 = var1.add(0, 2, 0, 2131231155).setIcon(17301560);
      return super.onCreateOptionsMenu(var1);
   }

   protected void onDestroy() {
      super.onDestroy();
   }

   public boolean onKeyDown(int var1, KeyEvent var2) {
      if(var1 == 4) {
         this.updateSettingsProvider();
      }

      return super.onKeyDown(var1, var2);
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      boolean var2;
      switch(var1.getItemId()) {
      case 1:
         this.updateSettingsProvider();
         this.finish();
         var2 = true;
         break;
      case 2:
         this.finish();
         var2 = true;
         break;
      default:
         var2 = super.onOptionsItemSelected(var1);
      }

      return var2;
   }

   protected void onPause() {
      super.onPause();
   }

   public boolean onPreferenceChange(Preference param1, Object param2) {
      // $FF: Couldn't be decompiled
   }

   public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2) {
      if(var2 instanceof IwlanNetwork) {
         IwlanNetwork var3 = (IwlanNetwork)var2;
         this.mSelectedIwlanNetwork = var3;
         IwlanNetwork var4 = this.mSelectedIwlanNetwork;
         this.showIwlanDialog(var4, (boolean)0);
      } else {
         Preference var7 = this.mAddIwlanNetwork;
         if(var2.equals(var7)) {
            this.mSelectedIwlanNetwork = null;
            this.showIwlanDialog((IwlanNetwork)null, (boolean)1);
         } else {
            super.onPreferenceTreeClick(var1, var2);
         }
      }

      String var5 = var2.getKey();
      boolean var6;
      if("mwlan_network".equals(var5)) {
         var6 = false;
      } else {
         var6 = true;
      }

      return var6;
   }

   protected void onResume() {
      super.onResume();
      this.updateUi();
      if(DEBUGGABLE == 1) {
         this.initNumChannelsPreference();
      }

      this.initSleepPolicyPreference();
      this.refreshWifiInfo();
   }
}
