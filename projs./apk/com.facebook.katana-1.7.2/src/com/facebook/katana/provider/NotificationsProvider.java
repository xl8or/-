// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NotificationsProvider.java

package com.facebook.katana.provider;

import android.content.*;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.List;

// Referenced classes of package com.facebook.katana.provider:
//            FacebookDatabaseHelper

public class NotificationsProvider extends ContentProvider
{
    public static final class Columns
        implements BaseColumns
    {

        public static final String APP_ID = "app_id";
        public static final String APP_IMAGE = "app_image";
        public static final String BODY = "body";
        public static final String CREATED_TIME = "created";
        public static final String DEFAULT_SORT_ORDER = "created DESC";
        public static final String HREF = "href";
        public static final String IS_READ = "is_read";
        public static final String NOTIFICATION_ID = "notif_id";
        public static final String OBJECT_ID = "object_id";
        public static final String OBJECT_TYPE = "object_type";
        public static final String SENDER_ID = "sender_id";
        public static final String SENDER_IMAGE_URL = "sender_url";
        public static final String SENDER_NAME = "sender_name";
        public static final String TITLE = "title";

        public Columns()
        {
        }
    }


    public NotificationsProvider()
    {
    }

    public static String getTableName()
    {
        return "notifications";
    }

    public static String getTableSQL()
    {
        return "CREATE TABLE notifications (_id INTEGER PRIMARY KEY,notif_id INT,app_id INT,sender_id INT,sender_name TEXT,sender_url TEXT,created INT,title TEXT,body TEXT,href TEXT,is_read INT,app_image TEXT,object_id TEXT,object_type TEXT);";
    }

    public int bulkInsert(Uri uri, ContentValues acontentvalues[])
    {
        int i = 0;
        if(URL_MATCHER.match(uri) != 1)
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        for(int j = 0; j < acontentvalues.length; j++)
            if(sqlitedatabase.insert("notifications", "notif_id", acontentvalues[j]) > 0L)
                i++;

        if(i > 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
            return i;
        } else
        {
            throw new SQLException((new StringBuilder()).append("Failed to insert row into ").append(uri).toString());
        }
    }

    public int delete(Uri uri, String s, String as[])
    {
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        URL_MATCHER.match(uri);
        JVM INSTR tableswitch 1 2: default 40
    //                   1 67
    //                   2 93;
           goto _L1 _L2 _L3
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        int i = sqlitedatabase.delete("notifications", s, as);
_L5:
        getContext().getContentResolver().notifyChange(uri, null);
        return i;
_L3:
        String s1 = (String)uri.getPathSegments().get(1);
        i = sqlitedatabase.delete("notifications", (new StringBuilder()).append("_id=").append(s1).toString(), null);
        if(true) goto _L5; else goto _L4
_L4:
    }

