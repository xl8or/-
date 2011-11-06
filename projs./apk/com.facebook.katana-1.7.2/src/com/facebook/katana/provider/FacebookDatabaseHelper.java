// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookDatabaseHelper.java

package com.facebook.katana.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.sqlite.*;
import com.facebook.katana.util.FileUtils;
import java.io.File;
import java.util.*;

// Referenced classes of package com.facebook.katana.provider:
//            UserValuesManager, UserStatusesProvider, ConnectionsProvider, PhotosProvider, 
//            MailboxProvider, NotificationsProvider, EventsProvider, ChatHistoryProvider, 
//            PagesProvider, CacheProvider, LoggingProvider, KeyValueProvider, 
//            UserValuesProvider

public class FacebookDatabaseHelper extends SQLiteOpenHelper
{
    static interface DatabaseUpdater
    {

        public abstract void update(SQLiteDatabase sqlitedatabase);
    }


    private FacebookDatabaseHelper(Context context)
    {
        super(context, "fb.db", null, 64);
        mContext = context;
    }

    public static void clearPrivateData(Context context)
    {
        UserValuesManager.clearUserValues(context);
        ContentResolver contentresolver = context.getContentResolver();
        contentresolver.delete(UserStatusesProvider.CONTENT_URI, null, null);
        contentresolver.delete(ConnectionsProvider.CONNECTIONS_CONTENT_URI, null, null);
        contentresolver.delete(ConnectionsProvider.USER_SEARCH_CONTENT_URI, null, null);
        contentresolver.delete(PhotosProvider.PHOTOS_CONTENT_URI, null, null);
        contentresolver.delete(PhotosProvider.ALBUMS_CONTENT_URI, null, null);
        contentresolver.delete(PhotosProvider.STREAM_PHOTOS_CONTENT_URI, null, null);
        contentresolver.delete(MailboxProvider.THREADS_CONTENT_URI, null, null);
        contentresolver.delete(MailboxProvider.MESSAGES_CONTENT_URI, null, null);
        contentresolver.delete(MailboxProvider.PROFILES_CONTENT_URI, null, null);
        contentresolver.delete(NotificationsProvider.CONTENT_URI, null, null);
        contentresolver.delete(EventsProvider.EVENTS_CONTENT_URI, null, null);
        contentresolver.delete(ChatHistoryProvider.HISTORY_CONTENT_URI, null, null);
        contentresolver.delete(ChatHistoryProvider.CONVERSATIONS_CONTENT_URI, null, null);
        contentresolver.delete(PagesProvider.SEARCH_RESULTS_CONTENT_URI, null, null);
        contentresolver.delete(CacheProvider.CONTENT_URI, null, null);
        FileUtils.deleteFilesInDirectory(context.getFilesDir().getAbsolutePath());
    }

    private static void dropSQLTableOrView(SQLiteDatabase sqlitedatabase, String s)
    {
        sqlitedatabase.execSQL((new StringBuilder()).append("DROP TABLE IF EXISTS ").append(s).toString());
_L1:
        return;
        SQLiteException sqliteexception;
        sqliteexception;
        sqlitedatabase.execSQL((new StringBuilder()).append("DROP VIEW IF EXISTS ").append(s).toString());
          goto _L1
    }

    /**
     * @deprecated Method getDatabaseHelper is deprecated
     */

