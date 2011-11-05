package com.htc.android.mail.eassvc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.htc.android.mail.eassvc.util.EASLog;
import com.htc.android.mail.eassvc.util.LockUtil;

public class EASScheduleService extends Service {

   private static final String TAG = "EASScheduleService";
   public static LockUtil.EASWakeLock mWakeLock = null;
   public static final Object object = new Object();


   public EASScheduleService() {}

   public static void releaseScheduleWakeLock() {
      EASLog.d("EASScheduleService", "- releaseScheduleWakeLock()");
      Object var0 = object;
      synchronized(var0) {
         if(mWakeLock != null) {
            EASLog.d("EASScheduleService", "- releaseScheduleWakeLock(): mWakeLock != null");
            LockUtil.releasePowerLock(mWakeLock);
         }

         mWakeLock = null;
      }
   }

   public IBinder onBind(Intent var1) {
      return null;
   }

   public void onCreate() {
      super.onCreate();
      EASLog.v("EASScheduleService", "- onCreate()");
   }

   public void onDestroy() {
      super.onDestroy();
      EASLog.v("EASScheduleService", "- onDestroy()");
   }

   public void onStart(Intent param1, int param2) {
      // $FF: Couldn't be decompiled
   }
}
