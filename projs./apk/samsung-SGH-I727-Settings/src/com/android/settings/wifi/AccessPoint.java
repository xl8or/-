package com.android.settings.wifi;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.preference.Preference;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.settings.wifi.Summary;

class AccessPoint extends Preference {

   private static final String BSSID_ANY = "any";
   static final int SECURITY_CCKM = 5;
   static final int SECURITY_DWEP = 4;
   static final int SECURITY_EAP = 3;
   static final int SECURITY_NONE = 0;
   static final int SECURITY_PSK = 2;
   static final int SECURITY_WEP = 1;
   private static final int[] STATE_NONE;
   private static final int[] STATE_SECURED;
   private static final String TAG = "AccessPoint";
   static int checkDialog;
   final String bssid;
   private WifiConfiguration mConfig;
   private android.net.wifi.WifiInfo mInfo;
   private int mRssi;
   private ImageView mSignal;
   private DetailedState mState;
   private TextView mSummaryText;
   private TextView mTitleText;
   private ImageView mWpsIcon;
   final int networkId;
   final int security;
   final String ssid;
   public boolean wpsPinMode;


   static {
      int[] var0 = new int[]{2130771968};
      STATE_SECURED = var0;
      STATE_NONE = new int[0];
      checkDialog = 0;
   }

   AccessPoint(Context var1, ScanResult var2) {
      super(var1);
      if(var1.getClass().getName().equals("com.android.settings.wifi.WifiSettings")) {
         this.setWidgetLayoutResource(2130903119);
         checkDialog = 0;
      } else {
         this.setWidgetLayoutResource(2130903119);
         checkDialog = 1;
      }

      String var3 = var2.SSID;
      this.ssid = var3;
      String var4 = var2.BSSID;
      this.bssid = var4;
      int var5 = getSecurity(var2);
      this.security = var5;
      this.networkId = -1;
      int var6 = var2.level;
      this.mRssi = var6;
      boolean var7 = var2.capabilities.contains("WPS");
      this.wpsPinMode = var7;
   }

   AccessPoint(Context var1, WifiConfiguration var2) {
      super(var1);
      if(var1.getClass().getName().equals("com.android.settings.wifi.WifiSettings")) {
         this.setWidgetLayoutResource(2130903119);
      } else {
         this.setWidgetLayoutResource(2130903119);
      }

      String var3;
      if(var2.SSID == null) {
         var3 = "";
      } else {
         var3 = removeDoubleQuotes(var2.SSID);
      }

      this.ssid = var3;
      String var4;
      if(var2.BSSID == null) {
         var4 = "any";
      } else {
         var4 = var2.BSSID;
      }

      this.bssid = var4;
      int var5 = getSecurity(var2);
      this.security = var5;
      int var6 = var2.networkId;
      this.networkId = var6;
      this.mConfig = var2;
      this.mRssi = Integer.MAX_VALUE;
   }

   static String convertToQuotedString(String var0) {
      return "\"" + var0 + "\"";
   }

   private static int getSecurity(ScanResult var0) {
      byte var1;
      if(var0.capabilities.contains("WEP")) {
         var1 = 1;
      } else if(var0.capabilities.contains("DWEP")) {
         var1 = 4;
      } else if(var0.capabilities.contains("PSK")) {
         var1 = 2;
      } else if(var0.capabilities.contains("EAP")) {
         var1 = 3;
      } else if(var0.capabilities.contains("CCKM")) {
         var1 = 5;
      } else {
         var1 = 0;
      }

      return var1;
   }

   static int getSecurity(WifiConfiguration var0) {
      byte var1;
      if(var0.allowedKeyManagement.get(1)) {
         var1 = 2;
      } else if(!var0.allowedKeyManagement.get(2) && !var0.allowedKeyManagement.get(3)) {
         if(var0.allowedKeyManagement.get(4)) {
            var1 = 5;
         } else if(var0.wepKeys[0] != false) {
            var1 = 1;
         } else {
            var1 = 0;
         }
      } else {
         var1 = 3;
      }

      return var1;
   }

