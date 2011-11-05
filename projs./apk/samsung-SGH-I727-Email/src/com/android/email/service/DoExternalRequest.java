package com.android.email.service;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import com.android.email.AccountBackupRestore;
import com.android.email.BadgeManager;
import com.android.email.Controller;
import com.android.email.Email;
import com.android.email.SecurityPolicy;
import com.android.email.activity.MessageList;
import com.android.email.mail.MessagingException;
import com.android.email.mail.Store;
import com.android.email.provider.EmailContent;
import com.digc.seven.Z7MailHandler;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DoExternalRequest {

   public static final String EXTRA_REQUEST_TYPE = "request_type";
   public static final int EXTRA_REQUEST_TYPE_DELETE_ACCOUNT = 1;
   public static final int EXTRA_REQUEST_TYPE_DELETE_MSG = 2;
   public static final int EXTRA_REQUEST_TYPE_GENERIC = 0;
   public static final int EXTRA_REQUEST_TYPE_MARK_AS_READ = 3;
   public static final String EXTRA_VAR_ACTION = "action";
   public static final String EXTRA_VAR_ID_ARRAY = "id_array";
   public static final String EXTRA_VAR_INTENT_TYPE = "intentType";
   public static final String EXTRA_VAR_IS_ANSWERED = "is_answered";
   public static final String EXTRA_VAR_IS_FAVORITE = "is_favorite";
   public static final String EXTRA_VAR_IS_READ = "is_read";
   public static final String EXTRA_VAR_STATUS = "status";
   public static final String EXTRA_VAR_STATUS_FOLLOWUPFLAG = "status_followupflag";
   public static final String EXTRA_VAR_STATUS_LASTVERB = "status_lastverb";
   private static final String TAG = "DoExternalRequest";
   private static Context mContext = null;
   private static Controller mController = null;
   protected Handler handler;
   private EmailContent.Account mDeletingAccount = null;


   public DoExternalRequest() {
      Handler var1 = new Handler();
      this.handler = var1;
   }

   private boolean checkIsEAS(long var1) {
      EmailContent.Account var3 = EmailContent.Account.restoreAccountWithId(mContext, var1);
      byte var4;
      if(var3 == null) {
         var4 = 0;
      } else {
         Context var5 = mContext;
         String var6 = var3.getStoreUri(var5);
         Context var7 = mContext;
         Store.StoreInfo var8 = Store.StoreInfo.getStoreInfo(var6, var7);
         if(var8 == null) {
            var4 = 0;
         } else {
            String var9 = var8.mScheme;
            var4 = "eas".equals(var9);
         }
      }

      return (boolean)var4;
   }

   private void deleteNext() {
      try {
         Intent var1 = new Intent("com.android.email.action.ACCOUNT_UPDATED");
         mContext.sendBroadcast(var1);
      } catch (Exception var6) {
         Email.logv("DoExternalRequest", "account delete ~~~~~~~~~~~~~~");
      } finally {
         this.onUpdateBadgeRequest();
      }

   }

   private void sendBroadcastDeleteAccount(Context var1, String[] var2) {
      Intent var3 = new Intent("com.sec.android.socialhub.action.EXTERNAL_DB_UPDATE");
      Intent var4 = var3.putExtra("intentType", 400);
      if(var2 != null) {
         var3.putExtra("id_array", var2);
      }

      Intent var6 = var3.putExtra("action", 6);
      Intent var7 = var3.putExtra("status", 0);
      var1.sendBroadcast(var3);
      int var8 = Log.d("Email", "+_+_+_+_+_+_+_+_+_+_+_+_+_+_+ sendBroadcastDeleteAccount() ");
   }

   public static void sendBroadcastDeleteMsg(String[] var0) {
      Intent var1 = new Intent("com.sec.android.socialhub.action.EXTERNAL_DB_UPDATE");
      Intent var2 = var1.putExtra("intentType", 400);
      var1.putExtra("id_array", var0);
      Intent var4 = var1.putExtra("action", 3);
      Intent var5 = var1.putExtra("status", 0);
      mContext.sendBroadcast(var1);
      int var6 = Log.e("ERROR", "+_+_+_+_+_+_+_+_+_+_+_+_+_+_+ sendBroadcastDeleteMsg() ");
   }

   public static void sendBroadcastInsertMsg(String[] var0) {
      Intent var1 = new Intent("com.sec.android.socialhub.action.EXTERNAL_DB_UPDATE");
      Intent var2 = var1.putExtra("intentType", 400);
      if(var0 != null) {
         var1.putExtra("id_array", var0);
      }

      Intent var4 = var1.putExtra("action", 1);
      Intent var5 = var1.putExtra("status", 0);
      mContext.sendBroadcast(var1);
      int var6 = Log.d("Email", "+_+_+_+_+_+_+_+_+_+_+_+_+_+_+ sendBroadcastInsertMsg() ");
   }

   public static void sendBroadcastMarkAsRead(String[] var0, boolean var1) {
      Intent var2 = new Intent("com.sec.android.socialhub.action.EXTERNAL_DB_UPDATE");
      Intent var3 = var2.putExtra("intentType", 400);
      if(var0 != null) {
         var2.putExtra("id_array", var0);
      }

      Intent var5 = var2.putExtra("action", 2);
      Intent var6 = var2.putExtra("status", 0);
      var2.putExtra("is_read", var1);
      mContext.sendBroadcast(var2);
      String var8 = "+_+_+_+_+_+_+_+_+_+_+_+_+_+_+ sendBroadcastMarkAsRead() to " + var1;
      int var9 = Log.d("Email", var8);
   }

   public static void sendBroadcastSetAnswered(String[] var0, boolean var1) {
      Intent var2 = new Intent("com.sec.android.socialhub.action.EXTERNAL_DB_UPDATE");
      Intent var3 = var2.putExtra("intentType", 400);
      if(var0 != null) {
         var2.putExtra("id_array", var0);
      }

      Intent var5 = var2.putExtra("action", 11);
      Intent var6 = var2.putExtra("status", 0);
      var2.putExtra("is_favorite", var1);
      mContext.sendBroadcast(var2);
      String var8 = "+_+_+_+_+_+_+_+_+_+_+_+_+_+_+ sendBroadcastSetFavorite() to " + var1;
      int var9 = Log.d("Email", var8);
   }

   public static void sendBroadcastSetFavorite(String[] var0, boolean var1) {
      Intent var2 = new Intent("com.sec.android.socialhub.action.EXTERNAL_DB_UPDATE");
      Intent var3 = var2.putExtra("intentType", 400);
      if(var0 != null) {
         var2.putExtra("id_array", var0);
      }

      Intent var5 = var2.putExtra("action", 8);
      Intent var6 = var2.putExtra("status", 0);
      var2.putExtra("is_favorite", var1);
      mContext.sendBroadcast(var2);
      String var8 = "+_+_+_+_+_+_+_+_+_+_+_+_+_+_+ sendBroadcastSetFavorite() to " + var1;
      int var9 = Log.d("Email", var8);
   }

   public static void setController(Context var0) {
      if(mContext == null) {
         mContext = var0;
      }

      if(mController == null) {
         mController = Controller.getInstance(var0);
      }
   }

   private void updateBadgeProvider(int var1) {
      ContentResolver var2 = mContext.getContentResolver();
      ContentValues var3 = new ContentValues();
      var3.put("package", "com.android.email");
      var3.put("class", "com.android.email.activity.Welcome");
      Integer var4 = Integer.valueOf(var1);
      var3.put("badgecount", var4);

      try {
         Uri var5 = Uri.parse("content://com.sec.badge/apps");
         var2.update(var5, var3, "package=\'com.android.email\' AND class=\'com.android.email.activity.Welcome\'", (String[])null);
         String var7 = "[updateBadgeProvider] - cnt : " + var1;
         Email.logd("DoExternalRequest", var7);
      } catch (Exception var10) {
         String var9 = "[updateBadgeProvider] fail : " + var10;
         Email.logd("DoExternalRequest", var9);
      }
   }

   public void onMarkAsReadRequest(long var1, boolean var3) {
      if(var1 >= 0L) {
         mController.setMessageRead(var1, var3);
      }
   }

   public void onMultiDeleteRequest(Set<Long> var1) {
      if(var1 != null) {
         if(!var1.isEmpty()) {
            HashSet var2 = new HashSet(var1);
            HashSet var5 = new HashSet();
            ArrayList var6 = new ArrayList();
            ArrayList var7 = new ArrayList();
            if(!var2.isEmpty()) {
               Iterator var8 = var2.iterator();

               while(var8.hasNext()) {
                  Long var9 = (Long)var8.next();
                  Controller var10 = mController;
                  long var11 = var9.longValue();
                  long var13 = var10.lookupAccountForMessage(var11);
                  DoExternalRequest.TempList var15 = new DoExternalRequest.TempList();
                  long var18 = var9.longValue();
                  var15.id = var18;
                  var15.account = var13;
                  var7.add(var15);
                  if(var6.size() == 0) {
                     Long var21 = Long.valueOf(var13);
                     var6.add(var21);
                  } else {
                     int var23 = 0;

                     while(true) {
                        int var24 = var6.size();
                        if(var23 >= var24 || ((Long)var6.get(var23)).longValue() == var13) {
                           int var25 = var6.size();
                           if(var23 == var25) {
                              Long var26 = Long.valueOf(var13);
                              var6.add(var26);
                           }
                           break;
                        }

                        ++var23;
                     }
                  }
               }

               int var28 = 0;

               while(true) {
                  int var29 = var6.size();
                  if(var28 >= var29) {
                     var1.clear();
                     this.onUpdateBadgeRequest();
                     return;
                  }

                  int var30 = 0;

                  while(true) {
                     int var31 = var7.size();
                     if(var30 >= var31) {
                        Controller var38 = mController;
                        long var39 = ((Long)var6.get(var28)).longValue();
                        var38.deleteMessage(var5, var39);
                        var5.clear();
                        ++var28;
                        break;
                     }

                     long var32 = ((Long)var6.get(var28)).longValue();
                     long var34 = ((DoExternalRequest.TempList)var7.get(var30)).account;
                     if(var32 == var34) {
                        Long var36 = Long.valueOf(((DoExternalRequest.TempList)var7.get(var30)).id);
                        var5.add(var36);
                     }

                     ++var30;
                  }
               }
            }
         }
      }
   }

   public void onSetFavoriteRequest(long var1, boolean var3) {
      if(var1 >= 0L) {
         mController.setMessageFavorite(var1, var3);
      }
   }

   public void onSetFollowUpFlagRequest(long var1, int var3) {
      if(var1 >= 0L) {
         mController.onSetFollowupFlag(var1, var3);
      }
   }

   public void onUpdateBadgeRequest() {
      int var1 = MessageList.getUnreadTotalCount(mContext);
      this.updateBadgeProvider(var1);
   }

   public boolean removeAccounts(Context var1, long var2) {
      boolean var4;
      if(var2 < 0L) {
         this.deleteNext();
      } else {
         EmailContent.Account var5 = EmailContent.Account.restoreAccountWithId(var1, var2);
         StringBuilder var6 = (new StringBuilder()).append("++++++++++++++++++++++++++++++++++++++++ removeAccounts() : now syncAccountID = ");
         String var9 = var6.append(var2).toString();
         int var10 = Log.d("Email", var9);
         byte var11 = 0;
         if(var5 == null) {
            int var22 = Log.d("Email", "++++++++++++++++++++++++++++++++++++++++ removeAccounts() : Shit !!! deleteAccount is null !!!!!!!!!!! ");
            var4 = false;
            return var4;
         }

         String var12 = "++++++++++++++++++++++++++++++++++++++++ removeAccounts() : now sevenAccountKey = " + var11;
         int var13 = Log.d("Email", var12);
         int var14 = (int)var5.getSevenAccountKey();
         String[] var15 = new String[1];
         String var16 = String.valueOf(var2);
         var15[0] = var16;
         this.sendBroadcastDeleteAccount(var1, var15);
         if(var14 > 0) {
            Z7MailHandler var20 = Z7MailHandler.getInstance(var1);
            Handler var21 = this.handler;
            var20.removeAccount(var21, var14, (Activity)null);
         } else {
            String var25 = var5.getStoreUri(var1);
            if(var5.getTypeMsg() == 0) {
               Object var26 = null;

               try {
                  Store.getInstance(var25, var1, (Store.PersistentDataCallbacks)var26).delete();
               } catch (MessagingException var40) {
                  var40.printStackTrace();
               }

               Store.removeInstance(var25);
            }

            Uri var30 = EmailContent.Account.CONTENT_URI;
            long var31 = var5.mId;
            Uri var33 = ContentUris.withAppendedId(var30, var31);
            int var34 = var1.getContentResolver().delete(var33, (String)null, (String[])null);
            ContentResolver var35 = var1.getContentResolver();
            Uri var36 = Uri.parse("content://logs/email");
            String[] var37 = new String[1];
            String var38 = Long.toString(var5.mId);
            var37[0] = var38;
            var35.delete(var36, "account_id=?", var37);
            AccountBackupRestore.backupAccounts(var1);
            SecurityPolicy.getInstance(var1).reducePolicies();
         }

         BadgeManager.updateBadgeProvider(mContext);
      }

      var4 = true;
      return var4;
   }

   class TempList {

      long account;
      long id;


      TempList() {}
   }
}
