package com.htc.android.mail.eassvc.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import com.htc.android.mail.Mail;
import com.htc.android.mail.eassvc.util.EASLog;

public class LockUtil {

   private static final boolean DEBUG = Mail.EAS_DEBUG;
   private static final String TAG = "LockUtil";
   public static final String WAKELOCK_DIRECTPUSH = "DIRECTPUSH";
   public static final String WAKELOCK_DIRECTPUSH_MAIL = "DIRECTPUSH_MAIL";
   public static final String WAKELOCK_DP_NETWORKCHANGE = "DP_NETWORK_CHANGE";
   public static final String WAKELOCK_EAS_NETWORKCHANGE = "EAS_NETWORK_CHANGE";
   public static final String WAKELOCK_MAIL_BLK = "MAIL_BLK";
   public static final String WAKELOCK_SCHEDULE = "SCHEDULE";
   public static final String WAKELOCK_START_SERVICE = "START_SERVICE";
   public static final String WAKELOCK_SYNC = "SYNCHRONIZED";
   public static final String WIFILOCK_DIRECTPUSH = "DIRECTPUSH";
   public static final String WIFILOCK_DIRECTPUSH_MAIL = "DIRECTPUSH_MAIL";
   public static final String WIFILOCK_MAIL_BLK = "MAIL_BLK";
   public static final String WIFILOCK_SYNC = "SYNCHRONIZED";


   public LockUtil() {}

   public static LockUtil.EASWakeLock acquirePowerLock(Context var0, String var1) {
      LockUtil.EASWakeLock var2 = newPowerLock(var0, var1);
      acquirePowerLock(var2);
      return var2;
   }

   public static void acquirePowerLock(LockUtil.EASWakeLock var0) {
      if(var0 != null) {
         StringBuilder var1 = (new StringBuilder()).append("- acquire PowerLock - PARTIAL_WAKE_LOCK: ");
         String var2 = var0.lockTag;
         String var3 = var1.append(var2).toString();
         EASLog.i("LockUtil", var3);
         var0.lock.acquire();
      }
   }

   public static LockUtil.EASWakeLock acquirePowerLockTimeout(Context var0, String var1, long var2) {
      LockUtil.EASWakeLock var4 = newPowerLock(var0, var1);
      acquirePowerLockTimeout(var4, var2);
      return var4;
   }

   public static void acquirePowerLockTimeout(LockUtil.EASWakeLock var0, long var1) {
      if(var0 != null) {
         StringBuilder var3 = (new StringBuilder()).append("- acquire PowerLock with Timeout - PARTIAL_WAKE_LOCK: ");
         String var4 = var0.lockTag;
         String var5 = var3.append(var4).toString();
         EASLog.i("LockUtil", var5);
         var0.lock.acquire(var1);
      }
   }

   public static LockUtil.EASWifiLock acquireWifiLock(Context var0, String var1) {
      LockUtil.EASWifiLock var2 = newWifiLock(var0, var1);
      acquireWifiLock(var2);
      return var2;
   }

   public static void acquireWifiLock(LockUtil.EASWifiLock var0) {
      if(var0 != null) {
         StringBuilder var1 = (new StringBuilder()).append("- acquire WifiLock: ");
         String var2 = var0.lockTag;
         String var3 = var1.append(var2).toString();
         EASLog.i("LockUtil", var3);
         var0.lock.acquire();
      }
   }

   public static boolean isPowerLockHeld(LockUtil.EASWakeLock var0) {
      boolean var2;
      if(var0 != null) {
         byte var1 = var0.lock.isHeld();
         if(1 == var1) {
            var2 = true;
            return var2;
         }
      }

      var2 = false;
      return var2;
   }

   private static LockUtil.EASWakeLock newPowerLock(Context var0, String var1) {
      PowerManager var2 = (PowerManager)var0.getSystemService("power");
      LockUtil.EASWakeLock var3;
      if(var2 == null) {
         var3 = null;
      } else {
         LockUtil.EASWakeLock var4 = new LockUtil.EASWakeLock();
         WakeLock var5 = var2.newWakeLock(1, var1);
         var4.lock = var5;
         var4.lock.setReferenceCounted((boolean)0);
         var4.lockTag = var1;
         var3 = var4;
      }

      return var3;
   }

   private static LockUtil.EASWifiLock newWifiLock(Context var0, String var1) {
      NetworkInfo var2 = ((ConnectivityManager)var0.getSystemService("connectivity")).getNetworkInfo(1);
      LockUtil.EASWifiLock var3;
      if(var2 != null && var2.isConnected()) {
         WifiManager var4 = (WifiManager)var0.getSystemService("wifi");
         if(var4 == null) {
            var3 = null;
         } else {
            LockUtil.EASWifiLock var5 = new LockUtil.EASWifiLock();
            WifiLock var6 = var4.createWifiLock(var1);
            var5.lock = var6;
            var5.lock.setReferenceCounted((boolean)0);
            var5.lockTag = var1;
            var3 = var5;
         }
      } else {
         var3 = null;
      }

      return var3;
   }

   public static void releasePowerLock(LockUtil.EASWakeLock var0) {
      if(var0 != null) {
         byte var1 = var0.lock.isHeld();
         if(1 == var1) {
            StringBuilder var2 = (new StringBuilder()).append("- release PowerLock: ");
            String var3 = var0.lockTag;
            String var4 = var2.append(var3).toString();
            EASLog.i("LockUtil", var4);
            var0.lock.release();
         }
      }
   }

   public static void releaseWifiLock(LockUtil.EASWifiLock var0) {
      if(var0 != null) {
         byte var1 = var0.lock.isHeld();
         if(1 == var1) {
            StringBuilder var2 = (new StringBuilder()).append("- release WifiLock: ");
            String var3 = var0.lockTag;
            String var4 = var2.append(var3).toString();
            EASLog.i("LockUtil", var4);
            var0.lock.release();
         }
      }
   }

   public static class EASWifiLock {

      WifiLock lock;
      String lockTag;


      public EASWifiLock() {}
   }

   public static class EASWakeLock {

      WakeLock lock;
      String lockTag;


      public EASWakeLock() {}
   }
}
