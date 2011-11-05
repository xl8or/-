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
import java.util.HashMap;
import java.util.Map;

public class MailboxProvider extends ContentProvider {

   private static final String AUTHORITY = "com.facebook.katana.provider.MailboxProvider";
   private static final String CONTENT_SCHEME = "content://";
   public static final int FOLDER_INBOX = 0;
   public static final int FOLDER_OUTBOX = 1;
   private static final Map<Integer, String> FOLDER_PATH_MAP = new HashMap();
   public static final int FOLDER_UPDATES = 4;
   public static final Uri INBOX_MESSAGES_CONTENT_URI;
   public static final Uri INBOX_MESSAGES_TID_CONTENT_URI;
   private static final String INBOX_PATH = "/inbox";
   public static final Uri INBOX_THREADS_CONTENT_URI;
   public static final Uri INBOX_THREADS_TID_CONTENT_URI;
   private static final int MAILBOX_MESSAGES = 10;
   private static final int MAILBOX_MESSAGES_INBOX = 12;
   private static final int MAILBOX_MESSAGES_INBOX_TID = 15;
   private static final int MAILBOX_MESSAGES_OUTBOX = 13;
   private static final int MAILBOX_MESSAGES_OUTBOX_TID = 16;
   public static final HashMap<String, String> MAILBOX_MESSAGES_PROJECTION_MAP;
   private static final int MAILBOX_MESSAGES_UPDATES = 14;
   private static final int MAILBOX_MESSAGES_UPDATES_TID = 17;
   private static final int MAILBOX_MESSAGE_ID = 11;
   private static final int MAILBOX_PROFILES = 20;
   public static final HashMap<String, String> MAILBOX_PROFILES_PROJECTION_MAP;
   private static final int MAILBOX_PROFILES_PRUNE = 23;
   private static final int MAILBOX_PROFILE_ID = 22;
   private static final int MAILBOX_PROFILE__ID = 21;
   private static final int MAILBOX_THREADS = 1;
   private static final int MAILBOX_THREADS_INBOX = 3;
   private static final int MAILBOX_THREADS_OUTBOX = 4;
   public static final HashMap<String, String> MAILBOX_THREADS_PROJECTION_MAP;
   private static final int MAILBOX_THREADS_UPDATES = 5;
   private static final int MAILBOX_THREAD_ID = 2;
   private static final int MAILBOX_THREAD_INBOX_TID = 6;
   private static final int MAILBOX_THREAD_OUTBOX_TID = 7;
   private static final int MAILBOX_THREAD_UPDATES_TID = 8;
   private static final String MESSAGES_BASE_URI = "content://com.facebook.katana.provider.MailboxProvider/mailbox_messages";
   public static final Uri MESSAGES_CONTENT_URI;
   public static final Uri OUTBOX_MESSAGES_CONTENT_URI;
   public static final Uri OUTBOX_MESSAGES_TID_CONTENT_URI;
   private static final String OUTBOX_PATH = "/outbox";
   public static final Uri OUTBOX_THREADS_CONTENT_URI;
   public static final Uri OUTBOX_THREADS_TID_CONTENT_URI;
   private static final String PROFILES_BASE_URI = "content://com.facebook.katana.provider.MailboxProvider/mailbox_profiles";
   public static final Uri PROFILES_CONTENT_URI;
   public static final Uri PROFILES_ID_CONTENT_URI;
   public static final Uri PROFILES_PRUNE_CONTENT_URI;
   private static final String SQL_MAILBOX_MESSAGES = "CREATE TABLE mailbox_messages (_id INTEGER PRIMARY KEY,folder INT,tid INT,mid INT,author_id INT,sent INT,body TEXT);";
   private static final String SQL_MAILBOX_MESSAGES_DISPLAY = "CREATE VIEW mailbox_messages_display AS SELECT mailbox_messages._id AS _id, mailbox_messages.mid AS mid, mailbox_messages.folder AS folder, mailbox_messages.tid AS tid, mailbox_messages.sent AS sent, mailbox_messages.body AS body, author.id as author_id, author.display_name as author_name, author.profile_image_url AS author_image_url, author.type AS author_type, object.id as object_id, object.display_name as object_name, object.profile_image_url AS object_image_url, object.type AS object_type FROM mailbox_messages LEFT OUTER JOIN mailbox_threads ON mailbox_messages.tid = mailbox_threads.tid AND mailbox_messages.folder = mailbox_threads.folder LEFT OUTER JOIN mailbox_profiles AS object ON mailbox_threads.object_id = object.id LEFT OUTER JOIN mailbox_profiles AS author ON mailbox_messages.author_id = author.id;";
   private static final String SQL_MAILBOX_PROFILES = "CREATE TABLE mailbox_profiles (_id INTEGER PRIMARY KEY,id INT,display_name TEXT,profile_image_url TEXT,type INT);";
   private static final String SQL_MAILBOX_THREADS = "CREATE TABLE mailbox_threads (_id INTEGER PRIMARY KEY,folder INT,tid INT,participants TEXT,subject TEXT,snippet TEXT,other_party INT,msg_count INT,unread_count INT,last_update INT,object_id INT);";
   private static final String SQL_PROFILES_ID_INDEX = "CREATE INDEX mailbox_profiles_id ON mailbox_profiles(id);";
   private static final String SQL_THREADS_TID_INDEX = "CREATE INDEX mailbox_threads_tid ON mailbox_threads(tid);";
   private static final String TABLE_MAILBOX_MESSAGES = "mailbox_messages";
   private static final String TABLE_MAILBOX_MESSAGES_DISPLAY = "mailbox_messages_display";
   private static final String TABLE_MAILBOX_PROFILES = "mailbox_profiles";
   private static final String TABLE_MAILBOX_THREADS = "mailbox_threads";
   private static final String TABLE_MAILBOX_THREADS_PROFILES = "mailbox_threads LEFT OUTER JOIN mailbox_profiles ON mailbox_threads.other_party=mailbox_profiles.id";
   private static final String THREADS_BASE_URI = "content://com.facebook.katana.provider.MailboxProvider/mailbox_threads";
   public static final Uri THREADS_CONTENT_URI;
   public static final Uri UPDATES_MESSAGES_CONTENT_URI;
   public static final Uri UPDATES_MESSAGES_TID_CONTENT_URI;
   private static final String UPDATES_PATH = "/updates";
   public static final Uri UPDATES_THREADS_CONTENT_URI;
   public static final Uri UPDATES_THREADS_TID_CONTENT_URI;
   private static final UriMatcher URL_MATCHER;
   private FacebookDatabaseHelper mDbHelper;


