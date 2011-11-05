package com.digc.seven;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import com.android.email.Email;
import com.android.email.combined.common.ExceptionUtil;
import com.android.email.provider.EmailContent;
import com.digc.seven.SevenMessageManager;
import com.seven.Z7.common.IZ7Service;
import com.seven.Z7.common.Z7Preference;
import java.util.HashMap;
import java.util.Map;

public class Z7MailHandler {

   private static final int CALL_SUBMIT_TASK_ANEW = 1002;
   public static final String EXTRA_CONNECTOR = "_connector";
   public static final String EXTRA_EMAIL = "email";
   public static final String EXTRA_ISP_TYPE = "isp_type";
   public static final String EXTRA_URL = "url";
   public static final String KEY_CONNECTOR = "connector";
   public static final String KEY_IGNORE_CERT = "ignore_cert";
   public static final String KEY_PASSWORD = "password";
   public static final String KEY_USERNAME = "username";
   private static Email ca;
   private static Z7MailHandler z7Handler;
   private final String TAG = "Z7MailHandler";


   public Z7MailHandler() {}

   public static Bundle getAccountBundle(String var0, String var1, String var2, int var3, String var4) {
      Bundle var5 = new Bundle();
      var5.putString("username", var0);
      var5.putString("password", var1);
      var5.putString("_connector", var2);
      var5.putInt("isp_type", var3);
      if(var3 == 6) {
         var5.putString("email", var0);
      }

      if(var4 != null) {
         var5.putString("url", var4);
         var5.putBoolean("ignore_cert", (boolean)1);
      } else {
         var5.putBoolean("ignore_cert", (boolean)0);
      }

      return var5;
   }

   public static Z7MailHandler getInstance(Context var0) {
      if(z7Handler == null) {
         ca = Email.getApplication(var0);
         z7Handler = new Z7MailHandler();
      }

      return z7Handler;
   }

   public int addAccount(Bundle var1) throws RemoteException {
      int var2 = 0;
      if(ca.isServiceConnected()) {
         try {
            if(ca.getIService().isNetworkAvailable()) {
               var2 = ca.getIService().addAccount(var1);
               String var3 = "add count result :" + var2;
               int var4 = Log.d("Z7MailHandler", var3);
            }

            return var2;
         } catch (Exception var6) {
            var6.printStackTrace();
            throw new RemoteException();
         }
      } else {
         int var5 = Log.w("Z7MailHandler", "Not connected to service");
         throw new RemoteException();
      }
   }

   public void cancelDownloadMailAttachment(int var1, int var2, int var3) throws RemoteException {
      try {
         if(ca.isServiceConnected()) {
            ca.getIService().cancelDownloadMailAttachment(var1, var2, var3);
         } else {
            int var4 = Log.w("Z7MailHandler", "Not connected to service");
            throw new RemoteException();
         }
      } catch (Exception var7) {
         int var6 = Log.e("Z7MailHandler", "Cancel failed", var7);
         throw new RemoteException();
      }
   }

   public void cancelDownloadMailBody(int var1, long var2, int var4) throws RemoteException {
      try {
         if(ca.isServiceConnected()) {
            ca.getIService().cancelDownloadMailBody(var1, var2, var4);
         } else {
            int var5 = Log.w("Z7MailHandler", "Not connected to service");
            throw new RemoteException();
         }
      } catch (Exception var8) {
         int var7 = Log.e("Z7MailHandler", "cancelDownloadMailBody()", var8);
         throw new RemoteException();
      }
   }

   public void cancelTask(int var1) throws RemoteException {
      if(ca.isServiceConnected()) {
         try {
            ca.getIService().cancelTask(var1);
         } catch (Exception var3) {
            var3.printStackTrace();
            throw new RemoteException();
         }
      } else {
         int var2 = Log.w("Z7MailHandler", "Not connected to service");
         throw new RemoteException();
      }
   }

