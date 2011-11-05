package com.seven.Z7.shared;

import java.util.Arrays;
import java.util.List;

public class PreferenceConstants {

   public PreferenceConstants() {}

   static enum PreferenceType {

      // $FF: synthetic field
      private static final PreferenceConstants.PreferenceType[] $VALUES;
      ACCOUNT_LOCAL("ACCOUNT_LOCAL", 3),
      ACCOUNT_SYNCED("ACCOUNT_SYNCED", 2),
      GLOBAL_LOCAL("GLOBAL_LOCAL", 1),
      GLOBAL_SYNCED("GLOBAL_SYNCED", 0);


      static {
         PreferenceConstants.PreferenceType[] var0 = new PreferenceConstants.PreferenceType[4];
         PreferenceConstants.PreferenceType var1 = GLOBAL_SYNCED;
         var0[0] = var1;
         PreferenceConstants.PreferenceType var2 = GLOBAL_LOCAL;
         var0[1] = var2;
         PreferenceConstants.PreferenceType var3 = ACCOUNT_SYNCED;
         var0[2] = var3;
         PreferenceConstants.PreferenceType var4 = ACCOUNT_LOCAL;
         var0[3] = var4;
         $VALUES = var0;
      }

      private PreferenceType(String var1, int var2) {}
   }

   static enum PreferenceGroups {

      // $FF: synthetic field
      private static final PreferenceConstants.PreferenceGroups[] $VALUES;
      API("API", 7),
      EMAIL("EMAIL", 5),
      IM("IM", 6),
      LOG("LOG", 3),
      NOTIFICATIONS("NOTIFICATIONS", 2),
      PAUSE("PAUSE", 4),
      PRESET_MESSAGES("PRESET_MESSAGES", 1),
      QUIET_TIME("QUIET_TIME", 0);


      static {
         PreferenceConstants.PreferenceGroups[] var0 = new PreferenceConstants.PreferenceGroups[8];
         PreferenceConstants.PreferenceGroups var1 = QUIET_TIME;
         var0[0] = var1;
         PreferenceConstants.PreferenceGroups var2 = PRESET_MESSAGES;
         var0[1] = var2;
         PreferenceConstants.PreferenceGroups var3 = NOTIFICATIONS;
         var0[2] = var3;
         PreferenceConstants.PreferenceGroups var4 = LOG;
         var0[3] = var4;
         PreferenceConstants.PreferenceGroups var5 = PAUSE;
         var0[4] = var5;
         PreferenceConstants.PreferenceGroups var6 = EMAIL;
         var0[5] = var6;
         PreferenceConstants.PreferenceGroups var7 = IM;
         var0[6] = var7;
         PreferenceConstants.PreferenceGroups var8 = API;
         var0[7] = var8;
         $VALUES = var0;
      }

      private PreferenceGroups(String var1, int var2) {}
   }

   static enum Preferences {

      // $FF: synthetic field
      private static final PreferenceConstants.Preferences[] $VALUES;
      EMAIL_TRUNC_MAX_ATTACHMENT_SIZE,
      EMAIL_TRUNC_SIGNATURE,
      EMAIL_TRUNC_SIZE,
      EMAIL_TRUNC_TIME,
      EMAIL_TRUNC_USE_SIGNATURE,
      IM_AUDIO_NOTIFICATION,
      IM_AUTO_SIGN_IN,
      IM_NEW_MESSAGE_NOTIFICATION,
      IM_SAVE_PASSWORD,
      IM_VIBRATE_NOTIFICATION,
      LOG_ALLOW_REMOTE_DOWNLOAD,
      LOG_LEVEL,
      NOTIFICATION_AUDIABLE,
      NOTIFICATION_VIBRATE,
      NOTIFICATION_VISUAL,
      PAUSE,
      QUIET_LOW_POWER,
      QUIET_ROAMING,
      QUIET_TIME_NIGHTS,
      QUIET_TIME_WEEKENDS,
      QUIET_WEEKDAYS_PUSH_END,
      QUIET_WEEKDAYS_PUSH_START,
      QUIET_WEEKDENDS_PUSH_END,
      QUIET_WEEKDEND_PUSH_FRI,
      QUIET_WEEKDEND_PUSH_MON,
      QUIET_WEEKDEND_PUSH_SAT,
      QUIET_WEEKDEND_PUSH_SUN,
      QUIET_WEEKDEND_PUSH_THU,
      QUIET_WEEKDEND_PUSH_TUE,
      QUIET_WEEKDEND_PUSH_WED,
      QUIET_WEEKENDS_PUSH_START,
      SERVICE_CONTACT_REVERSE_DISPLAY_NAME,
      SERVICE_ENABLE_EXTERNAL_LOG,
      SERVICE_SEND_NOTIFICATIONS;
      public PreferenceConstants.PreferenceGroups mGroup;
      public PreferenceConstants.PreferenceType mType;
      public int mValueType;


