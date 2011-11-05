package com.htc.android.mail.eassvc.preferences;

import android.content.SharedPreferences.Editor;
import com.htc.android.mail.eassvc.preferences.BasePreferences;

public class ContactPreferences extends BasePreferences {

   private static final String CONTACT_LAST_SYNC_STATUS_PROP = "contact_last_sync_status";
   private static final String CONTACT_LAST_SYNC_TIME_PROP = "contact_last_sync_time";
   private static final String DEFAULT_EAS_COL_ID = "";
   private static final String DEFAULT_EAS_SYNC_KEY = "";
   private static final long DEFAULT_LAST_SYNC_STATUS = 255L;
   private static final long DEFAULT_LAST_SYNC_TIME = 0L;
   private static final String EAS_COL_ID_PROP = "contact_col_id";
   private static final String EAS_SYNC_KEY_PROP = "contact_eas_key";


   public ContactPreferences() {}

   public static String getColID() {
      return settings.getString("contact_col_id", "");
   }

   public static long getLastSyncStatus() {
      return settings.getLong("contact_last_sync_status", 65535L);
   }

   public static long getLastSyncTime() {
      return settings.getLong("contact_last_sync_time", 0L);
   }

   public static String getSyncKey() {
      return settings.getString("contact_eas_key", "");
   }

   public static void setColID(String var0) {
      Editor var1 = editor.putString("contact_col_id", var0);
   }

   public static void setLastSyncStatus(int var0) {
      Editor var1 = editor;
      long var2 = (long)var0;
      var1.putLong("contact_last_sync_status", var2);
   }

   public static void setLastSyncTime(long var0) {
      Editor var2 = editor.putLong("contact_last_sync_time", var0);
      boolean var3 = editor.commit();
   }

   public static void setSyncKey(String var0) {
      Editor var1 = editor.putString("contact_eas_key", var0);
   }
}