   private void refresh() {
      if(this.mSignal != null) {
         Context var1 = this.getContext();
         ImageView var2 = this.mSignal;
         int var3 = this.getLevel();
         var2.setImageLevel(var3);
         if(this.mState != null) {
            DetailedState var4 = this.mState;
            String var5 = Summary.get(var1, var4);
            this.setSummary(var5);
         } else {
            String var6 = null;
            if(this.mRssi == Integer.MAX_VALUE) {
               var6 = var1.getString(2131231130);
            } else if(this.mConfig != null) {
               int var7;
               if(this.mConfig.status == 1) {
                  var7 = 2131231129;
               } else {
                  var7 = 2131231128;
               }

               var6 = var1.getString(var7);
            }

            if(this.security == 0) {
               this.setSummary(var6);
            } else {
               int var8;
               if(var6 == null) {
                  var8 = 2131231131;
               } else {
                  var8 = 2131231132;
               }

               String var9 = var1.getString(var8);
               String[] var10 = var1.getResources().getStringArray(2131034135);
               Object[] var11 = new Object[2];
               int var12 = this.security;
               String var13 = var10[var12];
               var11[0] = var13;
               var11[1] = var6;
               String var14 = String.format(var9, var11);
               this.setSummary(var14);
            }
         }
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
      if(!(var1 instanceof AccessPoint)) {
         var2 = 1;
      } else {
         AccessPoint var3 = (AccessPoint)var1;
         android.net.wifi.WifiInfo var4 = this.mInfo;
         android.net.wifi.WifiInfo var5 = var3.mInfo;
         if(var4 != var5) {
            if(this.mInfo != null) {
               var2 = -1;
            } else {
               var2 = 1;
            }
         } else {
            int var6 = this.mRssi;
            int var7 = var3.mRssi;
            if((var6 ^ var7) < 0) {
               if(this.mRssi != Integer.MAX_VALUE) {
                  var2 = -1;
               } else {
                  var2 = 1;
               }
            } else {
               int var8 = this.networkId;
               int var9 = var3.networkId;
               if((var8 ^ var9) < 0) {
                  if(this.networkId != -1) {
                     var2 = -1;
                  } else {
                     var2 = 1;
                  }
               } else {
                  String var10 = this.ssid;
                  String var11 = var3.ssid;
                  var2 = var10.compareToIgnoreCase(var11);
               }
            }
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

   int getLevel() {
      int var1;
      if(this.mRssi == Integer.MAX_VALUE) {
         var1 = -1;
      } else {
         var1 = WifiManager.calculateSignalLevel(this.mRssi, 4);
      }

      return var1;
   }

   DetailedState getState() {
      return this.mState;
   }

   protected void onBindView(View var1) {
      TextView var2 = (TextView)var1.findViewById(16908310);
      this.mTitleText = var2;
      TextView var3 = (TextView)var1.findViewById(16908304);
      this.mSummaryText = var3;
      if(checkDialog == 1) {
         TextView var4 = this.mSummaryText;
         ColorStateList var5 = this.getContext().getResources().getColorStateList(2131165190);
         var4.setTextColor(var5);
      }

      DetailedState var6 = this.mState;
      DetailedState var7 = DetailedState.CONNECTED;
      if(var6 == var7 && this.mTitleText != null) {
         Resources var8 = this.getContext().getResources();
         int var9 = 2130968630;

         try {
            XmlResourceParser var10 = var8.getXml(var9);
            ColorStateList var11 = ColorStateList.createFromXml(var8, var10);
            this.mTitleText.setTextColor(var11);
            TextView var12 = this.mTitleText;
            Typeface var13 = Typeface.DEFAULT;
            var12.setTypeface(var13, 1);
         } catch (Exception var21) {
            int var18 = Log.e("AccessPoint", "onBindView(), XmlResourceParser exception");
         }
      }

      String var14 = this.ssid;
      this.setTitle(var14);
      ImageView var15 = (ImageView)var1.findViewById(2131427578);
      this.mSignal = var15;
      ImageView var16 = (ImageView)var1.findViewById(2131427577);
      this.mWpsIcon = var16;
      if(this.wpsPinMode) {
         this.mWpsIcon.setImageResource(2130837621);
      }

      if(this.mRssi == Integer.MAX_VALUE) {
         this.mSignal.setImageDrawable((Drawable)null);
         this.mWpsIcon.setImageDrawable((Drawable)null);
      } else {
         this.mSignal.setImageResource(2130837700);
         ImageView var19 = this.mSignal;
         int[] var20;
         if(this.security != 0) {
            var20 = STATE_SECURED;
         } else {
            var20 = STATE_NONE;
         }

         var19.setImageState(var20, (boolean)1);
      }

      this.refresh();
      super.onBindView(var1);
   }

   void update(android.net.wifi.WifiInfo var1, DetailedState var2) {
      boolean var3;
      label25: {
         var3 = false;
         if(var1 != null && this.networkId != -1) {
            int var4 = this.networkId;
            int var5 = var1.getNetworkId();
            if(var4 == var5) {
               if(this.mInfo == null) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               int var6 = var1.getRssi();
               this.mRssi = var6;
               this.mInfo = var1;
               this.mState = var2;
               this.refresh();
               break label25;
            }
         }

         if(this.mInfo != null) {
            var3 = true;
            this.mInfo = null;
            this.mState = null;
            this.refresh();
         }
      }

      if(var3) {
         this.notifyHierarchyChanged();
      }
   }

   boolean update(ScanResult var1) {
      String var2 = this.ssid;
      String var3 = var1.SSID;
      boolean var10;
      if(var2.equals(var3)) {
         int var4 = this.security;
         int var5 = getSecurity(var1);
         if(var4 == var5) {
            boolean var6 = var1.capabilities.contains("WPS");
            this.wpsPinMode = var6;
            int var7 = var1.level;
            int var8 = this.mRssi;
            if(WifiManager.compareSignalLevel(var7, var8) > 0) {
               int var9 = var1.level;
               this.mRssi = var9;
            }

            var10 = true;
            return var10;
         }
      }

      var10 = false;
      return var10;
   }
}
