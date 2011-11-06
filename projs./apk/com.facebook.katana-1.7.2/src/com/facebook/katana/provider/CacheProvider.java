// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CacheProvider.java

package com.facebook.katana.provider;

import android.content.*;
import android.database.*;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.List;

// Referenced classes of package com.facebook.katana.provider:
//            FacebookDatabaseHelper

public class CacheProvider extends ContentProvider
{
    public static final class Columns
        implements BaseColumns
    {

        public static final String DEFAULT_SORT_ORDER = "name DESC";
        public static final String PROP_NAME = "name";
        public static final String PROP_TIMESTAMP = "timestamp";
        public static final String PROP_VALUE = "value";

        public Columns()
        {
        }
    }


    public CacheProvider()
    {
    }

    public static String[] getTableNames()
    {
        String as[] = new String[1];
        as[0] = "cache";
        return as;
    }

    public static String[] getTableSQLs()
    {
        String as[] = new String[1];
        as[0] = "CREATE TABLE cache (_id INTEGER PRIMARY KEY,name TEXT,value TEXT,timestamp INTEGER DEFAULT 0);";
        return as;
    }

    public int delete(Uri uri, String s, String as[])
    {
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        URI_MATCHER.match(uri);
        JVM INSTR tableswitch 1 5: default 52
    //                   1 79
    //                   2 106
    //                   3 154
    //                   4 205
    //                   5 273;
           goto _L1 _L2 _L3 _L4 _L5 _L6
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        int j = sqlitedatabase.delete("cache", s, as);
_L8:
        getContext().getContentResolver().notifyChange(uri, null, false);
        return j;
_L3:
        String s4 = (String)uri.getPathSegments().get(1);
        j = sqlitedatabase.delete("cache", (new StringBuilder()).append("_id=").append(s4).toString(), null);
        continue; /* Loop/switch isn't completed */
_L4:
        String s3 = (String)uri.getPathSegments().get(2);
        j = sqlitedatabase.delete("cache", (new StringBuilder()).append("name=").append(DatabaseUtils.sqlEscapeString(s3)).toString(), null);
        continue; /* Loop/switch isn't completed */
_L5:
        String s2 = (String)uri.getPathSegments().get(2);
        Object aobj1[] = new Object[3];
        aobj1[0] = "name";
        aobj1[1] = Integer.valueOf(s2.length());
        aobj1[2] = DatabaseUtils.sqlEscapeString(s2);
        j = sqlitedatabase.delete("cache", String.format("SUBSTR(%s, 1, %d)=%s", aobj1), null);
        continue; /* Loop/switch isn't completed */
_L6:
        String s1 = (String)uri.getPathSegments().get(2);
        int i = Integer.parseInt((String)uri.getPathSegments().get(3));
        Object aobj[] = new Object[6];
        aobj[0] = "name";
        aobj[1] = Integer.valueOf(s1.length());
        aobj[2] = DatabaseUtils.sqlEscapeString(s1);
        aobj[3] = Long.valueOf(System.currentTimeMillis() / 1000L);
        aobj[4] = "timestamp";
        aobj[5] = Integer.valueOf(i);
        j = sqlitedatabase.delete("cache", String.format("SUBSTR(%s, 1, %d)=%s AND (%d-%s > %d)", aobj), null);
        if(true) goto _L8; else goto _L7
_L7:
    }