   static {
      Map var0 = FOLDER_PATH_MAP;
      Integer var1 = Integer.valueOf(0);
      var0.put(var1, "/inbox");
      Map var3 = FOLDER_PATH_MAP;
      Integer var4 = Integer.valueOf(1);
      var3.put(var4, "/outbox");
      Map var6 = FOLDER_PATH_MAP;
      Integer var7 = Integer.valueOf(4);
      var6.put(var7, "/updates");
      THREADS_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_threads");
      INBOX_THREADS_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_threads/inbox");
      OUTBOX_THREADS_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_threads/outbox");
      UPDATES_THREADS_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_threads/updates");
      INBOX_THREADS_TID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_threads/inbox/tid");
      OUTBOX_THREADS_TID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_threads/outbox/tid");
      UPDATES_THREADS_TID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_threads/updates/tid");
      MESSAGES_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_messages");
      INBOX_MESSAGES_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_messages/inbox");
      OUTBOX_MESSAGES_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_messages/outbox");
      UPDATES_MESSAGES_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_messages/updates");
      INBOX_MESSAGES_TID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_messages/inbox/tid");
      OUTBOX_MESSAGES_TID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_messages/outbox/tid");
      UPDATES_MESSAGES_TID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_messages/updates/tid");
      PROFILES_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_profiles");
      PROFILES_ID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_profiles/id");
      PROFILES_PRUNE_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_profiles/prune");
      URL_MATCHER = new UriMatcher(-1);
      URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_threads", 1);
      URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_threads/#", 2);
      URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_threads/inbox", 3);
      URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_threads/outbox", 4);
      URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_threads/updates", 5);
      URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_threads/inbox/tid/#", 6);
      URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_threads/outbox/tid/#", 7);
      URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_threads/updates/tid/#", 8);
      URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_messages", 10);
      URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_messages/#", 11);
      URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_messages/inbox", 12);
      URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_messages/outbox", 13);
      URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_messages/updates", 14);
      URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_messages/inbox/tid/#", 15);
      URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_messages/outbox/tid/#", 16);
      URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_messages/updates/tid/#", 17);
      URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_profiles", 20);
      URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_profiles/#", 21);
      URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_profiles/id/#", 22);
      URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_profiles/prune", 23);
      MAILBOX_THREADS_PROJECTION_MAP = new HashMap();
      Object var9 = MAILBOX_THREADS_PROJECTION_MAP.put("_id", "_id");
      Object var10 = MAILBOX_THREADS_PROJECTION_MAP.put("mailbox_threads._id", "_id");
      Object var11 = MAILBOX_THREADS_PROJECTION_MAP.put("folder", "folder");
      Object var12 = MAILBOX_THREADS_PROJECTION_MAP.put("tid", "tid");
      Object var13 = MAILBOX_THREADS_PROJECTION_MAP.put("participants", "participants");
      Object var14 = MAILBOX_THREADS_PROJECTION_MAP.put("subject", "subject");
      Object var15 = MAILBOX_THREADS_PROJECTION_MAP.put("snippet", "snippet");
      Object var16 = MAILBOX_THREADS_PROJECTION_MAP.put("other_party", "other_party");
      Object var17 = MAILBOX_THREADS_PROJECTION_MAP.put("msg_count", "msg_count");
      Object var18 = MAILBOX_THREADS_PROJECTION_MAP.put("unread_count", "unread_count");
      Object var19 = MAILBOX_THREADS_PROJECTION_MAP.put("last_update", "last_update");
      MAILBOX_MESSAGES_PROJECTION_MAP = new HashMap();
      Object var20 = MAILBOX_MESSAGES_PROJECTION_MAP.put("_id", "_id");
      Object var21 = MAILBOX_MESSAGES_PROJECTION_MAP.put("mailbox_messages._id", "_id");
      Object var22 = MAILBOX_MESSAGES_PROJECTION_MAP.put("folder", "folder");
      Object var23 = MAILBOX_MESSAGES_PROJECTION_MAP.put("tid", "tid");
      Object var24 = MAILBOX_MESSAGES_PROJECTION_MAP.put("mid", "mid");
      Object var25 = MAILBOX_MESSAGES_PROJECTION_MAP.put("author_id", "author_id");
      Object var26 = MAILBOX_MESSAGES_PROJECTION_MAP.put("sent", "sent");
      Object var27 = MAILBOX_MESSAGES_PROJECTION_MAP.put("body", "body");
      MAILBOX_PROFILES_PROJECTION_MAP = new HashMap();
      Object var28 = MAILBOX_PROFILES_PROJECTION_MAP.put("_id", "_id");
      Object var29 = MAILBOX_PROFILES_PROJECTION_MAP.put("id", "id");
      Object var30 = MAILBOX_PROFILES_PROJECTION_MAP.put("display_name", "display_name");
      Object var31 = MAILBOX_PROFILES_PROJECTION_MAP.put("profile_image_url", "profile_image_url");
   }

