package com.htc.android.mail.mailservice;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IPowerManager;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.PowerManager.WakeLock;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.htc.android.mail.AbsRequestController;
import com.htc.android.mail.Account;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.MailRequestHandler;
import com.htc.android.mail.Mailbox;
import com.htc.android.mail.Mailboxs;
import com.htc.android.mail.NewMailNotification;
import com.htc.android.mail.Request;
import com.htc.android.mail.RequestController;
import com.htc.android.mail.Util;
import com.htc.android.mail.ll;
import com.htc.android.mail.eassvc.common.EASSyncCommon;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.HashSet;

public class MailService extends Service {

   public static final String ACTION_ACCOUNT_CREATED = "com.htc.android.mail.intent.action.MAIL_SERVICE_ACCOUNT_CREATED";
   private static final String ACTION_CANCEL = "com.htc.android.mail.intent.action.MAIL_SERVICE_CANCEL";
   public static final String ACTION_CANCEL_NOTIFIY = "com.htc.android.mail.intent.action.ACTION_CANCEL_NOTIFIY";
   private static final String ACTION_CHECK_MAIL = "com.htc.android.mail.intent.action.MAIL_SERVICE_WAKEUP";
   private static final String ACTION_CREATE_ACCOUNT = "com.htc.android.mail.intent.action.MAIL_SERVICE_CREATE_ACCOUNT";
   private static final String ACTION_RESCHEDULE = "com.htc.android.mail.intent.action.MAIL_SERVICE_RESCHEDULE";
   private static final String ACTION_RESCHEDULE_IFNEED = "com.htc.android.mail.intent.action.MAIL_SERVICE_RESCHEDULE_IFNEED";
   private static final String ACTION_RESCHEDULE_PEAK = "com.htc.android.mail.intent.action.MAIL_SERVICE_RESCHEDULE_PEAK";
   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   private static final String TAG = "MailService";
   private static final String WAKELOCK_KEY = "MAILSERVICE_PWR_LOCK";
   public static final int checkmore_complete = 1;
   public static final int configuration_changed = 26;
   public static final int contactGroup_changed = 23;
   public static final int deleteMail_complete = 19;
   public static final int delete_complete = 2;
   public static final int dlgCertificateError = 209;
   public static final int dlgConnectionTimeout = 201;
   public static final int dlgHostNotFound = 200;
   public static final int dlgLoginFailed = 205;
   public static final int dlgMailNotInServer = 204;
   public static final int dlgMailboxNotFound = 206;
   public static final int dlgNetworkError = 202;
   public static final int dlgNoSpace = 203;
   public static final int dlgOutOfMemoryAttach = 208;
   public static final int dlgOutOfMemoryMail = 207;
   public static final int dlgServerReplyError = 210;
   public static final int error = 255;
   public static final int fecthExchgAll_complete = 9;
   public static final int fetchAll_complete = 7;
   public static final int fetchImapAll_complete = 8;
   public static final int fetchMultiAttachments_complete = 10;
   public static final int fetchPartByUid_complete = 6;
   public static final int groupMail_changed = 22;
   public static final int importantMail_changed = 21;
   public static final int list_complete = 3;
   private static boolean mWasWakeupWifi = 0;
   public static final int mail_search = 32;
   public static final int markStar_complete = 17;
   public static final int moveMail_complete = 18;
   public static final int move_complete = 5;
   private static long nowWorkingAccount = 65535L;
   public static final int refresh_complete = 0;
   public static final int reloadMailList = 12;
   public static final int reloadMailbody = 11;
   public static final int setAttachmentIndicatorIcon = 27;
   public static final int setReadStatusLocal_complete = 20;
   public static final int setReadStatus_complete = 16;
   public static final int setSort_complete = 25;
   public static final int show_progress_for_loadMail = 15;
   public static final int show_progress_for_readMoreMail = 14;
   public static final int show_progress_for_refresh = 4;
   public static final int startChangeAccount = 29;
   public static final int startDoComposeView = 28;
   public static final int startDoInvokeMailSearch = 31;
   public static final int startDoLaunchAccountList = 30;
   public static final int updateProgressStatus = 13;
   public static final int updateUnreadNumber = 24;
   private final int MSG_WAIT_3G = 101;
   private final int MSG_WAIT_WIFI = 100;
   private long accountId;
   private AlarmManager am;
   private final int m3GWaitTime = 3000;
   private int mCurWifiState;
   private MailService.DelayHandler mDelayHandler;
   private boolean mIsScreenOn;
   private boolean mIsSmartWifi;
   private boolean mIsWifiConnected;
   private boolean mIsWifiLockSet;
   private Looper mLooper;
   private BroadcastReceiver mMailBroadcastReceiver;
   NotificationManager mNM;
   private int mNewUnreadMail = 0;
   private IPowerManager mPowerManagerService;
   private AbsRequestController mRequestController;
   private MailService.RequestHandler mRequestHandler = null;
   private MailService.RequestHandlerMap mRequestHandlerMap;
   private MailService.ServiceIds mServiceIds;
   private final int mWaitTime = 15000;
   private WakeLock mWakeLock;
   private boolean mWasScreenOn;
   private WeakReference<Handler> mWeakRequestHandler;
   private WifiLock mWifiLock;
   private WifiManager mWifiManager;


   public MailService() {
      MailService.DelayHandler var1 = new MailService.DelayHandler((MailService.1)null);
      this.mDelayHandler = var1;
      this.mIsWifiLockSet = (boolean)0;
      this.mIsScreenOn = (boolean)0;
      this.mWasScreenOn = (boolean)0;
      this.mIsWifiConnected = (boolean)0;
      this.mIsSmartWifi = (boolean)0;
      this.mCurWifiState = 4;
      MailService.ServiceIds var2 = new MailService.ServiceIds((MailService.1)null);
      this.mServiceIds = var2;
      MailService.RequestHandlerMap var3 = new MailService.RequestHandlerMap((MailService.1)null);
      this.mRequestHandlerMap = var3;
      MailService.17 var4 = new MailService.17();
      this.mMailBroadcastReceiver = var4;
   }

