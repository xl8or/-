package com.android.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.app.backup.IBackupManager;
import android.app.backup.IBackupManager.Stub;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.provider.Settings.Secure;

public class PrivacySettings extends PreferenceActivity implements OnClickListener {

   private static final String AUTO_RESTORE = "auto_restore";
   private static final String BACKUP_CATEGORY = "backup_category";
   private static final String BACKUP_DATA = "backup_data";
   private static final int DIALOG_ERASE_BACKUP = 2;
   private static final String GSETTINGS_PROVIDER = "com.google.settings";
   private CheckBoxPreference mAutoRestore;
   private CheckBoxPreference mBackup;
   private Dialog mConfirmDialog;
   private int mDialogType;


   public PrivacySettings() {}

   private void setBackupEnabled(boolean var1) {
      IBackupManager var2 = Stub.asInterface(ServiceManager.getService("backup"));
      if(var2 != null) {
         try {
            var2.setBackupEnabled(var1);
         } catch (RemoteException var8) {
            CheckBoxPreference var4 = this.mBackup;
            byte var5;
            if(!var1) {
               var5 = 1;
            } else {
               var5 = 0;
            }

            var4.setChecked((boolean)var5);
            CheckBoxPreference var6 = this.mAutoRestore;
            byte var7;
            if(!var1) {
               var7 = 1;
            } else {
               var7 = 0;
            }

            var6.setEnabled((boolean)var7);
            return;
         }
      }

      this.mBackup.setChecked(var1);
      this.mAutoRestore.setEnabled(var1);
   }

   private void showEraseBackupDialog() {
      this.mBackup.setChecked((boolean)1);
      this.mDialogType = 2;
      CharSequence var1 = this.getResources().getText(2131232018);
      AlertDialog var2 = (new Builder(this)).setMessage(var1).setTitle(2131232017).setIcon(17301543).setPositiveButton(17039370, this).setNegativeButton(17039360, this).show();
      this.mConfirmDialog = var2;
   }

   private void updateToggles() {
      ContentResolver var1 = this.getContentResolver();
      byte var2;
      if(Secure.getInt(var1, "backup_enabled", 0) == 1) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      this.mBackup.setChecked((boolean)var2);
      CheckBoxPreference var3 = this.mAutoRestore;
      byte var4;
      if(Secure.getInt(var1, "backup_auto_restore", 1) == 1) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      var3.setChecked((boolean)var4);
      this.mAutoRestore.setEnabled((boolean)var2);
   }

   public void onClick(DialogInterface var1, int var2) {
      if(var2 == -1) {
         if(this.mDialogType == 2) {
            this.setBackupEnabled((boolean)0);
         }
      } else if(this.mDialogType == 2) {
         this.mBackup.setChecked((boolean)1);
         this.mAutoRestore.setEnabled((boolean)1);
      }

      this.mDialogType = 0;
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.addPreferencesFromResource(2130968603);
      PreferenceScreen var2 = this.getPreferenceScreen();
      CheckBoxPreference var3 = (CheckBoxPreference)var2.findPreference("backup_data");
      this.mBackup = var3;
      CheckBoxPreference var4 = (CheckBoxPreference)var2.findPreference("auto_restore");
      this.mAutoRestore = var4;
      if(this.getPackageManager().resolveContentProvider("com.google.settings", 0) == null) {
         Preference var5 = this.findPreference("backup_category");
         var2.removePreference(var5);
      }

      this.updateToggles();
   }

   public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2) {
      byte var3 = 1;
      CheckBoxPreference var4 = this.mBackup;
      if(var2.equals(var4)) {
         if(!this.mBackup.isChecked()) {
            this.showEraseBackupDialog();
         } else {
            this.setBackupEnabled((boolean)1);
         }
      } else {
         CheckBoxPreference var5 = this.mAutoRestore;
         if(var2.equals(var5)) {
            IBackupManager var6 = Stub.asInterface(ServiceManager.getService("backup"));
            if(var6 != null) {
               boolean var7 = this.mAutoRestore.isChecked();

               try {
                  var6.setAutoRestore(var7);
               } catch (RemoteException var10) {
                  CheckBoxPreference var9 = this.mAutoRestore;
                  if(var7) {
                     var3 = 0;
                  }

                  var9.setChecked((boolean)var3);
               }
            }
         }
      }

      return false;
   }

   public void onStop() {
      if(this.mConfirmDialog != null && this.mConfirmDialog.isShowing()) {
         this.mConfirmDialog.dismiss();
      }

      this.mConfirmDialog = null;
      this.mDialogType = 0;
      super.onStop();
   }
}
