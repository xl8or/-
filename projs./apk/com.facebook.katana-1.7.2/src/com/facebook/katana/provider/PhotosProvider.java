// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhotosProvider.java

package com.facebook.katana.provider;

import android.content.*;
import android.database.*;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import java.io.File;
import java.util.HashMap;
import java.util.List;

// Referenced classes of package com.facebook.katana.provider:
//            FacebookDatabaseHelper

public class PhotosProvider extends ContentProvider
{
    public static final class StreamPhotoColumns
        implements BaseColumns
    {

        public static final String DEFAULT_SORT_ORDER = "url DESC";
        public static final String FILENAME = "filename";
        public static final String URL = "url";

        public StreamPhotoColumns()
        {
        }
    }

    public static final class AlbumColumns
        implements BaseColumns
    {

        public static final String ALBUM_ID = "aid";
        public static final String COVER_PHOTO_ID = "cover_pid";
        public static final String COVER_PHOTO_URL = "cover_url";
        public static final String COVER_THUMBNAIL = "thumbnail";
        public static final String CREATED = "created";
        public static final String DEFAULT_SORT_ORDER = "modified DESC";
        public static final String DESCRIPTION = "description";
        public static final String LOCATION = "location";
        public static final String MODIFIED = "modified";
        public static final String NAME = "name";
        public static final String OBJECT_ID = "object_id";
        public static final String OWNER = "owner";
        public static final String SIZE = "size";
        public static final String TYPE = "type";
        public static final String VISIBILITY = "visibility";

        public AlbumColumns()
        {
        }
    }

    public static final class PhotoColumns
        implements BaseColumns
    {

        public static final String ALBUM_ID = "aid";
        public static final String CAPTION = "caption";
        public static final String CREATED = "created";
        public static final String DEFAULT_SORT_ORDER = "position ASC";
        public static final String FILENAME = "filename";
        public static final String OWNER = "owner";
        public static final String PHOTO_ID = "pid";
        public static final String POSITION = "position";
        public static final String SRC = "src";
        public static final String SRC_BIG = "src_big";
        public static final String SRC_SMALL = "src_small";
        public static final String THUMBNAIL = "thumbnail";

        public PhotoColumns()
        {
        }
    }


    public PhotosProvider()
    {
    }

    public static void clearPhotoFiles(Context context, Uri uri)
    {
        ContentResolver contentresolver = context.getContentResolver();
        Cursor cursor = contentresolver.query(uri, PHOTOS_FILENAME_PROJECTION, null, null, null);
        if(cursor != null)
        {
            if(cursor.moveToFirst())
                do
                {
                    String s = cursor.getString(0);
                    if(s != null)
                        (new File(s)).delete();
                } while(cursor.moveToNext());
            cursor.close();
            ContentValues contentvalues = new ContentValues();
            contentvalues.put("filename", (String)null);
            contentresolver.update(uri, contentvalues, null, null);
        }
    }

    private static void deletePhotoFiles(SQLiteDatabase sqlitedatabase, String s, String as[])
    {
        Cursor cursor = sqlitedatabase.query("photos", PHOTOS_FILENAME_PROJECTION, s, as, null, null, null);
        if(cursor != null)
        {
            if(cursor.moveToFirst())
                do
                {
                    String s1 = cursor.getString(0);
                    if(s1 != null)
                        (new File(s1)).delete();
                } while(cursor.moveToNext());
            cursor.close();
        }
    }

    private static void deleteStreamPhotoFiles(SQLiteDatabase sqlitedatabase, String s, String as[])
    {
        Cursor cursor = sqlitedatabase.query("stream_photos", STREAM_FILENAME_PROJECTION, s, as, null, null, null);
        if(cursor != null)
        {
            if(cursor.moveToFirst())
                do
                    (new File(cursor.getString(0))).delete();
                while(cursor.moveToNext());
            cursor.close();
        }
    }

    public static String[] getTableNames()
    {
        String as[] = new String[3];
        as[0] = "photos";
        as[1] = "albums";
        as[2] = "stream_photos";
        return as;
    }

