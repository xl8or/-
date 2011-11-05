package com.android.settings.bluetooth;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import com.android.settings.bluetooth.CachedBluetoothDeviceManager;
import com.android.settings.bluetooth.LocalBluetoothManager;
import com.android.settings.bluetooth.LocalBluetoothProfileManager;

public class BluetoothEventRedirector {

   private static final String TAG = "BluetoothEventRedirector";
   private BroadcastReceiver mBroadcastReceiver;
   private LocalBluetoothManager mManager;


   public BluetoothEventRedirector(LocalBluetoothManager var1) {
      BluetoothEventRedirector.1 var2 = new BluetoothEventRedirector.1();
      this.mBroadcastReceiver = var2;
      this.mManager = var1;
   }

   private String getDockedDeviceAddress(Context var1) {
      IntentFilter var2 = new IntentFilter("android.intent.action.DOCK_EVENT");
      Intent var3 = var1.registerReceiver((BroadcastReceiver)null, var2);
      String var5;
      if(var3 != null && var3.getIntExtra("android.intent.extra.DOCK_STATE", 0) != 0) {
         BluetoothDevice var4 = (BluetoothDevice)var3.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
         if(var4 != null) {
            var5 = var4.getAddress();
            return var5;
         }
      }

      var5 = null;
      return var5;
   }

   private void persistDiscoveringTimestamp() {
      Editor var1 = this.mManager.getSharedPreferences().edit();
      long var2 = System.currentTimeMillis();
      var1.putLong("last_discovering_time", var2);
      var1.apply();
   }

   public void start() {
      IntentFilter var1 = new IntentFilter();
      var1.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
      var1.addAction("android.bluetooth.adapter.action.DISCOVERY_STARTED");
      var1.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
      var1.addAction("android.bluetooth.device.action.DISAPPEARED");
      var1.addAction("android.bluetooth.device.action.FOUND");
      var1.addAction("android.bluetooth.device.action.NAME_CHANGED");
      var1.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
      var1.addAction("android.bluetooth.a2dp.action.SINK_STATE_CHANGED");
      var1.addAction("android.bluetooth.headset.action.STATE_CHANGED");
      var1.addAction("com.broadcom.bt.service.hid.action.STATE_CHANGED");
      var1.addAction("android.bluetooth.device.action.CLASS_CHANGED");
      var1.addAction("android.bleutooth.device.action.UUID");
      var1.addAction("android.intent.action.DOCK_EVENT");
      Context var2 = this.mManager.getContext();
      BroadcastReceiver var3 = this.mBroadcastReceiver;
      var2.registerReceiver(var3, var1);
   }

   public void stop() {
      Context var1 = this.mManager.getContext();
      BroadcastReceiver var2 = this.mBroadcastReceiver;
      var1.unregisterReceiver(var2);
   }

   class 1 extends BroadcastReceiver {

      1() {}

