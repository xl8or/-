package com.htc.android.mail;

import android.app.backup.BackupManager;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class BackupAgentTest extends PreferenceActivity {

   private BackupManager mBackupManager;


   public BackupAgentTest() {}

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      Context var2 = this.getBaseContext();
      BackupManager var3 = new BackupManager(var2);
      this.mBackupManager = var3;
      this.mBackupManager.dataChanged();
   }
}
