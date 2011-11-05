package com.google.android.gsf;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class Gservices {

   public static final String CHANGED_ACTION = "com.google.gservices.intent.action.GSERVICES_CHANGED";
   public static final Uri CONTENT_PREFIX_URI = Uri.parse("content://com.google.android.gsf.gservices/prefix");
   public static final Uri CONTENT_URI = Uri.parse("content://com.google.android.gsf.gservices");
   public static final Pattern FALSE_PATTERN = Pattern.compile("^(0|false|f|off|no|n)$", 2);
   public static final String OVERRIDE_ACTION = "com.google.gservices.intent.action.GSERVICES_OVERRIDE";
   public static final String TAG = "Gservices";
   public static final Pattern TRUE_PATTERN = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
   private static HashMap<String, String> sCache;
   private static ContentResolver sResolver;
   private static Object sVersionToken;


   public Gservices() {}

   private static void ensureCacheInitializedLocked(ContentResolver var0) {
      if(sCache == null) {
         sCache = new HashMap();
         sVersionToken = new Object();
         sResolver = var0;
         (new Gservices.1(var0)).start();
      }
   }

   public static boolean getBoolean(ContentResolver var0, String var1, boolean var2) {
      String var3 = getString(var0, var1);
      if(var3 != null && !var3.equals("")) {
         if(TRUE_PATTERN.matcher(var3).matches()) {
            var2 = true;
         } else if(FALSE_PATTERN.matcher(var3).matches()) {
            var2 = false;
         } else {
            String var4 = "attempt to read gservices key " + var1 + " (value \"" + var3 + "\") as boolean";
            int var5 = Log.w("Gservices", var4);
         }
      }

      return var2;
   }

   public static int getInt(ContentResolver var0, String var1, int var2) {
      String var3 = getString(var0, var1);
      int var5;
      if(var3 != null) {
         int var4;
         try {
            var4 = Integer.parseInt(var3);
         } catch (NumberFormatException var7) {
            var5 = var2;
            return var5;
         }

         var5 = var4;
      } else {
         var5 = var2;
      }

      return var5;
   }

   public static long getLong(ContentResolver var0, String var1, long var2) {
      String var4 = getString(var0, var1);
      long var7;
      if(var4 != null) {
         long var5;
         try {
            var5 = Long.parseLong(var4);
         } catch (NumberFormatException var10) {
            var7 = var2;
            return var7;
         }

         var7 = var5;
      } else {
         var7 = var2;
      }

      return var7;
   }

   public static String getString(ContentResolver var0, String var1) {
      return getString(var0, var1, (String)null);
   }

   public static String getString(ContentResolver param0, String param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public static Map<String, String> getStringsByPrefix(ContentResolver var0, String ... var1) {
      Uri var2 = CONTENT_PREFIX_URI;
      Object var4 = null;
      Object var6 = null;
      Cursor var7 = var0.query(var2, (String[])null, (String)var4, var1, (String)var6);
      TreeMap var8 = new TreeMap();
      if(var7 != null) {
         try {
            while(var7.moveToNext()) {
               String var9 = var7.getString(0);
               String var10 = var7.getString(1);
               var8.put(var9, var10);
            }
         } finally {
            var7.close();
         }
      }

      return var8;
   }

   public static Object getVersionToken(ContentResolver var0) {
      synchronized(Gservices.class) {
         ensureCacheInitializedLocked(var0);
         Object var1 = sVersionToken;
         return var1;
      }
   }

   static class 1 extends Thread {

      // $FF: synthetic field
      final ContentResolver val$cr;


      1(ContentResolver var1) {
         this.val$cr = var1;
      }

      public void run() {
         Looper.prepare();
         ContentResolver var1 = this.val$cr;
         Uri var2 = Gservices.CONTENT_URI;
         Looper var3 = Looper.myLooper();
         Handler var4 = new Handler(var3);
         Gservices.1.1 var5 = new Gservices.1.1(var4);
         var1.registerContentObserver(var2, (boolean)1, var5);
         Looper.loop();
      }

      class 1 extends ContentObserver {

         1(Handler var2) {
            super(var2);
         }

         public void onChange(boolean var1) {
            synchronized(Gservices.class) {
               Gservices.sCache.clear();
               Object var2 = Gservices.sVersionToken = new Object();
            }
         }
      }
   }
}
