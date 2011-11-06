// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MailboxProvider.java

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

public class MailboxProvider extends ContentProvider
{
    public static final class ProfileColumns
        implements BaseColumns
    {

        public static final String DEFAULT_SORT_ORDER = "display_name DESC";
        public static final String PROFILE_DISPLAY_NAME = "display_name";
        public static final String PROFILE_ID = "id";
        public static final String PROFILE_IMAGE_URL = "profile_image_url";
        public static final String PROFILE_TYPE = "type";

        public ProfileColumns()
        {
        }
    }

    public static final class MessageDisplayColumns extends MessageColumns
    {

        public static final String AUTHOR_ID = "author_id";
        public static final String AUTHOR_IMAGE_URL = "author_image_url";
        public static final String AUTHOR_NAME = "author_name";
        public static final String AUTHOR_TYPE = "author_type";
        public static final String OBJECT_ID = "object_id";
        public static final String OBJECT_IMAGE_URL = "object_image_url";
        public static final String OBJECT_NAME = "object_name";
        public static final String OBJECT_TYPE = "object_type";

        public MessageDisplayColumns()
        {
        }
    }

    public static class MessageColumns
        implements BaseColumns
    {

        public static final String AUTHOR_PROFILE_ID = "author_id";
        public static final String BODY = "body";
        public static final String DEFAULT_SORT_ORDER = "mid ASC";
        public static final String FOLDER = "folder";
        public static final String MESSAGE_ID = "mid";
        public static final String SPECIFIC_ID = "mailbox_messages._id";
        public static final String THREAD_ID = "tid";
        public static final String TIME_SENT = "sent";

        public MessageColumns()
        {
        }
    }

    public static final class ThreadColumns
        implements BaseColumns
    {

        public static final String DEFAULT_SORT_ORDER = "last_update DESC";
        public static final String FOLDER = "folder";
        public static final String LAST_UPDATE = "last_update";
        public static final String MSG_COUNT = "msg_count";
        public static final String OBJECT_ID = "object_id";
        public static final String OTHER_PARTY_PROFILE_ID = "other_party";
        public static final String PARTICIPANTS = "participants";
        public static final String SNIPPET = "snippet";
        public static final String SPECIFIC_ID = "mailbox_threads._id";
        public static final String SUBJECT = "subject";
        public static final String THREAD_ID = "tid";
        public static final String UNREAD_COUNT = "unread_count";

        public ThreadColumns()
        {
        }
    }


    public MailboxProvider()
    {
    }

    public static Uri getMessagesFolderUri(int i)
    {
        return Uri.parse(getMessagesFolderUriString(i));
    }

    private static String getMessagesFolderUriString(int i)
    {
        return (new StringBuilder()).append("content://com.facebook.katana.provider.MailboxProvider/mailbox_messages").append((String)FOLDER_PATH_MAP.get(Integer.valueOf(i))).toString();
    }

    public static Uri getMessagesTidFolderUri(int i)
    {
        return Uri.parse((new StringBuilder()).append(getMessagesFolderUriString(i)).append("/tid").toString());
    }

    static String[] getTableNames()
    {
        String as[] = new String[3];
        as[0] = "mailbox_threads";
        as[1] = "mailbox_messages";
        as[2] = "mailbox_profiles";
        return as;
    }

    static String[] getTableSQLs()
    {
        String as[] = new String[6];
        as[0] = "CREATE TABLE mailbox_threads (_id INTEGER PRIMARY KEY,folder INT,tid INT,participants TEXT,subject TEXT,snippet TEXT,other_party INT,msg_count INT,unread_count INT,last_update INT,object_id INT);";
        as[1] = "CREATE INDEX mailbox_threads_tid ON mailbox_threads(tid);";
        as[2] = "CREATE TABLE mailbox_messages (_id INTEGER PRIMARY KEY,folder INT,tid INT,mid INT,author_id INT,sent INT,body TEXT);";
        as[3] = "CREATE TABLE mailbox_profiles (_id INTEGER PRIMARY KEY,id INT,display_name TEXT,profile_image_url TEXT,type INT);";
        as[4] = "CREATE INDEX mailbox_profiles_id ON mailbox_profiles(id);";
        as[5] = "CREATE VIEW mailbox_messages_display AS SELECT mailbox_messages._id AS _id, mailbox_messages.mid AS mid, mailbox_messages.folder AS folder, mailbox_messages.tid AS tid, mailbox_messages.sent AS sent, mailbox_messages.body AS body, author.id as author_id, author.display_name as author_name, author.profile_image_url AS author_image_url, author.type AS author_type, object.id as object_id, object.display_name as object_name, object.profile_image_url AS object_image_url, object.type AS object_type FROM mailbox_messages LEFT OUTER JOIN mailbox_threads ON mailbox_messages.tid = mailbox_threads.tid AND mailbox_messages.folder = mailbox_threads.folder LEFT OUTER JOIN mailbox_profiles AS object ON mailbox_threads.object_id = object.id LEFT OUTER JOIN mailbox_profiles AS author ON mailbox_messages.author_id = author.id;";
        return as;
    }

    private static String getThreadsFolderString(int i)
    {
        return (new StringBuilder()).append("content://com.facebook.katana.provider.MailboxProvider/mailbox_threads").append((String)FOLDER_PATH_MAP.get(Integer.valueOf(i))).toString();
    }

    public static Uri getThreadsFolderUri(int i)
    {
        return Uri.parse(getThreadsFolderString(i));
    }

    public static Uri getThreadsTidFolderUri(int i)
    {
        return Uri.parse((new StringBuilder()).append(getThreadsFolderString(i)).append("/tid").toString());
    }

    static String[] getViewNames()
    {
        String as[] = new String[1];
        as[0] = "mailbox_messages_display";
        return as;
    }

