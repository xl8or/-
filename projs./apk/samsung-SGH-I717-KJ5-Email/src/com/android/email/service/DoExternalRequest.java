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
   public static DoExternalRequest mDoExternalRequest = null;
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

   public static DoExternalRequest getInstance() {
      if(mDoExternalRequest == null) {
         mDoExternalRequest = new DoExternalRequest();
      }

      return mDoExternalRequest;
   }

   private void sendBroadcastDeleteAccount(Context var1, String[] var2) {
      Intent var3 = new Intent("com.sec.android.socialhub.action.EXTERNAL_DB_UPDATE");
      Intent var4 = var3.putExtra("intentType", 400);
      if(var2 != null) {
         var3.putExtra("id_array", var2);
      }

      Intent var6 = var3.putExtra("action", 6);
      Intent var7 = var3.putExtra("status", (boolean)1);
      var1.sendBroadcast(var3);
      Email.logd("DoExternalRequest", "+_+_+_+_+_+_+_+_+_+_+_+_+_+_+ sendBroadcastDeleteAccount() ");
   }

   public static void sendBroadcastDeleteMsg(String[] var0) {
      Intent var1 = new Intent("com.sec.android.socialhub.action.EXTERNAL_DB_UPDATE");
      Intent var2 = var1.putExtra("intentType", 400);
      if(var0 != null) {
         var1.putExtra("id_array", var0);
         StringBuilder var4 = (new StringBuilder()).append("+_+_+_+_+_+_+_+_+_+_+_+_+_+_+ sendBroadcastDeleteMsg() ");
         String var5 = var0[0];
         String var6 = var4.append(var5).toString();
         Email.logd("DoExternalRequest", var6);
      }

      Intent var7 = var1.putExtra("action", 3);
      Intent var8 = var1.putExtra("status", (boolean)1);
      mContext.sendBroadcast(var1);
   }

   public static void sendBroadcastInsertMsg(String[] var0) {
      Intent var1 = new Intent("com.sec.android.socialhub.action.EXTERNAL_DB_UPDATE");
      Intent var2 = var1.putExtra("intentType", 400);
      if(var0 != null) {
         var1.putExtra("id_array", var0);
         StringBuilder var4 = (new StringBuilder()).append("+_+_+_+_+_+_+_+_+_+_+_+_+_+_+ sendBroadcastInsertMsg() ");
         String var5 = var0[0];
         String var6 = var4.append(var5).toString();
         Email.logd("DoExternalRequest", var6);
      }

      Intent var7 = var1.putExtra("action", 1);
      Intent var8 = var1.putExtra("status", (boolean)1);
      mContext.sendBroadcast(var1);
   }

   public static void sendBroadcastMarkAsRead(String[] var0, boolean var1) {
      Intent var2 = new Intent("com.sec.android.socialhub.action.EXTERNAL_DB_UPDATE");
      Intent var3 = var2.putExtra("intentType", 400);
      if(var0 != null) {
         var2.putExtra("id_array", var0);
      }

      Intent var5 = var2.putExtra("action", 2);
      Intent var6 = var2.putExtra("status", (boolean)1);
      var2.putExtra("is_read", var1);
      mContext.sendBroadcast(var2);
      String var8 = "+_+_+_+_+_+_+_+_+_+_+_+_+_+_+ sendBroadcastMarkAsRead() to " + var1;
      Email.logd("Email", var8);
   }

   public static void sendBroadcastSetAnswered(String[] var0, boolean var1) {
      Intent var2 = new Intent("com.sec.android.socialhub.action.EXTERNAL_DB_UPDATE");
      Intent var3 = var2.putExtra("intentType", 400);
      if(var0 != null) {
         var2.putExtra("id_array", var0);
      }

      Intent var5 = var2.putExtra("action", 11);
      Intent var6 = var2.putExtra("status", (boolean)1);
      var2.putExtra("is_favorite", var1);
      mContext.sendBroadcast(var2);
      String var8 = "+_+_+_+_+_+_+_+_+_+_+_+_+_+_+ sendBroadcastSetFavorite() to " + var1;
      Email.logd("DoExternalRequest", var8);
   }

   public static void sendBroadcastSetFavorite(String[] var0, boolean var1) {
      Intent var2 = new Intent("com.sec.android.socialhub.action.EXTERNAL_DB_UPDATE");
      Intent var3 = var2.putExtra("intentType", 400);
      if(var0 != null) {
         var2.putExtra("id_array", var0);
      }

      Intent var5 = var2.putExtra("action", 8);
      Intent var6 = var2.putExtra("status", (boolean)1);
      var2.putExtra("is_favorite", var1);
      mContext.sendBroadcast(var2);
      String var8 = "+_+_+_+_+_+_+_+_+_+_+_+_+_+_+ sendBroadcastSetFavorite() to " + var1;
      Email.logd("DoExternalRequest", var8);
   }

   public static void sendBroadcastSetFollowUpFlag(String[] var0, int var1) {
      Intent var2 = new Intent("com.sec.android.socialhub.action.EXTERNAL_DB_UPDATE");
      Intent var3 = var2.putExtra("intentType", 400);
      if(var0 != null) {
         var2.putExtra("id_array", var0);
      }

      Intent var5 = var2.putExtra("action", 10);
      Intent var6 = var2.putExtra("status", (boolean)1);
      var2.putExtra("status_followupflag", var1);
      mContext.sendBroadcast(var2);
      int var8 = Log.d("Email", "+_+_+_+_+_+_+_+_+_+_+_+_+_+_+ sendBroadcastSetFollowUpFlag() ");
   }

   public static void sendBroadcastSetHasAttach(String[] var0, boolean var1) {
      Intent var2 = new Intent("com.sec.android.socialhub.action.EXTERNAL_DB_UPDATE");
      Intent var3 = var2.putExtra("intentType", 400);
      if(var0 != null) {
         var2.putExtra("id_array", var0);
      }

      Intent var5 = var2.putExtra("action", 12);
      String var6 = "status";
      byte var7;
      if(!var1) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      var2.putExtra(var6, (boolean)var7);
      mContext.sendBroadcast(var2);
      Email.logd("DoExternalRequest", "+_+_+_+_+_+_+_+_+_+_+_+_+_+_+ sendBroadcastSetHasAttach()");
   }

   public static void sendBroadcastSevenMessageAdd(Context var0, long var1) {
      String[] var3 = new String[1];
      String var4 = Long.toString(var1);
      var3[0] = var4;
      Intent var5 = new Intent("com.sec.android.socialhub.action.EXTERNAL_DB_UPDATE");
      Intent var6 = var5.putExtra("intentType", 400);
      var5.putExtra("id_array", var3);
      Intent var8 = var5.putExtra("action", 1);
      Intent var9 = var5.putExtra("status", (boolean)1);
      var0.sendBroadcast(var5);
      Email.logd("DoExternalRequest", "+_+_+_+_+_+_+_+_+_+_+_+_+_+_+ sendBroadcastSevenMessageAdd() ");
   }

   public static void sendBroadcastSyncCompleted(Context var0, long var1) {
      Intent var3 = new Intent("com.sec.android.socialhub.action.EXTERNAL_DB_UPDATE");
      Intent var4 = var3.putExtra("action", 7);
      String[] var5 = new String[1];
      String var6 = Long.toString(var1);
      var5[0] = var6;
      var3.putExtra("id_array", var5);
      Intent var8 = var3.putExtra("intentType", 400);
      Intent var9 = var3.putExtra("status", (boolean)1);
      var0.sendBroadcast(var3);
      Email.logd("DoExternalRequest", "+_+_+_+_+_+_+_+_+_+_+_+_+_+_+ sendBroadcastSyncCompleted() ");
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
                     boolean var24 = var6.add(var21);
                  } else {
                     int var25 = 0;

                     while(true) {
                        int var26 = var6.size();
                        if(var25 >= var26 || ((Long)var6.get(var25)).longValue() == var13) {
                           int var29 = var6.size();
                           if(var25 == var29) {
                              Long var32 = Long.valueOf(var13);
                              boolean var35 = var6.add(var32);
                           }
                           break;
                        }

                        ++var25;
                     }
                  }
               }

               int var36 = 0;

               while(true) {
                  int var37 = var6.size();
                  if(var36 >= var37) {
                     var1.clear();
                     this.onUpdateBadgeRequest();
                     return;
                  }

                  int var40 = 0;

                  while(true) {
                     int var41 = var7.size();
                     if(var40 >= var41) {
                        Controller var52 = mController;
                        long var53 = ((Long)var6.get(var36)).longValue();
                        byte var59 = 0;
                        var52.deleteMessage(var5, var53, (boolean)var59);
                        var5.clear();
                        ++var36;
                        break;
                     }

                     long var44 = ((Long)var6.get(var36)).longValue();
                     long var46 = ((DoExternalRequest.TempList)var7.get(var40)).account;
                     if(var44 == var46) {
                        Long var48 = Long.valueOf(((DoExternalRequest.TempList)var7.get(var40)).id);
                        boolean var51 = var5.add(var48);
                     }

                     ++var40;
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

   public boolean removeAccounts(Context var1, String[] var2) {
      int var3 = 0;

      boolean var20;
      while(true) {
         int var4 = var2.length;
         if(var3 >= var4) {
            this.sendBroadcastDeleteAccount(var1, var2);
            var20 = true;
            break;
         }

         long var5 = Long.parseLong(var2[var3]);
         if(var5 < 0L) {
            this.deleteNext();
         } else {
            EmailContent.Account var10 = EmailContent.Account.restoreAccountWithId(var1, var5);
            String var11 = "++++++++++++++++++++++++++++++++++++++++ removeAccounts() : now syncAccountID = " + var5;
            int var12 = Log.d("Email", var11);
            byte var13 = 0;
            if(var10 == null) {
               int var19 = Log.d("Email", "++++++++++++++++++++++++++++++++++++++++ removeAccounts() : Shit !!! deleteAccount is null !!!!!!!!!!! ");
               var20 = false;
               break;
            }

            String var14 = "++++++++++++++++++++++++++++++++++++++++ removeAccounts() : now sevenAccountKey = " + var13;
            int var15 = Log.d("Email", var14);
            int var16 = (int)var10.getSevenAccountKey();
            if(var16 > 0) {
               Z7MailHandler var17 = Z7MailHandler.getInstance(var1);
               Handler var18 = this.handler;
               var17.removeAccount(var18, var16, (Activity)null);
            } else {
               String var23 = var10.getStoreUri(var1);
               if(var10.getTypeMsg() == 0) {
                  Object var24 = null;

                  try {
                     Store.getInstance(var23, var1, (Store.PersistentDataCallbacks)var24).delete();
                  } catch (MessagingException var38) {
                     var38.printStackTrace();
                  }

                  Store.removeInstance(var23);
               }

               Uri var28 = EmailContent.Account.CONTENT_URI;
               long var29 = var10.mId;
               Uri var31 = ContentUris.withAppendedId(var28, var29);
               int var32 = var1.getContentResolver().delete(var31, (String)null, (String[])null);
               ContentResolver var33 = var1.getContentResolver();
               Uri var34 = Uri.parse("content://logs/email");
               String[] var35 = new String[1];
               String var36 = Long.toString(var10.mId);
               var35[0] = var36;
               var33.delete(var34, "account_id=?", var35);
               AccountBackupRestore.backupAccounts(var1);
               SecurityPolicy.getInstance(var1).reducePolicies();
            }

            BadgeManager.updateBadgeProvider(mContext);
         }

         ++var3;
      }

      return var20;
   }

   class TempList {

      long account;
      long id;


      TempList() {}
   }
}
