package com.sonyericsson.eventstream_private;

import android.net.Uri;

public class EventStreamPrivate {

   public static final String AUTHORITY = "com.sonyericsson.eventstream";
   protected static final Uri BASE_URI = Uri.parse("content://com.sonyericsson.eventstream");
   public static final String SPLINE_PERMISSION = "com.sonyericsson.eventstream.SPLINE_PERMISSION";


   protected EventStreamPrivate() {}

   public interface FriendColumns {

      String PLUGIN_ID = "plugin_id";

   }

   public interface PluginColumns {

      String UID_NAME = "uid_name";

   }

   public interface EventColumns {

      String DISPLAY_NAME = "display_name";
      String PLUGIN_ID = "plugin_id";

   }

   public interface Image {

      String IMAGE_CACHE = "images";
      String IMAGE_URI_PARAM = "imageUri";

   }

   public interface Settings {

      String LAST_REFRESH_BROADCAST_TIMESTAMP = "last_refresh_broadcast_timestamp";
      String SETTINGS_PREF_NAME = "eventstream_settings";
      String SET_SETTING_INTENT = "com.sonyericsson.eventstream.SET_SETTING";


      public interface EnableStateSetting {

         int DISABLED = 0;
         int ENABLED = 1;
         String EXTRA_ENABLE_STATE = "enable_state";

      }

      public interface SyncSetting {

         String EXTRA_SYNC_TYPE = "sync_type";
         int SYNC_TYPE_AUTO = 1;
         int SYNC_TYPE_MANUAL;

      }
   }

   public interface RawQuery {

      String MIME_TYPE = "eventstream-rawquery";
      String RAW_QUERY_PATH = "rawquery";
      Uri URI = Uri.withAppendedPath(EventStreamPrivate.BASE_URI, "rawquery");


   }
}
