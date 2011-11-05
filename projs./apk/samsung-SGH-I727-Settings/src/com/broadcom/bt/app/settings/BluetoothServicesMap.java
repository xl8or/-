package com.broadcom.bt.app.settings;

import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;

public class BluetoothServicesMap {

   public BluetoothServicesMap() {}

   public static void getServiceString(Context var0, int var1, BluetoothServicesMap.IServiceListManager var2) {}

   public static void getServiceStringByUUID(Context var0, ParcelUuid var1, BluetoothServicesMap.IServiceListManager var2) {
      String var3 = var1.toString().toUpperCase();
      String var4 = "UUID = " + var3;
      int var5 = Log.d("BluetoothServicesMap", var4);
      if(var3 == null) {
         ;
      }
   }

   public interface IServiceListManager {

      void add(String var1);
   }
}