   // $FF: synthetic method
   static int access$1200(MailService var0) {
      return var0.mNewUnreadMail;
   }

   // $FF: synthetic method
   static int access$1202(MailService var0, int var1) {
      var0.mNewUnreadMail = var1;
      return var1;
   }

   // $FF: synthetic method
   static IPowerManager access$1300(MailService var0) {
      return var0.mPowerManagerService;
   }

   // $FF: synthetic method
   static boolean access$1400(MailService var0) {
      return var0.mIsScreenOn;
   }

   // $FF: synthetic method
   static boolean access$1402(MailService var0, boolean var1) {
      var0.mIsScreenOn = var1;
      return var1;
   }

   // $FF: synthetic method
   static boolean access$1500() {
      return mWasWakeupWifi;
   }

   // $FF: synthetic method
   static boolean access$1502(boolean var0) {
      mWasWakeupWifi = var0;
      return var0;
   }

   // $FF: synthetic method
   static boolean access$1600(MailService var0) {
      return var0.mWasScreenOn;
   }

   // $FF: synthetic method
   static MailService.RequestHandlerMap access$1700(MailService var0) {
      return var0.mRequestHandlerMap;
   }

   // $FF: synthetic method
   static boolean access$1800(MailService var0) {
      return var0.mIsWifiConnected;
   }

   public static void actionCancel(Context var0) {
      Intent var1 = new Intent();
      Intent var2 = var1.setClassName("com.htc.android.mail", "com.htc.android.mail.mailservice.MailService");
      Intent var3 = var1.setAction("com.htc.android.mail.intent.action.MAIL_SERVICE_CANCEL");
      var0.startService(var1);
   }

   public static void actionReschedule(Context var0) {
      Intent var1 = new Intent();
      Intent var2 = var1.setClassName("com.htc.android.mail", "com.htc.android.mail.mailservice.MailService");
      Intent var3 = var1.setAction("com.htc.android.mail.intent.action.MAIL_SERVICE_RESCHEDULE");
      var0.startService(var1);
   }

   public static void actionRescheduleIfNeed(Context var0) {
      Intent var1 = new Intent();
      Intent var2 = var1.setClassName("com.htc.android.mail", "com.htc.android.mail.mailservice.MailService");
      Intent var3 = var1.setAction("com.htc.android.mail.intent.action.MAIL_SERVICE_RESCHEDULE_IFNEED");
      var0.startService(var1);
   }

   public static void actionReschedulePeak(Context var0) {
      Intent var1 = new Intent();
      Intent var2 = var1.setClassName("com.htc.android.mail", "com.htc.android.mail.mailservice.MailService");
      Intent var3 = var1.setAction("com.htc.android.mail.intent.action.MAIL_SERVICE_RESCHEDULE_PEAK");
      var0.startService(var1);
   }

   private void addRegularSyncAlarm(long var1) {
      Intent var3 = new Intent();
      Intent var4 = var3.setClassName("com.htc.android.mail", "com.htc.android.mail.mailservice.MailService");
      Intent var5 = var3.setAction("com.htc.android.mail.intent.action.MAIL_SERVICE_WAKEUP");
      PendingIntent var6 = PendingIntent.getService(this, 0, var3, 0);
      AlarmManager var7 = (AlarmManager)this.getSystemService("alarm");
      this.am = var7;
      AlarmManager var8 = this.am;
      long var9 = SystemClock.elapsedRealtime() + 1800000L;
      var8.set(2, var9, var6);
   }

   private void cancel() {
      AlarmManager var1 = (AlarmManager)this.getSystemService("alarm");
      Intent var2 = new Intent();
      Intent var3 = var2.setClassName("com.htc.android.mail", "com.htc.android.mail.mailservice.MailService");
      Intent var4 = var2.setAction("com.htc.android.mail.intent.action.MAIL_SERVICE_WAKEUP");
      PendingIntent var5 = PendingIntent.getService(this, 0, var2, 0);
      var1.cancel(var5);
   }

