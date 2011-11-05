package com.google.android.finsky.download;

import android.net.Uri;
import android.os.Build.VERSION;

public final class DownloadManagerConstants {

   public static final String ACTION_DOWNLOAD_COMPLETED = "android.intent.action.DOWNLOAD_COMPLETED";
   public static final String ACTION_NOTIFICATION_CLICKED = "android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED";
   public static final Uri ALL_DOWNLOADS_CONTENT_URI = Uri.parse("content://downloads/all_downloads");
   public static final String ANDROID_AUTH_COOKIE_NAME = "ANDROID";
   public static final String ANDROID_SECURE_AUTH_COOKIE_NAME = "ANDROIDSECURE";
   public static final String ASSET_SOURCE = "asset_source";
   public static final String AUTO_UPDATE_KEY = "auto_update";
   public static final String COLUMN_ALLOWED_NETWORK_TYPES = "allowed_network_types";
   public static final String COLUMN_ALLOW_ROAMING = "allow_roaming";
   public static final String COLUMN_APP_DATA = "entity";
   public static final String COLUMN_BYPASS_RECOMMENDED_SIZE_LIMIT = "bypass_recommended_size_limit";
   public static final String COLUMN_COOKIE_DATA = "cookiedata";
   public static final String COLUMN_CURRENT_BYTES = "current_bytes";
   public static final String COLUMN_DESCRIPTION = "description";
   public static final String COLUMN_DESTINATION = "destination";
   public static final String COLUMN_FILE_NAME = "_data";
   public static final String COLUMN_FILE_NAME_HINT = "hint";
   public static final String COLUMN_ID = "_id";
   public static final String COLUMN_IS_PUBLIC_API = "is_public_api";
   public static final String COLUMN_IS_VISIBLE_IN_DOWNLOADS_UI = "is_visible_in_downloads_ui";
   public static final String COLUMN_LAST_MODIFICATION = "lastmod";
   public static final String COLUMN_NOTIFICATION_CLASS = "notificationclass";
   public static final String COLUMN_NOTIFICATION_EXTRAS = "notificationextras";
   public static final String COLUMN_NOTIFICATION_PACKAGE = "notificationpackage";
   public static final String COLUMN_OTHER_UID = "otheruid";
   public static final String COLUMN_STATUS = "status";
   public static final String COLUMN_TITLE = "title";
   public static final String COLUMN_TOTAL_BYTES = "total_bytes";
   public static final String COLUMN_URI = "uri";
   public static final String COLUMN_VISIBILITY = "visibility";
   private static final Uri CONTENT_URI = Uri.parse("content://downloads/my_downloads");
   private static final String CONTENT_URI_STRING = "content://downloads/my_downloads";
   public static final int DESTINATION_CACHE_PARTITION_PURGEABLE = 2;
   public static final int DESTINATION_EXTERNAL = 0;
   public static final int DESTINATION_FILE_URI = 4;
   public static final int DESTINATION_SYSTEMCACHE_PARTITION = 5;
   public static final String DOWNLOAD_MAX_BYTES_OVER_MOBILE = "download_manager_max_bytes_over_mobile";
   private static final Uri FROYO_CONTENT_URI = Uri.parse("content://downloads/download");
   private static final String FROYO_CONTENT_URI_STRING = "content://downloads/download";
   public static final Uri PUBLICLY_ACCESSIBLE_DOWNLOADS_URI = Uri.parse("content://downloads/public_downloads");
   public static final String PUBLICLY_ACCESSIBLE_DOWNLOADS_URI_SEGMENT = "public_downloads";
   public static final int STATUS_PAUSED_BY_APP = 193;
   public static final int STATUS_PENDING = 190;
   public static final int STATUS_QUEUED_FOR_WIFI = 196;
   public static final int STATUS_RUNNING = 192;
   public static final int STATUS_WAITING_FOR_NETWORK = 195;
   public static final int STATUS_WAITING_TO_RETRY = 194;
   public static final int VISIBILITY_HIDDEN = 2;
   public static final int VISIBILITY_VISIBLE = 0;
   public static final int VISIBILITY_VISIBLE_NOTIFY_COMPLETED = 1;


   public DownloadManagerConstants() {}

   public static Uri getContentUri() {
      Uri var0;
      if(VERSION.SDK_INT <= 8) {
         var0 = FROYO_CONTENT_URI;
      } else {
         var0 = CONTENT_URI;
      }

      return var0;
   }

   public static String getContentUriString(String var0) {
      String var1;
      if(VERSION.SDK_INT <= 8) {
         var1 = "content://downloads/download/" + var0;
      } else {
         var1 = "content://downloads/my_downloads/" + var0;
      }

      return var1;
   }

   public static int getFileDestinationConstant() {
      byte var0;
      if(VERSION.SDK_INT <= 8) {
         var0 = 0;
      } else {
         var0 = 4;
      }

      return var0;
   }

   public static boolean isStatusClientError(int var0) {
      boolean var1;
      if(var0 >= 400 && var0 < 500) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isStatusCompleted(int var0) {
      boolean var1;
      if((var0 < 200 || var0 >= 300) && (var0 < 400 || var0 >= 600)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static boolean isStatusError(int var0) {
      boolean var1;
      if(var0 >= 400 && var0 < 600) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isStatusInformational(int var0) {
      boolean var1;
      if(var0 >= 100 && var0 < 200) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isStatusServerError(int var0) {
      boolean var1;
      if(var0 >= 500 && var0 < 600) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isStatusSuccess(int var0) {
      boolean var1;
      if(var0 >= 200 && var0 < 300) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
