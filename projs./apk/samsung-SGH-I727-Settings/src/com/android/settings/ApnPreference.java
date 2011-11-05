package com.android.settings;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.preference.Preference;
import android.provider.Telephony.Carriers;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ApnPreference extends Preference implements OnCheckedChangeListener, OnClickListener {

   static final String TAG = "ApnPreference";
   static final int TN_KOR_CELOX_SKT = 1;
   static final int TN_KOR_P1_SKT = 1;
   static final int TN_KOR_P1_SKT_ROAMING = 2;
   static final int TN_KOR_U1_KT2 = 1043;
   private static CompoundButton mCurrentChecked = null;
   private static String mSelectedKey = null;
   private boolean mProtectFromCheckedChange = 0;
   private boolean mSelectable = 1;
   private WifiManager mWifiManager;


   public ApnPreference(Context var1) {
      super(var1);
      this.init();
   }

   public ApnPreference(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.init();
   }

   public ApnPreference(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.init();
   }

   private void init() {
      this.setLayoutResource(2130903040);
   }

   private boolean isTetheringEnabled(Context var1) {
      boolean var2 = false;
      String var4 = "connectivity";
      ConnectivityManager var5 = (ConnectivityManager)var1.getSystemService(var4);
      String[] var6 = var5.getTetheredIfaces();
      String[] var7 = var5.getTetherableUsbRegexs();
      String[] var8 = var6;
      int var9 = var6.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         String var11 = (String)var8[var10];
         String[] var12 = var7;
         int var13 = var7.length;

         for(int var14 = 0; var14 < var13; ++var14) {
            String var15 = var12[var14];
            if(var11.matches(var15)) {
               var2 = true;
            }
         }
      }

      return var2;
   }

   public boolean getSelectable() {
      return this.mSelectable;
   }

   public View getView(View var1, ViewGroup var2) {
      View var3 = super.getView(var1, var2);
      View var4 = var3.findViewById(16842753);
      if(var4 != null && var4 instanceof RadioButton) {
         RadioButton var5 = (RadioButton)var4;
         if(this.mSelectable) {
            var5.setOnCheckedChangeListener(this);
            String var6 = this.getKey();
            String var7 = mSelectedKey;
            boolean var8 = var6.equals(var7);
            if(var8) {
               mCurrentChecked = var5;
               mSelectedKey = this.getKey();
            }

            this.mProtectFromCheckedChange = (boolean)1;
            var5.setChecked(var8);
            this.mProtectFromCheckedChange = (boolean)0;
         } else {
            var5.setVisibility(8);
         }
      }

      View var9 = var3.findViewById(16842752);
      if(var9 != null && var9 instanceof RelativeLayout) {
         var9.setOnClickListener(this);
      }

      return var3;
   }

   public boolean isChecked() {
      String var1 = this.getKey();
      String var2 = mSelectedKey;
      return var1.equals(var2);
   }

   public void onCheckedChanged(CompoundButton var1, boolean var2) {
      StringBuilder var3 = (new StringBuilder()).append("ID: ");
      String var4 = this.getKey();
      String var5 = var3.append(var4).append(" :").append(var2).toString();
      int var6 = Log.i("ApnPreference", var5);
      if(!this.mProtectFromCheckedChange) {
         if(var2) {
            if(mCurrentChecked != null) {
               mCurrentChecked.setChecked((boolean)0);
            }

            mCurrentChecked = var1;
            mSelectedKey = this.getKey();
            String var7 = mSelectedKey;
            this.callChangeListener(var7);
         } else {
            mCurrentChecked = null;
            mSelectedKey = null;
         }
      }
   }

   public void onClick(View var1) {
      if(var1 != null) {
         int var2 = var1.getId();
         if(16842752 == var2) {
            Context var3 = this.getContext();
            if(var3 != null) {
               int var4 = Integer.parseInt(this.getKey());
               switch(var4) {
               default:
                  Uri var5 = Carriers.CONTENT_URI;
                  long var6 = (long)var4;
                  Uri var8 = ContentUris.withAppendedId(var5, var6);
                  Intent var9 = new Intent("android.intent.action.EDIT", var8);
                  var3.startActivity(var9);
               case 1:
               case 2:
               }
            }
         }
      }
   }

   public void setChecked() {
      mSelectedKey = this.getKey();
   }

   public void setSelectable(boolean var1) {
      this.mSelectable = var1;
   }
}