      public void onReceive(Context var1, Intent var2) {
         StringBuilder var3 = (new StringBuilder()).append("Received ");
         String var4 = var2.getAction();
         String var5 = var3.append(var4).toString();
         int var6 = Log.v("BluetoothEventRedirector", var5);
         String var7 = var2.getAction();
         String var9 = "android.bluetooth.device.extra.DEVICE";
         BluetoothDevice var10 = (BluetoothDevice)var2.getParcelableExtra(var9);
         String var12 = "android.bluetooth.adapter.action.STATE_CHANGED";
         if(var7.equals(var12)) {
            String var14 = "android.bluetooth.adapter.extra.STATE";
            int var15 = Integer.MIN_VALUE;
            int var16 = var2.getIntExtra(var14, var15);
            LocalBluetoothManager var17 = BluetoothEventRedirector.this.mManager;
            var17.setBluetoothStateInt(var16);
         } else {
            String var20 = "android.bluetooth.adapter.action.DISCOVERY_STARTED";
            if(var7.equals(var20)) {
               BluetoothEventRedirector.this.persistDiscoveringTimestamp();
               BluetoothEventRedirector.this.mManager.onScanningStateChanged((boolean)1);
            } else {
               String var22 = "android.bluetooth.adapter.action.DISCOVERY_FINISHED";
               if(var7.equals(var22)) {
                  BluetoothEventRedirector.this.persistDiscoveringTimestamp();
                  BluetoothEventRedirector.this.mManager.onScanningStateChanged((boolean)0);
               } else {
                  String var24 = "android.bluetooth.device.action.FOUND";
                  if(var7.equals(var24)) {
                     String var26 = "android.bluetooth.device.extra.RSSI";
                     short var27 = -32768;
                     short var28 = var2.getShortExtra(var26, var27);
                     String var30 = "android.bluetooth.device.extra.CLASS";
                     BluetoothClass var31 = (BluetoothClass)var2.getParcelableExtra(var30);
                     String var33 = "android.bluetooth.device.extra.NAME";
                     String var34 = var2.getStringExtra(var33);
                     CachedBluetoothDeviceManager var35 = BluetoothEventRedirector.this.mManager.getCachedDeviceManager();
                     var35.onDeviceAppeared(var10, var28, var31, var34);
                  } else {
                     String var41 = "android.bluetooth.device.action.DISAPPEARED";
                     if(var7.equals(var41)) {
                        CachedBluetoothDeviceManager var42 = BluetoothEventRedirector.this.mManager.getCachedDeviceManager();
                        var42.onDeviceDisappeared(var10);
                     } else {
                        String var45 = "android.bluetooth.device.action.NAME_CHANGED";
                        if(var7.equals(var45)) {
                           CachedBluetoothDeviceManager var46 = BluetoothEventRedirector.this.mManager.getCachedDeviceManager();
                           var46.onDeviceNameUpdated(var10);
                        } else {
                           String var49 = "android.bluetooth.device.action.BOND_STATE_CHANGED";
                           if(var7.equals(var49)) {
                              String var51 = "android.bluetooth.device.extra.BOND_STATE";
                              int var52 = Integer.MIN_VALUE;
                              int var53 = var2.getIntExtra(var51, var52);
                              CachedBluetoothDeviceManager var54 = BluetoothEventRedirector.this.mManager.getCachedDeviceManager();
                              var54.onBondingStateChanged(var10, var53);
                              byte var56 = 10;
                              if(var53 == var56) {
                                 if(var10.isBluetoothDock()) {
                                    LocalBluetoothManager var57 = BluetoothEventRedirector.this.mManager;
                                    String var58 = var10.getAddress();
                                    var57.removeDockAutoConnectSetting(var58);
                                    String var59 = var10.getAddress();
                                    BluetoothEventRedirector var60 = BluetoothEventRedirector.this;
                                    String var62 = var60.getDockedDeviceAddress(var1);
                                    if(!var59.equals(var62)) {
                                       var54.onDeviceDisappeared(var10);
                                    }
                                 }

                                 String var64 = "android.bluetooth.device.extra.REASON";
                                 int var65 = Integer.MIN_VALUE;
                                 int var66 = var2.getIntExtra(var64, var65);
                                 var54.showUnbondMessage(var10, var66);
                              }
                           } else {
                              String var68 = "android.bluetooth.headset.action.STATE_CHANGED";
                              int var76;
                              int var72;
                              if(var7.equals(var68)) {
                                 String var70 = "android.bluetooth.headset.extra.STATE";
                                 byte var71 = 0;
                                 var72 = var2.getIntExtra(var70, var71);
                                 String var74 = "android.bluetooth.headset.extra.PREVIOUS_STATE";
                                 byte var75 = 0;
                                 var76 = var2.getIntExtra(var74, var75);
                                 if(var72 == 0) {
                                    byte var78 = 1;
                                    if(var76 == var78) {
                                       int var79 = Log.i("BluetoothEventRedirector", "Failed to connect BT headset");
                                    }
                                 }

                                 CachedBluetoothDeviceManager var80 = BluetoothEventRedirector.this.mManager.getCachedDeviceManager();
                                 LocalBluetoothProfileManager.Profile var81 = LocalBluetoothProfileManager.Profile.HEADSET;
                                 var80.onProfileStateChanged(var10, var81, var72);
                              } else {
                                 String var87 = "android.bluetooth.a2dp.action.SINK_STATE_CHANGED";
                                 if(var7.equals(var87)) {
                                    String var89 = "android.bluetooth.a2dp.extra.SINK_STATE";
                                    byte var90 = 0;
                                    var72 = var2.getIntExtra(var89, var90);
                                    String var92 = "android.bluetooth.a2dp.extra.PREVIOUS_SINK_STATE";
                                    byte var93 = 0;
                                    var76 = var2.getIntExtra(var92, var93);
                                    if(var72 == 0) {
                                       byte var95 = 1;
                                       if(var76 == var95) {
                                          int var96 = Log.i("BluetoothEventRedirector", "Failed to connect BT A2DP");
                                       }
                                    }

                                    CachedBluetoothDeviceManager var97 = BluetoothEventRedirector.this.mManager.getCachedDeviceManager();
                                    LocalBluetoothProfileManager.Profile var98 = LocalBluetoothProfileManager.Profile.A2DP;
                                    var97.onProfileStateChanged(var10, var98, var72);
                                 } else {
                                    String var104 = "android.bluetooth.device.action.CLASS_CHANGED";
                                    if(var7.equals(var104)) {
                                       CachedBluetoothDeviceManager var105 = BluetoothEventRedirector.this.mManager.getCachedDeviceManager();
                                       var105.onBtClassChanged(var10);
                                    } else {
                                       String var108 = "android.bleutooth.device.action.UUID";
                                       if(var7.equals(var108)) {
                                          CachedBluetoothDeviceManager var109 = BluetoothEventRedirector.this.mManager.getCachedDeviceManager();
                                          var109.onUuidChanged(var10);
                                       } else {
                                          String var112 = "android.intent.action.DOCK_EVENT";
                                          if(var7.equals(var112)) {
                                             String var114 = "android.intent.extra.DOCK_STATE";
                                             byte var115 = 1;
                                             if(var2.getIntExtra(var114, var115) == 0) {
                                                if(var10 != null) {
                                                   int var116 = var10.getBondState();
                                                   byte var117 = 10;
                                                   if(var116 == var117) {
                                                      CachedBluetoothDeviceManager var118 = BluetoothEventRedirector.this.mManager.getCachedDeviceManager();
                                                      var118.onDeviceDisappeared(var10);
                                                   }
                                                }
                                             }
                                          } else {
                                             String var121 = "com.broadcom.bt.service.hid.action.STATE_CHANGED";
                                             if(var7.equals(var121)) {
                                                String var123 = "com.broadcom.bt.service.hid.extra.STATE";
                                                byte var124 = 0;
                                                int var125 = var2.getIntExtra(var123, var124);
                                                CachedBluetoothDeviceManager var126 = BluetoothEventRedirector.this.mManager.getCachedDeviceManager();
                                                LocalBluetoothProfileManager.Profile var127 = LocalBluetoothProfileManager.Profile.HID;
                                                var126.onProfileStateChanged(var10, var127, var125);
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
