package com.htc.android.mail.eassvc.core;

import android.content.Context;
import android.net.Uri;
import android.provider.Calendar;
import com.htc.android.mail.Mail;
import com.htc.android.mail.eassvc.calendar.CalendarManager;
import com.htc.android.mail.eassvc.common.EASCalEvent;
import com.htc.android.mail.eassvc.core.BaseSyncSource;
import com.htc.android.mail.eassvc.core.BasicSyncListener;
import com.htc.android.mail.eassvc.pim.ExchangeAccount;
import com.htc.android.mail.eassvc.util.AccountUtil;
import com.htc.android.mail.eassvc.util.EASLog;
import java.io.File;
import java.util.ArrayList;

public class CalendarSyncSource extends BaseSyncSource {

   private static final boolean DEBUG = Mail.EAS_DEBUG;
   private static final String EAS_CAL_CFG_FILE_NAME = "eas_cal.prefs";
   private static final String TAG = "CalendarSyncSource";
   private long calendarId;
   private CalendarManager cm;
   private ExchangeAccount mAccount;


   public CalendarSyncSource(Context var1, ExchangeAccount var2) {
      super(var1, var2, 2);
      this.mAccount = var2;
      long var3 = var2.accountId;
      File var5 = AccountUtil.getAccountConfigPath(var1, var3);
      File var6 = new File(var5, "eas_cal.prefs");
      this.prefFile = var6;
      Uri var7 = Calendar.CONTENT_EAS_SYNCINFO_URI;
      this.syncInfoURI = var7;
      BasicSyncListener var8 = new BasicSyncListener(var1);
      var8.setSyncSrcType(2);
      this.setListener(var8);
      CalendarManager var9 = new CalendarManager(var1, var2, this);
      this.cm = var9;
      this.loadData();
   }

   public void cleanSyncFailRecord() {
      this.cm.cleanSyncFailRecord();
   }

   public void deletePIMAppData() {
      this.cm.init();
      this.cm.removeAllEvents();
      this.cm.reset();
      this.cm.release();
   }

   public long getCalendarId() {
      return this.calendarId;
   }

   public String getCollIdFromDB() {
      return this.cm.getCollIdFromDB();
   }

   public boolean getDeviceChangeList(ArrayList<EASCalEvent> var1, ArrayList<EASCalEvent> var2, ArrayList<String> var3) {
      return this.cm.getDeviceChangeList(var1, var2, var3);
   }

   public void loadData() {
      super.loadData();
      boolean var11 = false;

      label92: {
         label93: {
            try {
               var11 = true;
               long var1 = this.cm.getCalendarId();
               this.calendarId = var1;
               CalendarManager var3 = this.cm;
               long var4 = this.calendarId;
               var3.calendarId = var4;
               var11 = false;
               break label93;
            } catch (Exception var12) {
               ExchangeAccount var7 = this.mAccount;
               EASLog.e("CalendarSyncSource", var7, var12);
               var11 = false;
            } finally {
               if(var11) {
                  if(false && !null.isClosed()) {
                     null.close();
                  }

               }
            }

            if(true) {
               return;
            }

            if(null.isClosed()) {
               return;
            }
            break label92;
         }

         if(true) {
            return;
         }

         if(null.isClosed()) {
            return;
         }
      }

      null.close();
   }

   public boolean processClientAddRespList(ArrayList<EASCalEvent> var1) throws Exception {
      return this.cm.processClientAddRespList(var1);
   }

   public boolean processServerChangeList(ArrayList<EASCalEvent> var1, ArrayList<EASCalEvent> var2, ArrayList<EASCalEvent> var3) throws Exception {
      return this.cm.processServerChangeList(var1, var2, var3);
   }

   public void removeAllEvents() {
      this.cm.removeAllEvents();
      this.cm.reset();
   }

   protected void removeSyncInfo() {
      this.cm.removeSyncinfo();
   }

   public void setAccount(ExchangeAccount var1) {
      this.mAccount = var1;
      this.cm.setAccount(var1);
   }

   public void setProtocolVer(double var1) {
      super.setProtocolVer(var1);
      this.cm.setProtocolVer(var1);
   }

   public void setScreenOn(boolean var1) {
      if(var1) {
         this.cm.updateInterval = 1;
      } else {
         this.cm.updateInterval = 0;
      }
   }

   public void updateClientServerId(ArrayList<EASCalEvent> var1) {
      this.cm.updateClientServerId(var1);
   }

   public void updateClientServerId_withoutSetSyncingFlag(ArrayList<EASCalEvent> var1) {
      this.cm.updateClientServerId_withoutSetSyncingFlag(var1);
   }

   public void updateSynckeyAndTracker(String var1, String var2, String var3) {
      ExchangeAccount var4 = this.mAccount;
      StringBuilder var5 = (new StringBuilder()).append("- updateSynckeyAndTracker() : from ");
      String var6 = this.mSyncKey;
      StringBuilder var7 = var5.append(var6).append(" to ").append(var1).append(", type=");
      int var8 = this.type;
      String var9 = var7.append(var8).toString();
      EASLog.d("CalendarSyncSource", var4, var9);
      ExchangeAccount var10 = this.mAccount;
      String var11 = "update id =" + var2 + ", delete id = " + var3;
      EASLog.v("CalendarSyncSource", var10, var11);
      this.cm.updateSynckeyAndTracker(var1, var2, var3);
      this.mSyncKey = var1;

      try {
         this.saveDataToFile();
      } catch (Exception var17) {
         ExchangeAccount var13 = this.mAccount;
         StringBuilder var14 = (new StringBuilder()).append("updateSynckeyAndTracker(").append(var1).append(") exception: ");
         String var15 = var17.getMessage();
         String var16 = var14.append(var15).toString();
         EASLog.e("CalendarSyncSource", var13, var16);
      }
   }
}
