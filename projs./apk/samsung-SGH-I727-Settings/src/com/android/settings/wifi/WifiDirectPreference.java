package com.android.settings.wifi;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.preference.Preference;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.settings.wifi.Summary;

class WifiDirectPreference extends Preference {

   private static final String BSSID_ANY = "any";
   private static final int[] STATE_NONE = new int[0];
   private static final String TAG = "WifiDirectPreference";
   final String bssid;
   public int deviceType = 0;
   public boolean directMode;
   public int directStatus = 0;
   private WifiConfiguration mConfig;
   private ImageView mDirectIcon;
   private android.net.wifi.WifiInfo mInfo;
   private DetailedState mState;
   private TextView mTitleText;
   final String ssid;


   WifiDirectPreference(Context var1, ScanResult var2) {
      super(var1);
      this.setWidgetLayoutResource(2130903118);
      String var3 = var2.SSID;
      this.ssid = var3;
      String var4 = var2.BSSID;
      this.bssid = var4;
      int var5 = var2.primaryDev;
      this.deviceType = var5;
      int var6 = var2.status;
      this.directStatus = var6;
      boolean var7 = var2.capabilities.contains("DIRECT");
      this.directMode = var7;
   }

   static String convertToQuotedString(String var0) {
      return "\"" + var0 + "\"";
   }

   private static int getDeviceIconId(int var0) {
      int var1;
      switch(var0) {
      case 1:
         var1 = 2130837604;
         break;
      case 2:
         var1 = 2130837607;
         break;
      case 3:
         var1 = 2130837610;
         break;
      case 4:
         var1 = 2130837603;
         break;
      case 5:
         var1 = 2130837611;
         break;
      case 6:
         var1 = 2130837609;
         break;
      case 7:
         var1 = 2130837605;
         break;
      case 8:
         var1 = 2130837608;
         break;
      case 9:
         var1 = 2130837606;
         break;
      case 10:
      default:
         var1 = 2130837612;
         break;
      case 11:
         var1 = 2130837602;
      }

      return var1;
   }

   private void refresh() {
      Context var1 = this.getContext();
      if(this.directStatus == 1) {
         DetailedState var2 = DetailedState.CONNECTED;
         String var3 = Summary.get(var1, var2);
         this.setSummary(var3);
      } else {
         this.setSummary(2131231099);
      }
   }

   static String removeDoubleQuotes(String var0) {
      int var1 = var0.length();
      String var4;
      if(var1 > 1 && var0.charAt(0) == 34) {
         int var2 = var1 - 1;
         if(var0.charAt(var2) == 34) {
            int var3 = var1 - 1;
            var4 = var0.substring(1, var3);
            return var4;
         }
      }

      var4 = var0;
      return var4;
   }

   public int compareTo(Preference var1) {
      int var2;
      if(!(var1 instanceof WifiDirectPreference)) {
         var2 = 1;
      } else {
         WifiDirectPreference var3 = (WifiDirectPreference)var1;
         if(this.directStatus == 1) {
            var2 = -1;
         } else {
            String var4 = this.ssid;
            String var5 = var3.ssid;
            var2 = var4.compareToIgnoreCase(var5);
         }
      }

      return var2;
   }

   WifiConfiguration getConfig() {
      return this.mConfig;
   }

   android.net.wifi.WifiInfo getInfo() {
      return this.mInfo;
   }

   protected void onBindView(View var1) {
      TextView var2 = (TextView)var1.findViewById(16908310);
      this.mTitleText = var2;
      DetailedState var3 = this.mState;
      DetailedState var4 = DetailedState.CONNECTED;
      if((var3 == var4 || this.directStatus == 1) && this.mTitleText != null) {
         Resources var5 = this.getContext().getResources();
         int var6 = 2130968630;

         try {
            XmlResourceParser var7 = var5.getXml(var6);
            ColorStateList var8 = ColorStateList.createFromXml(var5, var7);
            this.mTitleText.setTextColor(var8);
            TextView var9 = this.mTitleText;
            Typeface var10 = Typeface.DEFAULT;
            var9.setTypeface(var10, 1);
         } catch (Exception var17) {
            int var16 = Log.e("WifiDirectPreference", "onBindView(), XmlResourceParser exception");
         }
      }

      String var11 = this.ssid;
      this.setTitle(var11);
      if(this.directMode) {
         ImageView var12 = (ImageView)var1.findViewById(2131427576);
         this.mDirectIcon = var12;
         ImageView var13 = this.mDirectIcon;
         int var14 = getDeviceIconId(this.deviceType);
         var13.setImageResource(var14);
      }

      this.refresh();
      super.onBindView(var1);
   }

   boolean update(ScanResult var1) {
      int var2 = var1.primaryDev;
      this.deviceType = var2;
      boolean var4;
      if(this.directStatus == 1) {
         DetailedState var3 = DetailedState.CONNECTED;
         this.mState = var3;
         this.refresh();
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }
}