    private int insertMessageValues(Uri uri, ContentValues acontentvalues[])
        throws SQLException
    {
        int i = 0;
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        for(int j = 0; j < acontentvalues.length; j++)
            if(sqlitedatabase.insert("mailbox_messages", "tid", acontentvalues[j]) > 0L)
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

    private Uri insertMessageValues(Uri uri, ContentValues contentvalues)
        throws SQLException
    {
        long l = mDbHelper.getWritableDatabase().insert("mailbox_messages", "tid", contentvalues);
        if(l > 0L)
        {
            Uri uri1 = Uri.withAppendedPath(MESSAGES_CONTENT_URI, Long.valueOf(l).toString());
            getContext().getContentResolver().notifyChange(uri, null);
            return uri1;
        } else
        {
            throw new SQLException((new StringBuilder()).append("Failed to insert row into ").append(uri).toString());
        }
    }

    private int insertProfileValues(Uri uri, ContentValues acontentvalues[])
        throws SQLException
    {
        int i = 0;
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        for(int j = 0; j < acontentvalues.length; j++)
            if(sqlitedatabase.insert("mailbox_profiles", "tid", acontentvalues[j]) > 0L)
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

    private Uri insertProfileValues(Uri uri, ContentValues contentvalues)
        throws SQLException
    {
        long l = mDbHelper.getWritableDatabase().insert("mailbox_profiles", "tid", contentvalues);
        if(l > 0L)
        {
            Uri uri1 = Uri.withAppendedPath(PROFILES_CONTENT_URI, Long.valueOf(l).toString());
            getContext().getContentResolver().notifyChange(uri, null);
            return uri1;
        } else
        {
            throw new SQLException((new StringBuilder()).append("Failed to insert row into ").append(uri).toString());
        }
    }

    private int insertThreadValues(Uri uri, ContentValues acontentvalues[])
        throws SQLException
    {
        int i = 0;
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        for(int j = 0; j < acontentvalues.length; j++)
            if(sqlitedatabase.insert("mailbox_threads", "tid", acontentvalues[j]) > 0L)
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

    private Uri insertThreadValues(Uri uri, ContentValues contentvalues)
        throws SQLException
    {
        long l = mDbHelper.getWritableDatabase().insert("mailbox_threads", "tid", contentvalues);
        if(l > 0L)
        {
            Uri uri1 = Uri.withAppendedPath(THREADS_CONTENT_URI, Long.valueOf(l).toString());
            getContext().getContentResolver().notifyChange(uri, null);
            return uri1;
        } else
        {
            throw new SQLException((new StringBuilder()).append("Failed to insert row into ").append(uri).toString());
        }
    }

    private static String setMessagesOrderBy(String s)
    {
        String s1;
        if(TextUtils.isEmpty(s))
            s1 = "mid ASC";
        else
            s1 = s;
        return s1;
    }

    private static String setProfilesOrderBy(String s)
    {
        String s1;
        if(TextUtils.isEmpty(s))
            s1 = "display_name DESC";
        else
            s1 = s;
        return s1;
    }

    private static String setThreadsOrderBy(String s)
    {
        String s1;
        if(TextUtils.isEmpty(s))
            s1 = "last_update DESC";
        else
            s1 = s;
        return s1;
    }

    public int bulkInsert(Uri uri, ContentValues acontentvalues[])
    {
        URL_MATCHER.match(uri);
        JVM INSTR tableswitch 1 20: default 100
    //                   1 128
    //                   2 100
    //                   3 137
    //                   4 177
    //                   5 217
    //                   6 100
    //                   7 100
    //                   8 100
    //                   9 100
    //                   10 257
    //                   11 100
    //                   12 267
    //                   13 307
    //                   14 347
    //                   15 100
    //                   16 100
    //                   17 100
    //                   18 100
    //                   19 100
    //                   20 387;
           goto _L1 _L2 _L1 _L3 _L4 _L5 _L1 _L1 _L1 _L1 _L6 _L1 _L7 _L8 _L9 _L1 _L1 _L1 _L1 _L1 _L10
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        int i = insertThreadValues(uri, acontentvalues);
_L12:
        return i;
_L3:
        for(int k1 = 0; k1 < acontentvalues.length; k1++)
            acontentvalues[k1].put("folder", Integer.valueOf(0));

        i = insertThreadValues(uri, acontentvalues);
        continue; /* Loop/switch isn't completed */
_L4:
        for(int j1 = 0; j1 < acontentvalues.length; j1++)
            acontentvalues[j1].put("folder", Integer.valueOf(1));

        i = insertThreadValues(uri, acontentvalues);
        continue; /* Loop/switch isn't completed */
_L5:
        for(int i1 = 0; i1 < acontentvalues.length; i1++)
            acontentvalues[i1].put("folder", Integer.valueOf(4));

        i = insertThreadValues(uri, acontentvalues);
        continue; /* Loop/switch isn't completed */
_L6:
        i = insertMessageValues(uri, acontentvalues);
        continue; /* Loop/switch isn't completed */
_L7:
        for(int l = 0; l < acontentvalues.length; l++)
            acontentvalues[l].put("folder", Integer.valueOf(0));

        i = insertMessageValues(uri, acontentvalues);
        continue; /* Loop/switch isn't completed */
_L8:
        for(int k = 0; k < acontentvalues.length; k++)
            acontentvalues[k].put("folder", Integer.valueOf(1));

        i = insertMessageValues(uri, acontentvalues);
        continue; /* Loop/switch isn't completed */
_L9:
        for(int j = 0; j < acontentvalues.length; j++)
            acontentvalues[j].put("folder", Integer.valueOf(4));

        i = insertMessageValues(uri, acontentvalues);
        continue; /* Loop/switch isn't completed */
_L10:
        i = insertProfileValues(uri, acontentvalues);
        if(true) goto _L12; else goto _L11
_L11:
    }

    public int delete(Uri uri, String s, String as[])
    {
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        URL_MATCHER.match(uri);
        JVM INSTR tableswitch 1 23: default 124
    //                   1 152
    //                   2 183
    //                   3 232
    //                   4 312
    //                   5 392
    //                   6 472
    //                   7 521
    //                   8 570
    //                   9 124
    //                   10 619
    //                   11 633
    //                   12 682
    //                   13 762
    //                   14 842
    //                   15 922
    //                   16 1044
    //                   17 1166
    //                   18 124
    //                   19 124
    //                   20 1288
    //                   21 1302
    //                   22 1351
    //                   23 1400;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L1 _L10 _L11 _L12 _L13 _L14 _L15 _L16 _L17 _L1 _L1 _L18 _L19 _L20 _L21
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        int i = sqlitedatabase.delete("mailbox_threads", s, as);
_L23:
        if(i > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return i;
_L3:
        String s19 = (String)uri.getPathSegments().get(1);
        i = sqlitedatabase.delete("mailbox_threads", (new StringBuilder()).append("_id=").append(s19).toString(), null);
        continue; /* Loop/switch isn't completed */
_L4:
        StringBuilder stringbuilder8 = (new StringBuilder()).append("folder=0");
        String s18;
        if(!TextUtils.isEmpty(s))
            s18 = (new StringBuilder()).append(" AND (").append(s).append(')').toString();
        else
            s18 = "";
        i = sqlitedatabase.delete("mailbox_threads", stringbuilder8.append(s18).toString(), as);
        continue; /* Loop/switch isn't completed */
_L5:
        StringBuilder stringbuilder7 = (new StringBuilder()).append("folder=1");
        String s17;
        if(!TextUtils.isEmpty(s))
            s17 = (new StringBuilder()).append(" AND (").append(s).append(')').toString();
        else
            s17 = "";
        i = sqlitedatabase.delete("mailbox_threads", stringbuilder7.append(s17).toString(), as);
        continue; /* Loop/switch isn't completed */
_L6:
        StringBuilder stringbuilder6 = (new StringBuilder()).append("folder=4");
        String s16;
        if(!TextUtils.isEmpty(s))
            s16 = (new StringBuilder()).append(" AND (").append(s).append(')').toString();
        else
            s16 = "";
        i = sqlitedatabase.delete("mailbox_threads", stringbuilder6.append(s16).toString(), as);
        continue; /* Loop/switch isn't completed */
_L7:
        String s15 = (String)uri.getPathSegments().get(3);
        i = sqlitedatabase.delete("mailbox_threads", (new StringBuilder()).append("folder=0 AND tid=").append(s15).toString(), null);
        continue; /* Loop/switch isn't completed */
_L8:
        String s14 = (String)uri.getPathSegments().get(3);
        i = sqlitedatabase.delete("mailbox_threads", (new StringBuilder()).append("folder=1 AND tid=").append(s14).toString(), null);
        continue; /* Loop/switch isn't completed */
_L9:
        String s13 = (String)uri.getPathSegments().get(3);
        i = sqlitedatabase.delete("mailbox_threads", (new StringBuilder()).append("folder=4 AND tid=").append(s13).toString(), null);
        continue; /* Loop/switch isn't completed */
_L10:
        i = sqlitedatabase.delete("mailbox_messages", s, as);
        continue; /* Loop/switch isn't completed */
_L11:
        String s12 = (String)uri.getPathSegments().get(1);
        i = sqlitedatabase.delete("mailbox_messages", (new StringBuilder()).append("_id=").append(s12).toString(), null);
        continue; /* Loop/switch isn't completed */
_L12:
        StringBuilder stringbuilder5 = (new StringBuilder()).append("folder=0");
        String s11;
        if(!TextUtils.isEmpty(s))
            s11 = (new StringBuilder()).append(" AND (").append(s).append(')').toString();
        else
            s11 = "";
        i = sqlitedatabase.delete("mailbox_messages", stringbuilder5.append(s11).toString(), as);
        continue; /* Loop/switch isn't completed */
_L13:
        StringBuilder stringbuilder4 = (new StringBuilder()).append("folder=1");
        String s10;
        if(!TextUtils.isEmpty(s))
            s10 = (new StringBuilder()).append(" AND (").append(s).append(')').toString();
        else
            s10 = "";
        i = sqlitedatabase.delete("mailbox_messages", stringbuilder4.append(s10).toString(), as);
        continue; /* Loop/switch isn't completed */
_L14:
        StringBuilder stringbuilder3 = (new StringBuilder()).append("folder=4");
        String s9;
        if(!TextUtils.isEmpty(s))
            s9 = (new StringBuilder()).append(" AND (").append(s).append(')').toString();
        else
            s9 = "";
        i = sqlitedatabase.delete("mailbox_messages", stringbuilder3.append(s9).toString(), as);
        continue; /* Loop/switch isn't completed */
_L15:
        String s7 = (String)uri.getPathSegments().get(3);
        StringBuilder stringbuilder2 = (new StringBuilder()).append("tid=").append(s7).append(" AND ").append("folder").append("=").append(0);
        String s8;
        if(!TextUtils.isEmpty(s))
            s8 = (new StringBuilder()).append(" AND (").append(s).append(')').toString();
        else
            s8 = "";
        i = sqlitedatabase.delete("mailbox_messages", stringbuilder2.append(s8).toString(), as);
        continue; /* Loop/switch isn't completed */
_L16:
        String s5 = (String)uri.getPathSegments().get(3);
        StringBuilder stringbuilder1 = (new StringBuilder()).append("tid=").append(s5).append(" AND ").append("folder").append("=").append(1);
        String s6;
        if(!TextUtils.isEmpty(s))
            s6 = (new StringBuilder()).append(" AND (").append(s).append(')').toString();
        else
            s6 = "";
        i = sqlitedatabase.delete("mailbox_messages", stringbuilder1.append(s6).toString(), as);
        continue; /* Loop/switch isn't completed */
_L17:
        String s3 = (String)uri.getPathSegments().get(3);
        StringBuilder stringbuilder = (new StringBuilder()).append("tid=").append(s3).append(" AND ").append("folder").append("=").append(4);
        String s4;
        if(!TextUtils.isEmpty(s))
            s4 = (new StringBuilder()).append(" AND (").append(s).append(')').toString();
        else
            s4 = "";
        i = sqlitedatabase.delete("mailbox_messages", stringbuilder.append(s4).toString(), as);
        continue; /* Loop/switch isn't completed */
_L18:
        i = sqlitedatabase.delete("mailbox_profiles", s, as);
        continue; /* Loop/switch isn't completed */
_L19:
        String s2 = (String)uri.getPathSegments().get(1);
        i = sqlitedatabase.delete("mailbox_profiles", (new StringBuilder()).append("_id=").append(s2).toString(), null);
        continue; /* Loop/switch isn't completed */
_L20:
        String s1 = (String)uri.getPathSegments().get(2);
        i = sqlitedatabase.delete("mailbox_profiles", (new StringBuilder()).append("id=").append(s1).toString(), null);
        continue; /* Loop/switch isn't completed */
_L21:
        i = sqlitedatabase.delete("mailbox_profiles", "id NOT IN (SELECT DISTINCT author_id FROM mailbox_messages)", null);
        if(true) goto _L23; else goto _L22
_L22:
    }

    public String getType(Uri uri)
    {
        URL_MATCHER.match(uri);
        JVM INSTR tableswitch 1 22: default 108
    //                   1 136
    //                   2 136
    //                   3 136
    //                   4 136
    //                   5 136
    //                   6 136
    //                   7 136
    //                   8 136
    //                   9 108
    //                   10 142
    //                   11 142
    //                   12 142
    //                   13 142
    //                   14 142
    //                   15 142
    //                   16 142
    //                   17 142
    //                   18 108
    //                   19 108
    //                   20 149
    //                   21 149
    //                   22 149;
           goto _L1 _L2 _L2 _L2 _L2 _L2 _L2 _L2 _L2 _L1 _L3 _L3 _L3 _L3 _L3 _L3 _L3 _L3 _L1 _L1 _L4 _L4 _L4
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        String s = "vnd.android.cursor.dir/vnd.facebook.mailboxthreads";
_L6:
        return s;
_L3:
        s = "vnd.android.cursor.dir/vnd.facebook.mailboxmessages";
        continue; /* Loop/switch isn't completed */
_L4:
        s = "vnd.android.cursor.dir/vnd.facebook.mailboxprofiles";
        if(true) goto _L6; else goto _L5
_L5:
    }

    public Uri insert(Uri uri, ContentValues contentvalues)
    {
        ContentValues contentvalues1;
        if(contentvalues != null)
            contentvalues1 = new ContentValues(contentvalues);
        else
            contentvalues1 = new ContentValues();
        URL_MATCHER.match(uri);
        JVM INSTR tableswitch 1 20: default 116
    //                   1 155
    //                   2 116
    //                   3 166
    //                   4 188
    //                   5 210
    //                   6 116
    //                   7 116
    //                   8 116
    //                   9 116
    //                   10 232
    //                   11 116
    //                   12 243
    //                   13 265
    //                   14 287
    //                   15 116
    //                   16 116
    //                   17 116
    //                   18 116
    //                   19 116
    //                   20 309;
           goto _L1 _L2 _L1 _L3 _L4 _L5 _L1 _L1 _L1 _L1 _L6 _L1 _L7 _L8 _L9 _L1 _L1 _L1 _L1 _L1 _L10
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        Uri uri1 = insertThreadValues(uri, contentvalues1);
_L12:
        return uri1;
_L3:
        contentvalues1.put("folder", Integer.valueOf(0));
        uri1 = insertThreadValues(uri, contentvalues1);
        continue; /* Loop/switch isn't completed */
_L4:
        contentvalues1.put("folder", Integer.valueOf(1));
        uri1 = insertThreadValues(uri, contentvalues1);
        continue; /* Loop/switch isn't completed */
_L5:
        contentvalues1.put("folder", Integer.valueOf(4));
        uri1 = insertThreadValues(uri, contentvalues1);
        continue; /* Loop/switch isn't completed */
_L6:
        uri1 = insertMessageValues(uri, contentvalues1);
        continue; /* Loop/switch isn't completed */
_L7:
        contentvalues1.put("folder", Integer.valueOf(0));
        uri1 = insertMessageValues(uri, contentvalues1);
        continue; /* Loop/switch isn't completed */
_L8:
        contentvalues1.put("folder", Integer.valueOf(1));
        uri1 = insertMessageValues(uri, contentvalues1);
        continue; /* Loop/switch isn't completed */
_L9:
        contentvalues1.put("folder", Integer.valueOf(4));
        uri1 = insertMessageValues(uri, contentvalues1);
        continue; /* Loop/switch isn't completed */
_L10:
        uri1 = insertProfileValues(uri, contentvalues1);
        if(true) goto _L12; else goto _L11
_L11:
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
        JVM INSTR tableswitch 1 22: default 120
    //                   1 148
    //                   2 210
    //                   3 265
    //                   4 290
    //                   5 315
    //                   6 340
    //                   7 395
    //                   8 450
    //                   9 120
    //                   10 505
    //                   11 530
    //                   12 585
    //                   13 618
    //                   14 651
    //                   15 684
    //                   16 738
    //                   17 792
    //                   18 120
    //                   19 120
    //                   20 846
    //                   21 871
    //                   22 926;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L1 _L10 _L11 _L12 _L13 _L14 _L15 _L16 _L17 _L1 _L1 _L18 _L19 _L20
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        sqlitequerybuilder.setTables("mailbox_threads");
        sqlitequerybuilder.setProjectionMap(MAILBOX_THREADS_PROJECTION_MAP);
        s2 = setThreadsOrderBy(s1);
_L22:
        Cursor cursor = sqlitequerybuilder.query(mDbHelper.getReadableDatabase(), as, s, as1, null, null, s2);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
_L3:
        sqlitequerybuilder.setTables("mailbox_threads");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("_id=").append((String)uri.getPathSegments().get(1)).toString());
        sqlitequerybuilder.setProjectionMap(MAILBOX_THREADS_PROJECTION_MAP);
        continue; /* Loop/switch isn't completed */
_L4:
        sqlitequerybuilder.setTables("mailbox_threads LEFT OUTER JOIN mailbox_profiles ON mailbox_threads.other_party=mailbox_profiles.id");
        sqlitequerybuilder.appendWhere("folder=0");
        s2 = setThreadsOrderBy(s1);
        continue; /* Loop/switch isn't completed */
_L5:
        sqlitequerybuilder.setTables("mailbox_threads LEFT OUTER JOIN mailbox_profiles ON mailbox_threads.other_party=mailbox_profiles.id");
        sqlitequerybuilder.appendWhere("folder=1");
        s2 = setThreadsOrderBy(s1);
        continue; /* Loop/switch isn't completed */
_L6:
        sqlitequerybuilder.setTables("mailbox_threads LEFT OUTER JOIN mailbox_profiles ON mailbox_threads.other_party=mailbox_profiles.id");
        sqlitequerybuilder.appendWhere("folder=4");
        s2 = setThreadsOrderBy(s1);
        continue; /* Loop/switch isn't completed */
_L7:
        sqlitequerybuilder.setTables("mailbox_threads");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("folder=0 AND tid=").append((String)uri.getPathSegments().get(3)).toString());
        sqlitequerybuilder.setProjectionMap(MAILBOX_THREADS_PROJECTION_MAP);
        continue; /* Loop/switch isn't completed */
_L8:
        sqlitequerybuilder.setTables("mailbox_threads");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("folder=1 AND tid=").append((String)uri.getPathSegments().get(3)).toString());
        sqlitequerybuilder.setProjectionMap(MAILBOX_THREADS_PROJECTION_MAP);
        continue; /* Loop/switch isn't completed */
_L9:
        sqlitequerybuilder.setTables("mailbox_threads");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("folder=4 AND tid=").append((String)uri.getPathSegments().get(3)).toString());
        sqlitequerybuilder.setProjectionMap(MAILBOX_THREADS_PROJECTION_MAP);
        continue; /* Loop/switch isn't completed */
_L10:
        sqlitequerybuilder.setTables("mailbox_messages_display");
        sqlitequerybuilder.setProjectionMap(MAILBOX_MESSAGES_PROJECTION_MAP);
        s2 = setMessagesOrderBy(s1);
        continue; /* Loop/switch isn't completed */
_L11:
        sqlitequerybuilder.setTables("mailbox_messages_display");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("_id=").append((String)uri.getPathSegments().get(1)).toString());
        sqlitequerybuilder.setProjectionMap(MAILBOX_MESSAGES_PROJECTION_MAP);
        continue; /* Loop/switch isn't completed */
_L12:
        sqlitequerybuilder.setTables("mailbox_messages_display");
        sqlitequerybuilder.appendWhere("folder=0");
        sqlitequerybuilder.setProjectionMap(MAILBOX_MESSAGES_PROJECTION_MAP);
        s2 = setMessagesOrderBy(s1);
        continue; /* Loop/switch isn't completed */
_L13:
        sqlitequerybuilder.setTables("mailbox_messages_display");
        sqlitequerybuilder.appendWhere("folder=1");
        sqlitequerybuilder.setProjectionMap(MAILBOX_MESSAGES_PROJECTION_MAP);
        s2 = setMessagesOrderBy(s1);
        continue; /* Loop/switch isn't completed */
_L14:
        sqlitequerybuilder.setTables("mailbox_messages_display");
        sqlitequerybuilder.appendWhere("folder=4");
        sqlitequerybuilder.setProjectionMap(MAILBOX_MESSAGES_PROJECTION_MAP);
        s2 = setMessagesOrderBy(s1);
        continue; /* Loop/switch isn't completed */
_L15:
        sqlitequerybuilder.setTables("mailbox_messages_display");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("folder=0 AND tid=").append((String)uri.getPathSegments().get(3)).toString());
        s2 = setMessagesOrderBy(s1);
        continue; /* Loop/switch isn't completed */