      static {
         PreferenceConstants.PreferenceType var0 = PreferenceConstants.PreferenceType.GLOBAL_SYNCED;
         PreferenceConstants.PreferenceGroups var1 = PreferenceConstants.PreferenceGroups.QUIET_TIME;
         QUIET_TIME_NIGHTS = new PreferenceConstants.Preferences("QUIET_TIME_NIGHTS", 0, var0, 4, var1);
         PreferenceConstants.PreferenceType var2 = PreferenceConstants.PreferenceType.GLOBAL_SYNCED;
         PreferenceConstants.PreferenceGroups var3 = PreferenceConstants.PreferenceGroups.QUIET_TIME;
         byte var4 = 4;
         QUIET_TIME_WEEKENDS = new PreferenceConstants.Preferences("QUIET_TIME_WEEKENDS", 1, var2, var4, var3);
         PreferenceConstants.PreferenceType var5 = PreferenceConstants.PreferenceType.GLOBAL_SYNCED;
         PreferenceConstants.PreferenceGroups var6 = PreferenceConstants.PreferenceGroups.QUIET_TIME;
         byte var7 = 2;
         byte var8 = 4;
         QUIET_LOW_POWER = new PreferenceConstants.Preferences("QUIET_LOW_POWER", var7, var5, var8, var6);
         PreferenceConstants.PreferenceType var9 = PreferenceConstants.PreferenceType.GLOBAL_SYNCED;
         PreferenceConstants.PreferenceGroups var10 = PreferenceConstants.PreferenceGroups.QUIET_TIME;
         byte var11 = 3;
         byte var12 = 4;
         QUIET_ROAMING = new PreferenceConstants.Preferences("QUIET_ROAMING", var11, var9, var12, var10);
         PreferenceConstants.PreferenceType var13 = PreferenceConstants.PreferenceType.GLOBAL_LOCAL;
         PreferenceConstants.PreferenceGroups var14 = PreferenceConstants.PreferenceGroups.QUIET_TIME;
         byte var15 = 4;
         byte var16 = 2;
         QUIET_WEEKDAYS_PUSH_START = new PreferenceConstants.Preferences("QUIET_WEEKDAYS_PUSH_START", var15, var13, var16, var14);
         PreferenceConstants.PreferenceType var17 = PreferenceConstants.PreferenceType.GLOBAL_LOCAL;
         PreferenceConstants.PreferenceGroups var18 = PreferenceConstants.PreferenceGroups.QUIET_TIME;
         byte var19 = 2;
         QUIET_WEEKDAYS_PUSH_END = new PreferenceConstants.Preferences("QUIET_WEEKDAYS_PUSH_END", 5, var17, var19, var18);
         PreferenceConstants.PreferenceType var20 = PreferenceConstants.PreferenceType.GLOBAL_LOCAL;
         PreferenceConstants.PreferenceGroups var21 = PreferenceConstants.PreferenceGroups.QUIET_TIME;
         byte var22 = 2;
         QUIET_WEEKENDS_PUSH_START = new PreferenceConstants.Preferences("QUIET_WEEKENDS_PUSH_START", 6, var20, var22, var21);
         PreferenceConstants.PreferenceType var23 = PreferenceConstants.PreferenceType.GLOBAL_LOCAL;
         PreferenceConstants.PreferenceGroups var24 = PreferenceConstants.PreferenceGroups.QUIET_TIME;
         byte var25 = 2;
         QUIET_WEEKDENDS_PUSH_END = new PreferenceConstants.Preferences("QUIET_WEEKDENDS_PUSH_END", 7, var23, var25, var24);
         PreferenceConstants.PreferenceType var26 = PreferenceConstants.PreferenceType.GLOBAL_SYNCED;
         PreferenceConstants.PreferenceGroups var27 = PreferenceConstants.PreferenceGroups.QUIET_TIME;
         byte var28 = 4;
         QUIET_WEEKDEND_PUSH_SAT = new PreferenceConstants.Preferences("QUIET_WEEKDEND_PUSH_SAT", 8, var26, var28, var27);
         PreferenceConstants.PreferenceType var29 = PreferenceConstants.PreferenceType.GLOBAL_SYNCED;
         PreferenceConstants.PreferenceGroups var30 = PreferenceConstants.PreferenceGroups.QUIET_TIME;
         byte var31 = 4;
         QUIET_WEEKDEND_PUSH_SUN = new PreferenceConstants.Preferences("QUIET_WEEKDEND_PUSH_SUN", 9, var29, var31, var30);
         PreferenceConstants.PreferenceType var32 = PreferenceConstants.PreferenceType.GLOBAL_SYNCED;
         PreferenceConstants.PreferenceGroups var33 = PreferenceConstants.PreferenceGroups.QUIET_TIME;
         byte var34 = 4;
         QUIET_WEEKDEND_PUSH_MON = new PreferenceConstants.Preferences("QUIET_WEEKDEND_PUSH_MON", 10, var32, var34, var33);
         PreferenceConstants.PreferenceType var35 = PreferenceConstants.PreferenceType.GLOBAL_SYNCED;
         PreferenceConstants.PreferenceGroups var36 = PreferenceConstants.PreferenceGroups.QUIET_TIME;
         byte var37 = 4;
         QUIET_WEEKDEND_PUSH_TUE = new PreferenceConstants.Preferences("QUIET_WEEKDEND_PUSH_TUE", 11, var35, var37, var36);
         PreferenceConstants.PreferenceType var38 = PreferenceConstants.PreferenceType.GLOBAL_SYNCED;
         PreferenceConstants.PreferenceGroups var39 = PreferenceConstants.PreferenceGroups.QUIET_TIME;
         byte var40 = 4;
         QUIET_WEEKDEND_PUSH_WED = new PreferenceConstants.Preferences("QUIET_WEEKDEND_PUSH_WED", 12, var38, var40, var39);
         PreferenceConstants.PreferenceType var41 = PreferenceConstants.PreferenceType.GLOBAL_SYNCED;
         PreferenceConstants.PreferenceGroups var42 = PreferenceConstants.PreferenceGroups.QUIET_TIME;
         byte var43 = 4;
         QUIET_WEEKDEND_PUSH_THU = new PreferenceConstants.Preferences("QUIET_WEEKDEND_PUSH_THU", 13, var41, var43, var42);
         PreferenceConstants.PreferenceType var44 = PreferenceConstants.PreferenceType.GLOBAL_SYNCED;
         PreferenceConstants.PreferenceGroups var45 = PreferenceConstants.PreferenceGroups.QUIET_TIME;
         byte var46 = 4;
         QUIET_WEEKDEND_PUSH_FRI = new PreferenceConstants.Preferences("QUIET_WEEKDEND_PUSH_FRI", 14, var44, var46, var45);
         PreferenceConstants.PreferenceType var47 = PreferenceConstants.PreferenceType.GLOBAL_LOCAL;
         PreferenceConstants.PreferenceGroups var48 = PreferenceConstants.PreferenceGroups.NOTIFICATIONS;
         byte var49 = 4;
         NOTIFICATION_AUDIABLE = new PreferenceConstants.Preferences("NOTIFICATION_AUDIABLE", 15, var47, var49, var48);
         PreferenceConstants.PreferenceType var50 = PreferenceConstants.PreferenceType.GLOBAL_LOCAL;
         PreferenceConstants.PreferenceGroups var51 = PreferenceConstants.PreferenceGroups.NOTIFICATIONS;
         byte var52 = 4;
         NOTIFICATION_VIBRATE = new PreferenceConstants.Preferences("NOTIFICATION_VIBRATE", 16, var50, var52, var51);
         PreferenceConstants.PreferenceType var53 = PreferenceConstants.PreferenceType.GLOBAL_LOCAL;
         PreferenceConstants.PreferenceGroups var54 = PreferenceConstants.PreferenceGroups.NOTIFICATIONS;
         byte var55 = 4;
         NOTIFICATION_VISUAL = new PreferenceConstants.Preferences("NOTIFICATION_VISUAL", 17, var53, var55, var54);
         PreferenceConstants.PreferenceType var56 = PreferenceConstants.PreferenceType.GLOBAL_SYNCED;
         PreferenceConstants.PreferenceGroups var57 = PreferenceConstants.PreferenceGroups.LOG;
         byte var58 = 2;
         LOG_LEVEL = new PreferenceConstants.Preferences("LOG_LEVEL", 18, var56, var58, var57);
         PreferenceConstants.PreferenceType var59 = PreferenceConstants.PreferenceType.GLOBAL_SYNCED;
         PreferenceConstants.PreferenceGroups var60 = PreferenceConstants.PreferenceGroups.LOG;
         byte var61 = 4;
         LOG_ALLOW_REMOTE_DOWNLOAD = new PreferenceConstants.Preferences("LOG_ALLOW_REMOTE_DOWNLOAD", 19, var59, var61, var60);
         PreferenceConstants.PreferenceType var62 = PreferenceConstants.PreferenceType.GLOBAL_LOCAL;
         PreferenceConstants.PreferenceGroups var63 = PreferenceConstants.PreferenceGroups.PAUSE;
         byte var64 = 4;
         PAUSE = new PreferenceConstants.Preferences("PAUSE", 20, var62, var64, var63);
         PreferenceConstants.PreferenceType var65 = PreferenceConstants.PreferenceType.ACCOUNT_SYNCED;
         PreferenceConstants.PreferenceGroups var66 = PreferenceConstants.PreferenceGroups.EMAIL;
         byte var67 = 2;
         EMAIL_TRUNC_TIME = new PreferenceConstants.Preferences("EMAIL_TRUNC_TIME", 21, var65, var67, var66);
         PreferenceConstants.PreferenceType var68 = PreferenceConstants.PreferenceType.ACCOUNT_SYNCED;
         PreferenceConstants.PreferenceGroups var69 = PreferenceConstants.PreferenceGroups.EMAIL;
         byte var70 = 2;
         EMAIL_TRUNC_SIZE = new PreferenceConstants.Preferences("EMAIL_TRUNC_SIZE", 22, var68, var70, var69);
         PreferenceConstants.PreferenceType var71 = PreferenceConstants.PreferenceType.ACCOUNT_SYNCED;
         PreferenceConstants.PreferenceGroups var72 = PreferenceConstants.PreferenceGroups.EMAIL;
         byte var73 = 4;
         EMAIL_TRUNC_USE_SIGNATURE = new PreferenceConstants.Preferences("EMAIL_TRUNC_USE_SIGNATURE", 23, var71, var73, var72);
         PreferenceConstants.PreferenceType var74 = PreferenceConstants.PreferenceType.ACCOUNT_SYNCED;
         PreferenceConstants.PreferenceGroups var75 = PreferenceConstants.PreferenceGroups.EMAIL;
         byte var76 = 3;
         EMAIL_TRUNC_SIGNATURE = new PreferenceConstants.Preferences("EMAIL_TRUNC_SIGNATURE", 24, var74, var76, var75);
         PreferenceConstants.PreferenceType var77 = PreferenceConstants.PreferenceType.ACCOUNT_SYNCED;
         PreferenceConstants.PreferenceGroups var78 = PreferenceConstants.PreferenceGroups.EMAIL;
         byte var79 = 2;
         EMAIL_TRUNC_MAX_ATTACHMENT_SIZE = new PreferenceConstants.Preferences("EMAIL_TRUNC_MAX_ATTACHMENT_SIZE", 25, var77, var79, var78);
         PreferenceConstants.PreferenceType var80 = PreferenceConstants.PreferenceType.ACCOUNT_LOCAL;
         PreferenceConstants.PreferenceGroups var81 = PreferenceConstants.PreferenceGroups.IM;
         byte var82 = 4;
         IM_AUTO_SIGN_IN = new PreferenceConstants.Preferences("IM_AUTO_SIGN_IN", 26, var80, var82, var81);
         PreferenceConstants.PreferenceType var83 = PreferenceConstants.PreferenceType.ACCOUNT_LOCAL;
         PreferenceConstants.PreferenceGroups var84 = PreferenceConstants.PreferenceGroups.IM;
         byte var85 = 4;
         IM_NEW_MESSAGE_NOTIFICATION = new PreferenceConstants.Preferences("IM_NEW_MESSAGE_NOTIFICATION", 27, var83, var85, var84);
         PreferenceConstants.PreferenceType var86 = PreferenceConstants.PreferenceType.ACCOUNT_LOCAL;
         PreferenceConstants.PreferenceGroups var87 = PreferenceConstants.PreferenceGroups.IM;
         byte var88 = 4;
         IM_AUDIO_NOTIFICATION = new PreferenceConstants.Preferences("IM_AUDIO_NOTIFICATION", 28, var86, var88, var87);
         PreferenceConstants.PreferenceType var89 = PreferenceConstants.PreferenceType.ACCOUNT_LOCAL;
         PreferenceConstants.PreferenceGroups var90 = PreferenceConstants.PreferenceGroups.IM;
         byte var91 = 4;
         IM_VIBRATE_NOTIFICATION = new PreferenceConstants.Preferences("IM_VIBRATE_NOTIFICATION", 29, var89, var91, var90);
         PreferenceConstants.PreferenceType var92 = PreferenceConstants.PreferenceType.ACCOUNT_LOCAL;
         PreferenceConstants.PreferenceGroups var93 = PreferenceConstants.PreferenceGroups.IM;
         byte var94 = 4;
         IM_SAVE_PASSWORD = new PreferenceConstants.Preferences("IM_SAVE_PASSWORD", 30, var92, var94, var93);
         PreferenceConstants.PreferenceType var95 = PreferenceConstants.PreferenceType.GLOBAL_LOCAL;
         PreferenceConstants.PreferenceGroups var96 = PreferenceConstants.PreferenceGroups.API;
         byte var97 = 4;
         SERVICE_SEND_NOTIFICATIONS = new PreferenceConstants.Preferences("SERVICE_SEND_NOTIFICATIONS", 31, var95, var97, var96);
         PreferenceConstants.PreferenceType var98 = PreferenceConstants.PreferenceType.GLOBAL_LOCAL;
         PreferenceConstants.PreferenceGroups var99 = PreferenceConstants.PreferenceGroups.API;
         byte var100 = 4;
         SERVICE_CONTACT_REVERSE_DISPLAY_NAME = new PreferenceConstants.Preferences("SERVICE_CONTACT_REVERSE_DISPLAY_NAME", 32, var98, var100, var99);
         PreferenceConstants.PreferenceType var101 = PreferenceConstants.PreferenceType.GLOBAL_LOCAL;
         PreferenceConstants.PreferenceGroups var102 = PreferenceConstants.PreferenceGroups.API;
         byte var103 = 4;
         SERVICE_ENABLE_EXTERNAL_LOG = new PreferenceConstants.Preferences("SERVICE_ENABLE_EXTERNAL_LOG", 33, var101, var103, var102);
         PreferenceConstants.Preferences[] var104 = new PreferenceConstants.Preferences[34];
         PreferenceConstants.Preferences var105 = QUIET_TIME_NIGHTS;
         var104[0] = var105;
         PreferenceConstants.Preferences var106 = QUIET_TIME_WEEKENDS;
         var104[1] = var106;
         PreferenceConstants.Preferences var107 = QUIET_LOW_POWER;
         var104[2] = var107;
         PreferenceConstants.Preferences var108 = QUIET_ROAMING;
         var104[3] = var108;
         PreferenceConstants.Preferences var109 = QUIET_WEEKDAYS_PUSH_START;
         var104[4] = var109;
         PreferenceConstants.Preferences var110 = QUIET_WEEKDAYS_PUSH_END;
         var104[5] = var110;
         PreferenceConstants.Preferences var111 = QUIET_WEEKENDS_PUSH_START;
         var104[6] = var111;
         PreferenceConstants.Preferences var112 = QUIET_WEEKDENDS_PUSH_END;
         var104[7] = var112;
         PreferenceConstants.Preferences var113 = QUIET_WEEKDEND_PUSH_SAT;
         var104[8] = var113;
         PreferenceConstants.Preferences var114 = QUIET_WEEKDEND_PUSH_SUN;
         var104[9] = var114;
         PreferenceConstants.Preferences var115 = QUIET_WEEKDEND_PUSH_MON;
         var104[10] = var115;
         PreferenceConstants.Preferences var116 = QUIET_WEEKDEND_PUSH_TUE;
         var104[11] = var116;
         PreferenceConstants.Preferences var117 = QUIET_WEEKDEND_PUSH_WED;
         var104[12] = var117;
         PreferenceConstants.Preferences var118 = QUIET_WEEKDEND_PUSH_THU;
         var104[13] = var118;
         PreferenceConstants.Preferences var119 = QUIET_WEEKDEND_PUSH_FRI;
         var104[14] = var119;
         PreferenceConstants.Preferences var120 = NOTIFICATION_AUDIABLE;
         var104[15] = var120;
         PreferenceConstants.Preferences var121 = NOTIFICATION_VIBRATE;
         var104[16] = var121;
         PreferenceConstants.Preferences var122 = NOTIFICATION_VISUAL;
         var104[17] = var122;
         PreferenceConstants.Preferences var123 = LOG_LEVEL;
         var104[18] = var123;
         PreferenceConstants.Preferences var124 = LOG_ALLOW_REMOTE_DOWNLOAD;
         var104[19] = var124;
         PreferenceConstants.Preferences var125 = PAUSE;
         var104[20] = var125;
         PreferenceConstants.Preferences var126 = EMAIL_TRUNC_TIME;
         var104[21] = var126;
         PreferenceConstants.Preferences var127 = EMAIL_TRUNC_SIZE;
         var104[22] = var127;
         PreferenceConstants.Preferences var128 = EMAIL_TRUNC_USE_SIGNATURE;
         var104[23] = var128;
         PreferenceConstants.Preferences var129 = EMAIL_TRUNC_SIGNATURE;
         var104[24] = var129;
         PreferenceConstants.Preferences var130 = EMAIL_TRUNC_MAX_ATTACHMENT_SIZE;
         var104[25] = var130;
         PreferenceConstants.Preferences var131 = IM_AUTO_SIGN_IN;
         var104[26] = var131;
         PreferenceConstants.Preferences var132 = IM_NEW_MESSAGE_NOTIFICATION;
         var104[27] = var132;
         PreferenceConstants.Preferences var133 = IM_AUDIO_NOTIFICATION;
         var104[28] = var133;
         PreferenceConstants.Preferences var134 = IM_VIBRATE_NOTIFICATION;
         var104[29] = var134;
         PreferenceConstants.Preferences var135 = IM_SAVE_PASSWORD;
         var104[30] = var135;
         PreferenceConstants.Preferences var136 = SERVICE_SEND_NOTIFICATIONS;
         var104[31] = var136;
         PreferenceConstants.Preferences var137 = SERVICE_CONTACT_REVERSE_DISPLAY_NAME;
         var104[32] = var137;
         PreferenceConstants.Preferences var138 = SERVICE_ENABLE_EXTERNAL_LOG;
         var104[33] = var138;
         $VALUES = var104;
      }

