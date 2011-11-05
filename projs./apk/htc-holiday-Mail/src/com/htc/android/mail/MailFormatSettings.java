package com.htc.android.mail;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.IContentProvider;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Environment;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;
import com.htc.android.mail.Account;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailCommon;
import com.htc.android.mail.MailEventBroadcaster;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.ll;
import com.htc.android.mail.signatureEditor;
import com.htc.preference.HtcCheckBoxPreference;
import com.htc.preference.HtcListPreference;
import com.htc.preference.HtcPreference;
import com.htc.preference.HtcPreferenceActivity;
import com.htc.preference.HtcPreferenceScreen;
import com.htc.preference.HtcPreference.OnPreferenceChangeListener;
import com.htc.preference.HtcPreference.OnPreferenceClickListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MailFormatSettings extends HtcPreferenceActivity {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   final int MAX_LEN;
   String TAG = "MailFormatSettings";
   String dir;
   String fileName;
   private Account mAccount = null;
   private long mAccountId = 65535L;
   private int mExternalIndex = 1;
   private int mInternalIndex = 0;
   private BroadcastReceiver mReceiver;
   private boolean mSigTextModified = 0;
   private boolean mbPreviewLineNumberChanged;
   HtcPreferenceScreen preferenceScreen;
   private int sdsave;
   String sigText = null;
   Uri uri;


   public MailFormatSettings() {
      int var1 = this.mInternalIndex;
      this.sdsave = var1;
      this.mbPreviewLineNumberChanged = (boolean)0;
      this.uri = null;
      this.fileName = "signature";
      this.dir = "//data//data//com.htc.android.mail//databases//";
      this.MAX_LEN = 50;
      MailFormatSettings.7 var2 = new MailFormatSettings.7();
      this.mReceiver = var2;
   }

   private final void bind() {
      Uri var1 = this.getIntent().getData();
      this.uri = var1;
      if(this.uri == null) {
         if(DEBUG) {
            ll.d(this.TAG, "bind null url>");
         }
      } else {
         long var2 = ContentUris.parseId(this.uri);
         this.mAccountId = var2;
         if(DEBUG) {
            String var4 = this.TAG;
            StringBuilder var5 = (new StringBuilder()).append("mAccountId>");
            long var6 = this.mAccountId;
            StringBuilder var8 = var5.append(var6).append(",");
            Uri var9 = this.uri;
            String var10 = var8.append(var9).toString();
            ll.d(var4, var10);
         }

         Account var11 = MailProvider.getAccount(this.mAccountId);
         this.mAccount = var11;
         if(this.mAccount.useSignature > 0) {
            ((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_useSignature_value")).setChecked((boolean)1);
            HtcCheckBoxPreference var12 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_useSignature_value");
            String var13 = this.getText(2131362281).toString();
            var12.setSummary(var13);
         } else {
            ((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_useSignature_value")).setChecked((boolean)0);
            HtcCheckBoxPreference var66 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_useSignature_value");
            String var67 = this.getText(2131362282).toString();
            var66.setSummary(var67);
         }

         HtcCheckBoxPreference var14 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_useSignature_value");
         String var15 = this.getText(2131362281).toString();
         var14.setSummaryOn(var15);
         HtcCheckBoxPreference var16 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_useSignature_value");
         String var17 = this.getText(2131362282).toString();
         var16.setSummaryOff(var17);
         if(this.mAccount.askBeforeDelete > 0) {
            ((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_ask_before_delete_value")).setChecked((boolean)1);
            HtcCheckBoxPreference var18 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_ask_before_delete_value");
            String var19 = this.getText(2131362281).toString();
            var18.setSummary(var19);
         } else {
            ((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_ask_before_delete_value")).setChecked((boolean)0);
            HtcCheckBoxPreference var68 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_ask_before_delete_value");
            String var69 = this.getText(2131362282).toString();
            var68.setSummary(var69);
         }

         HtcCheckBoxPreference var20 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_ask_before_delete_value");
         String var21 = this.getText(2131362281).toString();
         var20.setSummaryOn(var21);
         HtcCheckBoxPreference var22 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_ask_before_delete_value");
         String var23 = this.getText(2131362282).toString();
         var22.setSummaryOff(var23);
         HtcCheckBoxPreference var24 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_ask_before_delete_value");
         MailFormatSettings.1 var25 = new MailFormatSettings.1();
         var24.setOnPreferenceChangeListener(var25);
         HtcCheckBoxPreference var26 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_refresh_mail_when_open_folder");
         String var27 = this.getText(2131361994).toString();
         var26.setSummary(var27);
         Account var28 = this.mAccount;
         Context var29 = this.getApplicationContext();
         if(var28.refreshMailOpenFolder(var29)) {
            ((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_refresh_mail_when_open_folder")).setChecked((boolean)1);
         } else {
            ((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_refresh_mail_when_open_folder")).setChecked((boolean)0);
         }

         HtcCheckBoxPreference var30 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_refresh_mail_when_open_folder");
         MailFormatSettings.2 var31 = new MailFormatSettings.2();
         var30.setOnPreferenceChangeListener(var31);
         if(this.mAccount.defaultaccount > 0) {
            ((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_default_account")).setChecked((boolean)1);
            HtcCheckBoxPreference var32 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_default_account");
            String var33 = this.getText(2131362281).toString();
            var32.setSummary(var33);
            ((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_default_account")).setEnabled((boolean)0);
         } else {
            ((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_default_account")).setChecked((boolean)0);
            HtcCheckBoxPreference var70 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_default_account");
            String var71 = this.getText(2131362282).toString();
            var70.setSummary(var71);
            ((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_default_account")).setEnabled((boolean)1);
         }

         HtcCheckBoxPreference var34 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_default_account");
         String var35 = this.getText(2131362281).toString();
         var34.setSummaryOn(var35);
         HtcCheckBoxPreference var36 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_default_account");
         String var37 = this.getText(2131362282).toString();
         var36.setSummaryOff(var37);
         HtcListPreference var38 = (HtcListPreference)this.preferenceScreen.findPreference("account_detail_font_size_value");
         int var39 = this.mAccount.fontsize;
         var38.setValueIndex(var39);
         CharSequence[] var40 = ((HtcListPreference)this.preferenceScreen.findPreference("account_detail_font_size_value")).getEntries();
         HtcListPreference var41 = (HtcListPreference)this.preferenceScreen.findPreference("account_detail_font_size_value");
         int var42 = this.mAccount.fontsize;
         CharSequence var43 = var40[var42];
         var41.setSummary(var43);
         HtcListPreference var44 = (HtcListPreference)this.preferenceScreen.findPreference("account_detail_font_size_value");
         MailFormatSettings.3 var45 = new MailFormatSettings.3();
         var44.setOnPreferenceChangeListener(var45);
         HtcListPreference var46 = (HtcListPreference)this.preferenceScreen.findPreference("account_mail_body_preview");
         Account var47 = this.mAccount;
         Context var48 = this.getApplicationContext();
         int var49 = var47.getPreviewLineNum(var48);
         var46.setValueIndex(var49);
         CharSequence[] var50 = var46.getEntries();
         Account var51 = this.mAccount;
         Context var52 = this.getApplicationContext();
         int var53 = var51.getPreviewLineNum(var52);
         CharSequence var54 = var50[var53];
         var46.setSummary(var54);
         MailFormatSettings.4 var55 = new MailFormatSettings.4();
         var46.setOnPreferenceChangeListener(var55);
         if(DEBUG) {
            String var56 = this.TAG;
            StringBuilder var57 = (new StringBuilder()).append("sig>");
            String var58 = this.mAccount.signature;
            String var59 = var57.append(var58).toString();
            ll.d(var56, var59);
         }

         if(this.mAccount.signature != null) {
            String var60 = this.mAccount.signature;
            this.sigText = var60;
         } else {
            String var72 = MailCommon.getDefaultSignature(this);
            this.sigText = var72;
         }

         String var61 = this.sigText;
         String var62 = this.makeSigDisp(var61);
         this.preferenceScreen.findPreference("account_detail_signature_value").setSummary(var62);
         HtcPreference var63 = this.preferenceScreen.findPreference("account_detail_signature_value");
         MailFormatSettings.5 var64 = new MailFormatSettings.5();
         var63.setOnPreferenceClickListener(var64);
         int var65 = this.mAccount.enableSDsave;
         this.sdsave = var65;
         this.updateSDStatus();
      }
   }

   private final ContentValues gatherValues() {
      ContentValues var1 = new ContentValues();
      Account var2 = MailProvider.getAccount(this.mAccountId);
      if(((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_ask_before_delete_value")).isChecked() == 1) {
         Integer var3 = Integer.valueOf(1);
         var1.put("_askBeforeDelete", var3);
         var2.askBeforeDelete = 1;
      } else {
         Integer var21 = Integer.valueOf(0);
         var1.put("_askBeforeDelete", var21);
         var2.askBeforeDelete = 0;
      }

      if(((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_default_account")).isChecked() == 1) {
         Integer var4 = Integer.valueOf(1);
         var1.put("_defaultaccount", var4);
         MailProvider.getAccount(MailProvider.getDefaultAccountId()).defaultaccount = 0;
         Context var5 = this.getApplicationContext();
         long var6 = this.mAccountId;
         MailProvider.setDefaultAccountById(var5, var6);
         var2.defaultaccount = 1;
      } else {
         Integer var22 = Integer.valueOf(0);
         var1.put("_defaultaccount", var22);
         var2.defaultaccount = 0;
      }

      if(((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_useSignature_value")).isChecked() == 1) {
         Integer var8 = Integer.valueOf(1);
         var1.put("_useSignature", var8);
         var2.useSignature = 1;
      } else {
         Integer var23 = Integer.valueOf(0);
         var1.put("_useSignature", var23);
         var2.useSignature = 0;
      }

      String var9 = ((HtcListPreference)this.preferenceScreen.findPreference("account_detail_font_size_value")).getValue();
      int var10 = ((HtcListPreference)this.preferenceScreen.findPreference("account_detail_font_size_value")).findIndexOfValue(var9);
      Integer var11 = Integer.valueOf(var10);
      var1.put("_fontSize", var11);
      var2.fontsize = var10;
      if(this.mSigTextModified) {
         if(DEBUG) {
            String var12 = this.TAG;
            String var13 = "new sig str>" + var9;
            ll.d(var12, var13);
         }

         String var14 = var2.signature;
         var1.put("_signature", var14);
         this.mSigTextModified = (boolean)0;
      }

      String var15 = ((HtcListPreference)this.preferenceScreen.findPreference("account_detail_enableSDsave_value")).getValue();
      int var16 = ((HtcListPreference)this.preferenceScreen.findPreference("account_detail_enableSDsave_value")).findIndexOfValue(var15);
      Integer var17 = Integer.valueOf(var16);
      var1.put("_enableSDsave", var17);
      var2.enableSDsave = var16;
      String var18 = ((HtcListPreference)this.preferenceScreen.findPreference("account_mail_body_preview")).getValue();
      int var19 = ((HtcListPreference)this.preferenceScreen.findPreference("account_mail_body_preview")).findIndexOfValue(var18);
      Integer var20 = Integer.valueOf(var19);
      var1.put("_previewLinesNumber", var20);
      var2.setPreviewLineNum(var19);
      return var1;
   }

   private String makeSigDisp(String var1) {
      String var2;
      if(var1 == null) {
         var2 = "";
      } else {
         String var3 = var1;
         if(DEBUG) {
            String var5 = this.TAG;
            String var6 = "sigDisp>" + var1;
            ll.d(var5, var6);
         }

         int var7 = var1.indexOf("\n");
         if(var7 > 0) {
            int var8 = var7 + 1;
            String var9 = var1.substring(var8);
            if(DEBUG) {
               String var10 = this.TAG;
               String var11 = "sigDisp2>" + var7 + "," + var9;
               ll.d(var10, var11);
            }

            int var12 = var9.indexOf("\n");
            if(var12 > 0) {
               if(DEBUG) {
                  String var13 = this.TAG;
                  String var14 = "index2>" + var12;
                  ll.d(var13, var14);
               }

               int var15 = var12 + 1;
               String var16 = var1.substring(var15);
               if(DEBUG) {
                  String var17 = this.TAG;
                  String var18 = "sigDisp3>" + var16;
                  ll.d(var17, var18);
               }

               if(var16.length() > 0) {
                  if(var12 > 30) {
                     StringBuilder var19 = new StringBuilder();
                     int var20 = var7 + 1 + var12 - 3;
                     String var21 = var1.substring(0, var20);
                     var3 = var19.append(var21).append("...").toString();
                  } else {
                     StringBuilder var24 = new StringBuilder();
                     int var25 = var7 + 1 + var12;
                     String var26 = var1.substring(0, var25);
                     var3 = var24.append(var26).append("...").toString();
                  }

                  if(DEBUG) {
                     String var22 = this.TAG;
                     String var23 = "sigDisp a>" + var3;
                     ll.d(var22, var23);
                  }
               } else {
                  if(var9.length() > 50) {
                     StringBuilder var27 = new StringBuilder();
                     int var28 = var7 + 1 + 25;
                     String var29 = var1.substring(0, var28);
                     var3 = var27.append(var29).append("...").toString();
                  } else {
                     var3 = var1;
                  }

                  if(DEBUG) {
                     String var30 = this.TAG;
                     String var31 = "sigDisp b>" + var3;
                     ll.d(var30, var31);
                  }
               }
            } else {
               if(var9.length() > 50) {
                  StringBuilder var32 = new StringBuilder();
                  int var33 = var7 + 1 + 25;
                  String var34 = var1.substring(0, var33);
                  var3 = var32.append(var34).append("...").toString();
               } else {
                  var3 = var1;
               }

               if(DEBUG) {
                  String var35 = this.TAG;
                  String var36 = "sigDisp b>" + var3;
                  ll.d(var35, var36);
               }
            }
         } else if(var1.length() > 50) {
            StringBuilder var37 = new StringBuilder();
            String var38 = var1.substring(0, 25);
            var3 = var37.append(var38).append("...").toString();
         }

         var2 = var3;
      }

      return var2;
   }

   private final void saveAccount() {
      ContentValues var1 = this.gatherValues();
      IContentProvider var2 = MailProvider.instance();
      Uri var3 = null;

      label19: {
         Uri var5;
         try {
            Uri var4 = MailProvider.sAccountsURI;
            var5 = var2.insert(var4, var1);
         } catch (DeadObjectException var9) {
            break label19;
         } catch (RemoteException var10) {
            break label19;
         }

         var3 = var5;
      }

      if(var3 != null) {
         Toast.makeText(this, 2131361929, 0).show();
         this.updateFetchTime(var3);
         Mail.setServicesEnabled(this);
         Intent var6 = new Intent();
         this.startActivity(var6);
         this.finish();
      }
   }

   private void saveAccountAskBeforeDelete(boolean var1) {
      ContentValues var2 = new ContentValues();
      if(var1) {
         Integer var3 = Integer.valueOf(1);
         var2.put("_askBeforeDelete", var3);
      } else {
         Integer var14 = Integer.valueOf(0);
         var2.put("_askBeforeDelete", var14);
      }

      IContentProvider var4 = MailProvider.instance();
      Uri var5 = this.getIntent().getData();
      if(DEBUG) {
         String var6 = this.TAG;
         String var7 = "saveAccountAskBeforeDelete>" + var5 + "," + var2;
         ll.d(var6, var7);
      }

      Object var8 = null;
      Object var9 = null;
      boolean var24 = false;

      MailEventBroadcaster var11;
      long var12;
      label64: {
         label63: {
            label62: {
               try {
                  var24 = true;
                  var4.update(var5, var2, (String)var8, (String[])var9);
                  var24 = false;
                  break label63;
               } catch (DeadObjectException var25) {
                  var24 = false;
                  break label62;
               } catch (RemoteException var26) {
                  var24 = false;
               } finally {
                  if(var24) {
                     MailEventBroadcaster var16 = Mail.mMailEvent;
                     long var17 = this.mAccountId;
                     var16.sendSettingChangedIntent(this, var17);
                  }
               }

               var11 = Mail.mMailEvent;
               var12 = this.mAccountId;
               break label64;
            }

            var11 = Mail.mMailEvent;
            var12 = this.mAccountId;
            break label64;
         }

         var11 = Mail.mMailEvent;
         var12 = this.mAccountId;
      }

      var11.sendSettingChangedIntent(this, var12);
   }

   private void saveAccountPreviewLineNumbers() {
      MailEventBroadcaster var1 = Mail.mMailEvent;
      long var2 = this.mAccountId;
      var1.sendSettingChangedIntent(this, var2);
   }

   private void saveAccountRefreshMail(boolean var1) {
      if(var1) {
         this.mAccount.setRefreshMailOpenFolder(this, (boolean)1);
      } else {
         this.mAccount.setRefreshMailOpenFolder(this, (boolean)0);
      }

      Account var2 = this.mAccount;
      Context var3 = this.getApplicationContext();
      var2.commit(var3);
   }

   private void update() {
      String var1 = Environment.getExternalStorageState();
      if(var1.equals("mounted")) {
         this.updateSDStatus();
      } else if(var1.equals("unmounted")) {
         this.updateSDStatus();
      } else if(var1.equals("removed")) {
         this.updateSDStatus();
      } else if(var1.equals("shared")) {
         this.updateSDStatus();
      } else if(var1.equals("mounted_ro")) {
         this.updateSDStatus();
      } else if(var1.equals("bad_removal")) {
         this.updateSDStatus();
      }
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

      this.setResult(-1);
   }

   private final void updateFetchTime(Uri var1) {
      MailProvider.getAccount(ContentUris.parseId(var1)).updateFetchTime();
   }

   private void updateSDStatus() {
      HtcListPreference var1 = (HtcListPreference)this.preferenceScreen.findPreference("account_detail_enableSDsave_value");
      int var2 = this.sdsave;
      var1.setValueIndex(var2);
      CharSequence[] var3 = ((HtcListPreference)this.preferenceScreen.findPreference("account_detail_enableSDsave_value")).getEntries();
      String var4 = this.getString(2131362157);
      if(Environment.getExternalStorageState().equals("mounted_ro")) {
         var4 = this.getString(2131362159);
      } else if(Environment.getExternalStorageState().equals("shared")) {
         var4 = this.getString(2131362158);
      }

      if(!Environment.getExternalStorageState().equals("unmounted") && !Environment.getExternalStorageState().equals("removed") && !Environment.getExternalStorageState().equals("shared") && !Environment.getExternalStorageState().equals("mounted_ro") && !Environment.getExternalStorageState().equals("bad_removal")) {
         if(Environment.getExternalStorageState().equals("mounted")) {
            ((HtcListPreference)this.preferenceScreen.findPreference("account_detail_enableSDsave_value")).setEnabled((boolean)1);
            HtcListPreference var19 = (HtcListPreference)this.preferenceScreen.findPreference("account_detail_enableSDsave_value");
            int var20 = this.sdsave;
            CharSequence var21 = var3[var20];
            var19.setSummary(var21);
            HtcListPreference var22 = (HtcListPreference)this.preferenceScreen.findPreference("account_detail_enableSDsave_value");
            MailFormatSettings.6 var23 = new MailFormatSettings.6();
            var22.setOnPreferenceChangeListener(var23);
         }
      } else {
         Dialog var5 = ((HtcListPreference)this.preferenceScreen.findPreference("account_detail_enableSDsave_value")).getDialog();
         if(var5 != null) {
            var5.dismiss();
         }

         ((HtcListPreference)this.preferenceScreen.findPreference("account_detail_enableSDsave_value")).setEnabled((boolean)0);
         HtcListPreference var6 = (HtcListPreference)this.preferenceScreen.findPreference("account_detail_enableSDsave_value");
         int var7 = this.sdsave;
         CharSequence var8 = var3[var7];
         var6.setSummary(var8);
         int var9 = this.sdsave;
         int var10 = this.mExternalIndex;
         if(var9 == var10) {
            HtcListPreference var11 = (HtcListPreference)this.preferenceScreen.findPreference("account_detail_enableSDsave_value");
            StringBuilder var12 = new StringBuilder();
            int var13 = this.mInternalIndex;
            CharSequence var14 = var3[var13];
            String var15 = var12.append(var14).append(" (").append(var4).append(")").toString();
            var11.setSummary(var15);
         } else {
            HtcListPreference var16 = (HtcListPreference)this.preferenceScreen.findPreference("account_detail_enableSDsave_value");
            int var17 = this.sdsave;
            CharSequence var18 = var3[var17];
            var16.setSummary(var18);
         }
      }
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      if(var1 == 1) {
         if(var2 == -1) {
            if(var3 == null) {
               if(DEBUG) {
                  ll.d(this.TAG, "onActivityResult<result ok,but data null");
               }
            } else {
               this.mSigTextModified = (boolean)1;
               Account var4 = this.mAccount;
               String var5 = var3.getStringExtra("sig");
               var4.signature = var5;
               String var6 = this.mAccount.signature;
               String var7 = this.makeSigDisp(var6);
               this.preferenceScreen.findPreference("account_detail_signature_value").setSummary(var7);
               if(DEBUG) {
                  String var8 = this.TAG;
                  StringBuilder var9 = (new StringBuilder()).append("onActivityResult>");
                  String var10 = this.mAccount.signature;
                  String var11 = var9.append(var10).toString();
                  ll.d(var8, var11);
               }
            }
         } else if(DEBUG) {
            ll.d(this.TAG, "sig cancel");
         }
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      IntentFilter var2 = new IntentFilter();
      var2.addAction("android.intent.action.MEDIA_REMOVED");
      var2.addAction("android.intent.action.MEDIA_UNMOUNTED");
      var2.addAction("android.intent.action.MEDIA_MOUNTED");
      var2.addAction("android.intent.action.MEDIA_SHARED");
      var2.addDataScheme("file");
      this.addPreferencesFromResource(2130968591);
      HtcPreferenceScreen var3 = this.getPreferenceScreen();
      this.preferenceScreen = var3;
      BroadcastReceiver var4 = this.mReceiver;
      this.registerReceiver(var4, var2);
   }

   protected final void onDestroy() {
      if(DEBUG) {
         ll.d(this.TAG, "onDestroy>");
      }

      super.onDestroy();
      BroadcastReceiver var1 = this.mReceiver;
      this.unregisterReceiver(var1);
   }

   protected void onPause() {
      super.onPause();
      if(this.mAccountId != 65535L) {
         this.updateAccount();
         this.saveAccountPreviewLineNumbers();
         this.mbPreviewLineNumberChanged = (boolean)0;
      }
   }

   protected final void onResume() {
      super.onResume();
      this.bind();
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

      String var14;
      String var17;
      try {
         new File(var5);
         FileInputStream var7 = new FileInputStream(var5);
         BufferedInputStream var8 = new BufferedInputStream(var7, 1024);
         int var9 = var8.available();
         if(DEBUG) {
            String var10 = this.TAG;
            String var11 = "insize>" + var9;
            ll.d(var10, var11);
         }

         byte[] var12 = new byte[var9];
         var8.read(var12, 0, var9);
         var8.close();
         var14 = new String(var12);
         if(DEBUG) {
            String var15 = this.TAG;
            String var16 = "data>" + var14;
            ll.d(var15, var16);
         }
      } catch (IOException var20) {
         if(DEBUG) {
            int var19 = Log.w(this.TAG, "Could not read data.", var20);
         }

         var17 = null;
         return var17;
      }

      var17 = var14;
      return var17;
   }

   void startSigEditor() {
      Uri var1 = this.uri;
      Intent var2 = new Intent("android.intent.action.MAIN", var1, this, signatureEditor.class);
      String var3 = this.sigText;
      var2.putExtra("sig", var3);
      this.startActivityForResult(var2, 1);
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
         if(DEBUG) {
            int var13 = Log.w(this.TAG, "Could not writeSig.", var14);
         }
      }
   }

   class 4 implements OnPreferenceChangeListener {

      4() {}

      public boolean onPreferenceChange(HtcPreference var1, Object var2) {
         String var3 = (String)var2;
         Integer var4 = new Integer(var3);
         CharSequence[] var5 = ((HtcListPreference)var1).getEntries();
         int var6 = var4.intValue();
         CharSequence var7 = var5[var6];
         var1.setSummary(var7);
         boolean var8 = (boolean)(MailFormatSettings.this.mbPreviewLineNumberChanged = (boolean)1);
         return true;
      }
   }

   class 3 implements OnPreferenceChangeListener {

      3() {}

      public boolean onPreferenceChange(HtcPreference var1, Object var2) {
         String var3 = (String)var2;
         Integer var4 = new Integer(var3);
         CharSequence[] var5 = ((HtcListPreference)var1).getEntries();
         int var6 = var4.intValue();
         CharSequence var7 = var5[var6];
         var1.setSummary(var7);
         return true;
      }
   }

   class 2 implements OnPreferenceChangeListener {

      2() {}

      public boolean onPreferenceChange(HtcPreference var1, Object var2) {
         Boolean var3 = (Boolean)var2;
         MailFormatSettings var4 = MailFormatSettings.this;
         boolean var5 = var3.booleanValue();
         var4.saveAccountRefreshMail(var5);
         return true;
      }
   }

   class 1 implements OnPreferenceChangeListener {

      1() {}

      public boolean onPreferenceChange(HtcPreference var1, Object var2) {
         Boolean var3 = (Boolean)var2;
         MailFormatSettings var4 = MailFormatSettings.this;
         boolean var5 = var3.booleanValue();
         var4.saveAccountAskBeforeDelete(var5);
         return true;
      }
   }

   class 7 extends BroadcastReceiver {

      7() {}

      public void onReceive(Context var1, Intent var2) {
         if(MailFormatSettings.DEBUG) {
            String var3 = MailFormatSettings.this.TAG;
            StringBuilder var4 = (new StringBuilder()).append("# get action.");
            String var5 = var2.getAction();
            String var6 = var4.append(var5).toString();
            ll.d(var3, var6);
         }

         MailFormatSettings.this.update();
      }
   }

   class 6 implements OnPreferenceChangeListener {

      6() {}

      public boolean onPreferenceChange(HtcPreference var1, Object var2) {
         String var3 = (String)var2;
         Integer var4 = new Integer(var3);
         CharSequence[] var5 = ((HtcListPreference)var1).getEntries();
         int var6 = var4.intValue();
         CharSequence var7 = var5[var6];
         var1.setSummary(var7);
         MailFormatSettings var8 = MailFormatSettings.this;
         int var9 = var4.intValue();
         var8.sdsave = var9;
         return true;
      }
   }

   class 5 implements OnPreferenceClickListener {

      5() {}

      public boolean onPreferenceClick(HtcPreference var1) {
         if(MailFormatSettings.DEBUG) {
            String var2 = MailFormatSettings.this.TAG;
            StringBuilder var3 = (new StringBuilder()).append("onPreferenceClick sig false>").append(var1).append(",");
            String var4 = MailFormatSettings.this.sigText;
            String var5 = var3.append(var4).toString();
            ll.d(var2, var5);
         }

         MailFormatSettings.this.startSigEditor();
         return false;
      }
   }
}
