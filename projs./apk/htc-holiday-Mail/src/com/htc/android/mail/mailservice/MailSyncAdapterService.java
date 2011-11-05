package com.htc.android.mail.mailservice;

import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import com.htc.android.mail.AbsRequestController;
import com.htc.android.mail.Account;
import com.htc.android.mail.AccountPool;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailRequestHandler;
import com.htc.android.mail.Mailbox;
import com.htc.android.mail.Request;
import com.htc.android.mail.RequestController;
import com.htc.android.mail.ll;
import java.lang.ref.WeakReference;

public class MailSyncAdapterService extends Service {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   private static final String TAG = "MailSyncAdapterService";
   private static MailSyncAdapterService.SyncAdapterImpl sSyncAdapter = null;
   private static final Object sSyncAdapterLock = new Object();


   public MailSyncAdapterService() {}

   public IBinder onBind(Intent var1) {
      Context var2 = this.getApplicationContext();
      return (new MailSyncAdapterService.SyncAdapterImpl(var2)).getSyncAdapterBinder();
   }

   public void onCreate() {
      super.onCreate();
      Object var1 = sSyncAdapterLock;
      synchronized(var1) {
         if(sSyncAdapter == null) {
            Context var2 = this.getApplicationContext();
            sSyncAdapter = new MailSyncAdapterService.SyncAdapterImpl(var2);
         }

      }
   }

   private static class RequestHandler extends MailRequestHandler {

      private Object mLockObject;
      private Account mMailAccount;
      private AbsRequestController mRequestController;
      private WeakReference<Handler> mWeakRequestHandler;


      public RequestHandler(Object var1) {
         this.mLockObject = var1;
      }

      public void onRefreshComplete(Account param1, Request param2, Message param3) {
         // $FF: Couldn't be decompiled
      }

      public void setAccount(Account var1) {
         this.mMailAccount = var1;
      }

      public void setRequestController(AbsRequestController var1) {
         this.mRequestController = var1;
      }

      public void setWeakRequestHandler(WeakReference<Handler> var1) {
         this.mWeakRequestHandler = var1;
      }
   }

   private static class SyncAdapterImpl extends AbstractThreadedSyncAdapter {

      private Context mContext;
      private Object mLockObject;
      private Account mMailAccount;
      private AbsRequestController mRequestController;
      private MailSyncAdapterService.RequestHandler mRequestHandler;
      private WeakReference<Handler> mWeakRequestHandler;


      public SyncAdapterImpl(Context var1) {
         super(var1, (boolean)1);
         Object var2 = new Object();
         this.mLockObject = var2;
         this.mContext = var1;
         Object var3 = this.mLockObject;
         MailSyncAdapterService.RequestHandler var4 = new MailSyncAdapterService.RequestHandler(var3);
         this.mRequestHandler = var4;
         MailSyncAdapterService.RequestHandler var5 = this.mRequestHandler;
         WeakReference var6 = new WeakReference(var5);
         this.mWeakRequestHandler = var6;
         RequestController var7 = RequestController.getInstance(this.mContext);
         this.mRequestController = var7;
         MailSyncAdapterService.RequestHandler var8 = this.mRequestHandler;
         AbsRequestController var9 = this.mRequestController;
         var8.setRequestController(var9);
         MailSyncAdapterService.RequestHandler var10 = this.mRequestHandler;
         WeakReference var11 = this.mWeakRequestHandler;
         var10.setWeakRequestHandler(var11);
      }

      public void onPerformSync(android.accounts.Account var1, Bundle var2, String var3, ContentProviderClient var4, SyncResult var5) {
         if(MailSyncAdapterService.DEBUG) {
            String var6 = "onPerformSync: account = " + var1 + ", extras = " + var2;
            ll.i("MailSyncAdapterService", var6);
         }

         if(var2 == null || !var2.getBoolean("upload")) {
            AccountPool var7 = AccountPool.getInstance(this.mContext);
            Context var8 = this.mContext;
            Account var9 = var7.getAccount(var8, var1);
            this.mMailAccount = var9;
            if(var9 == null) {
               if(MailSyncAdapterService.DEBUG) {
                  ll.i("MailSyncAdapterService", "mailAccount == null");
               }
            } else {
               if(MailSyncAdapterService.DEBUG) {
                  StringBuilder var10 = (new StringBuilder()).append("sync mailAccount: id = ");
                  long var11 = var9.id;
                  String var13 = var10.append(var11).toString();
                  ll.i("MailSyncAdapterService", var13);
               }

               this.mRequestHandler.setAccount(var9);
               AbsRequestController var14 = this.mRequestController;
               WeakReference var15 = this.mWeakRequestHandler;
               var14.addWeakHandler(var15);
               AbsRequestController var16 = this.mRequestController;
               WeakReference var17 = this.mWeakRequestHandler;
               var16.registerWeakMailRequestHandler(var9, var17);
               Mailbox var18 = var9.getMailboxs().getMailboxById(9223372036854775802L);
               Bundle var19 = new Bundle();
               var19.putParcelable("Mailbox", var18);
               Request var20 = new Request();
               WeakReference var21 = this.mWeakRequestHandler;
               var20.weakHandler = var21;
               var20.messageWhat = 0;
               var20.command = 1;
               var20.parameter = var19;
               long var22 = var9.id;
               var20.setAccountId(var22);
               this.mRequestController.refreshOrCheckMoreMail(var20, (boolean)0);
               if(this.mRequestController.isServerRefreshing(var9)) {
                  if(MailSyncAdapterService.DEBUG) {
                     StringBuilder var25 = (new StringBuilder()).append("wait refresh complete: accountId = ");
                     long var26 = var9.id;
                     String var28 = var25.append(var26).toString();
                     ll.i("MailSyncAdapterService", var28);
                  }

                  Object var29 = this.mLockObject;
                  synchronized(var29) {
                     try {
                        this.mLockObject.wait();
                     } catch (Exception var32) {
                        if(MailSyncAdapterService.DEBUG) {
                           ll.i("MailSyncAdapterService", "catch exception", var32);
                        }
                     }

                  }
               }
            }
         }
      }

      public void onSyncCanceled() {
         super.onSyncCanceled();
         if(MailSyncAdapterService.DEBUG) {
            StringBuilder var1 = (new StringBuilder()).append("onSyncCanceled: accountId = ");
            long var2 = this.mMailAccount.id;
            String var4 = var1.append(var2).toString();
            ll.i("MailSyncAdapterService", var4);
         }

         AbsRequestController var5 = this.mRequestController;
         long var6 = this.mMailAccount.id;
         var5.removeRequest(var6, 1);
         AbsRequestController var8 = this.mRequestController;
         long var9 = this.mMailAccount.id;
         var8.removeRequest(var9, 3);
      }
   }
}