    public static String[] getTableSQLs()
    {
        String as[] = new String[3];
        as[0] = "CREATE TABLE photos (_id INTEGER PRIMARY KEY,pid TEXT,aid TEXT,owner INT,src TEXT,src_big TEXT,src_small TEXT,caption TEXT,created INT,position INT,thumbnail BLOB,filename TEXT);";
        as[1] = "CREATE TABLE albums (_id INTEGER PRIMARY KEY,aid TEXT,cover_pid TEXT,cover_url TEXT,owner INT,name TEXT,created INT,modified INT,description TEXT,location TEXT,size INT,visibility TEXT,type TEXT,thumbnail BLOB,object_id INTEGER);";
        as[2] = "CREATE TABLE stream_photos (_id INTEGER PRIMARY KEY,url TEXT,filename TEXT);";
        return as;
    }

    private static String setAlbumsOrderBy(String s)
    {
        String s1;
        if(TextUtils.isEmpty(s))
            s1 = "modified DESC";
        else
            s1 = s;
        return s1;
    }

    private static String setPhotosOrderBy(String s)
    {
        String s1;
        if(TextUtils.isEmpty(s))
            s1 = "position ASC";
        else
            s1 = s;
        return s1;
    }

    private static String setStreamPhotosOrderBy(String s)
    {
        String s1;
        if(TextUtils.isEmpty(s))
            s1 = "url DESC";
        else
            s1 = s;
        return s1;
    }

