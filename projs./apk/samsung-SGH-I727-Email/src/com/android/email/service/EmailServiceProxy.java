package com.android.email.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.android.email.service.IEmailService;
import com.android.email.service.IEmailServiceCallback;
import com.android.exchange.OoODataList;
import java.util.List;

public class EmailServiceProxy implements IEmailService {

   public static final String AUTO_DISCOVER_BUNDLE_ERROR_CODE = "autodiscover_error_code";
   public static final String AUTO_DISCOVER_BUNDLE_HOST_AUTH = "autodiscover_host_auth";
   private static final boolean DEBUG_PROXY = false;
   private static final String TAG = "EmailServiceProxy";
   private final IEmailServiceCallback mCallback;
   private final Class<?> mClass;
   private final Context mContext;
   private boolean mDead;
   private Object mReturn;
   private Runnable mRunnable;
   private IEmailService mService;
   private ServiceConnection mSyncManagerConnection;
   private int mTimeout;


   public EmailServiceProxy(Context var1, Class<?> var2) {
      this(var1, var2, (IEmailServiceCallback)null);
   }

   public EmailServiceProxy(Context var1, Class<?> var2, IEmailServiceCallback var3) {
      EmailServiceProxy.EmailServiceConnection var4 = new EmailServiceProxy.EmailServiceConnection();
      this.mSyncManagerConnection = var4;
      this.mService = null;
      this.mReturn = null;
      this.mTimeout = 90;
      this.mDead = (boolean)0;
      this.mContext = var1;
      this.mClass = var2;
      this.mCallback = var3;
      if(Debug.isDebuggerConnected()) {
         int var5 = this.mTimeout << 2;
         this.mTimeout = var5;
      }
   }

   private void runTask() {
      Runnable var1 = this.mRunnable;
      Thread var2 = new Thread(var1);
      var2.start();

      try {
         var2.join();
      } catch (InterruptedException var11) {
         ;
      }

      try {
         Context var3 = this.mContext;
         ServiceConnection var4 = this.mSyncManagerConnection;
         var3.unbindService(var4);
      } catch (IllegalArgumentException var10) {
         ;
      }

      this.mDead = (boolean)1;
      ServiceConnection var5 = this.mSyncManagerConnection;
      synchronized(var5) {
         this.mSyncManagerConnection.notify();
      }
   }

   private void setTask(Runnable var1) throws RemoteException {
      if(this.mDead) {
         throw new RemoteException();
      } else {
         this.mRunnable = var1;
         Context var2 = this.mContext;
         Context var3 = this.mContext;
         Class var4 = this.mClass;
         Intent var5 = new Intent(var3, var4);
         ServiceConnection var6 = this.mSyncManagerConnection;
         var2.bindService(var5, var6, 1);
      }
   }

   public void MoveMessage(List<String> var1, long var2, long var4, long var6) throws RemoteException {
      EmailServiceProxy.13 var16 = new EmailServiceProxy.13(var1, var2, var4, var6);
      this.setTask(var16);
   }

   public void OoOffice(long var1, OoODataList var3) throws RemoteException {
      EmailServiceProxy.10 var4 = new EmailServiceProxy.10(var1, var3);
      this.setTask(var4);
   }

   public IBinder asBinder() {
      return null;
   }

   public Bundle autoDiscover(String var1, String var2, String var3, boolean var4) throws RemoteException {
      EmailServiceProxy.5 var10 = new EmailServiceProxy.5(var1, var2, var3, var4);
      this.setTask(var10);
      this.waitForCompletion();
      Bundle var11;
      if(this.mReturn == null) {
         var11 = null;
      } else {
         Bundle var12 = (Bundle)this.mReturn;
         StringBuilder var13 = (new StringBuilder()).append("autoDiscover returns ");
         int var14 = var12.getInt("autodiscover_error_code");
         String var15 = var13.append(var14).toString();
         int var16 = Log.v("EmailServiceProxy", var15);
         var11 = var12;
      }

      return var11;
   }

   public boolean createFolder(long var1, String var3) throws RemoteException {
      return false;
   }

   public boolean deleteFolder(long var1, String var3) throws RemoteException {
      return false;
   }

   public void emptyTrash(long var1) throws RemoteException {
      EmailServiceProxy.16 var3 = new EmailServiceProxy.16(var1);
      this.setTask(var3);
   }

   public void folderCreate(long param1, String param3, long param4) throws RemoteException {
      // $FF: Couldn't be decompiled
   }

   public void hostChanged(long var1) throws RemoteException {
      EmailServiceProxy.9 var3 = new EmailServiceProxy.9(var1);
      this.setTask(var3);
   }

