package com.htc.android.mail.eassvc.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.ConditionVariable;
import android.os.IBinder;
import com.htc.android.mail.Mail;
import com.htc.android.mail.eassvc.pim.IEASService;
import com.htc.android.mail.eassvc.util.EASLog;

public class EASServiceBinder {

   private static final boolean DEBUG = Mail.EAS_DEBUG;
   private static final String TAG = "EASServiceBinder";
   private static Object bindLock = new Object();
   private static ConditionVariable bindServiceBlock = new ConditionVariable((boolean)1);
   private static boolean isBind = 0;
   private static IEASService mService;
   private static ServiceConnection mSvcConnection = new EASServiceBinder.1();


   public EASServiceBinder() {}

   public static void bindService(Context var0) {
      if(mService == null) {
         if(DEBUG) {
            EASLog.d("EASServiceBinder", "bindService()");
         }

         Object var1 = bindLock;
         synchronized(var1) {
            bindServiceBlock.close();
            Context var2 = var0.getApplicationContext();
            Intent var3 = new Intent("com.htc.android.mail.eassvc.EASAppSvc");
            ServiceConnection var4 = mSvcConnection;
            if(!var2.bindService(var3, var4, 1) && DEBUG) {
               EASLog.e("EASServiceBinder", "Fail to bind EAS AppSvc!");
            }

         }
      }
   }

   public static IEASService getEASService() {
      bindServiceBlock.block();
      return mService;
   }

   public static void unbindService(Context var0) {
      if(DEBUG) {
         EASLog.d("EASServiceBinder", "unbindService()");
      }

      Object var1 = bindLock;
      synchronized(var1) {
         if(isBind) {
            bindServiceBlock.block();
            Context var2 = var0.getApplicationContext();
            ServiceConnection var3 = mSvcConnection;
            var2.unbindService(var3);
            isBind = (boolean)0;
            mService = null;
         }

      }
   }

   interface BindCallback {

      void onServiceConnected(ComponentName var1, IBinder var2);

      void onServiceDisconnected(ComponentName var1);
   }

   static class 1 implements ServiceConnection {

      1() {}

      public void onServiceConnected(ComponentName var1, IBinder var2) {
         if(EASServiceBinder.DEBUG) {
            EASLog.d("EASServiceBinder", "onServiceConnected");
         }

         IEASService var3 = EASServiceBinder.mService = IEASService.Stub.asInterface(var2);
         EASServiceBinder.bindServiceBlock.open();
         boolean var4 = (boolean)(EASServiceBinder.isBind = (boolean)1);
      }

      public void onServiceDisconnected(ComponentName var1) {
         if(EASServiceBinder.DEBUG) {
            EASLog.d("EASServiceBinder", "onServiceDisconnected");
         }

         IEASService var2 = EASServiceBinder.mService = null;
      }
   }
}
