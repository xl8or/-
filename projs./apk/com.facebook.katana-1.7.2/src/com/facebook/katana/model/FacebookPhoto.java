// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookPhoto.java

package com.facebook.katana.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.provider.PhotosProvider;
import com.facebook.katana.util.jsonmirror.*;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.model:
//            FacebookApiException

public class FacebookPhoto extends JMCachingDictDestination
{
    private static interface PhotoQuery
    {

        public static final int INDEX_ALBUM_ID = 0;
        public static final int INDEX_CAPTION = 4;
        public static final int INDEX_CREATED = 3;
        public static final int INDEX_OWNER = 2;
        public static final int INDEX_PHOTO_ID = 1;
        public static final int INDEX_POSITION = 9;
        public static final int INDEX_SRC = 5;
        public static final int INDEX_SRC_BIG = 6;
        public static final int INDEX_SRC_SMALL = 7;
        public static final int INDEX_THUMBNAIL = 8;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[10];
            as[0] = "aid";
            as[1] = "pid";
            as[2] = "owner";
            as[3] = "created";
            as[4] = "caption";
            as[5] = "src";
            as[6] = "src_big";
            as[7] = "src_small";
            as[8] = "thumbnail";
            as[9] = "position";
        }
    }


    private FacebookPhoto()
    {
        mPhotoId = null;
        mAlbumId = null;
        mOwner = -1L;
        mSrcUrl = null;
        mSrcUrlBig = null;
        mSrcUrlSmall = null;
        mCaption = null;
        mCreated = -1L;
        mImageBytes = null;
        mObjectId = -1L;
        position = -1L;
    }

    public FacebookPhoto(String s, String s1, long l, String s2, String s3, String s4, 
            String s5, long l1, byte abyte0[], long l2, long l3)
    {
        mPhotoId = s;
        mAlbumId = s1;
        mOwner = l;
        mCaption = s2;
        mSrcUrl = s3;
        mSrcUrlBig = s4;
        mSrcUrlSmall = s5;
        mCreated = l1;
        mImageBytes = abyte0;
        mObjectId = l2;
        position = l3;
    }

    public static FacebookPhoto parseJson(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        return (FacebookPhoto)JMParser.parseObjectJson(jsonparser, com/facebook/katana/model/FacebookPhoto);
    }

    public static FacebookPhoto readFromContentProvider(Context context, Uri uri)
    {
        Cursor cursor = context.getContentResolver().query(uri, PhotoQuery.PROJECTION, null, null, null);
        FacebookPhoto facebookphoto;
        if(cursor.moveToFirst())
            facebookphoto = new FacebookPhoto(cursor.getString(1), cursor.getString(0), cursor.getLong(2), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getLong(3), cursor.getBlob(8), -1L, cursor.getLong(9));
        else
            facebookphoto = null;
        cursor.close();
        return facebookphoto;
    }

    public static FacebookPhoto readFromContentProvider(Context context, String s)
    {
        return readFromContentProvider(context, Uri.withAppendedPath(PhotosProvider.PHOTOS_PID_CONTENT_URI, s));
    }

    public String getAlbumId()
    {
        return mAlbumId;
    }

    public String getCaption()
    {
        return mCaption;
    }

    public long getCreated()
    {
        return mCreated;
    }

    public long getCreatedMs()
    {
        return 1000L * mCreated;
    }

    public byte[] getImageBytes()
    {
        return mImageBytes;
    }

    public long getObjectId()
    {
        return mObjectId;
    }

    public long getOwnerId()
    {
        return mOwner;
    }

    public String getPhotoId()
    {
        return mPhotoId;
    }

    public String getSrc()
    {
        return mSrcUrl;
    }

    public String getSrcBig()
    {
        return mSrcUrlBig;
    }

    public String getSrcSmall()
    {
        return mSrcUrlSmall;
    }

    private final String mAlbumId;
    private final String mCaption;
    private final long mCreated;
    private final byte mImageBytes[];
    private final long mObjectId;
    private final long mOwner;
    private final String mPhotoId;
    private final String mSrcUrl;
    private final String mSrcUrlBig;
    private final String mSrcUrlSmall;
    public final long position;
}
