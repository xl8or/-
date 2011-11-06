// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookAlbum.java

package com.facebook.katana.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.provider.PhotosProvider;
import java.io.IOException;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.model:
//            FacebookApiException

public class FacebookAlbum
{
    private static interface AlbumQuery
    {

        public static final String ALBUM_PROJECTION[] = as;
        public static final int INDEX_ALBUM_ID = 0;
        public static final int INDEX_COVER_PHOTO_ID = 2;
        public static final int INDEX_COVER_PHOTO_URL = 3;
        public static final int INDEX_COVER_THUMBNAIL = 4;
        public static final int INDEX_CREATED = 5;
        public static final int INDEX_DESCRIPTION = 8;
        public static final int INDEX_LOCATION = 9;
        public static final int INDEX_MODIFIED = 6;
        public static final int INDEX_NAME = 7;
        public static final int INDEX_OBJECT_ID = 13;
        public static final int INDEX_OWNER = 1;
        public static final int INDEX_SIZE = 10;
        public static final int INDEX_TYPE = 12;
        public static final int INDEX_VISIBILITY = 11;

        
        {
            String as[] = new String[14];
            as[0] = "aid";
            as[1] = "owner";
            as[2] = "cover_pid";
            as[3] = "cover_url";
            as[4] = "thumbnail";
            as[5] = "created";
            as[6] = "modified";
            as[7] = "name";
            as[8] = "description";
            as[9] = "location";
            as[10] = "size";
            as[11] = "visibility";
            as[12] = "type";
            as[13] = "object_id";
        }
    }


    public FacebookAlbum(String s, String s1, String s2, long l, String s3, long l1, long l2, String s4, String s5, String s6, int i, 
            String s7, String s8, byte abyte0[], long l3)
    {
        mCoverChanged = false;
        mAlbumId = s;
        mAlbumCoverPhotoId = s1;
        mAlbumCoverPhotoUrl = s2;
        mOwner = l;
        mName = s3;
        mCreated = l1;
        mModified = l2;
        mDescription = s4;
        mLocation = s5;
        mLink = s6;
        mSize = i;
        mVisibility = s7;
        mType = s8;
        mImageBytes = abyte0;
        mObjectId = l3;
    }