    public static FacebookDatabaseHelper getDatabaseHelper(Context context)
    {
        com/facebook/katana/provider/FacebookDatabaseHelper;
        JVM INSTR monitorenter ;
        if(mDbHelper == null) goto _L2; else goto _L1
_L1:
        FacebookDatabaseHelper facebookdatabasehelper = mDbHelper;
_L4:
        com/facebook/katana/provider/FacebookDatabaseHelper;
        JVM INSTR monitorexit ;
        return facebookdatabasehelper;
_L2:
        mDbHelper = new FacebookDatabaseHelper(context);
        facebookdatabasehelper = mDbHelper;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public void onCreate(SQLiteDatabase sqlitedatabase)
    {
        recreateUserValuesTables.update(sqlitedatabase);
        recreateNotificationsTables.update(sqlitedatabase);
        recreateUserStatusTables.update(sqlitedatabase);
        recreateMailboxTables.update(sqlitedatabase);
        recreateConnectionTables.update(sqlitedatabase);
        recreatePagesTables.update(sqlitedatabase);
        recreatePhotosTables.update(sqlitedatabase);
        recreateEventsTables.update(sqlitedatabase);
        recreateLoggingTables.update(sqlitedatabase);
        recreateKeyValueTables.update(sqlitedatabase);
        recreateChatTables.update(sqlitedatabase);
        recreateCacheTables.update(sqlitedatabase);
    }

    public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j)
    {
        if(i <= 45)
            onCreate(sqlitedatabase);
        else
            try
            {
                LinkedHashSet linkedhashset = new LinkedHashSet();
                boolean flag = false;
                if(i <= 46)
                    linkedhashset.add(recreateMailboxTables);
                if(i <= 48)
                {
                    linkedhashset.add(recreateConnectionTables);
                    linkedhashset.add(recreateMailboxTables);
                    linkedhashset.add(recreateUserStatusTables);
                    flag = true;
                }
                if(i <= 49)
                    linkedhashset.add(recreateEventsTables);
                if(i <= 50)
                {
                    linkedhashset.add(recreateConnectionTables);
                    flag = true;
                }
                if(i <= 51)
                    linkedhashset.add(recreateEventsTables);
                if(i <= 52)
                    linkedhashset.add(recreateLoggingTables);
                if(i <= 53)
                    linkedhashset.add(recreateKeyValueTables);
                if(i <= 55)
                    linkedhashset.add(recreateChatTables);
                if(i <= 56)
                    linkedhashset.add(recreateNotificationsTables);
                if(i <= 57)
                    linkedhashset.add(recreateMailboxTables);
                if(i <= 58)
                    linkedhashset.add(recreatePagesTables);
                if(i <= 59)
                    linkedhashset.add(recreateCacheTables);
                if(i <= 60 && !flag)
                    linkedhashset.add(migrateFriendsTable);
                if(i <= 61)
                    linkedhashset.add(forceFriendsSync);
                if(i <= 62)
                    linkedhashset.add(recreatePhotosTables);
                if(i <= 63)
                    linkedhashset.add(recreatePhotosTables);
                Iterator iterator = linkedhashset.iterator();
                while(iterator.hasNext()) 
                    ((DatabaseUpdater)iterator.next()).update(sqlitedatabase);
            }
            catch(SQLiteException sqliteexception)
            {
                onCreate(sqlitedatabase);
            }
        FileUtils.deleteFilesInDirectory(mContext.getFilesDir().getAbsolutePath());
    }

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
    private static DatabaseUpdater forceFriendsSync = new DatabaseUpdater() {

        public void update(SQLiteDatabase sqlitedatabase)
        {
            sqlitedatabase.execSQL("UPDATE connections SET hash=0");
        }

    }
;
    private static FacebookDatabaseHelper mDbHelper;
    private static DatabaseUpdater migrateFriendsTable = new DatabaseUpdater() {

        public void update(SQLiteDatabase sqlitedatabase)
        {
            sqlitedatabase.execSQL("DROP TABLE search_results");
            sqlitedatabase.execSQL("DROP TABLE info_contacts");
            sqlitedatabase.execSQL("DROP TABLE default_user_images");
            sqlitedatabase.execSQL("ALTER TABLE friends RENAME TO connections");
            sqlitedatabase.execSQL((new StringBuilder()).append("ALTER TABLE connections ADD COLUMN connection_type INT NOT NULL DEFAULT ").append(ConnectionsProvider.ConnectionType.USER.ordinal()).toString());
            sqlitedatabase.execSQL("CREATE TABLE friends_data (_id INTEGER PRIMARY KEY,user_id INT UNIQUE,first_name TEXT,last_name TEXT,cell TEXT,other TEXT,email TEXT,birthday_month INT,birthday_day INT,birthday_year INT);");
            sqlitedatabase.execSQL("CREATE TABLE search_results (_id INTEGER PRIMARY KEY,user_id INT,display_name TEXT,user_image_url TEXT);");
            sqlitedatabase.execSQL(ConnectionsProvider.SQL_FRIENDS_VIEW);
            sqlitedatabase.execSQL((new StringBuilder()).append("INSERT INTO friends_data(user_id) SELECT user_id  FROM connections  WHERE connection_type=").append(ConnectionsProvider.ConnectionType.USER.ordinal()).toString());
            FacebookDatabaseHelper.forceFriendsSync.update(sqlitedatabase);
        }

    }
;
    private static DatabaseUpdater recreateCacheTables = new DatabaseUpdater() {

        public void update(SQLiteDatabase sqlitedatabase)
        {
            String as[] = CacheProvider.getTableNames();
            for(int i = 0; i < as.length; i++)
                FacebookDatabaseHelper.dropSQLTableOrView(sqlitedatabase, as[i]);

            String as1[] = CacheProvider.getTableSQLs();
            for(int j = 0; j < as1.length; j++)
                sqlitedatabase.execSQL(as1[j]);

        }

    }
;
    private static DatabaseUpdater recreateChatTables = new DatabaseUpdater() {

        public void update(SQLiteDatabase sqlitedatabase)
        {
            String as[] = ChatHistoryProvider.getTableNames();
            String as1[] = ChatHistoryProvider.getTableSQLs();
            for(int i = 0; i < as.length; i++)
            {
                FacebookDatabaseHelper.dropSQLTableOrView(sqlitedatabase, as[i]);
                sqlitedatabase.execSQL(as1[i]);
            }

            sqlitedatabase.execSQL((new StringBuilder()).append("CREATE INDEX IF NOT EXISTS CHAT_INDEX ON ").append(as[0]).append(" ( ").append("friend_uid").append(" ) ").toString());
        }

    }
;
    private static DatabaseUpdater recreateConnectionTables = new DatabaseUpdater() {

        public void update(SQLiteDatabase sqlitedatabase)
        {
            String as[] = ConnectionsProvider.getTableNames();
            for(int i = 0; i < as.length; i++)
                FacebookDatabaseHelper.dropSQLTableOrView(sqlitedatabase, as[i]);

            String as1[] = ConnectionsProvider.getViewNames();
            for(int j = 0; j < as1.length; j++)
                FacebookDatabaseHelper.dropSQLTableOrView(sqlitedatabase, as1[j]);

            String as2[] = ConnectionsProvider.getTableSQLs();
            for(int k = 0; k < as2.length; k++)
                sqlitedatabase.execSQL(as2[k]);

        }

    }
;
    private static DatabaseUpdater recreateEventsTables = new DatabaseUpdater() {

        public void update(SQLiteDatabase sqlitedatabase)
        {
            String as[] = EventsProvider.getTableNames();
            for(int i = 0; i < as.length; i++)
                FacebookDatabaseHelper.dropSQLTableOrView(sqlitedatabase, as[i]);

            String as1[] = EventsProvider.getTableSQLs();
            for(int j = 0; j < as1.length; j++)
                sqlitedatabase.execSQL(as1[j]);

        }

    }
;
    private static DatabaseUpdater recreateKeyValueTables = new DatabaseUpdater() {

        public void update(SQLiteDatabase sqlitedatabase)
        {
            String as[] = KeyValueProvider.getTableNames();
            for(int i = 0; i < as.length; i++)
                FacebookDatabaseHelper.dropSQLTableOrView(sqlitedatabase, as[i]);

            String as1[] = KeyValueProvider.getTableSQLs();
            for(int j = 0; j < as1.length; j++)
                sqlitedatabase.execSQL(as1[j]);

        }

    }
;
    private static DatabaseUpdater recreateLoggingTables = new DatabaseUpdater() {

        public void update(SQLiteDatabase sqlitedatabase)
        {
            String as[] = LoggingProvider.getTableNames();
            for(int i = 0; i < as.length; i++)
                FacebookDatabaseHelper.dropSQLTableOrView(sqlitedatabase, as[i]);

            String as1[] = LoggingProvider.getTableSQLs();
            for(int j = 0; j < as1.length; j++)
                sqlitedatabase.execSQL(as1[j]);

        }

    }
;
    private static DatabaseUpdater recreateMailboxTables = new DatabaseUpdater() {

        public void update(SQLiteDatabase sqlitedatabase)
        {
            String as[] = MailboxProvider.getTableNames();
            for(int i = 0; i < as.length; i++)
                FacebookDatabaseHelper.dropSQLTableOrView(sqlitedatabase, as[i]);

            String as1[] = MailboxProvider.getViewNames();
            for(int j = 0; j < as1.length; j++)
                FacebookDatabaseHelper.dropSQLTableOrView(sqlitedatabase, as1[j]);

            String as2[] = MailboxProvider.getTableSQLs();
            for(int k = 0; k < as2.length; k++)
                sqlitedatabase.execSQL(as2[k]);

        }

    }
;
    private static DatabaseUpdater recreateNotificationsTables = new DatabaseUpdater() {

        public void update(SQLiteDatabase sqlitedatabase)
        {
            FacebookDatabaseHelper.dropSQLTableOrView(sqlitedatabase, NotificationsProvider.getTableName());
            sqlitedatabase.execSQL(NotificationsProvider.getTableSQL());
        }

    }
;
    private static DatabaseUpdater recreatePagesTables = new DatabaseUpdater() {

        public void update(SQLiteDatabase sqlitedatabase)
        {
            String as[] = PagesProvider.getTableNames();
            for(int i = 0; i < as.length; i++)
                FacebookDatabaseHelper.dropSQLTableOrView(sqlitedatabase, as[i]);

            String as1[] = PagesProvider.getTableSQLs();
            for(int j = 0; j < as1.length; j++)
                sqlitedatabase.execSQL(as1[j]);

        }

    }
;
    private static DatabaseUpdater recreatePhotosTables = new DatabaseUpdater() {

        public void update(SQLiteDatabase sqlitedatabase)
        {
            String as[] = PhotosProvider.getTableNames();
            for(int i = 0; i < as.length; i++)
                FacebookDatabaseHelper.dropSQLTableOrView(sqlitedatabase, as[i]);

            String as1[] = PhotosProvider.getTableSQLs();
            for(int j = 0; j < as1.length; j++)
                sqlitedatabase.execSQL(as1[j]);

        }

    }
;
    private static DatabaseUpdater recreateUserStatusTables = new DatabaseUpdater() {

        public void update(SQLiteDatabase sqlitedatabase)
        {
            FacebookDatabaseHelper.dropSQLTableOrView(sqlitedatabase, UserStatusesProvider.getTableName());
            sqlitedatabase.execSQL(UserStatusesProvider.getTableSQL());
        }

    }
;
    private static DatabaseUpdater recreateUserValuesTables = new DatabaseUpdater() {

        public void update(SQLiteDatabase sqlitedatabase)
        {
            FacebookDatabaseHelper.dropSQLTableOrView(sqlitedatabase, UserValuesProvider.getTableName());
            sqlitedatabase.execSQL(UserValuesProvider.getTableSQL());
        }

    }
;
    private final Context mContext;



}