   public void loadAttachment(long var1, String var3, String var4) throws RemoteException {
      EmailServiceProxy.1 var10 = new EmailServiceProxy.1(var1, var3, var4);
      this.setTask(var10);
   }

   public void loadMore(long var1) throws RemoteException {
      EmailServiceProxy.14 var3 = new EmailServiceProxy.14(var1);
      this.setTask(var3);
   }

   public void moveConversationAlways(long var1, long var3, byte[] var5, int var6) throws RemoteException {
      EmailServiceProxy.11 var15 = new EmailServiceProxy.11(var1, var3, var5, var6);
      this.setTask(var15);
   }

   public boolean renameFolder(long var1, String var3, String var4) throws RemoteException {
      return false;
   }

   public void sendMeetingResponse(long var1, int var3) throws RemoteException {
      EmailServiceProxy.12 var4 = new EmailServiceProxy.12(var1, var3);
      this.setTask(var4);
   }

   public void sendRecoveryPassword(long var1, String var3) throws RemoteException {
      int var4 = Log.d("Email", "EmailServiceProxy.sendRecoveryPassword");
      EmailServiceProxy.17 var5 = new EmailServiceProxy.17(var1, var3);
      this.setTask(var5);
   }

   public void setCallback(IEmailServiceCallback var1) throws RemoteException {
      EmailServiceProxy.8 var2 = new EmailServiceProxy.8(var1);
      this.setTask(var2);
   }

   public void setDeviceInfo(long var1) throws RemoteException {
      EmailServiceProxy.18 var3 = new EmailServiceProxy.18(var1);
      this.setTask(var3);
   }

   public void setLogging(int var1) throws RemoteException {
      EmailServiceProxy.7 var2 = new EmailServiceProxy.7(var1);
      this.setTask(var2);
   }

   public EmailServiceProxy setTimeout(int var1) {
      this.mTimeout = var1;
      return this;
   }

   public void startSync(long var1) throws RemoteException {
      EmailServiceProxy.2 var3 = new EmailServiceProxy.2(var1);
      this.setTask(var3);
   }

   public void stopSync(long var1) throws RemoteException {
      EmailServiceProxy.3 var3 = new EmailServiceProxy.3(var1);
      this.setTask(var3);
   }

   public void updateFolderList(long var1) throws RemoteException {
      EmailServiceProxy.6 var3 = new EmailServiceProxy.6(var1);
      this.setTask(var3);
   }

   public int validate(String var1, String var2, String var3, String var4, int var5, boolean var6, boolean var7) throws RemoteException {
      EmailServiceProxy.4 var16 = new EmailServiceProxy.4(var1, var2, var3, var4, var5, var6, var7);
      this.setTask(var16);
      this.waitForCompletion();
      int var17;
      if(this.mReturn == null) {
         var17 = 0;
      } else {
         StringBuilder var18 = (new StringBuilder()).append("validate returns ");
         Object var19 = this.mReturn;
         String var20 = var18.append(var19).toString();
         int var21 = Log.v("EmailServiceProxy", var20);
         var17 = ((Integer)this.mReturn).intValue();
      }

      return var17;
   }

   public void waitForCompletion() {
      // $FF: Couldn't be decompiled
   }

   class 18 implements Runnable {

      // $FF: synthetic field
      final long val$accountId;


      18(long var2) {
         this.val$accountId = var2;
      }

      public void run() {
         try {
            if(EmailServiceProxy.this.mCallback != null) {
               IEmailService var1 = EmailServiceProxy.this.mService;
               IEmailServiceCallback var2 = EmailServiceProxy.this.mCallback;
               var1.setCallback(var2);
            }

            IEmailService var3 = EmailServiceProxy.this.mService;
            long var4 = this.val$accountId;
            var3.setDeviceInfo(var4);
         } catch (RemoteException var7) {
            ;
         }
      }
   }

   class 17 implements Runnable {

      // $FF: synthetic field
      final long val$accountId;
      // $FF: synthetic field
      final String val$password;


      17(long var2, String var4) {
         this.val$accountId = var2;
         this.val$password = var4;
      }

      public void run() {
         try {
            IEmailService var1 = EmailServiceProxy.this.mService;
            long var2 = this.val$accountId;
            String var4 = this.val$password;
            var1.sendRecoveryPassword(var2, var4);
         } catch (RemoteException var6) {
            ;
         }
      }
   }

   class 7 implements Runnable {

      // $FF: synthetic field
      final int val$on;


