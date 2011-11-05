package com.android.settings.bluetooth;


public class SettingsBtStatus {

   public static final int CONNECTION_STATUS_ACTIVE = 1;
   public static final int CONNECTION_STATUS_CONNECTED = 2;
   public static final int CONNECTION_STATUS_CONNECTING = 3;
   public static final int CONNECTION_STATUS_DISCONNECTED = 4;
   public static final int CONNECTION_STATUS_DISCONNECTING = 5;
   public static final int CONNECTION_STATUS_UNKNOWN = 0;
   private static final String TAG = "SettingsBtStatus";


   public SettingsBtStatus() {}

   public static final int getConnectionStatusSummary(int var0) {
      int var1 = 2131230805;
      switch(var0) {
      case 0:
         var1 = 2131230809;
      case 1:
      case 2:
         break;
      case 3:
         var1 = 2131230808;
         break;
      case 4:
         var1 = 2131230806;
         break;
      case 5:
         var1 = 2131230807;
         break;
      default:
         var1 = 0;
      }

      return var1;
   }

   public static final int getPairingStatusSummary(int var0) {
      int var1;
      switch(var0) {
      case 10:
         var1 = 2131230810;
         break;
      case 11:
         var1 = 2131230811;
         break;
      case 12:
         var1 = 2131230813;
         break;
      default:
         var1 = 0;
      }

      return var1;
   }

   public static final boolean isConnectionStatusBusy(int var0) {
      boolean var1;
      if(var0 != 3 && var0 != 5) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static final boolean isConnectionStatusConnected(int var0) {
      boolean var1;
      if(var0 != 1 && var0 != 2) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }
}