   public MailboxProvider() {}

   public static Uri getMessagesFolderUri(int var0) {
      return Uri.parse(getMessagesFolderUriString(var0));
   }

   private static String getMessagesFolderUriString(int var0) {
      StringBuilder var1 = (new StringBuilder()).append("content://com.facebook.katana.provider.MailboxProvider/mailbox_messages");
      Map var2 = FOLDER_PATH_MAP;
      Integer var3 = Integer.valueOf(var0);
      String var4 = (String)var2.get(var3);
      return var1.append(var4).toString();
   }

   public static Uri getMessagesTidFolderUri(int var0) {
      StringBuilder var1 = new StringBuilder();
      String var2 = getMessagesFolderUriString(var0);
      return Uri.parse(var1.append(var2).append("/tid").toString());
   }

   static String[] getTableNames() {
      String[] var0 = new String[]{"mailbox_threads", "mailbox_messages", "mailbox_profiles"};
      return var0;
   }

   static String[] getTableSQLs() {
      String[] var0 = new String[]{"CREATE TABLE mailbox_threads (_id INTEGER PRIMARY KEY,folder INT,tid INT,participants TEXT,subject TEXT,snippet TEXT,other_party INT,msg_count INT,unread_count INT,last_update INT,object_id INT);", "CREATE INDEX mailbox_threads_tid ON mailbox_threads(tid);", "CREATE TABLE mailbox_messages (_id INTEGER PRIMARY KEY,folder INT,tid INT,mid INT,author_id INT,sent INT,body TEXT);", "CREATE TABLE mailbox_profiles (_id INTEGER PRIMARY KEY,id INT,display_name TEXT,profile_image_url TEXT,type INT);", "CREATE INDEX mailbox_profiles_id ON mailbox_profiles(id);", "CREATE VIEW mailbox_messages_display AS SELECT mailbox_messages._id AS _id, mailbox_messages.mid AS mid, mailbox_messages.folder AS folder, mailbox_messages.tid AS tid, mailbox_messages.sent AS sent, mailbox_messages.body AS body, author.id as author_id, author.display_name as author_name, author.profile_image_url AS author_image_url, author.type AS author_type, object.id as object_id, object.display_name as object_name, object.profile_image_url AS object_image_url, object.type AS object_type FROM mailbox_messages LEFT OUTER JOIN mailbox_threads ON mailbox_messages.tid = mailbox_threads.tid AND mailbox_messages.folder = mailbox_threads.folder LEFT OUTER JOIN mailbox_profiles AS object ON mailbox_threads.object_id = object.id LEFT OUTER JOIN mailbox_profiles AS author ON mailbox_messages.author_id = author.id;"};
      return var0;
   }

   private static String getThreadsFolderString(int var0) {
      StringBuilder var1 = (new StringBuilder()).append("content://com.facebook.katana.provider.MailboxProvider/mailbox_threads");
      Map var2 = FOLDER_PATH_MAP;
      Integer var3 = Integer.valueOf(var0);
      String var4 = (String)var2.get(var3);
      return var1.append(var4).toString();
   }

   public static Uri getThreadsFolderUri(int var0) {
      return Uri.parse(getThreadsFolderString(var0));
   }

   public static Uri getThreadsTidFolderUri(int var0) {
      StringBuilder var1 = new StringBuilder();
      String var2 = getThreadsFolderString(var0);
      return Uri.parse(var1.append(var2).append("/tid").toString());
   }

   static String[] getViewNames() {
      String[] var0 = new String[]{"mailbox_messages_display"};
      return var0;
   }

   private int insertMessageValues(Uri var1, ContentValues[] var2) throws SQLException {
      int var3 = 0;
      SQLiteDatabase var4 = this.mDbHelper.getWritableDatabase();
      int var5 = 0;

      while(true) {
         int var6 = var2.length;
         if(var5 >= var6) {
            if(var3 > 0) {
               this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
               return var3;
            }

            String var8 = "Failed to insert rows into " + var1;
            throw new SQLException(var8);
         }

         ContentValues var7 = var2[var5];
         if(var4.insert("mailbox_messages", "tid", var7) > 0L) {
            ++var3;
         }

         ++var5;
      }
   }

   private Uri insertMessageValues(Uri var1, ContentValues var2) throws SQLException {
      long var3 = this.mDbHelper.getWritableDatabase().insert("mailbox_messages", "tid", var2);
      if(var3 > 0L) {
         Uri var5 = MESSAGES_CONTENT_URI;
         String var6 = Long.valueOf(var3).toString();
         Uri var7 = Uri.withAppendedPath(var5, var6);
         this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
         return var7;
      } else {
         String var8 = "Failed to insert row into " + var1;
         throw new SQLException(var8);
      }
   }

   private int insertProfileValues(Uri var1, ContentValues[] var2) throws SQLException {
      int var3 = 0;
      SQLiteDatabase var4 = this.mDbHelper.getWritableDatabase();
      int var5 = 0;

      while(true) {
         int var6 = var2.length;
         if(var5 >= var6) {
            if(var3 > 0) {
               this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
               return var3;
            }

            String var8 = "Failed to insert rows into " + var1;
            throw new SQLException(var8);
         }

         ContentValues var7 = var2[var5];
         if(var4.insert("mailbox_profiles", "tid", var7) > 0L) {
            ++var3;
         }

         ++var5;
      }
   }

