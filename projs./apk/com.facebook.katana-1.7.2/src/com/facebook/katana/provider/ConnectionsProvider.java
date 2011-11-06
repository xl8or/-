// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConnectionsProvider.java

package com.facebook.katana.provider;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.platform.PlatformStorage;
import com.facebook.katana.util.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

// Referenced classes of package com.facebook.katana.provider:
//            FacebookDatabaseHelper

public class ConnectionsProvider extends ContentProvider
{
    private static final class Selectors extends Enum
    {

        public static Selectors valueOf(String s)
        {
            return (Selectors)Enum.valueOf(com/facebook/katana/provider/ConnectionsProvider$Selectors, s);
        }

        public static Selectors[] values()
        {
            return (Selectors[])$VALUES.clone();
        }

        public int uriMatcherIndex()
        {
            return 1 + ordinal();
        }

        private static final Selectors $VALUES[];
        public static final Selectors CONNECTIONS_CONTENT;
        public static final Selectors CONNECTION_ID;
        public static final Selectors FRIENDS_BIRTHDAYS;
        public static final Selectors FRIENDS_CONTENT;
        public static final Selectors FRIENDS_SEARCH;
        public static final Selectors FRIEND_UID;
        public static final Selectors PAGES_CONTENT;
        public static final Selectors PAGES_SEARCH;
        public static final Selectors PAGE_ID;
        public static final Selectors SEARCH_CONTENT;
        final String category;
        final String uriMatcherSuffix;
        final String uriSuffix;

        static 
        {
            CONNECTIONS_CONTENT = new Selectors("CONNECTIONS_CONTENT", 0, "connections", "", "");
            CONNECTION_ID = new Selectors("CONNECTION_ID", 1, "connections", "/id", "/#");
            FRIENDS_CONTENT = new Selectors("FRIENDS_CONTENT", 2, "friends", "", "");
            FRIEND_UID = new Selectors("FRIEND_UID", 3, "friends", "/uid", "/#");
            FRIENDS_SEARCH = new Selectors("FRIENDS_SEARCH", 4, "friends", "/search", "/*");
            FRIENDS_BIRTHDAYS = new Selectors("FRIENDS_BIRTHDAYS", 5, "friends", "/birthdays", "");
            PAGES_CONTENT = new Selectors("PAGES_CONTENT", 6, "pages", "", "");
            PAGE_ID = new Selectors("PAGE_ID", 7, "pages", "/id", "/#");
            PAGES_SEARCH = new Selectors("PAGES_SEARCH", 8, "pages", "/search", "/*");
            SEARCH_CONTENT = new Selectors("SEARCH_CONTENT", 9, "search_results", "", "");
            Selectors aselectors[] = new Selectors[10];
            aselectors[0] = CONNECTIONS_CONTENT;
            aselectors[1] = CONNECTION_ID;
            aselectors[2] = FRIENDS_CONTENT;
            aselectors[3] = FRIEND_UID;
            aselectors[4] = FRIENDS_SEARCH;
            aselectors[5] = FRIENDS_BIRTHDAYS;
            aselectors[6] = PAGES_CONTENT;
            aselectors[7] = PAGE_ID;
            aselectors[8] = PAGES_SEARCH;
            aselectors[9] = SEARCH_CONTENT;
            $VALUES = aselectors;
        }

        private Selectors(String s, int i, String s1, String s2, String s3)
        {
            super(s, i);
            category = s1;
            uriSuffix = s2;
            uriMatcherSuffix = s3;
        }
    }

    public static final class ConnectionType extends Enum
    {

        public static ConnectionType valueOf(String s)
        {
            return (ConnectionType)Enum.valueOf(com/facebook/katana/provider/ConnectionsProvider$ConnectionType, s);
        }

        public static ConnectionType[] values()
        {
            return (ConnectionType[])$VALUES.clone();
        }

        private static final ConnectionType $VALUES[];
        public static final ConnectionType PAGE_ADMIN;
        public static final ConnectionType PAGE_FAN;
        public static final ConnectionType USER;

        static 
        {
            USER = new ConnectionType("USER", 0);
            PAGE_ADMIN = new ConnectionType("PAGE_ADMIN", 1);
            PAGE_FAN = new ConnectionType("PAGE_FAN", 2);
            ConnectionType aconnectiontype[] = new ConnectionType[3];
            aconnectiontype[0] = USER;
            aconnectiontype[1] = PAGE_ADMIN;
            aconnectiontype[2] = PAGE_FAN;
            $VALUES = aconnectiontype;
        }

        private ConnectionType(String s, int i)
        {
            super(s, i);
        }
    }

    public static final class BirthdayColumns extends FriendColumns
    {

        public static final String NORMALIZED_BIRTHDAY_DAY = "normalized_birthday_day";

        public BirthdayColumns()
        {
        }
    }

    public static class FriendColumns extends ConnectionColumns
    {