_L16:
        sqlitequerybuilder.setTables("mailbox_messages_display");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("folder=1 AND tid=").append((String)uri.getPathSegments().get(3)).toString());
        s2 = setMessagesOrderBy(s1);
        continue; /* Loop/switch isn't completed */
_L17:
        sqlitequerybuilder.setTables("mailbox_messages_display");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("folder=4 AND tid=").append((String)uri.getPathSegments().get(3)).toString());
        s2 = setMessagesOrderBy(s1);
        continue; /* Loop/switch isn't completed */
_L18:
        sqlitequerybuilder.setTables("mailbox_profiles");
        sqlitequerybuilder.setProjectionMap(MAILBOX_PROFILES_PROJECTION_MAP);
        s2 = setProfilesOrderBy(s1);
        continue; /* Loop/switch isn't completed */
_L19:
        sqlitequerybuilder.setTables("mailbox_profiles");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("_id=").append((String)uri.getPathSegments().get(1)).toString());
        sqlitequerybuilder.setProjectionMap(MAILBOX_PROFILES_PROJECTION_MAP);
        continue; /* Loop/switch isn't completed */
_L20:
        sqlitequerybuilder.setTables("mailbox_profiles");
        sqlitequerybuilder.appendWhere((new StringBuilder()).append("id=").append((String)uri.getPathSegments().get(2)).toString());
        sqlitequerybuilder.setProjectionMap(MAILBOX_PROFILES_PROJECTION_MAP);
        if(true) goto _L22; else goto _L21