   private Uri insertProfileValues(Uri var1, ContentValues var2) throws SQLException {
      long var3 = this.mDbHelper.getWritableDatabase().insert("mailbox_profiles", "tid", var2);
      if(var3 > 0L) {
         Uri var5 = PROFILES_CONTENT_URI;
         String var6 = Long.valueOf(var3).toString();
         Uri var7 = Uri.withAppendedPath(var5, var6);
         this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
         return var7;
      } else {
         String var8 = "Failed to insert row into " + var1;
         throw new SQLException(var8);
      }
   }

   private int insertThreadValues(Uri var1, ContentValues[] var2) throws SQLException {
      int var3 = 0;
      SQLiteDatabase var4 = this.mDbHelper.getWritableDatabase();
      int var5 = 0;

      while(true) {
         int var6 = var2.length;
         if(var5 >= var6) {
            if(var3 > 0) {
               this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
               return var3;
            }

            String var8 = "Failed to insert rows into " + var1;
            throw new SQLException(var8);
         }

         ContentValues var7 = var2[var5];
         if(var4.insert("mailbox_threads", "tid", var7) > 0L) {
            ++var3;
         }

         ++var5;
      }
   }

   private Uri insertThreadValues(Uri var1, ContentValues var2) throws SQLException {
      long var3 = this.mDbHelper.getWritableDatabase().insert("mailbox_threads", "tid", var2);
      if(var3 > 0L) {
         Uri var5 = THREADS_CONTENT_URI;
         String var6 = Long.valueOf(var3).toString();
         Uri var7 = Uri.withAppendedPath(var5, var6);
         this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
         return var7;
      } else {
         String var8 = "Failed to insert row into " + var1;
         throw new SQLException(var8);
      }
   }

   private static String setMessagesOrderBy(String var0) {
      String var1;
      if(TextUtils.isEmpty(var0)) {
         var1 = "mid ASC";
      } else {
         var1 = var0;
      }

      return var1;
   }

   private static String setProfilesOrderBy(String var0) {
      String var1;
      if(TextUtils.isEmpty(var0)) {
         var1 = "display_name DESC";
      } else {
         var1 = var0;
      }

      return var1;
   }

   private static String setThreadsOrderBy(String var0) {
      String var1;
      if(TextUtils.isEmpty(var0)) {
         var1 = "last_update DESC";
      } else {
         var1 = var0;
      }

      return var1;
   }

   public int bulkInsert(Uri var1, ContentValues[] var2) {
      int var4;
      byte var5;
      switch(URL_MATCHER.match(var1)) {
      case 1:
         var4 = this.insertThreadValues(var1, var2);
         break;
      case 2:
      case 6:
      case 7:
      case 8:
      case 9:
      case 11:
      case 15:
      case 16:
      case 17:
      case 18:
      case 19:
      default:
         String var3 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var3);
      case 3:
         int var29 = 0;

         while(true) {
            int var6 = var2.length;
            if(var29 >= var6) {
               var4 = this.insertThreadValues(var1, var2);
               return var4;
            }

            ContentValues var7 = var2[var29];
            Integer var8 = Integer.valueOf(0);
            var7.put("folder", var8);
            ++var29;
         }
      case 4:
         var5 = 0;

         while(true) {
            int var9 = var2.length;
            if(var5 >= var9) {
               var4 = this.insertThreadValues(var1, var2);
               return var4;
            }

            ContentValues var10 = var2[var5];
            Integer var11 = Integer.valueOf(1);
            var10.put("folder", var11);
            int var12 = var5 + 1;
         }
      case 5:
         var5 = 0;

         while(true) {
            int var13 = var2.length;
            if(var5 >= var13) {
               var4 = this.insertThreadValues(var1, var2);
               return var4;
            }

            ContentValues var14 = var2[var5];
            Integer var15 = Integer.valueOf(4);
            var14.put("folder", var15);
            int var16 = var5 + 1;
         }
      case 10:
         var4 = this.insertMessageValues(var1, var2);
         break;
      case 12:
         var5 = 0;

         while(true) {
            int var17 = var2.length;
            if(var5 >= var17) {
               var4 = this.insertMessageValues(var1, var2);
               return var4;
            }

            ContentValues var18 = var2[var5];
            Integer var19 = Integer.valueOf(0);
            var18.put("folder", var19);
            int var20 = var5 + 1;
         }
      case 13:
         var5 = 0;

         while(true) {
            int var21 = var2.length;
            if(var5 >= var21) {
               var4 = this.insertMessageValues(var1, var2);
               return var4;
            }

            ContentValues var22 = var2[var5];
            Integer var23 = Integer.valueOf(1);
            var22.put("folder", var23);
            int var24 = var5 + 1;
         }
      case 14:
         var5 = 0;

         while(true) {
            int var25 = var2.length;
            if(var5 >= var25) {
               var4 = this.insertMessageValues(var1, var2);
               return var4;
            }

            ContentValues var26 = var2[var5];
            Integer var27 = Integer.valueOf(4);
            var26.put("folder", var27);
            int var28 = var5 + 1;
         }
      case 20:
         var4 = this.insertProfileValues(var1, var2);
      }

