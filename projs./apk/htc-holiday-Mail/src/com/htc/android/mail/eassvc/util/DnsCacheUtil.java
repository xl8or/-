package com.htc.android.mail.eassvc.util;

import com.htc.android.mail.Mail;
import com.htc.android.mail.eassvc.util.EASLog;

public class DnsCacheUtil {

   private static final boolean DEBUG = Mail.EAS_DEBUG;
   private static final String TAG = "EAS_DnsCacheUtil";
   private static boolean isLibLoadedOK = 0;


   static {
      try {
         System.loadLibrary("eas_jni");
         isLibLoadedOK = (boolean)1;
      } catch (Exception var0) {
         var0.printStackTrace();
      }
   }

   public DnsCacheUtil() {}

   public static void clear() {
      if(isLibLoadedOK) {
         if(DEBUG) {
            EASLog.d("EAS_DnsCacheUtil", "clear dns cache");
         }

         clearDNSCache();
      }
   }

   private static native void clearDNSCache();
}
