package com.htc.android.mail.eassvc.adapter;

import android.accounts.Account;
import android.content.Context;
import android.content.ISyncContext;
import android.content.SyncContext;
import android.content.SyncResult;
import android.content.ISyncAdapter.Stub;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractThreadedSyncAdapter {

   public static final int LOG_SYNC_DETAILS = 2743;
   private final boolean mAutoInitialize;
   private final Context mContext;
   private final AbstractThreadedSyncAdapter.ISyncAdapterImpl mISyncAdapterImpl;
   private final AtomicInteger mNumSyncStarts;
   private Hashtable<String, AbstractThreadedSyncAdapter.SyncThread> mSyncThreadList;
   private final Object mSyncThreadLock;


   public AbstractThreadedSyncAdapter(Context var1, boolean var2) {
      Hashtable var3 = new Hashtable();
      this.mSyncThreadList = var3;
      Object var4 = new Object();
      this.mSyncThreadLock = var4;
      this.mContext = var1;
      AbstractThreadedSyncAdapter.ISyncAdapterImpl var5 = new AbstractThreadedSyncAdapter.ISyncAdapterImpl((AbstractThreadedSyncAdapter.1)null);
      this.mISyncAdapterImpl = var5;
      AtomicInteger var6 = new AtomicInteger(0);
      this.mNumSyncStarts = var6;
      this.mAutoInitialize = var2;
   }

   // $FF: synthetic method
   static boolean access$300(AbstractThreadedSyncAdapter var0) {
      return var0.mAutoInitialize;
   }

   // $FF: synthetic method
   static AtomicInteger access$400(AbstractThreadedSyncAdapter var0) {
      return var0.mNumSyncStarts;
   }

   public Context getContext() {
      return this.mContext;
   }

   public final IBinder getSyncAdapterBinder() {
      return this.mISyncAdapterImpl.asBinder();
   }

   public abstract void onPerformSync(Account var1, Bundle var2, String var3, SyncResult var4);

   public void onSyncCanceled() {
      Object var1 = this.mSyncThreadLock;
      synchronized(var1) {
         Iterator var2 = this.mSyncThreadList.values().iterator();

         while(var2.hasNext()) {
            AbstractThreadedSyncAdapter.SyncThread var3 = (AbstractThreadedSyncAdapter.SyncThread)var2.next();
            if(var3 != null) {
               var3.interrupt();
            }
         }

      }
   }

   private class ISyncAdapterImpl extends Stub {

      private ISyncAdapterImpl() {}

      // $FF: synthetic method
      ISyncAdapterImpl(AbstractThreadedSyncAdapter.1 var2) {
         this();
      }

      public void cancelSync(ISyncContext var1) {
         Object var2 = AbstractThreadedSyncAdapter.this.mSyncThreadLock;
         synchronized(var2) {
            Iterator var3 = AbstractThreadedSyncAdapter.this.mSyncThreadList.values().iterator();

            while(var3.hasNext()) {
               AbstractThreadedSyncAdapter.SyncThread var4 = (AbstractThreadedSyncAdapter.SyncThread)var3.next();
               if(var4 != null) {
                  IBinder var5 = var4.mSyncContext.getSyncContextBinder();
                  IBinder var6 = var1.asBinder();
                  if(var5 == var6) {
                     AbstractThreadedSyncAdapter.this.onSyncCanceled();
                  }
               }
            }

         }
      }

      public void initialize(Account var1, String var2) throws RemoteException {
         Bundle var3 = new Bundle();
         var3.putBoolean("initialize", (boolean)1);
         this.startSync((ISyncContext)null, var2, var1, var3);
      }

      public void startSync(ISyncContext param1, String param2, Account param3, Bundle param4) {
         // $FF: Couldn't be decompiled
      }
   }

   // $FF: synthetic class
   static class 1 {
   }

   private class SyncThread extends Thread {

      private final Account mAccount;
      private final String mAuthority;
      private final Bundle mExtras;
      private final SyncContext mSyncContext;


      private SyncThread(String var2, SyncContext var3, String var4, Account var5, Bundle var6) {
         super(var2);
         this.mSyncContext = var3;
         this.mAuthority = var4;
         this.mAccount = var5;
         this.mExtras = var6;
      }

      // $FF: synthetic method
      SyncThread(String var2, SyncContext var3, String var4, Account var5, Bundle var6, AbstractThreadedSyncAdapter.1 var7) {
         this(var2, var3, var4, var5, var6);
      }

      private boolean isCanceled() {
         return Thread.currentThread().isInterrupted();
      }

      public void run() {
         // $FF: Couldn't be decompiled
      }
   }
}