   private long getNextPeakSwitchTimeFromNow(Account var1) {
      Date var2 = new Date();
      int var3 = var1.peakDays;
      EASSyncCommon.DaysOfWeek var4 = new EASSyncCommon.DaysOfWeek(var3);
      int var5 = var1.peakTimeStart;
      int var6 = var1.peakTimeEnd;
      int var7 = var2.getHours() * 60;
      int var8 = var2.getMinutes();
      int var9 = var7 + var8;
      int var10 = var9 + 1;
      int var11;
      if(var2.getDay() == 0) {
         var11 = 6;
      } else {
         var11 = var2.getDay() - 1;
      }

      long var12;
      long var14;
      if(var5 == var6) {
         if(var9 == var5) {
            var12 = 60000L;
         } else if(var4.isSet(var11) && var9 < var5) {
            var12 = (long)(var5 - var9) * 60L * 1000L;
         } else {
            long var19 = (long)this.getNextdayFromToday(var11, var1) * 24L * 60L * 60L * 1000L;
            long var21 = (long)var9;
            long var23 = (1440L - var21) * 60L * 1000L;
            long var25 = var19 + var23;
            long var27 = (long)var5 * 60L * 1000L;
            var12 = var25 + var27;
         }

         var14 = var12;
      } else if(var5 < var6) {
         if(this.isInPeakTime(var1, var10)) {
            var14 = (long)(var6 - var9 + 1) * 60L * 1000L;
         } else {
            if(var4.isSet(var11) && var9 < var5) {
               var12 = (long)(var5 - var9) * 60L * 1000L;
            } else {
               long var35 = (long)this.getNextdayFromToday(var11, var1) * 24L * 60L * 60L * 1000L;
               long var37 = (long)var9;
               long var39 = (1440L - var37) * 60L * 1000L;
               long var41 = var35 + var39;
               long var43 = (long)var5 * 60L * 1000L;
               var12 = var41 + var43;
            }

            var14 = var12;
         }
      } else {
         if(this.isInPeakTime(var1, var10)) {
            if(var6 > var9) {
               var12 = (long)(var6 - var9 + 1) * 60L * 1000L;
            } else if(this.getNextdayFromToday(var11, var1) == 0) {
               long var51 = (long)var9;
               long var53 = (1440L - var51) * 60L * 1000L;
               long var55 = (long)(var6 + 1) * 60L * 1000L;
               var12 = var53 + var55;
            } else {
               long var57 = (long)var9;
               var12 = (1440L - var57) * 60L * 1000L;
            }
         } else if(var4.isSet(var11)) {
            var12 = (long)(var5 - var9) * 60L * 1000L;
         } else {
            long var59 = (long)var9;
            long var61 = (1440L - var59) * 60L * 1000L;
            long var66 = (long)this.getNextdayFromToday(var11, var1) * 24L * 60L * 60L * 1000L;
            var12 = var61 + var66;
         }

         var14 = var12;
      }

      return var14;
   }

   private boolean isInPeakTime(Account var1) {
      Date var2 = new Date();
      int var3 = var1.peakDays;
      EASSyncCommon.DaysOfWeek var4 = new EASSyncCommon.DaysOfWeek(var3);
      int var5 = var1.peakTimeStart;
      int var6 = var1.peakTimeEnd;
      int var7 = var2.getHours() * 60;
      int var8 = var2.getMinutes();
      int var9 = var7 + var8;
      int var10;
      if(var2.getDay() == 0) {
         var10 = 6;
      } else {
         var10 = var2.getDay() - 1;
      }

      boolean var11;
      if(!var4.isSet(var10)) {
         var11 = false;
      } else if(var5 == var6) {
         var11 = false;
      } else if(var5 < var6) {
         if(var5 <= var9 && var9 <= var6) {
            var11 = true;
         } else {
            var11 = false;
         }
      } else if(var9 < var5 && var9 > var6) {
         var11 = false;
      } else {
         var11 = true;
      }

      return var11;
   }

   private boolean isInPeakTime(Account var1, int var2) {
      Date var3 = new Date();
      int var4 = var1.peakDays;
      EASSyncCommon.DaysOfWeek var5 = new EASSyncCommon.DaysOfWeek(var4);
      int var6 = var1.peakTimeStart;
      int var7 = var1.peakTimeEnd;
      int var9;
      if(var3.getDay() == 0) {
         var9 = 6;
      } else {
         var9 = var3.getDay() - 1;
      }

      boolean var10;
      if(!var5.isSet(var9)) {
         var10 = false;
      } else if(var6 == var7) {
         var10 = false;
      } else if(var6 < var7) {
         if(var6 <= var2 && var2 <= var7) {
            var10 = true;
         } else {
            var10 = false;
         }
      } else if(var2 < var6 && var2 > var7) {
         var10 = false;
      } else {
         var10 = true;
      }

      return var10;
   }

   private void reschedule(boolean param1) {
      // $FF: Couldn't be decompiled
   }

   private void reschedulePeak(boolean param1) {
      // $FF: Couldn't be decompiled
   }

   public static void setHandler(Handler var0) {}

   private void setPowerLock() {
      if(DEBUG) {
         ll.d("MailService", "Power acquire");
      }

      if(this.mWakeLock != null) {
         if(!this.mWakeLock.isHeld()) {
            this.mWakeLock.acquire();
         }
      }
   }

   private void setPowerRelease() {
      if(DEBUG) {
         ll.d("MailService", "Power release");
      }

      if(this.mWakeLock != null) {
         if(this.mWakeLock.isHeld()) {
            this.mWakeLock.release();
         }
      }
   }

   private void setWifiLock() {
      if(DEBUG) {
         ll.d("MailService", "Wifi acquire");
      }

      if(this.mWifiLock != null && !this.mWifiLock.isHeld()) {
         this.mWifiLock.acquire();
      }

      this.mIsWifiLockSet = (boolean)1;
   }

   private void setWifiRelease() {
      if(DEBUG) {
         ll.d("MailService", "Wifi release");
      }

      if(this.mWifiLock != null) {
         if(this.mWifiLock.isHeld()) {
            this.mWifiLock.release();
         }
      }
   }

   private void showMailNotification(long param1, int param3, long param4) {
      // $FF: Couldn't be decompiled
   }

   public static void updateNotification() {}

   boolean checkNetwork() {
      ConnectivityManager var1 = (ConnectivityManager)this.getSystemService("connectivity");
      NetworkInfo var2 = var1.getNetworkInfo(0);
      NetworkInfo var3 = var1.getNetworkInfo(1);
      NetworkInfo var4 = var1.getNetworkInfo(15);
      byte var5 = 0;
      byte var6 = 0;
      byte var7 = 0;
      byte var8 = 0;
      if(var2 != null) {
         var5 = var2.isConnected();
      }

      if(var3 != null) {
         var6 = var3.isConnected();
      }

      if(false) {
         var7 = null.isConnected();
      }

      if(var4 != null) {
         var8 = var4.isConnected();
      }

      this.mIsWifiConnected = (boolean)var6;
      if(DEBUG) {
         String var9 = "mobile connection:" + var5 + ", wifi connection:" + var6 + ", wimax connection:" + var7 + ", usb connection:" + var8;
         ll.d("MailService", var9);
      }

      boolean var10;
      if(var6 != 1 && var5 != 1 && var7 != 1 && var8 != 1) {
         var10 = false;
      } else {
         var10 = true;
      }

      return var10;
   }