      7(int var2) {
         this.val$on = var2;
      }

      public void run() {
         try {
            if(EmailServiceProxy.this.mCallback != null) {
               IEmailService var1 = EmailServiceProxy.this.mService;
               IEmailServiceCallback var2 = EmailServiceProxy.this.mCallback;
               var1.setCallback(var2);
            }

            IEmailService var3 = EmailServiceProxy.this.mService;
            int var4 = this.val$on;
            var3.setLogging(var4);
         } catch (RemoteException var6) {
            ;
         }
      }
   }

   class 6 implements Runnable {

      // $FF: synthetic field
      final long val$accountId;


      6(long var2) {
         this.val$accountId = var2;
      }

      public void run() {
         try {
            if(EmailServiceProxy.this.mCallback != null) {
               IEmailService var1 = EmailServiceProxy.this.mService;
               IEmailServiceCallback var2 = EmailServiceProxy.this.mCallback;
               var1.setCallback(var2);
            }

            IEmailService var3 = EmailServiceProxy.this.mService;
            long var4 = this.val$accountId;
            var3.updateFolderList(var4);
         } catch (RemoteException var7) {
            ;
         }
      }
   }

   class 9 implements Runnable {

      // $FF: synthetic field
      final long val$accountId;


      9(long var2) {
         this.val$accountId = var2;
      }

      public void run() {
         try {
            IEmailService var1 = EmailServiceProxy.this.mService;
            long var2 = this.val$accountId;
            var1.hostChanged(var2);
         } catch (RemoteException var5) {
            ;
         }
      }
   }

   class 8 implements Runnable {

      // $FF: synthetic field
      final IEmailServiceCallback val$cb;


      8(IEmailServiceCallback var2) {
         this.val$cb = var2;
      }

      public void run() {
         try {
            IEmailService var1 = EmailServiceProxy.this.mService;
            IEmailServiceCallback var2 = this.val$cb;
            var1.setCallback(var2);
         } catch (RemoteException var4) {
            ;
         }
      }
   }

   class 3 implements Runnable {

      // $FF: synthetic field
      final long val$mailboxId;


      3(long var2) {
         this.val$mailboxId = var2;
      }

      public void run() {
         try {
            if(EmailServiceProxy.this.mCallback != null) {
               IEmailService var1 = EmailServiceProxy.this.mService;
               IEmailServiceCallback var2 = EmailServiceProxy.this.mCallback;
               var1.setCallback(var2);
            }

            IEmailService var3 = EmailServiceProxy.this.mService;
            long var4 = this.val$mailboxId;
            var3.stopSync(var4);
         } catch (RemoteException var7) {
            ;
         }
      }
   }

   class 2 implements Runnable {

      // $FF: synthetic field
      final long val$mailboxId;


      2(long var2) {
         this.val$mailboxId = var2;
      }

      public void run() {
         try {
            if(EmailServiceProxy.this.mCallback != null) {
               IEmailService var1 = EmailServiceProxy.this.mService;
               IEmailServiceCallback var2 = EmailServiceProxy.this.mCallback;
               var1.setCallback(var2);
            }

            IEmailService var3 = EmailServiceProxy.this.mService;
            long var4 = this.val$mailboxId;
            var3.startSync(var4);
         } catch (RemoteException var7) {
            ;
         }
      }
   }

   class 5 implements Runnable {

      // $FF: synthetic field
      final boolean val$bTrustCert;
      // $FF: synthetic field
      final String val$domain;
      // $FF: synthetic field
      final String val$password;
      // $FF: synthetic field
      final String val$userName;


      5(String var2, String var3, String var4, boolean var5) {
         this.val$userName = var2;
         this.val$password = var3;
         this.val$domain = var4;
         this.val$bTrustCert = var5;
      }

      public void run() {
         try {
            if(EmailServiceProxy.this.mCallback != null) {
               IEmailService var1 = EmailServiceProxy.this.mService;
               IEmailServiceCallback var2 = EmailServiceProxy.this.mCallback;
               var1.setCallback(var2);
            }

            EmailServiceProxy var3 = EmailServiceProxy.this;
            IEmailService var4 = EmailServiceProxy.this.mService;
            String var5 = this.val$userName;
            String var6 = this.val$password;
            String var7 = this.val$domain;
            boolean var8 = this.val$bTrustCert;
            Bundle var9 = var4.autoDiscover(var5, var6, var7, var8);
            var3.mReturn = var9;
         } catch (RemoteException var12) {
            ;
         }
      }
   }

   class 4 implements Runnable {