      return var4;
   }

   public int delete(Uri var1, String var2, String[] var3) {
      SQLiteDatabase var4 = this.mDbHelper.getWritableDatabase();
      int var6;
      String var9;
      String var11;
      switch(URL_MATCHER.match(var1)) {
      case 1:
         var6 = var4.delete("mailbox_threads", var2, var3);
         break;
      case 2:
         String var7 = (String)var1.getPathSegments().get(1);
         String var8 = "_id=" + var7;
         var6 = var4.delete("mailbox_threads", var8, (String[])null);
         break;
      case 3:
         var9 = "mailbox_threads";
         StringBuilder var10 = (new StringBuilder()).append("folder=0");
         if(!TextUtils.isEmpty(var2)) {
            var11 = " AND (" + var2 + ')';
         } else {
            var11 = "";
         }

         String var12 = var10.append(var11).toString();
         var6 = var4.delete(var9, var12, var3);
         break;
      case 4:
         var9 = "mailbox_threads";
         StringBuilder var13 = (new StringBuilder()).append("folder=1");
         if(!TextUtils.isEmpty(var2)) {
            var11 = " AND (" + var2 + ')';
         } else {
            var11 = "";
         }

         String var14 = var13.append(var11).toString();
         var6 = var4.delete(var9, var14, var3);
         break;
      case 5:
         var9 = "mailbox_threads";
         StringBuilder var15 = (new StringBuilder()).append("folder=4");
         if(!TextUtils.isEmpty(var2)) {
            var11 = " AND (" + var2 + ')';
         } else {
            var11 = "";
         }

         String var16 = var15.append(var11).toString();
         var6 = var4.delete(var9, var16, var3);
         break;
      case 6:
         String var17 = (String)var1.getPathSegments().get(3);
         String var18 = "folder=0 AND tid=" + var17;
         var6 = var4.delete("mailbox_threads", var18, (String[])null);
         break;
      case 7:
         String var19 = (String)var1.getPathSegments().get(3);
         String var20 = "folder=1 AND tid=" + var19;
         var6 = var4.delete("mailbox_threads", var20, (String[])null);
         break;
      case 8:
         String var21 = (String)var1.getPathSegments().get(3);
         String var22 = "folder=4 AND tid=" + var21;
         var6 = var4.delete("mailbox_threads", var22, (String[])null);
         break;
      case 9:
      case 18:
      case 19:
      default:
         String var5 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var5);
      case 10:
         var6 = var4.delete("mailbox_messages", var2, var3);
         break;
      case 11:
         String var23 = (String)var1.getPathSegments().get(1);
         String var24 = "_id=" + var23;
         var6 = var4.delete("mailbox_messages", var24, (String[])null);
         break;
      case 12:
         var9 = "mailbox_messages";
         StringBuilder var25 = (new StringBuilder()).append("folder=0");
         if(!TextUtils.isEmpty(var2)) {
            var11 = " AND (" + var2 + ')';
         } else {
            var11 = "";
         }

         String var26 = var25.append(var11).toString();
         var6 = var4.delete(var9, var26, var3);
         break;
      case 13:
         var9 = "mailbox_messages";
         StringBuilder var27 = (new StringBuilder()).append("folder=1");
         if(!TextUtils.isEmpty(var2)) {
            var11 = " AND (" + var2 + ')';
         } else {
            var11 = "";
         }

         String var28 = var27.append(var11).toString();
         var6 = var4.delete(var9, var28, var3);
         break;
      case 14:
         var9 = "mailbox_messages";
         StringBuilder var29 = (new StringBuilder()).append("folder=4");
         if(!TextUtils.isEmpty(var2)) {
            var11 = " AND (" + var2 + ')';
         } else {
            var11 = "";
         }

         String var30 = var29.append(var11).toString();
         var6 = var4.delete(var9, var30, var3);
         break;
      case 15:
         String var31 = (String)var1.getPathSegments().get(3);
         var9 = "mailbox_messages";
         StringBuilder var32 = (new StringBuilder()).append("tid=").append(var31).append(" AND ").append("folder").append("=").append(0);
         if(!TextUtils.isEmpty(var2)) {
            var11 = " AND (" + var2 + ')';
         } else {
            var11 = "";
         }

         String var33 = var32.append(var11).toString();
         var6 = var4.delete(var9, var33, var3);
         break;
      case 16:
         String var34 = (String)var1.getPathSegments().get(3);
         var9 = "mailbox_messages";
         StringBuilder var35 = (new StringBuilder()).append("tid=").append(var34).append(" AND ").append("folder").append("=").append(1);
         if(!TextUtils.isEmpty(var2)) {
            var11 = " AND (" + var2 + ')';
         } else {
            var11 = "";
         }

         String var36 = var35.append(var11).toString();
         var6 = var4.delete(var9, var36, var3);
         break;
      case 17:
         String var37 = (String)var1.getPathSegments().get(3);
         var9 = "mailbox_messages";
         StringBuilder var38 = (new StringBuilder()).append("tid=").append(var37).append(" AND ").append("folder").append("=").append(4);
         if(!TextUtils.isEmpty(var2)) {
            var11 = " AND (" + var2 + ')';
         } else {
            var11 = "";
         }

         String var39 = var38.append(var11).toString();
         var6 = var4.delete(var9, var39, var3);
         break;
      case 20:
         var6 = var4.delete("mailbox_profiles", var2, var3);
         break;
      case 21:
         String var40 = (String)var1.getPathSegments().get(1);
         String var41 = "_id=" + var40;
         var6 = var4.delete("mailbox_profiles", var41, (String[])null);
         break;
      case 22:
         String var42 = (String)var1.getPathSegments().get(2);
         String var43 = "id=" + var42;
         var6 = var4.delete("mailbox_profiles", var43, (String[])null);
         break;
      case 23:
         var6 = var4.delete("mailbox_profiles", "id NOT IN (SELECT DISTINCT author_id FROM mailbox_messages)", (String[])null);
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
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
         var3 = "vnd.android.cursor.dir/vnd.facebook.mailboxthreads";
         break;
      case 9:
      case 18:
      case 19:
      default:
         String var2 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var2);
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 16:
      case 17:
         var3 = "vnd.android.cursor.dir/vnd.facebook.mailboxmessages";
         break;
      case 20:
      case 21:
      case 22:
         var3 = "vnd.android.cursor.dir/vnd.facebook.mailboxprofiles";
      }

      return var3;
   }

   public Uri insert(Uri var1, ContentValues var2) {
      ContentValues var3;
      if(var2 != null) {
         var3 = new ContentValues(var2);
      } else {
         var3 = new ContentValues();
      }

      Uri var5;
      switch(URL_MATCHER.match(var1)) {
      case 1:
         var5 = this.insertThreadValues(var1, var3);
         break;
      case 2:
      case 6:
      case 7:
      case 8:
      case 9:
      case 11:
      case 15:
      case 16:
      case 17:
      case 18:
      case 19:
      default:
         String var4 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var4);
      case 3:
         Integer var6 = Integer.valueOf(0);
         var3.put("folder", var6);
         var5 = this.insertThreadValues(var1, var3);
         break;
      case 4:
         Integer var7 = Integer.valueOf(1);
         var3.put("folder", var7);
         var5 = this.insertThreadValues(var1, var3);
         break;
      case 5:
         Integer var8 = Integer.valueOf(4);
         var3.put("folder", var8);
         var5 = this.insertThreadValues(var1, var3);
         break;
      case 10:
         var5 = this.insertMessageValues(var1, var3);
         break;
      case 12:
         Integer var9 = Integer.valueOf(0);
         var3.put("folder", var9);
         var5 = this.insertMessageValues(var1, var3);
         break;
      case 13:
         Integer var10 = Integer.valueOf(1);
         var3.put("folder", var10);
         var5 = this.insertMessageValues(var1, var3);
         break;
      case 14:
         Integer var11 = Integer.valueOf(4);
         var3.put("folder", var11);
         var5 = this.insertMessageValues(var1, var3);
         break;
      case 20:
         var5 = this.insertProfileValues(var1, var3);
      }

      return var5;
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
      String var7 = null;
      switch(URL_MATCHER.match(var1)) {
      case 1:
         var6.setTables("mailbox_threads");
         HashMap var9 = MAILBOX_THREADS_PROJECTION_MAP;
         var6.setProjectionMap(var9);
         var7 = setThreadsOrderBy(var5);
         break;
      case 2:
         var6.setTables("mailbox_threads");
         StringBuilder var17 = (new StringBuilder()).append("_id=");
         String var18 = (String)var1.getPathSegments().get(1);
         String var19 = var17.append(var18).toString();
         var6.appendWhere(var19);
         HashMap var20 = MAILBOX_THREADS_PROJECTION_MAP;
         var6.setProjectionMap(var20);
         break;
      case 3:
         var6.setTables("mailbox_threads LEFT OUTER JOIN mailbox_profiles ON mailbox_threads.other_party=mailbox_profiles.id");
         var6.appendWhere("folder=0");
         var7 = setThreadsOrderBy(var5);
         break;
      case 4:
         var6.setTables("mailbox_threads LEFT OUTER JOIN mailbox_profiles ON mailbox_threads.other_party=mailbox_profiles.id");
         var6.appendWhere("folder=1");
         var7 = setThreadsOrderBy(var5);
         break;
      case 5:
         var6.setTables("mailbox_threads LEFT OUTER JOIN mailbox_profiles ON mailbox_threads.other_party=mailbox_profiles.id");
         var6.appendWhere("folder=4");
         var7 = setThreadsOrderBy(var5);
         break;
      case 6:
         var6.setTables("mailbox_threads");
         StringBuilder var21 = (new StringBuilder()).append("folder=0 AND tid=");
         String var22 = (String)var1.getPathSegments().get(3);
         String var23 = var21.append(var22).toString();
         var6.appendWhere(var23);
         HashMap var24 = MAILBOX_THREADS_PROJECTION_MAP;
         var6.setProjectionMap(var24);
         break;
      case 7:
         var6.setTables("mailbox_threads");
         StringBuilder var25 = (new StringBuilder()).append("folder=1 AND tid=");
         String var26 = (String)var1.getPathSegments().get(3);
         String var27 = var25.append(var26).toString();
         var6.appendWhere(var27);
         HashMap var28 = MAILBOX_THREADS_PROJECTION_MAP;
         var6.setProjectionMap(var28);
         break;
      case 8:
         var6.setTables("mailbox_threads");
         StringBuilder var29 = (new StringBuilder()).append("folder=4 AND tid=");
         String var30 = (String)var1.getPathSegments().get(3);
         String var31 = var29.append(var30).toString();
         var6.appendWhere(var31);
         HashMap var32 = MAILBOX_THREADS_PROJECTION_MAP;
         var6.setProjectionMap(var32);
         break;
      case 9:
      case 18:
      case 19:
      default:
         String var8 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var8);
      case 10:
         var6.setTables("mailbox_messages_display");
         HashMap var33 = MAILBOX_MESSAGES_PROJECTION_MAP;
         var6.setProjectionMap(var33);
         var7 = setMessagesOrderBy(var5);
         break;
      case 11:
         var6.setTables("mailbox_messages_display");
         StringBuilder var34 = (new StringBuilder()).append("_id=");
         String var35 = (String)var1.getPathSegments().get(1);
         String var36 = var34.append(var35).toString();
         var6.appendWhere(var36);
         HashMap var37 = MAILBOX_MESSAGES_PROJECTION_MAP;
         var6.setProjectionMap(var37);
         break;
      case 12:
         var6.setTables("mailbox_messages_display");
         var6.appendWhere("folder=0");
         HashMap var38 = MAILBOX_MESSAGES_PROJECTION_MAP;
         var6.setProjectionMap(var38);
         var7 = setMessagesOrderBy(var5);
         break;
      case 13:
         var6.setTables("mailbox_messages_display");
         var6.appendWhere("folder=1");
         HashMap var39 = MAILBOX_MESSAGES_PROJECTION_MAP;
         var6.setProjectionMap(var39);
         var7 = setMessagesOrderBy(var5);
         break;
      case 14:
         var6.setTables("mailbox_messages_display");
         var6.appendWhere("folder=4");
         HashMap var40 = MAILBOX_MESSAGES_PROJECTION_MAP;
         var6.setProjectionMap(var40);
         var7 = setMessagesOrderBy(var5);
         break;
      case 15:
         var6.setTables("mailbox_messages_display");
         StringBuilder var41 = (new StringBuilder()).append("folder=0 AND tid=");
         String var42 = (String)var1.getPathSegments().get(3);
         String var43 = var41.append(var42).toString();
         var6.appendWhere(var43);
         var7 = setMessagesOrderBy(var5);
         break;
      case 16:
         var6.setTables("mailbox_messages_display");
         StringBuilder var44 = (new StringBuilder()).append("folder=1 AND tid=");
         String var45 = (String)var1.getPathSegments().get(3);
         String var46 = var44.append(var45).toString();
         var6.appendWhere(var46);
         var7 = setMessagesOrderBy(var5);
         break;
      case 17:
         var6.setTables("mailbox_messages_display");
         StringBuilder var47 = (new StringBuilder()).append("folder=4 AND tid=");
         String var48 = (String)var1.getPathSegments().get(3);
         String var49 = var47.append(var48).toString();
         var6.appendWhere(var49);
         var7 = setMessagesOrderBy(var5);
         break;
      case 20:
         var6.setTables("mailbox_profiles");
         HashMap var50 = MAILBOX_PROFILES_PROJECTION_MAP;
         var6.setProjectionMap(var50);
         var7 = setProfilesOrderBy(var5);
         break;
      case 21:
         var6.setTables("mailbox_profiles");
         StringBuilder var51 = (new StringBuilder()).append("_id=");
         String var52 = (String)var1.getPathSegments().get(1);
         String var53 = var51.append(var52).toString();
         var6.appendWhere(var53);
         HashMap var54 = MAILBOX_PROFILES_PROJECTION_MAP;
         var6.setProjectionMap(var54);
         break;
      case 22:
         var6.setTables("mailbox_profiles");
         StringBuilder var55 = (new StringBuilder()).append("id=");
         String var56 = (String)var1.getPathSegments().get(2);
         String var57 = var55.append(var56).toString();
         var6.appendWhere(var57);
         HashMap var58 = MAILBOX_PROFILES_PROJECTION_MAP;
         var6.setProjectionMap(var58);
      }

      SQLiteDatabase var10 = this.mDbHelper.getReadableDatabase();
      Object var14 = null;
      Cursor var15 = var6.query(var10, var2, var3, var4, (String)null, (String)var14, var7);
      ContentResolver var16 = this.getContext().getContentResolver();
      var15.setNotificationUri(var16, var1);
      return var15;
   }

   public int update(Uri var1, ContentValues var2, String var3, String[] var4) {
      SQLiteDatabase var5 = this.mDbHelper.getWritableDatabase();
      int var7;
      String var10;
      String var12;
      switch(URL_MATCHER.match(var1)) {
      case 1:
         var7 = var5.update("mailbox_threads", var2, var3, var4);
         break;
      case 2:
         String var8 = (String)var1.getPathSegments().get(1);
         String var9 = "_id=" + var8;
         var7 = var5.update("mailbox_threads", var2, var9, (String[])null);
         break;
      case 3:
         var10 = "mailbox_threads";
         StringBuilder var11 = (new StringBuilder()).append("folder=0");
         if(!TextUtils.isEmpty(var3)) {
            var12 = " AND (" + var3 + ')';
         } else {
            var12 = "";
         }

         String var13 = var11.append(var12).toString();
         var7 = var5.update(var10, var2, var13, var4);
         break;
      case 4:
         var10 = "mailbox_threads";
         StringBuilder var14 = (new StringBuilder()).append("folder=1");
         if(!TextUtils.isEmpty(var3)) {
            var12 = " AND (" + var3 + ')';
         } else {
            var12 = "";
         }

         String var15 = var14.append(var12).toString();
         var7 = var5.update(var10, var2, var15, var4);
         break;
      case 5:
         var10 = "mailbox_threads";
         StringBuilder var16 = (new StringBuilder()).append("folder=4");
         if(!TextUtils.isEmpty(var3)) {
            var12 = " AND (" + var3 + ')';
         } else {
            var12 = "";
         }

         String var17 = var16.append(var12).toString();
         var7 = var5.update(var10, var2, var17, var4);
         break;
      case 6:
         String var18 = (String)var1.getPathSegments().get(3);
         String var19 = "folder=0 AND tid=" + var18;
         var7 = var5.update("mailbox_threads", var2, var19, (String[])null);
         break;
      case 7:
         String var20 = (String)var1.getPathSegments().get(3);
         String var21 = "folder=1 AND tid=" + var20;
         var7 = var5.update("mailbox_threads", var2, var21, (String[])null);
         break;
      case 8:
         String var22 = (String)var1.getPathSegments().get(3);
         String var23 = "folder=4 AND tid=" + var22;
         var7 = var5.update("mailbox_threads", var2, var23, (String[])null);
         break;
      case 9:
      case 18:
      case 19:
      default:
         String var6 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var6);
      case 10:
         var7 = var5.update("mailbox_messages", var2, var3, var4);
         break;
      case 11:
         String var24 = (String)var1.getPathSegments().get(1);
         String var25 = "_id=" + var24;
         var7 = var5.update("mailbox_messages", var2, var25, (String[])null);
         break;
      case 12:
         var10 = "mailbox_messages";
         StringBuilder var26 = (new StringBuilder()).append("folder=0");
         if(!TextUtils.isEmpty(var3)) {
            var12 = " AND (" + var3 + ')';
         } else {
            var12 = "";
         }

         String var27 = var26.append(var12).toString();
         var7 = var5.update(var10, var2, var27, var4);
         break;
      case 13:
         var10 = "mailbox_messages";
         StringBuilder var28 = (new StringBuilder()).append("folder=1");
         if(!TextUtils.isEmpty(var3)) {
            var12 = " AND (" + var3 + ')';
         } else {
            var12 = "";
         }

         String var29 = var28.append(var12).toString();
         var7 = var5.update(var10, var2, var29, var4);
         break;
      case 14:
         var10 = "mailbox_messages";
         StringBuilder var30 = (new StringBuilder()).append("folder=4");
         if(!TextUtils.isEmpty(var3)) {
            var12 = " AND (" + var3 + ')';
         } else {
            var12 = "";
         }

         String var31 = var30.append(var12).toString();
         var7 = var5.update(var10, var2, var31, var4);
         break;
      case 15:
         String var32 = (String)var1.getPathSegments().get(3);
         String var33 = "tid=" + var32 + " AND " + "folder" + "=" + 0;
         var7 = var5.update("mailbox_messages", var2, var33, (String[])null);
         break;
      case 16:
         String var34 = (String)var1.getPathSegments().get(3);
         String var35 = "tid=" + var34 + " AND " + "folder" + "=" + 1;
         var7 = var5.update("mailbox_messages", var2, var35, (String[])null);
         break;
      case 17:
         String var36 = (String)var1.getPathSegments().get(3);
         String var37 = "tid=" + var36 + " AND " + "folder" + "=" + 4;
         var7 = var5.update("mailbox_messages", var2, var37, (String[])null);
         break;
      case 20:
         var7 = var5.update("mailbox_profiles", var2, var3, var4);
         break;
      case 21:
         String var38 = (String)var1.getPathSegments().get(1);
         String var39 = "_id=" + var38;
         var7 = var5.update("mailbox_profiles", var2, var39, (String[])null);
         break;
      case 22:
         String var40 = (String)var1.getPathSegments().get(2);
         String var41 = "id=" + var40;
         var7 = var5.update("mailbox_profiles", var2, var41, (String[])null);
      }

      if(var7 > 0) {
         this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
      }

      return var7;
   }

   public static final class MessageDisplayColumns extends MailboxProvider.MessageColumns {

      public static final String AUTHOR_ID = "author_id";
      public static final String AUTHOR_IMAGE_URL = "author_image_url";
      public static final String AUTHOR_NAME = "author_name";
      public static final String AUTHOR_TYPE = "author_type";
      public static final String OBJECT_ID = "object_id";
      public static final String OBJECT_IMAGE_URL = "object_image_url";
      public static final String OBJECT_NAME = "object_name";
      public static final String OBJECT_TYPE = "object_type";


      public MessageDisplayColumns() {}
   }

   public static class MessageColumns implements BaseColumns {

      public static final String AUTHOR_PROFILE_ID = "author_id";
      public static final String BODY = "body";
      public static final String DEFAULT_SORT_ORDER = "mid ASC";
      public static final String FOLDER = "folder";
      public static final String MESSAGE_ID = "mid";
      public static final String SPECIFIC_ID = "mailbox_messages._id";
      public static final String THREAD_ID = "tid";
      public static final String TIME_SENT = "sent";


      public MessageColumns() {}
   }

   public static final class ProfileColumns implements BaseColumns {

      public static final String DEFAULT_SORT_ORDER = "display_name DESC";
      public static final String PROFILE_DISPLAY_NAME = "display_name";
      public static final String PROFILE_ID = "id";
      public static final String PROFILE_IMAGE_URL = "profile_image_url";
      public static final String PROFILE_TYPE = "type";


      public ProfileColumns() {}
   }

   public static final class ThreadColumns implements BaseColumns {

      public static final String DEFAULT_SORT_ORDER = "last_update DESC";
      public static final String FOLDER = "folder";
      public static final String LAST_UPDATE = "last_update";
      public static final String MSG_COUNT = "msg_count";
      public static final String OBJECT_ID = "object_id";
      public static final String OTHER_PARTY_PROFILE_ID = "other_party";
      public static final String PARTICIPANTS = "participants";
      public static final String SNIPPET = "snippet";
      public static final String SPECIFIC_ID = "mailbox_threads._id";
      public static final String SUBJECT = "subject";
      public static final String THREAD_ID = "tid";
      public static final String UNREAD_COUNT = "unread_count";


      public ThreadColumns() {}
   }
}
