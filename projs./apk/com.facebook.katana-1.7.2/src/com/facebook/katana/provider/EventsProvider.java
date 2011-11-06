// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EventsProvider.java

package com.facebook.katana.provider;

import android.content.*;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import java.util.*;

// Referenced classes of package com.facebook.katana.provider:
//            FacebookDatabaseHelper

public class EventsProvider extends ContentProvider
{
    public static final class EventColumns
        implements BaseColumns
    {

        public static final String CREATOR_DISPLAY_NAME = "display_name";
        public static final String CREATOR_ID = "creator_id";
        public static final String CREATOR_IMAGE_URL = "creator_image_url";
        public static final String DEFAULT_SORT_ORDER = "start_time ASC";
        public static final String DESCRIPTION = "description";
        public static final String END_TIME = "end_time";
        public static final String EVENT_ID = "event_id";
        public static final String EVENT_NAME = "event_name";
        public static final String EVENT_SUBTYPE = "event_subtype";
        public static final String EVENT_TYPE = "event_type";
        public static final String HIDE_GUEST_LIST = "hide_guest_list";
        public static final String HOST = "host";
        public static final String IMAGE_URL = "image_url";
        public static final String LOCATION = "location";
        public static final String MEDIUM_IMAGE_URL = "medium_image_url";
        public static final String RSVP_STATUS = "rsvp_status";
        public static final String START_TIME = "start_time";
        public static final String TAGLINE = "tagline";
        public static final String VENUE = "venue";

        public EventColumns()
        {
        }
    }


    public EventsProvider()
    {
    }

    public static String[] getTableNames()
    {
        String as[] = new String[1];
        as[0] = "events";
        return as;
    }

    public static String[] getTableSQLs()
    {
        String as[] = new String[1];
        as[0] = "CREATE TABLE events (_id INTEGER PRIMARY KEY,event_id INT,event_name TEXT,tagline TEXT,image_url TEXT,medium_image_url TEXT,host TEXT,description TEXT,event_type TEXT,event_subtype TEXT,start_time INT,end_time INT,creator_id INT,display_name TEXT,creator_image_url TEXT,location TEXT,venue BLOB,hide_guest_list INT,rsvp_status INT);";
        return as;
    }

    public int bulkInsert(Uri uri, ContentValues acontentvalues[])
    {
        return insertHelper(uri, acontentvalues, null);
    }

    public int delete(Uri uri, String s, String as[])
    {
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        URL_MATCHER.match(uri);
        JVM INSTR tableswitch 1 3: default 44
    //                   1 71
    //                   2 102
    //                   3 150;
           goto _L1 _L2 _L3 _L4
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        int i = sqlitedatabase.delete("events", s, as);
_L6:
        if(i > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return i;
_L3:
        String s2 = (String)uri.getPathSegments().get(1);
        i = sqlitedatabase.delete("events", (new StringBuilder()).append("_id=").append(s2).toString(), null);
        continue; /* Loop/switch isn't completed */
_L4:
        String s1 = (String)uri.getPathSegments().get(2);
        i = sqlitedatabase.delete("events", (new StringBuilder()).append("event_id=").append(s1).toString(), null);
        if(true) goto _L6; else goto _L5
_L5:
    }

    public String getType(Uri uri)
    {
        switch(URL_MATCHER.match(uri))
        {
        default:
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());

        case 1: // '\001'
        case 2: // '\002'
        case 3: // '\003'
            return "vnd.android.cursor.item/vnd.com.facebook.katana.provider.events";
        }
    }

    public Uri insert(Uri uri, ContentValues contentvalues)
    {
        ContentValues acontentvalues[] = new ContentValues[1];
        acontentvalues[0] = contentvalues;
        ArrayList arraylist = new ArrayList();
        int i = insertHelper(uri, acontentvalues, arraylist);
        if(!$assertionsDisabled && i != 1)
            throw new AssertionError();
        if(!$assertionsDisabled && arraylist.size() != 1)
            throw new AssertionError();
        else
            return (Uri)arraylist.get(0);
    }