      // $FF: synthetic field
      final String val$host;
      // $FF: synthetic field
      final String val$password;
      // $FF: synthetic field
      final int val$port;
      // $FF: synthetic field
      final String val$protocol;
      // $FF: synthetic field
      final boolean val$ssl;
      // $FF: synthetic field
      final boolean val$trustCertificates;
      // $FF: synthetic field
      final String val$userName;


      4(String var2, String var3, String var4, String var5, int var6, boolean var7, boolean var8) {
         this.val$protocol = var2;
         this.val$host = var3;
         this.val$userName = var4;
         this.val$password = var5;
         this.val$port = var6;
         this.val$ssl = var7;
         this.val$trustCertificates = var8;
      }

      public void run() {
         try {
            if(EmailServiceProxy.this.mCallback != null) {
               IEmailService var1 = EmailServiceProxy.this.mService;
               IEmailServiceCallback var2 = EmailServiceProxy.this.mCallback;
               var1.setCallback(var2);
            }

            EmailServiceProxy var3 = EmailServiceProxy.this;
            IEmailService var4 = EmailServiceProxy.this.mService;
            String var5 = this.val$protocol;
            String var6 = this.val$host;
            String var7 = this.val$userName;
            String var8 = this.val$password;
            int var9 = this.val$port;
            boolean var10 = this.val$ssl;
            boolean var11 = this.val$trustCertificates;
            Integer var12 = Integer.valueOf(var4.validate(var5, var6, var7, var8, var9, var10, var11));
            var3.mReturn = var12;
         } catch (RemoteException var15) {
            ;
         }
      }
   }

   class 15 implements Runnable {

      // $FF: synthetic field
      final EmailServiceProxy this$0;
      // $FF: synthetic field
      final long val$accountId;
      // $FF: synthetic field
      final String val$folderName;
      // $FF: synthetic field
      final long val$parentMailboxId;


      15(EmailServiceProxy param1, long param2, String param4, long param5) {
         // $FF: Couldn't be decompiled
      }

      public void run() {
         try {
            IEmailService var1 = this.this$0.mService;
            long var2 = this.val$accountId;
            String var4 = this.val$folderName;
            long var5 = this.val$parentMailboxId;
            var1.folderCreate(var2, var4, var5);
         } catch (RemoteException var15) {
            StringBuilder var8 = (new StringBuilder()).append("Could not create folder: ");
            String var9 = this.val$folderName;
            StringBuilder var10 = var8.append(var9).append(", parent: ");
            long var11 = this.val$parentMailboxId;
            String var13 = var10.append(var11).toString();
            int var14 = Log.e("EmailServiceProxy", var13, var15);
         }
      }
   }

   class 16 implements Runnable {

      // $FF: synthetic field
      final long val$accountId;


      16(long var2) {
         this.val$accountId = var2;
      }

      public void run() {
         try {
            if(EmailServiceProxy.this.mCallback != null) {
               IEmailService var1 = EmailServiceProxy.this.mService;
               IEmailServiceCallback var2 = EmailServiceProxy.this.mCallback;
               var1.setCallback(var2);
            }

            IEmailService var3 = EmailServiceProxy.this.mService;
            long var4 = this.val$accountId;
            var3.emptyTrash(var4);
         } catch (RemoteException var7) {
            ;
         }
      }
   }

   class EmailServiceConnection implements ServiceConnection {

      EmailServiceConnection() {}

      public void onServiceConnected(ComponentName var1, IBinder var2) {
         EmailServiceProxy var3 = EmailServiceProxy.this;
         IEmailService var4 = IEmailService.Stub.asInterface(var2);
         var3.mService = var4;
         EmailServiceProxy.EmailServiceConnection.1 var6 = new EmailServiceProxy.EmailServiceConnection.1();
         (new Thread(var6)).start();
      }

      public void onServiceDisconnected(ComponentName var1) {}

      class 1 implements Runnable {

         1() {}

         public void run() {
            EmailServiceProxy.this.runTask();
         }
      }
   }

   class 13 implements Runnable {

      // $FF: synthetic field
      final long val$accountId;
      // $FF: synthetic field
      final long val$curBoxKey;
      // $FF: synthetic field
      final long val$mailboxKey;
      // $FF: synthetic field
      final List val$messageId;


      13(List var2, long var3, long var5, long var7) {
         this.val$messageId = var2;
         this.val$accountId = var3;
         this.val$mailboxKey = var5;
         this.val$curBoxKey = var7;
      }

