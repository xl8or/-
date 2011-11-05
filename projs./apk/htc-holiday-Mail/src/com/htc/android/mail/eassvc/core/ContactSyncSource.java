package com.htc.android.mail.eassvc.core;

import android.content.Context;
import android.net.Uri;
import android.provider.Contacts.People;
import com.htc.android.mail.Mail;
import com.htc.android.mail.eassvc.common.EASContact;
import com.htc.android.mail.eassvc.contact.ContactManager;
import com.htc.android.mail.eassvc.core.BaseSyncSource;
import com.htc.android.mail.eassvc.core.BasicSyncListener;
import com.htc.android.mail.eassvc.pim.ExchangeAccount;
import com.htc.android.mail.eassvc.util.AccountUtil;
import com.htc.android.mail.eassvc.util.EASLog;
import java.io.File;
import java.util.ArrayList;

public class ContactSyncSource extends BaseSyncSource {

   private static final boolean DEBUG = Mail.EAS_DEBUG;
   private static final String EAS_CON_CFG_FILE_NAME = "eas_con.prefs";
   private static final String TAG = "ContactSyncSource";
   private ContactManager cm;
   private ExchangeAccount mAccount;


   public ContactSyncSource(Context var1, ExchangeAccount var2) {
      super(var1, var2, 1);
      this.mAccount = var2;
      long var3 = var2.accountId;
      File var5 = AccountUtil.getAccountConfigPath(var1, var3);
      File var6 = new File(var5, "eas_con.prefs");
      this.prefFile = var6;
      Uri var7 = People.CONTENT_EAS_SYNCINFO_URI;
      this.syncInfoURI = var7;
      BasicSyncListener var8 = new BasicSyncListener(var1);
      var8.setSyncSrcType(1);
      this.setListener(var8);
      ContactManager var9 = new ContactManager(var1, var2, this);
      this.cm = var9;
      this.loadData();
   }

   public void cleanSyncFailRecord() {
      this.cm.cleanSyncFailRecord();
   }

   public void deletePIMAppData() {
      try {
         this.cm.init();
         this.cm.removeAllSyncPeople();
         this.cm.reset();
         this.cm.release();
      } catch (Exception var7) {
         long var2 = this.mAccount.accountId;
         StringBuilder var4 = (new StringBuilder()).append("Error while deleting PIMAppData, ");
         String var5 = var7.getMessage();
         String var6 = var4.append(var5).toString();
         EASLog.e("ContactSyncSource", var2, var6);
      }
   }

   public String getCollIdFromDB() {
      return this.cm.getCollIdFromDB();
   }

   public void getDeviceChangeList(ArrayList<EASContact> var1, ArrayList<EASContact> var2, ArrayList<String> var3, boolean var4) {
      this.cm.getDeviceChangeList(var1, var2, var3, var4);
   }

   public boolean processClientAddRespList(ArrayList<EASContact> var1) throws Exception {
      return this.cm.processClientAddRespList(var1);
   }

   public boolean processServerChangeList(ArrayList<EASContact> var1, ArrayList<EASContact> var2, ArrayList<EASContact> var3) throws Exception {
      try {
         boolean var4 = this.cm.processServerChangeList(var1, var2, var3);
         return var4;
      } catch (Throwable var6) {
         throw var6;
      }
   }

   protected void removeSyncInfo() {
      ContactManager var1 = this.cm;
      String var2 = this.mAccount.accountName;
      var1.removeSyncinfo(var2);
   }

   public void setAccount(ExchangeAccount var1) {
      this.mAccount = var1;
      this.cm.setAccount(var1);
   }

   public void setScreenOn(boolean var1) {
      if(var1) {
         this.cm.updateInterval = 1;
      } else {
         this.cm.updateInterval = 0;
      }
   }

   public boolean skipIncorrectContact(String var1) {
      return this.cm.skipIncorrectContact(var1);
   }

   public void updateClientServerId(ArrayList<EASContact> var1) {
      this.cm.updateClientServerId(var1);
   }

   public void updateSynckeyAndTracker(String var1, ArrayList<String> var2) {
      long var3 = this.mAccount.accountId;
      StringBuilder var5 = (new StringBuilder()).append("- updateSynckeyAndTracker() : from ");
      String var6 = this.mSyncKey;
      StringBuilder var7 = var5.append(var6).append(" to ").append(var1).append(", type=");
      int var8 = this.type;
      String var9 = var7.append(var8).toString();
      EASLog.d("ContactSyncSource", var3, var9);
      this.cm.updateSynckeyAndTracker(var1, var2);
      this.mSyncKey = var1;

      try {
         this.saveDataToFile();
      } catch (Exception var16) {
         long var11 = this.mAccount.accountId;
         StringBuilder var13 = (new StringBuilder()).append("updateSynckeyAndTracker(").append(var1).append(") exception: ");
         String var14 = var16.getMessage();
         String var15 = var13.append(var14).toString();
         EASLog.e("ContactSyncSource", var11, var15);
      }
   }
}
