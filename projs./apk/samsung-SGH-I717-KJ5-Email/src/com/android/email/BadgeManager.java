package com.android.email;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.android.email.Email;

public class BadgeManager {

   private static final String ACCOUNT_ID_SELECTION = "accountKey =?";
   private static final String[] ACCOUNT_UNREAD_COUNT_PROJECTION;
   public static final String BADGE_APPS_CLASS = "class";
   public static final String BADGE_APPS_COUNT = "badgecount";
   public static final String BADGE_APPS_PACKAGE = "package";
   public static final String BADGE_AUTHORITY = "com.sec.badge";
   public static final String BADGE_TABLE_APPS = "apps";
   private static final String TAG = "BadgeManager";
   public static boolean bInitBadgeProvider = 0;


   static {
      String[] var0 = new String[]{"unreadCount", "type"};
      ACCOUNT_UNREAD_COUNT_PROJECTION = var0;
   }

   public BadgeManager() {}

   public static int getCountofAccounts(Context param0) {
      // $FF: Couldn't be decompiled
   }

   public static int getUnreadTotalCountByAccountID(Context param0, long param1) {
      // $FF: Couldn't be decompiled
   }

   public static void insertBadgeProvider(Context var0) {
      if(var0 != null) {
         ContentResolver var1 = var0.getContentResolver();
         ContentValues var2 = new ContentValues();
         var2.put("package", "com.android.email");
         var2.put("class", "com.android.email.activity.Welcome");
         Integer var3 = Integer.valueOf(0);
         var2.put("badgecount", var3);
         StringBuilder var4 = new StringBuilder();
         String var5 = "content://".toString();
         StringBuilder var6 = var4.append(var5).append("com.sec.badge");
         String var7 = "/".toString();
         Uri var8 = Uri.parse(var6.append(var7).append("apps").toString());
         var1.insert(var8, var2);
         Email.logd("BadgeManager", "[insertBadgeProvider]");
         bInitBadgeProvider = (boolean)1;
      }
   }

   public static void updateBadgeProvider(Context var0) {
      if(var0 != null) {
         if(!bInitBadgeProvider) {
            insertBadgeProvider(var0);
         }

         ContentResolver var1 = var0.getContentResolver();
         int var2 = getCountofAccounts(var0);
         ContentValues var3 = new ContentValues();
         var3.put("package", "com.android.email");
         var3.put("class", "com.android.email.activity.Welcome");
         Integer var4 = Integer.valueOf(var2);
         var3.put("badgecount", var4);
         String var5 = "package=\'com.android.email\' AND class=\'com.android.email.activity.Welcome\'";

         try {
            Uri var6 = Uri.parse("content://com.sec.badge/apps");
            var1.update(var6, var3, var5, (String[])null);
            String var8 = "[updateBadgeProvider] - cnt : " + var2;
            Email.logd("BadgeManager", var8);
         } catch (Exception var11) {
            int var10 = Log.e("BadgeManager", "updateBadgeProvider ", var11);
         }
      }
   }
}
