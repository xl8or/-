package com.htc.android.mail.eassvc.preferences;

import android.content.SharedPreferences.Editor;
import com.htc.android.mail.eassvc.preferences.BasePreferences;

public class MailPreferences extends BasePreferences {

   private static final String DEFAULT_EAS_COL_ID = "";
   private static final String DEFAULT_EAS_SYNC_KEY = "";
   private static final long DEFAULT_LAST_ANCHOR = 0L;
   private static final long DEFAULT_NEXT_ANCHOR = 0L;
   private static final String EAS_COL_ID_PROP = "email_col_id";
   private static final String EAS_SYNC_KEY_PROP = "email_eas_key";
   private static final String LAST_ANCHOR_PROP = "email_last";
   private static final String NEXT_ANCHOR_PROP = "email_next";


   public MailPreferences() {}

   public static String getColID() {
      return settings.getString("email_col_id", "");
   }

   public static long getLastAnchor() {
      return settings.getLong("email_last", 0L);
   }

   public static long getNextAnchor() {
      return settings.getLong("email_next", 0L);
   }

   public static String getSyncKey() {
      return settings.getString("email_eas_key", "");
   }

   public static void setColID(String var0) {
      Editor var1 = editor.putString("email_col_id", var0);
   }

   public static void setLastAnchor(long var0) {
      Editor var2 = editor.putLong("email_last", var0);
   }

   public static void setNextAnchor(long var0) {
      Editor var2 = editor.putLong("email_next", var0);
   }

   public static void setSyncKey(String var0) {
      Editor var1 = editor.putString("email_eas_key", var0);
   }
}