    public String getType(Uri uri)
    {
        switch(URL_MATCHER.match(uri))
        {
        default:
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());

        case 1: // '\001'
        case 2: // '\002'
            return "vnd.android.cursor.dir/vnd.facebook.katana.notifications";
        }
    }

    public Uri insert(Uri uri, ContentValues contentvalues)
    {
        ContentValues contentvalues1;
        if(contentvalues != null)
            contentvalues1 = new ContentValues(contentvalues);
        else
            contentvalues1 = new ContentValues();
        if(URL_MATCHER.match(uri) != 1)
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
        long l = mDbHelper.getWritableDatabase().insert("notifications", "notif_id", contentvalues1);
        if(l > 0L)
        {
            Uri uri1 = Uri.withAppendedPath(CONTENT_URI, Long.valueOf(l).toString());
            getContext().getContentResolver().notifyChange(uri, null);
            return uri1;
        } else
        {
            throw new SQLException((new StringBuilder()).append("Failed to insert row into ").append(uri).toString());
        }
    }

    public boolean onCreate()
    {
        mDbHelper = FacebookDatabaseHelper.getDatabaseHelper(getContext());
        boolean flag;
        if(mDbHelper.getReadableDatabase() != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public Cursor query(Uri uri, String as[], String s, String as1[], String s1)
    {
        SQLiteQueryBuilder sqlitequerybuilder;
        sqlitequerybuilder = new SQLiteQueryBuilder();
        switch(URL_MATCHER.match(uri))
        {
        default:
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());

        case 2: // '\002'
            break MISSING_BLOCK_LABEL_134;

        case 1: // '\001'
            sqlitequerybuilder.setTables("notifications");
            sqlitequerybuilder.setProjectionMap(NOTIFICATIONS_PROJECTION_MAP);
            break;
        }
_L1:
        String s2;
        Cursor cursor;
        if(TextUtils.isEmpty(s1))
            s2 = "created DESC";
        else
            s2 = s1;
        cursor = sqlitequerybuilder.query(mDbHelper.getReadableDatabase(), as, s, as1, null, null, s2);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
        sqlitequerybuilder.setTables("notifications");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("_id=").append((String)uri.getPathSegments().get(1)).toString());
          goto _L1
    }

    public int update(Uri uri, ContentValues contentvalues, String s, String as[])
    {
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        URL_MATCHER.match(uri);
        JVM INSTR tableswitch 1 2: default 40
    //                   1 67
    //                   2 95;
           goto _L1 _L2 _L3
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        int i = sqlitedatabase.update("notifications", contentvalues, s, as);
_L5:
        getContext().getContentResolver().notifyChange(uri, null);
        return i;
_L3:
        String s1 = (String)uri.getPathSegments().get(1);
        i = sqlitedatabase.update("notifications", contentvalues, (new StringBuilder()).append("_id=").append(s1).toString(), null);
        if(true) goto _L5; else goto _L4
_L4:
    }

    private static final String AUTHORITY = "com.facebook.katana.provider.NotificationsProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.NotificationsProvider/notifications");
    private static final int NOTIFICATIONS = 1;
    private static final HashMap NOTIFICATIONS_PROJECTION_MAP;
    private static final String NOTIFICATIONS_TABLE = "notifications";
    private static final int NOTIFICATION_ID = 2;
    private static final String SQL_NOTIFICATIONS = "CREATE TABLE notifications (_id INTEGER PRIMARY KEY,notif_id INT,app_id INT,sender_id INT,sender_name TEXT,sender_url TEXT,created INT,title TEXT,body TEXT,href TEXT,is_read INT,app_image TEXT,object_id TEXT,object_type TEXT);";
    private static final UriMatcher URL_MATCHER;
    private FacebookDatabaseHelper mDbHelper;

    static 
    {
        URL_MATCHER = new UriMatcher(-1);
        URL_MATCHER.addURI("com.facebook.katana.provider.NotificationsProvider", "notifications", 1);
        URL_MATCHER.addURI("com.facebook.katana.provider.NotificationsProvider", "notifications/#", 2);
        NOTIFICATIONS_PROJECTION_MAP = new HashMap();
        NOTIFICATIONS_PROJECTION_MAP.put("_id", "_id");
        NOTIFICATIONS_PROJECTION_MAP.put("app_id", "app_id");
        NOTIFICATIONS_PROJECTION_MAP.put("notif_id", "notif_id");
        NOTIFICATIONS_PROJECTION_MAP.put("sender_id", "sender_id");
        NOTIFICATIONS_PROJECTION_MAP.put("sender_name", "sender_name");
        NOTIFICATIONS_PROJECTION_MAP.put("sender_url", "sender_url");
        NOTIFICATIONS_PROJECTION_MAP.put("created", "created");
        NOTIFICATIONS_PROJECTION_MAP.put("title", "title");
        NOTIFICATIONS_PROJECTION_MAP.put("body", "body");
        NOTIFICATIONS_PROJECTION_MAP.put("href", "href");
        NOTIFICATIONS_PROJECTION_MAP.put("is_read", "is_read");
        NOTIFICATIONS_PROJECTION_MAP.put("app_image", "app_image");
        NOTIFICATIONS_PROJECTION_MAP.put("object_id", "object_id");
        NOTIFICATIONS_PROJECTION_MAP.put("object_type", "object_type");
    }
}