    public int bulkInsert(Uri uri, ContentValues acontentvalues[])
    {
        int i = 0;
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        switch(URL_MATCHER.match(uri))
        {
        default:
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());

        case 1: // '\001'
            for(int i1 = 0; i1 < acontentvalues.length; i1++)
                if(sqlitedatabase.insert("photos", "pid", acontentvalues[i1]) > 0L)
                    i++;

            break;

        case 4: // '\004'
            for(int l = 0; l < acontentvalues.length; l++)
            {
                acontentvalues[l].put("aid", (String)uri.getPathSegments().get(2));
                if(sqlitedatabase.insert("photos", "pid", acontentvalues[l]) > 0L)
                    i++;
            }

            break;

        case 10: // '\n'
            for(int k = 0; k < acontentvalues.length; k++)
                if(sqlitedatabase.insert("albums", "aid", acontentvalues[k]) > 0L)
                    i++;

            break;

        case 13: // '\r'
            for(int j = 0; j < acontentvalues.length; j++)
            {
                acontentvalues[j].put("owner", (String)uri.getPathSegments().get(2));
                if(sqlitedatabase.insert("albums", "aid", acontentvalues[j]) > 0L)
                    i++;
            }

            break;
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

    public int delete(Uri uri, String s, String as[])
    {
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        URL_MATCHER.match(uri);
        JVM INSTR tableswitch 1 21: default 116
    //                   1 144
    //                   2 170
    //                   3 231
    //                   4 295
    //                   5 116
    //                   6 116
    //                   7 116
    //                   8 116
    //                   9 116
    //                   10 410
    //                   11 424
    //                   12 473
    //                   13 525
    //                   14 116
    //                   15 116
    //                   16 116
    //                   17 116
    //                   18 116
    //                   19 116
    //                   20 625
    //                   21 646;
           goto _L1 _L2 _L3 _L4 _L5 _L1 _L1 _L1 _L1 _L1 _L6 _L7 _L8 _L9 _L1 _L1 _L1 _L1 _L1 _L1 _L10 _L11
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        int i = sqlitedatabase.delete("photos", s, as);
_L13:
        getContext().getContentResolver().notifyChange(uri, null);
        return i;
_L3:
        String s12 = (String)uri.getPathSegments().get(1);
        String s13 = (new StringBuilder()).append("_id=").append(s12).toString();
        deletePhotoFiles(sqlitedatabase, s13, null);
        i = sqlitedatabase.delete("photos", s13, null);
        continue; /* Loop/switch isn't completed */
_L4:
        String s10 = (String)uri.getPathSegments().get(2);
        String s11 = (new StringBuilder()).append("pid=").append(DatabaseUtils.sqlEscapeString(s10)).toString();
        deletePhotoFiles(sqlitedatabase, s11, null);
        i = sqlitedatabase.delete("photos", s11, null);
        continue; /* Loop/switch isn't completed */
_L5:
        String s7 = (String)uri.getPathSegments().get(2);
        StringBuilder stringbuilder1 = (new StringBuilder()).append("aid=").append(DatabaseUtils.sqlEscapeString(s7));
        String s8;
        String s9;
        if(!TextUtils.isEmpty(s))
            s8 = (new StringBuilder()).append(" AND (").append(s).append(')').toString();
        else
            s8 = "";
        s9 = stringbuilder1.append(s8).toString();
        deletePhotoFiles(sqlitedatabase, s9, as);
        i = sqlitedatabase.delete("photos", s9, as);
        continue; /* Loop/switch isn't completed */
_L6:
        i = sqlitedatabase.delete("albums", s, as);
        continue; /* Loop/switch isn't completed */
_L7:
        String s6 = (String)uri.getPathSegments().get(1);
        i = sqlitedatabase.delete("albums", (new StringBuilder()).append("_id=").append(s6).toString(), null);
        continue; /* Loop/switch isn't completed */
_L8:
        String s5 = (String)uri.getPathSegments().get(2);
        i = sqlitedatabase.delete("albums", (new StringBuilder()).append("aid=").append(DatabaseUtils.sqlEscapeString(s5)).toString(), null);
        continue; /* Loop/switch isn't completed */
_L9:
        String s3 = (String)uri.getPathSegments().get(2);
        StringBuilder stringbuilder = (new StringBuilder()).append("owner=").append(s3);
        String s4;
        if(!TextUtils.isEmpty(s))
            s4 = (new StringBuilder()).append(" AND (").append(s).append(')').toString();
        else
            s4 = "";
        i = sqlitedatabase.delete("albums", stringbuilder.append(s4).toString(), as);
        continue; /* Loop/switch isn't completed */
_L10:
        deleteStreamPhotoFiles(sqlitedatabase, s, as);
        i = sqlitedatabase.delete("stream_photos", s, as);
        continue; /* Loop/switch isn't completed */
_L11:
        String s1 = (String)uri.getPathSegments().get(1);
        String s2 = (new StringBuilder()).append("_id=").append(s1).toString();
        deleteStreamPhotoFiles(sqlitedatabase, s2, null);
        i = sqlitedatabase.delete("stream_photos", s2, null);
        if(true) goto _L13; else goto _L12
_L12:
    }

    public String getType(Uri uri)
    {
        URL_MATCHER.match(uri);
        JVM INSTR tableswitch 1 21: default 104
    //                   1 132
    //                   2 132
    //                   3 132
    //                   4 132
    //                   5 104
    //                   6 104
    //                   7 104
    //                   8 104
    //                   9 104
    //                   10 138
    //                   11 138
    //                   12 138
    //                   13 138
    //                   14 104
    //                   15 104
    //                   16 104
    //                   17 104
    //                   18 104
    //                   19 104
    //                   20 145
    //                   21 145;
           goto _L1 _L2 _L2 _L2 _L2 _L1 _L1 _L1 _L1 _L1 _L3 _L3 _L3 _L3 _L1 _L1 _L1 _L1 _L1 _L1 _L4 _L4
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        String s = "vnd.android.cursor.item/vnd.facebook.katana.photos";
_L6:
        return s;
_L3:
        s = "vnd.android.cursor.item/vnd.facebook.katana.albums";
        continue; /* Loop/switch isn't completed */
_L4:
        s = "vnd.android.cursor.item/vnd.facebook.katana.stream_photos";
        if(true) goto _L6; else goto _L5
_L5:
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
        URL_MATCHER.match(uri);
        JVM INSTR lookupswitch 5: default 80
    //                   1: 119
    //                   4: 168
    //                   10: 236
    //                   13: 285
    //                   20: 353;
           goto _L1 _L2 _L3 _L4 _L5 _L6
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        long l4 = sqlitedatabase.insert("photos", "pid", contentvalues1);
        if(l4 <= 0L) goto _L8; else goto _L7
_L7:
        Uri uri1;
        getContext().getContentResolver().notifyChange(uri, null);
        uri1 = Uri.withAppendedPath(PHOTOS_CONTENT_URI, String.valueOf(l4));
_L9:
        return uri1;
_L3:
        contentvalues1.put("aid", (String)uri.getPathSegments().get(2));
        long l3 = sqlitedatabase.insert("photos", "pid", contentvalues1);
        if(l3 <= 0L)
            break; /* Loop/switch isn't completed */
        getContext().getContentResolver().notifyChange(uri, null);
        uri1 = Uri.withAppendedPath(PHOTOS_CONTENT_URI, String.valueOf(l3));
        continue; /* Loop/switch isn't completed */
_L4:
        long l2 = sqlitedatabase.insert("albums", "aid", contentvalues1);
        if(l2 <= 0L)
            break; /* Loop/switch isn't completed */
        getContext().getContentResolver().notifyChange(uri, null);
        uri1 = Uri.withAppendedPath(ALBUMS_CONTENT_URI, String.valueOf(l2));
        continue; /* Loop/switch isn't completed */
_L5:
        contentvalues1.put("owner", (String)uri.getPathSegments().get(2));
        long l1 = sqlitedatabase.insert("albums", "aid", contentvalues1);
        if(l1 <= 0L)
            break; /* Loop/switch isn't completed */
        getContext().getContentResolver().notifyChange(uri, null);
        uri1 = Uri.withAppendedPath(ALBUMS_CONTENT_URI, String.valueOf(l1));
        continue; /* Loop/switch isn't completed */
_L6:
        long l = sqlitedatabase.insert("stream_photos", "url", contentvalues1);
        if(l <= 0L)
            break; /* Loop/switch isn't completed */
        getContext().getContentResolver().notifyChange(uri, null);
        uri1 = Uri.withAppendedPath(STREAM_PHOTOS_CONTENT_URI, String.valueOf(l));
        if(true) goto _L9; else goto _L8
_L8:
        throw new SQLException((new StringBuilder()).append("Failed to insert row into ").append(uri).toString());
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
        String s2;
        sqlitequerybuilder = new SQLiteQueryBuilder();
        s2 = null;
        URL_MATCHER.match(uri);
        JVM INSTR tableswitch 1 21: default 116
    //                   1 144
    //                   2 206
    //                   3 261
    //                   4 319
    //                   5 116
    //                   6 116
    //                   7 116
    //                   8 116
    //                   9 116
    //                   10 384
    //                   11 409
    //                   12 464
    //                   13 522
    //                   14 116
    //                   15 116
    //                   16 116
    //                   17 116
    //                   18 116
    //                   19 116
    //                   20 587
    //                   21 612;
           goto _L1 _L2 _L3 _L4 _L5 _L1 _L1 _L1 _L1 _L1 _L6 _L7 _L8 _L9 _L1 _L1 _L1 _L1 _L1 _L1 _L10 _L11
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        sqlitequerybuilder.setTables("photos");
        sqlitequerybuilder.setProjectionMap(PHOTOS_PROJECTION_MAP);
        s2 = setPhotosOrderBy(s1);
_L13:
        Cursor cursor = sqlitequerybuilder.query(mDbHelper.getReadableDatabase(), as, s, as1, null, null, s2);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
_L3:
        sqlitequerybuilder.setTables("photos");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("_id=").append((String)uri.getPathSegments().get(1)).toString());
        sqlitequerybuilder.setProjectionMap(PHOTOS_PROJECTION_MAP);
        continue; /* Loop/switch isn't completed */
_L4:
        sqlitequerybuilder.setTables("photos");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("pid=").append(DatabaseUtils.sqlEscapeString((String)uri.getPathSegments().get(2))).toString());
        sqlitequerybuilder.setProjectionMap(PHOTOS_PROJECTION_MAP);
        continue; /* Loop/switch isn't completed */
_L5:
        sqlitequerybuilder.setTables("photos");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("aid=").append(DatabaseUtils.sqlEscapeString((String)uri.getPathSegments().get(2))).toString());
        sqlitequerybuilder.setProjectionMap(PHOTOS_PROJECTION_MAP);
        s2 = setPhotosOrderBy(s1);
        continue; /* Loop/switch isn't completed */