   public int checkContentUpdates(int var1) throws RemoteException {
      if(ca.isServiceConnected()) {
         try {
            int var2 = ca.getIService().checkContentUpdates(var1, 256);
            return 0;
         } catch (Exception var7) {
            String var4 = var7.getMessage();
            Log.e("Z7MailHandler", var4, var7);
            throw new RemoteException();
         }
      } else {
         int var6 = Log.w("Z7MailHandler", "Not connected to service");
         throw new RemoteException();
      }
   }

   public int checkForUpgrade(boolean var1, boolean var2) {
      return 0;
   }

   public void clearDownloadNotification(int var1, int var2) throws RemoteException {
      if(ca.isServiceConnected()) {
         try {
            ca.getIService().clearDownloadNotification(var1, var2);
         } catch (Exception var4) {
            var4.printStackTrace();
            throw new RemoteException();
         }
      } else {
         int var3 = Log.w("Z7MailHandler", "Not connected to service");
         throw new RemoteException();
      }
   }

   public void clearEmailNotifications(int var1) throws RemoteException {
      try {
         if(ca.isServiceConnected()) {
            String var2 = "clearEmailNotifications for account:" + var1;
            int var3 = Log.i("Z7MailHandler", var2);
            ca.getIService().clearEmailNotifications(var1);
         } else {
            int var4 = Log.w("Z7MailHandler", "Not connected to service");
            throw new RemoteException();
         }
      } catch (Exception var7) {
         int var6 = Log.e("Z7MailHandler", "onResume", var7);
         throw new RemoteException();
      }
   }

   public void clearEmailSendFailedNotifications(int var1, int var2) throws RemoteException {
      try {
         if(ca.isServiceConnected()) {
            ca.getIService().clearEmailSendFailedNotifications(var1, var2);
         } else {
            int var3 = Log.w("Z7MailHandler", "Not connected to service");
            throw new RemoteException();
         }
      } catch (Exception var6) {
         int var5 = Log.e("Z7MailHandler", "OnItemClick", var6);
         throw new RemoteException();
      }
   }

   public void clearUpgradeNotification() {}

   public void contactSearchRelogin(int var1, String var2, boolean var3) {}

   public void delete(int var1, int var2, int var3, long var4) throws RemoteException {
      String var6 = "delete mail :256:" + var3 + ":" + var4;
      int var7 = Log.d("Z7MailHandler", var6);

      try {
         if(ca.isServiceConnected()) {
            IZ7Service var8 = ca.getIService();
            var8.delete(var1, 256, var3, var4);
         } else {
            int var13 = Log.w("Z7MailHandler", "Not connected to service");
            throw new RemoteException();
         }
      } catch (Exception var14) {
         var14.printStackTrace();
         throw new RemoteException();
      }
   }

   public void downloadMailAttachment(int var1, int var2, int var3, String var4) throws RemoteException {
      if(ca.isServiceConnected()) {
         try {
            ca.getIService().downloadMailAttachment(var1, var2, var3, var4);
         } catch (Exception var6) {
            var6.printStackTrace();
            throw new RemoteException();
         }
      } else {
         int var5 = Log.w("Z7MailHandler", "Not connected to service");
         throw new RemoteException();
      }
   }

   public int downloadMailBody(int var1, long var2, int var4, boolean var5, String var6) throws RemoteException {
      if(ca.isServiceConnected()) {
         try {
            IZ7Service var7 = ca.getIService();
            int var14 = var7.downloadMailBody(var1, var2, var4, var5, var6);
            return var14;
         } catch (Exception var16) {
            var16.printStackTrace();
            throw new RemoteException();
         }
      } else {
         int var15 = Log.w("Z7MailHandler", "Not connected to service");
         throw new RemoteException();
      }
   }

   public void downloadMailContentAttachments(int var1, int var2) throws RemoteException {
      try {
         if(ca.isServiceConnected()) {
            ca.getIService().downloadMailContentAttachments(var1, var2);
         } else {
            int var3 = Log.w("Z7MailHandler", "Not connected to service");
            throw new RemoteException();
         }
      } catch (Exception var6) {
         int var5 = Log.e("Z7MailHandler", "downloadMailContentAttachments", var6);
         throw new RemoteException();
      }
   }