_L21:
    }

    public int update(Uri uri, ContentValues contentvalues, String s, String as[])
    {
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        URL_MATCHER.match(uri);
        JVM INSTR tableswitch 1 22: default 120
    //                   1 148
    //                   2 181
    //                   3 231
    //                   4 313
    //                   5 395
    //                   6 477
    //                   7 527
    //                   8 577
    //                   9 120
    //                   10 627
    //                   11 643
    //                   12 693
    //                   13 775
    //                   14 857
    //                   15 939
    //                   16 1011
    //                   17 1083
    //                   18 120
    //                   19 120
    //                   20 1155
    //                   21 1171
    //                   22 1221;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L1 _L10 _L11 _L12 _L13 _L14 _L15 _L16 _L17 _L1 _L1 _L18 _L19 _L20
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
_L2:
        int i = sqlitedatabase.update("mailbox_threads", contentvalues, s, as);
_L22:
        if(i > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return i;
_L3:
        String s16 = (String)uri.getPathSegments().get(1);
        i = sqlitedatabase.update("mailbox_threads", contentvalues, (new StringBuilder()).append("_id=").append(s16).toString(), null);
        continue; /* Loop/switch isn't completed */
_L4:
        StringBuilder stringbuilder5 = (new StringBuilder()).append("folder=0");
        String s15;
        if(!TextUtils.isEmpty(s))
            s15 = (new StringBuilder()).append(" AND (").append(s).append(')').toString();
        else
            s15 = "";
        i = sqlitedatabase.update("mailbox_threads", contentvalues, stringbuilder5.append(s15).toString(), as);
        continue; /* Loop/switch isn't completed */
_L5:
        StringBuilder stringbuilder4 = (new StringBuilder()).append("folder=1");
        String s14;
        if(!TextUtils.isEmpty(s))
            s14 = (new StringBuilder()).append(" AND (").append(s).append(')').toString();
        else
            s14 = "";
        i = sqlitedatabase.update("mailbox_threads", contentvalues, stringbuilder4.append(s14).toString(), as);
        continue; /* Loop/switch isn't completed */
_L6:
        StringBuilder stringbuilder3 = (new StringBuilder()).append("folder=4");
        String s13;
        if(!TextUtils.isEmpty(s))
            s13 = (new StringBuilder()).append(" AND (").append(s).append(')').toString();
        else
            s13 = "";
        i = sqlitedatabase.update("mailbox_threads", contentvalues, stringbuilder3.append(s13).toString(), as);
        continue; /* Loop/switch isn't completed */
_L7:
        String s12 = (String)uri.getPathSegments().get(3);
        i = sqlitedatabase.update("mailbox_threads", contentvalues, (new StringBuilder()).append("folder=0 AND tid=").append(s12).toString(), null);
        continue; /* Loop/switch isn't completed */
_L8:
        String s11 = (String)uri.getPathSegments().get(3);
        i = sqlitedatabase.update("mailbox_threads", contentvalues, (new StringBuilder()).append("folder=1 AND tid=").append(s11).toString(), null);
        continue; /* Loop/switch isn't completed */
_L9:
        String s10 = (String)uri.getPathSegments().get(3);
        i = sqlitedatabase.update("mailbox_threads", contentvalues, (new StringBuilder()).append("folder=4 AND tid=").append(s10).toString(), null);
        continue; /* Loop/switch isn't completed */
_L10:
        i = sqlitedatabase.update("mailbox_messages", contentvalues, s, as);
        continue; /* Loop/switch isn't completed */
_L11:
        String s9 = (String)uri.getPathSegments().get(1);
        i = sqlitedatabase.update("mailbox_messages", contentvalues, (new StringBuilder()).append("_id=").append(s9).toString(), null);
        continue; /* Loop/switch isn't completed */
_L12:
        StringBuilder stringbuilder2 = (new StringBuilder()).append("folder=0");
        String s8;
        if(!TextUtils.isEmpty(s))
            s8 = (new StringBuilder()).append(" AND (").append(s).append(')').toString();
        else
            s8 = "";
        i = sqlitedatabase.update("mailbox_messages", contentvalues, stringbuilder2.append(s8).toString(), as);
        continue; /* Loop/switch isn't completed */
_L13:
        StringBuilder stringbuilder1 = (new StringBuilder()).append("folder=1");
        String s7;
        if(!TextUtils.isEmpty(s))
            s7 = (new StringBuilder()).append(" AND (").append(s).append(')').toString();
        else
            s7 = "";
        i = sqlitedatabase.update("mailbox_messages", contentvalues, stringbuilder1.append(s7).toString(), as);
        continue; /* Loop/switch isn't completed */
_L14:
        StringBuilder stringbuilder = (new StringBuilder()).append("folder=4");
        String s6;
        if(!TextUtils.isEmpty(s))
            s6 = (new StringBuilder()).append(" AND (").append(s).append(')').toString();
        else
            s6 = "";
        i = sqlitedatabase.update("mailbox_messages", contentvalues, stringbuilder.append(s6).toString(), as);
        continue; /* Loop/switch isn't completed */
_L15:
        String s5 = (String)uri.getPathSegments().get(3);
        i = sqlitedatabase.update("mailbox_messages", contentvalues, (new StringBuilder()).append("tid=").append(s5).append(" AND ").append("folder").append("=").append(0).toString(), null);
        continue; /* Loop/switch isn't completed */
_L16:
        String s4 = (String)uri.getPathSegments().get(3);
        i = sqlitedatabase.update("mailbox_messages", contentvalues, (new StringBuilder()).append("tid=").append(s4).append(" AND ").append("folder").append("=").append(1).toString(), null);
        continue; /* Loop/switch isn't completed */
_L17:
        String s3 = (String)uri.getPathSegments().get(3);
        i = sqlitedatabase.update("mailbox_messages", contentvalues, (new StringBuilder()).append("tid=").append(s3).append(" AND ").append("folder").append("=").append(4).toString(), null);
        continue; /* Loop/switch isn't completed */
_L18:
        i = sqlitedatabase.update("mailbox_profiles", contentvalues, s, as);
        continue; /* Loop/switch isn't completed */
_L19:
        String s2 = (String)uri.getPathSegments().get(1);
        i = sqlitedatabase.update("mailbox_profiles", contentvalues, (new StringBuilder()).append("_id=").append(s2).toString(), null);
        continue; /* Loop/switch isn't completed */
_L20:
        String s1 = (String)uri.getPathSegments().get(2);
        i = sqlitedatabase.update("mailbox_profiles", contentvalues, (new StringBuilder()).append("id=").append(s1).toString(), null);
        if(true) goto _L22; else goto _L21
_L21:
    }

    private static final String AUTHORITY = "com.facebook.katana.provider.MailboxProvider";
    private static final String CONTENT_SCHEME = "content://";
    public static final int FOLDER_INBOX = 0;
    public static final int FOLDER_OUTBOX = 1;
    private static final Map FOLDER_PATH_MAP;
    public static final int FOLDER_UPDATES = 4;
    public static final Uri INBOX_MESSAGES_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_messages/inbox");
    public static final Uri INBOX_MESSAGES_TID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_messages/inbox/tid");
    private static final String INBOX_PATH = "/inbox";
    public static final Uri INBOX_THREADS_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_threads/inbox");
    public static final Uri INBOX_THREADS_TID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_threads/inbox/tid");
    private static final int MAILBOX_MESSAGES = 10;
    private static final int MAILBOX_MESSAGES_INBOX = 12;
    private static final int MAILBOX_MESSAGES_INBOX_TID = 15;
    private static final int MAILBOX_MESSAGES_OUTBOX = 13;
    private static final int MAILBOX_MESSAGES_OUTBOX_TID = 16;
    public static final HashMap MAILBOX_MESSAGES_PROJECTION_MAP;
    private static final int MAILBOX_MESSAGES_UPDATES = 14;
    private static final int MAILBOX_MESSAGES_UPDATES_TID = 17;
    private static final int MAILBOX_MESSAGE_ID = 11;
    private static final int MAILBOX_PROFILES = 20;
    public static final HashMap MAILBOX_PROFILES_PROJECTION_MAP;
    private static final int MAILBOX_PROFILES_PRUNE = 23;
    private static final int MAILBOX_PROFILE_ID = 22;
    private static final int MAILBOX_PROFILE__ID = 21;
    private static final int MAILBOX_THREADS = 1;
    private static final int MAILBOX_THREADS_INBOX = 3;
    private static final int MAILBOX_THREADS_OUTBOX = 4;
    public static final HashMap MAILBOX_THREADS_PROJECTION_MAP;
    private static final int MAILBOX_THREADS_UPDATES = 5;
    private static final int MAILBOX_THREAD_ID = 2;
    private static final int MAILBOX_THREAD_INBOX_TID = 6;
    private static final int MAILBOX_THREAD_OUTBOX_TID = 7;
    private static final int MAILBOX_THREAD_UPDATES_TID = 8;
    private static final String MESSAGES_BASE_URI = "content://com.facebook.katana.provider.MailboxProvider/mailbox_messages";
    public static final Uri MESSAGES_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_messages");
    public static final Uri OUTBOX_MESSAGES_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_messages/outbox");
    public static final Uri OUTBOX_MESSAGES_TID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_messages/outbox/tid");
    private static final String OUTBOX_PATH = "/outbox";
    public static final Uri OUTBOX_THREADS_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_threads/outbox");
    public static final Uri OUTBOX_THREADS_TID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_threads/outbox/tid");
    private static final String PROFILES_BASE_URI = "content://com.facebook.katana.provider.MailboxProvider/mailbox_profiles";
    public static final Uri PROFILES_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_profiles");
    public static final Uri PROFILES_ID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_profiles/id");
    public static final Uri PROFILES_PRUNE_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_profiles/prune");
    private static final String SQL_MAILBOX_MESSAGES = "CREATE TABLE mailbox_messages (_id INTEGER PRIMARY KEY,folder INT,tid INT,mid INT,author_id INT,sent INT,body TEXT);";
    private static final String SQL_MAILBOX_MESSAGES_DISPLAY = "CREATE VIEW mailbox_messages_display AS SELECT mailbox_messages._id AS _id, mailbox_messages.mid AS mid, mailbox_messages.folder AS folder, mailbox_messages.tid AS tid, mailbox_messages.sent AS sent, mailbox_messages.body AS body, author.id as author_id, author.display_name as author_name, author.profile_image_url AS author_image_url, author.type AS author_type, object.id as object_id, object.display_name as object_name, object.profile_image_url AS object_image_url, object.type AS object_type FROM mailbox_messages LEFT OUTER JOIN mailbox_threads ON mailbox_messages.tid = mailbox_threads.tid AND mailbox_messages.folder = mailbox_threads.folder LEFT OUTER JOIN mailbox_profiles AS object ON mailbox_threads.object_id = object.id LEFT OUTER JOIN mailbox_profiles AS author ON mailbox_messages.author_id = author.id;";
    private static final String SQL_MAILBOX_PROFILES = "CREATE TABLE mailbox_profiles (_id INTEGER PRIMARY KEY,id INT,display_name TEXT,profile_image_url TEXT,type INT);";
    private static final String SQL_MAILBOX_THREADS = "CREATE TABLE mailbox_threads (_id INTEGER PRIMARY KEY,folder INT,tid INT,participants TEXT,subject TEXT,snippet TEXT,other_party INT,msg_count INT,unread_count INT,last_update INT,object_id INT);";
    private static final String SQL_PROFILES_ID_INDEX = "CREATE INDEX mailbox_profiles_id ON mailbox_profiles(id);";
    private static final String SQL_THREADS_TID_INDEX = "CREATE INDEX mailbox_threads_tid ON mailbox_threads(tid);";
    private static final String TABLE_MAILBOX_MESSAGES = "mailbox_messages";
    private static final String TABLE_MAILBOX_MESSAGES_DISPLAY = "mailbox_messages_display";
    private static final String TABLE_MAILBOX_PROFILES = "mailbox_profiles";
    private static final String TABLE_MAILBOX_THREADS = "mailbox_threads";
    private static final String TABLE_MAILBOX_THREADS_PROFILES = "mailbox_threads LEFT OUTER JOIN mailbox_profiles ON mailbox_threads.other_party=mailbox_profiles.id";
    private static final String THREADS_BASE_URI = "content://com.facebook.katana.provider.MailboxProvider/mailbox_threads";
    public static final Uri THREADS_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_threads");
    public static final Uri UPDATES_MESSAGES_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_messages/updates");
    public static final Uri UPDATES_MESSAGES_TID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_messages/updates/tid");
    private static final String UPDATES_PATH = "/updates";
    public static final Uri UPDATES_THREADS_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_threads/updates");
    public static final Uri UPDATES_THREADS_TID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.MailboxProvider/mailbox_threads/updates/tid");
    private static final UriMatcher URL_MATCHER;
    private FacebookDatabaseHelper mDbHelper;

    static 
    {
        FOLDER_PATH_MAP = new HashMap();
        FOLDER_PATH_MAP.put(Integer.valueOf(0), "/inbox");
        FOLDER_PATH_MAP.put(Integer.valueOf(1), "/outbox");
        FOLDER_PATH_MAP.put(Integer.valueOf(4), "/updates");
        URL_MATCHER = new UriMatcher(-1);
        URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_threads", 1);
        URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_threads/#", 2);
        URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_threads/inbox", 3);
        URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_threads/outbox", 4);
        URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_threads/updates", 5);
        URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_threads/inbox/tid/#", 6);
        URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_threads/outbox/tid/#", 7);
        URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_threads/updates/tid/#", 8);
        URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_messages", 10);
        URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_messages/#", 11);
        URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_messages/inbox", 12);
        URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_messages/outbox", 13);
        URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_messages/updates", 14);
        URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_messages/inbox/tid/#", 15);
        URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_messages/outbox/tid/#", 16);
        URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_messages/updates/tid/#", 17);
        URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_profiles", 20);
        URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_profiles/#", 21);
        URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_profiles/id/#", 22);
        URL_MATCHER.addURI("com.facebook.katana.provider.MailboxProvider", "mailbox_profiles/prune", 23);
        MAILBOX_THREADS_PROJECTION_MAP = new HashMap();
        MAILBOX_THREADS_PROJECTION_MAP.put("_id", "_id");
        MAILBOX_THREADS_PROJECTION_MAP.put("mailbox_threads._id", "_id");
        MAILBOX_THREADS_PROJECTION_MAP.put("folder", "folder");
        MAILBOX_THREADS_PROJECTION_MAP.put("tid", "tid");
        MAILBOX_THREADS_PROJECTION_MAP.put("participants", "participants");
        MAILBOX_THREADS_PROJECTION_MAP.put("subject", "subject");
        MAILBOX_THREADS_PROJECTION_MAP.put("snippet", "snippet");
        MAILBOX_THREADS_PROJECTION_MAP.put("other_party", "other_party");
        MAILBOX_THREADS_PROJECTION_MAP.put("msg_count", "msg_count");
        MAILBOX_THREADS_PROJECTION_MAP.put("unread_count", "unread_count");
        MAILBOX_THREADS_PROJECTION_MAP.put("last_update", "last_update");
        MAILBOX_MESSAGES_PROJECTION_MAP = new HashMap();
        MAILBOX_MESSAGES_PROJECTION_MAP.put("_id", "_id");
        MAILBOX_MESSAGES_PROJECTION_MAP.put("mailbox_messages._id", "_id");
        MAILBOX_MESSAGES_PROJECTION_MAP.put("folder", "folder");
        MAILBOX_MESSAGES_PROJECTION_MAP.put("tid", "tid");
        MAILBOX_MESSAGES_PROJECTION_MAP.put("mid", "mid");
        MAILBOX_MESSAGES_PROJECTION_MAP.put("author_id", "author_id");
        MAILBOX_MESSAGES_PROJECTION_MAP.put("sent", "sent");
        MAILBOX_MESSAGES_PROJECTION_MAP.put("body", "body");
        MAILBOX_PROFILES_PROJECTION_MAP = new HashMap();
        MAILBOX_PROFILES_PROJECTION_MAP.put("_id", "_id");
        MAILBOX_PROFILES_PROJECTION_MAP.put("id", "id");
        MAILBOX_PROFILES_PROJECTION_MAP.put("display_name", "display_name");
        MAILBOX_PROFILES_PROJECTION_MAP.put("profile_image_url", "profile_image_url");
    }
}
