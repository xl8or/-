package com.facebook.katana.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import com.facebook.katana.provider.FacebookDatabaseHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatHistoryProvider extends ContentProvider {

   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   private static final String AUTHORITY = "com.facebook.katana.provider.ChatHistoryProvider";
   private static final String BASE_CONTENT_URI = "content://com.facebook.katana.provider.ChatHistoryProvider/";
   private static final int CHAT_CONVERSATIONS = 10;
   private static final HashMap<String, String> CHAT_CONVERSATIONS_PROJECTION_MAP;
   private static final String CHAT_CONVERSATIONS_TABLE = "chatconversations";
   private static final HashMap<String, String> CHAT_HISTORY_PROJECTION_MAP;
   private static final int CHAT_MESSAGES = 1;
   private static final String CHAT_MESSAGES_TABLE = "chatmessages";
   public static final Uri CONVERSATIONS_CONTENT_URI;
   public static final Uri HISTORY_CONTENT_URI;
   private static final String SQL_CHAT_CONVERSATIONS = "CREATE TABLE chatconversations (_id INTEGER PRIMARY KEY,friend_uid INT,unread_count INT,unread_message TEXT);";
   private static final String SQL_CHAT_HISTORY = "CREATE TABLE chatmessages (_id INTEGER PRIMARY KEY,friend_uid INT,sent INT,timestamp INT,body TEXT);";
   private static final UriMatcher URL_MATCHER;
   private FacebookDatabaseHelper mDbHelper;


   static {
      byte var0;
      if(!ChatHistoryProvider.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
      HISTORY_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.ChatHistoryProvider/chatmessages");
      CONVERSATIONS_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.ChatHistoryProvider/chatconversations");
      URL_MATCHER = new UriMatcher(-1);
      URL_MATCHER.addURI("com.facebook.katana.provider.ChatHistoryProvider", "chatmessages", 1);
      URL_MATCHER.addURI("com.facebook.katana.provider.ChatHistoryProvider", "chatconversations", 10);
      CHAT_HISTORY_PROJECTION_MAP = new HashMap();
      Object var1 = CHAT_HISTORY_PROJECTION_MAP.put("_id", "_id");
      Object var2 = CHAT_HISTORY_PROJECTION_MAP.put("friend_uid", "friend_uid");
      Object var3 = CHAT_HISTORY_PROJECTION_MAP.put("sent", "sent");
      Object var4 = CHAT_HISTORY_PROJECTION_MAP.put("timestamp", "timestamp");
      Object var5 = CHAT_HISTORY_PROJECTION_MAP.put("body", "body");
      CHAT_CONVERSATIONS_PROJECTION_MAP = new HashMap();
      Object var6 = CHAT_CONVERSATIONS_PROJECTION_MAP.put("_id", "_id");
      Object var7 = CHAT_CONVERSATIONS_PROJECTION_MAP.put("friend_uid", "friend_uid");
      Object var8 = CHAT_CONVERSATIONS_PROJECTION_MAP.put("unread_count", "unread_count");
      Object var9 = CHAT_CONVERSATIONS_PROJECTION_MAP.put("unread_message", "unread_message");
   }

   public ChatHistoryProvider() {}

   public static String[] getTableNames() {
      String[] var0 = new String[]{"chatmessages", "chatconversations"};
      return var0;
   }

   public static String[] getTableSQLs() {
      String[] var0 = new String[]{"CREATE TABLE chatmessages (_id INTEGER PRIMARY KEY,friend_uid INT,sent INT,timestamp INT,body TEXT);", "CREATE TABLE chatconversations (_id INTEGER PRIMARY KEY,friend_uid INT,unread_count INT,unread_message TEXT);"};
      return var0;
   }

   public int bulkInsert(Uri var1, ContentValues[] var2) {
      return this.insertHelper(var1, var2, (List)null);
   }

   public int delete(Uri var1, String var2, String[] var3) {
      SQLiteDatabase var4 = this.mDbHelper.getWritableDatabase();
      int var6;
      switch(URL_MATCHER.match(var1)) {
      case 1:
         var6 = var4.delete("chatmessages", var2, var3);
         break;
      case 10:
         var6 = var4.delete("chatconversations", var2, var3);
         break;
      default:
         String var5 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var5);
      }

      if(var6 > 0) {
         this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
      }

      return var6;
   }

   public String getType(Uri var1) {
      String var3;
      switch(URL_MATCHER.match(var1)) {
      case 1:
         var3 = "vnd.android.cursor.dir/vnd.facebook.katana.provider.chatmessages";
         break;
      case 10:
         var3 = "vnd.android.cursor.dir/vnd.facebook.katana.provider.chatconversations";
         break;
      default:
         String var2 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var2);
      }

      return var3;
   }

   public Uri insert(Uri var1, ContentValues var2) {
      ContentValues[] var3 = new ContentValues[]{var2};
      ArrayList var4 = new ArrayList();
      int var5 = this.insertHelper(var1, var3, var4);
      if(!$assertionsDisabled && var5 != 1) {
         throw new AssertionError();
      } else if(!$assertionsDisabled && var4.size() != 1) {
         throw new AssertionError();
      } else {
         return (Uri)var4.get(0);
      }
   }

   protected int insertHelper(Uri var1, ContentValues[] var2, List<Uri> var3) {
      int var4;
      var4 = 0;
      SQLiteDatabase var5 = this.mDbHelper.getWritableDatabase();
      int var7;
      label39:
      switch(URL_MATCHER.match(var1)) {
      case 1:
         var7 = 0;

         while(true) {
            int var8 = var2.length;
            if(var7 >= var8) {
               break label39;
            }

            ContentValues var9 = var2[var7];
            long var10 = var5.insert("chatmessages", "friend_uid", var9);
            if(var10 > 0L) {
               ++var4;
               if(var3 != null) {
                  Uri var12 = HISTORY_CONTENT_URI;
                  String var13 = Long.valueOf(var10).toString();
                  Uri var14 = Uri.withAppendedPath(var12, var13);
                  var3.add(var14);
               }
            }

            ++var7;
         }
      case 10:
         var7 = 0;

         while(true) {
            int var16 = var2.length;
            if(var7 >= var16) {
               break label39;
            }

            ContentValues var17 = var2[var7];
            long var18 = var5.insert("chatconversations", "friend_uid", var17);
            if(var18 > 0L) {
               ++var4;
               if(var3 != null) {
                  Uri var20 = CONVERSATIONS_CONTENT_URI;
                  String var21 = Long.valueOf(var18).toString();
                  Uri var22 = Uri.withAppendedPath(var20, var21);
                  var3.add(var22);
               }
            }

            ++var7;
         }
      default:
         String var6 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var6);
      }

      if(var4 > 0) {
         this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
         return var4;
      } else {
         String var24 = "Failed to insert rows into " + var1;
         throw new SQLException(var24);
      }
   }

   public boolean onCreate() {
      FacebookDatabaseHelper var1 = FacebookDatabaseHelper.getDatabaseHelper(this.getContext());
      this.mDbHelper = var1;
      boolean var2;
      if(this.mDbHelper.getReadableDatabase() != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public Cursor query(Uri var1, String[] var2, String var3, String[] var4, String var5) {
      SQLiteQueryBuilder var6 = new SQLiteQueryBuilder();
      String var9;
      switch(URL_MATCHER.match(var1)) {
      case 1:
         var6.setTables("chatmessages");
         HashMap var8 = CHAT_HISTORY_PROJECTION_MAP;
         var6.setProjectionMap(var8);
         if(TextUtils.isEmpty(var5)) {
            var9 = "timestamp ASC";
         } else {
            var9 = var5;
         }
         break;
      case 10:
         var6.setTables("chatconversations");
         HashMap var17 = CHAT_CONVERSATIONS_PROJECTION_MAP;
         var6.setProjectionMap(var17);
         if(TextUtils.isEmpty(var5)) {
            var9 = "_id ASC";
         } else {
            var9 = var5;
         }
         break;
      default:
         String var7 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var7);
      }

      SQLiteDatabase var10 = this.mDbHelper.getReadableDatabase();
      Object var14 = null;
      Cursor var15 = var6.query(var10, var2, var3, var4, (String)null, (String)var14, var9);
      ContentResolver var16 = this.getContext().getContentResolver();
      var15.setNotificationUri(var16, var1);
      return var15;
   }

   public int update(Uri var1, ContentValues var2, String var3, String[] var4) {
      SQLiteDatabase var5 = this.mDbHelper.getWritableDatabase();
      int var7;
      switch(URL_MATCHER.match(var1)) {
      case 1:
         var7 = var5.update("chatmessages", var2, var3, var4);
         break;
      case 10:
         var7 = var5.update("chatconversations", var2, var3, var4);
         break;
      default:
         String var6 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var6);
      }

      this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
      return var7;
   }

   public static final class MessageColumns implements BaseColumns {

      public static final String BODY = "body";
      public static final String DEFAULT_SORT_ORDER = "timestamp ASC";
      public static final String FRIEND_UID = "friend_uid";
      public static final String SENT = "sent";
      public static final String TIME_STAMP = "timestamp";


      public MessageColumns() {}
   }

   public static final class ConversationColumns implements BaseColumns {

      public static final String DEFAULT_SORT_ORDER = "_id ASC";
      public static final String FRIEND_UID = "friend_uid";
      public static final String UNREAD_COUNT = "unread_count";
      public static final String UNREAD_MESSAGE = "unread_message";


      public ConversationColumns() {}
   }
}
