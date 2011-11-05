package com.android.settings.bluetooth;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import com.android.settings.bluetooth.CachedBluetoothDevice;
import com.android.settings.bluetooth.LocalBluetoothManager;
import com.android.settings.bluetooth.LocalBluetoothProfileManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CachedBluetoothDeviceManager {

   private static final String TAG = "CachedBluetoothDeviceManager";
   final List<CachedBluetoothDevice> mCachedDevices;
   final List<LocalBluetoothManager.Callback> mCallbacks;
   final LocalBluetoothManager mLocalManager;


   public CachedBluetoothDeviceManager(LocalBluetoothManager var1) {
      ArrayList var2 = new ArrayList();
      this.mCachedDevices = var2;
      this.mLocalManager = var1;
      List var3 = var1.getCallbacks();
      this.mCallbacks = var3;
   }

   private void checkForDeviceRemoval(CachedBluetoothDevice var1) {
      if(var1.getBondState() == 10) {
         if(!var1.isVisible()) {
            this.mCachedDevices.remove(var1);
            this.dispatchDeviceDeleted(var1);
         }
      }
   }

   private void dispatchDeviceAdded(CachedBluetoothDevice var1) {
      List var2 = this.mCallbacks;
      synchronized(var2) {
         Iterator var3 = this.mCallbacks.iterator();

         while(var3.hasNext()) {
            ((LocalBluetoothManager.Callback)var3.next()).onDeviceAdded(var1);
         }

      }
   }

   private void dispatchDeviceDeleted(CachedBluetoothDevice var1) {
      List var2 = this.mCallbacks;
      synchronized(var2) {
         Iterator var3 = this.mCallbacks.iterator();

         while(var3.hasNext()) {
            ((LocalBluetoothManager.Callback)var3.next()).onDeviceDeleted(var1);
         }

      }
   }

   private boolean readPairedDevices() {
      // $FF: Couldn't be decompiled
   }

   public CachedBluetoothDevice findDevice(BluetoothDevice param1) {
      // $FF: Couldn't be decompiled
   }

   public List<CachedBluetoothDevice> getCachedDevicesCopy() {
      synchronized(this){}

      ArrayList var2;
      try {
         List var1 = this.mCachedDevices;
         var2 = new ArrayList(var1);
      } finally {
         ;
      }

      return var2;
   }

   public String getName(BluetoothDevice var1) {
      CachedBluetoothDevice var2 = this.findDevice(var1);
      String var3;
      if(var2 != null) {
         var3 = var2.getName();
      } else {
         String var4 = var1.getName();
         if(var4 != null) {
            var3 = var4;
         } else {
            var3 = var1.getAddress();
         }
      }

      return var3;
   }

   public boolean isPairedHeadsetOrA2DPExist() {
      boolean var1 = false;
      if(this.mCachedDevices.size() > 0) {
         Iterator var2 = this.mCachedDevices.iterator();

         while(var2.hasNext()) {
            CachedBluetoothDevice var3 = (CachedBluetoothDevice)var2.next();
            if(var3.getBondState() == 12) {
               var1 = var3.isContainsA2DPOrHFProfiles();
               if(var1) {
                  break;
               }
            }
         }
      }

      return var1;
   }

   void onBluetoothStateChanged(boolean var1) {
      if(var1) {
         boolean var2 = this.readPairedDevices();
      }
   }

   public void onBondingError(BluetoothDevice param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public void onBondingStateChanged(BluetoothDevice var1, int var2) {
      synchronized(this){}

      try {
         CachedBluetoothDevice var3 = this.findDevice(var1);
         if(var3 == null) {
            if(!this.readPairedDevices()) {
               String var4 = "Got bonding state changed for " + var1 + ", but we have no record of that device.";
               int var5 = Log.e("CachedBluetoothDeviceManager", var4);
               return;
            }

            var3 = this.findDevice(var1);
            if(var3 == null) {
               String var6 = "Got bonding state changed for " + var1 + "but device not added in cache";
               int var7 = Log.e("CachedBluetoothDeviceManager", var6);
               return;
            }
         }

         var3.onBondingStateChanged(var2);
      } finally {
         ;
      }

   }

   public void onBtClassChanged(BluetoothDevice var1) {
      synchronized(this){}

      try {
         CachedBluetoothDevice var2 = this.findDevice(var1);
         if(var2 != null) {
            var2.refreshBtClass();
         }
      } finally {
         ;
      }

   }

   public void onConnectingError(BluetoothDevice param1) {
      // $FF: Couldn't be decompiled
   }

   public void onDeviceAppeared(BluetoothDevice var1, int var2, BluetoothClass var3, String var4) {
      synchronized(this){}
      boolean var5 = false;

      try {
         CachedBluetoothDevice var6 = this.findDevice(var1);
         if(var6 == null) {
            Context var7 = this.mLocalManager.getContext();
            var6 = new CachedBluetoothDevice(var7, var1);
            this.mCachedDevices.add(var6);
            var5 = true;
         }

         var6.setRssi(var2);
         var6.setBtClass(var3);
         var6.setName(var4);
         var6.setVisible((boolean)1);
         if(var5) {
            this.dispatchDeviceAdded(var6);
         }
      } finally {
         ;
      }

   }

   public void onDeviceDisappeared(BluetoothDevice param1) {
      // $FF: Couldn't be decompiled
   }

   public void onDeviceNameUpdated(BluetoothDevice var1) {
      synchronized(this){}

      try {
         CachedBluetoothDevice var2 = this.findDevice(var1);
         if(var2 != null) {
            var2.refreshName();
         }
      } finally {
         ;
      }

   }

   public void onProfileStateChanged(BluetoothDevice param1, LocalBluetoothProfileManager.Profile param2, int param3) {
      // $FF: Couldn't be decompiled
   }

   public void onScanningStateChanged(boolean param1) {
      // $FF: Couldn't be decompiled
   }

   public void onUuidChanged(BluetoothDevice var1) {
      synchronized(this){}

      try {
         CachedBluetoothDevice var2 = this.findDevice(var1);
         if(var2 != null) {
            var2.onUuidChanged();
         }
      } finally {
         ;
      }

   }

   public void showUnbondMessage(BluetoothDevice param1, int param2) {
      // $FF: Couldn't be decompiled
   }
}