   public int getNextdayFromToday(int var1, Account var2) {
      int var3 = var2.peakDays;
      EASSyncCommon.DaysOfWeek var4 = new EASSyncCommon.DaysOfWeek(var3);
      int var5 = 0;
      int var6 = var1 + 1;
      if(var6 == 7) {
         var6 = 0;
      }

      int var7;
      while(true) {
         if(!var4.isSet(var6)) {
            ++var5;
            ++var6;
            if(var6 == 7) {
               ;
            }

            if(var5 <= 10) {
               continue;
            }

            if(DEBUG) {
               ll.d("MailService", "getNextdayFromToday got error!!");
            }

            var7 = 0;
            break;
         }

         if(DEBUG) {
            String var8 = "dayCount= " + var5;
            ll.d("MailService", var8);
         }

         var7 = var5;
         break;
      }

      return var7;
   }

   public void newPowerLock() {
      if(this.mWakeLock == null) {
         WakeLock var1 = ((PowerManager)this.getSystemService("power")).newWakeLock(1, "MAILSERVICE_PWR_LOCK");
         this.mWakeLock = var1;
         this.mWakeLock.setReferenceCounted((boolean)0);
      }
   }

   public IBinder onBind(Intent var1) {
      return null;
   }

   public final void onCreate() {
      if(DEBUG) {
         ll.d("MailService", "new Mail fetch service start!!");
      }

      Mail.setDefaultCharset(this);
      Looper var1 = Looper.myLooper();
      this.mLooper = var1;
      WifiManager var2 = (WifiManager)this.getSystemService("wifi");
      this.mWifiManager = var2;
      IntentFilter var3 = new IntentFilter();
      var3.addAction("android.intent.action.SCREEN_ON");
      BroadcastReceiver var4 = this.mMailBroadcastReceiver;
      this.registerReceiver(var4, var3);
   }

   public final void onDestroy() {
      if(DEBUG) {
         ll.d("MailService", "onDestroy");
      }

      super.onDestroy();
      BroadcastReceiver var1 = this.mMailBroadcastReceiver;
      this.unregisterReceiver(var1);
   }

