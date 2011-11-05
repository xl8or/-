package com.htc.android.mail.ulog;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.app.IHtcAppUsageStatsService;
import com.android.internal.app.IHtcAppUsageStatsService.Stub;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.ll;
import com.htc.android.mail.ulog.MULogReceiver;
import com.htc.android.mail.ulog.Record;
import com.htc.android.mail.ulog.ULogFileMgr;
import com.htc.utils.ulog.ReusableULogData;
import com.htc.utils.ulog.ULog;
import com.htc.utils.ulog.ULogTags;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class MULogMgr {

   private static Object ULogFileLock = new Object();
   private final String ACCOUNT_TYPE = "account_type";
   private final String APP_ID = "com.htc.android.mail";
   private final String CATEGORY_EAS_CREATATION = "EAS_success";
   private final String CATEGORY_MAILCOUNT = "mail_count";
   private final String CATEGORY_MAILSIZE = "default_mailsize";
   private final String CATEGORY_UPDATE_SCHEDULE = "update_schedule";
   private final boolean DEBUG = 1;
   private final String PACKAGE_NAME = "com.htc.android.mail";
   private final int POP3orIMAP = 3;
   private final String TAG = "MULogMgr";
   private final String TAG_ANALYTIC = "ANALYTIC_Mail";
   private final int htmlFormat = 2;
   private IHtcAppUsageStatsService mHtcAppUsageStatsService;
   private ULogFileMgr mULogFileMgr;
   private String[] mailSizeList = null;
   private String[] scheduleList = null;
   private final int textFormat = 1;
   private ReusableULogData uLogData;


   public MULogMgr() {}

   private String getAccountType(long var1) {
      String var3;
      if(Mail.isIMAP4(MailProvider.getAccount(var1).protocol)) {
         var3 = "imap";
      } else {
         var3 = "pop3";
      }

      return var3;
   }

   private void initHtcAppUsageStatsService() {
      IBinder var1 = ServiceManager.getService("HtcAppUsageStatsService");
      if(var1 != null) {
         IHtcAppUsageStatsService var2 = Stub.asInterface(var1);
         this.mHtcAppUsageStatsService = var2;
      } else {
         this.mHtcAppUsageStatsService = null;
         ll.e("MULogMgr", "Failed to get HtcAppUsageStatsService");
      }
   }

   private void setMailFormat(int var1) {
      switch(var1) {
      case 1:
         String[] var3 = new String[]{"Headers only", "2 KB", "5 KB", "10 KB", "20 KB", "50 KB", "Maximum size"};
         this.mailSizeList = var3;
         return;
      case 2:
         String[] var2 = new String[]{"Headers only", "2 KB", "5 KB", "10 KB", "20 KB", "50 KB", "Text only, no pictures", "Maximum size"};
         this.mailSizeList = var2;
         return;
      case 3:
         String[] var4 = new String[]{"Headers only", "1 KB (text only)", "5 KB (text only)", "25 KB", "50 KB", "100 KB", "1 MB", "Maximum size"};
         this.mailSizeList = var4;
         return;
      default:
      }
   }

   public void addEASULog() {
      ReusableULogData var1 = ReusableULogData.obtain();
      this.uLogData = var1;
      ReusableULogData var2 = this.uLogData.setAppId("com.htc.android.mail");
      Class var3 = this.getClass();
      ReusableULogData var4 = var2.setCategory("EAS_success").addData("EAS_activate", "true");
      ULog.log(this.uLogData);
      this.uLogData.recycle();
      int var5 = Log.i("ANALYTIC_Mail", "[EAS]User creates an EAS account");
   }

   public void addMailCountULog(Context var1, ArrayList<String[]> var2) {
      ReusableULogData var3 = ReusableULogData.obtain();
      this.uLogData = var3;
      ReusableULogData var4 = this.uLogData.setAppId("com.htc.android.mail").setCategory("mail_count");
      int var5 = var2.size();
      StringBuilder var6 = new StringBuilder();
      String var7 = "";
      if(this.mULogFileMgr == null) {
         ULogFileMgr var8 = new ULogFileMgr(var1);
         this.mULogFileMgr = var8;
      }

      ArrayList var9 = this.mULogFileMgr.getRecordList();
      int var10 = var9.size();
      boolean var11 = false;
      int var12 = 0;

      while(var12 < var5) {
         String var13 = ((String[])var2.get(var12))[1];
         var6.append(var13);
         StringBuilder var15 = var6.append(",");
         String var16 = ((String[])var2.get(var12))[2];
         var6.append(var16);
         StringBuilder var18 = var6.append(",");
         int var19 = 0;

         while(true) {
            if(var19 < var10) {
               label32: {
                  long var20 = Long.parseLong(((String[])var2.get(var12))[0]);
                  long var22 = ((Record)var9.get(var19)).getId();
                  if(var20 == var22) {
                     var11 = true;
                     String var24 = ((Record)var9.get(var19)).toString();
                     var6.append(var24);
                     if(!this.mULogFileMgr.hasOldRecord()) {
                        break label32;
                     }
                  }

                  ++var19;
                  continue;
               }
            }

            if(!var11) {
               StringBuilder var26 = (new StringBuilder()).append("0,0,");
               long var27 = System.currentTimeMillis();
               String var29 = var26.append(var27).toString();
               var6.append(var29);
               var11 = false;
            }

            ReusableULogData var31 = this.uLogData;
            StringBuilder var32 = (new StringBuilder()).append("account");
            String var33 = Integer.toString(var12 + 1);
            String var34 = var32.append(var33).toString();
            String var35 = var6.toString();
            var31.addData(var34, var35);
            StringBuilder var37 = (new StringBuilder()).append(var7);
            String var38 = var6.toString();
            var7 = var37.append(var38).append(" ").toString();
            var6 = new StringBuilder();
            ++var12;
            break;
         }
      }

      ULog.log(this.uLogData);
      this.uLogData.recycle();
      if(!this.mULogFileMgr.hasOldRecord()) {
         this.mULogFileMgr.clean();
      } else {
         this.mULogFileMgr.cleanNotTodayRecordCache();
      }

      String var39 = "[MailCount]" + var7;
      int var40 = Log.i("ANALYTIC_Mail", var39);
   }

   public void addMailSizeULog(long var1, int var3, int var4) {
      String var5 = this.getAccountType(var1);
      this.addMailSizeULog(var5, var3, var4);
   }

   public void addMailSizeULog(String var1, int var2, int var3) {
      this.setMailFormat(var3);
      StringBuilder var4 = (new StringBuilder()).append(var1).append(":");
      String var5 = this.mailSizeList[var2];
      String var6 = var4.append(var5).toString();
      ReusableULogData var7 = ReusableULogData.obtain();
      this.uLogData = var7;
      ReusableULogData var8 = this.uLogData.setAppId("com.htc.android.mail");
      Class var9 = this.getClass();
      ReusableULogData var10 = var8.setCategory("default_mailsize").addData("account_type", var1);
      String var11 = this.mailSizeList[var2];
      var10.addData("mail_size_limit", var11);
      ULog.log(this.uLogData);
      this.uLogData.recycle();
      String var13 = "[MailSize]" + var6;
      int var14 = Log.i("ANALYTIC_Mail", var13);
   }

   public void addPausedMailListLog(int var1, String var2) {
      if(this.mHtcAppUsageStatsService == null) {
         this.initHtcAppUsageStatsService();
      }

      try {
         this.mHtcAppUsageStatsService.notePauseActivity(var1, var2);
      } catch (Exception var9) {
         var9.printStackTrace();
      }

      StringBuilder var3 = new StringBuilder(var2);
      StringBuilder var4 = var3.append(" is paused");
      StringBuilder var5 = (new StringBuilder()).append("[MailList]");
      String var6 = var3.toString();
      String var7 = var5.append(var6).toString();
      int var8 = Log.i("ANALYTIC_Mail", var7);
   }

   public void addResumedMailListLog(int var1, String var2) {
      if(this.mHtcAppUsageStatsService == null) {
         this.initHtcAppUsageStatsService();
      }

      try {
         IHtcAppUsageStatsService var3 = this.mHtcAppUsageStatsService;
         Class var4 = this.getClass();
         var3.noteResumeActivity_pkg(var1, var2, "com.htc.android.mail", (boolean)0, (boolean)1);
      } catch (Exception var13) {
         var13.printStackTrace();
      }

      StringBuilder var7 = new StringBuilder(var2);
      StringBuilder var8 = var7.append(" is resumed");
      StringBuilder var9 = (new StringBuilder()).append("[MailList]");
      String var10 = var7.toString();
      String var11 = var9.append(var10).toString();
      int var12 = Log.i("ANALYTIC_Mail", var11);
   }

   public void addScheduleULog(long var1, int var3, int var4) {
      String var5 = this.getAccountType(var1);
      this.addScheduleULog(var5, var3, var4);
   }

   public void addScheduleULog(String var1, int var2, int var3) {
      StringBuilder var4 = new StringBuilder();
      String var5 = this.scheduleList[var2];
      StringBuilder var6 = var4.append(var5).append(",");
      String var7 = this.scheduleList[var3];
      String var8 = var6.append(var7).toString();
      String var9 = var1 + ":" + var8;
      ReusableULogData var10 = ReusableULogData.obtain();
      this.uLogData = var10;
      ReusableULogData var11 = this.uLogData.setAppId("com.htc.android.mail");
      Class var12 = this.getClass();
      ReusableULogData var13 = var11.setCategory("update_schedule").addData("account_type", var1).addData("update_schedule", var8);
      ULog.log(this.uLogData);
      this.uLogData.recycle();
      String var14 = "[UpdateSchedule]" + var9;
      int var15 = Log.i("ANALYTIC_Mail", var14);
   }

   public void addSyncErrLog(String var1) {
      ULog.log(ULogTags.APP_MAIL_INVALID_SYNC_KEY, var1);
      String var2 = "[SyncFail]" + var1;
      int var3 = Log.i("ANALYTIC_Mail", var2);
   }

   public void cleanMailCountRecords(Context var1) {
      if(this.mULogFileMgr == null) {
         ULogFileMgr var2 = new ULogFileMgr(var1);
         this.mULogFileMgr = var2;
      }

      this.mULogFileMgr.clean();
   }

   public void deleteMailCountRecord(Context var1, long var2) {
      if(this.mULogFileMgr == null) {
         ULogFileMgr var4 = new ULogFileMgr(var1);
         this.mULogFileMgr = var4;
      }

      this.mULogFileMgr.delete((boolean)1, var2);
   }

   public boolean isNeedUpdateMailCountULog(Context var1) {
      if(this.mULogFileMgr == null) {
         ULogFileMgr var2 = new ULogFileMgr(var1);
         this.mULogFileMgr = var2;
      }

      boolean var3;
      if(!this.mULogFileMgr.isULogFileEmpty() && this.mULogFileMgr.hasNotToday()) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public void manageShowMeLog(Context var1, int var2) {
      if(2 == var2 || var2 == 0) {
         if(ULogFileMgr.isAddShowMeFile(var1)) {
            Intent var3 = new Intent();
            Intent var4 = var3.setAction("com.htc.showme.LOG");
            Intent var5 = var3.putExtra("callingApp", "com.htc.android.mail");
            Intent var6 = var3.putExtra("actionCoverage", "topic_tag-mail-pop_imap_setup");
            var1.sendBroadcast(var3);
         }
      }
   }

   public void setAlarm(Context var1, boolean var2) {
      String var4 = "alarm";
      AlarmManager var5 = (AlarmManager)var1.getSystemService(var4);
      Intent var6 = new Intent("android.intent.action.mail.ulog.MAILCOUNT");
      byte var8 = 0;
      int var10 = 134217728;
      PendingIntent var11 = PendingIntent.getBroadcast(var1, var8, var6, var10);
      if(var2) {
         GregorianCalendar var12 = new GregorianCalendar();
         int var13 = var12.get(11);
         int var14 = var12.get(12);
         int var15 = var12.get(13);
         int var16 = var12.get(14);
         long var17 = System.currentTimeMillis();
         int var19 = var13 * 3600;
         int var20 = var14 * 60;
         long var21 = (long)((var19 + var20 + var15) * 1000);
         long var23 = 86400000L - var21;
         long var25 = (long)var16;
         long var27 = var23 + var25 - 541000L;
         ll.d("MULogMgr", "> setAlarm(true)");
         StringBuilder var29 = (new StringBuilder()).append("triggerAtTime: ").append(var17);
         String var32 = var29.append(var27).append(", period: ").append(86400000L).toString();
         ll.d("MULogMgr", var32);
         long var33 = var17 + var27;
         var5.setRepeating(1, var33, 86400000L, var11);
      } else {
         var5.cancel(var11);
      }

      ll.d("MULogMgr", "< setAlarm(true)");
   }

   public void setMULogReceiver(boolean var1, Context var2) {
      PackageManager var3 = var2.getPackageManager();
      ComponentName var4 = new ComponentName(var2, MULogReceiver.class);
      byte var5;
      if(var1) {
         var5 = 1;
      } else {
         var5 = 2;
      }

      var3.setComponentEnabledSetting(var4, var5, 1);
   }

   public void setMailAccountType(boolean var1) {
      if(var1) {
         String[] var2 = new String[]{"Manual", "Push mail", "Every 5 minutes", "Every 10 minutes", "Every 15 minutes", "Every 30 minutes", "Every hour", "Every 2 hours", "Every 4 hours", "Once a day"};
         this.scheduleList = var2;
      } else {
         String[] var3 = new String[]{"Manual", "Every 5 minutes", "Every 10 minutes", "Every 15 minutes", "Every 30 minutes", "Every hour", "Every 2 hours", "Every 4 hours", "Once a day"};
         this.scheduleList = var3;
      }
   }

   public void updateMailCountRecord(Context var1, long var2, long var4, long var6) {
      if(this.mULogFileMgr == null) {
         ULogFileMgr var8 = new ULogFileMgr(var1);
         this.mULogFileMgr = var8;
      }

      ULogFileMgr var9 = this.mULogFileMgr;
      var9.manageMailCountULog((boolean)1, var2, var4, var6);
   }
}
