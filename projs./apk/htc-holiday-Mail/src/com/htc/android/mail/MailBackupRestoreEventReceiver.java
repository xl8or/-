package com.htc.android.mail;

import com.htc.util.backup.BackupRestoreReceiver;

public class MailBackupRestoreEventReceiver extends BackupRestoreReceiver {

   public MailBackupRestoreEventReceiver() {}

   protected String getServiceClassName() {
      return "com.htc.android.mail.MailBackupRestoreService";
   }
}