    protected int insertHelper(Uri uri, ContentValues acontentvalues[], List list)
    {
        int i = 0;
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        int j;
        switch(URL_MATCHER.match(uri))
        {
        default:
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());

        case 1: // '\001'
            j = 0;
            break;
        }
        for(; j < acontentvalues.length; j++)
        {
            long l = sqlitedatabase.insert("events", "event_name", acontentvalues[j]);
            if(l <= 0L)
                continue;
            i++;
            if(list != null)
                list.add(Uri.withAppendedPath(EVENTS_CONTENT_URI, Long.toString(l)));
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
        JVM INSTR tableswitch 1 3: default 44
    //                   1 71
    //                   2 144
    //                   3 203;
           goto _L1 _L2 _L3 _L4
_L4:
        break MISSING_BLOCK_LABEL_203;
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        String s2;
        sqlitequerybuilder.setTables("events");
        sqlitequerybuilder.setProjectionMap(EVENTS_PROJECTION_MAP);
        s2 = "start_time ASC";
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
        sqlitequerybuilder.setTables("events");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("_id=").append((String)uri.getPathSegments().get(1)).toString());
        sqlitequerybuilder.setProjectionMap(EVENTS_PROJECTION_MAP);
        s2 = "start_time ASC";
          goto _L5
        sqlitequerybuilder.setTables("events");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("event_id=").append((String)uri.getPathSegments().get(2)).toString());
        sqlitequerybuilder.setProjectionMap(EVENTS_PROJECTION_MAP);
        s2 = "start_time ASC";
          goto _L5
    }

    public int update(Uri uri, ContentValues contentvalues, String s, String as[])
    {
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        URL_MATCHER.match(uri);
        JVM INSTR tableswitch 1 3: default 44
    //                   1 71
    //                   2 104
    //                   3 153;
           goto _L1 _L2 _L3 _L4
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        int i = sqlitedatabase.update("events", contentvalues, s, as);
_L6:
        if(i > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return i;
_L3:
        String s2 = (String)uri.getPathSegments().get(1);
        i = sqlitedatabase.update("events", contentvalues, (new StringBuilder()).append("_id=").append(s2).toString(), null);
        continue; /* Loop/switch isn't completed */
_L4:
        String s1 = (String)uri.getPathSegments().get(2);
        i = sqlitedatabase.update("events", contentvalues, (new StringBuilder()).append("event_id=").append(s1).toString(), null);
        if(true) goto _L6; else goto _L5
_L5:
    }

    static final boolean $assertionsDisabled = false;
    private static final String AUTHORITY = "com.facebook.katana.provider.EventsProvider";
    private static final String BASE_CONTENT_URI = "content://com.facebook.katana.provider.EventsProvider/";
    private static final int EVENTS = 1;
    public static final Uri EVENTS_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.EventsProvider/events");
    private static final HashMap EVENTS_PROJECTION_MAP;
    private static final String EVENTS_TABLE = "events";
    private static final int EVENT_EID = 3;
    public static final Uri EVENT_EID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.EventsProvider/events/eid");
    private static final int EVENT_ID = 2;
    private static final String SQL_EVENTS = "CREATE TABLE events (_id INTEGER PRIMARY KEY,event_id INT,event_name TEXT,tagline TEXT,image_url TEXT,medium_image_url TEXT,host TEXT,description TEXT,event_type TEXT,event_subtype TEXT,start_time INT,end_time INT,creator_id INT,display_name TEXT,creator_image_url TEXT,location TEXT,venue BLOB,hide_guest_list INT,rsvp_status INT);";
    private static final UriMatcher URL_MATCHER;
    private FacebookDatabaseHelper mDbHelper;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/provider/EventsProvider.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
        URL_MATCHER = new UriMatcher(-1);
        URL_MATCHER.addURI("com.facebook.katana.provider.EventsProvider", "events", 1);
        URL_MATCHER.addURI("com.facebook.katana.provider.EventsProvider", "events/#", 2);
        URL_MATCHER.addURI("com.facebook.katana.provider.EventsProvider", "events/eid/#", 3);
        EVENTS_PROJECTION_MAP = new HashMap();
        EVENTS_PROJECTION_MAP.put("_id", "_id");
        EVENTS_PROJECTION_MAP.put("event_id", "event_id");
        EVENTS_PROJECTION_MAP.put("event_name", "event_name");
        EVENTS_PROJECTION_MAP.put("tagline", "tagline");
        EVENTS_PROJECTION_MAP.put("image_url", "image_url");
        EVENTS_PROJECTION_MAP.put("medium_image_url", "medium_image_url");
        EVENTS_PROJECTION_MAP.put("host", "host");
        EVENTS_PROJECTION_MAP.put("description", "description");
        EVENTS_PROJECTION_MAP.put("event_type", "event_type");
        EVENTS_PROJECTION_MAP.put("event_subtype", "event_subtype");
        EVENTS_PROJECTION_MAP.put("start_time", "start_time");
        EVENTS_PROJECTION_MAP.put("end_time", "end_time");
        EVENTS_PROJECTION_MAP.put("creator_id", "creator_id");
        EVENTS_PROJECTION_MAP.put("display_name", "display_name");
        EVENTS_PROJECTION_MAP.put("creator_image_url", "creator_image_url");
        EVENTS_PROJECTION_MAP.put("location", "location");
        EVENTS_PROJECTION_MAP.put("venue", "venue");
        EVENTS_PROJECTION_MAP.put("hide_guest_list", "hide_guest_list");
        EVENTS_PROJECTION_MAP.put("rsvp_status", "rsvp_status");
    }
}