      private Preferences(String var1, int var2, PreferenceConstants.PreferenceType var3, int var4, PreferenceConstants.PreferenceGroups var5) {
         this.mType = var3;
         this.mValueType = var4;
         this.mGroup = var5;
      }

      public static int getType(String var0) {
         int var1;
         try {
            var1 = valueOf(var0).mValueType;
         } catch (IllegalArgumentException var3) {
            var1 = 0;
         }

         return var1;
      }
   }

   public interface ImAccountPreferences {

      String KEY_checkbox_audio_notification = "checkbox_audio_notification";
      String KEY_checkbox_auto_sign_in = "checkbox_auto_sign_in";
      String KEY_checkbox_new_message_notification = "checkbox_new_message_notification";
      String KEY_checkbox_save_password = "checkbox_save_password";
      String KEY_checkbox_vibrate = "checkbox_vibrate";

   }

   public interface GeneralPreferences {

      String KEY_checkbox_allow_log_download = "checkbox_allow_log_download";
      String KEY_checkbox_beep = "checkbox_beep";
      String KEY_checkbox_quiet_time_low_power = "checkbox_quiet_time_low_power";
      String KEY_checkbox_quiet_time_nights = "checkbox_quiet_time_nights";
      String KEY_checkbox_quiet_time_roaming = "checkbox_quiet_time_roaming";
      String KEY_checkbox_quiet_time_weekends = "checkbox_quiet_time_weekends";
      String KEY_checkbox_save_attachments_without_file_browser = "checkbox_save_attachments_without_file_browser";
      String KEY_checkbox_vibrate = "checkbox_vibrate";
      String KEY_checkbox_visual = "checkbox_visual";
      String KEY_checkbox_weekend_fri = "checkbox_weekend_fri";
      String KEY_checkbox_weekend_mon = "checkbox_weekend_mon";
      String KEY_checkbox_weekend_sat = "checkbox_weekend_sat";
      String KEY_checkbox_weekend_sun = "checkbox_weekend_sun";
      String KEY_checkbox_weekend_thu = "checkbox_weekend_thu";
      String KEY_checkbox_weekend_tue = "checkbox_weekend_tue";
      String KEY_checkbox_weekend_wed = "checkbox_weekend_wed";
      String KEY_edittext_default_path_to_save_attachments = "edittext_default_path_to_save_attachments";
      String KEY_list_log_level = "list_log_level";
      String KEY_list_pause = "list_pause";
      String KEY_long_quiet_time_weekdays_push_start = "long_quiet_time_weekdays_push_start";
      String KEY_long_quiet_time_weekdays_push_stop = "long_quiet_time_weekdays_push_stop";
      String KEY_long_quiet_time_weekend_push_start = "long_quiet_time_weekend_push_start";
      String KEY_long_quiet_time_weekend_push_stop = "long_quiet_time_weekend_push_stop";
      String KEY_prefs_email_store_path_settings = "prefs_email_store_path_settings";
      String KEY_prefs_support_tools = "prefs_support_tools";
      String KEY_weekend_days = "weekend_days";
      String[] PEAK_TIME_KEYS;
      List<String> QUITE_TIME_KEYS;
      List<String> WEEKEND_DAYS_KEYS;


