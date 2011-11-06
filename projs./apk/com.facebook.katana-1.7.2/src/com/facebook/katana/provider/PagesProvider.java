// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PagesProvider.java

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

public class PagesProvider extends ContentProvider
{
    public static final class DefaultPageImagesColumns
        implements BaseColumns
    {

        public static final String DEFAULT_SORT_ORDER = "_id ASC";
        public static final String PAGE_IMAGE = "pic";

        public DefaultPageImagesColumns()
        {
        }
    }

    public static final class SearchResultColumns extends PageColumns
    {

        public static final String DEFAULT_SORT_ORDER = "_id ASC";

        public SearchResultColumns()
        {
        }
    }

    public static class PageColumns
        implements BaseColumns
    {

        public static final String DEFAULT_SORT_ORDER = "display_name ASC";
        public static final String PAGE_DISPLAY_NAME = "display_name";
        public static final String PAGE_ID = "page_id";
        public static final String PAGE_IMAGE_URL = "pic";

        public PageColumns()
        {
        }
    }


    public PagesProvider()
    {
    }

    public static String[] getTableNames()
    {
        String as[] = new String[2];
        as[0] = "page_search_results";
        as[1] = "default_page_images";
        return as;
    }

    public static String[] getTableSQLs()
    {
        String as[] = new String[2];
        as[0] = "CREATE TABLE page_search_results (_id INTEGER PRIMARY KEY,page_id INT,display_name TEXT,pic TEXT);";
        as[1] = "CREATE TABLE default_page_images (_id INTEGER PRIMARY KEY,pic BLOB);";
        return as;
    }

    public int bulkInsert(Uri uri, ContentValues acontentvalues[])
    {
        int i = 0;
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        int j;
        switch(URL_MATCHER.match(uri))
        {
        default:
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());

        case 31: // '\037'
            j = 0;
            break;
        }
        for(; j < acontentvalues.length; j++)
            if(sqlitedatabase.insert("page_search_results", "page_id", acontentvalues[j]) > 0L)
                i++;