   public final void onStart(Intent param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public void startCheck(long var1, long var3, int var5) {
      if(DEBUG) {
         StringBuilder var6 = (new StringBuilder()).append("enter startCheck1, accountId = ");
         StringBuilder var9 = var6.append(var1).append(",mailboxId = ");
         String var12 = var9.append(var3).toString();
         ll.d("MailService", var12);
      }

      Cursor var13 = null;
      boolean var152 = false;

      MailService.ServiceIds var23;
      label411: {
         label412: {
            label413: {
               try {
                  var152 = true;
                  String var15 = "connectivity";
                  ConnectivityManager var16 = (ConnectivityManager)this.getSystemService(var15);
                  if(var16 != null && !var16.getBackgroundDataSetting()) {
                     if(DEBUG) {
                        ll.i("MailService", "BackgroundData disabled");
                        var152 = false;
                     } else {
                        var152 = false;
                     }
                     break label413;
                  }

                  ContentResolver var27 = this.getContentResolver();
                  Uri var28 = MailProvider.sAccountsURI;
                  var13 = var27.query(var28, (String[])null, "_del=-1", (String[])null, (String)null);
                  if(var13.getCount() <= 0) {
                     if(DEBUG) {
                        ll.d("MailService", "No account");
                        var152 = false;
                     } else {
                        var152 = false;
                     }
                     break label412;
                  }

                  if(DEBUG) {
                     ll.d("MailService", "enter startCheck2");
                  }

                  while(true) {
                     if(!var13.moveToNext()) {
                        if(DEBUG) {
                           ll.d("MailService", "end startCheck");
                           var152 = false;
                        } else {
                           var152 = false;
                        }
                        break;
                     }

                     int var35 = var13.getColumnIndexOrThrow("_protocol");
                     int var36 = var13.getInt(var35);
                     byte var37 = 4;
                     if(var36 == var37) {
                        if(var1 != Long.MAX_VALUE) {
                           continue;
                        }

                        if(DEBUG) {
                           ll.d("MailService", "startCheck - exchange");
                        }
                     }

                     int var38 = var13.getColumnIndexOrThrow("_nextfetchtime");
                     long var39 = var13.getLong(var38);
                     int var41 = var13.getColumnIndexOrThrow("_id");
                     long var42 = var13.getLong(var41);
                     if(!this.mIsScreenOn && Util.getAccountPowerSavingPref(this.getApplicationContext(), var42) == 1) {
                        if(DEBUG) {
                           ll.d("MailService", "not to fetch mail because enabled power saving");
                        }
                     } else {
                        int var54 = var13.getColumnIndexOrThrow("_lastupdatetime");
                        long var55 = var13.getLong(var54);
                        int var57 = var13.getColumnIndexOrThrow("_poll_frequency_number");
                        int var58 = var13.getInt(var57);
                        if(DEBUG) {
                           StringBuilder var59 = (new StringBuilder()).append("enter startCheck: id = ").append(var42).append(", freqNum = ");
                           StringBuilder var61 = var59.append(var58).append(", fetchTime = ");
                           StringBuilder var64 = var61.append(var39).append(",");
                           long var65 = SystemClock.elapsedRealtime();
                           StringBuilder var67 = var64.append(var65).append(", lastupdateTime = ");
                           StringBuilder var70 = var67.append(var55).append(", current: ");
                           long var71 = System.currentTimeMillis();
                           StringBuilder var73 = var70.append(var71).append(", timeInterval =");
                           int var74 = Account.getPollValue(var58) * '\uea60';
                           String var75 = var73.append(var74).toString();
                           ll.d("MailService", var75);
                        }

                        if(var1 != Long.MAX_VALUE && var1 != var42) {
                           if(var1 != 0L || var58 <= 0) {
                              continue;
                           }

                           long var76 = SystemClock.elapsedRealtime();
                           if(var39 >= var76) {
                              continue;
                           }
                        }

                        if(DEBUG) {
                           String var78 = "Fetch mail for account:" + var42;
                           ll.d("MailService", var78);
                        }

                        nowWorkingAccount = var42;
                        Account var79 = new Account;
                        byte var83 = 1;
                        var79.<init>(this, var13, (boolean)var83);
                        RequestController var84 = RequestController.getInstance(this);
                        this.mRequestController = var84;
                        MailService.RequestHandler var85 = new MailService.RequestHandler;
                        long var86 = nowWorkingAccount;
                        Looper var88 = this.mLooper;
                        var85.<init>(var86, var88);
                        this.mRequestHandler = var85;
                        MailService.RequestHandler var95 = this.mRequestHandler;
                        AbsRequestController var96 = this.mRequestController;
                        var95.setRequestController(var96);
                        MailService.RequestHandler var97 = this.mRequestHandler;
                        WeakReference var98 = new WeakReference(var97);
                        this.mWeakRequestHandler = var98;
                        AbsRequestController var99 = this.mRequestController;
                        WeakReference var100 = this.mWeakRequestHandler;
                        var99.addWeakHandler(var100);
                        AbsRequestController var101 = this.mRequestController;
                        WeakReference var102 = this.mWeakRequestHandler;
                        var101.registerWeakMailRequestHandler(var79, var102);
                        Request var103 = new Request();
                        WeakReference var104 = this.mWeakRequestHandler;
                        var103.weakHandler = var104;
                        byte var105 = 0;
                        var103.messageWhat = var105;
                        byte var106 = 1;
                        var103.command = var106;
                        Bundle var107 = new Bundle();
                        Mailboxs var108 = var79.getMailboxs();
                        Mailbox var111 = var108.getMailboxById(var3);
                        if(var111 == null) {
                           var111 = var79.getMailboxs().getMailboxById(9223372036854775802L);
                        }

                        if(var111 == null) {
                           var111 = var79.getMailboxs().getDefaultMailbox();
                        }

                        String var113 = "Mailbox";
                        var107.putParcelable(var113, var111);
                        var103.parameter = var107;
                        var103.serviceStartId = var5;
                        long var118 = var79.id;
                        var103.setAccountId(var118);
                        byte var123 = 0;
                        var103.removeable = (boolean)var123;
                        AbsRequestController var124 = this.mRequestController;
                        MailService.RequestHandlerMap var125 = this.mRequestHandlerMap;
                        WeakReference var126 = this.mWeakRequestHandler;
                        MailService.CallbackIfNotAdded var129 = new MailService.CallbackIfNotAdded(var124, var125, var126, var5, var79);
                        var103.setCallbackIfNotAdded(var129);
                        if(DEBUG) {
                           StringBuilder var132 = (new StringBuilder()).append("account: ");
                           String var133 = var79.emailAddress;
                           String var134 = var132.append(var133).toString();
                           ll.d("MailService", var134);
                        }

                        MailService.RequestHandlerMap var135 = this.mRequestHandlerMap;
                        MailService.RequestHandler var136 = this.mRequestHandler;
                        var135.put(var5, var136);
                        AbsRequestController var141 = this.mRequestController;
                        byte var143 = 0;
                        var141.refreshOrCheckMoreMail(var103, (boolean)var143);
                        var79.updateFetchTime();
                        nowWorkingAccount = 65535L;
                     }
                  }
               } finally {
                  if(var152) {
                     if(DEBUG) {
                        ll.d("MailService", "Do finally");
                     }

                     if(var1 >= 0L) {
                        byte var46 = 1;
                        this.reschedule((boolean)var46);
                     }

                     if(var13 != null && !var13.isClosed()) {
                        var13.close();
                     }

                     if(this.mIsWifiLockSet) {
                        this.setWifiRelease();
                        byte var47 = 0;
                        this.mIsWifiLockSet = (boolean)var47;
                     }

                     this.setPowerRelease();
                     MailService.RequestHandlerMap var48 = this.mRequestHandlerMap;
                     if(var48.size(var5) == 0) {
                        MailService.ServiceIds var51 = this.mServiceIds;
                        var51.removeAndTryStop(var5);
                     }

                  }
               }

               if(DEBUG) {
                  ll.d("MailService", "Do finally");
               }

               if(var1 >= 0L) {
                  byte var146 = 1;
                  this.reschedule((boolean)var146);
               }

               if(var13 != null && !var13.isClosed()) {
                  var13.close();
               }

               if(this.mIsWifiLockSet) {
                  this.setWifiRelease();
                  byte var147 = 0;
                  this.mIsWifiLockSet = (boolean)var147;
               }

               this.setPowerRelease();
               MailService.RequestHandlerMap var148 = this.mRequestHandlerMap;
               if(var148.size(var5) != 0) {
                  return;
               }

               var23 = this.mServiceIds;
               break label411;
            }

            if(DEBUG) {
               ll.d("MailService", "Do finally");
            }

            if(var1 >= 0L) {
               byte var18 = 1;
               this.reschedule((boolean)var18);
            }

            if(var13 != null && !var13.isClosed()) {
               var13.close();
            }

            if(this.mIsWifiLockSet) {
               this.setWifiRelease();
               byte var19 = 0;
               this.mIsWifiLockSet = (boolean)var19;
            }

            this.setPowerRelease();
            MailService.RequestHandlerMap var20 = this.mRequestHandlerMap;
            if(var20.size(var5) != 0) {
               return;
            }

            var23 = this.mServiceIds;
            break label411;
         }

         if(DEBUG) {
            ll.d("MailService", "Do finally");
         }

         if(var1 >= 0L) {
            byte var30 = 1;
            this.reschedule((boolean)var30);
         }

         if(var13 != null && !var13.isClosed()) {
            var13.close();
         }

         if(this.mIsWifiLockSet) {
            this.setWifiRelease();
            byte var31 = 0;
            this.mIsWifiLockSet = (boolean)var31;
         }

         this.setPowerRelease();
         MailService.RequestHandlerMap var32 = this.mRequestHandlerMap;
         if(var32.size(var5) != 0) {
            return;
         }

         var23 = this.mServiceIds;
      }

      var23.removeAndTryStop(var5);
   }

   class 9 implements Runnable {

      // $FF: synthetic field
      final int val$startId;


      9(int var2) {
         this.val$startId = var2;
      }

      public void run() {
         MailService.this.reschedulePeak((boolean)0);
         MailService.this.setPowerRelease();
         MailService.ServiceIds var1 = MailService.this.mServiceIds;
         int var2 = this.val$startId;
         var1.removeAndTryStop(var2);
      }
   }

   private class RequestHandlerMap {

      private SparseArray<HashSet<Handler>> mRequestSparseArray;


      private RequestHandlerMap() {
         SparseArray var2 = new SparseArray();
         this.mRequestSparseArray = var2;
      }

      // $FF: synthetic method
      RequestHandlerMap(MailService.1 var2) {
         this();
      }

      public void put(int var1, Handler var2) {
         synchronized(this){}

         try {
            if(MailService.DEBUG) {
               StringBuilder var3 = (new StringBuilder()).append("put request: ").append(var1).append(", Handler: ");
               String var4 = var2.toString();
               String var5 = var3.append(var4).toString();
               ll.d("MailService", var5);
            }

            HashSet var6 = (HashSet)this.mRequestSparseArray.get(var1);
            if(var6 == null) {
               var6 = new HashSet();
            }

            var6.add(var2);
            this.mRequestSparseArray.put(var1, var6);
         } finally {
            ;
         }

      }

      public void remove(int param1, Handler param2) {
         // $FF: Couldn't be decompiled
      }

      public int size(int param1) {
         // $FF: Couldn't be decompiled
      }
   }

   private class RequestHandler extends MailRequestHandler {

      private long id = 65535L;
      private AbsRequestController mRequestController;


      public RequestHandler(long var2) {
         this.id = var2;
      }

      public RequestHandler(long var2, Looper var4) {
         super(var4);
         this.id = var2;
      }

      public void onRefreshComplete(Account param1, Request param2, Message param3) {
         // $FF: Couldn't be decompiled
      }

      public void setRequestController(AbsRequestController var1) {
         this.mRequestController = var1;
      }
   }

   private class ServiceIds {

      private SparseIntArray mServiceIdArray;


      private ServiceIds() {
         SparseIntArray var2 = new SparseIntArray();
         this.mServiceIdArray = var2;
      }

      // $FF: synthetic method
      ServiceIds(MailService.1 var2) {
         this();
      }

      public void add(int var1) {
         synchronized(this){}

         try {
            if(MailService.DEBUG) {
               String var2 = "add serviceId: " + var1;
               ll.d("MailService", var2);
            }

            this.mServiceIdArray.put(var1, 0);
         } finally {
            ;
         }

      }

      public void removeAndTryStop(int var1) {
         synchronized(this){}

         try {
            if(MailService.DEBUG) {
               String var2 = "remove statrtId: " + var1;
               ll.d("MailService", var2);
            }

            this.mServiceIdArray.delete(var1);
            if(this.mServiceIdArray.size() == 0) {
               if(MailService.DEBUG) {
                  String var3 = "stop Self: " + var1;
                  ll.d("MailService", var3);
               }

               MailService.this.stopSelf();
            }
         } finally {
            ;
         }

      }

      public int size() {
         synchronized(this){}
         boolean var5 = false;

         int var1;
         try {
            var5 = true;
            var1 = this.mServiceIdArray.size();
            var5 = false;
         } finally {
            if(var5) {
               ;
            }
         }

         return var1;
      }
   }

   private class DelayHandler extends Handler {

      private DelayHandler() {}

      // $FF: synthetic method
      DelayHandler(MailService.1 var2) {
         this();
      }

      public void handleMessage(Message param1) {
         // $FF: Couldn't be decompiled
      }

      class 3 implements Runnable {

         // $FF: synthetic field
         final int val$startId;


         3(int var2) {
            this.val$startId = var2;
         }

         public void run() {
            MailService var1 = MailService.this;
            int var2 = this.val$startId;
            var1.startCheck(0L, 65535L, var2);
         }
      }

      class 2 implements Runnable {

         // $FF: synthetic field
         final int val$startId;


         2(int var2) {
            this.val$startId = var2;
         }

         public void run() {
            MailService.this.reschedule((boolean)1);
            MailService.this.setPowerRelease();
            MailService.ServiceIds var1 = MailService.this.mServiceIds;
            int var2 = this.val$startId;
            var1.removeAndTryStop(var2);
         }
      }

      class 1 implements Runnable {

         // $FF: synthetic field
         final int val$startId;


         1(int var2) {
            this.val$startId = var2;
         }

         public void run() {
            MailService var1 = MailService.this;
            int var2 = this.val$startId;
            var1.startCheck(0L, 65535L, var2);
         }
      }

      class 4 implements Runnable {

         // $FF: synthetic field
         final int val$startId;


         4(int var2) {
            this.val$startId = var2;
         }

         public void run() {
            MailService.this.reschedule((boolean)1);
            MailService.this.setPowerRelease();
            MailService.ServiceIds var1 = MailService.this.mServiceIds;
            int var2 = this.val$startId;
            var1.removeAndTryStop(var2);
         }
      }
   }

   private static class CallbackIfNotAdded implements Runnable {

      private Account mAccount;
      private AbsRequestController mRequestController;
      private MailService.RequestHandlerMap mRequestHandlerMap;
      private int mStartId;
      private WeakReference<Handler> mWeakRequestHandler;


      public CallbackIfNotAdded(AbsRequestController var1, MailService.RequestHandlerMap var2, WeakReference<Handler> var3, int var4, Account var5) {
         this.mRequestController = var1;
         this.mRequestHandlerMap = var2;
         this.mWeakRequestHandler = var3;
         this.mStartId = var4;
         this.mAccount = var5;
      }

      public void run() {
         if(MailService.DEBUG) {
            ll.i("MailService", "DestroyRunnable run:");
         }

         AbsRequestController var1 = this.mRequestController;
         WeakReference var2 = this.mWeakRequestHandler;
         var1.removeWeakHandler(var2);
         AbsRequestController var3 = this.mRequestController;
         Account var4 = this.mAccount;
         WeakReference var5 = this.mWeakRequestHandler;
         var3.unregisterWeakMailRequestHandler(var4, var5);
         Handler var6 = (Handler)this.mWeakRequestHandler.get();
         if(var6 != null) {
            MailService.RequestHandlerMap var7 = this.mRequestHandlerMap;
            int var8 = this.mStartId;
            var7.remove(var8, var6);
         }
      }
   }

   class 3 implements Runnable {

      // $FF: synthetic field
      final int val$startId;


      3(int var2) {
         this.val$startId = var2;
      }

      public void run() {
         MailService.this.reschedule((boolean)1);
         MailService.this.setPowerRelease();
         MailService.ServiceIds var1 = MailService.this.mServiceIds;
         int var2 = this.val$startId;
         var1.removeAndTryStop(var2);
      }
   }

   class 10 implements Runnable {

      // $FF: synthetic field
      final int val$startId;


      10(int var2) {
         this.val$startId = var2;
      }

      public void run() {
         MailService.this.reschedule((boolean)0);
         MailService.this.setPowerRelease();
         MailService.ServiceIds var1 = MailService.this.mServiceIds;
         int var2 = this.val$startId;
         var1.removeAndTryStop(var2);
      }
   }

   class 4 implements Runnable {

      // $FF: synthetic field
      final int val$startId;


      4(int var2) {
         this.val$startId = var2;
      }

      public void run() {
         MailService.this.reschedule((boolean)1);
         MailService.this.setPowerRelease();
         MailService.ServiceIds var1 = MailService.this.mServiceIds;
         int var2 = this.val$startId;
         var1.removeAndTryStop(var2);
      }
   }

   class 11 implements Runnable {

      // $FF: synthetic field
      final long val$mailboxId;
      // $FF: synthetic field
      final int val$startId;


      11(long var2, int var4) {
         this.val$mailboxId = var2;
         this.val$startId = var4;
      }

      public void run() {
         long var1 = Account.find139Account();
         if(var1 != 65535L) {
            MailService var3 = MailService.this;
            long var4 = this.val$mailboxId;
            int var6 = this.val$startId;
            var3.startCheck(var1, var4, var6);
         }
      }
   }

   class 1 implements Runnable {

      // $FF: synthetic field
      final int val$startId;


      1(int var2) {
         this.val$startId = var2;
      }

      public void run() {
         Context var1 = MailService.this.getApplicationContext();
         long var2 = MailService.this.accountId;
         NewMailNotification.clearNotification(var1, var2);
         if(MailService.DEBUG) {
            StringBuilder var5 = (new StringBuilder()).append("cancel notify>");
            long var6 = MailService.this.accountId;
            String var8 = var5.append(var6).toString();
            ll.d("MailService", var8);
         }

         MailService.ServiceIds var9 = MailService.this.mServiceIds;
         int var10 = this.val$startId;
         var9.removeAndTryStop(var10);
      }
   }

   class 12 implements Runnable {

      // $FF: synthetic field
      final String val$email;
      // $FF: synthetic field
      final long val$mailboxId;
      // $FF: synthetic field
      final int val$startId;


      12(String var2, long var3, int var5) {
         this.val$email = var2;
         this.val$mailboxId = var3;
         this.val$startId = var5;
      }

      public void run() {
         long var1 = Account.findAccountByEmail(this.val$email);
         if(var1 != 65535L) {
            MailService var3 = MailService.this;
            long var4 = this.val$mailboxId;
            int var6 = this.val$startId;
            var3.startCheck(var1, var4, var6);
         }
      }
   }

   class 2 implements Runnable {

      // $FF: synthetic field
      final long val$checkid;
      // $FF: synthetic field
      final int val$startId;


      2(long var2, int var4) {
         this.val$checkid = var2;
         this.val$startId = var4;
      }

      public void run() {
         MailService.this.setWifiLock();
         MailService var1 = MailService.this;
         long var2 = this.val$checkid;
         int var4 = this.val$startId;
         var1.startCheck(var2, 65535L, var4);
      }
   }

   class 13 implements Runnable {

      // $FF: synthetic field
      final Long val$id;
      // $FF: synthetic field
      final long val$mailboxId;
      // $FF: synthetic field
      final int val$startId;


      13(Long var2, long var3, int var5) {
         this.val$id = var2;
         this.val$mailboxId = var3;
         this.val$startId = var5;
      }

      public void run() {
         MailService var1 = MailService.this;
         long var2 = this.val$id.longValue();
         long var4 = this.val$mailboxId;
         int var6 = this.val$startId;
         var1.startCheck(var2, var4, var6);
      }
   }

   class 7 implements Runnable {

      // $FF: synthetic field
      final int val$startId;


      7(int var2) {
         this.val$startId = var2;
      }

      public void run() {
         MailService.this.cancel();
         MailService.this.setPowerRelease();
         MailService.ServiceIds var1 = MailService.this.mServiceIds;
         int var2 = this.val$startId;
         var1.removeAndTryStop(var2);
      }
   }

   class 14 implements Runnable {

      // $FF: synthetic field
      final int val$startId;


      14(int var2) {
         this.val$startId = var2;
      }

      public void run() {
         MailService.this.reschedule((boolean)1);
         MailService.this.setPowerRelease();
         MailService.ServiceIds var1 = MailService.this.mServiceIds;
         int var2 = this.val$startId;
         var1.removeAndTryStop(var2);
      }
   }

   class 8 implements Runnable {

      // $FF: synthetic field
      final int val$startId;


      8(int var2) {
         this.val$startId = var2;
      }

      public void run() {
         MailService.this.reschedule((boolean)1);
         MailService.this.setPowerRelease();
         MailService.ServiceIds var1 = MailService.this.mServiceIds;
         int var2 = this.val$startId;
         var1.removeAndTryStop(var2);
      }
   }

   class 15 implements Runnable {

      // $FF: synthetic field
      final Bundle val$accBundle;
      // $FF: synthetic field
      final int val$startId;


      15(Bundle var2, int var3) {
         this.val$accBundle = var2;
         this.val$startId = var3;
      }

      public void run() {
         Context var1 = MailService.this.getApplicationContext();
         Bundle var2 = this.val$accBundle;
         Uri var3 = Account.createAccountByEmail(var1, var2);
         if(MailService.DEBUG && var3 != null) {
            String var4 = "acount uri:" + var3;
            ll.d("MailService", var4);
         }

         Intent var5 = new Intent("com.htc.android.mail.intent.action.MAIL_SERVICE_ACCOUNT_CREATED");
         String var6 = this.val$accBundle.getString("provider");
         var5.putExtra("privoder", var6);
         String var8 = this.val$accBundle.getString("email");
         var5.putExtra("email", var8);
         MailService.this.sendBroadcast(var5);
         MailService.this.setPowerRelease();
         MailService.ServiceIds var10 = MailService.this.mServiceIds;
         int var11 = this.val$startId;
         var10.removeAndTryStop(var11);
      }
   }

   class 5 implements Runnable {

      // $FF: synthetic field
      final int val$startId;


      5(int var2) {
         this.val$startId = var2;
      }

      public void run() {
         MailService.this.reschedule((boolean)1);
         MailService.this.setPowerRelease();
         MailService.ServiceIds var1 = MailService.this.mServiceIds;
         int var2 = this.val$startId;
         var1.removeAndTryStop(var2);
      }
   }

   class 16 implements Runnable {

      // $FF: synthetic field
      final int val$startId;
      // $FF: synthetic field
      final int val$state;


      16(int var2, int var3) {
         this.val$state = var2;
         this.val$startId = var3;
      }

      public void run() {
         if(MailService.DEBUG) {
            StringBuilder var1 = (new StringBuilder()).append("SMART_WIFI_STATE_CHANGED_ACTION :");
            int var2 = this.val$state;
            String var3 = var1.append(var2).toString();
            ll.d("MailService", var3);
         }

         if(this.val$state == 1) {
            boolean var4 = (boolean)(MailService.this.mIsSmartWifi = (boolean)1);
            if(MailService.DEBUG) {
               ll.d("MailService", "set mIsSmartWifi = true");
            }

            MailService.this.reschedulePeak((boolean)0);
         } else {
            boolean var7 = (boolean)(MailService.this.mIsSmartWifi = (boolean)0);
            if(MailService.DEBUG) {
               ll.d("MailService", "set mIsSmartWifi = false");
            }

            MailService.this.reschedulePeak((boolean)0);
         }

         MailService.this.setPowerRelease();
         MailService.ServiceIds var5 = MailService.this.mServiceIds;
         int var6 = this.val$startId;
         var5.removeAndTryStop(var6);
      }
   }

   class 6 implements Runnable {

      // $FF: synthetic field
      final int val$startId;


      6(int var2) {
         this.val$startId = var2;
      }

      public void run() {
         MailService.this.reschedule((boolean)1);
         MailService.this.setPowerRelease();
         MailService.ServiceIds var1 = MailService.this.mServiceIds;
         int var2 = this.val$startId;
         var1.removeAndTryStop(var2);
      }
   }

   class 17 extends BroadcastReceiver {

      17() {}

      public void onReceive(Context var1, Intent var2) {
         String var3 = var2.getAction();
         if(MailService.DEBUG) {
            String var4 = "broadcast received action=" + var3;
            ll.d("MailService", var4);
         }

         if(var3.equals("android.intent.action.SCREEN_ON")) {
            boolean var5 = (boolean)(MailService.this.mWasScreenOn = (boolean)1);
            if(MailService.DEBUG) {
               ll.d("MailService", "get action_screen_on intent");
            }
         }
      }
   }
}
