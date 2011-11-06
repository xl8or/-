// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LoggingProvider.java

package com.facebook.katana.provider;

import android.content.*;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import java.util.HashMap;

// Referenced classes of package com.facebook.katana.provider:
//            FacebookDatabaseHelper

public class LoggingProvider extends ContentProvider
{
    public static final class SessionColumns
        implements BaseColumns
    {

        public static final String API_LOG = "api_log";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.facebook.logresult";
        public static final String DEFAULT_SORT_ORDER = "session_id ASC";
        public static final String END_TIME = "end_time";
        public static final String SESSION_ID = "session_id";
        public static final String SPECIFIC_ID = "perf_sessions._id";
        public static final String START_TIME = "start_time";

        public SessionColumns()
        {
        }
    }


    public LoggingProvider()
    {
    }

    static String[] getTableNames()
    {
        String as[] = new String[1];
        as[0] = "perf_sessions";
        return as;
    }

    static String[] getTableSQLs()
    {
        String as[] = new String[1];
        as[0] = "CREATE TABLE perf_sessions (_id INTEGER PRIMARY KEY,session_id INT,start_time INT,end_time INT,api_log TEXT);";
        return as;
    }

    private Uri insertSessionValues(Uri uri, ContentValues contentvalues)
        throws SQLException
    {
        long l = mDbHelper.getWritableDatabase().insert("perf_sessions", "session_id", contentvalues);
        if(l > 0L)
        {
            Uri uri1 = Uri.withAppendedPath(SESSIONS_CONTENT_URI, Long.valueOf(l).toString());
            getContext().getContentResolver().notifyChange(uri, null);
            return uri1;
        } else
        {
            throw new SQLException((new StringBuilder()).append("Failed to insert row into ").append(uri).toString());
        }
    }

    private int insertSessionsValues(Uri uri, ContentValues acontentvalues[])
        throws SQLException
    {
        int i = 0;
        int j = acontentvalues.length;
        for(int k = 0; k < j; k++)
        {
            ContentValues contentvalues = acontentvalues[k];
            if(mDbHelper.getWritableDatabase().insert("perf_sessions", "session_id", contentvalues) > 0L)
                i++;
        }

        if(i > 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
            return i;
        } else
        {
            throw new SQLException((new StringBuilder()).append("Failed to insert rows into ").append(uri).toString());
        }
    }

    public int bulkInsert(Uri uri, ContentValues acontentvalues[])
    {
        switch(URL_MATCHER.match(uri))
        {
        default:
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());

        case 1: // '\001'
            return insertSessionsValues(uri, acontentvalues);
        }
    }

    public int delete(Uri uri, String s, String as[])
    {
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        int i;
        switch(URL_MATCHER.match(uri))
        {
        default:
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());

        case 1: // '\001'
            i = sqlitedatabase.delete("perf_sessions", s, as);
            break;
        }
        if(i > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return i;
    }

    public String getType(Uri uri)
    {
        switch(URL_MATCHER.match(uri))
        {
        default:
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());

        case 1: // '\001'
        case 2: // '\002'
            return "vnd.android.cursor.dir/vnd.facebook.logresult";
        }
    }

    public Uri insert(Uri uri, ContentValues contentvalues)
    {
        ContentValues contentvalues1;
        if(contentvalues != null)
            contentvalues1 = new ContentValues(contentvalues);
        else
            contentvalues1 = new ContentValues();
        switch(URL_MATCHER.match(uri))
        {
        default:
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());

        case 1: // '\001'
            return insertSessionValues(uri, contentvalues1);
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
        SQLiteQueryBuilder sqlitequerybuilder = new SQLiteQueryBuilder();
        switch(URL_MATCHER.match(uri))
        {
        default:
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());

        case 1: // '\001'
            sqlitequerybuilder.setTables("perf_sessions");
            break;
        }
        sqlitequerybuilder.setProjectionMap(PERF_SESSIONS_PROJECTION_MAP);
        Cursor cursor = sqlitequerybuilder.query(mDbHelper.getReadableDatabase(), as, s, as1, null, null, "session_id ASC");
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    public int update(Uri uri, ContentValues contentvalues, String s, String as[])
    {
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        int i;
        switch(URL_MATCHER.match(uri))
        {
        default:
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());

        case 1: // '\001'
            i = sqlitedatabase.update("perf_sessions", contentvalues, s, as);
            break;
        }
        if(i > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return i;
    }

    private static final String AUTHORITY = "com.facebook.katana.provider.LoggingProvider";
    private static final String CONTENT_SCHEME = "content://";
    private static final int PERF_SESSIONS = 1;
    public static final HashMap PERF_SESSIONS_PROJECTION_MAP;
    private static final int PERF_SESSIONS_SID = 2;
    private static final String SESSIONS_BASE_URI = "content://com.facebook.katana.provider.LoggingProvider/perf_sessions";
    public static final Uri SESSIONS_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.LoggingProvider/perf_sessions");
    private static final String SESSIONS_PATH = "/sid";
    public static final Uri SESSIONS_SID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.LoggingProvider/perf_sessions/sid");
    private static final String SQL_PERF_SESSIONS = "CREATE TABLE perf_sessions (_id INTEGER PRIMARY KEY,session_id INT,start_time INT,end_time INT,api_log TEXT);";
    private static final String TABLE_PERF_SESSIONS = "perf_sessions";
    private static final UriMatcher URL_MATCHER;
    private FacebookDatabaseHelper mDbHelper;

    static 
    {
        URL_MATCHER = new UriMatcher(-1);
        URL_MATCHER.addURI("com.facebook.katana.provider.LoggingProvider", "perf_sessions", 1);
        URL_MATCHER.addURI("com.facebook.katana.provider.LoggingProvider", "perf_sessions/#", 2);
        PERF_SESSIONS_PROJECTION_MAP = new HashMap();
        PERF_SESSIONS_PROJECTION_MAP.put("session_id", "session_id");
        PERF_SESSIONS_PROJECTION_MAP.put("start_time", "start_time");
        PERF_SESSIONS_PROJECTION_MAP.put("end_time", "end_time");
        PERF_SESSIONS_PROJECTION_MAP.put("api_log", "api_log");
    }
}
