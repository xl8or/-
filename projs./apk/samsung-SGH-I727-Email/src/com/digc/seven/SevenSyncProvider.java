package com.digc.seven;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.android.email.provider.EmailContent;
import com.seven.Z7.provider.Z7Content;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class SevenSyncProvider {

   private static final Uri ACCOUNT_URI = Z7Content.Accounts.CONTENT_URI;
   private static final String[] ATTACHE_PROJECTION;
   protected static final String BODY_DATABASE_NAME = "EmailProviderBody.db";
   private static final String[] COMPOSE_PROJECTION;
   protected static final String DATABASE_NAME = "EmailProvider.db";
   protected static final String ENGINE_VERSION_FILE = "z7_engine_ver.xml";
   private static final String[] FOLDER_PROJECTION;
   private static final String[] PROJECTION;
   public static final String[] RECEIVED_PROJECTION;
   private static final String TAG = "SevenSyncProvider";


   static {
      String[] var0 = new String[]{"account_id"};
      PROJECTION = var0;
      String[] var1 = new String[]{"name", "_id", "folder_id"};
      FOLDER_PROJECTION = var1;
      String[] var2 = new String[]{"bb", "bcc", "cc", "reply_to", "body", "html_body", "_from", "account_id", "missing_body", "missing_html_body", "unk_encoding", "_to", "folder_id", "is_unread", "delivery_time", "subject", "has_attachments"};
      RECEIVED_PROJECTION = var2;
      String[] var3 = new String[]{"_id", "folder_id", "subject", "_to", "body", "delivery_time", "cc", "bcc", "org_action", "org_id", "has_attachments", "importance"};
      COMPOSE_PROJECTION = var3;
      String[] var4 = new String[]{"_id", "file_name", "mime_type", "email_id", "size", "est_size", "uri", "status", "pos"};
      ATTACHE_PROJECTION = var4;
   }

   public SevenSyncProvider() {}

   public static boolean checkExistAccount(Context param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   public static boolean checkRunningTask(Activity var0) {
      List var1 = ((ActivityManager)var0.getSystemService("activity")).getRunningTasks(10);
      int var2 = 0;
      int var3 = 0;
      int var4 = 0;

      while(true) {
         int var5 = var1.size();
         if(var4 >= var5) {
            break;
         }

         RunningTaskInfo var6 = (RunningTaskInfo)var1.get(var4);
         String var7 = var6.baseActivity.getPackageName();
         String var8 = var0.getPackageName();
         if(var7.equals(var8)) {
            StringBuilder var9 = (new StringBuilder()).append("numActivities = ");
            int var10 = var6.numActivities;
            String var11 = var9.append(var10).toString();
            int var12 = Log.d("SevenSyncProvider", var11);
            StringBuilder var13 = (new StringBuilder()).append("numRunning = ");
            int var14 = var6.numRunning;
            String var15 = var13.append(var14).toString();
            int var16 = Log.d("SevenSyncProvider", var15);
            var2 = var6.numActivities;
            var3 = var6.numRunning;
            break;
         }

         ++var4;
      }

      boolean var18;
      if(var2 == var3) {
         int var17 = Log.w("SevenSyncProvider", "Seven Engine need update");
         var18 = true;
      } else {
         var18 = false;
      }

      return var18;
   }

   public static boolean checkSevenApkVer(Activity param0) {
      // $FF: Couldn't be decompiled
   }

   public static int getAccountCount(Context param0) {
      // $FF: Couldn't be decompiled
   }

   public static int getAccountID(Context param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   public static HashMap<Integer, Hashtable<String, Object>> getAttacheUri(Context param0, int param1) {
      // $FF: Couldn't be decompiled
   }

   public static Cursor getBasicAccountAndMessageKey(Context var0, int var1) {
      int var2 = -1;
      ContentResolver var3 = var0.getContentResolver();
      Uri var4 = EmailContent.MessageCB.CONTENT_URI;
      String[] var5 = new String[]{"messageKey"};
      String var6 = "sevenMessageKey = " + var1;
      Object var7 = null;
      Cursor var8 = var3.query(var4, var5, var6, (String[])null, (String)var7);
      if(var8 != null && var8.getCount() > 0 && var8.moveToFirst()) {
         var2 = var8.getInt(0);
      }

      if(var2 != -1) {
         if(var8 != null) {
            var8.close();
         }

         ContentResolver var9 = var0.getContentResolver();
         Uri var10 = EmailContent.Message.CONTENT_URI;
         String[] var11 = new String[]{"accountKey", "_id"};
         String var12 = "_id = " + var2;
         Object var13 = null;
         var8 = var9.query(var10, var11, var12, (String[])null, (String)var13);
      }

      return var8;
   }

   public static Cursor getComposeCursor(Context var0, Uri var1) {
      ContentResolver var2 = var0.getContentResolver();
      String[] var3 = COMPOSE_PROJECTION;
      Object var5 = null;
      Object var6 = null;
      return var2.query(var1, var3, (String)null, (String[])var5, (String)var6);
   }

   public static Uri getComposeUri(Context var0, ContentValues var1) {
      ContentResolver var2 = var0.getContentResolver();
      Uri var3 = Z7Content.Emails.CONTENT_URI;
      return var2.insert(var3, var1);
   }

   public static Hashtable<String, Integer> getFolders(Context param0, int param1) {
      // $FF: Couldn't be decompiled
   }

   public static String getParentsFolderName(Context param0, int param1) {
      // $FF: Couldn't be decompiled
   }

   public static int getSevenFolderIdFromMessageId(Context param0, long param1) {
      // $FF: Couldn't be decompiled
   }

   public static int getSevenVersionInfo(Context var0) {
      int var1 = -1;

      int var8;
      label34: {
         int var7;
         try {
            InputStream var2 = var0.getResources().getAssets().open("z7_engine_ver.xml");
            XmlPullParserFactory var3 = XmlPullParserFactory.newInstance();
            var3.setNamespaceAware((boolean)1);
            XmlPullParser var4 = var3.newPullParser();
            var4.setInput(var2, "UTF-8");
            int var5 = var4.getEventType();

            String var6;
            for(var6 = ""; var5 != 1; var5 = var4.next()) {
               if(var5 == 4) {
                  var6 = var4.getText();
               }
            }

            if(var6 == null || var6.equals("")) {
               break label34;
            }

            var7 = Integer.parseInt(var6);
         } catch (Exception var10) {
            var8 = -1;
            return var8;
         }

         var1 = var7;
      }

      var8 = var1;
      return var8;
   }

   public static String replaceNull(String var0) {
      String var1;
      if(var0 == null) {
         var1 = "";
      } else {
         var1 = var0;
      }

      return var1;
   }

   public static int setBasicMessageCBupdate(Context var0, long var1, long var3, long var5) {
      ContentValues var7 = new ContentValues();
      Long var8 = Long.valueOf(var1);
      var7.put("sevenAccountKey", var8);
      Long var9 = Long.valueOf(var5);
      var7.put("sevenMessageKey", var9);
      Integer var10 = Integer.valueOf(1);
      var7.put("typeMsg", var10);
      ContentResolver var11 = var0.getContentResolver();
      Uri var12 = EmailContent.MessageCB.CONTENT_URI;
      String var13 = "messageKey" + "=?";
      String[] var14 = new String[1];
      String var15 = Long.toString(var3);
      var14[0] = var15;
      return var11.update(var12, var7, var13, var14);
   }
}