    public FacebookAlbum(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException
    {
        mCoverChanged = false;
        String s = null;
        long l = -1L;
        String s1 = null;
        String s2 = null;
        String s3 = null;
        String s4 = null;
        String s5 = null;
        String s6 = null;
        String s7 = null;
        long l1 = -1L;
        long l2 = -1L;
        int i = -1;
        int j = -1;
        String s8 = null;
        long l3 = -1L;
        JsonToken jsontoken = jsonparser.nextToken();
        do
        {
            JsonToken jsontoken1 = JsonToken.END_OBJECT;
            if(jsontoken != jsontoken1)
            {
                JsonToken jsontoken2 = JsonToken.VALUE_STRING;
                if(jsontoken == jsontoken2)
                {
                    String s10 = jsonparser.getCurrentName();
                    if(s10.equals("aid"))
                        s = jsonparser.getText();
                    else
                    if(s10.equals("cover_pid"))
                        s5 = jsonparser.getText();
                    else
                    if(s10.equals("name"))
                        s1 = jsonparser.getText();
                    else
                    if(s10.equals("description"))
                        s2 = jsonparser.getText();
                    else
                    if(s10.equals("location"))
                        s3 = jsonparser.getText();
                    else
                    if(s10.equals("link"))
                        s4 = jsonparser.getText();
                    else
                    if(s10.equals("visible"))
                        s6 = jsonparser.getText();
                    else
                    if(s10.equals("type"))
                        s7 = jsonparser.getText();
                    else
                    if(s10.equals("error_msg"))
                        s8 = jsonparser.getText();
                    else
                    if(s10.equals("owner"))
                        l = Long.parseLong(jsonparser.getText());
                } else
                {
                    JsonToken jsontoken3 = JsonToken.VALUE_NUMBER_INT;
                    if(jsontoken == jsontoken3)
                    {
                        String s9 = jsonparser.getCurrentName();
                        if(s9.equals("owner"))
                            l = jsonparser.getLongValue();
                        else
                        if(s9.equals("created"))
                            l1 = jsonparser.getLongValue();
                        else
                        if(s9.equals("modified"))
                            l2 = jsonparser.getLongValue();
                        else
                        if(s9.equals("size"))
                            i = jsonparser.getIntValue();
                        else
                        if(s9.equals("error_code"))
                            j = jsonparser.getIntValue();
                        else
                        if(s9.equals("object_id"))
                            l3 = jsonparser.getLongValue();
                    }
                }
                jsontoken = jsonparser.nextToken();
            } else
            {
                if(j > 0)
                {
                    FacebookApiException facebookapiexception = new FacebookApiException(j, s8);
                    throw facebookapiexception;
                }
                mAlbumId = s;
                mOwner = l;
                mName = s1;
                mAlbumCoverPhotoId = s5;
                mCreated = l1;
                mModified = l2;
                mDescription = s2;
                mLocation = s3;
                mLink = s4;
                mSize = i;
                mVisibility = s6;
                if(s7 == null)
                    mType = "normal";
                else
                    mType = s7;
                mImageBytes = null;
                mObjectId = l3;
                return;
            }
        } while(true);
    }

    public static FacebookAlbum readFromContentProvider(Context context, Uri uri)
    {
        Cursor cursor = context.getContentResolver().query(uri, AlbumQuery.ALBUM_PROJECTION, null, null, null);
        FacebookAlbum facebookalbum;
        if(cursor.moveToFirst())
            facebookalbum = new FacebookAlbum(cursor.getString(0), cursor.getString(2), cursor.getString(3), cursor.getLong(1), cursor.getString(7), cursor.getLong(5), cursor.getLong(6), cursor.getString(8), cursor.getString(9), null, cursor.getInt(10), cursor.getString(11), cursor.getString(12), cursor.getBlob(4), cursor.getLong(13));
        else
            facebookalbum = null;
        cursor.close();
        return facebookalbum;
    }

    public static FacebookAlbum readFromContentProvider(Context context, String s)
    {
        return readFromContentProvider(context, Uri.withAppendedPath(PhotosProvider.ALBUMS_AID_CONTENT_URI, s));
    }

    public String getAlbumId()
    {
        return mAlbumId;
    }

    public String getCoverPhotoId()
    {
        return mAlbumCoverPhotoId;
    }

    public String getCoverPhotoUrl()
    {
        return mAlbumCoverPhotoUrl;
    }

    public long getCreated()
    {
        return mCreated;
    }

    public String getDescription()
    {
        return mDescription;
    }

    public byte[] getImageBytes()
    {
        return mImageBytes;
    }

    public String getLink()
    {
        return mLink;
    }

    public String getLocation()
    {
        return mLocation;
    }

    public long getModified()
    {
        return mModified;
    }

    public String getName()
    {
        return mName;
    }

    public long getObjectId()
    {
        return mObjectId;
    }

    public long getOwner()
    {
        return mOwner;
    }

    public int getSize()
    {
        return mSize;
    }

    public String getType()
    {
        return mType;
    }

    public String getVisibility()
    {
        return mVisibility;
    }

    public boolean hasCoverChanged()
    {
        return mCoverChanged;
    }

    public void setCoverChanged(boolean flag)
    {
        mCoverChanged = flag;
    }

    public void setCoverPhotoUrl(String s)
    {
        mAlbumCoverPhotoUrl = s;
    }

    public static final String TYPE_MOBILE = "mobile";
    public static final String TYPE_NORMAL = "normal";
    public static final String TYPE_PROFILE = "profile";
    public static final String TYPE_WALL = "wall";
    private final String mAlbumCoverPhotoId;
    private String mAlbumCoverPhotoUrl;
    private final String mAlbumId;
    private boolean mCoverChanged;
    private final long mCreated;
    private final String mDescription;
    private final byte mImageBytes[];
    private final String mLink;
    private final String mLocation;
    private final long mModified;
    private final String mName;
    private final long mObjectId;
    private final long mOwner;
    private final int mSize;
    private final String mType;
    private final String mVisibility;
}
