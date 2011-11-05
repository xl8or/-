package com.htc.android.mail;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.IContentProvider;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;
import com.htc.android.mail.Account;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.ll;
import com.htc.preference.HtcCheckBoxPreference;
import com.htc.preference.HtcPreference;
import com.htc.preference.HtcPreferenceActivity;
import com.htc.preference.HtcPreferenceScreen;
import com.htc.preference.HtcPreference.OnPreferenceChangeListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MiscSettings extends HtcPreferenceActivity {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   String TAG = "MiscSettings";
   String dir = "//data//data//com.htc.android.mail//databases//";
   String fileName = "signature";
   private long mAccountId = 65535L;
   HtcPreferenceScreen preferenceScreen;


   public MiscSettings() {}

   private final void bind() {
      Uri var1 = this.getIntent().getData();
      if(var1 == null) {
         if(DEBUG) {
            ll.d(this.TAG, "bind null url>");
         }
      } else {
         long var2 = ContentUris.parseId(var1);
         this.mAccountId = var2;
         if(DEBUG) {
            String var4 = this.TAG;
            StringBuilder var5 = (new StringBuilder()).append("mAccountId>");
            long var6 = this.mAccountId;
            String var8 = var5.append(var6).append(",").append(var1).toString();
            ll.d(var4, var8);
         }

         Account var9 = MailProvider.getAccount(this.mAccountId);
         if(var9.mEmailNotifications > 0) {
            ((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_email_notifications")).setChecked((boolean)1);
            HtcCheckBoxPreference var10 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_email_notifications");
            String var11 = this.getText(2131362281).toString();
            var10.setSummary(var11);
         } else {
            ((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_email_notifications")).setChecked((boolean)0);
            HtcCheckBoxPreference var34 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_email_notifications");
            String var35 = this.getText(2131362282).toString();
            var34.setSummary(var35);
         }

         HtcCheckBoxPreference var12 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_email_notifications");
         String var13 = this.getText(2131362281).toString();
         var12.setSummaryOn(var13);
         HtcCheckBoxPreference var14 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_email_notifications");
         String var15 = this.getText(2131362282).toString();
         var14.setSummaryOff(var15);
         HtcCheckBoxPreference var16 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_email_notifications");
         MiscSettings.1 var17 = new MiscSettings.1();
         var16.setOnPreferenceChangeListener(var17);
         if(var9.vibrate > 0) {
            ((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_notify_vibrate")).setChecked((boolean)1);
            HtcCheckBoxPreference var18 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_notify_vibrate");
            String var19 = this.getText(2131362281).toString();
            var18.setSummary(var19);
         } else {
            ((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_notify_vibrate")).setChecked((boolean)0);
            HtcCheckBoxPreference var36 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_notify_vibrate");
            String var37 = this.getText(2131362282).toString();
            var36.setSummary(var37);
         }

         HtcCheckBoxPreference var20 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_notify_vibrate");
         String var21 = this.getText(2131362281).toString();
         var20.setSummaryOn(var21);
         HtcCheckBoxPreference var22 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_notify_vibrate");
         String var23 = this.getText(2131362282).toString();
         var22.setSummaryOff(var23);
         HtcCheckBoxPreference var24 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_notify_vibrate");
         MiscSettings.2 var25 = new MiscSettings.2();
         var24.setOnPreferenceChangeListener(var25);
         if(var9.sound > 0) {
            ((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_notify_sound")).setChecked((boolean)1);
            HtcCheckBoxPreference var26 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_notify_sound");
            String var27 = this.getText(2131362281).toString();
            var26.setSummary(var27);
         } else {
            ((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_notify_sound")).setChecked((boolean)0);
            HtcCheckBoxPreference var38 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_notify_sound");
            String var39 = this.getText(2131362282).toString();
            var38.setSummary(var39);
         }

         HtcCheckBoxPreference var28 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_notify_sound");
         String var29 = this.getText(2131362281).toString();
         var28.setSummaryOn(var29);
         HtcCheckBoxPreference var30 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_notify_sound");
         String var31 = this.getText(2131362282).toString();
         var30.setSummaryOff(var31);
         HtcCheckBoxPreference var32 = (HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_notify_sound");
         MiscSettings.3 var33 = new MiscSettings.3();
         var32.setOnPreferenceChangeListener(var33);
      }
   }

   private final ContentValues gatherValues() {
      ContentValues var1 = new ContentValues();
      Account var2 = MailProvider.getAccount(this.mAccountId);
      if(((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_email_notifications")).isChecked() == 1) {
         Integer var3 = Integer.valueOf(1);
         var1.put("_emailnotifications", var3);
         var2.mEmailNotifications = 1;
      } else {
         Integer var6 = Integer.valueOf(0);
         var1.put("_emailnotifications", var6);
         var2.mEmailNotifications = 0;
      }

      if(((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_notify_vibrate")).isChecked() == 1) {
         Integer var4 = Integer.valueOf(1);
         var1.put("_vibrate", var4);
         var2.vibrate = 1;
      } else {
         Integer var7 = Integer.valueOf(0);
         var1.put("_vibrate", var7);
         var2.vibrate = 0;
      }

      if(((HtcCheckBoxPreference)this.preferenceScreen.findPreference("account_detail_notify_sound")).isChecked() == 1) {
         Integer var5 = Integer.valueOf(1);
         var1.put("_sound", var5);
         var2.sound = 1;
      } else {
         Integer var8 = Integer.valueOf(0);
         var1.put("_sound", var8);
         var2.sound = 0;
      }

      return var1;
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

   private void saveAccountNotify(boolean var1) {
      ContentValues var2 = new ContentValues();
      if(var1) {
         Integer var3 = Integer.valueOf(1);
         var2.put("_emailnotifications", var3);
      } else {
         Integer var11 = Integer.valueOf(0);
         var2.put("_emailnotifications", var11);
      }

      IContentProvider var4 = MailProvider.instance();
      Uri var5 = this.getIntent().getData();
      if(DEBUG) {
         String var6 = this.TAG;
         String var7 = "saveAccountNotify>" + var5 + "," + var2;
         ll.d(var6, var7);
      }

      Object var8 = null;
      Object var9 = null;

      try {
         var4.update(var5, var2, (String)var8, (String[])var9);
      } catch (DeadObjectException var14) {
         ;
      } catch (RemoteException var15) {
         ;
      }
   }

   private void saveAccountSound(boolean var1) {
      ContentValues var2 = new ContentValues();
      if(var1) {
         Integer var3 = Integer.valueOf(1);
         var2.put("_sound", var3);
      } else {
         Integer var11 = Integer.valueOf(0);
         var2.put("_sound", var11);
      }

      IContentProvider var4 = MailProvider.instance();
      Uri var5 = this.getIntent().getData();
      if(DEBUG) {
         String var6 = this.TAG;
         String var7 = "saveAccountSound>" + var5 + "," + var2;
         ll.d(var6, var7);
      }

      Object var8 = null;
      Object var9 = null;

      try {
         var4.update(var5, var2, (String)var8, (String[])var9);
      } catch (DeadObjectException var14) {
         ;
      } catch (RemoteException var15) {
         ;
      }
   }

   private void saveAccountVibrate(boolean var1) {
      ContentValues var2 = new ContentValues();
      if(var1) {
         Integer var3 = Integer.valueOf(1);
         var2.put("_vibrate", var3);
      } else {
         Integer var11 = Integer.valueOf(0);
         var2.put("_vibrate", var11);
      }

      IContentProvider var4 = MailProvider.instance();
      Uri var5 = this.getIntent().getData();
      if(DEBUG) {
         String var6 = this.TAG;
         String var7 = "saveAccountVibrate>" + var5 + "," + var2;
         ll.d(var6, var7);
      }

      Object var8 = null;
      Object var9 = null;

      try {
         var4.update(var5, var2, (String)var8, (String[])var9);
      } catch (DeadObjectException var14) {
         ;
      } catch (RemoteException var15) {
         ;
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
   }

   private final void updateFetchTime(Uri var1) {
      MailProvider.getAccount(ContentUris.parseId(var1)).updateFetchTime();
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.addPreferencesFromResource(2130968593);
      HtcPreferenceScreen var2 = this.getPreferenceScreen();
      this.preferenceScreen = var2;
      this.bind();
   }

   protected final void onDestroy() {
      super.onDestroy();
   }

   protected final void onResume() {
      super.onResume();
      if(DEBUG) {
         ll.d(this.TAG, "# do register");
      }
   }

   protected final void onStop() {
      super.onStop();
      if(DEBUG) {
         ll.d(this.TAG, "# un register");
      }
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
         int var19 = Log.e(this.TAG, "Could not read data.", var20);
         var17 = null;
         return var17;
      }

      var17 = var14;
      return var17;
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
         int var13 = Log.e(this.TAG, "Could not writeSig.", var14);
      }
   }

   class 3 implements OnPreferenceChangeListener {

      3() {}

      public boolean onPreferenceChange(HtcPreference var1, Object var2) {
         Boolean var3 = (Boolean)var2;
         MiscSettings var4 = MiscSettings.this;
         boolean var5 = var3.booleanValue();
         var4.saveAccountSound(var5);
         HtcCheckBoxPreference var6 = (HtcCheckBoxPreference)MiscSettings.this.preferenceScreen.findPreference("account_detail_notify_sound");
         boolean var7 = var3.booleanValue();
         var6.setChecked(var7);
         MiscSettings.this.updateAccount();
         return true;
      }
   }

   class 1 implements OnPreferenceChangeListener {

      1() {}

      public boolean onPreferenceChange(HtcPreference var1, Object var2) {
         Boolean var3 = (Boolean)var2;
         MiscSettings var4 = MiscSettings.this;
         boolean var5 = var3.booleanValue();
         var4.saveAccountNotify(var5);
         HtcCheckBoxPreference var6 = (HtcCheckBoxPreference)MiscSettings.this.preferenceScreen.findPreference("account_detail_email_notifications");
         boolean var7 = var3.booleanValue();
         var6.setChecked(var7);
         MiscSettings.this.updateAccount();
         return true;
      }
   }

   class 2 implements OnPreferenceChangeListener {

      2() {}

      public boolean onPreferenceChange(HtcPreference var1, Object var2) {
         Boolean var3 = (Boolean)var2;
         MiscSettings var4 = MiscSettings.this;
         boolean var5 = var3.booleanValue();
         var4.saveAccountVibrate(var5);
         HtcCheckBoxPreference var6 = (HtcCheckBoxPreference)MiscSettings.this.preferenceScreen.findPreference("account_detail_notify_vibrate");
         boolean var7 = var3.booleanValue();
         var6.setChecked(var7);
         MiscSettings.this.updateAccount();
         return true;
      }
   }
}