      static {
         String[] var0 = new String[]{"checkbox_weekend_sat", "checkbox_weekend_sun", "checkbox_weekend_mon", "checkbox_weekend_tue", "checkbox_weekend_wed", "checkbox_weekend_thu", "checkbox_weekend_fri"};
         WEEKEND_DAYS_KEYS = Arrays.asList(var0);
         String[] var1 = new String[]{"checkbox_quiet_time_nights", "checkbox_quiet_time_weekends", "checkbox_quiet_time_low_power", "checkbox_quiet_time_roaming", "long_quiet_time_weekdays_push_stop", "long_quiet_time_weekdays_push_start", "checkbox_weekend_sat", "checkbox_weekend_sun", "checkbox_weekend_mon", "checkbox_weekend_tue", "checkbox_weekend_wed", "checkbox_weekend_thu", "checkbox_weekend_fri"};
         QUITE_TIME_KEYS = Arrays.asList(var1);
         String[] var2 = new String[]{"checkbox_quiet_time_nights", "checkbox_quiet_time_weekends", "long_quiet_time_weekdays_push_start", "long_quiet_time_weekdays_push_stop", "checkbox_weekend_sun", "checkbox_weekend_mon", "checkbox_weekend_tue", "checkbox_weekend_wed", "checkbox_weekend_thu", "checkbox_weekend_fri", "checkbox_weekend_sat", "checkbox_quiet_time_roaming", "checkbox_quiet_time_low_power"};
         PEAK_TIME_KEYS = var2;
      }
   }

