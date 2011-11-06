// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserStatusesProvider.java

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

public class UserStatusesProvider extends ContentProvider
{
    public static final class Columns
        implements BaseColumns
    {

        public static final String DEFAULT_SORT_ORDER = "timestamp DESC";
        public static final String MESSAGE = "message";
        public static final String TIMESTAMP = "timestamp";
        public static final String USER_DISPLAY_NAME = "display_name";
        public static final String USER_FIRST_NAME = "first_name";
        public static final String USER_ID = "user_id";
        public static final String USER_LAST_NAME = "last_name";
        public static final String USER_PIC = "user_pic";

        public Columns()
        {
        }
    }


    public UserStatusesProvider()
    {
    }

    public static String getTableName()
    {
        return "user_statuses";
    }

    public static String getTableSQL()
    {
        return "CREATE TABLE user_statuses (_id INTEGER PRIMARY KEY,user_id INT,first_name TEXT,last_name TEXT,display_name TEXT,user_pic TEXT,timestamp INT,message TEXT);";
    }

    public int delete(Uri uri, String s, String as[])
    {
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        URL_MATCHER.match(uri);
        JVM INSTR tableswitch 1 2: default 40
    //                   1 67
    //                   2 94;
           goto _L1 _L2 _L3
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        int i = sqlitedatabase.delete("user_statuses", s, as);
_L5:
        getContext().getContentResolver().notifyChange(uri, null, false);
        return i;
_L3:
        String s1 = (String)uri.getPathSegments().get(1);
        i = sqlitedatabase.delete("user_statuses", (new StringBuilder()).append("_id=").append(s1).toString(), null);
        if(true) goto _L5; else goto _L4
_L4:
    }

    public String getType(Uri uri)
    {
        URL_MATCHER.match(uri);
        JVM INSTR tableswitch 1 2: default 28
    //                   1 55
    //                   2 60;
           goto _L1 _L2 _L3
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        String s = "vnd.android.cursor.dir/vnd.facebook.katana.userstatuses";
_L5:
        return s;
_L3:
        s = "vnd.android.cursor.item/vnd.facebook.katana.userstatuses";
        if(true) goto _L5; else goto _L4
_L4:
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
        long l = mDbHelper.getWritableDatabase().insert("user_statuses", "user_id", contentvalues1);
        if(l > 0L)
        {
            Uri uri1 = Uri.withAppendedPath(CONTENT_URI, Long.valueOf(l).toString());
            getContext().getContentResolver().notifyChange(uri, null, false);
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
            sqlitequerybuilder.setTables("user_statuses");
            sqlitequerybuilder.setProjectionMap(USER_STATUSES_PROJECTION_MAP);
            break;
        }
_L1:
        String s2;
        Cursor cursor;
        if(TextUtils.isEmpty(s1))
            s2 = "timestamp DESC";
        else
            s2 = s1;
        cursor = sqlitequerybuilder.query(mDbHelper.getReadableDatabase(), as, s, as1, null, null, s2);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
        sqlitequerybuilder.setTables("user_statuses");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("_id=").append((String)uri.getPathSegments().get(1)).toString());
          goto _L1
    }

    public int update(Uri uri, ContentValues contentvalues, String s, String as[])
    {
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        URL_MATCHER.match(uri);
        JVM INSTR tableswitch 1 2: default 40
    //                   1 67
    //                   2 96;
           goto _L1 _L2 _L3
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        int i = sqlitedatabase.update("user_statuses", contentvalues, s, as);
_L5:
        getContext().getContentResolver().notifyChange(uri, null, false);
        return i;
_L3:
        String s1 = (String)uri.getPathSegments().get(1);
        i = sqlitedatabase.update("user_statuses", contentvalues, (new StringBuilder()).append("_id=").append(s1).toString(), null);
        if(true) goto _L5; else goto _L4
_L4:
    }

    private static final String AUTHORITY = "com.facebook.katana.provider.UserStatusesProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.UserStatusesProvider/user_statuses");
    private static final String SQL_USER_STATUSES = "CREATE TABLE user_statuses (_id INTEGER PRIMARY KEY,user_id INT,first_name TEXT,last_name TEXT,display_name TEXT,user_pic TEXT,timestamp INT,message TEXT);";
    private static final UriMatcher URL_MATCHER;
    private static final int USER_STATUSES = 1;
    private static final HashMap USER_STATUSES_PROJECTION_MAP;
    private static final String USER_STATUSES_TABLE = "user_statuses";
    private static final int USER_STATUS_ID = 2;
    private FacebookDatabaseHelper mDbHelper;

    static 
    {
        URL_MATCHER = new UriMatcher(-1);
        URL_MATCHER.addURI("com.facebook.katana.provider.UserStatusesProvider", "user_statuses", 1);
        URL_MATCHER.addURI("com.facebook.katana.provider.UserStatusesProvider", "user_statuses/#", 2);
        USER_STATUSES_PROJECTION_MAP = new HashMap();
        USER_STATUSES_PROJECTION_MAP.put("_id", "_id");
        USER_STATUSES_PROJECTION_MAP.put("user_id", "user_id");
        USER_STATUSES_PROJECTION_MAP.put("first_name", "first_name");
        USER_STATUSES_PROJECTION_MAP.put("last_name", "last_name");
        USER_STATUSES_PROJECTION_MAP.put("display_name", "display_name");
        USER_STATUSES_PROJECTION_MAP.put("user_pic", "user_pic");
        USER_STATUSES_PROJECTION_MAP.put("timestamp", "timestamp");
        USER_STATUSES_PROJECTION_MAP.put("message", "message");
    }
}
