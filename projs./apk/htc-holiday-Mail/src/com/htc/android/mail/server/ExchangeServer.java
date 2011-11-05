package com.htc.android.mail.server;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IContentProvider;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.widget.Toast;
import com.htc.android.mail.Account;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailMessage;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.Mailbox;
import com.htc.android.mail.Mailboxs;
import com.htc.android.mail.ll;
import com.htc.android.mail.database.ExchangeUtil;
import com.htc.android.mail.eassvc.mail.MailSender;
import com.htc.android.mail.eassvc.pim.EASEventCallback;
import com.htc.android.mail.eassvc.pim.EASMailSendItem;
import com.htc.android.mail.eassvc.pim.EASMoveItems;
import com.htc.android.mail.eassvc.pim.EASOptions;
import com.htc.android.mail.eassvc.pim.IEASService;
import com.htc.android.mail.exception.MailBoxNotFoundException;
import com.htc.android.mail.server.Server;
import com.htc.android.mail.ssl.MailSslError;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class ExchangeServer extends Server {

   private static boolean DEBUG = Mail.MAIL_DEBUG;
   private static final String TAG = "ExchangeServer";
   private static ConditionVariable mCheckSvcBound = null;
   private static EASEventCallback mEventCallbackQueue = null;
   public static IEASService mService = null;
   private static ServiceConnection mSvcConnection = new ExchangeServer.1();
   private Account mAccount = null;
   private int mCommand = 0;
   private Context mContext = null;
   private boolean mIsCancelCommand = 0;
   private String mParam1 = null;
   private String mParam2 = null;


   public ExchangeServer(Context var1, Account var2) {
      Context var3 = var1.getApplicationContext();
      this.mContext = var3;
      this.mAccount = var2;
      this.mCommand = 0;
      this.mIsCancelCommand = (boolean)0;
      if(mService == null) {
         if(mCheckSvcBound == null) {
            mCheckSvcBound = new ConditionVariable((boolean)0);
         }

         Context var4 = this.mContext;
         Intent var5 = new Intent("com.htc.android.mail.eassvc.EASAppSvc");
         ServiceConnection var6 = mSvcConnection;
         if(!var4.bindService(var5, var6, 1)) {
            ll.e("ExchangeServer", "Fail to bind EAS AppSvc!");
         }

         mCheckSvcBound.block();
      }
   }

   // $FF: synthetic method
   static int access$300(ExchangeServer var0) {
      return var0.mCommand;
   }

   // $FF: synthetic method
   static int access$302(ExchangeServer var0, int var1) {
      var0.mCommand = var1;
      return var1;
   }

   // $FF: synthetic method
   static Account access$400(ExchangeServer var0) {
      return var0.mAccount;
   }

   // $FF: synthetic method
   static String access$500(ExchangeServer var0) {
      return var0.mParam1;
   }

   // $FF: synthetic method
   static String access$502(ExchangeServer var0, String var1) {
      var0.mParam1 = var1;
      return var1;
   }

   // $FF: synthetic method
   static String access$600(ExchangeServer var0) {
      return var0.mParam2;
   }

   // $FF: synthetic method
   static String access$602(ExchangeServer var0, String var1) {
      var0.mParam2 = var1;
      return var1;
   }

   public static void cancelBackgroundSync(long var0) {
      try {
         if(mService != null && mService.isAlive()) {
            String var2 = mService.getMailboxRefreshing(var0);
            if(var2 != null) {
               if(DEBUG) {
                  String var3 = "cancelBackgroundSync(): Server is refreshing, stop it, Mailbox Server ID: " + var2;
                  ll.d("ExchangeServer", var3);
               }

               mService.cancelSyncSource(var0, 3);
            }
         } else if(DEBUG) {
            ll.d("ExchangeServer", "cancelBackgroundSync(): mService is null or die");
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }
   }

   public static String getMailboxRefreshing(long param0) {
      // $FF: Couldn't be decompiled
   }

   public static void registerCallback(EASEventCallback param0, Context param1) {
      // $FF: Couldn't be decompiled
   }

   public static void setBindService(Context var0) {
      if(DEBUG) {
         ll.d("ExchangeServer", "- setBindService()");
      }

      if(mService == null) {
         if(mCheckSvcBound == null) {
            mCheckSvcBound = new ConditionVariable((boolean)0);
         }

         Intent var1 = new Intent("com.htc.android.mail.eassvc.EASAppSvc");
         ServiceConnection var2 = mSvcConnection;
         if(!var0.bindService(var1, var2, 1)) {
            ll.e("ExchangeServer", "setBindService(): Fail to bind EAS AppSvc!");
         }
      }
   }

   public static void unregisterCallback(EASEventCallback var0) {
      if(var0 != null) {
         try {
            if(mService != null) {
               mService.unregisterCallback(var0);
            } else if(DEBUG) {
               ll.d("ExchangeServer", "unregisterCallback(): mService is null or die");
            }
         } catch (Exception var1) {
            var1.printStackTrace();
         }
      }
   }

   public void cancelSyncSourceByMode() {
      if(mService == null) {
         if(DEBUG) {
            ll.e("ExchangeServer", "cancelSyncSourceByMode, mService is null");
         }
      } else {
         try {
            if(DEBUG) {
               ll.d("ExchangeServer", "cancel SyncSource by mode");
            }

            IEASService var1 = mService;
            long var2 = this.mAccount.id;
            var1.cancelSyncSourceByMode(var2, 3, 3);
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }
   }

   public void close() {
      StringBuilder var1 = (new StringBuilder()).append("close: ");
      int var2 = this.mCommand;
      String var3 = var1.append(var2).toString();
      ll.d("ExchangeServer", var3);
      this.mIsCancelCommand = (boolean)1;
      ExchangeServer.2 var4 = new ExchangeServer.2();
      (new Thread(var4)).start();
   }

   public void emptyMailbox(long var1, long var3) {
      String var5 = "emptyMailbox: " + var1 + ", " + var3;
      ll.d("ExchangeServer", var5);

      try {
         ContentResolver var6 = this.mContext.getContentResolver();
         String var7 = ExchangeUtil.getMailboxSvrIdbyMailboxId(var3, var6);
         mService.EmptyFolderContents(var1, var7, (boolean)1);
      } catch (RemoteException var8) {
         var8.printStackTrace();
      }
   }

   public int fetchAttachment(long var1, long var3, String var5, boolean var6, int var7) throws Exception {
      if(DEBUG) {
         StringBuilder var8 = (new StringBuilder()).append("fetchAttachment: ");
         StringBuilder var11 = var8.append(var1).append(", ");
         StringBuilder var14 = var11.append(var3).append(", ");
         StringBuilder var17 = var14.append(var5).append(",");
         String var19 = var17.append(var6).toString();
         ll.d("ExchangeServer", var19);
      }

      short var20 = 510;
      this.mCommand = var20;
      String var21 = Long.toString(var3);
      this.mParam1 = var21;
      this.mParam2 = var5;
      int var24 = '\uffff';
      Cursor var25 = null;
      IContentProvider var26 = MailProvider.instance();
      long var27 = 65535L;
      String var29 = null;
      byte var30 = 2;
      boolean var76 = false;

      try {
         var76 = true;
         String[] var31 = new String[var30];
         var31[0] = "_id";
         var31[1] = "_filename";
         Object[] var32 = new Object[3];
         Long var33 = Long.valueOf(var1);
         var32[0] = var33;
         Long var34 = Long.valueOf(var3);
         var32[1] = var34;
         var32[2] = var5;
         String var35 = String.format("_account=%d AND _message=%d AND _filereference=\'%s\'", var32);
         if(!var6) {
            Uri var36 = MailProvider.sPartsURI;
            var25 = var26.query(var36, var31, var35, (String[])null, (String)null);
         } else {
            Uri var59 = MailProvider.sSearchSvrPartsURI;
            var25 = var26.query(var59, var31, var35, (String[])null, (String)null);
         }

         if(var25 != null && var25.moveToFirst()) {
            String var38 = "_id";
            int var39 = var25.getColumnIndexOrThrow(var38);
            var27 = var25.getLong(var39);
            String var43 = "_filename";
            int var44 = var25.getColumnIndexOrThrow(var43);
            var29 = var25.getString(var44);
         }

         if(var27 != 65535L) {
            IEASService var47 = mService;
            long var48 = this.mAccount.id;
            var24 = var47.getAttachment(var48, var27, var5, var29, var6, var7);
         } else {
            ll.e("ExchangeServer", "Can not get attachment from db.");
         }

         if(DEBUG) {
            StringBuilder var54 = (new StringBuilder()).append("Service.fetchEMailAttach() return: ");
            String var56 = var54.append(var24).toString();
            ll.d("ExchangeServer", var56);
            var76 = false;
         } else {
            var76 = false;
         }
      } finally {
         if(var76) {
            Object var61 = null;
            this.mParam1 = (String)var61;
            Object var62 = null;
            this.mParam2 = (String)var62;
            if(var25 != null && !var25.isClosed()) {
               var25.close();
            }

         }
      }

      Object var57 = null;
      this.mParam1 = (String)var57;
      Object var58 = null;
      this.mParam2 = (String)var58;
      if(var25 != null && !var25.isClosed()) {
         var25.close();
      }

      switch(var24) {
      case 300:
         throw new Exception();
      case 301:
         throw new IOException();
      case 302:
         throw new OutOfMemoryError("OutOfMemoryError");
      case 303:
         throw new SocketTimeoutException("SocketTimeoutException");
      case 304:
         throw new UnknownHostException("UnknownHostException");
      case 305:
         throw new CertificateException("CertificateException");
      case 306:
      case 500:
         if(DEBUG) {
            ll.d("ExchangeServer", "downloadAttachException");
         }

         Looper var63 = this.mContext.getMainLooper();
         Handler var64 = new Handler(var63);
         ExchangeServer.5 var65 = new ExchangeServer.5();
         var64.post(var65);
         throw new Exception();
      case 309:
         if(DEBUG) {
            ll.d("ExchangeServer", "Http status 413");
         }

         Looper var69 = this.mContext.getMainLooper();
         Handler var70 = new Handler(var69);
         ExchangeServer.6 var71 = new ExchangeServer.6();
         var70.post(var71);
         throw new Exception();
      default:
         return var24;
      }
   }

   public void fetchMailAgain(Mailbox param1, String param2, long param3, int param5) throws Exception {
      // $FF: Couldn't be decompiled
   }

   public boolean fetchMailItem(Mailbox param1, String param2, long param3, int param5, String param6) throws Exception {
      // $FF: Couldn't be decompiled
   }

   public void fetchMultiAttachments(Mailbox param1, MailMessage param2, boolean param3, boolean param4) throws Exception {
      // $FF: Couldn't be decompiled
   }

   public MailSslError getSslError() {
      return null;
   }

   public EASOptions getSyncOption() {
      EASOptions var1 = null;

      EASOptions var5;
      try {
         IEASService var2 = mService;
         long var3 = this.mAccount.id;
         var5 = var2.getSyncOptions(var3);
      } catch (RemoteException var6) {
         var6.printStackTrace();
         return var1;
      }

      var1 = var5;
      return var1;
   }

   public X509Certificate getX509Certificate() {
      return null;
   }

   public boolean isOpen() {
      boolean var1;
      if(mService != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void meetingResp(long var1, String var3, String var4, int var5) throws Exception {
      if(DEBUG) {
         String var6 = "meetingResp: " + var1 + ", " + var3 + "," + var4 + "," + var5;
         ll.d("ExchangeServer", var6);
      }

      IEASService var7 = mService;
      var7.meetingResp(var1, var3, var4, var5);
   }

   public void moveMail(EASMoveItems var1) {
      if(DEBUG) {
         ll.d("ExchangeServer", "move mail");
      }

      this.mCommand = 2;

      try {
         IEASService var2 = mService;
         long var3 = this.mAccount.id;
         var2.moveMail(var3, var1);
      } catch (RemoteException var6) {
         var6.printStackTrace();
      }
   }

   public void probe() {
      if(DEBUG) {
         ll.d("ExchangeServer", "probe: do nothing");
      }
   }

   public int refresh(Mailbox var1) throws Exception {
      if(DEBUG) {
         StringBuilder var2 = (new StringBuilder()).append("refresh: ");
         long var3 = var1.id;
         String var5 = var2.append(var3).toString();
         ll.d("ExchangeServer", var5);
      }

      this.mCommand = 1;

      try {
         Account var6 = this.mAccount;
         Context var7 = this.mContext;
         var6.refresh(var7);
         Mailboxs var8 = this.mAccount.getMailboxs();
         long var9 = var1.id;
         if(var8.getMailboxById(var9) == null) {
            String var11 = var1.shortName;
            throw new MailBoxNotFoundException(var11);
         }

         Account var12 = this.mAccount;
         this.updateProgressStatus(var12, "");
         Account var13 = this.mAccount;
         this.updateProgressStatus(var13, "");
         if(DEBUG) {
            ll.d("ExchangeServer", "refresh(): start syncMailbox");
         }

         if(var1.kind == 2147483642) {
            if(var1.id == 9223372036854775802L) {
               IEASService var14 = mService;
               long var15 = this.mAccount.id;
               var14.syncMail(var15);
            } else if(var1.id == 9223372036854775801L) {
               String[] var23 = new String[2];
               String var24 = this.mAccount.getMailboxs().getMailboxByKind(Integer.MAX_VALUE).serverId;
               var23[0] = var24;
               String var25 = this.mAccount.getMailboxs().getMailboxByKind(2147483645).serverId;
               var23[1] = var25;
               IEASService var26 = mService;
               long var27 = this.mAccount.id;
               var26.syncMailboxs(var27, var23);
            }
         } else {
            IEASService var29 = mService;
            long var30 = this.mAccount.id;
            String var32 = var1.serverId;
            var29.syncMailbox(var30, var32);
         }

         if(DEBUG) {
            ll.d("ExchangeServer", "refresh(): return syncMailbox");
         }

         if(this.mAccount != null && this.mAccount.protocol == 4) {
            Account var17 = this.mAccount;
            Context var18 = this.mContext;
            var17.reloadMailboxs(var18);
            Mailboxs var19 = this.mAccount.getMailboxs();
            long var20 = var1.id;
            if(var19.getMailboxById(var20) == null) {
               String var22 = var1.shortName;
               throw new MailBoxNotFoundException(var22);
            }
         }
      } catch (RemoteException var33) {
         var33.printStackTrace();
      }

      return 1;
   }

   public void sendMail(Bundle var1) throws Exception {
      long var2 = var1.getLong("account");
      long var4 = var1.getLong("id");
      long var6 = var1.getLong("mailboxId");
      String var8 = "> sendMail: " + var2 + ", " + var4 + ", " + var6;
      ll.d("ExchangeServer", var8);
      Context var9 = this.mContext;
      MailSender.SendMailItem var10 = MailSender.getSendMailItem(var1, var9);
      if(var10 != null) {
         IEASService var11 = mService;
         int var12 = var10.meetingResp;
         EASMailSendItem var13 = var10.sendItem;
         var11.sendMail(var2, var12, var13);
         ll.d("ExchangeServer", "< sendMail");
      } else {
         ll.e("ExchangeServer", "sendMail failed: send mail item is null");
      }
   }

   public void setAccount(Account var1) {
      this.mAccount = var1;
   }

   public void setContext(Context var1) {
      Context var2 = var1.getApplicationContext();
      this.mContext = var2;
      if(mService == null) {
         if(mCheckSvcBound == null) {
            mCheckSvcBound = new ConditionVariable((boolean)0);
         }

         Context var3 = this.mContext;
         Intent var4 = new Intent("com.htc.android.mail.eassvc.EASAppSvc");
         ServiceConnection var5 = mSvcConnection;
         if(!var3.bindService(var4, var5, 1)) {
            ll.e("ExchangeServer", "setContext, Fail to bind EAS AppSvc!");
         }
      }
   }

   public void setX509Certificate(X509Certificate var1) {}

   public void stop() {
      this.setStop((boolean)1);
      this.close();
   }

   public void stop(boolean var1) {
      if(mService == null) {
         ll.e("ExchangeServer", "stop: error #1");
      } else {
         try {
            if(DEBUG) {
               String var2 = "stop: " + var1;
               ll.d("ExchangeServer", var2);
            }

            if(var1) {
               this.stop();
            } else {
               IEASService var3 = mService;
               long var4 = this.mAccount.id;
               var3.cancelSyncSourceByMode(var4, 3, 3);
            }
         } catch (Exception var6) {
            var6.printStackTrace();
         }
      }
   }

   public void updateSyncOption(EASOptions var1) {
      if(DEBUG) {
         StringBuilder var2 = (new StringBuilder()).append("updateSyncOption: ");
         long var3 = this.mAccount.id;
         String var5 = var2.append(var3).toString();
         ll.d("ExchangeServer", var5);
      }

      try {
         if(mService != null) {
            IEASService var6 = mService;
            long var7 = this.mAccount.id;
            var6.setSyncOptions(var7, var1);
         }
      } catch (RemoteException var9) {
         var9.printStackTrace();
      }
   }

   class 5 implements Runnable {

      5() {}

      public void run() {
         Context var1 = ExchangeServer.this.mContext;
         String var2 = ExchangeServer.this.mContext.getString(2131362452);
         Toast.makeText(var1, var2, 1).show();
      }
   }

   class 4 implements Runnable {

      4() {}

      public void run() {
         Context var1 = ExchangeServer.this.mContext;
         String var2 = ExchangeServer.this.mContext.getString(2131362205);
         Toast.makeText(var1, var2, 1).show();
      }
   }

   class 6 implements Runnable {

      6() {}

      public void run() {
         Context var1 = ExchangeServer.this.mContext;
         String var2 = ExchangeServer.this.mContext.getString(2131362452);
         Toast.makeText(var1, var2, 1).show();
      }
   }

   static class 1 implements ServiceConnection {

      1() {}

      public void onServiceConnected(ComponentName var1, IBinder var2) {
         if(ExchangeServer.DEBUG) {
            ll.d("ExchangeServer", "onServiceConnected");
         }

         ExchangeServer.mService = IEASService.Stub.asInterface(var2);
         ExchangeServer.1.1 var3 = new ExchangeServer.1.1();
         (new Thread(var3)).start();
      }

      public void onServiceDisconnected(ComponentName var1) {
         if(ExchangeServer.DEBUG) {
            ll.d("ExchangeServer", "onServiceDisconnected");
         }

         ExchangeServer.mService = null;
      }

      class 1 implements Runnable {

         1() {}

         public void run() {
            if(ExchangeServer.mEventCallbackQueue != null) {
               try {
                  IEASService var1 = ExchangeServer.mService;
                  EASEventCallback var2 = ExchangeServer.mEventCallbackQueue;
                  var1.registerCallback(var2);
               } catch (RemoteException var4) {
                  var4.printStackTrace();
               }

               EASEventCallback var3 = ExchangeServer.mEventCallbackQueue = null;
            }

            ExchangeServer.mCheckSvcBound.open();
         }
      }
   }

   class 2 implements Runnable {

      2() {}

      public void run() {
         // $FF: Couldn't be decompiled
      }
   }

   class 3 implements Runnable {

      3() {}

      public void run() {
         Context var1 = ExchangeServer.this.mContext;
         String var2 = ExchangeServer.this.mContext.getString(2131362205);
         Toast.makeText(var1, var2, 1).show();
      }
   }
}
