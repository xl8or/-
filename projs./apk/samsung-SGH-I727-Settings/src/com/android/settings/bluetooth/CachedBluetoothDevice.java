package com.android.settings.bluetooth;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.ParcelUuid;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.widget.Toast;
import com.android.settings.bluetooth.CachedBluetoothDeviceManager;
import com.android.settings.bluetooth.ConnectSpecificProfilesActivity;
import com.android.settings.bluetooth.LocalBluetoothManager;
import com.android.settings.bluetooth.LocalBluetoothProfileManager;
import com.android.settings.bluetooth.SettingsBtStatus;
import com.broadcom.bt.app.settings.DiscoverServicesActivity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CachedBluetoothDevice implements Comparable<CachedBluetoothDevice> {

   private static final int CONTEXT_ITEM_CONNECT = 2;
   private static final int CONTEXT_ITEM_CONNECT_ADVANCED = 5;
   private static final int CONTEXT_ITEM_DISCONNECT = 3;
   private static final int CONTEXT_ITEM_GET_SERVICES = 6;
   private static final int CONTEXT_ITEM_UNPAIR = 4;
   private static final boolean D = true;
   private static final boolean DEBUG = true;
   private static final String TAG = "CachedBluetoothDevice";
   private static final boolean V = true;
   private BluetoothClass mBtClass;
   private int mBtClassDrawable = 2130837542;
   private List<CachedBluetoothDevice.Callback> mCallbacks;
   private boolean mConnectAfterPairing;
   private Context mContext;
   private final BluetoothDevice mDevice;
   private AlertDialog mDialog;
   private boolean mIsConnecting;
   private boolean mIsConnectingErrorPossible;
   private boolean mIsPairing;
   private boolean mIsUnpairing;
   private final LocalBluetoothManager mLocalManager;
   private String mName;
   private List<LocalBluetoothProfileManager.Profile> mProfiles;
   private final BroadcastReceiver mReceiver;
   private int mRssi;
   private boolean mUpdateBtClassDrawable = 1;
   private boolean mVisible;


   CachedBluetoothDevice(Context var1, BluetoothDevice var2) {
      ArrayList var3 = new ArrayList();
      this.mProfiles = var3;
      this.mDialog = null;
      ArrayList var4 = new ArrayList();
      this.mCallbacks = var4;
      this.mIsPairing = (boolean)0;
      this.mIsUnpairing = (boolean)0;
      CachedBluetoothDevice.1 var5 = new CachedBluetoothDevice.1();
      this.mReceiver = var5;
      this.mConnectAfterPairing = (boolean)0;
      this.mIsConnecting = (boolean)0;
      this.mContext = var1;
      LocalBluetoothManager var6 = LocalBluetoothManager.getInstance(var1);
      this.mLocalManager = var6;
      if(this.mLocalManager == null) {
         throw new IllegalStateException("Cannot use CachedBluetoothDevice without Bluetooth hardware");
      } else {
         this.mDevice = var2;
         this.fillData();
      }
   }

   CachedBluetoothDevice(Context var1, CachedBluetoothDevice var2) {
      ArrayList var3 = new ArrayList();
      this.mProfiles = var3;
      this.mDialog = null;
      ArrayList var4 = new ArrayList();
      this.mCallbacks = var4;
      this.mIsPairing = (boolean)0;
      this.mIsUnpairing = (boolean)0;
      CachedBluetoothDevice.1 var5 = new CachedBluetoothDevice.1();
      this.mReceiver = var5;
      this.mConnectAfterPairing = (boolean)0;
      this.mIsConnecting = (boolean)0;
      this.mContext = var1;
      LocalBluetoothManager var6 = LocalBluetoothManager.getInstance(var1);
      this.mLocalManager = var6;
      if(this.mLocalManager == null) {
         throw new IllegalStateException("Cannot use CachedBluetoothDevice without Bluetooth hardware");
      } else {
         this.mVisible = (boolean)0;
         BluetoothDevice var7 = var2.mDevice;
         this.mDevice = var7;
         String var8 = var2.mName;
         this.mName = var8;
         int var9 = var2.mRssi;
         this.mRssi = var9;
         BluetoothClass var10 = var2.mBtClass;
         this.mBtClass = var10;
         int var11 = var2.mBtClassDrawable;
         this.mBtClassDrawable = var11;
         boolean var12 = var2.mUpdateBtClassDrawable;
         this.mUpdateBtClassDrawable = var12;
         Iterator var13 = var2.mProfiles.iterator();

         while(var13.hasNext()) {
            LocalBluetoothProfileManager.Profile var14 = (LocalBluetoothProfileManager.Profile)var13.next();
            this.mProfiles.add(var14);
         }

         this.dispatchAttributesChanged();
      }
   }

   private void cancelDiscovery() {
      BluetoothAdapter var1 = this.mLocalManager.getBluetoothAdapter();
      if(var1.isDiscovering()) {
         boolean var2 = var1.cancelDiscovery();
      }
   }

   private void connectAllProfiles() {
      if(this.ensurePaired()) {
         this.mIsConnectingErrorPossible = (boolean)1;
         Iterator var1 = this.mProfiles.iterator();

         while(var1.hasNext()) {
            LocalBluetoothProfileManager.Profile var2 = (LocalBluetoothProfileManager.Profile)var1.next();
            if(this.isConnectableProfile(var2)) {
               LocalBluetoothProfileManager var3 = LocalBluetoothProfileManager.getProfileManager(this.mLocalManager, var2);
               BluetoothDevice var4 = this.mDevice;
               var3.setPreferred(var4, (boolean)0);
               this.disconnectConnected(this, var2);
               this.connectInt(this, var2);
            }
         }

      }
   }

   private boolean connectInt(CachedBluetoothDevice var1, LocalBluetoothProfileManager.Profile var2) {
      boolean var3;
      if(!var1.ensurePaired()) {
         var3 = false;
      } else {
         LocalBluetoothProfileManager var4 = LocalBluetoothProfileManager.getProfileManager(this.mLocalManager, var2);
         BluetoothDevice var5 = var1.mDevice;
         var4.getConnectionStatus(var5);
         BluetoothDevice var7 = var1.mDevice;
         if(var4.connect(var7)) {
            StringBuilder var8 = (new StringBuilder()).append("Command sent successfully:CONNECT ");
            String var9 = this.describe(var2);
            String var10 = var8.append(var9).toString();
            int var11 = Log.d("CachedBluetoothDevice", var10);
            var3 = true;
         } else {
            StringBuilder var12 = (new StringBuilder()).append("Failed to connect ");
            String var13 = var2.toString();
            StringBuilder var14 = var12.append(var13).append(" to ");
            String var15 = var1.mName;
            String var16 = var14.append(var15).toString();
            int var17 = Log.i("CachedBluetoothDevice", var16);
            var3 = false;
         }
      }

      return var3;
   }

   private void connectWithoutResettingTimer() {
      if((this.mProfiles.size() == 0 || this.mDevice.getUuids() == null) && !this.updateProfiles()) {
         int var1 = Log.d("CachedBluetoothDevice", "No profiles. Maybe we will connect later");
      } else {
         this.mIsConnectingErrorPossible = (boolean)1;
         int var2 = 0;
         Iterator var3 = this.mProfiles.iterator();

         while(var3.hasNext()) {
            LocalBluetoothProfileManager.Profile var4 = (LocalBluetoothProfileManager.Profile)var3.next();
            if(this.isConnectableProfile(var4)) {
               LocalBluetoothProfileManager var5 = LocalBluetoothProfileManager.getProfileManager(this.mLocalManager, var4);
               BluetoothDevice var6 = this.mDevice;
               if(var5.isPreferred(var6)) {
                  label27: {
                     ++var2;
                     LocalBluetoothProfileManager.Profile var7 = LocalBluetoothProfileManager.Profile.HEADSET;
                     if(var4 != var7) {
                        LocalBluetoothProfileManager.Profile var8 = LocalBluetoothProfileManager.Profile.A2DP;
                        if(var4 != var8) {
                           break label27;
                        }
                     }

                     this.disconnectConnected(this, var4);
                  }

                  this.connectInt(this, var4);
               }
            }
         }

         String var10 = "Preferred profiles = " + var2;
         int var11 = Log.d("CachedBluetoothDevice", var10);
         if(var2 == 0) {
            this.connectAllProfiles();
         }
      }
   }

   private String describe(CachedBluetoothDevice var1, LocalBluetoothProfileManager.Profile var2) {
      StringBuilder var3 = new StringBuilder();
      StringBuilder var4 = var3.append("Address:");
      BluetoothDevice var5 = var1.mDevice;
      var4.append(var5);
      if(var2 != null) {
         StringBuilder var7 = var3.append(" Profile:");
         String var8 = var2.name();
         var7.append(var8);
      }

      return var3.toString();
   }

   private String describe(LocalBluetoothProfileManager.Profile var1) {
      return this.describe(this, var1);
   }

   private void disconnectConnected(CachedBluetoothDevice var1, LocalBluetoothProfileManager.Profile var2) {
      LocalBluetoothProfileManager var3 = LocalBluetoothProfileManager.getProfileManager(this.mLocalManager, var2);
      CachedBluetoothDeviceManager var4 = this.mLocalManager.getCachedDeviceManager();
      Set var5 = var3.getConnectedDevices();
      if(var5 != null) {
         Iterator var6 = var5.iterator();

         while(var6.hasNext()) {
            BluetoothDevice var7 = (BluetoothDevice)var6.next();
            CachedBluetoothDevice var8 = var4.findDevice(var7);
            if(var8 != null && !var8.equals(var1)) {
               this.disconnectInt(var8, var2);
            }
         }

      }
   }

   private boolean disconnectInt(CachedBluetoothDevice var1, LocalBluetoothProfileManager.Profile var2) {
      LocalBluetoothProfileManager var3 = LocalBluetoothProfileManager.getProfileManager(this.mLocalManager, var2);
      BluetoothDevice var4 = var1.mDevice;
      int var5 = var3.getConnectionStatus(var4);
      if(!this.mIsUnpairing && this.mDevice.getUuids() == null) {
         BluetoothAdapter var6 = this.mLocalManager.getBluetoothAdapter();
         int var7 = Log.i("CachedBluetoothDevice", "***getRemoteServices***");
         String var8 = this.mDevice.getAddress();
         var6.getRemoteServices(var8);
      }

      boolean var11;
      if(SettingsBtStatus.isConnectionStatusConnected(var5)) {
         if(this.isHid()) {
            BluetoothDevice var10 = var1.mDevice;
            var11 = var3.virtualUnplug(var10);
            return var11;
         }

         BluetoothDevice var12 = var1.mDevice;
         if(var3.disconnect(var12)) {
            StringBuilder var13 = (new StringBuilder()).append("Command sent successfully:DISCONNECT ");
            String var14 = this.describe(var2);
            String var15 = var13.append(var14).toString();
            int var16 = Log.d("CachedBluetoothDevice", var15);
            var11 = true;
            return var11;
         }
      }

      var11 = false;
      return var11;
   }

   private void dispatchAttributesChanged() {
      List var1 = this.mCallbacks;
      synchronized(var1) {
         Iterator var2 = this.mCallbacks.iterator();

         while(var2.hasNext()) {
            ((CachedBluetoothDevice.Callback)var2.next()).onDeviceAttributesChanged(this);
         }

      }
   }

   private boolean ensurePaired() {
      boolean var1;
      if(this.getBondState() == 10) {
         this.pair();
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private void fetchBtClass() {
      this.mUpdateBtClassDrawable = (boolean)1;
      BluetoothClass var1 = this.mDevice.getBluetoothClass();
      this.mBtClass = var1;
   }

   private void fetchName() {
      String var1 = this.mDevice.getName();
      this.mName = var1;
      if(TextUtils.isEmpty(this.mName)) {
         String var2 = this.mDevice.getAddress();
         this.mName = var2;
         StringBuilder var3 = (new StringBuilder()).append("Default to address. Device has no name (yet) ");
         String var4 = this.mName;
         String var5 = var3.append(var4).toString();
         int var6 = Log.d("CachedBluetoothDevice", var5);
      }
   }

   private void fillData() {
      this.fetchName();
      this.fetchBtClass();
      boolean var1 = this.updateProfiles();
      this.mVisible = (boolean)0;
      this.dispatchAttributesChanged();
   }

   private int getOneOffSummary() {
      byte var1 = 0;
      byte var2 = 0;
      boolean var3 = false;
      byte var4 = 0;
      List var5 = this.mProfiles;
      LocalBluetoothProfileManager.Profile var6 = LocalBluetoothProfileManager.Profile.A2DP;
      LocalBluetoothProfileManager var9;
      if(var5.contains(var6)) {
         LocalBluetoothManager var7 = this.mLocalManager;
         LocalBluetoothProfileManager.Profile var8 = LocalBluetoothProfileManager.Profile.A2DP;
         var9 = LocalBluetoothProfileManager.getProfileManager(var7, var8);
         BluetoothDevice var10 = this.mDevice;
         if(var9.getConnectionStatus(var10) == 3) {
            var3 = true;
         } else {
            var3 = false;
         }

         BluetoothDevice var11 = this.mDevice;
         var1 = var9.isConnected(var11);
      }

      List var12 = this.mProfiles;
      LocalBluetoothProfileManager.Profile var13 = LocalBluetoothProfileManager.Profile.HEADSET;
      if(var12.contains(var13)) {
         LocalBluetoothManager var14 = this.mLocalManager;
         LocalBluetoothProfileManager.Profile var15 = LocalBluetoothProfileManager.Profile.HEADSET;
         var9 = LocalBluetoothProfileManager.getProfileManager(var14, var15);
         BluetoothDevice var16 = this.mDevice;
         boolean var17;
         if(var9.getConnectionStatus(var16) == 3) {
            var17 = true;
         } else {
            var17 = false;
         }

         var3 |= var17;
         BluetoothDevice var18 = this.mDevice;
         var2 = var9.isConnected(var18);
      }

      List var19 = this.mProfiles;
      LocalBluetoothProfileManager.Profile var20 = LocalBluetoothProfileManager.Profile.HID;
      if(var19.contains(var20)) {
         LocalBluetoothManager var21 = this.mLocalManager;
         LocalBluetoothProfileManager.Profile var22 = LocalBluetoothProfileManager.Profile.HID;
         var9 = LocalBluetoothProfileManager.getProfileManager(var21, var22);
         BluetoothDevice var23 = this.mDevice;
         boolean var24;
         if(var9.getConnectionStatus(var23) == 3) {
            var24 = true;
         } else {
            var24 = false;
         }

         var3 |= var24;
         BluetoothDevice var25 = this.mDevice;
         var4 = var9.isConnected(var25);
      }

      int var26;
      if(var3) {
         var26 = SettingsBtStatus.getConnectionStatusSummary(3);
      } else if(var1 != 0 && var2 != 0) {
         var26 = 2131231037;
      } else if(var1 != 0) {
         var26 = 2131231035;
      } else if(var2 != 0) {
         var26 = 2131231036;
      } else if(var4 != 0) {
         var26 = 2131232615;
      } else {
         var26 = 0;
      }

      return var26;
   }

   private boolean isConnectableProfile(LocalBluetoothProfileManager.Profile var1) {
      LocalBluetoothProfileManager.Profile var2 = LocalBluetoothProfileManager.Profile.HEADSET;
      boolean var5;
      if(!var1.equals(var2)) {
         LocalBluetoothProfileManager.Profile var3 = LocalBluetoothProfileManager.Profile.A2DP;
         if(!var1.equals(var3)) {
            LocalBluetoothProfileManager.Profile var4 = LocalBluetoothProfileManager.Profile.HID;
            if(!var1.equals(var4)) {
               var5 = false;
               return var5;
            }
         }
      }

      var5 = true;
      return var5;
   }

   private boolean updateProfiles() {
      ParcelUuid[] var1 = this.mDevice.getUuids();
      boolean var2;
      if(var1 == null) {
         var2 = false;
      } else {
         this.mUpdateBtClassDrawable = (boolean)1;
         List var3 = this.mProfiles;
         LocalBluetoothProfileManager.updateProfiles(var1, var3);
         StringBuilder var4 = (new StringBuilder()).append("updating profiles for ");
         String var5 = this.mDevice.getName();
         String var6 = var4.append(var5).toString();
         int var7 = Log.e("CachedBluetoothDevice", var6);
         boolean var8 = true;
         BluetoothClass var9 = this.mDevice.getBluetoothClass();
         if(var9 != null) {
            boolean var10 = var9.doesClassMatch(0);
            List var11 = this.mProfiles;
            LocalBluetoothProfileManager.Profile var12 = LocalBluetoothProfileManager.Profile.HEADSET;
            boolean var13 = var11.contains(var12);
            if(var10 != var13) {
               int var14 = Log.v("CachedBluetoothDevice", "headset classbits != uuid");
               var8 = true;
            }

            boolean var15 = var9.doesClassMatch(1);
            List var16 = this.mProfiles;
            LocalBluetoothProfileManager.Profile var17 = LocalBluetoothProfileManager.Profile.A2DP;
            boolean var18 = var16.contains(var17);
            if(var15 != var18) {
               int var19 = Log.v("CachedBluetoothDevice", "a2dp classbits != uuid");
               var8 = true;
            }

            boolean var20 = var9.doesClassMatch(2);
            List var21 = this.mProfiles;
            LocalBluetoothProfileManager.Profile var22 = LocalBluetoothProfileManager.Profile.OPP;
            boolean var23 = var21.contains(var22);
            if(var20 != var23) {
               int var24 = Log.v("CachedBluetoothDevice", "opp classbits != uuid");
               var8 = true;
            }

            boolean var25 = var9.doesClassMatch(3);
            List var26 = this.mProfiles;
            LocalBluetoothProfileManager.Profile var27 = LocalBluetoothProfileManager.Profile.PRINTER;
            boolean var28 = var26.contains(var27);
            if(var25 != var28) {
               int var29 = Log.v("CachedBluetoothDevice", "opp classbits != uuid");
               var8 = true;
            }
         }

         if(var8) {
            if(var9 != null) {
               StringBuilder var30 = (new StringBuilder()).append("Class: ");
               String var31 = var9.toString();
               String var32 = var30.append(var31).toString();
               int var33 = Log.v("CachedBluetoothDevice", var32);
            }

            int var34 = Log.v("CachedBluetoothDevice", "UUID:");
            int var35 = 0;

            while(true) {
               int var36 = var1.length;
               if(var35 >= var36) {
                  break;
               }

               StringBuilder var37 = (new StringBuilder()).append("  ");
               ParcelUuid var38 = var1[var35];
               String var39 = var37.append(var38).toString();
               int var40 = Log.v("CachedBluetoothDevice", var39);
               ++var35;
            }
         }

         var2 = true;
      }

      return var2;
   }

   public void askDisconnect() {
      Activity var1 = this.mLocalManager.getForegroundActivity();
      if(var1 == null) {
         if(this.isHid()) {
            this.unpair();
         } else {
            this.disconnect();
         }
      } else {
         Resources var2 = var1.getResources();
         String var3 = this.getName();
         if(TextUtils.isEmpty(var3)) {
            var3 = var2.getString(2131230814);
         }

         Object[] var4 = new Object[]{var3};
         String var5 = var2.getString(2131230804, var4);
         CachedBluetoothDevice.2 var6 = new CachedBluetoothDevice.2();
         Builder var7 = new Builder(var1);
         String var8 = this.getName();
         AlertDialog var9 = var7.setTitle(var8).setMessage(var5).setPositiveButton(17039370, var6).setNegativeButton(17039360, (OnClickListener)null).show();
      }
   }

   public int compareTo(CachedBluetoothDevice var1) {
      byte var2;
      if(var1.isConnected()) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      byte var3;
      if(this.isConnected()) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      int var4 = var2 - var3;
      int var5;
      if(var4 != 0) {
         var5 = var4;
      } else {
         byte var6;
         if(var1.getBondState() == 12) {
            var6 = 1;
         } else {
            var6 = 0;
         }

         if(this.getBondState() == 12) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         var4 = var6 - var3;
         if(var4 != 0) {
            var5 = var4;
         } else {
            byte var7;
            if(var1.mVisible) {
               var7 = 1;
            } else {
               var7 = 0;
            }

            if(this.mVisible) {
               var3 = 1;
            } else {
               var3 = 0;
            }

            var4 = var7 - var3;
            if(var4 != 0) {
               var5 = var4;
            } else {
               int var8 = var1.mRssi;
               int var9 = this.mRssi;
               var4 = var8 - var9;
               if(var4 != 0) {
                  var5 = var4;
               } else {
                  String var10 = this.getName();
                  String var11 = var1.getName();
                  var5 = var10.compareTo(var11);
               }
            }
         }
      }

      return var5;
   }

   public void connect() {
      StringBuilder var1 = (new StringBuilder()).append("connect: mName = [");
      String var2 = this.mName;
      String var3 = var1.append(var2).append("]").toString();
      int var4 = Log.i("CachedBluetoothDevice", var3);
      if(this.ensurePaired()) {
         this.cancelDiscovery();
         this.mIsConnecting = (boolean)1;
         this.connectWithoutResettingTimer();
      }
   }

   public void connect(LocalBluetoothProfileManager.Profile var1) {
      this.mIsConnectingErrorPossible = (boolean)1;
      this.disconnectConnected(this, var1);
      this.connectInt(this, var1);
   }

   public void disconnect() {
      StringBuilder var1 = (new StringBuilder()).append("disconnect: mName = [");
      String var2 = this.mName;
      String var3 = var1.append(var2).append("]").toString();
      int var4 = Log.i("CachedBluetoothDevice", var3);
      this.cancelDiscovery();
      Iterator var5 = this.mProfiles.iterator();

      while(var5.hasNext()) {
         LocalBluetoothProfileManager.Profile var6 = (LocalBluetoothProfileManager.Profile)var5.next();
         this.disconnect(var6);
      }

   }

   public void disconnect(LocalBluetoothProfileManager.Profile var1) {
      this.disconnectInt(this, var1);
   }

   public boolean equals(Object var1) {
      if(var1 != null && var1 instanceof CachedBluetoothDevice) {
         BluetoothDevice var2 = this.mDevice;
         BluetoothDevice var3 = ((CachedBluetoothDevice)var1).mDevice;
         return var2.equals(var3);
      } else {
         throw new ClassCastException();
      }
   }

   protected void finalize() throws Throwable {
      if(this.mDialog != null) {
         this.mDialog.dismiss();
         this.mDialog = null;
      }

      super.finalize();
   }

   public int getBondState() {
      return this.mDevice.getBondState();
   }

   public int getBtClassDrawable() {
      int var1;
      if(!this.mUpdateBtClassDrawable) {
         var1 = this.mBtClassDrawable;
      } else {
         this.mUpdateBtClassDrawable = (boolean)0;
         if(this.mBtClass != null) {
            switch(this.mBtClass.getMajorDeviceClass()) {
            case 256:
               this.mBtClassDrawable = 2130837547;
               var1 = 2130837547;
               return var1;
            case 512:
               this.mBtClassDrawable = 2130837541;
               var1 = 2130837541;
               return var1;
            case 1536:
               this.mBtClassDrawable = 2130837549;
               var1 = 2130837549;
               return var1;
            }
         } else {
            int var4 = Log.w("CachedBluetoothDevice", "mBtClass is null");
         }

         if(this.mProfiles.size() > 0) {
            List var2 = this.mProfiles;
            LocalBluetoothProfileManager.Profile var3 = LocalBluetoothProfileManager.Profile.PRINTER;
            if(var2.contains(var3)) {
               this.mBtClassDrawable = 2130837549;
               var1 = 2130837549;
               return var1;
            }

            List var5 = this.mProfiles;
            LocalBluetoothProfileManager.Profile var6 = LocalBluetoothProfileManager.Profile.A2DP;
            if(var5.contains(var6)) {
               this.mBtClassDrawable = 2130837543;
               var1 = 2130837543;
               return var1;
            }

            List var7 = this.mProfiles;
            LocalBluetoothProfileManager.Profile var8 = LocalBluetoothProfileManager.Profile.HEADSET;
            if(var7.contains(var8)) {
               this.mBtClassDrawable = 2130837544;
               var1 = 2130837544;
               return var1;
            }
         } else {
            int var9 = Log.w("CachedBluetoothDevice", "Device profile number = 0");
         }

         if(this.mBtClass != null) {
            if(this.mBtClass.doesClassMatch(1)) {
               this.mBtClassDrawable = 2130837543;
               var1 = 2130837543;
               return var1;
            }

            if(this.mBtClass.doesClassMatch(0)) {
               this.mBtClassDrawable = 2130837544;
               var1 = 2130837544;
               return var1;
            }

            if(this.mBtClass.getMajorDeviceClass() == 1280) {
               switch(this.mBtClass.getDeviceClass()) {
               case 1344:
                  this.mBtClassDrawable = 2130837545;
                  break;
               case 1408:
                  this.mBtClassDrawable = 2130837548;
                  break;
               case 1472:
                  this.mBtClassDrawable = 2130837546;
                  break;
               default:
                  this.mBtClassDrawable = 2130837542;
               }

               var1 = this.mBtClassDrawable;
               return var1;
            }
         }

         this.mUpdateBtClassDrawable = (boolean)1;
         this.mBtClassDrawable = 2130837542;
         var1 = this.mBtClassDrawable;
      }

      return var1;
   }

   public int getBtConnectStateDrawable() {
      int var1;
      switch(this.getBondState()) {
      case 10:
         var1 = 2130837563;
         break;
      case 11:
      case 12:
         if(this.isConnected()) {
            var1 = 2130837564;
         } else {
            var1 = 2130837565;
         }
         break;
      default:
         var1 = 0;
      }

      return var1;
   }

   public List<LocalBluetoothProfileManager.Profile> getConnectableProfiles() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.mProfiles.iterator();

      while(var2.hasNext()) {
         LocalBluetoothProfileManager.Profile var3 = (LocalBluetoothProfileManager.Profile)var2.next();
         if(this.isConnectableProfile(var3)) {
            var1.add(var3);
         }
      }

      return var1;
   }

   public BluetoothDevice getDevice() {
      return this.mDevice;
   }

   public String getName() {
      return this.mName;
   }

   public int getSummary() {
      int var1 = this.getOneOffSummary();
      int var2;
      if(var1 != 0) {
         var2 = var1;
      } else {
         Iterator var3 = this.mProfiles.iterator();

         while(true) {
            if(var3.hasNext()) {
               LocalBluetoothProfileManager.Profile var4 = (LocalBluetoothProfileManager.Profile)var3.next();
               LocalBluetoothProfileManager var5 = LocalBluetoothProfileManager.getProfileManager(this.mLocalManager, var4);
               BluetoothDevice var6 = this.mDevice;
               int var7 = var5.getConnectionStatus(var6);
               if(!SettingsBtStatus.isConnectionStatusConnected(var7) && var7 != 3 && var7 != 5) {
                  continue;
               }

               var2 = SettingsBtStatus.getConnectionStatusSummary(var7);
               break;
            }

            var2 = SettingsBtStatus.getPairingStatusSummary(this.getBondState());
            break;
         }
      }

      return var2;
   }

   public int hashCode() {
      return this.mDevice.getAddress().hashCode();
   }

   public boolean isBusy() {
      Iterator var1 = this.mProfiles.iterator();

      boolean var5;
      while(true) {
         if(var1.hasNext()) {
            LocalBluetoothProfileManager.Profile var2 = (LocalBluetoothProfileManager.Profile)var1.next();
            LocalBluetoothProfileManager var3 = LocalBluetoothProfileManager.getProfileManager(this.mLocalManager, var2);
            BluetoothDevice var4 = this.mDevice;
            if(!SettingsBtStatus.isConnectionStatusBusy(var3.getConnectionStatus(var4))) {
               continue;
            }

            var5 = true;
            break;
         }

         if(this.getBondState() == 11) {
            var5 = true;
         } else {
            var5 = false;
         }
         break;
      }

      return var5;
   }

   public boolean isConnected() {
      Iterator var1 = this.mProfiles.iterator();

      boolean var5;
      while(true) {
         if(var1.hasNext()) {
            LocalBluetoothProfileManager.Profile var2 = (LocalBluetoothProfileManager.Profile)var1.next();
            LocalBluetoothProfileManager var3 = LocalBluetoothProfileManager.getProfileManager(this.mLocalManager, var2);
            BluetoothDevice var4 = this.mDevice;
            if(!SettingsBtStatus.isConnectionStatusConnected(var3.getConnectionStatus(var4))) {
               continue;
            }

            var5 = true;
            break;
         }

         var5 = false;
         break;
      }

      return var5;
   }

   public boolean isContainsA2DPOrHFProfiles() {
      boolean var1 = false;
      if(this.mProfiles.size() > 0) {
         Iterator var2 = this.mProfiles.iterator();

         while(var2.hasNext()) {
            LocalBluetoothProfileManager.Profile var3 = (LocalBluetoothProfileManager.Profile)var2.next();
            var1 = this.isConnectableProfile(var3);
            if(var1) {
               break;
            }
         }
      }

      return var1;
   }

   public boolean isHid() {
      boolean var1;
      if(this.mBtClass != null) {
         if(this.mBtClass.getMajorDeviceClass() == 1280) {
            var1 = true;
         } else {
            var1 = false;
         }
      } else {
         int var2 = Log.w("CachedBluetoothDevice", "mBtClass is null");
         var1 = false;
      }

      return var1;
   }

   public boolean isHidMouse() {
      boolean var1;
      if(this.mBtClassDrawable == 2130837548) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isVisible() {
      return this.mVisible;
   }

   void onBondingDockConnect() {
      this.connect();
   }

   public void onBondingStateChanged(int var1) {
      StringBuilder var2 = (new StringBuilder()).append("onBondingStateChanged: bondState = ").append(var1).append(", mIsPairing = ");
      boolean var3 = this.mIsPairing;
      StringBuilder var4 = var2.append(var3).append(", mIsUnpairing = ");
      boolean var5 = this.mIsUnpairing;
      StringBuilder var6 = var4.append(var5).append(", mConnectAfterPairing = ");
      boolean var7 = this.mConnectAfterPairing;
      StringBuilder var8 = var6.append(var7).append(", mIsConnecting = ");
      boolean var9 = this.mIsConnecting;
      String var10 = var8.append(var9).toString();
      int var11 = Log.i("CachedBluetoothDevice", var10);
      if(var1 == 10) {
         this.mProfiles.clear();
         this.mConnectAfterPairing = (boolean)0;
         this.mIsConnecting = (boolean)0;
      }

      this.refresh();
      if(var1 == 12) {
         this.mIsPairing = (boolean)0;
         this.mIsUnpairing = (boolean)0;
         if(this.mDevice.isBluetoothDock()) {
            this.mConnectAfterPairing = (boolean)0;
            this.onBondingDockConnect();
         } else if(this.mConnectAfterPairing) {
            if(!this.isHidMouse()) {
               this.mConnectAfterPairing = (boolean)0;
               this.connect();
            }
         }
      } else if(var1 == 10) {
         this.mIsPairing = (boolean)0;
         if(this.mIsUnpairing) {
            this.mIsUnpairing = (boolean)0;
         } else {
            int var12 = Log.e("CachedBluetoothDevice", "onBondingStateChanged: Unpaired but not due to unpairing, unpair anyway to clean up...");
            this.unpair();
         }
      }
   }

   public void onClicked() {
      StringBuilder var1 = (new StringBuilder()).append("############ onClicked: mName = [");
      String var2 = this.mName;
      String var3 = var1.append(var2).append("]").toString();
      int var4 = Log.i("CachedBluetoothDevice", var3);
      this.mConnectAfterPairing = (boolean)0;
      if(this.isConnected()) {
         this.askDisconnect();
      } else {
         int var5 = this.getBondState();
         if(var5 == 12) {
            if(this.isHid()) {
               if(this.shouldHostConnect()) {
                  this.connect();
               } else {
                  int var6;
                  if(this.isHidMouse()) {
                     var6 = 2131232623;
                  } else {
                     var6 = 2131232624;
                  }

                  Toast.makeText(this.mContext, var6, 1).show();
               }
            } else {
               this.connect();
            }
         } else if(var5 == 10) {
            this.mIsConnecting = (boolean)1;
            this.pair();
         }
      }
   }

   public void onContextItemSelected(MenuItem var1) {
      this.mIsConnecting = (boolean)0;
      Intent var2;
      switch(var1.getItemId()) {
      case 2:
         if(this.getBondState() == 10) {
            this.pair();
            return;
         }

         this.connect();
         return;
      case 3:
         if(this.isHid()) {
            this.unpair();
            return;
         }

         this.disconnect();
         return;
      case 4:
         this.unpair();
         return;
      case 5:
         var2 = new Intent();
         Object var15 = this.mLocalManager.getForegroundActivity();
         if(var15 == null) {
            var15 = this.mLocalManager.getContext();
            Intent var4 = var2.setFlags(268435456);
         }

         var2.setClass((Context)var15, ConnectSpecificProfilesActivity.class);
         BluetoothDevice var6 = this.mDevice;
         var2.putExtra("device", var6);
         ((Context)var15).startActivity(var2);
         return;
      case 6:
         this.cancelDiscovery();
         var2 = new Intent();
         Activity var3 = this.mLocalManager.getForegroundActivity();
         if(var3 == null) {
            Context var8 = this.mLocalManager.getContext();
            Intent var9 = var2.setFlags(268435456);
         }

         var2.setClass(var3, DiscoverServicesActivity.class);
         BluetoothDevice var11 = this.mDevice;
         var2.putExtra("android.bluetooth.device.extra.DEVICE", var11);
         String var13 = this.mName;
         var2.putExtra("android.bluetooth.device.extra.NAME", var13);
         var3.startActivity(var2);
         return;
      default:
      }
   }

   public void onCreateContextMenu(ContextMenu var1) {
      if(this.mLocalManager.getBluetoothState() == 12) {
         if(!this.isBusy()) {
            this.mIsConnecting = (boolean)0;
            String var2 = this.getName();
            var1.setHeaderTitle(var2);
            if(this.getBondState() == 10) {
               MenuItem var4 = var1.add(0, 2, 0, 2131231027);
            } else {
               boolean var5 = false;
               Iterator var6 = this.mProfiles.iterator();

               while(var6.hasNext()) {
                  LocalBluetoothProfileManager.Profile var7 = (LocalBluetoothProfileManager.Profile)var6.next();
                  if(this.isConnectableProfile(var7)) {
                     var5 = true;
                     break;
                  }
               }

               if(this.isConnected()) {
                  if(!this.isHid()) {
                     MenuItem var8 = var1.add(0, 3, 0, 2131231026);
                  }

                  MenuItem var9 = var1.add(0, 4, 0, 2131231029);
               } else {
                  if(this.isHid()) {
                     if(this.shouldHostConnect()) {
                        MenuItem var11 = var1.add(0, 2, 0, 2131231025);
                     }
                  } else if(var5) {
                     MenuItem var13 = var1.add(0, 2, 0, 2131231025);
                  }

                  MenuItem var12 = var1.add(0, 4, 0, 2131231028);
               }

               if(var5) {
                  MenuItem var10 = var1.add(0, 5, 0, 2131231030);
               }
            }
         }
      }
   }

   public void onProfileStateChanged(LocalBluetoothProfileManager.Profile var1, int var2) {
      StringBuilder var3 = (new StringBuilder()).append("onProfileStateChanged: profile ");
      String var4 = var1.toString();
      String var5 = var3.append(var4).append(" newProfileState ").append(var2).toString();
      int var6 = Log.d("CachedBluetoothDevice", var5);
      int var7 = LocalBluetoothProfileManager.getProfileManager(this.mLocalManager, var1).convertState(var2);
      if(var7 == 2 && !this.mProfiles.contains(var1)) {
         this.mProfiles.add(var1);
         this.mUpdateBtClassDrawable = (boolean)1;
      }

      if(var7 == 2 || var7 == 4) {
         if(this.isHidMouse()) {
            if(this.mConnectAfterPairing) {
               this.mConnectAfterPairing = (boolean)0;
               if(var7 == 4) {
                  if(this.getBondState() != 10) {
                     this.unpair();
                  }
               }
            }
         }
      }
   }

   public void onUuidChanged() {
      int var1 = Log.i("CachedBluetoothDevice", "onUuidChanged...");
      boolean var2 = this.updateProfiles();
      if(this.mIsConnecting && this.mProfiles.size() > 0) {
         this.mIsConnecting = (boolean)0;
         this.connectWithoutResettingTimer();
      }

      this.dispatchAttributesChanged();
   }

   public void pair() {
      StringBuilder var1 = (new StringBuilder()).append("pair: mName = [");
      String var2 = this.mName;
      String var3 = var1.append(var2).append("]").toString();
      int var4 = Log.i("CachedBluetoothDevice", var3);
      this.mIsUnpairing = (boolean)0;
      BluetoothAdapter var5 = this.mLocalManager.getBluetoothAdapter();
      if(var5.isDiscovering()) {
         IntentFilter var6 = new IntentFilter();
         var6.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
         Context var7 = this.mContext;
         BroadcastReceiver var8 = this.mReceiver;
         var7.registerReceiver(var8, var6);
         boolean var10 = var5.cancelDiscovery();
         this.mIsPairing = (boolean)1;
      } else if(!this.mDevice.createBond()) {
         LocalBluetoothManager var11 = this.mLocalManager;
         BluetoothDevice var12 = this.mDevice;
         var11.showError(var12, 2131231014, 2131231015);
      } else {
         this.mConnectAfterPairing = (boolean)1;
      }
   }

   public void refresh() {
      this.dispatchAttributesChanged();
   }

   public void refreshBtClass() {
      this.fetchBtClass();
      this.dispatchAttributesChanged();
   }

   public void refreshName() {
      this.fetchName();
      this.dispatchAttributesChanged();
   }

   public void registerCallback(CachedBluetoothDevice.Callback var1) {
      List var2 = this.mCallbacks;
      synchronized(var2) {
         this.mCallbacks.add(var1);
      }
   }

   public void setBtClass(BluetoothClass var1) {
      if(var1 != null) {
         if(this.mBtClass != var1) {
            this.mBtClass = var1;
            this.mUpdateBtClassDrawable = (boolean)1;
            this.dispatchAttributesChanged();
         }
      }
   }

   public void setName(String var1) {
      if(!this.mName.equals(var1)) {
         if(TextUtils.isEmpty(var1)) {
            String var2 = this.mDevice.getAddress();
            this.mName = var2;
         } else {
            this.mName = var1;
         }

         this.dispatchAttributesChanged();
      }
   }

   void setRssi(int var1) {
      if(this.mRssi != var1) {
         this.mRssi = var1;
         this.dispatchAttributesChanged();
      }
   }

   void setVisible(boolean var1) {
      if(this.mVisible != var1) {
         this.mVisible = var1;
         this.dispatchAttributesChanged();
      }
   }

   public boolean shouldHostConnect() {
      LocalBluetoothManager var1 = this.mLocalManager;
      LocalBluetoothProfileManager.Profile var2 = LocalBluetoothProfileManager.Profile.HID;
      LocalBluetoothProfileManager var3 = LocalBluetoothProfileManager.getProfileManager(var1, var2);
      BluetoothDevice var4 = this.mDevice;
      return var3.shouldHostConnect(var4);
   }

   public void showConnectingError() {
      if(this.mIsConnectingErrorPossible) {
         this.mIsConnectingErrorPossible = (boolean)0;
         LocalBluetoothManager var1 = this.mLocalManager;
         BluetoothDevice var2 = this.mDevice;
         var1.showError(var2, 2131231014, 2131231019);
      }
   }

   public String toString() {
      return this.mDevice.toString();
   }

   public void unpair() {
      StringBuilder var1 = (new StringBuilder()).append("unpair: mName = [");
      String var2 = this.mName;
      String var3 = var1.append(var2).append("]").toString();
      int var4 = Log.i("CachedBluetoothDevice", var3);
      this.mIsUnpairing = (boolean)1;
      this.disconnect();
      int var5 = this.getBondState();
      if(var5 == 11) {
         boolean var6 = this.mDevice.cancelBondProcess();
      }

      if(var5 != 10) {
         BluetoothDevice var7 = this.getDevice();
         if(var7 != null) {
            if(var7.removeBond()) {
               StringBuilder var8 = (new StringBuilder()).append("Command sent successfully:REMOVE_BOND ");
               String var9 = this.describe((LocalBluetoothProfileManager.Profile)null);
               String var10 = var8.append(var9).toString();
               int var11 = Log.d("CachedBluetoothDevice", var10);
            } else {
               StringBuilder var12 = (new StringBuilder()).append("Framework rejected command immediately:REMOVE_BOND ");
               String var13 = this.describe((LocalBluetoothProfileManager.Profile)null);
               String var14 = var12.append(var13).toString();
               int var15 = Log.v("CachedBluetoothDevice", var14);
            }
         }
      }
   }

   public void unregisterCallback(CachedBluetoothDevice.Callback var1) {
      List var2 = this.mCallbacks;
      synchronized(var2) {
         this.mCallbacks.remove(var1);
      }
   }

   public interface Callback {

      void onDeviceAttributesChanged(CachedBluetoothDevice var1);
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(DialogInterface var1, int var2) {
         if(CachedBluetoothDevice.this.isHid()) {
            CachedBluetoothDevice.this.unpair();
         } else {
            CachedBluetoothDevice.this.disconnect();
         }
      }
   }

   class 1 extends BroadcastReceiver {

      1() {}

      public void onReceive(Context var1, Intent var2) {
         if(var2.getAction().equals("android.bluetooth.adapter.action.DISCOVERY_FINISHED")) {
            StringBuilder var3 = (new StringBuilder()).append("ACTION_DISCOVERY_FINISHED. mIsPairing: ");
            boolean var4 = CachedBluetoothDevice.this.mIsPairing;
            String var5 = var3.append(var4).toString();
            int var6 = Log.i("CachedBluetoothDevice", var5);
            if(CachedBluetoothDevice.this.mIsPairing) {
               boolean var7 = (boolean)(CachedBluetoothDevice.this.mIsPairing = (boolean)0);
               boolean var8 = (boolean)(CachedBluetoothDevice.this.mIsConnecting = (boolean)1);
               CachedBluetoothDevice.this.pair();
            }
         }

         Context var9 = CachedBluetoothDevice.this.mContext;
         BroadcastReceiver var10 = CachedBluetoothDevice.this.mReceiver;
         var9.unregisterReceiver(var10);
      }
   }
}