        public static final String BIRTHDAY_DAY = "birthday_day";
        public static final String BIRTHDAY_MONTH = "birthday_month";
        public static final String BIRTHDAY_YEAR = "birthday_year";
        public static final String CELL = "cell";
        public static final String DEFAULT_SORT_ORDER = "user_id DESC";
        public static final String EMAIL = "email";
        public static final String OTHER_PHONE = "other";
        public static final String USER_FIRST_NAME = "first_name";
        public static final String USER_LAST_NAME = "last_name";

        public FriendColumns()
        {
        }
    }

    public static final class SearchResultColumns extends ProfileColumns
    {

        public static final String DEFAULT_SORT_ORDER = "_id ASC";

        public SearchResultColumns()
        {
        }
    }

    public static class ConnectionColumns extends ProfileColumns
    {

        public static final String CONNECTION_TYPE = "connection_type";
        public static final String HASH = "hash";
        public static final String IMAGE = "user_image";

        public ConnectionColumns()
        {
        }
    }

    public static class ProfileColumns
        implements BaseColumns
    {

        public static final String DEFAULT_SORT_ORDER = "display_name ASC";
        public static final String DISPLAY_NAME = "display_name";
        public static final String ID = "user_id";
        public static final String IMAGE_URL = "user_image_url";

        public ProfileColumns()
        {
        }
    }


    public ConnectionsProvider()
    {
    }

    public static FacebookProfile getAdminProfile(Context context, long l)
    {
        String as[] = new String[4];
        as[0] = "user_id";
        as[1] = "display_name";
        as[2] = "user_image_url";
        as[3] = "connection_type";
        Cursor cursor = context.getContentResolver().query(Uri.withAppendedPath(PAGE_ID_CONTENT_URI, String.valueOf(l)), as, null, null, null);
        FacebookProfile facebookprofile;
        if(cursor.moveToFirst() && cursor.getInt(cursor.getColumnIndex("connection_type")) == ConnectionType.PAGE_ADMIN.ordinal())
            facebookprofile = new FacebookProfile(cursor.getInt(cursor.getColumnIndex("user_id")), cursor.getString(cursor.getColumnIndex("display_name")), cursor.getString(cursor.getColumnIndex("user_image_url")), 1);
        else
            facebookprofile = null;
        return facebookprofile;
    }

    public static FacebookProfile getFriendProfileFromId(Context context, long l)
    {
        String as[] = new String[2];
        as[0] = "display_name";
        as[1] = "user_image_url";
        Cursor cursor = context.getContentResolver().query(ContentUris.withAppendedId(FRIEND_UID_CONTENT_URI, l), as, null, null, null);
        FacebookProfile facebookprofile;
        if(cursor.moveToFirst())
            facebookprofile = new FacebookProfile(l, cursor.getString(cursor.getColumnIndex("display_name")), cursor.getString(cursor.getColumnIndex("user_image_url")), 0);
        else
            facebookprofile = null;
        return facebookprofile;
    }

    public static String[] getTableNames()
    {
        String as[] = new String[3];
        as[0] = "connections";
        as[1] = "friends_data";
        as[2] = "search_results";
        return as;
    }

    public static String[] getTableSQLs()
    {
        String as[] = new String[4];
        as[0] = SQL_CONNECTIONS;
        as[1] = "CREATE TABLE friends_data (_id INTEGER PRIMARY KEY,user_id INT UNIQUE,first_name TEXT,last_name TEXT,cell TEXT,other TEXT,email TEXT,birthday_month INT,birthday_day INT,birthday_year INT);";
        as[2] = "CREATE TABLE search_results (_id INTEGER PRIMARY KEY,user_id INT,display_name TEXT,user_image_url TEXT);";
        as[3] = SQL_FRIENDS_VIEW;
        return as;
    }

    static String[] getViewNames()
    {
        String as[] = new String[1];
        as[0] = "friends";
        return as;
    }

    public static ProfileImage updateImage(Context context, long l, String s, String s1)
        throws IOException
    {
        android.graphics.Bitmap bitmap = ImageUtils.decodeFile(s1, new android.graphics.BitmapFactory.Options());
        if(bitmap == null)
            throw new IOException("Cannot decode bitmap");
        byte abyte0[] = FileUtils.getBytesFromFile(new File(s1));
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("user_image", abyte0);
        contentvalues.put("user_image_url", s);
        Uri uri = ContentUris.withAppendedId(CONNECTION_ID_CONTENT_URI, l);
        context.getContentResolver().update(uri, contentvalues, null, null);
        if(PlatformUtils.platformStorageSupported(context))
            PlatformStorage.updateContactPhoto(context, l, abyte0);
        return new ProfileImage(l, s, bitmap);
    }

