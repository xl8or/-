package com.facebook.katana.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.net.Uri;
import com.facebook.katana.provider.CacheProvider;
import com.facebook.katana.provider.ChatHistoryProvider;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.provider.EventsProvider;
import com.facebook.katana.provider.KeyValueProvider;
import com.facebook.katana.provider.LoggingProvider;
import com.facebook.katana.provider.MailboxProvider;
import com.facebook.katana.provider.NotificationsProvider;
import com.facebook.katana.provider.PagesProvider;
import com.facebook.katana.provider.PhotosProvider;
import com.facebook.katana.provider.UserStatusesProvider;
import com.facebook.katana.provider.UserValuesManager;
import com.facebook.katana.provider.UserValuesProvider;
import com.facebook.katana.util.FileUtils;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class FacebookDatabaseHelper extends SQLiteOpenHelper {

   private static final String DATABASE_NAME = "fb.db";
   private static final int DATABASE_VERSION = 64;
   private static final int DATABASE_VERSION_BEFORE_ALBUM_FBIDS = 62;
   private static final int DATABASE_VERSION_BEFORE_BIRTHDAYS = 50;
   private static final int DATABASE_VERSION_BEFORE_CACHE = 59;
   private static final int DATABASE_VERSION_BEFORE_CHAT_HISTORY = 55;
   private static final int DATABASE_VERSION_BEFORE_CONNECTIONS = 60;
   private static final int DATABASE_VERSION_BEFORE_EVENTS = 49;
   private static final int DATABASE_VERSION_BEFORE_EVENTS_DB_FIX = 51;
   private static final int DATABASE_VERSION_BEFORE_FACEBOOK = 45;
   private static final int DATABASE_VERSION_BEFORE_FNLN = 48;
   private static final int DATABASE_VERSION_BEFORE_FRIENDS_DATA_ERROR = 61;
   private static final int DATABASE_VERSION_BEFORE_KEY_VALUE = 53;
   private static final int DATABASE_VERSION_BEFORE_LOGGING = 52;
   private static final int DATABASE_VERSION_BEFORE_MAILBOX_PROFILE = 57;
   private static final int DATABASE_VERSION_BEFORE_MESSAGING = 46;
   private static final int DATABASE_VERSION_BEFORE_NOTIFICATIONS_REFRESH = 56;
   private static final int DATABASE_VERSION_BEFORE_PAGES = 58;
   private static final int DATABASE_VERSION_BEFORE_PHOTO_POSITION = 63;
   static final String DROP_TABLE_PREFIX = "DROP TABLE IF EXISTS ";
   static final String DROP_VIEW_PREFIX = "DROP VIEW IF EXISTS ";
   private static FacebookDatabaseHelper.DatabaseUpdater forceFriendsSync = new FacebookDatabaseHelper.14();
   private static FacebookDatabaseHelper mDbHelper;
   private static FacebookDatabaseHelper.DatabaseUpdater migrateFriendsTable = new FacebookDatabaseHelper.13();
   private static FacebookDatabaseHelper.DatabaseUpdater recreateCacheTables = new FacebookDatabaseHelper.7();
   private static FacebookDatabaseHelper.DatabaseUpdater recreateChatTables = new FacebookDatabaseHelper.12();
   private static FacebookDatabaseHelper.DatabaseUpdater recreateConnectionTables = new FacebookDatabaseHelper.5();
   private static FacebookDatabaseHelper.DatabaseUpdater recreateEventsTables = new FacebookDatabaseHelper.9();
   private static FacebookDatabaseHelper.DatabaseUpdater recreateKeyValueTables = new FacebookDatabaseHelper.11();
   private static FacebookDatabaseHelper.DatabaseUpdater recreateLoggingTables = new FacebookDatabaseHelper.10();
   private static FacebookDatabaseHelper.DatabaseUpdater recreateMailboxTables = new FacebookDatabaseHelper.4();
   private static FacebookDatabaseHelper.DatabaseUpdater recreateNotificationsTables = new FacebookDatabaseHelper.2();
   private static FacebookDatabaseHelper.DatabaseUpdater recreatePagesTables = new FacebookDatabaseHelper.6();
   private static FacebookDatabaseHelper.DatabaseUpdater recreatePhotosTables = new FacebookDatabaseHelper.8();
   private static FacebookDatabaseHelper.DatabaseUpdater recreateUserStatusTables = new FacebookDatabaseHelper.3();
   private static FacebookDatabaseHelper.DatabaseUpdater recreateUserValuesTables = new FacebookDatabaseHelper.1();
   private final Context mContext;


   private FacebookDatabaseHelper(Context var1) {
      super(var1, "fb.db", (CursorFactory)null, 64);
      this.mContext = var1;
   }

   public static void clearPrivateData(Context var0) {
      UserValuesManager.clearUserValues(var0);
      ContentResolver var1 = var0.getContentResolver();
      Uri var2 = UserStatusesProvider.CONTENT_URI;
      var1.delete(var2, (String)null, (String[])null);
      Uri var4 = ConnectionsProvider.CONNECTIONS_CONTENT_URI;
      var1.delete(var4, (String)null, (String[])null);
      Uri var6 = ConnectionsProvider.USER_SEARCH_CONTENT_URI;
      var1.delete(var6, (String)null, (String[])null);
      Uri var8 = PhotosProvider.PHOTOS_CONTENT_URI;
      var1.delete(var8, (String)null, (String[])null);
      Uri var10 = PhotosProvider.ALBUMS_CONTENT_URI;
      var1.delete(var10, (String)null, (String[])null);
      Uri var12 = PhotosProvider.STREAM_PHOTOS_CONTENT_URI;
      var1.delete(var12, (String)null, (String[])null);
      Uri var14 = MailboxProvider.THREADS_CONTENT_URI;
      var1.delete(var14, (String)null, (String[])null);
      Uri var16 = MailboxProvider.MESSAGES_CONTENT_URI;
      var1.delete(var16, (String)null, (String[])null);
      Uri var18 = MailboxProvider.PROFILES_CONTENT_URI;
      var1.delete(var18, (String)null, (String[])null);
      Uri var20 = NotificationsProvider.CONTENT_URI;
      var1.delete(var20, (String)null, (String[])null);
      Uri var22 = EventsProvider.EVENTS_CONTENT_URI;
      var1.delete(var22, (String)null, (String[])null);
      Uri var24 = ChatHistoryProvider.HISTORY_CONTENT_URI;
      var1.delete(var24, (String)null, (String[])null);
      Uri var26 = ChatHistoryProvider.CONVERSATIONS_CONTENT_URI;
      var1.delete(var26, (String)null, (String[])null);
      Uri var28 = PagesProvider.SEARCH_RESULTS_CONTENT_URI;
      var1.delete(var28, (String)null, (String[])null);
      Uri var30 = CacheProvider.CONTENT_URI;
      var1.delete(var30, (String)null, (String[])null);
      FileUtils.deleteFilesInDirectory(var0.getFilesDir().getAbsolutePath());
   }

   private static void dropSQLTableOrView(SQLiteDatabase var0, String var1) {
      try {
         String var2 = "DROP TABLE IF EXISTS " + var1;
         var0.execSQL(var2);
      } catch (SQLiteException var5) {
         String var4 = "DROP VIEW IF EXISTS " + var1;
         var0.execSQL(var4);
      }
   }

   public static FacebookDatabaseHelper getDatabaseHelper(Context var0) {
      synchronized(FacebookDatabaseHelper.class){}

      FacebookDatabaseHelper var1;
      try {
         if(mDbHelper != null) {
            var1 = mDbHelper;
         } else {
            mDbHelper = new FacebookDatabaseHelper(var0);
            var1 = mDbHelper;
         }
      } finally {
         ;
      }

      return var1;
   }

   public void onCreate(SQLiteDatabase var1) {
      recreateUserValuesTables.update(var1);
      recreateNotificationsTables.update(var1);
      recreateUserStatusTables.update(var1);
      recreateMailboxTables.update(var1);
      recreateConnectionTables.update(var1);
      recreatePagesTables.update(var1);
      recreatePhotosTables.update(var1);
      recreateEventsTables.update(var1);
      recreateLoggingTables.update(var1);
      recreateKeyValueTables.update(var1);
      recreateChatTables.update(var1);
      recreateCacheTables.update(var1);
   }

   public void onUpgrade(SQLiteDatabase var1, int var2, int var3) {
      if(var2 <= 45) {
         this.onCreate(var1);
      } else {
         try {
            LinkedHashSet var4 = new LinkedHashSet();
            boolean var5 = false;
            if(var2 <= 46) {
               FacebookDatabaseHelper.DatabaseUpdater var6 = recreateMailboxTables;
               var4.add(var6);
            }

            if(var2 <= 48) {
               FacebookDatabaseHelper.DatabaseUpdater var8 = recreateConnectionTables;
               var4.add(var8);
               FacebookDatabaseHelper.DatabaseUpdater var10 = recreateMailboxTables;
               var4.add(var10);
               FacebookDatabaseHelper.DatabaseUpdater var12 = recreateUserStatusTables;
               var4.add(var12);
               var5 = true;
            }

            if(var2 <= 49) {
               FacebookDatabaseHelper.DatabaseUpdater var14 = recreateEventsTables;
               var4.add(var14);
            }

            if(var2 <= 50) {
               FacebookDatabaseHelper.DatabaseUpdater var16 = recreateConnectionTables;
               var4.add(var16);
               var5 = true;
            }

            if(var2 <= 51) {
               FacebookDatabaseHelper.DatabaseUpdater var18 = recreateEventsTables;
               var4.add(var18);
            }

            if(var2 <= 52) {
               FacebookDatabaseHelper.DatabaseUpdater var20 = recreateLoggingTables;
               var4.add(var20);
            }

            if(var2 <= 53) {
               FacebookDatabaseHelper.DatabaseUpdater var22 = recreateKeyValueTables;
               var4.add(var22);
            }

            if(var2 <= 55) {
               FacebookDatabaseHelper.DatabaseUpdater var24 = recreateChatTables;
               var4.add(var24);
            }

            if(var2 <= 56) {
               FacebookDatabaseHelper.DatabaseUpdater var26 = recreateNotificationsTables;
               var4.add(var26);
            }

            if(var2 <= 57) {
               FacebookDatabaseHelper.DatabaseUpdater var28 = recreateMailboxTables;
               var4.add(var28);
            }

            if(var2 <= 58) {
               FacebookDatabaseHelper.DatabaseUpdater var30 = recreatePagesTables;
               var4.add(var30);
            }

            if(var2 <= 59) {
               FacebookDatabaseHelper.DatabaseUpdater var32 = recreateCacheTables;
               var4.add(var32);
            }

            if(var2 <= 60 && !var5) {
               FacebookDatabaseHelper.DatabaseUpdater var34 = migrateFriendsTable;
               var4.add(var34);
            }

            if(var2 <= 61) {
               FacebookDatabaseHelper.DatabaseUpdater var36 = forceFriendsSync;
               var4.add(var36);
            }

            if(var2 <= 62) {
               FacebookDatabaseHelper.DatabaseUpdater var38 = recreatePhotosTables;
               var4.add(var38);
            }

            if(var2 <= 63) {
               FacebookDatabaseHelper.DatabaseUpdater var40 = recreatePhotosTables;
               var4.add(var40);
            }

            Iterator var42 = var4.iterator();

            while(var42.hasNext()) {
               ((FacebookDatabaseHelper.DatabaseUpdater)var42.next()).update(var1);
            }
         } catch (SQLiteException var44) {
            this.onCreate(var1);
         }
      }

      FileUtils.deleteFilesInDirectory(this.mContext.getFilesDir().getAbsolutePath());
   }

   static class 9 implements FacebookDatabaseHelper.DatabaseUpdater {

      9() {}

      public void update(SQLiteDatabase var1) {
         String[] var2 = EventsProvider.getTableNames();
         int var3 = 0;

         while(true) {
            int var4 = var2.length;
            if(var3 >= var4) {
               String[] var6 = EventsProvider.getTableSQLs();
               int var7 = 0;

               while(true) {
                  int var8 = var6.length;
                  if(var7 >= var8) {
                     return;
                  }

                  String var9 = var6[var7];
                  var1.execSQL(var9);
                  ++var7;
               }
            }

            String var5 = var2[var3];
            FacebookDatabaseHelper.dropSQLTableOrView(var1, var5);
            ++var3;
         }
      }
   }

   static class 7 implements FacebookDatabaseHelper.DatabaseUpdater {

      7() {}

      public void update(SQLiteDatabase var1) {
         String[] var2 = CacheProvider.getTableNames();
         int var3 = 0;

         while(true) {
            int var4 = var2.length;
            if(var3 >= var4) {
               String[] var6 = CacheProvider.getTableSQLs();
               int var7 = 0;

               while(true) {
                  int var8 = var6.length;
                  if(var7 >= var8) {
                     return;
                  }

                  String var9 = var6[var7];
                  var1.execSQL(var9);
                  ++var7;
               }
            }

            String var5 = var2[var3];
            FacebookDatabaseHelper.dropSQLTableOrView(var1, var5);
            ++var3;
         }
      }
   }

   static class 8 implements FacebookDatabaseHelper.DatabaseUpdater {

      8() {}

      public void update(SQLiteDatabase var1) {
         String[] var2 = PhotosProvider.getTableNames();
         int var3 = 0;

         while(true) {
            int var4 = var2.length;
            if(var3 >= var4) {
               String[] var6 = PhotosProvider.getTableSQLs();
               int var7 = 0;

               while(true) {
                  int var8 = var6.length;
                  if(var7 >= var8) {
                     return;
                  }

                  String var9 = var6[var7];
                  var1.execSQL(var9);
                  ++var7;
               }
            }

            String var5 = var2[var3];
            FacebookDatabaseHelper.dropSQLTableOrView(var1, var5);
            ++var3;
         }
      }
   }

   static class 5 implements FacebookDatabaseHelper.DatabaseUpdater {

      5() {}

      public void update(SQLiteDatabase var1) {
         String[] var2 = ConnectionsProvider.getTableNames();
         int var3 = 0;

         while(true) {
            int var4 = var2.length;
            if(var3 >= var4) {
               String[] var6 = ConnectionsProvider.getViewNames();
               int var7 = 0;

               while(true) {
                  int var8 = var6.length;
                  if(var7 >= var8) {
                     String[] var10 = ConnectionsProvider.getTableSQLs();
                     int var11 = 0;

                     while(true) {
                        int var12 = var10.length;
                        if(var11 >= var12) {
                           return;
                        }

                        String var13 = var10[var11];
                        var1.execSQL(var13);
                        ++var11;
                     }
                  }

                  String var9 = var6[var7];
                  FacebookDatabaseHelper.dropSQLTableOrView(var1, var9);
                  ++var7;
               }
            }

            String var5 = var2[var3];
            FacebookDatabaseHelper.dropSQLTableOrView(var1, var5);
            ++var3;
         }
      }
   }

   static class 6 implements FacebookDatabaseHelper.DatabaseUpdater {

      6() {}

      public void update(SQLiteDatabase var1) {
         String[] var2 = PagesProvider.getTableNames();
         int var3 = 0;

         while(true) {
            int var4 = var2.length;
            if(var3 >= var4) {
               String[] var6 = PagesProvider.getTableSQLs();
               int var7 = 0;

               while(true) {
                  int var8 = var6.length;
                  if(var7 >= var8) {
                     return;
                  }

                  String var9 = var6[var7];
                  var1.execSQL(var9);
                  ++var7;
               }
            }

            String var5 = var2[var3];
            FacebookDatabaseHelper.dropSQLTableOrView(var1, var5);
            ++var3;
         }
      }
   }

   static class 10 implements FacebookDatabaseHelper.DatabaseUpdater {

      10() {}

      public void update(SQLiteDatabase var1) {
         String[] var2 = LoggingProvider.getTableNames();
         int var3 = 0;

         while(true) {
            int var4 = var2.length;
            if(var3 >= var4) {
               String[] var6 = LoggingProvider.getTableSQLs();
               int var7 = 0;

               while(true) {
                  int var8 = var6.length;
                  if(var7 >= var8) {
                     return;
                  }

                  String var9 = var6[var7];
                  var1.execSQL(var9);
                  ++var7;
               }
            }

            String var5 = var2[var3];
            FacebookDatabaseHelper.dropSQLTableOrView(var1, var5);
            ++var3;
         }
      }
   }

   static class 11 implements FacebookDatabaseHelper.DatabaseUpdater {

      11() {}

      public void update(SQLiteDatabase var1) {
         String[] var2 = KeyValueProvider.getTableNames();
         int var3 = 0;

         while(true) {
            int var4 = var2.length;
            if(var3 >= var4) {
               String[] var6 = KeyValueProvider.getTableSQLs();
               int var7 = 0;

               while(true) {
                  int var8 = var6.length;
                  if(var7 >= var8) {
                     return;
                  }

                  String var9 = var6[var7];
                  var1.execSQL(var9);
                  ++var7;
               }
            }

            String var5 = var2[var3];
            FacebookDatabaseHelper.dropSQLTableOrView(var1, var5);
            ++var3;
         }
      }
   }

   static class 12 implements FacebookDatabaseHelper.DatabaseUpdater {

      12() {}

      public void update(SQLiteDatabase var1) {
         String[] var2 = ChatHistoryProvider.getTableNames();
         String[] var3 = ChatHistoryProvider.getTableSQLs();
         int var4 = 0;

         while(true) {
            int var5 = var2.length;
            if(var4 >= var5) {
               StringBuilder var8 = (new StringBuilder()).append("CREATE INDEX IF NOT EXISTS CHAT_INDEX ON ");
               String var9 = var2[0];
               String var10 = var8.append(var9).append(" ( ").append("friend_uid").append(" ) ").toString();
               var1.execSQL(var10);
               return;
            }

            String var6 = var2[var4];
            FacebookDatabaseHelper.dropSQLTableOrView(var1, var6);
            String var7 = var3[var4];
            var1.execSQL(var7);
            ++var4;
         }
      }
   }

   static class 13 implements FacebookDatabaseHelper.DatabaseUpdater {

      13() {}

      public void update(SQLiteDatabase var1) {
         var1.execSQL("DROP TABLE search_results");
         var1.execSQL("DROP TABLE info_contacts");
         var1.execSQL("DROP TABLE default_user_images");
         var1.execSQL("ALTER TABLE friends RENAME TO connections");
         StringBuilder var2 = (new StringBuilder()).append("ALTER TABLE connections ADD COLUMN connection_type INT NOT NULL DEFAULT ");
         int var3 = ConnectionsProvider.ConnectionType.USER.ordinal();
         String var4 = var2.append(var3).toString();
         var1.execSQL(var4);
         var1.execSQL("CREATE TABLE friends_data (_id INTEGER PRIMARY KEY,user_id INT UNIQUE,first_name TEXT,last_name TEXT,cell TEXT,other TEXT,email TEXT,birthday_month INT,birthday_day INT,birthday_year INT);");
         var1.execSQL("CREATE TABLE search_results (_id INTEGER PRIMARY KEY,user_id INT,display_name TEXT,user_image_url TEXT);");
         String var5 = ConnectionsProvider.SQL_FRIENDS_VIEW;
         var1.execSQL(var5);
         StringBuilder var6 = (new StringBuilder()).append("INSERT INTO friends_data(user_id) SELECT user_id  FROM connections  WHERE connection_type=");
         int var7 = ConnectionsProvider.ConnectionType.USER.ordinal();
         String var8 = var6.append(var7).toString();
         var1.execSQL(var8);
         FacebookDatabaseHelper.forceFriendsSync.update(var1);
      }
   }

   static class 2 implements FacebookDatabaseHelper.DatabaseUpdater {

      2() {}

      public void update(SQLiteDatabase var1) {
         String var2 = NotificationsProvider.getTableName();
         FacebookDatabaseHelper.dropSQLTableOrView(var1, var2);
         String var3 = NotificationsProvider.getTableSQL();
         var1.execSQL(var3);
      }
   }

   interface DatabaseUpdater {

      void update(SQLiteDatabase var1);
   }

   static class 1 implements FacebookDatabaseHelper.DatabaseUpdater {

      1() {}

      public void update(SQLiteDatabase var1) {
         String var2 = UserValuesProvider.getTableName();
         FacebookDatabaseHelper.dropSQLTableOrView(var1, var2);
         String var3 = UserValuesProvider.getTableSQL();
         var1.execSQL(var3);
      }
   }

   static class 4 implements FacebookDatabaseHelper.DatabaseUpdater {

      4() {}

      public void update(SQLiteDatabase var1) {
         String[] var2 = MailboxProvider.getTableNames();
         int var3 = 0;

         while(true) {
            int var4 = var2.length;
            if(var3 >= var4) {
               String[] var6 = MailboxProvider.getViewNames();
               int var7 = 0;

               while(true) {
                  int var8 = var6.length;
                  if(var7 >= var8) {
                     String[] var10 = MailboxProvider.getTableSQLs();
                     int var11 = 0;

                     while(true) {
                        int var12 = var10.length;
                        if(var11 >= var12) {
                           return;
                        }

                        String var13 = var10[var11];
                        var1.execSQL(var13);
                        ++var11;
                     }
                  }

                  String var9 = var6[var7];
                  FacebookDatabaseHelper.dropSQLTableOrView(var1, var9);
                  ++var7;
               }
            }

            String var5 = var2[var3];
            FacebookDatabaseHelper.dropSQLTableOrView(var1, var5);
            ++var3;
         }
      }
   }

   static class 3 implements FacebookDatabaseHelper.DatabaseUpdater {

      3() {}

      public void update(SQLiteDatabase var1) {
         String var2 = UserStatusesProvider.getTableName();
         FacebookDatabaseHelper.dropSQLTableOrView(var1, var2);
         String var3 = UserStatusesProvider.getTableSQL();
         var1.execSQL(var3);
      }
   }

   static class 14 implements FacebookDatabaseHelper.DatabaseUpdater {

      14() {}

      public void update(SQLiteDatabase var1) {
         var1.execSQL("UPDATE connections SET hash=0");
      }
   }
}