      public void run() {
         try {
            if(EmailServiceProxy.this.mCallback != null) {
               IEmailService var1 = EmailServiceProxy.this.mService;
               IEmailServiceCallback var2 = EmailServiceProxy.this.mCallback;
               var1.setCallback(var2);
            }

            IEmailService var3 = EmailServiceProxy.this.mService;
            List var4 = this.val$messageId;
            long var5 = this.val$accountId;
            long var7 = this.val$mailboxKey;
            long var9 = this.val$curBoxKey;
            var3.MoveMessage(var4, var5, var7, var9);
         } catch (RemoteException var12) {
            ;
         }
      }
   }

   class 1 implements Runnable {

      // $FF: synthetic field
      final long val$attachmentId;
      // $FF: synthetic field
      final String val$contentUriString;
      // $FF: synthetic field
      final String val$destinationFile;


      1(long var2, String var4, String var5) {
         this.val$attachmentId = var2;
         this.val$destinationFile = var4;
         this.val$contentUriString = var5;
      }

      public void run() {
         try {
            if(EmailServiceProxy.this.mCallback != null) {
               IEmailService var1 = EmailServiceProxy.this.mService;
               IEmailServiceCallback var2 = EmailServiceProxy.this.mCallback;
               var1.setCallback(var2);
            }

            IEmailService var3 = EmailServiceProxy.this.mService;
            long var4 = this.val$attachmentId;
            String var6 = this.val$destinationFile;
            String var7 = this.val$contentUriString;
            var3.loadAttachment(var4, var6, var7);
         } catch (RemoteException var9) {
            ;
         }
      }
   }

   class 14 implements Runnable {

      // $FF: synthetic field
      final long val$messageId;


      14(long var2) {
         this.val$messageId = var2;
      }

      public void run() {
         try {
            if(EmailServiceProxy.this.mCallback != null) {
               IEmailService var1 = EmailServiceProxy.this.mService;
               IEmailServiceCallback var2 = EmailServiceProxy.this.mCallback;
               var1.setCallback(var2);
            }

            IEmailService var3 = EmailServiceProxy.this.mService;
            long var4 = this.val$messageId;
            var3.loadMore(var4);
         } catch (RemoteException var7) {
            ;
         }
      }
   }

   class 11 implements Runnable {

      // $FF: synthetic field
      final byte[] val$conversationId;
      // $FF: synthetic field
      final int val$ignore;
      // $FF: synthetic field
      final long val$messageId;
      // $FF: synthetic field
      final long val$toMailboxId;


      11(long var2, long var4, byte[] var6, int var7) {
         this.val$messageId = var2;
         this.val$toMailboxId = var4;
         this.val$conversationId = var6;
         this.val$ignore = var7;
      }

      public void run() {
         try {
            IEmailService var1 = EmailServiceProxy.this.mService;
            long var2 = this.val$messageId;
            long var4 = this.val$toMailboxId;
            byte[] var6 = this.val$conversationId;
            int var7 = this.val$ignore;
            var1.moveConversationAlways(var2, var4, var6, var7);
         } catch (RemoteException var9) {
            ;
         }
      }
   }

   class 12 implements Runnable {

      // $FF: synthetic field
      final long val$messageId;
      // $FF: synthetic field
      final int val$response;


      12(long var2, int var4) {
         this.val$messageId = var2;
         this.val$response = var4;
      }

      public void run() {
         try {
            if(EmailServiceProxy.this.mCallback != null) {
               IEmailService var1 = EmailServiceProxy.this.mService;
               IEmailServiceCallback var2 = EmailServiceProxy.this.mCallback;
               var1.setCallback(var2);
            }

            IEmailService var3 = EmailServiceProxy.this.mService;
            long var4 = this.val$messageId;
            int var6 = this.val$response;
            var3.sendMeetingResponse(var4, var6);
         } catch (RemoteException var8) {
            ;
         }
      }
   }

   class 10 implements Runnable {

      // $FF: synthetic field
      final long val$accountId;
      // $FF: synthetic field
      final OoODataList val$data;


      10(long var2, OoODataList var4) {
         this.val$accountId = var2;
         this.val$data = var4;
      }

      public void run() {
         try {
            if(EmailServiceProxy.this.mCallback != null) {
               IEmailService var1 = EmailServiceProxy.this.mService;
               IEmailServiceCallback var2 = EmailServiceProxy.this.mCallback;
               var1.setCallback(var2);
            }

            IEmailService var3 = EmailServiceProxy.this.mService;
            long var4 = this.val$accountId;
            OoODataList var6 = this.val$data;
            var3.OoOffice(var4, var6);
         } catch (RemoteException var8) {
            ;
         }
      }
   }
}
