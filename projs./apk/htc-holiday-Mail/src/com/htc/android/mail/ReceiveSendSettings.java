package com.htc.android.mail;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IContentProvider;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import com.htc.android.mail.Account;
import com.htc.android.mail.FetchTypeSettings2;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailCommon;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.Mailbox;
import com.htc.android.mail.Mailboxs;
import com.htc.android.mail.ll;
import com.htc.android.mail.easclient.PeakTimeSetting;
import com.htc.android.mail.ulog.MULogMgr;
import com.htc.preference.HtcCheckBoxPreference;
import com.htc.preference.HtcListPreference;
import com.htc.preference.HtcPreference;
import com.htc.preference.HtcPreferenceActivity;
import com.htc.preference.HtcPreferenceCategory;
import com.htc.preference.HtcPreferenceScreen;
import com.htc.preference.HtcPreference.OnPreferenceChangeListener;
import com.htc.preference.HtcPreference.OnPreferenceClickListener;
import com.htc.widget.HtcAlertDialog;
import com.htc.widget.HtcAlertDialog.Builder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class ReceiveSendSettings extends HtcPreferenceActivity {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   private static final int PEAK_SETTING_REQUEST = 10;
   private static HtcCheckBoxPreference del_cb = null;
   private final int DIALOG_FOLDER_SYNC = 1;
   String TAG = "ReceiveSendSettings";
   String dir;
   String fileName;
   private Account mAccount;
   private long mAccountId = 65535L;
   private HtcCheckBoxPreference mChkPreference;
   private HashMap<Long, Boolean> mDefaultSyncEnabledBuffer;
   private Mailboxs mDefaultSyncMailboxs = null;
   HtcPreferenceScreen mFolderSyncPref;
   private boolean mIsIMAP4;
   private HtcCheckBoxPreference mSyncWithServerPref = null;
   HtcPreferenceScreen preferenceScreen;
   HtcPreferenceScreen preferenceScreen_send;


   public ReceiveSendSettings() {
      HashMap var1 = new HashMap();
      this.mDefaultSyncEnabledBuffer = var1;
      this.mAccount = null;
      this.mIsIMAP4 = (boolean)0;
      this.mChkPreference = null;
      this.fileName = "signature";
      this.dir = "//data//data//com.htc.android.mail//databases//";
   }

   private void addPreferenceToReceiveSetting() {
      if(this.mAccount.protocol == 0 || this.mAccount.protocol == 1) {
         HtcCheckBoxPreference var1 = new HtcCheckBoxPreference(this);
         this.mSyncWithServerPref = var1;
         this.mSyncWithServerPref.setTitle(2131361997);
         this.mSyncWithServerPref.setSummary(2131361998);
         HtcPreferenceCategory var2 = (HtcPreferenceCategory)this.preferenceScreen.findPreference("PreferenceCategory_title");
         HtcCheckBoxPreference var3 = this.mSyncWithServerPref;
         var2.addPreference(var3);
      }
   }

   private final void bind() {
      Uri var1 = this.getIntent().getData();
      if(var1 == null) {
         if(DEBUG) {
            ll.d(this.TAG, "bind null url>");
         }
      } else {
         HtcPreferenceScreen var2 = this.mFolderSyncPref;
         String var3 = this.getFolderSyncPreferenceSummary();
         var2.setSummary(var3);
         long var4 = ContentUris.parseId(var1);
         this.mAccountId = var4;
         if(DEBUG) {
            String var6 = this.TAG;
            StringBuilder var7 = (new StringBuilder()).append("mAccountId>");
            long var8 = this.mAccountId;
            String var10 = var7.append(var8).append(",").append(var1).toString();
            ll.d(var6, var10);
         }

         int var11 = this.mAccount.fetchMailNumIndex;
         int var12 = this.mAccount.fetchMailDaysIndex;
         String var13 = null;
         if((this.mAccount.fetchMailType & 1) == 0) {
            switch(var11) {
            case 0:
               StringBuilder var52 = new StringBuilder();
               String var53 = this.getText(2131361981).toString();
               StringBuilder var54 = var52.append(var53).append(" ");
               String var55 = this.getText(2131362176).toString();
               var13 = var54.append(var55).toString();
               break;
            case 1:
               StringBuilder var56 = new StringBuilder();
               String var57 = this.getText(2131361981).toString();
               StringBuilder var58 = var56.append(var57).append(" ");
               String var59 = this.getText(2131362177).toString();
               var13 = var58.append(var59).toString();
               break;
            case 2:
               StringBuilder var60 = new StringBuilder();
               String var61 = this.getText(2131361981).toString();
               StringBuilder var62 = var60.append(var61).append(" ");
               String var63 = this.getText(2131362178).toString();
               var13 = var62.append(var63).toString();
               break;
            case 3:
               StringBuilder var64 = new StringBuilder();
               String var65 = this.getText(2131361981).toString();
               StringBuilder var66 = var64.append(var65).append(" ");
               String var67 = this.getText(2131362179).toString();
               var13 = var66.append(var67).toString();
               break;
            case 4:
               StringBuilder var68 = new StringBuilder();
               String var69 = this.getText(2131361981).toString();
               StringBuilder var70 = var68.append(var69).append(" ");
               String var71 = this.getText(2131362180).toString();
               var13 = var70.append(var71).toString();
               break;
            case 5:
               StringBuilder var72 = new StringBuilder();
               String var73 = this.getText(2131361981).toString();
               StringBuilder var74 = var72.append(var73).append(" ");
               String var75 = this.getText(2131362181).toString();
               var13 = var74.append(var75).toString();
            }

            ((HtcPreferenceScreen)this.preferenceScreen.findPreference("account_detail_fetch_type")).setSummary(var13);
         } else {
            switch(var12) {
            case 0:
               StringBuilder var76 = new StringBuilder();
               String var77 = this.getText(2131361983).toString();
               StringBuilder var78 = var76.append(var77).append(": ");
               String var79 = this.getText(2131362182).toString();
               var13 = var78.append(var79).toString();
               break;
            case 1:
               StringBuilder var80 = new StringBuilder();
               String var81 = this.getText(2131361983).toString();
               StringBuilder var82 = var80.append(var81).append(": ");
               String var83 = this.getText(2131362183).toString();
               var13 = var82.append(var83).toString();
               break;
            case 2:
               StringBuilder var84 = new StringBuilder();
               String var85 = this.getText(2131361983).toString();
               StringBuilder var86 = var84.append(var85).append(": ");
               String var87 = this.getText(2131362184).toString();
               var13 = var86.append(var87).toString();
               break;
            case 3:
               StringBuilder var88 = new StringBuilder();
               String var89 = this.getText(2131361983).toString();
               StringBuilder var90 = var88.append(var89).append(": ");
               String var91 = this.getText(2131362185).toString();
               var13 = var90.append(var91).toString();
               break;
            case 4:
               StringBuilder var92 = new StringBuilder();
               String var93 = this.getText(2131361983).toString();
               StringBuilder var94 = var92.append(var93).append(": ");
               String var95 = this.getText(2131362186).toString();
               var13 = var94.append(var95).toString();
               break;
            case 5:
               StringBuilder var96 = new StringBuilder();
               String var97 = this.getText(2131361983).toString();
               StringBuilder var98 = var96.append(var97).append(": ");
               String var99 = this.getText(2131362187).toString();
               var13 = var98.append(var99).toString();
            }

            ((HtcPreferenceScreen)this.preferenceScreen.findPreference("account_detail_fetch_type")).setSummary(var13);
         }

         if(this.mAccount.provider.compareTo("Gmail") != 0) {
            boolean var14 = false;
            if(this.mChkPreference == null) {
               HtcCheckBoxPreference var15 = new HtcCheckBoxPreference(this);
               this.mChkPreference = var15;
               HtcCheckBoxPreference var16 = this.mChkPreference;
               CharSequence var17 = this.getText(2131361989);
               var16.setTitle(var17);
               var14 = true;
            }

            if(this.mAccount.alwaysbccMyself > 0) {
               this.mChkPreference.setChecked((boolean)1);
               HtcCheckBoxPreference var18 = this.mChkPreference;
               String var19 = this.getText(2131362281).toString();
               var18.setSummary(var19);
            } else {
               this.mChkPreference.setChecked((boolean)0);
               HtcCheckBoxPreference var100 = this.mChkPreference;
               String var101 = this.getText(2131362282).toString();
               var100.setSummary(var101);
            }

            HtcCheckBoxPreference var20 = this.mChkPreference;
            String var21 = this.getText(2131362281).toString();
            var20.setSummaryOn(var21);
            HtcCheckBoxPreference var22 = this.mChkPreference;
            String var23 = this.getText(2131362282).toString();
            var22.setSummaryOff(var23);
            if(var14) {
               HtcPreferenceCategory var24 = (HtcPreferenceCategory)this.preferenceScreen.findPreference("PreferenceCategory_title_send");
               HtcCheckBoxPreference var25 = this.mChkPreference;
               var24.addPreference(var25);
            }
         }

         if(!this.mIsIMAP4) {
            if(DEBUG) {
               ll.d(this.TAG, "bind: not IMAP4");
            }

            if(this.mAccount.deleteFromServer > 0) {
               del_cb.setChecked((boolean)1);
               del_cb.setSummary(2131361975);
            } else {
               del_cb.setChecked((boolean)0);
               HtcCheckBoxPreference var102 = del_cb;
               String var103 = this.getText(2131362282).toString();
               var102.setSummary(var103);
            }

            HtcCheckBoxPreference var27 = del_cb;
            String var28 = this.getText(2131362281).toString();
            var27.setSummaryOn(var28);
            HtcCheckBoxPreference var29 = del_cb;
            String var30 = this.getText(2131362282).toString();
            var29.setSummaryOff(var30);
            if(this.mAccount.syncWithServer == 1) {
               this.mSyncWithServerPref.setChecked((boolean)1);
            } else {
               this.mSyncWithServerPref.setChecked((boolean)0);
            }
         }

         if(this.mAccount.replyWithText > 0) {
            ((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_reply_with_text_value")).setChecked((boolean)1);
            HtcCheckBoxPreference var31 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_reply_with_text_value");
            String var32 = this.getText(2131362281).toString();
            var31.setSummary(var32);
         } else {
            ((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_reply_with_text_value")).setChecked((boolean)0);
            HtcCheckBoxPreference var104 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_reply_with_text_value");
            String var105 = this.getText(2131362282).toString();
            var104.setSummary(var105);
         }

         HtcCheckBoxPreference var33 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_reply_with_text_value");
         String var34 = this.getText(2131362281).toString();
         var33.setSummaryOn(var34);
         HtcCheckBoxPreference var35 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_reply_with_text_value");
         String var36 = this.getText(2131362282).toString();
         var35.setSummaryOff(var36);
         if(DEBUG) {
            StringBuilder var37 = (new StringBuilder()).append("limit>");
            int var38 = this.mAccount.sizeLimit;
            String var39 = var37.append(var38).toString();
            ll.d("Jerry", var39);
         }

         HtcListPreference var40 = (HtcListPreference)this.preferenceScreen.findPreference("account_detail_mailsize_value");
         int var41 = this.mAccount.sizeLimitIndex;
         var40.setValueIndex(var41);
         CharSequence[] var42 = ((HtcListPreference)this.preferenceScreen.findPreference("account_detail_mailsize_value")).getEntries();
         HtcListPreference var43 = (HtcListPreference)this.preferenceScreen.findPreference("account_detail_mailsize_value");
         int var44 = this.mAccount.sizeLimitIndex;
         CharSequence var45 = var42[var44];
         var43.setSummary(var45);
         HtcListPreference var46 = (HtcListPreference)this.preferenceScreen.findPreference("account_detail_mailsize_value");
         ReceiveSendSettings.8 var47 = new ReceiveSendSettings.8();
         var46.setOnPreferenceChangeListener(var47);
         String[] var48 = this.getResources().getStringArray(2131034115);
         HtcPreferenceScreen var49 = (HtcPreferenceScreen)this.preferenceScreen.findPreference("account_detail_poll_frequency_value");
         int var50 = this.mAccount.checkFreq;
         String var51 = var48[var50];
         var49.setSummary(var51);
         if(this.mAccount.getDownloadMessageWhenScroll()) {
            ((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_auto_download_message")).setChecked((boolean)1);
         } else {
            ((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_auto_download_message")).setChecked((boolean)0);
         }
      }
   }

   private final ContentValues gatherValues() {
      ContentValues var1 = new ContentValues();
      Account var2 = MailProvider.getAccount(this.mAccountId);
      if(var2.provider.compareTo("Gmail") != 0 && this.mChkPreference != null) {
         if(this.mChkPreference.isChecked() == 1) {
            Integer var3 = Integer.valueOf(1);
            var1.put("_alwaysBccMyself", var3);
            var2.alwaysbccMyself = 1;
         } else {
            Integer var12 = Integer.valueOf(0);
            var1.put("_alwaysBccMyself", var12);
            var2.alwaysbccMyself = 0;
         }
      }

      if(this.mIsIMAP4 == 1) {
         Integer var4 = Integer.valueOf(1);
         var1.put("_deleteFromServer", var4);
         var2.deleteFromServer = 1;
      } else {
         if(del_cb.isChecked() == 1) {
            Integer var13 = Integer.valueOf(1);
            var1.put("_deleteFromServer", var13);
            var2.deleteFromServer = 1;
         } else {
            Integer var15 = Integer.valueOf(0);
            var1.put("_deleteFromServer", var15);
            var2.deleteFromServer = 0;
         }

         if(this.mSyncWithServerPref.isChecked()) {
            Integer var14 = Integer.valueOf(1);
            var1.put("_syncWithServer", var14);
            var2.syncWithServer = 1;
         } else {
            Integer var16 = Integer.valueOf(0);
            var1.put("_syncWithServer", var16);
            var2.syncWithServer = 0;
         }
      }

      if(((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_reply_with_text_value")).isChecked() == 1) {
         Integer var5 = Integer.valueOf(1);
         var1.put("_replyWithText", var5);
         var2.replyWithText = 1;
      } else {
         Integer var17 = Integer.valueOf(0);
         var1.put("_replyWithText", var17);
         var2.replyWithText = 0;
      }

      String var6 = ((HtcListPreference)this.preferenceScreen.findPreference("account_detail_mailsize_value")).getValue();
      int var7 = ((HtcListPreference)this.preferenceScreen.findPreference("account_detail_mailsize_value")).findIndexOfValue(var6);
      Integer var8 = Integer.valueOf(var7);
      var1.put("_sizelimit", var8);
      var2.setSizeLimitIndex(var7);
      if(MailCommon.isInPeakTime(var2)) {
         Integer var9 = Integer.valueOf(this.mAccount.syncSchedulePeakOn);
         var1.put("_poll_frequency_number", var9);
         int var10 = this.mAccount.syncSchedulePeakOn;
         var2.checkFreq = var10;
      } else {
         Integer var18 = Integer.valueOf(this.mAccount.syncSchedulePeakOff);
         var1.put("_poll_frequency_number", var18);
         int var19 = this.mAccount.syncSchedulePeakOff;
         var2.checkFreq = var19;
      }

      if(((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_auto_download_message")).isChecked()) {
         Integer var11 = Integer.valueOf(1);
         var1.put("_downloadMessageWhenScroll", var11);
         var2.mDownloadMessageWhenScroll = (boolean)1;
      } else {
         Integer var20 = Integer.valueOf(0);
         var1.put("_downloadMessageWhenScroll", var20);
         var2.mDownloadMessageWhenScroll = (boolean)0;
      }

      return var1;
   }

   private String getFolderSyncPreferenceSummary() {
      String var1;
      if(this.mDefaultSyncMailboxs == null) {
         var1 = null;
      } else {
         String var2 = this.mDefaultSyncMailboxs.getDecodeNames(this)[0];
         Integer var3 = Integer.valueOf(0);
         int var4 = this.mDefaultSyncMailboxs.getDecodeNames(this).length;
         boolean[] var5 = this.mDefaultSyncMailboxs.getDefaultSyncEnabled();

         for(int var6 = 1; var6 < var4; ++var6) {
            if(var5[var6]) {
               StringBuilder var7 = (new StringBuilder()).append(var2).append(", ");
               String var8 = this.mDefaultSyncMailboxs.getDecodeNames(this)[var6];
               var2 = var7.append(var8).toString();
               var3 = Integer.valueOf(var3.intValue() + 1);
            }
         }

         if(var3.intValue() >= 3) {
            StringBuilder var9 = new StringBuilder();
            String var10 = this.mDefaultSyncMailboxs.getDecodeNames(this)[0];
            StringBuilder var11 = var9.append(var10).append(", and other ");
            String var12 = var3.toString();
            var2 = var11.append(var12).append(" folders").toString();
         }

         var1 = var2;
      }

      return var1;
   }

   private void setupListener() {
      HtcPreference var1 = this.preferenceScreen.findPreference("account_detail_fetch_type");
      ReceiveSendSettings.6 var2 = new ReceiveSendSettings.6();
      var1.setOnPreferenceClickListener(var2);
      HtcPreference var3 = this.preferenceScreen.findPreference("account_detail_poll_frequency_value");
      ReceiveSendSettings.7 var4 = new ReceiveSendSettings.7();
      var3.setOnPreferenceClickListener(var4);
   }

   private final void updateAccount() {
      ContentValues var1 = this.gatherValues();
      Uri var2 = this.getIntent().getData();
      if(DEBUG) {
         String var3 = this.TAG;
         String var4 = "updateAccount>" + var2 + "," + var1;
         ll.d(var3, var4);
      }

      IContentProvider var5 = MailProvider.instance();
      Object var6 = null;
      Object var7 = null;

      try {
         var5.update(var2, var1, (String)var6, (String[])var7);
      } catch (DeadObjectException var11) {
         ;
      } catch (RemoteException var12) {
         ;
      }

      this.updateFetchTime(var2);
      Mail.setServicesEnabled(this);
   }

   private final void updateFetchTime(Uri var1) {
      MailProvider.getAccount(ContentUris.parseId(var1)).updateFetchTime();
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      this.setResult(var2);
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.addPreferencesFromResource(2130968596);
      long var2 = ContentUris.parseId(this.getIntent().getData());
      this.mAccountId = var2;
      Account var4 = MailProvider.getAccount(this.mAccountId);
      this.mAccount = var4;
      boolean var5 = Mail.isIMAP4(this.mAccount.protocol);
      this.mIsIMAP4 = var5;
      HtcPreferenceScreen var6 = this.getPreferenceScreen();
      this.preferenceScreen = var6;
      this.addPreferenceToReceiveSetting();
      if(!this.mIsIMAP4) {
         if(DEBUG) {
            ll.d(this.TAG, "onCreate: not IMAP4");
         }

         del_cb = new HtcCheckBoxPreference(this);
         del_cb.setTitle(2131361975);
         HtcPreferenceCategory var7 = (HtcPreferenceCategory)this.preferenceScreen.findPreference("PreferenceCategory_title_send");
         HtcCheckBoxPreference var8 = del_cb;
         var7.addPreference(var8);
      }

      HtcPreferenceScreen var10 = (HtcPreferenceScreen)this.findPreference("account_folder_to_sync");
      this.mFolderSyncPref = var10;
      HtcPreferenceScreen var11 = this.mFolderSyncPref;
      ReceiveSendSettings.1 var12 = new ReceiveSendSettings.1();
      var11.setOnPreferenceClickListener(var12);
      Mailboxs var13 = this.mAccount.getMailboxs().getMailboxsForSelectDefaultSync();
      this.mDefaultSyncMailboxs = var13;
      this.bind();
   }

   protected Dialog onCreateDialog(int var1) {
      HtcAlertDialog var2;
      switch(var1) {
      case 1:
         Builder var3 = (new Builder(this)).setTitle(2131361979);
         String[] var4 = this.mDefaultSyncMailboxs.getDecodeNames(this);
         boolean[] var5 = this.mDefaultSyncMailboxs.getDefaultSyncEnabled();
         ReceiveSendSettings.5 var6 = new ReceiveSendSettings.5();
         Builder var7 = var3.setMultiChoiceItems(var4, var5, var6);
         ReceiveSendSettings.4 var8 = new ReceiveSendSettings.4();
         Builder var9 = var7.setPositiveButton(2131362432, var8);
         ReceiveSendSettings.3 var10 = new ReceiveSendSettings.3();
         Builder var11 = var9.setNegativeButton(2131361931, var10);
         ReceiveSendSettings.2 var12 = new ReceiveSendSettings.2();
         var2 = var11.setOnKeyListener(var12).create();
         break;
      default:
         var2 = null;
      }

      return var2;
   }

   protected final void onDestroy() {
      if(DEBUG) {
         ll.d(this.TAG, "@@ onDestroy");
      }

      super.onDestroy();
   }

   protected final void onPause() {
      super.onPause();
      ReceiveSendSettings.9 var1 = new ReceiveSendSettings.9();
      (new Thread(var1)).start();
   }

   protected void onResume() {
      super.onResume();
      this.bind();
      this.setupListener();
   }

   protected final void onStop() {
      super.onStop();
   }

   String readSig() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.dir;
      StringBuilder var3 = var1.append(var2);
      String var4 = this.fileName;
      String var5 = var3.append(var4).toString();

      String var13;
      String var16;
      try {
         FileInputStream var6 = new FileInputStream(var5);
         BufferedInputStream var7 = new BufferedInputStream(var6, 1024);
         int var8 = var7.available();
         if(DEBUG) {
            String var9 = this.TAG;
            String var10 = "insize>" + var8;
            ll.d(var9, var10);
         }

         byte[] var11 = new byte[var8];
         var7.read(var11, 0, var8);
         var7.close();
         var13 = new String(var11);
         if(DEBUG) {
            String var14 = this.TAG;
            String var15 = "data>" + var13;
            ll.d(var14, var15);
         }
      } catch (IOException var19) {
         int var18 = Log.w(this.TAG, "Could not read data.", var19);
         var16 = null;
         return var16;
      }

      var16 = var13;
      return var16;
   }

   void writeSig(String var1) {
      StringBuilder var2 = new StringBuilder();
      String var3 = this.dir;
      StringBuilder var4 = var2.append(var3);
      String var5 = this.fileName;
      String var6 = var4.append(var5).toString();

      try {
         if(DEBUG) {
            String var7 = this.TAG;
            String var8 = "writeSig>" + var6 + "," + var1;
            ll.d(var7, var8);
         }

         File var9 = new File(var6);
         FileOutputStream var10 = new FileOutputStream(var9);
         byte[] var11 = var1.getBytes();
         var10.write(var11);
         var10.close();
      } catch (IOException var14) {
         int var13 = Log.w(this.TAG, "Could not writeSig.", var14);
      }
   }

   class 6 implements OnPreferenceClickListener {

      6() {}

      public boolean onPreferenceClick(HtcPreference var1) {
         StringBuilder var2 = (new StringBuilder()).append("content://mail/accounts/");
         long var3 = ReceiveSendSettings.this.mAccountId;
         Uri var5 = Uri.parse(var2.append(var3).toString());
         ReceiveSendSettings var6 = ReceiveSendSettings.this;
         Intent var7 = new Intent("android.intent.action.MAIN", var5, var6, FetchTypeSettings2.class);
         ReceiveSendSettings.this.startActivityForResult(var7, 0);
         return true;
      }
   }

   class 7 implements OnPreferenceClickListener {

      7() {}

      public boolean onPreferenceClick(HtcPreference var1) {
         StringBuilder var2 = (new StringBuilder()).append("content://mail/accounts/");
         long var3 = ReceiveSendSettings.this.mAccountId;
         Uri var5 = Uri.parse(var2.append(var3).toString());
         ReceiveSendSettings var6 = ReceiveSendSettings.this;
         Intent var7 = new Intent("android.intent.action.MAIN", var5, var6, PeakTimeSetting.class);
         ReceiveSendSettings.this.startActivityForResult(var7, 10);
         return true;
      }
   }

   class 4 implements OnClickListener {

      4() {}

      public void onClick(DialogInterface var1, int var2) {
         if(ReceiveSendSettings.this.mDefaultSyncEnabledBuffer.size() > 0) {
            Iterator var3 = ReceiveSendSettings.this.mDefaultSyncEnabledBuffer.keySet().iterator();

            while(var3.hasNext()) {
               Long var4 = (Long)var3.next();
               Account var5 = ReceiveSendSettings.this.mAccount;
               long var6 = var4.longValue();
               boolean var8 = ((Boolean)ReceiveSendSettings.this.mDefaultSyncEnabledBuffer.get(var4)).booleanValue();
               var5.setMailboxDefaultSyncEnabled(var6, var8);
            }

            ContentResolver var9 = ReceiveSendSettings.this.getApplicationContext().getContentResolver();
            Uri var10 = MailCommon.getSummariesUri(ReceiveSendSettings.this.mAccount.id, 9223372036854775802L);
            var9.notifyChange(var10, (ContentObserver)null);
            Uri var11 = MailCommon.getSummariesUri(Long.MAX_VALUE, 9223372036854775802L);
            var9.notifyChange(var11, (ContentObserver)null);
            ReceiveSendSettings.4.1 var12 = new ReceiveSendSettings.4.1();
            (new Thread(var12)).start();
         }

         HtcPreferenceScreen var13 = ReceiveSendSettings.this.mFolderSyncPref;
         String var14 = ReceiveSendSettings.this.getFolderSyncPreferenceSummary();
         var13.setSummary(var14);
         ReceiveSendSettings.this.mDefaultSyncEnabledBuffer.clear();
         ReceiveSendSettings.this.removeDialog(1);
      }

      class 1 implements Runnable {

         1() {}

         public void run() {
            Account var1 = ReceiveSendSettings.this.mAccount;
            Context var2 = ReceiveSendSettings.this.getApplicationContext();
            var1.commitMailboxChanges(var2);
         }
      }
   }

   class 5 implements OnMultiChoiceClickListener {

      5() {}

      public void onClick(DialogInterface var1, int var2, boolean var3) {
         Mailbox var4 = ReceiveSendSettings.this.mDefaultSyncMailboxs.getMailbox(var2);
         if(var4 != null) {
            if(var4.kind == Integer.MAX_VALUE) {
               ((HtcAlertDialog)var1).getListView().setItemChecked(var2, (boolean)1);
            } else {
               HashMap var5 = ReceiveSendSettings.this.mDefaultSyncEnabledBuffer;
               Long var6 = Long.valueOf(var4.id);
               if(var5.get(var6) != null) {
                  HashMap var7 = ReceiveSendSettings.this.mDefaultSyncEnabledBuffer;
                  Long var8 = Long.valueOf(var4.id);
                  var7.remove(var8);
               } else {
                  HashMap var10 = ReceiveSendSettings.this.mDefaultSyncEnabledBuffer;
                  Long var11 = Long.valueOf(var4.id);
                  Boolean var12 = Boolean.valueOf(var3);
                  var10.put(var11, var12);
               }
            }
         }
      }
   }

   class 2 implements OnKeyListener {

      2() {}

      public boolean onKey(DialogInterface var1, int var2, KeyEvent var3) {
         boolean var4;
         if(var3.getKeyCode() == 4) {
            ReceiveSendSettings.this.mDefaultSyncEnabledBuffer.clear();
            ReceiveSendSettings.this.removeDialog(1);
            var4 = true;
         } else {
            var4 = false;
         }

         return var4;
      }
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(DialogInterface var1, int var2) {
         ReceiveSendSettings.this.mDefaultSyncEnabledBuffer.clear();
         ReceiveSendSettings.this.removeDialog(1);
      }
   }

   class 1 implements OnPreferenceClickListener {

      1() {}

      public boolean onPreferenceClick(HtcPreference var1) {
         ReceiveSendSettings.this.showDialog(1);
         return true;
      }
   }

   class 9 implements Runnable {

      9() {}

      public void run() {
         ReceiveSendSettings.this.updateAccount();
      }
   }

   class 8 implements OnPreferenceChangeListener {

      8() {}

      public boolean onPreferenceChange(HtcPreference var1, Object var2) {
         String var3 = (String)var2;
         Integer var4 = new Integer(var3);
         CharSequence[] var5 = ((HtcListPreference)var1).getEntries();
         MULogMgr var6 = new MULogMgr();
         long var7 = ReceiveSendSettings.this.mAccount.id;
         int var9 = var4.intValue();
         var6.addMailSizeULog(var7, var9, 3);
         int var10 = var4.intValue();
         CharSequence var11 = var5[var10];
         var1.setSummary(var11);
         return true;
      }
   }
}
