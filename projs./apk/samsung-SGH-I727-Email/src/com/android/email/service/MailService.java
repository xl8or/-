package com.android.email.service;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import com.android.email.AccountBackupRestore;
import com.android.email.Controller;
import com.android.email.Email;
import com.android.email.mail.MessagingException;
import com.android.email.provider.EmailContent;
import com.android.email.service.DoExternalRequest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class MailService extends Service {

   public static final String ACTION_ACCOUNT_DELETE = "com.android.email.intent.action.ACTION_ACCOUNT_DELETE";
   private static final String ACTION_CANCEL = "com.android.email.intent.action.MAIL_SERVICE_CANCEL";
   private static final String ACTION_CHECK_MAIL = "com.android.email.intent.action.MAIL_SERVICE_WAKEUP";
   public static final String ACTION_EXTERNAL_DB_ACCOUNT_DELETE = "com.android.email.intent.action.ACTION_EXTERNAL_ACCOUNT_DELETE";
   public static final String ACTION_EXTERNAL_DB_EMAIL_DELETE = "com.android.email.intent.action.ACTION_EXTERNAL_EMAIL_DELETE";
   public static final String ACTION_EXTERNAL_DB_MARK_AS_READ = "com.android.email.intent.action.ACTION_EXTERNAL_MARK_AS_READ";
   public static final String ACTION_EXTERNAL_DB_SET_FAVORITE = "com.android.email.intent.action.ACTION_EXTERNAL_DB_SET_FAVORITE";
   public static final String ACTION_EXTERNAL_DB_SET_FOLLOWUPFLAG = "com.android.email.intent.action.ACTION_EXTERNAL_DB_SET_FOLLOWUPFLAG";
   private static final String ACTION_NOTIFY_MAIL = "com.android.email.intent.action.MAIL_SERVICE_NOTIFY";
   public static final String ACTION_REFRESH_ONE_ACCOUNT = "com.android.email.intent.action.MAIL_SERVICE_REFRESH_ONE_ACCOUNT";
   private static final String ACTION_RESCHEDULE = "com.android.email.intent.action.MAIL_SERVICE_RESCHEDULE";
   private static final String ACTION_SYNC_ONE_ACCOUNT = "com.android.email.intent.action.MAIL_SERVICE_SYNC_ONE_ACCOUNT";
   private static final String ALERTONCALL_MODE = "alertoncall_mode";
   private static final int ALERTONCALL_SOUND = 1;
   private static boolean AccountRefreshRequst;
   public static final String BADGE_APPS_CLASS = "class";
   public static final String BADGE_APPS_COUNT = "badgecount";
   public static final String BADGE_APPS_PACKAGE = "package";
   public static final String BADGE_AUTHORITY = "com.sec.badge";
   public static final String BADGE_TABLE_APPS = "apps";
   public static final String BROADCAST_NOTIFY_UPDATE = "com.sec.android.socialhub.action.EXTERNAL_DB_UPDATE";
   private static final boolean DEBUG_FORCE_QUICK_REFRESH = false;
   private static final String EXTRA_ACCOUNT_INFO = "com.android.email.intent.extra.ACCOUNT_INFO";
   private static final String EXTRA_CHECK_ACCOUNT = "com.android.email.intent.extra.ACCOUNT";
   private static final String EXTRA_DEBUG_WATCHDOG = "com.android.email.intent.extra.WATCHDOG";
   private static final String LOG_TAG = "Email-MailService";
   private static final String[] NEW_MESSAGE_COUNT_PROJECTION;
   public static int NOTIFICATION_ID_EXCHANGE_CALENDAR_ADDED = 3;
   public static int NOTIFICATION_ID_NEW_MESSAGES = 1;
   public static int NOTIFICATION_ID_SECURITY_NEEDED = 2;
   public static int NOTIFICATION_ID_SENDING_FAIL_OUTOFMEMORY = 5;
   public static final int NOTIFICATION_ID_SENDING_MESSAGE = 4;
   private static long RefreshRequestAccountId;
   private static final int WATCHDOG_DELAY = 600000;
   static ContentValues mClearNewMessages;
   private static HashMap<Long, MailService.AccountSyncReport> mSyncReports;
   private Controller.Result mControllerCallback;
   public DoExternalRequest mDoExternalRequest;
   private int mStartId;


   static {
      String[] var0 = new String[]{"newMessageCount"};
      NEW_MESSAGE_COUNT_PROJECTION = var0;
      AccountRefreshRequst = (boolean)0;
      RefreshRequestAccountId = 65535L;
      mSyncReports = new HashMap();
      mClearNewMessages = new ContentValues();
      ContentValues var1 = mClearNewMessages;
      Integer var2 = Integer.valueOf(0);
      var1.put("newMessageCount", var2);
   }

   public MailService() {
      MailService.ControllerResults var1 = new MailService.ControllerResults();
      this.mControllerCallback = var1;
      this.mDoExternalRequest = null;
   }

   public static void actionCancel(Context var0) {
      Intent var1 = new Intent();
      var1.setClass(var0, MailService.class);
      Intent var3 = var1.setAction("com.android.email.intent.action.MAIL_SERVICE_CANCEL");
      var0.startService(var1);
   }

   public static void actionNotifyNewMessages(Context var0, long var1) {
      Intent var3 = new Intent("com.android.email.intent.action.MAIL_SERVICE_NOTIFY");
      var3.setClass(var0, MailService.class);
      var3.putExtra("com.android.email.intent.extra.ACCOUNT", var1);
      var0.startService(var3);
   }

   public static void actionRefreshOneAccount(Context var0, long var1) {
      Intent var3 = new Intent();
      var3.setClass(var0, MailService.class);
      Intent var5 = var3.setAction("com.android.email.intent.action.MAIL_SERVICE_REFRESH_ONE_ACCOUNT");
      var3.putExtra("com.android.email.intent.extra.ACCOUNT", var1);
      if(var1 != 65535L) {
         RefreshRequestAccountId = var1;
         AccountRefreshRequst = (boolean)1;
      }

      var0.startService(var3);
   }

   public static void actionReschedule(Context var0) {
      Intent var1 = new Intent();
      var1.setClass(var0, MailService.class);
      Intent var3 = var1.setAction("com.android.email.intent.action.MAIL_SERVICE_RESCHEDULE");
      var0.startService(var1);
   }

   public static void actionSyncOneAccount(Context var0, long var1) {
      Intent var3 = new Intent();
      var3.setClass(var0, MailService.class);
      Intent var5 = var3.setAction("com.android.email.intent.action.MAIL_SERVICE_SYNC_ONE_ACCOUNT");
      var3.putExtra("com.android.email.intent.extra.ACCOUNT", var1);
      if(var1 != 65535L) {
         RefreshRequestAccountId = var1;
         AccountRefreshRequst = (boolean)1;
      }

      var0.startService(var3);
   }

   private void cancel() {
      AlarmManager var1 = (AlarmManager)this.getSystemService("alarm");
      PendingIntent var2 = this.createAlarmIntent(65535L, (long[])null, (boolean)0);
      var1.cancel(var2);
   }

   private void notifyNewMessages(long param1) {
      // $FF: Couldn't be decompiled
   }

   private void refreshSyncReports() {
      HashMap var1 = mSyncReports;
      synchronized(var1) {
         HashMap var2 = mSyncReports;
         HashMap var3 = new HashMap(var2);
         mSyncReports.clear();
         this.setupSyncReportsLocked(65535L);
         Iterator var4 = mSyncReports.values().iterator();

         while(var4.hasNext()) {
            MailService.AccountSyncReport var5 = (MailService.AccountSyncReport)var4.next();
            Long var6 = Long.valueOf(var5.accountId);
            MailService.AccountSyncReport var7 = (MailService.AccountSyncReport)var3.get(var6);
            if(var7 != null) {
               long var8 = var7.prevSyncTime;
               var5.prevSyncTime = var8;
               if(var5.syncInterval > 0 && var5.prevSyncTime != 0L) {
                  long var10 = var5.prevSyncTime;
                  long var12 = (long)(var5.syncInterval * 1000 * 60);
                  long var14 = var10 + var12;
                  var5.nextSyncTime = var14;
               }
            }
         }

      }
   }

   public static void resetNewMessageCount(Context var0, long var1) {
      HashMap var3 = mSyncReports;
      synchronized(var3) {
         Iterator var4 = mSyncReports.values().iterator();

         while(true) {
            if(!var4.hasNext()) {
               break;
            }

            MailService.AccountSyncReport var5 = (MailService.AccountSyncReport)var4.next();
            if(var1 != 65535L) {
               long var6 = var5.accountId;
               if(var1 != var6) {
                  continue;
               }
            }

            var5.numNewMessages = 0;
         }
      }

      Uri var9;
      if(var1 == 65535L) {
         var9 = EmailContent.Account.CONTENT_URI;
      } else {
         var9 = ContentUris.withAppendedId(EmailContent.Account.CONTENT_URI, var1);
      }

      ContentResolver var10 = var0.getContentResolver();
      ContentValues var11 = mClearNewMessages;
      var10.update(var9, var11, (String)null, (String[])null);
   }

   private void setWatchdog(long var1, AlarmManager var3) {
      PendingIntent var4 = this.createAlarmIntent(var1, (long[])null, (boolean)1);
      long var5 = SystemClock.elapsedRealtime() + 600000L;
      var3.set(2, var5, var4);
   }

   private void setupSyncReportsLocked(long param1) {
      // $FF: Couldn't be decompiled
   }

   private boolean syncOneAccount(Controller var1, long var2, int var4) {
      long var5 = EmailContent.Mailbox.findMailboxOfType(this, var2, 0);
      boolean var7;
      if(var5 == 65535L) {
         var7 = false;
      } else {
         if(AccountRefreshRequst) {
            long var8 = RefreshRequestAccountId;
            if(var2 == var8) {
               Intent var10 = new Intent("com.android.email.action.ACCOUNT_REFRESHING");
               var10.putExtra("ACCOUNT_ID", var2);
               Intent var12 = var10.putExtra("STATUS_START", (boolean)1);
               this.sendBroadcast(var10);
            }
         }

         long var13 = (long)var4;
         Controller.Result var15 = this.mControllerCallback;
         byte var16;
         if(AccountRefreshRequst) {
            var16 = 1;
         } else {
            var16 = 0;
         }

         var1.serviceCheckMail(var2, var5, var13, var15, (boolean)var16);
         var7 = true;
      }

      return var7;
   }

   PendingIntent createAlarmIntent(long var1, long[] var3, boolean var4) {
      Intent var5 = new Intent();
      var5.setClass(this, MailService.class);
      Intent var7 = var5.setAction("com.android.email.intent.action.MAIL_SERVICE_WAKEUP");
      var5.putExtra("com.android.email.intent.extra.ACCOUNT", var1);
      var5.putExtra("com.android.email.intent.extra.ACCOUNT_INFO", var3);
      if(var4 != null) {
         Intent var10 = var5.putExtra("com.android.email.intent.extra.WATCHDOG", (boolean)1);
      }

      return PendingIntent.getService(this, 0, var5, 134217728);
   }

   public IBinder onBind(Intent var1) {
      return null;
   }

   public void onDestroy() {
      super.onDestroy();
      Controller var1 = Controller.getInstance(this.getApplication());
      Controller.Result var2 = this.mControllerCallback;
      var1.removeResultCallback(var2);
   }

   public int onStartCommand(Intent var1, int var2, int var3) {
      super.onStartCommand(var1, var2, var3);
      AccountBackupRestore.restoreAccountsIfNeeded(this);
      this.mStartId = var3;
      String var6 = var1.getAction();
      Controller var7 = Controller.getInstance(this.getApplication());
      Controller.Result var8 = this.mControllerCallback;
      var7.addResultCallback(var8);
      long var18;
      if("com.android.email.intent.action.MAIL_SERVICE_WAKEUP".equals(var6)) {
         this.restoreSyncReports(var1);
         String var12 = "alarm";
         AlarmManager var13 = (AlarmManager)this.getSystemService(var12);
         String var15 = "com.android.email.intent.extra.ACCOUNT";
         long var16 = 65535L;
         var18 = var1.getLongExtra(var15, var16);
         if(Email.DEBUG) {
            StringBuilder var20 = (new StringBuilder()).append("action: check mail for id=");
            String var23 = var20.append(var18).toString();
            int var24 = Log.d("Email-MailService", var23);
         }

         if(var18 >= 0L) {
            this.setWatchdog(var18, var13);
         }

         if(var18 == 65535L || !this.syncOneAccount(var7, var18, var3)) {
            if(var18 != 65535L) {
               byte var37 = 0;
               this.updateAccountReport(var18, var37);
            }

            this.reschedule(var13);
            this.stopSelf(var3);
         }
      } else if("com.android.email.intent.action.MAIL_SERVICE_CANCEL".equals(var6)) {
         if(Email.DEBUG) {
            int var43 = Log.d("Email-MailService", "action: cancel");
         }

         this.cancel();
         this.stopSelf(var3);
      } else if("com.android.email.intent.action.MAIL_SERVICE_RESCHEDULE".equals(var6)) {
         if(Email.DEBUG) {
            int var46 = Log.d("Email-MailService", "action: reschedule");
         }

         String var48 = "notification";
         NotificationManager var49 = (NotificationManager)this.getSystemService(var48);
         int var50 = NOTIFICATION_ID_NEW_MESSAGES;
         var49.cancel(var50);
         this.refreshSyncReports();
         String var54 = "alarm";
         AlarmManager var55 = (AlarmManager)this.getSystemService(var54);
         this.reschedule(var55);
         this.stopSelf(var3);
      } else if("com.android.email.intent.action.MAIL_SERVICE_NOTIFY".equals(var6)) {
         String var61 = "com.android.email.intent.extra.ACCOUNT";
         long var62 = 65535L;
         long var64 = var1.getLongExtra(var61, var62);
         ContentResolver var66 = this.getContentResolver();
         Uri var67 = ContentUris.withAppendedId(EmailContent.Account.CONTENT_URI, var64);
         String[] var68 = NEW_MESSAGE_COUNT_PROJECTION;
         Cursor var69 = var66.query(var67, var68, (String)null, (String[])null, (String)null);
         int var70 = 0;
         boolean var257 = false;

         label391: {
            int var71;
            label390: {
               try {
                  var257 = true;
                  if(var69.moveToFirst()) {
                     var71 = var69.getInt(0);
                     var257 = false;
                     break label390;
                  }

                  var257 = false;
               } finally {
                  if(var257) {
                     var69.close();
                  }
               }

               var64 = 65535L;
               break label391;
            }

            var70 = var71;
         }

         var69.close();
         if(Email.DEBUG) {
            StringBuilder var72 = (new StringBuilder()).append("notify accountId=");
            String var73 = Long.toString(var64);
            StringBuilder var74 = var72.append(var73).append(" count=");
            String var76 = var74.append(var70).toString();
            int var77 = Log.d("Email-MailService", var76);
         }

         if(var64 != 65535L) {
            MailService.AccountSyncReport var82 = this.updateAccountReport(var64, var70);
            this.notifyNewMessages(var64);
         }

         this.stopSelf(var3);
      } else if(!"com.android.email.intent.action.MAIL_SERVICE_SYNC_ONE_ACCOUNT".equals(var6) && !"com.android.email.intent.action.MAIL_SERVICE_REFRESH_ONE_ACCOUNT".equals(var6)) {
         byte var260;
         int var118;
         int var117;
         String[] var115;
         if("com.android.email.intent.action.ACTION_EXTERNAL_EMAIL_DELETE".equals(var6)) {
            String var114 = "id_array";
            var115 = var1.getStringArrayExtra(var114);
            int var116 = Log.d("Email-MailService", "+++++++++++++++++++++ received action: ACTION_EXTERNAL_DB_EMAIL_DELETE ");
            if(var115 != null && var115.length > 0) {
               var117 = var115.length;
               var118 = 0;

               while(true) {
                  int var119 = var115.length;
                  if(var118 >= var119) {
                     if(this.mDoExternalRequest == null) {
                        DoExternalRequest var129 = new DoExternalRequest();
                        this.mDoExternalRequest = var129;
                        DoExternalRequest var130 = this.mDoExternalRequest;
                        DoExternalRequest.setController(this.getApplicationContext());
                     }

                     if(var117 != 0 && ((Object[])var117).length > 0) {
                        HashSet var131 = new HashSet();
                        var260 = 0;

                        while(true) {
                           int var132 = ((Object[])var117).length;
                           if(var260 >= var132) {
                              DoExternalRequest var145 = this.mDoExternalRequest;
                              var145.onMultiDeleteRequest(var131);
                              return 2;
                           }

                           StringBuilder var135 = (new StringBuilder()).append("+++++++++++++++++++++ start message delete: id = ");
                           long var136 = (long)((Object[])var117)[var260];
                           String var138 = var135.append(var136).toString();
                           int var139 = Log.d("Email-MailService", var138);
                           Long var140 = Long.valueOf((long)((Object[])var117)[var260]);
                           boolean var143 = var131.add(var140);
                           int var144 = var260 + 1;
                        }
                     }
                     break;
                  }

                  if(var115[var118] == false) {
                     int var122 = Log.d("Email-MailService", "+++++++++++++++++++++ message is null !!!!!!!!!!!!!!!!!!!!!!!!!! ");
                  } else {
                     StringBuilder var123 = (new StringBuilder()).append("+++++++++++++++++++++ start message delete: id = ");
                     String var124 = var115[var118];
                     String var125 = var123.append(var124).toString();
                     int var126 = Log.d("Email-MailService", var125);
                     long var127 = Long.parseLong(var115[var118]);
                     ((Object[])var117)[var118] = (int)var127;
                  }

                  ++var118;
               }
            }
         } else if("com.android.email.intent.action.ACTION_EXTERNAL_ACCOUNT_DELETE".equals(var6)) {
            String var148 = "id_array";
            String[] var149 = var1.getStringArrayExtra(var148);
            if(var149 != null && var149.length > 0) {
               var18 = Long.parseLong(var149[0]);
               int var150 = Log.d("Email-MailService", "+++++++++++++++++++++ received action: ACTION_EXTERNAL_DB_ACCOUNT_DELETE ");
               StringBuilder var151 = (new StringBuilder()).append("+++++++++++++++++++++ start account delete : id = ");
               String var154 = var151.append(var18).toString();
               int var155 = Log.d("Email-MailService", var154);
               if(this.mDoExternalRequest == null) {
                  DoExternalRequest var156 = new DoExternalRequest();
                  this.mDoExternalRequest = var156;
                  DoExternalRequest var157 = this.mDoExternalRequest;
                  DoExternalRequest.setController(this.getApplicationContext());
               }

               DoExternalRequest var158 = this.mDoExternalRequest;
               Context var159 = this.getBaseContext();
               boolean var164 = var158.removeAccounts(var159, var18);
               Email.setNotifyUiAccountsChanged((boolean)1);
            }
         } else if("com.android.email.intent.action.ACTION_EXTERNAL_MARK_AS_READ".equals(var6)) {
            String var166 = "is_read";
            byte var167 = 0;
            boolean var168 = var1.getBooleanExtra(var166, (boolean)var167);
            String var170 = "id_array";
            var115 = var1.getStringArrayExtra(var170);
            int var171 = Log.d("Email-MailService", "+++++++++++++++++++++ received action: ACTION_EXTERNAL_DB_MARK_AS_READ ");
            if(var115 != null && var115.length > 0) {
               var117 = var115.length;
               var260 = 0;

               while(true) {
                  int var172 = var115.length;
                  if(var260 >= var172) {
                     if(this.mDoExternalRequest == null) {
                        DoExternalRequest var178 = new DoExternalRequest();
                        this.mDoExternalRequest = var178;
                        DoExternalRequest var179 = this.mDoExternalRequest;
                        DoExternalRequest.setController(this.getApplicationContext());
                     }

                     if(var117 != 0 && ((Object[])var117).length > 0) {
                        var118 = 0;

                        while(true) {
                           int var180 = ((Object[])var117).length;
                           if(var118 >= var180) {
                              return 2;
                           }

                           StringBuilder var183 = (new StringBuilder()).append("+++++++++++++++++++++ start mark as read : id = ");
                           long var184 = (long)((Object[])var117)[var118];
                           String var186 = var183.append(var184).toString();
                           int var187 = Log.d("Email-MailService", var186);
                           DoExternalRequest var188 = this.mDoExternalRequest;
                           long var189 = (long)((Object[])var117)[var118];
                           var188.onMarkAsReadRequest(var189, var168);
                           ++var118;
                        }
                     }
                     break;
                  }

                  long var175 = Long.parseLong(var115[var260]);
                  ((Object[])var117)[var260] = (int)var175;
                  int var177 = var260 + 1;
               }
            }
         } else if("com.android.email.intent.action.ACTION_EXTERNAL_DB_SET_FAVORITE".equals(var6)) {
            String var196 = "is_favorite";
            byte var197 = 0;
            boolean var198 = var1.getBooleanExtra(var196, (boolean)var197);
            String var200 = "id_array";
            var115 = var1.getStringArrayExtra(var200);
            int var201 = Log.d("Email-MailService", "+++++++++++++++++++++ received action: ACTION_EXTERNAL_DB_SET_FAVORITE ");
            if(var115 != null && var115.length > 0) {
               var117 = var115.length;
               var260 = 0;

               while(true) {
                  int var202 = var115.length;
                  if(var260 >= var202) {
                     if(this.mDoExternalRequest == null) {
                        DoExternalRequest var208 = new DoExternalRequest();
                        this.mDoExternalRequest = var208;
                        DoExternalRequest var209 = this.mDoExternalRequest;
                        DoExternalRequest.setController(this.getApplicationContext());
                     }

                     if(var117 != 0 && ((Object[])var117).length > 0) {
                        var118 = 0;

                        while(true) {
                           int var210 = ((Object[])var117).length;
                           if(var118 >= var210) {
                              return 2;
                           }

                           StringBuilder var213 = (new StringBuilder()).append("+++++++++++++++++++++ start set as favorite : id = ");
                           long var214 = (long)((Object[])var117)[var118];
                           String var216 = var213.append(var214).toString();
                           int var217 = Log.d("Email-MailService", var216);
                           DoExternalRequest var218 = this.mDoExternalRequest;
                           long var219 = (long)((Object[])var117)[var118];
                           var218.onSetFavoriteRequest(var219, var198);
                           ++var118;
                        }
                     }
                     break;
                  }

                  long var205 = Long.parseLong(var115[var260]);
                  ((Object[])var117)[var260] = (int)var205;
                  int var207 = var260 + 1;
               }
            }
         } else if("com.android.email.intent.action.ACTION_EXTERNAL_DB_SET_FOLLOWUPFLAG".equals(var6)) {
            int var225 = Log.e("Email-MailService", "+++++++++++++++++++++ received action: ACTION_EXTERNAL_DB_SET_FOLLOWUPFLAG ");
            String var227 = "status_followupflag";
            byte var228 = 0;
            int var229 = var1.getIntExtra(var227, var228);
            String var231 = "id_array";
            var115 = var1.getStringArrayExtra(var231);
            if(var115 != null && var115.length > 0) {
               var117 = var115.length;
               var260 = 0;

               while(true) {
                  int var232 = var115.length;
                  if(var260 >= var232) {
                     if(this.mDoExternalRequest == null) {
                        DoExternalRequest var238 = new DoExternalRequest();
                        this.mDoExternalRequest = var238;
                        DoExternalRequest var239 = this.mDoExternalRequest;
                        DoExternalRequest.setController(this.getApplicationContext());
                     }

                     if(var117 != 0 && ((Object[])var117).length > 0) {
                        var118 = 0;

                        while(true) {
                           int var240 = ((Object[])var117).length;
                           if(var118 >= var240) {
                              return 2;
                           }

                           StringBuilder var243 = (new StringBuilder()).append("+++++++++++++++++++++ start set as favorite : id = ");
                           long var244 = (long)((Object[])var117)[var118];
                           String var246 = var243.append(var244).toString();
                           int var247 = Log.d("Email-MailService", var246);
                           DoExternalRequest var248 = this.mDoExternalRequest;
                           long var249 = (long)((Object[])var117)[var118];
                           var248.onSetFollowUpFlagRequest(var249, var229);
                           ++var118;
                        }
                     }
                     break;
                  }

                  long var235 = Long.parseLong(var115[var260]);
                  ((Object[])var117)[var260] = (int)var235;
                  int var237 = var260 + 1;
               }
            }
         }
      } else {
         if("com.android.email.intent.action.MAIL_SERVICE_SYNC_ONE_ACCOUNT".equals(var6)) {
            try {
               int var89 = Log.e("Email-MailService", "onStartCommand: sleep 1000");
               Thread.sleep(1000L);
            } catch (InterruptedException var258) {
               int var112 = Log.e("Email-MailService", "onStartCommand: InterruptedException");
            }
         }

         this.restoreSyncReports(var1);
         String var91 = "com.android.email.intent.extra.ACCOUNT";
         long var92 = 65535L;
         var18 = var1.getLongExtra(var91, var92);
         if(Email.DEBUG) {
            StringBuilder var94 = (new StringBuilder()).append("action: sync one account for id=");
            String var97 = var94.append(var18).toString();
            int var98 = Log.d("Email-MailService", var97);
         }

         if(var18 == 65535L || !this.syncOneAccount(var7, var18, var3)) {
            if(var18 != 65535L) {
               byte var107 = 0;
               this.updateAccountReport(var18, var107);
            }

            this.stopSelf(var3);
         }
      }

      return 2;
   }

   void reschedule(AlarmManager param1) {
      // $FF: Couldn't be decompiled
   }

   void restoreSyncReports(Intent var1) {
      this.setupSyncReports(65535L);
      HashMap var2 = mSyncReports;
      synchronized(var2) {
         long[] var3 = var1.getLongArrayExtra("com.android.email.intent.extra.ACCOUNT_INFO");
         if(var3 == null) {
            int var4 = Log.d("Email-MailService", "no data in intent to restore");
         } else {
            int var5 = var3.length;
            int var6 = 0;

            while(var6 < var5) {
               int var7 = var6 + 1;
               long var8 = var3[var6];
               var6 = var7 + 1;
               long var10 = var3[var7];
               HashMap var12 = mSyncReports;
               Long var13 = Long.valueOf(var8);
               MailService.AccountSyncReport var14 = (MailService.AccountSyncReport)var12.get(var13);
               if(var14 != null && var14.prevSyncTime == 0L) {
                  var14.prevSyncTime = var10;
                  if(var14.syncInterval > 0 && var14.prevSyncTime != 0L) {
                     long var15 = var14.prevSyncTime;
                     long var17 = (long)(var14.syncInterval * 1000 * 60);
                     long var19 = var15 + var17;
                     var14.nextSyncTime = var19;
                  }
               }
            }

         }
      }
   }

   void setupSyncReports(long var1) {
      HashMap var3 = mSyncReports;
      synchronized(var3) {
         this.setupSyncReportsLocked(var1);
      }
   }

   MailService.AccountSyncReport updateAccountReport(long var1, int var3) {
      this.setupSyncReports(var1);
      HashMap var4 = mSyncReports;
      synchronized(var4) {
         HashMap var5 = mSyncReports;
         Long var6 = Long.valueOf(var1);
         MailService.AccountSyncReport var7 = (MailService.AccountSyncReport)var5.get(var6);
         MailService.AccountSyncReport var27;
         if(var7 == null) {
            StringBuilder var8 = (new StringBuilder()).append("No account to update for id=");
            String var9 = Long.toString(var1);
            String var10 = var8.append(var9).toString();
            int var11 = Log.d("Email-MailService", var10);
            var27 = null;
         } else {
            long var12 = SystemClock.elapsedRealtime();
            var7.prevSyncTime = var12;
            if(var7.syncInterval > 0) {
               long var14 = var7.prevSyncTime;
               long var16 = (long)(var7.syncInterval * 1000 * 60);
               long var18 = var14 + var16;
               var7.nextSyncTime = var18;
            }

            if(var3 != -1) {
               int var20 = var7.numNewMessages + var3;
               var7.numNewMessages = var20;
            }

            if(Email.DEBUG) {
               StringBuilder var21 = (new StringBuilder()).append("update account ");
               String var22 = var7.toString();
               String var23 = var21.append(var22).toString();
               int var24 = Log.d("Email-MailService", var23);
            }

            var27 = var7;
         }

         return var27;
      }
   }

   // $FF: synthetic class
   static class 1 {
   }

   class ControllerResults implements Controller.Result {

      ControllerResults() {}

      public void Attachment_StatusStart_Progress(MessagingException var1, long var2, long var4, long var6, long var8) {}

      public void OoOCallback(MessagingException var1, long var2, int var4, Bundle var5) {}

      public void deviceInformationCallback(MessagingException var1, long var2, int var4) {}

      public void emptyTrashCallback(MessagingException var1, long var2, int var4) {}

      public void folderCommandCallback(int var1, MessagingException var2, String var3) {}

      public void loadAttachmentCallback(MessagingException var1, long var2, long var4, int var6) {}

      public void loadMessageForViewCallback(MessagingException var1, long var2, int var4) {}

      public void loadMoreCallback(MessagingException var1, long var2, int var4) {}

      public void moveConvAlwaysCallback(MessagingException var1, byte[] var2, int var3, int var4) {}

      public void moveConvesationCallback(MessagingException var1, long var2) {}

      public void moveMessageCallback(MessagingException var1, long var2) {}

      public void sendMailCallback(MessagingException var1, long var2, long var4, int var6) {}

      public void serviceCheckMailCallback(MessagingException var1, long var2, long var4, int var6, long var7) {
         if(var1 != null || var6 == 100) {
            if(MailService.AccountRefreshRequst) {
               Intent var9 = new Intent("com.android.email.action.ACCOUNT_REFRESHING");
               var9.putExtra("ACCOUNT_ID", var2);
               Intent var11 = var9.putExtra("STATUS_START", (boolean)0);
               MailService.this.sendBroadcast(var9);
               boolean var12 = (boolean)(MailService.AccountRefreshRequst = (boolean)0);
               long var13 = MailService.RefreshRequestAccountId = 65535L;
            }

            if(var1 != null) {
               MailService.this.updateAccountReport(var2, -1);
            }

            AlarmManager var16 = (AlarmManager)MailService.this.getSystemService("alarm");
            MailService.this.reschedule(var16);
            int var17 = MailService.this.mStartId;
            if(var7 != 0L) {
               var17 = (int)var7;
            }

            MailService.this.stopSelf(var17);
         }
      }

      public void updateMailboxCallback(MessagingException var1, long var2, long var4, int var6, int var7) {
         if(var1 == null) {
            byte var10 = 100;
            if(var6 != var10) {
               return;
            }
         }

         MailService var11 = MailService.this;
         byte var14 = 0;
         long var15 = EmailContent.Mailbox.findMailboxOfType(var11, var2, var14);
         if(var4 == var15) {
            byte var19 = 100;
            if(var6 == var19) {
               MailService var20 = MailService.this;
               var20.updateAccountReport(var2, var7);
               if(var7 > 0) {
                  MailService var25 = MailService.this;
                  var25.notifyNewMessages(var2);
               }

               if(var7 == 0) {
                  int var28 = 0;
                  Iterator var29 = MailService.mSyncReports.values().iterator();

                  while(var29.hasNext()) {
                     MailService.AccountSyncReport var30 = (MailService.AccountSyncReport)var29.next();
                     if(var30.numNewMessages != 0) {
                        int var31 = var30.numNewMessages;
                        var7 += var31;
                        StringBuilder var32 = (new StringBuilder()).append("Acc Id =");
                        long var33 = var30.accountId;
                        StringBuilder var35 = var32.append(var33).append(" count: ");
                        int var36 = var30.numNewMessages;
                        String var37 = var35.append(var36).toString();
                        Email.logd("MailService", var37);
                        ++var28;
                     }
                  }

                  String var38 = "Total Count : " + var28;
                  Email.logd("MailService", var38);
                  if(var28 == 0) {
                     Email.logd("MailService", "Notifcation canceled");
                     NotificationManager var39 = (NotificationManager)MailService.this.getSystemService("notification");
                     if(var39 != null) {
                        int var40 = MailService.NOTIFICATION_ID_NEW_MESSAGES;
                        var39.cancel(var40);
                     }
                  }
               }
            } else {
               MailService var41 = MailService.this;
               byte var44 = -1;
               var41.updateAccountReport(var2, var44);
            }
         }

         Email.updateMailboxRefreshTime(var4);
      }

      public void updateMailboxListCallback(int var1, MessagingException var2, long var3, int var5) {}
   }

   private static class AccountSyncReport {

      long accountId;
      String displayName;
      long nextSyncTime;
      boolean notify;
      int numNewMessages;
      long prevSyncTime;
      Uri ringtoneUri;
      int syncInterval;
      boolean vibrate;
      boolean vibrateWhenSilent;


      private AccountSyncReport() {}

      // $FF: synthetic method
      AccountSyncReport(MailService.1 var1) {
         this();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         String var2 = this.displayName;
         StringBuilder var3 = var1.append(var2).append(": id=");
         long var4 = this.accountId;
         StringBuilder var6 = var3.append(var4).append(" prevSync=");
         long var7 = this.prevSyncTime;
         StringBuilder var9 = var6.append(var7).append(" nextSync=");
         long var10 = this.nextSyncTime;
         StringBuilder var12 = var9.append(var10).append(" numNew=");
         int var13 = this.numNewMessages;
         return var12.append(var13).toString();
      }
   }
}
