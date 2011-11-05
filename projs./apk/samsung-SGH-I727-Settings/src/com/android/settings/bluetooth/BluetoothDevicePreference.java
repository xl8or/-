package com.android.settings.bluetooth;

import android.content.Context;
import android.graphics.Typeface;
import android.preference.Preference;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.settings.bluetooth.CachedBluetoothDevice;

public class BluetoothDevicePreference extends Preference implements CachedBluetoothDevice.Callback {

   private static final String TAG = "BluetoothDevicePreference";
   private static int sDimAlpha = Integer.MIN_VALUE;
   private CachedBluetoothDevice mCachedDevice;
   private boolean mIsBusy;


   public BluetoothDevicePreference(Context var1, CachedBluetoothDevice var2) {
      super(var1);
      if(sDimAlpha == Integer.MIN_VALUE) {
         TypedValue var3 = new TypedValue();
         boolean var4 = var1.getTheme().resolveAttribute(16842803, var3, (boolean)1);
         sDimAlpha = (int)(var3.getFloat() * 255.0F);
      }

      this.mCachedDevice = var2;
      this.setLayoutResource(2130903108);
      var2.registerCallback(this);
      this.onDeviceAttributesChanged(var2);
   }

   public int compareTo(Preference var1) {
      int var2;
      if(!(var1 instanceof BluetoothDevicePreference)) {
         var2 = 1;
      } else {
         CachedBluetoothDevice var3 = this.mCachedDevice;
         CachedBluetoothDevice var4 = ((BluetoothDevicePreference)var1).mCachedDevice;
         var2 = var3.compareTo(var4);
      }

      return var2;
   }

   public CachedBluetoothDevice getCachedDevice() {
      return this.mCachedDevice;
   }

   public boolean isEnabled() {
      this.setEnabled((boolean)1);
      boolean var1;
      if(super.isEnabled() && !this.mIsBusy) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   protected void onBindView(View var1) {
      if(this.findPreferenceInHierarchy("bt_checkbox") != null) {
         this.setDependency("bt_checkbox");
      }

      super.onBindView(var1);
      if(this.mCachedDevice.isConnected() == 1) {
         TextView var2 = (TextView)var1.findViewById(16908310);
         Typeface var3 = Typeface.DEFAULT;
         var2.setTypeface(var3, 1);
         var2.setTextColor(-16737793);
      }

      ImageView var4 = (ImageView)var1.findViewById(2131427555);
      int var5 = this.mCachedDevice.getBtClassDrawable();
      var4.setImageResource(var5);
      int var6;
      if(this.isEnabled()) {
         var6 = 255;
      } else {
         var6 = sDimAlpha;
      }

      var4.setAlpha(var6);
      ImageView var7 = (ImageView)var1.findViewById(2131427556);
      int var8 = this.mCachedDevice.getBtConnectStateDrawable();
      var7.setImageResource(var8);
      int var9;
      if(this.isEnabled()) {
         var9 = 255;
      } else {
         var9 = sDimAlpha;
      }

      var7.setAlpha(var9);
   }

   public void onDeviceAttributesChanged(CachedBluetoothDevice var1) {
      String var2 = this.mCachedDevice.getName();
      this.setTitle(var2);
      int var3 = this.mCachedDevice.getSummary();
      this.setSummary(var3);
      boolean var4 = this.mCachedDevice.isBusy();
      this.mIsBusy = var4;
      this.notifyChanged();
      this.notifyHierarchyChanged();
   }

   protected void onPrepareForRemoval() {
      super.onPrepareForRemoval();
      this.mCachedDevice.unregisterCallback(this);
   }
}