    public int bulkInsert(Uri uri, ContentValues acontentvalues[])
    {
        int i = 0;
        int j = 0;
        SQLiteDatabase sqlitedatabase = mDbHelper.getWritableDatabase();
        int k = URL_MATCHER.match(uri);
        int j1;
        if(k == Selectors.FRIENDS_CONTENT.uriMatcherIndex())
        {
            int i2 = acontentvalues.length;
            int j2 = 0;
            while(j2 < i2) 
            {
                if(friendsInsert(sqlitedatabase, acontentvalues[j2]) != null)
                    i++;
                else
                    j++;
                j2++;
            }
            if(i != 0)
            {
                getContext().getContentResolver().notifyChange(CONNECTIONS_CONTENT_URI, null);
                getContext().getContentResolver().notifyChange(FRIENDS_CONTENT_URI, null);
            }
            if(j > 0)
            {
                Object aobj2[] = new Object[1];
                aobj2[0] = Integer.valueOf(j);
                Utils.reportSoftError("Failed call to friendsInsert", String.format("Failed friendsInsert on %d rows", aobj2));
            }
            j1 = i;
        } else
        if(k == Selectors.PAGES_CONTENT.uriMatcherIndex())
        {
            int k1 = acontentvalues.length;
            int l1 = 0;
            while(l1 < k1) 
            {
                if(pagesInsert(sqlitedatabase, acontentvalues[l1]) != null)
                    i++;
                else
                    j++;
                l1++;
            }
            if(i != 0)
                getContext().getContentResolver().notifyChange(USER_SEARCH_CONTENT_URI, null);
            if(j > 0)
            {
                Object aobj1[] = new Object[1];
                aobj1[0] = Integer.valueOf(j);
                Utils.reportSoftError("Failed call to pagesInsert", String.format("Failed pagesInsert on %d rows", aobj1));
            }
            j1 = i;
        } else
        if(k == Selectors.SEARCH_CONTENT.uriMatcherIndex())
        {
            int l = acontentvalues.length;
            int i1 = 0;
            while(i1 < l) 
            {
                if(sqlitedatabase.insert("search_results", "user_id", acontentvalues[i1]) > 0L)
                    i++;
                else
                    j++;
                i1++;
            }
            if(i != 0)
                getContext().getContentResolver().notifyChange(USER_SEARCH_CONTENT_URI, null);
            if(j > 0)
            {
                Object aobj[] = new Object[1];
                aobj[0] = Integer.valueOf(j);
                Utils.reportSoftError("Failed insert into SEARCH_RESULTS_TABLE", String.format("Failed on %d rows", aobj));
            }
            j1 = i;
        } else
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
        }
        return j1;
    }

    public int delete(Uri uri, String s, String as[])
    {
        SQLiteDatabase sqlitedatabase;
        int i;
        sqlitedatabase = mDbHelper.getWritableDatabase();
        i = URL_MATCHER.match(uri);
        if(i != Selectors.CONNECTIONS_CONTENT.uriMatcherIndex()) goto _L2; else goto _L1
_L1:
        sqlitedatabase.beginTransaction();
        int l;
        int i1;
        l = sqlitedatabase.delete("connections", s, as);
        i1 = sqlitedatabase.delete("friends_data", s, as);
        sqlitedatabase.setTransactionSuccessful();
        int k;
        sqlitedatabase.endTransaction();
        if(l > 0 || i1 > 0)
        {
            getContext().getContentResolver().notifyChange(CONNECTIONS_CONTENT_URI, null);
            getContext().getContentResolver().notifyChange(FRIENDS_CONTENT_URI, null);
            getContext().getContentResolver().notifyChange(PAGES_CONTENT_URI, null);
        }
        Exception exception;
        RuntimeException runtimeexception;
        if(l > i1)
            k = l;
        else
            k = i1;
_L4:
        return k;
        runtimeexception;
        throw runtimeexception;
        exception;
        sqlitedatabase.endTransaction();
        throw exception;
_L2:
        if(i == Selectors.SEARCH_CONTENT.uriMatcherIndex())
        {
            int j = sqlitedatabase.delete("search_results", s, as);
            if(j > 0)
                getContext().getContentResolver().notifyChange(USER_SEARCH_CONTENT_URI, null);
            k = j;
        } else
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected Uri friendsInsert(SQLiteDatabase sqlitedatabase, ContentValues contentvalues)
    {
        ContentValues contentvalues1;
        ContentValues contentvalues2;
        boolean flag;
        if(!contentvalues.containsKey("user_id"))
            throw new IllegalArgumentException("friends inserts must contain a uid");
        contentvalues.put("connection_type", Integer.valueOf(ConnectionType.USER.ordinal()));
        Tuple tuple = splitFriendsData(contentvalues);
        contentvalues1 = (ContentValues)tuple.d0;
        contentvalues2 = (ContentValues)tuple.d1;
        flag = false;
        sqlitedatabase.beginTransaction();
        if(contentvalues1.size() != 0 && sqlitedatabase.insert("connections", null, contentvalues1) > 0L)
            flag = true;
        if(contentvalues2.size() != 0 && sqlitedatabase.insert("friends_data", null, contentvalues2) > 0L)
            flag = true;
        sqlitedatabase.setTransactionSuccessful();
        sqlitedatabase.endTransaction();
        Exception exception;
        RuntimeException runtimeexception;
        Uri uri;
        if(flag)
            uri = Uri.withAppendedPath(FRIEND_UID_CONTENT_URI, contentvalues.getAsString("user_id"));
        else
            uri = null;
        return uri;
        runtimeexception;
        throw runtimeexception;
        exception;
        sqlitedatabase.endTransaction();
        throw exception;
    }

    public String getType(Uri uri)
    {
        if(URL_MATCHER.match(uri) > 0)
            return "vnd.android.cursor.item/vnd.com.facebook.katana.provider.friends";
        else
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
    }

    public Uri insert(Uri uri, ContentValues contentvalues)
    {
        SQLiteDatabase sqlitedatabase;
        int i;
        sqlitedatabase = mDbHelper.getWritableDatabase();
        i = URL_MATCHER.match(uri);
        if(i != Selectors.FRIENDS_CONTENT.uriMatcherIndex()) goto _L2; else goto _L1
_L1:
        Uri uri3 = friendsInsert(sqlitedatabase, contentvalues);
        if(uri3 == null) goto _L4; else goto _L3
_L3:
        Uri uri1;
        getContext().getContentResolver().notifyChange(CONNECTIONS_CONTENT_URI, null);
        getContext().getContentResolver().notifyChange(FRIENDS_CONTENT_URI, null);
        uri1 = uri3;
_L6:
        return uri1;
_L2:
        if(i == Selectors.PAGES_CONTENT.uriMatcherIndex())
        {
            Uri uri2 = pagesInsert(sqlitedatabase, contentvalues);
            if(uri2 != null)
            {
                getContext().getContentResolver().notifyChange(CONNECTIONS_CONTENT_URI, null);
                getContext().getContentResolver().notifyChange(PAGES_CONTENT_URI, null);
                uri1 = uri2;
                continue; /* Loop/switch isn't completed */
            }
        } else
        {
            if(i == Selectors.SEARCH_CONTENT.uriMatcherIndex())
            {
                long l = sqlitedatabase.insert("search_results", "display_name", contentvalues);
                if(l > 0L)
                    getContext().getContentResolver().notifyChange(USER_SEARCH_CONTENT_URI, null);
                uri1 = Uri.withAppendedPath(USER_SEARCH_CONTENT_URI, String.valueOf(l));
            } else
            {
                throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
            }
            continue; /* Loop/switch isn't completed */
        }
_L4:
        Utils.reportSoftError("Single row insert failed", (new StringBuilder()).append("Failed to insert row into ").append(uri).toString());
        uri1 = null;
        if(true) goto _L6; else goto _L5
_L5:
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

    protected Uri pagesInsert(SQLiteDatabase sqlitedatabase, ContentValues contentvalues)
    {
        if(!contentvalues.containsKey("user_id"))
            throw new IllegalArgumentException("pages inserts must contain a id");
        Integer integer = contentvalues.getAsInteger("connection_type");
        if(integer == null)
            throw new IllegalArgumentException("pages inserts must contain a connection type, and it must be an integer");
        if(integer.intValue() != ConnectionType.PAGE_ADMIN.ordinal() && integer.intValue() != ConnectionType.PAGE_FAN.ordinal())
            throw new IllegalArgumentException("pages inserts must be PAGE_ADMIN or PAGE_FAN");
        Uri uri;
        if(sqlitedatabase.insert("connections", null, contentvalues) > 0L)
            uri = Uri.withAppendedPath(PAGE_ID_CONTENT_URI, contentvalues.getAsString("user_id"));
        else
            uri = null;
        return uri;
    }

    public Cursor query(Uri uri, String as[], String s, String as1[], String s1)
    {
        SQLiteQueryBuilder sqlitequerybuilder = new SQLiteQueryBuilder();
        String s2 = null;
        int i = URL_MATCHER.match(uri);
        String s3;
        String s4;
        Cursor cursor;
        if(i == Selectors.CONNECTIONS_CONTENT.uriMatcherIndex())
        {
            sqlitequerybuilder.setTables("connections");
            sqlitequerybuilder.setProjectionMap(CONNECTIONS_PROJECTION_MAP);
            s3 = "display_name ASC";
        } else
        if(i == Selectors.CONNECTION_ID.uriMatcherIndex())
        {
            sqlitequerybuilder.setTables("connections");
            sqlitequerybuilder.appendWhere((new StringBuilder()).append("user_id=").append((String)uri.getPathSegments().get(2)).toString());
            sqlitequerybuilder.setProjectionMap(CONNECTIONS_PROJECTION_MAP);
            s3 = "display_name ASC";
        } else
        if(i == Selectors.FRIENDS_CONTENT.uriMatcherIndex())
        {
            sqlitequerybuilder.setTables("friends");
            sqlitequerybuilder.setProjectionMap(FRIENDS_PROJECTION_MAP);
            s3 = "display_name ASC";
        } else
        if(i == Selectors.FRIEND_UID.uriMatcherIndex())
        {
            sqlitequerybuilder.setTables("friends");
            sqlitequerybuilder.appendWhere((new StringBuilder()).append("user_id=").append((String)uri.getPathSegments().get(2)).toString());
            sqlitequerybuilder.setProjectionMap(FRIENDS_PROJECTION_MAP);
            s3 = "display_name ASC";
        } else
        if(i == Selectors.FRIENDS_SEARCH.uriMatcherIndex())
        {
            String s6 = (String)uri.getPathSegments().get(2);
            sqlitequerybuilder.setTables("friends");
            sqlitequerybuilder.setProjectionMap(FRIENDS_PROJECTION_MAP);
            sqlitequerybuilder.appendWhere("display_name LIKE ");
            sqlitequerybuilder.appendWhereEscapeString((new StringBuilder()).append("%").append(s6).append("%").toString());
            s2 = "15";
            s3 = "display_name ASC";
        } else
        if(i == Selectors.FRIENDS_BIRTHDAYS.uriMatcherIndex())
        {
            HashMap hashmap = new HashMap(FRIENDS_PROJECTION_MAP);
            GregorianCalendar gregoriancalendar = new GregorianCalendar();
            boolean flag = false;
            if(gregoriancalendar.get(2) <= 1)
            {
                if(!gregoriancalendar.isLeapYear(gregoriancalendar.get(1)))
                    flag = true;
            } else
            if(!gregoriancalendar.isLeapYear(1 + gregoriancalendar.get(1)))
                flag = true;
            if(flag)
                hashmap.put("normalized_birthday_day", "CASE WHEN (friends.birthday_month=2 AND            friends.birthday_day=29) THEN 28      ELSE friends.birthday_day END AS normalized_birthday_day");
            else
                hashmap.put("normalized_birthday_day", "birthday_day");
            sqlitequerybuilder.setTables("friends");
            sqlitequerybuilder.appendWhere("birthday_month!=-1 AND birthday_day!=-1");
            sqlitequerybuilder.setProjectionMap(hashmap);
            s3 = "display_name ASC";
        } else
        if(i == Selectors.PAGES_CONTENT.uriMatcherIndex())
        {
            sqlitequerybuilder.setTables("connections");
            sqlitequerybuilder.setProjectionMap(CONNECTIONS_PROJECTION_MAP);
            Object aobj2[] = new Object[3];
            aobj2[0] = "connection_type";
            aobj2[1] = Integer.valueOf(ConnectionType.PAGE_ADMIN.ordinal());
            aobj2[2] = Integer.valueOf(ConnectionType.PAGE_FAN.ordinal());
            sqlitequerybuilder.appendWhere(String.format("(%1$s=%2$d OR %1$s=%3$d)", aobj2));
            s3 = "display_name ASC";
        } else
        if(i == Selectors.PAGE_ID.uriMatcherIndex())
        {
            sqlitequerybuilder.setTables("connections");
            Object aobj1[] = new Object[5];
            aobj1[0] = "user_id";
            aobj1[1] = uri.getPathSegments().get(2);
            aobj1[2] = "connection_type";
            aobj1[3] = Integer.valueOf(ConnectionType.PAGE_ADMIN.ordinal());
            aobj1[4] = Integer.valueOf(ConnectionType.PAGE_FAN.ordinal());
            sqlitequerybuilder.appendWhere(String.format("%1$s=%2$s AND (%3$s=%4$d OR %3$s=%5$d)", aobj1));
            sqlitequerybuilder.setProjectionMap(CONNECTIONS_PROJECTION_MAP);
            s3 = "display_name ASC";
        } else
        if(i == Selectors.PAGES_SEARCH.uriMatcherIndex())
        {
            String s5 = (String)uri.getPathSegments().get(2);
            sqlitequerybuilder.setTables("connections");
            sqlitequerybuilder.setProjectionMap(CONNECTIONS_PROJECTION_MAP);
            Object aobj[] = new Object[3];
            aobj[0] = "connection_type";
            aobj[1] = Integer.valueOf(ConnectionType.PAGE_ADMIN.ordinal());
            aobj[2] = Integer.valueOf(ConnectionType.PAGE_FAN.ordinal());
            sqlitequerybuilder.appendWhere(String.format("(%1$s=%2$d OR %1$s=%3$d)", aobj));
            sqlitequerybuilder.appendWhere(" AND display_name LIKE ");
            sqlitequerybuilder.appendWhereEscapeString((new StringBuilder()).append("%").append(s5).append("%").toString());
            s2 = "15";
            s3 = "display_name ASC";
        } else
        if(i == Selectors.SEARCH_CONTENT.uriMatcherIndex())
        {
            sqlitequerybuilder.setTables("search_results");
            sqlitequerybuilder.setProjectionMap(SEARCH_RESULTS_PROJECTION_MAP);
            s3 = "_id ASC";
        } else
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
        }
        if(TextUtils.isEmpty(s1))
            s4 = s3;
        else
            s4 = s1;
        cursor = sqlitequerybuilder.query(mDbHelper.getReadableDatabase(), as, s, as1, null, null, s4, s2);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    protected Tuple splitFriendsData(ContentValues contentvalues)
    {
        ContentValues contentvalues1 = new ContentValues(contentvalues);
        ContentValues contentvalues2 = new ContentValues(contentvalues);
        Iterator iterator = contentvalues.valueSet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            String s = (String)((java.util.Map.Entry)iterator.next()).getKey();
            if(!CONNECTIONS_PROJECTION_MAP.containsKey(s))
                contentvalues1.remove(s);
            if(!FRIENDS_DATA_COLUMNS.contains(s))
                contentvalues2.remove(s);
        } while(true);
        return new Tuple(contentvalues1, contentvalues2);
    }

    public int update(Uri uri, ContentValues contentvalues, String s, String as[])
    {
        SQLiteDatabase sqlitedatabase;
        int i;
        sqlitedatabase = mDbHelper.getWritableDatabase();
        i = URL_MATCHER.match(uri);
        if(i != Selectors.CONNECTION_ID.uriMatcherIndex() && i != Selectors.PAGE_ID.uriMatcherIndex()) goto _L2; else goto _L1
_L1:
        String s1;
        int j;
        s1 = (String)uri.getPathSegments().get(2);
        try
        {
            Long.parseLong(s1);
            break MISSING_BLOCK_LABEL_61;
        }
        catch(NumberFormatException numberformatexception)
        {
            Utils.reportSoftError("NumberFormatException: URI must have a long argument", (new StringBuilder()).append("Uri ").append(uri).append(" must have a long argument").toString());
            j = 0;
        }
_L3:
        return j;
        int k = 0;
        if(i == Selectors.CONNECTION_ID.uriMatcherIndex())
        {
            if(contentvalues.containsKey("connection_type"))
                throw new IllegalArgumentException("connection updates should not touch the connection type column");
        } else
        if(i == Selectors.PAGE_ID.uriMatcherIndex())
        {
            Integer integer = contentvalues.getAsInteger("connection_type");
            if(integer != null && integer.intValue() != ConnectionType.PAGE_ADMIN.ordinal() && integer.intValue() != ConnectionType.PAGE_FAN.ordinal())
                throw new IllegalArgumentException("page updates must stay PAGE_ADMIN or PAGE_FAN");
        }
        if(contentvalues.size() != 0)
            k = sqlitedatabase.update("connections", contentvalues, (new StringBuilder()).append("user_id=").append(s1).toString(), null);
        if(k > 0)
        {
            if(i == Selectors.CONNECTION_ID.uriMatcherIndex())
            {
                getContext().getContentResolver().notifyChange(CONNECTIONS_CONTENT_URI, null);
                getContext().getContentResolver().notifyChange(FRIENDS_CONTENT_URI, null);
            }
            getContext().getContentResolver().notifyChange(PAGES_CONTENT_URI, null);
        }
        j = k;
          goto _L3
_L2:
        String s2;
        if(i != Selectors.FRIEND_UID.uriMatcherIndex())
            break MISSING_BLOCK_LABEL_652;
        s2 = (String)uri.getPathSegments().get(2);
        Long.parseLong(s2);
        Integer integer1 = contentvalues.getAsInteger("connection_type");
        if(integer1 != null && integer1.intValue() != ConnectionType.USER.ordinal())
            throw new IllegalArgumentException("user updates must not change connection type USER");
        break MISSING_BLOCK_LABEL_412;
        NumberFormatException numberformatexception1;
        numberformatexception1;
        Utils.reportSoftError("NumberFormatException: URI must have a long argument", (new StringBuilder()).append("uri ").append(uri).append(" must contain a long").toString());
        j = 0;
          goto _L3
        ContentValues contentvalues1;
        ContentValues contentvalues2;
        int l;
        int i1;
        Tuple tuple = splitFriendsData(contentvalues);
        contentvalues1 = (ContentValues)tuple.d0;
        contentvalues2 = (ContentValues)tuple.d1;
        l = 0;
        i1 = 0;
        sqlitedatabase.beginTransaction();
        if(contentvalues1.size() != 0)
            l = sqlitedatabase.update("connections", contentvalues1, (new StringBuilder()).append("user_id=").append(s2).toString(), null);
        if(contentvalues2.size() != 0)
        {
            i1 = sqlitedatabase.update("friends_data", contentvalues2, (new StringBuilder()).append("user_id=").append(s2).toString(), null);
            if(l != 0 && i1 == 0)
            {
                contentvalues2.put("user_id", Long.valueOf(Long.parseLong(s2)));
                sqlitedatabase.insert("friends_data", null, contentvalues2);
            }
        }
        sqlitedatabase.setTransactionSuccessful();
        sqlitedatabase.endTransaction();
        if(l > 0 || i1 > 0)
        {
            getContext().getContentResolver().notifyChange(CONNECTIONS_CONTENT_URI, null);
            getContext().getContentResolver().notifyChange(FRIENDS_CONTENT_URI, null);
        }
        Exception exception;
        RuntimeException runtimeexception;
        if(l > i1)
            j = l;
        else
            j = i1;
          goto _L3
        runtimeexception;
        throw runtimeexception;
        exception;
        sqlitedatabase.endTransaction();
        throw exception;
        throw new IllegalArgumentException((new StringBuilder()).append("Unknown URL ").append(uri).toString());
    }

    private static final String AUTHORITY = "com.facebook.katana.provider.ConnectionsProvider";
    private static final String BASE_CONTENT_URI = "content://com.facebook.katana.provider.ConnectionsProvider/";
    public static final Uri CONNECTIONS_CONTENT_URI;
    private static final HashMap CONNECTIONS_PROJECTION_MAP;
    static final String CONNECTIONS_TABLE = "connections";
    public static final Uri CONNECTION_ID_CONTENT_URI;
    public static final Uri FRIENDS_BIRTHDAY_CONTENT_URI;
    public static final Uri FRIENDS_CONTENT_URI;
    private static final Set FRIENDS_DATA_COLUMNS;
    static final String FRIENDS_DATA_TABLE = "friends_data";
    private static final HashMap FRIENDS_PROJECTION_MAP;
    public static final Uri FRIENDS_SEARCH_CONTENT_URI;
    private static final String FRIENDS_VIEW = "friends";
    public static final Uri FRIEND_UID_CONTENT_URI;
    private static final String PAGES_CATEGORY = "pages";
    public static final Uri PAGES_CONTENT_URI;
    public static final Uri PAGES_SEARCH_CONTENT_URI;
    public static final Uri PAGE_ID_CONTENT_URI;
    private static final HashMap SEARCH_RESULTS_PROJECTION_MAP;
    static final String SEARCH_RESULTS_TABLE = "search_results";
    private static final String SQL_CONNECTIONS;
    static final String SQL_FRIENDS_DATA = "CREATE TABLE friends_data (_id INTEGER PRIMARY KEY,user_id INT UNIQUE,first_name TEXT,last_name TEXT,cell TEXT,other TEXT,email TEXT,birthday_month INT,birthday_day INT,birthday_year INT);";
    static final String SQL_FRIENDS_VIEW;
    static final String SQL_SEARCH_RESULTS = "CREATE TABLE search_results (_id INTEGER PRIMARY KEY,user_id INT,display_name TEXT,user_image_url TEXT);";
    private static final UriMatcher URL_MATCHER;
    public static final Uri USER_SEARCH_CONTENT_URI;
    private FacebookDatabaseHelper mDbHelper;

    static 
    {
        SQL_CONNECTIONS = (new StringBuilder()).append("CREATE TABLE connections (_id INTEGER PRIMARY KEY,user_id INT UNIQUE,display_name TEXT,connection_type INT NOT NULL DEFAULT ").append(ConnectionType.USER.ordinal()).append(",").append("user_image_url").append(" TEXT,").append("user_image").append(" BLOB,").append("hash").append(" INT);").toString();
        Object aobj[] = new Object[19];
        aobj[0] = "friends";
        aobj[1] = "connections";
        aobj[2] = "friends_data";
        aobj[3] = Integer.valueOf(ConnectionType.USER.ordinal());
        aobj[4] = "_id";
        aobj[5] = "user_id";
        aobj[6] = "display_name";
        aobj[7] = "connection_type";
        aobj[8] = "user_image_url";
        aobj[9] = "user_image";
        aobj[10] = "hash";
        aobj[11] = "first_name";
        aobj[12] = "last_name";
        aobj[13] = "cell";
        aobj[14] = "other";
        aobj[15] = "email";
        aobj[16] = "birthday_month";
        aobj[17] = "birthday_day";
        aobj[18] = "birthday_year";
        SQL_FRIENDS_VIEW = String.format("CREATE VIEW %1$s AS SELECT %2$s.%5$s AS %5$s, %2$s.%6$s AS %6$s, %2$s.%7$s AS %7$s, %2$s.%8$s AS %8$s, %2$s.%9$s AS %9$s, %2$s.%10$s AS %10$s, %2$s.%11$s AS %11$s, %3$s.%12$s AS %12$s, %3$s.%13$s AS %13$s, %3$s.%14$s AS %14$s, %3$s.%15$s AS %15$s, %3$s.%16$s AS %16$s, %3$s.%17$s AS %17$s, %3$s.%18$s AS %18$s, %3$s.%19$s AS %19$s FROM %2$s LEFT OUTER JOIN %3$s ON %2$s.%6$s=%3$s.%6$s WHERE %2$s.%8$s=%4$d;", aobj);
        CONNECTIONS_CONTENT_URI = Uri.parse((new StringBuilder()).append("content://com.facebook.katana.provider.ConnectionsProvider/").append(Selectors.CONNECTIONS_CONTENT.category).append(Selectors.CONNECTIONS_CONTENT.uriSuffix).toString());
        CONNECTION_ID_CONTENT_URI = Uri.parse((new StringBuilder()).append("content://com.facebook.katana.provider.ConnectionsProvider/").append(Selectors.CONNECTION_ID.category).append(Selectors.CONNECTION_ID.uriSuffix).toString());
        FRIENDS_CONTENT_URI = Uri.parse((new StringBuilder()).append("content://com.facebook.katana.provider.ConnectionsProvider/").append(Selectors.FRIENDS_CONTENT.category).append(Selectors.FRIENDS_CONTENT.uriSuffix).toString());
        FRIEND_UID_CONTENT_URI = Uri.parse((new StringBuilder()).append("content://com.facebook.katana.provider.ConnectionsProvider/").append(Selectors.FRIEND_UID.category).append(Selectors.FRIEND_UID.uriSuffix).toString());
        FRIENDS_SEARCH_CONTENT_URI = Uri.parse((new StringBuilder()).append("content://com.facebook.katana.provider.ConnectionsProvider/").append(Selectors.FRIENDS_SEARCH.category).append(Selectors.FRIENDS_SEARCH.uriSuffix).toString());
        FRIENDS_BIRTHDAY_CONTENT_URI = Uri.parse((new StringBuilder()).append("content://com.facebook.katana.provider.ConnectionsProvider/").append(Selectors.FRIENDS_BIRTHDAYS.category).append(Selectors.FRIENDS_BIRTHDAYS.uriSuffix).toString());
        PAGES_CONTENT_URI = Uri.parse((new StringBuilder()).append("content://com.facebook.katana.provider.ConnectionsProvider/").append(Selectors.PAGES_CONTENT.category).append(Selectors.PAGES_CONTENT.uriSuffix).toString());
        PAGE_ID_CONTENT_URI = Uri.parse((new StringBuilder()).append("content://com.facebook.katana.provider.ConnectionsProvider/").append(Selectors.PAGE_ID.category).append(Selectors.PAGE_ID.uriSuffix).toString());
        PAGES_SEARCH_CONTENT_URI = Uri.parse((new StringBuilder()).append("content://com.facebook.katana.provider.ConnectionsProvider/").append(Selectors.PAGES_SEARCH.category).append(Selectors.PAGES_SEARCH.uriSuffix).toString());
        USER_SEARCH_CONTENT_URI = Uri.parse((new StringBuilder()).append("content://com.facebook.katana.provider.ConnectionsProvider/").append(Selectors.SEARCH_CONTENT.category).append(Selectors.SEARCH_CONTENT.uriSuffix).toString());
        URL_MATCHER = new UriMatcher(-1);
        Selectors aselectors[] = Selectors.values();
        int i = aselectors.length;
        for(int j = 0; j < i; j++)
        {
            Selectors selectors = aselectors[j];
            String s = (new StringBuilder()).append(selectors.category).append(selectors.uriSuffix).append(selectors.uriMatcherSuffix).toString();
            URL_MATCHER.addURI("com.facebook.katana.provider.ConnectionsProvider", s, selectors.uriMatcherIndex());
        }

        CONNECTIONS_PROJECTION_MAP = new HashMap();
        CONNECTIONS_PROJECTION_MAP.put("_id", "_id");
        CONNECTIONS_PROJECTION_MAP.put("user_id", "user_id");
        CONNECTIONS_PROJECTION_MAP.put("display_name", "display_name");
        CONNECTIONS_PROJECTION_MAP.put("connection_type", "connection_type");
        CONNECTIONS_PROJECTION_MAP.put("user_image_url", "user_image_url");
        CONNECTIONS_PROJECTION_MAP.put("user_image", "user_image");
        CONNECTIONS_PROJECTION_MAP.put("hash", "hash");
        FRIENDS_PROJECTION_MAP = new HashMap();
        FRIENDS_PROJECTION_MAP.put("_id", "_id");
        FRIENDS_PROJECTION_MAP.put("user_id", "user_id");
        FRIENDS_PROJECTION_MAP.put("first_name", "first_name");
        FRIENDS_PROJECTION_MAP.put("last_name", "last_name");
        FRIENDS_PROJECTION_MAP.put("display_name", "display_name");
        FRIENDS_PROJECTION_MAP.put("user_image_url", "user_image_url");
        FRIENDS_PROJECTION_MAP.put("user_image", "user_image");
        FRIENDS_PROJECTION_MAP.put("birthday_month", "birthday_month");
        FRIENDS_PROJECTION_MAP.put("birthday_day", "birthday_day");
        FRIENDS_PROJECTION_MAP.put("birthday_year", "birthday_year");
        FRIENDS_PROJECTION_MAP.put("cell", "cell");
        FRIENDS_PROJECTION_MAP.put("other", "other");
        FRIENDS_PROJECTION_MAP.put("email", "email");
        FRIENDS_PROJECTION_MAP.put("hash", "hash");
        SEARCH_RESULTS_PROJECTION_MAP = new HashMap();
        SEARCH_RESULTS_PROJECTION_MAP.put("_id", "_id");
        SEARCH_RESULTS_PROJECTION_MAP.put("user_id", "user_id");
        SEARCH_RESULTS_PROJECTION_MAP.put("display_name", "display_name");
        SEARCH_RESULTS_PROJECTION_MAP.put("user_image_url", "user_image_url");
        FRIENDS_DATA_COLUMNS = new HashSet();
        FRIENDS_DATA_COLUMNS.add("user_id");
        FRIENDS_DATA_COLUMNS.add("first_name");
        FRIENDS_DATA_COLUMNS.add("last_name");
        FRIENDS_DATA_COLUMNS.add("birthday_month");
        FRIENDS_DATA_COLUMNS.add("birthday_day");
        FRIENDS_DATA_COLUMNS.add("birthday_year");
        FRIENDS_DATA_COLUMNS.add("cell");
        FRIENDS_DATA_COLUMNS.add("other");
        FRIENDS_DATA_COLUMNS.add("email");
    }
}