    public String getType(Uri uri)
    {
        switch(URI_MATCHER.match(uri))
        {
        default:
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());

        case 1: // '\001'
        case 2: // '\002'
        case 3: // '\003'
            return "vnd.android.cursor.item/vnd.facebook.katana.cache";
        }
    }

    public Uri insert(Uri uri, ContentValues contentvalues)
    {
        ContentValues contentvalues1;
        if(contentvalues != null)
            contentvalues1 = new ContentValues(contentvalues);
        else
            contentvalues1 = new ContentValues();
        if(URI_MATCHER.match(uri) != 1)
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
        long l = mDbHelper.getWritableDatabase().insert("cache", "name", contentvalues1);
        if(l > 0L)
        {
            Uri uri1 = Uri.withAppendedPath(CONTENT_URI, Long.toString(l));
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
        SQLiteQueryBuilder sqlitequerybuilder = new SQLiteQueryBuilder();
        URI_MATCHER.match(uri);
        JVM INSTR tableswitch 1 4: default 48
    //                   1 75
    //                   2 143
    //                   3 189
    //                   4 238;
           goto _L1 _L2 _L3 _L4 _L5
_L5:
        break MISSING_BLOCK_LABEL_238;
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        sqlitequerybuilder.setTables("cache");
        sqlitequerybuilder.setProjectionMap(CACHE_PROJECTION_MAP);
_L6:
        String s2;
        Object aobj[];
        String s3;
        Cursor cursor;
        if(TextUtils.isEmpty(s1))
            s3 = "name DESC";
        else
            s3 = s1;
        cursor = sqlitequerybuilder.query(mDbHelper.getReadableDatabase(), as, s, as1, null, null, s3);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
_L3:
        sqlitequerybuilder.setTables("cache");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("_id=").append((String)uri.getPathSegments().get(1)).toString());
          goto _L6
_L4:
        sqlitequerybuilder.setTables("cache");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("name=").append(DatabaseUtils.sqlEscapeString((String)uri.getPathSegments().get(2))).toString());
          goto _L6
        s2 = (String)uri.getPathSegments().get(2);
        sqlitequerybuilder.setTables("cache");
        aobj = new Object[3];
        aobj[0] = "name";
        aobj[1] = Integer.valueOf(s2.length());
        aobj[2] = DatabaseUtils.sqlEscapeString(s2);
        sqlitequerybuilder.appendWhere(String.format("SUBSTR(%s, 1, %d)=%s", aobj));
          goto _L6
    }

    public int update(Uri uri, ContentValues contentvalues, String s, String as[])
    {
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        URI_MATCHER.match(uri);
        JVM INSTR tableswitch 1 3: default 44
    //                   1 71
    //                   2 100
    //                   3 149;
           goto _L1 _L2 _L3 _L4
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        int i = sqlitedatabase.update("cache", contentvalues, s, as);
_L6:
        getContext().getContentResolver().notifyChange(uri, null, false);
        return i;
_L3:
        String s2 = (String)uri.getPathSegments().get(1);
        i = sqlitedatabase.update("cache", contentvalues, (new StringBuilder()).append("_id=").append(s2).toString(), null);
        continue; /* Loop/switch isn't completed */
_L4:
        String s1 = (String)uri.getPathSegments().get(2);
        i = sqlitedatabase.update("cache", contentvalues, (new StringBuilder()).append("name=").append(DatabaseUtils.sqlEscapeString(s1)).toString(), null);
        if(true) goto _L6; else goto _L5
_L5:
    }

    private static final String AUTHORITY = "com.facebook.katana.provider.CacheProvider";
    private static final int CACHE_ID_MATCHER = 2;
    private static final int CACHE_MATCHER = 1;
    private static final int CACHE_NAME_MATCHER = 3;
    private static final int CACHE_PREFIX_MATCHER = 4;
    private static final HashMap CACHE_PROJECTION_MAP;
    private static final int CACHE_SWEEP_PREFIX_MATCHER = 5;
    public static final Uri CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.CacheProvider/cache");
    public static final Uri NAME_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.CacheProvider/cache/name");
    public static final Uri PREFIX_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.CacheProvider/cache/prefix");
    private static final String SQL_CACHE = "CREATE TABLE cache (_id INTEGER PRIMARY KEY,name TEXT,value TEXT,timestamp INTEGER DEFAULT 0);";
    public static final Uri SWEEP_PREFIX_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.CacheProvider/cache/sweep_prefix");
    private static final String TABLE_CACHE = "cache";
    private static final UriMatcher URI_MATCHER;
    private FacebookDatabaseHelper mDbHelper;

    static 
    {
        URI_MATCHER = new UriMatcher(-1);
        URI_MATCHER.addURI("com.facebook.katana.provider.CacheProvider", "cache", 1);
        URI_MATCHER.addURI("com.facebook.katana.provider.CacheProvider", "cache/#", 2);
        URI_MATCHER.addURI("com.facebook.katana.provider.CacheProvider", "cache/name/*", 3);
        URI_MATCHER.addURI("com.facebook.katana.provider.CacheProvider", "cache/prefix/*", 4);
        URI_MATCHER.addURI("com.facebook.katana.provider.CacheProvider", "cache/sweep_prefix/*/#", 5);
        CACHE_PROJECTION_MAP = new HashMap();
        CACHE_PROJECTION_MAP.put("_id", "_id");
        CACHE_PROJECTION_MAP.put("name", "name");
        CACHE_PROJECTION_MAP.put("value", "value");
        CACHE_PROJECTION_MAP.put("timestamp", "timestamp");
    }
}
