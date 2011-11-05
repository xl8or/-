package com.seven.Z7.authenticator;

import android.accounts.Account;
import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ComponentName;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.seven.Z7.common.IZ7Service;
import com.seven.Z7.common.IZ7ServiceCallback;
import com.seven.Z7.common.Z7CallbackFilter;
import com.seven.Z7.common.Z7ServiceCallback;
import com.seven.Z7.shared.Z7ServiceConstants;
import java.lang.ref.WeakReference;

public class Z7SyncAdapterService extends Service {

   protected static final long MAX_SYNC_TIME_BEFORE_TIMEOUT = 120000L;
   public static final String TAG = "SyncAdapterService";
   final int contentId;
   private Z7SyncAdapterService.SyncThread sSyncAdapter;
   private Object sSyncAdapterLock;
   private Object syncLock;


   protected Z7SyncAdapterService(int var1) {
      Object var2 = new Object();
      this.sSyncAdapterLock = var2;
      Object var3 = new Object();
      this.syncLock = var3;
      this.contentId = var1;
   }

   // $FF: synthetic method
   static Object access$000(Z7SyncAdapterService var0) {
      return var0.syncLock;
   }

   public IBinder onBind(Intent var1) {
      String var2 = "  ACTION_AUTHENTICATOR_INTENT :" + var1;
      int var3 = Log.i("SyncAdapterService", var2);
      return this.sSyncAdapter.getSyncAdapterBinder();
   }

   public void onCreate() {
      // $FF: Couldn't be decompiled
   }

   public static class CalendarSyncAdapterServiceYahoo extends Z7SyncAdapterService.CalendarSyncAdapterService {

      public CalendarSyncAdapterServiceYahoo() {}
   }

   public static class CalendarSyncAdapterServiceWork2 extends Z7SyncAdapterService.CalendarSyncAdapterService {

      public CalendarSyncAdapterServiceWork2() {}
   }

   private class SyncThread extends AbstractThreadedSyncAdapter {

      public static final String TAG = "SyncThread";


      public SyncThread(Context var2, boolean var3) {
         super(var2, var3);
      }

      public void onPerformSync(Account param1, Bundle param2, String param3, ContentProviderClient param4, SyncResult param5) {
         // $FF: Couldn't be decompiled
      }
   }

   public static class CalendarSyncAdapterServiceWork extends Z7SyncAdapterService.CalendarSyncAdapterService {

      public CalendarSyncAdapterServiceWork() {}
   }

   static final class Z7ServiceConnection implements ServiceConnection {

      private static final String TAG = "Z7ServiceConnection";
      private int account;
      private int contentId;
      private boolean forcedSync = 0;
      private WeakReference<IZ7ServiceCallback> listener;
      private WeakReference<Object> lock;
      private WeakReference<IZ7Service> service;


      public Z7ServiceConnection(int var1, int var2, Object var3, boolean var4) {
         this.account = var1;
         this.contentId = var2;
         WeakReference var5 = new WeakReference(var3);
         this.lock = var5;
         this.forcedSync = var4;
      }

      private IZ7Service bindService(IBinder var1) {
         IZ7Service var2 = IZ7Service.Stub.asInterface(var1);
         WeakReference var3 = new WeakReference(var2);
         this.service = var3;
         return var2;
      }

      private IZ7ServiceCallback createListener() {
         int var1 = this.account;
         int var2 = this.contentId;
         WeakReference var3 = this.lock;
         Z7SyncAdapterService.SyncCallbackListener var4 = new Z7SyncAdapterService.SyncCallbackListener(var1, var2, var3);
         WeakReference var5 = new WeakReference(var4);
         this.listener = var5;
         return var4;
      }

      public void onServiceConnected(ComponentName var1, IBinder var2) {
         String var3 = "onServiceConnected " + var1;
         int var4 = Log.i("Z7ServiceConnection", var3);
         IZ7Service var5 = this.bindService(var2);
         IZ7ServiceCallback var6 = this.createListener();

         try {
            if(!var5.isNetworkAvailable() || this.forcedSync) {
               var5.registerCallback(var6, (Z7CallbackFilter)null);
               int var7 = this.account;
               int var8 = this.contentId;
               var5.checkContentUpdates(var7, var8);
            }
         } catch (RemoteException var13) {
            String var11 = var13.getMessage();
            Log.e("Z7ServiceConnection", var11, var13);
         }
      }

      public void onServiceDisconnected(ComponentName var1) {
         String var2 = "onServiceDisconnected() " + var1;
         int var3 = Log.i("Z7ServiceConnection", var2);
         this.service.clear();
      }