   public void getAccountStatusDetails(int var1) throws RemoteException {
      try {
         ca.getIService().getAccountStatusDetails(var1);
      } catch (Exception var4) {
         int var3 = Log.e("Z7MailHandler", "", var4);
         throw new RemoteException();
      }
   }

   public void getAvailableConnectors(Handler var1, Activity var2, OnClickListener var3, OnKeyListener var4, Runnable var5) {
      Email var6 = ca;
      Z7MailHandler.2 var13 = new Z7MailHandler.2(var5, var1, var2, var3, var4);
      var6.callWhenServiceConnected(var1, var13);
   }

   public Z7Preference getPreference(int var1, Z7Preference var2) {
      return null;
   }

   public Map getPreferences(int var1) {
      return null;
   }

   public int getServiceState(int var1, int var2, boolean var3) throws RemoteException {
      if(ca.isServiceConnected()) {
         try {
            int var4 = ca.getIService().getServiceState(var1, var2, var3);
            return var4;
         } catch (Exception var9) {
            String var6 = var9.getMessage();
            Log.e("Z7MailHandler", var6, var9);
            throw new RemoteException();
         }
      } else {
         int var8 = Log.w("Z7MailHandler", "Not connected to service");
         throw new RemoteException();
      }
   }

   public boolean isExtraFeatureAvailable(int var1, int var2, String var3) {
      return false;
   }

   public boolean isNetworkAvailable() throws RemoteException {
      if(ca.isServiceConnected()) {
         try {
            boolean var1 = ca.getIService().isNetworkAvailable();
            String var2 = "getNetworkStatus :" + var1;
            int var3 = Log.d("Z7MailHandler", var2);
            return var1;
         } catch (Exception var5) {
            var5.printStackTrace();
            throw new RemoteException();
         }
      } else {
         int var4 = Log.w("Z7MailHandler", "Not connected to service");
         throw new RemoteException();
      }
   }

   public void markRead(int var1, int var2, long var3, boolean var5) throws RemoteException {
      try {
         if(ca.isServiceConnected()) {
            String var6 = "markRead :" + var1 + ":" + var2 + ":" + var3;
            int var7 = Log.d("Z7MailHandler", var6);
            IZ7Service var8 = ca.getIService();
            var8.markRead(var1, var2, var3, var5);
         } else {
            int var14 = Log.w("Z7MailHandler", "Not connected to service");
            throw new RemoteException();
         }
      } catch (Exception var15) {
         var15.printStackTrace();
         throw new RemoteException();
      }
   }

   public void moveMail(int var1, int var2, long var3, int var5) throws RemoteException {
      try {
         if(ca.isServiceConnected()) {
            IZ7Service var6 = ca.getIService();
            var6.moveMail(var1, var2, var3, var5);
         } else {
            int var12 = Log.w("Z7MailHandler", "Not connected to service");
            throw new RemoteException();
         }
      } catch (Exception var13) {
         var13.printStackTrace();
         throw new RemoteException();
      }
   }

   public void pause(boolean var1) throws RemoteException {
      try {
         ca.getIService().pause(var1);
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RemoteException();
      }
   }

   public void refreshData(int var1) throws RemoteException {
      if(ca.isServiceConnected()) {
         try {
            ca.getIService().clearEmailSendFailedNotifications(var1, -1);
            ca.getIService().refreshData(var1);
         } catch (Exception var3) {
            var3.printStackTrace();
            throw new RemoteException();
         }
      } else {
         int var2 = Log.w("Z7MailHandler", "Not connected to service");
         throw new RemoteException();
      }
   }

   public void registerListener(Email.Z7ConnectionListener var1) {
      ca.registerListener(var1);
   }