        if(i > 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
            return i;
        } else
        {
            throw new SQLException((new StringBuilder()).append("Failed to insert rows into ").append(uri).toString());
        }
    }

    public int delete(Uri uri, String s, String as[])
    {
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        URL_MATCHER.match(uri);
        JVM INSTR tableswitch 31 33: default 44
    //                   31 71
    //                   32 102
    //                   33 150;
           goto _L1 _L2 _L3 _L4
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        int i = sqlitedatabase.delete("page_search_results", s, as);
_L6:
        if(i > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return i;
_L3:
        String s2 = (String)uri.getPathSegments().get(1);
        i = sqlitedatabase.delete("page_search_results", (new StringBuilder()).append("_id=").append(s2).toString(), null);
        continue; /* Loop/switch isn't completed */
_L4:
        String s1 = (String)uri.getPathSegments().get(2);
        i = sqlitedatabase.delete("page_search_results", (new StringBuilder()).append("page_id=").append(s1).toString(), null);
        if(true) goto _L6; else goto _L5
_L5:
    }

    public String getType(Uri uri)
    {
        switch(URL_MATCHER.match(uri))
        {
        default:
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());

        case 31: // '\037'
        case 32: // ' '
        case 33: // '!'
            return "vnd.android.cursor.item/vnd.com.facebook.katana.provider.search_results";
        }
    }

    public Uri insert(Uri uri, ContentValues contentvalues)
    {
        ContentValues contentvalues1;
        SQLiteDatabase sqlitedatabase;
        if(contentvalues != null)
            contentvalues1 = new ContentValues(contentvalues);
        else
            contentvalues1 = new ContentValues();
        sqlitedatabase = mDbHelper.getWritableDatabase();
        long l;
        switch(URL_MATCHER.match(uri))
        {
        default:
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());

        case 31: // '\037'
            l = sqlitedatabase.insert("page_search_results", "display_name", contentvalues1);
            break;
        }
        if(l > 0L)
        {
            Uri uri1 = Uri.withAppendedPath(SEARCH_RESULTS_CONTENT_URI, Long.valueOf(l).toString());
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
        SQLiteQueryBuilder sqlitequerybuilder = new SQLiteQueryBuilder();
        URL_MATCHER.match(uri);
        JVM INSTR tableswitch 31 33: default 44
    //                   31 71
    //                   32 143
    //                   33 201;
           goto _L1 _L2 _L3 _L4
_L4:
        break MISSING_BLOCK_LABEL_201;
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        String s2;
        sqlitequerybuilder.setTables("page_search_results");
        sqlitequerybuilder.setProjectionMap(SEARCH_RESULTS_PROJECTION_MAP);
        s2 = "_id ASC";
_L5:
        String s3;
        Cursor cursor;
        if(TextUtils.isEmpty(s1))
            s3 = s2;
        else
            s3 = s1;
        cursor = sqlitequerybuilder.query(mDbHelper.getReadableDatabase(), as, s, as1, null, null, s3, null);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
_L3:
        sqlitequerybuilder.setTables("page_search_results");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("_id=").append((String)uri.getPathSegments().get(1)).toString());
        sqlitequerybuilder.setProjectionMap(SEARCH_RESULTS_PROJECTION_MAP);
        s2 = "_id ASC";
          goto _L5
        sqlitequerybuilder.setTables("page_search_results");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("page_id=").append((String)uri.getPathSegments().get(2)).toString());
        sqlitequerybuilder.setProjectionMap(SEARCH_RESULTS_PROJECTION_MAP);
        s2 = "_id ASC";
          goto _L5
    }

    public int update(Uri uri, ContentValues contentvalues, String s, String as[])
    {
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        URL_MATCHER.match(uri);
        JVM INSTR tableswitch 31 33: default 44
    //                   31 71
    //                   32 104
    //                   33 153;
           goto _L1 _L2 _L3 _L4
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        int i = sqlitedatabase.update("page_search_results", contentvalues, s, as);
_L6:
        if(i > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return i;
_L3:
        String s2 = (String)uri.getPathSegments().get(1);
        i = sqlitedatabase.update("page_search_results", contentvalues, (new StringBuilder()).append("_id=").append(s2).toString(), null);
        continue; /* Loop/switch isn't completed */
_L4:
        String s1 = (String)uri.getPathSegments().get(2);
        i = sqlitedatabase.update("page_search_results", contentvalues, (new StringBuilder()).append("page_id=").append(s1).toString(), null);
        if(true) goto _L6; else goto _L5
_L5:
    }

    private static final String AUTHORITY = "com.facebook.katana.provider.PagesProvider";
    private static final String BASE_CONTENT_URI = "content://com.facebook.katana.provider.PagesProvider/";
    public static final Uri DEFAULT_PAGE_IMAGE_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.PagesProvider/default_page_images");
    private static final HashMap DEFAULT_PAGE_IMAGE_PROJECTION_MAP;
    private static final String DEFAULT_PAGE_IMAGE_TABLE = "default_page_images";
    private static final int SEARCH_RESULTS = 31;
    public static final Uri SEARCH_RESULTS_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.PagesProvider/page_search_results");
    private static final HashMap SEARCH_RESULTS_PROJECTION_MAP;
    private static final String SEARCH_RESULTS_TABLE = "page_search_results";
    private static final int SEARCH_RESULT_ID = 32;
    private static final int SEARCH_RESULT_UID = 33;
    public static final Uri SEARCH_RESULT_UID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.PagesProvider/page_search_results/uid");
    private static final String SQL_DEFAULT_PAGE_IMAGE = "CREATE TABLE default_page_images (_id INTEGER PRIMARY KEY,pic BLOB);";
    private static final String SQL_SEARCH_RESULTS = "CREATE TABLE page_search_results (_id INTEGER PRIMARY KEY,page_id INT,display_name TEXT,pic TEXT);";
    private static final UriMatcher URL_MATCHER;
    private FacebookDatabaseHelper mDbHelper;

    static 
    {
        URL_MATCHER = new UriMatcher(-1);
        URL_MATCHER.addURI("com.facebook.katana.provider.PagesProvider", "page_search_results", 31);
        URL_MATCHER.addURI("com.facebook.katana.provider.PagesProvider", "page_search_results/#", 32);
        URL_MATCHER.addURI("com.facebook.katana.provider.PagesProvider", "page_search_results/uid/#", 33);
        DEFAULT_PAGE_IMAGE_PROJECTION_MAP = new HashMap();
        DEFAULT_PAGE_IMAGE_PROJECTION_MAP.put("_id", "_id");
        DEFAULT_PAGE_IMAGE_PROJECTION_MAP.put("pic", "pic");
        SEARCH_RESULTS_PROJECTION_MAP = new HashMap();
        SEARCH_RESULTS_PROJECTION_MAP.put("_id", "_id");
        SEARCH_RESULTS_PROJECTION_MAP.put("page_id", "page_id");
        SEARCH_RESULTS_PROJECTION_MAP.put("display_name", "display_name");
        SEARCH_RESULTS_PROJECTION_MAP.put("pic", "pic");
    }
}