_L6:
        sqlitequerybuilder.setTables("albums");
        sqlitequerybuilder.setProjectionMap(ALBUMS_PROJECTION_MAP);
        s2 = setAlbumsOrderBy(s1);
        continue; /* Loop/switch isn't completed */
_L7:
        sqlitequerybuilder.setTables("albums");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("_id=").append((String)uri.getPathSegments().get(1)).toString());
        sqlitequerybuilder.setProjectionMap(ALBUMS_PROJECTION_MAP);
        continue; /* Loop/switch isn't completed */
_L8:
        sqlitequerybuilder.setTables("albums");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("aid=").append(DatabaseUtils.sqlEscapeString((String)uri.getPathSegments().get(2))).toString());
        sqlitequerybuilder.setProjectionMap(ALBUMS_PROJECTION_MAP);
        continue; /* Loop/switch isn't completed */
_L9:
        sqlitequerybuilder.setTables("albums");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("owner=").append(DatabaseUtils.sqlEscapeString((String)uri.getPathSegments().get(2))).toString());
        sqlitequerybuilder.setProjectionMap(ALBUMS_PROJECTION_MAP);
        s2 = setAlbumsOrderBy(s1);
        continue; /* Loop/switch isn't completed */
_L10:
        sqlitequerybuilder.setTables("stream_photos");
        sqlitequerybuilder.setProjectionMap(STREAM_PHOTOS_PROJECTION_MAP);
        s2 = setStreamPhotosOrderBy(s1);
        continue; /* Loop/switch isn't completed */