   public int relogin(int var1, String var2) throws RemoteException {
      if(ca.isServiceConnected()) {
         try {
            int var3 = ca.getIService().relogin(var1, var2);
            return var3;
         } catch (RemoteException var6) {
            var6.printStackTrace();
            throw var6;
         }
      } else {
         int var5 = Log.w("Z7MailHandler", "Not connected to service");
         throw new RemoteException();
      }
   }

   public void removeAccount(Handler var1, int var2, Activity var3) {
      int var4 = Log.d("Z7MailHandler", "begin removeAccount");
      Email var5 = ca;
      Z7MailHandler.1 var6 = new Z7MailHandler.1(var2, var3);
      var5.callWhenServiceConnected(var1, var6);
   }

   public void removePreference(int var1, String var2) {}

   public void saveSearchResult(long var1) {}

   public void search(String var1) {}

   public void searchMailRecipient(String var1) {}

   public int sendMessage(EmailContent.Message var1, EmailContent.Attachment[] var2, int var3) throws RemoteException {
      int var4 = SevenMessageManager.saveforSeven(ca.getApplicationContext(), var1, var2, var3);

      try {
         if(ca.isServiceConnected()) {
            IZ7Service var5 = ca.getIService();
            int var6 = (int)var1.mSevenAccountKey;
            int var7 = var5.sendMessage(var6, 256, 2, var4);
            String var8 = "Z7MailHandler: z7service sendMessage result= " + var7;
            int var9 = Log.e("soundchan", var8);
            return var7;
         } else {
            int var10 = Log.w("Z7MailHandler", "Not connected to service");
            throw new RemoteException();
         }
      } catch (Exception var11) {
         var11.printStackTrace();
         throw new RemoteException();
      }
   }

   public void setAccountParameters(Bundle var1) throws RemoteException {
      try {
         if(ca.isServiceConnected()) {
            ca.getIService().setAccountParameters(var1);
         } else {
            int var2 = Log.w("Z7MailHandler", "Not connected to service");
            throw new RemoteException();
         }
      } catch (Exception var5) {
         int var4 = Log.e("Z7MailHandler", "Failed to set account parameters", var5);
         throw new RemoteException();
      }
   }

   public void setMSISDN(String var1) {}

   public void setMookSevenNoti() {
      HashMap var1 = new HashMap();
      Boolean var2 = new Boolean((boolean)0);
      var1.put("checkbox_beep", var2);
      Boolean var4 = new Boolean((boolean)0);
      var1.put("checkbox_visual", var4);
      Boolean var6 = new Boolean((boolean)0);
      var1.put("checkbox_vibrate", var6);

      try {
         this.updateSettings(0, var1);
      } catch (Exception var11) {
         String var9 = "" + var11;
         int var10 = Log.d("###", var9);
      }
   }

   public void setPreference(int var1, Z7Preference var2) {}

   public int setServiceState(int var1, int var2, int var3) throws RemoteException {
      if(ca.isServiceConnected()) {
         try {
            int var4 = ca.getIService().setServiceState(var1, var2, var3);
            return var4;
         } catch (Exception var9) {
            String var6 = var9.getMessage();
            Log.e("Z7MailHandler", var6, var9);
            throw new RemoteException();
         }
      } else {
         int var8 = Log.w("Z7MailHandler", "Not connected to service");
         throw new RemoteException();
      }
   }

   public void stopSearch() {}

   public int submitValidateUrlTask(String var1) throws RemoteException {
      if(ca.isServiceConnected()) {
         try {
            int var2 = ca.getIService().submitValidateUrlTask(var1);
            String var3 = "submitValidateUrlTask result :" + var2;
            int var4 = Log.d("Z7MailHandler", var3);
            return var2;
         } catch (Exception var6) {
            var6.printStackTrace();
            throw new RemoteException();
         }
      } else {
         int var5 = Log.w("Z7MailHandler", "Not connected to service");
         throw new RemoteException();
      }
   }

   public void unRegisterListener(Email.Z7ConnectionListener var1) {
      ca.unregisterListener(var1);
   }

