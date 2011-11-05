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
   private final ServiceConnection mSyncManagerConnection;
   private int mTimeout;


   public EmailServiceProxy(Context var1, Class<?> var2) {
      this(var1, var2, (IEmailServiceCallback)null);
   }

   public EmailServiceProxy(Context var1, Class<?> var2, IEmailServiceCallback var3) {
      EmailServiceProxy.EmailServiceConnection var4 = new EmailServiceProxy.EmailServiceConnection();
      this.mSyncManagerConnection = var4;
      this.mService = null;
      this.mReturn = null;
      this.mTimeout = 45;
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

   public IBinder asBinder() {
      return null;
   }

   public Bundle autoDiscover(String var1, String var2) throws RemoteException {
      EmailServiceProxy.5 var3 = new EmailServiceProxy.5(var1, var2);
      this.setTask(var3);
      this.waitForCompletion();
      Bundle var4;
      if(this.mReturn == null) {
         var4 = null;
      } else {
         Bundle var5 = (Bundle)this.mReturn;
         StringBuilder var6 = (new StringBuilder()).append("autoDiscover returns ");
         int var7 = var5.getInt("autodiscover_error_code");
         String var8 = var6.append(var7).toString();
         int var9 = Log.v("EmailServiceProxy", var8);
         var4 = var5;
      }

      return var4;
   }

   public boolean createFolder(long var1, String var3) throws RemoteException {
      return false;
   }

   public boolean deleteFolder(long var1, String var3) throws RemoteException {
      return false;
   }

   public void hostChanged(long var1) throws RemoteException {
      EmailServiceProxy.9 var3 = new EmailServiceProxy.9(var1);
      this.setTask(var3);
   }

   public void loadAttachment(long var1, String var3, String var4) throws RemoteException {
      EmailServiceProxy.1 var10 = new EmailServiceProxy.1(var1, var3, var4);
      this.setTask(var10);
   }

   public void loadMore(long var1) throws RemoteException {}

   public boolean renameFolder(long var1, String var3, String var4) throws RemoteException {
      return false;
   }

   public void sendMeetingResponse(long var1, int var3) throws RemoteException {
      EmailServiceProxy.10 var4 = new EmailServiceProxy.10(var1, var3);
      this.setTask(var4);
   }

   public void setCallback(IEmailServiceCallback var1) throws RemoteException {
      EmailServiceProxy.8 var2 = new EmailServiceProxy.8(var1);
      this.setTask(var2);
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
      final String val$password;
      // $FF: synthetic field
      final String val$userName;


      5(String var2, String var3) {
         this.val$userName = var2;
         this.val$password = var3;
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
            Bundle var7 = var4.autoDiscover(var5, var6);
            var3.mReturn = var7;
         } catch (RemoteException var10) {
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

   class 10 implements Runnable {

      // $FF: synthetic field
      final long val$messageId;
      // $FF: synthetic field
      final int val$response;


      10(long var2, int var4) {
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
}
