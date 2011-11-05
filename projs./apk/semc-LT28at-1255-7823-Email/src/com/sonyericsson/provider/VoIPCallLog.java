package com.sonyericsson.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class VoIPCallLog {

   public static final String AUTHORITY = "com.sonyericsson.voip_call_log";
   public static final Uri CONTENT_URI = Uri.parse("content://com.sonyericsson.voip_call_log");
   public static final String PERMISSION = "com.sonyericsson.permission.VOIPCALLLOG";


   public VoIPCallLog() {}

   public static class VoIPCalls implements BaseColumns {

      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.sonyericsson.android.voip_call";
      public static final Uri CONTENT_URI = Uri.withAppendedPath(VoIPCallLog.CONTENT_URI, "calls");
      public static final String PROTOCOL = "protocol";


      public VoIPCalls() {}
   }
}