   public void updateFolderSyncModes(int var1, Map var2) throws RemoteException {
      try {
         if(ca.isServiceConnected()) {
            ca.getIService().updateFolderSyncModes(var1, var2);
         } else {
            int var3 = Log.w("Z7MailHandler", "Not connected to service");
            throw new RemoteException();
         }
      } catch (Exception var6) {
         int var5 = Log.e("Z7MailHandler", "Failed to update folder sync modes", var6);
         throw new RemoteException();
      }
   }

   public void updateSettings(int var1, Map var2) throws RemoteException {
      if(ca.isServiceConnected()) {
         try {
            ca.getIService().updateSettings(var1, var2);
         } catch (Exception var4) {
            var4.printStackTrace();
            throw new RemoteException();
         }
      } else {
         int var3 = Log.w("Z7MailHandler", "Not connected to service");
         throw new RemoteException();
      }
   }

   public int upgradeDownload() {
      return 0;
   }

   public int validateMSISDN(String var1) {
      return 0;
   }

   class 2 implements Runnable {

      // $FF: synthetic field
      final Activity val$act;
      // $FF: synthetic field
      final OnKeyListener val$keyListener;
      // $FF: synthetic field
      final OnClickListener val$listener;
      // $FF: synthetic field
      final Handler val$mHandler;
      // $FF: synthetic field
      final Runnable val$runnable;


      2(Runnable var2, Handler var3, Activity var4, OnClickListener var5, OnKeyListener var6) {
         this.val$runnable = var2;
         this.val$mHandler = var3;
         this.val$act = var4;
         this.val$listener = var5;
         this.val$keyListener = var6;
      }

      public void run() {
         Bundle var1 = new Bundle();
         var1.putBoolean("email", (boolean)1);
         var1.putBoolean("isFirst", (boolean)1);
         int var2 = Log.i("Z7MailHandler", "Call getAvailableConnectors() service");

         try {
            int var3 = Z7MailHandler.ca.getIService().getAvailableConnectors(var1);
         } catch (Exception var23) {
            int var5 = Log.e("Z7MailHandler", "Fail getAvailableConnectors() service", var23);
            if(var23 instanceof NullPointerException) {
               try {
                  int var6 = Z7MailHandler.ca.getIService().getAvailableConnectors(var1);
               } catch (Exception var22) {
                  if(this.val$runnable != null) {
                     Handler var8 = this.val$mHandler;
                     Runnable var9 = this.val$runnable;
                     var8.post(var9);
                  }

                  int var11 = Log.e("Z7MailHandler", "Fail retry getAvailableConnectors() service", var23);
                  Activity var12 = this.val$act;
                  OnClickListener var13 = this.val$listener;
                  OnKeyListener var14 = this.val$keyListener;
                  ExceptionUtil.showDialogException(var12, var23, var13, var14);
               }
            } else {
               if(this.val$runnable != null) {
                  Handler var15 = this.val$mHandler;
                  Runnable var16 = this.val$runnable;
                  var15.post(var16);
               }

               int var18 = Log.e("Z7MailHandler", "Fail getAvailableConnectors() service", var23);
               Activity var19 = this.val$act;
               OnClickListener var20 = this.val$listener;
               OnKeyListener var21 = this.val$keyListener;
               ExceptionUtil.showDialogException(var19, var23, var20, var21);
            }
         }
      }
   }

   class 1 implements Runnable {

      // $FF: synthetic field
      final int val$accountId;
      // $FF: synthetic field
      final Activity val$act;


      1(int var2, Activity var3) {
         this.val$accountId = var2;
         this.val$act = var3;
      }

      public void run() {
         try {
            int var1 = Log.d("Z7MailHandler", "removeAccount");
            IZ7Service var2 = Z7MailHandler.ca.getIService();
            int var3 = this.val$accountId;
            var2.removeAccount(var3);
         } catch (Exception var5) {
            if(this.val$act != null) {
               ExceptionUtil.showDialogException(this.val$act, var5);
            }
         }
      }
   }
}