      public void release() {
         try {
            IZ7Service var1 = (IZ7Service)this.service.get();
            IZ7ServiceCallback var2 = (IZ7ServiceCallback)this.listener.get();
            if(var1 != null) {
               if(var2 != null) {
                  var1.unregisterCallback(var2);
               }
            }
         } catch (Exception var5) {
            int var4 = Log.w("Z7ServiceConnection", "Releasing service connection failed", var5);
         }
      }
   }

   public static class CalendarSyncAdapterServiceGmail2 extends Z7SyncAdapterService.CalendarSyncAdapterService {

      public CalendarSyncAdapterServiceGmail2() {}
   }

   public static class CalendarSyncAdapterServiceGmail extends Z7SyncAdapterService.CalendarSyncAdapterService {

      public CalendarSyncAdapterServiceGmail() {}
   }

   public static class CalendarSyncAdapterServiceMSN extends Z7SyncAdapterService.CalendarSyncAdapterService {

      public CalendarSyncAdapterServiceMSN() {}
   }

   public static class ContactsSyncAdapterService extends Z7SyncAdapterService {

      protected ContactsSyncAdapterService() {
         super(258);
      }
   }

   static class SyncCallbackListener extends IZ7ServiceCallback.Stub {

      private final int account;
      private final int contentId;
      private final WeakReference<Object> lock;


      public SyncCallbackListener(int var1, int var2, WeakReference<Object> var3) {
         this.account = var1;
         this.contentId = var2;
         this.lock = var3;
      }

      private void notify(Z7ServiceConstants.SystemCallbackType var1, int var2) {
         Z7ServiceConstants.SystemCallbackType var3 = Z7ServiceConstants.SystemCallbackType.Z7_CALLBACK_SYNC_ADAPTER_SYNC_DONE;
         if(var1 == var3) {
            int var4 = this.account;
            if(var2 == var4) {
               StringBuilder var5 = (new StringBuilder()).append("sync(");
               int var6 = this.contentId;
               String var7 = var5.append(var6).append(") done for account ").append(var2).toString();
               int var8 = Log.d("SyncAdapterService", var7);
               Object var9 = this.lock.get();
               if(var9 != null) {
                  synchronized(var9) {
                     var9.notifyAll();
                  }
               }
            }
         }
      }

      public void callback(Bundle var1) throws RemoteException {
         int var2 = var1.getInt("event-id");
         if(var1.containsKey("result")) {
            int var3 = var1.getInt("result");
            Z7ServiceConstants.SystemCallbackType var4 = Z7ServiceConstants.SystemCallbackType.fromId(var2);
            this.notify(var4, var3);
         }
      }

      public void callback2(Z7ServiceCallback var1) throws RemoteException {
         Z7ServiceConstants.SystemCallbackType var2 = var1.getSystemCallbackType();
         int var3 = var1.getAccountId();
         this.notify(var2, var3);
      }
   }

   public static class ContactsSyncAdapterServiceAOL extends Z7SyncAdapterService.ContactsSyncAdapterService {

      public ContactsSyncAdapterServiceAOL() {}
   }

   public static class CalendarSyncAdapterServiceAOL extends Z7SyncAdapterService.CalendarSyncAdapterService {

      public CalendarSyncAdapterServiceAOL() {}
   }

   public static class CalendarSyncAdapterServiceYahoo2 extends Z7SyncAdapterService.CalendarSyncAdapterService {

      public CalendarSyncAdapterServiceYahoo2() {}
   }

   public static class IMSyncAdapterService extends Z7SyncAdapterService {

      public IMSyncAdapterService() {
         super(283);
      }
   }

   public static class ContactsSyncAdapterServiceGmail extends Z7SyncAdapterService.ContactsSyncAdapterService {

      public ContactsSyncAdapterServiceGmail() {}
   }

   public static class ContactsSyncAdapterServiceMSN extends Z7SyncAdapterService.ContactsSyncAdapterService {

      public ContactsSyncAdapterServiceMSN() {}
   }

   public static class CalendarSyncAdapterService extends Z7SyncAdapterService {

      public CalendarSyncAdapterService() {
         super(257);
      }
   }

   public static class ContactsSyncAdapterServiceYahoo extends Z7SyncAdapterService.ContactsSyncAdapterService {

      public ContactsSyncAdapterServiceYahoo() {}
   }

   public static class ContactsSyncAdapterServiceWork extends Z7SyncAdapterService.ContactsSyncAdapterService {

      public ContactsSyncAdapterServiceWork() {}
   }
}
