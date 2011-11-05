package com.android.settings.wifi;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceClickListener;
import android.text.Editable;
import android.widget.EditText;

public class WifiAPITest extends PreferenceActivity implements OnPreferenceClickListener {

   private static final String KEY_DISABLE_NETWORK = "disable_network";
   private static final String KEY_DISCONNECT = "disconnect";
   private static final String KEY_ENABLE_NETWORK = "enable_network";
   private static final String TAG = "WifiAPITest";
   private Preference mWifiDisableNetwork;
   private Preference mWifiDisconnect;
   private Preference mWifiEnableNetwork;
   private WifiManager mWifiManager;
   private int netid;


   public WifiAPITest() {}

   private void onCreatePreferences() {
      this.addPreferencesFromResource(2130903141);
      PreferenceScreen var1 = this.getPreferenceScreen();
      Preference var2 = var1.findPreference("disconnect");
      this.mWifiDisconnect = var2;
      this.mWifiDisconnect.setOnPreferenceClickListener(this);
      Preference var3 = var1.findPreference("disable_network");
      this.mWifiDisableNetwork = var3;
      this.mWifiDisableNetwork.setOnPreferenceClickListener(this);
      Preference var4 = var1.findPreference("enable_network");
      this.mWifiEnableNetwork = var4;
      this.mWifiEnableNetwork.setOnPreferenceClickListener(this);
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.onCreatePreferences();
      WifiManager var2 = (WifiManager)this.getSystemService("wifi");
      this.mWifiManager = var2;
   }

   public boolean onPreferenceClick(Preference var1) {
      Preference var2 = this.mWifiDisconnect;
      if(var1.equals(var2)) {
         boolean var3 = this.mWifiManager.disconnect();
      } else {
         Preference var4 = this.mWifiDisableNetwork;
         if(var1.equals(var4)) {
            Builder var5 = new Builder(this);
            Builder var6 = var5.setTitle("Input");
            Builder var7 = var5.setMessage("Enter Network ID");
            EditText var8 = new EditText(this);
            var5.setView(var8);
            WifiAPITest.1 var10 = new WifiAPITest.1(var8);
            var5.setPositiveButton("Ok", var10);
            WifiAPITest.2 var12 = new WifiAPITest.2();
            var5.setNegativeButton("Cancel", var12);
            AlertDialog var14 = var5.show();
         } else {
            Preference var15 = this.mWifiEnableNetwork;
            if(var1.equals(var15)) {
               Builder var16 = new Builder(this);
               Builder var17 = var16.setTitle("Input");
               Builder var18 = var16.setMessage("Enter Network ID");
               EditText var19 = new EditText(this);
               var16.setView(var19);
               WifiAPITest.3 var21 = new WifiAPITest.3(var19);
               var16.setPositiveButton("Ok", var21);
               WifiAPITest.4 var23 = new WifiAPITest.4();
               var16.setNegativeButton("Cancel", var23);
               AlertDialog var25 = var16.show();
            }
         }
      }

      return true;
   }

   public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2) {
      super.onPreferenceTreeClick(var1, var2);
      return false;
   }

   class 4 implements OnClickListener {

      4() {}

      public void onClick(DialogInterface var1, int var2) {}
   }

   class 3 implements OnClickListener {

      // $FF: synthetic field
      final EditText val$input;


      3(EditText var2) {
         this.val$input = var2;
      }

      public void onClick(DialogInterface var1, int var2) {
         Editable var3 = this.val$input.getText();
         WifiAPITest var4 = WifiAPITest.this;
         int var5 = Integer.parseInt(var3.toString());
         var4.netid = var5;
         WifiManager var7 = WifiAPITest.this.mWifiManager;
         int var8 = WifiAPITest.this.netid;
         var7.enableNetwork(var8, (boolean)0);
      }
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(DialogInterface var1, int var2) {}
   }

   class 1 implements OnClickListener {

      // $FF: synthetic field
      final EditText val$input;


      1(EditText var2) {
         this.val$input = var2;
      }

      public void onClick(DialogInterface var1, int var2) {
         Editable var3 = this.val$input.getText();
         WifiAPITest var4 = WifiAPITest.this;
         int var5 = Integer.parseInt(var3.toString());
         var4.netid = var5;
         WifiManager var7 = WifiAPITest.this.mWifiManager;
         int var8 = WifiAPITest.this.netid;
         var7.disableNetwork(var8);
      }
   }
}