   public interface EmailAccountPreferences {

      String KEY_checkbox_mail_bar_cc_bcc = "checkbox_mail_bar_cc_bcc";
      String KEY_checkbox_mail_bar_mailing_lists = "checkbox_mail_bar_mailing_lists";
      String KEY_checkbox_pause_sync_calendar = "checkbox_pause_sync_calendar";
      String KEY_checkbox_pause_sync_contacts = "checkbox_pause_sync_contacts";
      String KEY_checkbox_sync_calendar = "checkbox_sync_calendar";
      String KEY_checkbox_sync_contacts = "checkbox_sync_contacts";
      String KEY_checkbox_use_sig_new_emails = "checkbox_use_sig_new_emails";
      String KEY_checkbox_use_sig_rep_or_fwd = "checkbox_use_sig_rep_or_fwd";
      String KEY_edittext_preference_nickname = "edittext_preference_nickname";
      String KEY_edittext_preference_reply_to = "edittext_preference_reply_to";
      String KEY_edittext_preference_sender_name = "edittext_preference_sender_name";
      String KEY_edittext_preference_user_sig = "edittext_preference_user_sig";
      String KEY_hidden_preference_max_attachment_size = "hidden_pref_max_attachment_size";
      String KEY_pref_trunc_size = "pref_trunc_size";
      String KEY_pref_trunc_time = "pref_truc_time";

   }
}