_L11:
        sqlitequerybuilder.setTables("stream_photos");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("_id=").append((String)uri.getPathSegments().get(1)).toString());
        if(true) goto _L13; else goto _L12
_L12:
    }

    public int update(Uri uri, ContentValues contentvalues, String s, String as[])
    {
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        URL_MATCHER.match(uri);
        JVM INSTR tableswitch 1 21: default 116
    //                   1 144
    //                   2 177
    //                   3 227
    //                   4 280
    //                   5 116
    //                   6 116
    //                   7 116
    //                   8 116
    //                   9 116
    //                   10 385
    //                   11 401
    //                   12 451
    //                   13 504
    //                   14 116
    //                   15 116
    //                   16 116
    //                   17 116
    //                   18 116
    //                   19 116
    //                   20 606
    //                   21 622;
           goto _L1 _L2 _L3 _L4 _L5 _L1 _L1 _L1 _L1 _L1 _L6 _L7 _L8 _L9 _L1 _L1 _L1 _L1 _L1 _L1 _L10 _L11
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        int i = sqlitedatabase.update("photos", contentvalues, s, as);
_L13:
        if(i > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return i;
_L3:
        String s9 = (String)uri.getPathSegments().get(1);
        i = sqlitedatabase.update("photos", contentvalues, (new StringBuilder()).append("_id=").append(s9).toString(), null);
        continue; /* Loop/switch isn't completed */
_L4:
        String s8 = (String)uri.getPathSegments().get(2);
        i = sqlitedatabase.update("photos", contentvalues, (new StringBuilder()).append("pid=").append(DatabaseUtils.sqlEscapeString(s8)).toString(), null);
        continue; /* Loop/switch isn't completed */
_L5:
        String s6 = (String)uri.getPathSegments().get(2);
        StringBuilder stringbuilder1 = (new StringBuilder()).append("aid=").append(DatabaseUtils.sqlEscapeString(s6));
        String s7;
        if(!TextUtils.isEmpty(s))
            s7 = (new StringBuilder()).append(" AND (").append(s).append(')').toString();
        else
            s7 = "";
        i = sqlitedatabase.update("photos", contentvalues, stringbuilder1.append(s7).toString(), as);
        continue; /* Loop/switch isn't completed */
_L6:
        i = sqlitedatabase.update("albums", contentvalues, s, as);
        continue; /* Loop/switch isn't completed */
_L7:
        String s5 = (String)uri.getPathSegments().get(1);
        i = sqlitedatabase.update("albums", contentvalues, (new StringBuilder()).append("_id=").append(s5).toString(), null);
        continue; /* Loop/switch isn't completed */
_L8:
        String s4 = (String)uri.getPathSegments().get(2);
        i = sqlitedatabase.update("albums", contentvalues, (new StringBuilder()).append("aid=").append(DatabaseUtils.sqlEscapeString(s4)).toString(), null);
        continue; /* Loop/switch isn't completed */
_L9:
        String s2 = (String)uri.getPathSegments().get(2);
        StringBuilder stringbuilder = (new StringBuilder()).append("owner=").append(s2);
        String s3;
        if(!TextUtils.isEmpty(s))
            s3 = (new StringBuilder()).append(" AND (").append(s).append(')').toString();
        else
            s3 = "";
        i = sqlitedatabase.update("albums", contentvalues, stringbuilder.append(s3).toString(), as);
        continue; /* Loop/switch isn't completed */
_L10:
        i = sqlitedatabase.update("stream_photos", contentvalues, s, as);
        continue; /* Loop/switch isn't completed */
_L11:
        String s1 = (String)uri.getPathSegments().get(1);
        i = sqlitedatabase.update("stream_photos", contentvalues, (new StringBuilder()).append("_id=").append(s1).toString(), null);
        if(true) goto _L13; else goto _L12
_L12:
    }

    private static final int ALBUMS = 10;
    public static final Uri ALBUMS_AID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.PhotosProvider/albums/aid");
    private static final String ALBUMS_BASE_URI = "content://com.facebook.katana.provider.PhotosProvider/albums";
    public static final Uri ALBUMS_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.PhotosProvider/albums");
    private static final int ALBUMS_OWNER = 13;
    public static final Uri ALBUMS_OWNER_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.PhotosProvider/albums/owner");
    private static final HashMap ALBUMS_PROJECTION_MAP;
    private static final int ALBUM_AID = 12;
    private static final int ALBUM_ID = 11;
    public static final String ALBUM_SELECTION = "aid IN(?)";
    private static final String AUTHORITY = "com.facebook.katana.provider.PhotosProvider";
    private static final String CONTENT_SCHEME = "content://";
    private static final int PHOTOS = 1;
    private static final int PHOTOS_AID = 4;
    public static final Uri PHOTOS_AID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.PhotosProvider/photos/aid");
    private static final String PHOTOS_BASE_URI = "content://com.facebook.katana.provider.PhotosProvider/photos";
    public static final Uri PHOTOS_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.PhotosProvider/photos");
    private static final String PHOTOS_FILENAME_PROJECTION[];
    public static final Uri PHOTOS_PID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.PhotosProvider/photos/pid");
    private static HashMap PHOTOS_PROJECTION_MAP;
    private static final int PHOTO_ID = 2;
    private static final int PHOTO_PID = 3;
    public static final String PHOTO_SELECTION = "pid IN(?)";
    private static final String SQL_ALBUMS = "CREATE TABLE albums (_id INTEGER PRIMARY KEY,aid TEXT,cover_pid TEXT,cover_url TEXT,owner INT,name TEXT,created INT,modified INT,description TEXT,location TEXT,size INT,visibility TEXT,type TEXT,thumbnail BLOB,object_id INTEGER);";
    private static final String SQL_PHOTOS = "CREATE TABLE photos (_id INTEGER PRIMARY KEY,pid TEXT,aid TEXT,owner INT,src TEXT,src_big TEXT,src_small TEXT,caption TEXT,created INT,position INT,thumbnail BLOB,filename TEXT);";
    private static final String SQL_STREAM_PHOTOS = "CREATE TABLE stream_photos (_id INTEGER PRIMARY KEY,url TEXT,filename TEXT);";
    private static final String STREAM_FILENAME_PROJECTION[];
    private static final int STREAM_PHOTOS = 20;
    private static final String STREAM_PHOTOS_BASE_URI = "content://com.facebook.katana.provider.PhotosProvider/stream_photos";
    public static final Uri STREAM_PHOTOS_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.PhotosProvider/stream_photos");
    private static final int STREAM_PHOTOS_ID = 21;
    private static HashMap STREAM_PHOTOS_PROJECTION_MAP;
    private static final String TABLE_ALBUMS = "albums";
    private static final String TABLE_PHOTOS = "photos";
    private static final String TABLE_STREAM_PHOTOS = "stream_photos";
    private static final UriMatcher URL_MATCHER;
    private FacebookDatabaseHelper mDbHelper;

    static 
    {
        String as[] = new String[1];
        as[0] = "filename";
        STREAM_FILENAME_PROJECTION = as;
        String as1[] = new String[1];
        as1[0] = "filename";
        PHOTOS_FILENAME_PROJECTION = as1;
        URL_MATCHER = new UriMatcher(-1);
        URL_MATCHER.addURI("com.facebook.katana.provider.PhotosProvider", "photos", 1);
        URL_MATCHER.addURI("com.facebook.katana.provider.PhotosProvider", "photos/#", 2);
        URL_MATCHER.addURI("com.facebook.katana.provider.PhotosProvider", "photos/pid/*", 3);
        URL_MATCHER.addURI("com.facebook.katana.provider.PhotosProvider", "photos/aid/*", 4);
        URL_MATCHER.addURI("com.facebook.katana.provider.PhotosProvider", "albums", 10);
        URL_MATCHER.addURI("com.facebook.katana.provider.PhotosProvider", "albums/#", 11);
        URL_MATCHER.addURI("com.facebook.katana.provider.PhotosProvider", "albums/aid/*", 12);
        URL_MATCHER.addURI("com.facebook.katana.provider.PhotosProvider", "albums/owner/#", 13);
        URL_MATCHER.addURI("com.facebook.katana.provider.PhotosProvider", "stream_photos", 20);
        URL_MATCHER.addURI("com.facebook.katana.provider.PhotosProvider", "stream_photos/#", 21);
        PHOTOS_PROJECTION_MAP = new HashMap();
        PHOTOS_PROJECTION_MAP.put("_id", "_id");
        PHOTOS_PROJECTION_MAP.put("pid", "pid");
        PHOTOS_PROJECTION_MAP.put("aid", "aid");
        PHOTOS_PROJECTION_MAP.put("owner", "owner");
        PHOTOS_PROJECTION_MAP.put("src", "src");
        PHOTOS_PROJECTION_MAP.put("src_big", "src_big");
        PHOTOS_PROJECTION_MAP.put("src_small", "src_small");
        PHOTOS_PROJECTION_MAP.put("caption", "caption");
        PHOTOS_PROJECTION_MAP.put("created", "created");
        PHOTOS_PROJECTION_MAP.put("position", "position");
        PHOTOS_PROJECTION_MAP.put("thumbnail", "thumbnail");
        PHOTOS_PROJECTION_MAP.put("filename", "filename");
        ALBUMS_PROJECTION_MAP = new HashMap();
        ALBUMS_PROJECTION_MAP.put("_id", "_id");
        ALBUMS_PROJECTION_MAP.put("aid", "aid");
        ALBUMS_PROJECTION_MAP.put("cover_pid", "cover_pid");
        ALBUMS_PROJECTION_MAP.put("cover_url", "cover_url");
        ALBUMS_PROJECTION_MAP.put("thumbnail", "thumbnail");
        ALBUMS_PROJECTION_MAP.put("owner", "owner");
        ALBUMS_PROJECTION_MAP.put("name", "name");
        ALBUMS_PROJECTION_MAP.put("created", "created");
        ALBUMS_PROJECTION_MAP.put("modified", "modified");
        ALBUMS_PROJECTION_MAP.put("description", "description");
        ALBUMS_PROJECTION_MAP.put("location", "location");
        ALBUMS_PROJECTION_MAP.put("size", "size");
        ALBUMS_PROJECTION_MAP.put("visibility", "visibility");
        ALBUMS_PROJECTION_MAP.put("type", "type");
        ALBUMS_PROJECTION_MAP.put("object_id", "object_id");
        STREAM_PHOTOS_PROJECTION_MAP = new HashMap();
        STREAM_PHOTOS_PROJECTION_MAP.put("_id", "_id");
        STREAM_PHOTOS_PROJECTION_MAP.put("url", "url");
        STREAM_PHOTOS_PROJECTION_MAP.put("filename", "filename");
    }
}
