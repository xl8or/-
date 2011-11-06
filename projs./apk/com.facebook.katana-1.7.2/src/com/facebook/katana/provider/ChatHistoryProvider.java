// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChatHistoryProvider.java

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

public class ChatHistoryProvider extends ContentProvider
{
    public static final class MessageColumns
        implements BaseColumns
    {

        public static final String BODY = "body";
        public static final String DEFAULT_SORT_ORDER = "timestamp ASC";
        public static final String FRIEND_UID = "friend_uid";
        public static final String SENT = "sent";
        public static final String TIME_STAMP = "timestamp";

        public MessageColumns()
        {
        }
    }

    public static final class ConversationColumns
        implements BaseColumns
    {

        public static final String DEFAULT_SORT_ORDER = "_id ASC";
        public static final String FRIEND_UID = "friend_uid";
        public static final String UNREAD_COUNT = "unread_count";
        public static final String UNREAD_MESSAGE = "unread_message";

        public ConversationColumns()
        {
        }
    }


    public ChatHistoryProvider()
    {
    }

    public static String[] getTableNames()
    {
        String as[] = new String[2];
        as[0] = "chatmessages";
        as[1] = "chatconversations";
        return as;
    }

    public static String[] getTableSQLs()
    {
        String as[] = new String[2];
        as[0] = "CREATE TABLE chatmessages (_id INTEGER PRIMARY KEY,friend_uid INT,sent INT,timestamp INT,body TEXT);";
        as[1] = "CREATE TABLE chatconversations (_id INTEGER PRIMARY KEY,friend_uid INT,unread_count INT,unread_message TEXT);";
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
        JVM INSTR lookupswitch 2: default 44
    //                   1: 71
    //                   10: 102;
           goto _L1 _L2 _L3
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        int i = sqlitedatabase.delete("chatmessages", s, as);
_L5:
        if(i > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return i;
_L3:
        i = sqlitedatabase.delete("chatconversations", s, as);
        if(true) goto _L5; else goto _L4
_L4:
    }

    public String getType(Uri uri)
    {
        URL_MATCHER.match(uri);
        JVM INSTR lookupswitch 2: default 32
    //                   1: 59
    //                   10: 64;
           goto _L1 _L2 _L3
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        String s = "vnd.android.cursor.dir/vnd.facebook.katana.provider.chatmessages";
_L5:
        return s;
_L3:
        s = "vnd.android.cursor.dir/vnd.facebook.katana.provider.chatconversations";
        if(true) goto _L5; else goto _L4
_L4:
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
        switch(URL_MATCHER.match(uri))
        {
        default:
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());

        case 1: // '\001'
            for(int k = 0; k < acontentvalues.length; k++)
            {
                long l1 = sqlitedatabase.insert("chatmessages", "friend_uid", acontentvalues[k]);
                if(l1 > 0L)
                {
                    i++;
                    if(list != null)
                        list.add(Uri.withAppendedPath(HISTORY_CONTENT_URI, Long.valueOf(l1).toString()));
                }
            }

            break;

        case 10: // '\n'
            for(int j = 0; j < acontentvalues.length; j++)
            {
                long l = sqlitedatabase.insert("chatconversations", "friend_uid", acontentvalues[j]);
                if(l <= 0L)
                    continue;
                i++;
                if(list != null)
                    list.add(Uri.withAppendedPath(CONVERSATIONS_CONTENT_URI, Long.valueOf(l).toString()));
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
        JVM INSTR lookupswitch 2: default 44
    //                   1: 71
    //                   10: 146;
           goto _L1 _L2 _L3
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        String s2;
        sqlitequerybuilder.setTables("chatmessages");
        sqlitequerybuilder.setProjectionMap(CHAT_HISTORY_PROJECTION_MAP);
        Cursor cursor;
        if(TextUtils.isEmpty(s1))
            s2 = "timestamp ASC";
        else
            s2 = s1;
_L5:
        cursor = sqlitequerybuilder.query(mDbHelper.getReadableDatabase(), as, s, as1, null, null, s2);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
_L3:
        sqlitequerybuilder.setTables("chatconversations");
        sqlitequerybuilder.setProjectionMap(CHAT_CONVERSATIONS_PROJECTION_MAP);
        if(TextUtils.isEmpty(s1))
            s2 = "_id ASC";
        else
            s2 = s1;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public int update(Uri uri, ContentValues contentvalues, String s, String as[])
    {
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        URL_MATCHER.match(uri);
        JVM INSTR lookupswitch 2: default 44
    //                   1: 71
    //                   10: 99;
           goto _L1 _L2 _L3
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        int i = sqlitedatabase.update("chatmessages", contentvalues, s, as);
_L5:
        getContext().getContentResolver().notifyChange(uri, null);
        return i;
_L3:
        i = sqlitedatabase.update("chatconversations", contentvalues, s, as);
        if(true) goto _L5; else goto _L4
_L4:
    }

    static final boolean $assertionsDisabled = false;
    private static final String AUTHORITY = "com.facebook.katana.provider.ChatHistoryProvider";
    private static final String BASE_CONTENT_URI = "content://com.facebook.katana.provider.ChatHistoryProvider/";
    private static final int CHAT_CONVERSATIONS = 10;
    private static final HashMap CHAT_CONVERSATIONS_PROJECTION_MAP;
    private static final String CHAT_CONVERSATIONS_TABLE = "chatconversations";
    private static final HashMap CHAT_HISTORY_PROJECTION_MAP;
    private static final int CHAT_MESSAGES = 1;
    private static final String CHAT_MESSAGES_TABLE = "chatmessages";
    public static final Uri CONVERSATIONS_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.ChatHistoryProvider/chatconversations");
    public static final Uri HISTORY_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.ChatHistoryProvider/chatmessages");
    private static final String SQL_CHAT_CONVERSATIONS = "CREATE TABLE chatconversations (_id INTEGER PRIMARY KEY,friend_uid INT,unread_count INT,unread_message TEXT);";
    private static final String SQL_CHAT_HISTORY = "CREATE TABLE chatmessages (_id INTEGER PRIMARY KEY,friend_uid INT,sent INT,timestamp INT,body TEXT);";
    private static final UriMatcher URL_MATCHER;
    private FacebookDatabaseHelper mDbHelper;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/provider/ChatHistoryProvider.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
        URL_MATCHER = new UriMatcher(-1);
        URL_MATCHER.addURI("com.facebook.katana.provider.ChatHistoryProvider", "chatmessages", 1);
        URL_MATCHER.addURI("com.facebook.katana.provider.ChatHistoryProvider", "chatconversations", 10);
        CHAT_HISTORY_PROJECTION_MAP = new HashMap();
        CHAT_HISTORY_PROJECTION_MAP.put("_id", "_id");
        CHAT_HISTORY_PROJECTION_MAP.put("friend_uid", "friend_uid");
        CHAT_HISTORY_PROJECTION_MAP.put("sent", "sent");
        CHAT_HISTORY_PROJECTION_MAP.put("timestamp", "timestamp");
        CHAT_HISTORY_PROJECTION_MAP.put("body", "body");
        CHAT_CONVERSATIONS_PROJECTION_MAP = new HashMap();
        CHAT_CONVERSATIONS_PROJECTION_MAP.put("_id", "_id");
        CHAT_CONVERSATIONS_PROJECTION_MAP.put("friend_uid", "friend_uid");
        CHAT_CONVERSATIONS_PROJECTION_MAP.put("unread_count", "unread_count");
        CHAT_CONVERSATIONS_PROJECTION_MAP.put("unread_message", "unread_message");
    }
}
