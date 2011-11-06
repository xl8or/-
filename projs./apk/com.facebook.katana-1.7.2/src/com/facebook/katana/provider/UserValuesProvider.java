// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserValuesProvider.java

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

public class UserValuesProvider extends ContentProvider
{
    public static final class Columns
        implements BaseColumns
    {

        public static final String DEFAULT_SORT_ORDER = "name DESC";
        public static final String PROP_NAME = "name";
        public static final String PROP_VALUE = "value";

        public Columns()
        {
        }
    }


    public UserValuesProvider()
    {
    }

    public static String getTableName()
    {
        return "user_values";
    }

    public static String getTableSQL()
    {
        return "CREATE TABLE user_values (_id INTEGER PRIMARY KEY,name TEXT,value TEXT);";
    }

    public int delete(Uri uri, String s, String as[])
    {
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        URI_MATCHER.match(uri);
        JVM INSTR tableswitch 1 3: default 44
    //                   1 71
    //                   2 98
    //                   3 146;
           goto _L1 _L2 _L3 _L4
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        int i = sqlitedatabase.delete("user_values", s, as);
_L6:
        getContext().getContentResolver().notifyChange(uri, null, false);
        return i;
_L3:
        String s2 = (String)uri.getPathSegments().get(1);
        i = sqlitedatabase.delete("user_values", (new StringBuilder()).append("_id=").append(s2).toString(), null);
        continue; /* Loop/switch isn't completed */
_L4:
        String s1 = (String)uri.getPathSegments().get(1);
        i = sqlitedatabase.delete("user_values", (new StringBuilder()).append("name=").append(s1).toString(), null);
        if(true) goto _L6; else goto _L5
_L5:
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
            return "vnd.android.cursor.item/vnd.facebook.katana.uservalues";
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
        long l = mDbHelper.getWritableDatabase().insert("user_values", "name", contentvalues1);
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
        SQLiteQueryBuilder sqlitequerybuilder = new SQLiteQueryBuilder();
        URI_MATCHER.match(uri);
        JVM INSTR tableswitch 1 3: default 44
    //                   1 71
    //                   2 138
    //                   3 184;
           goto _L1 _L2 _L3 _L4
_L4:
        break MISSING_BLOCK_LABEL_184;
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        sqlitequerybuilder.setTables("user_values");
        sqlitequerybuilder.setProjectionMap(USER_VALUES_PROJECTION_MAP);
_L5:
        String s2;
        Cursor cursor;
        if(TextUtils.isEmpty(s1))
            s2 = "name DESC";
        else
            s2 = s1;
        cursor = sqlitequerybuilder.query(mDbHelper.getReadableDatabase(), as, s, as1, null, null, s2);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
_L3:
        sqlitequerybuilder.setTables("user_values");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("_id=").append((String)uri.getPathSegments().get(1)).toString());
          goto _L5
        sqlitequerybuilder.setTables("user_values");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("name='").append((String)uri.getPathSegments().get(2)).append("'").toString());
          goto _L5
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
        int i = sqlitedatabase.update("user_values", contentvalues, s, as);
_L6:
        getContext().getContentResolver().notifyChange(uri, null, false);
        return i;
_L3:
        String s2 = (String)uri.getPathSegments().get(1);
        i = sqlitedatabase.update("user_values", contentvalues, (new StringBuilder()).append("_id=").append(s2).toString(), null);
        continue; /* Loop/switch isn't completed */
_L4:
        String s1 = (String)uri.getPathSegments().get(2);
        i = sqlitedatabase.update("user_values", contentvalues, (new StringBuilder()).append("name='").append(s1).append("'").toString(), null);
        if(true) goto _L6; else goto _L5
_L5:
    }

    private static final String AUTHORITY = "com.facebook.katana.provider.UserValuesProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.UserValuesProvider/user_values");
    public static final Uri NAME_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.UserValuesProvider/user_values/name");
    private static final String SQL_USER_VALUES = "CREATE TABLE user_values (_id INTEGER PRIMARY KEY,name TEXT,value TEXT);";
    private static final String TABLE_USER_VALUES = "user_values";
    private static final UriMatcher URI_MATCHER;
    private static final int USER_VALUES = 1;
    private static final HashMap USER_VALUES_PROJECTION_MAP;
    private static final int USER_VALUE_ID = 2;
    private static final int USER_VALUE_NAME = 3;
    private FacebookDatabaseHelper mDbHelper;

    static 
    {
        URI_MATCHER = new UriMatcher(-1);
        URI_MATCHER.addURI("com.facebook.katana.provider.UserValuesProvider", "user_values", 1);
        URI_MATCHER.addURI("com.facebook.katana.provider.UserValuesProvider", "user_values/#", 2);
        URI_MATCHER.addURI("com.facebook.katana.provider.UserValuesProvider", "user_values/name/*", 3);
        USER_VALUES_PROJECTION_MAP = new HashMap();
        USER_VALUES_PROJECTION_MAP.put("_id", "_id");
        USER_VALUES_PROJECTION_MAP.put("name", "name");
        USER_VALUES_PROJECTION_MAP.put("value", "value");
    }
}
