// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhotosDeletePhoto.java

package com.facebook.katana.service.method;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.provider.PhotosProvider;
import java.util.Map;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodListener

public class PhotosDeletePhoto extends ApiMethod
{
    private static interface AlbumQuery
    {

        public static final String ALBUM_PROJECTION[] = as;
        public static final int INDEX_COVER_PHOTO_ID = 1;
        public static final int INDEX_SIZE;

        
        {
            String as[] = new String[2];
            as[0] = "size";
            as[1] = "cover_pid";
        }
    }


    public PhotosDeletePhoto(Context context, Intent intent, String s, String s1, String s2, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", "photos.deletePhoto", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("session_key", s);
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        mParams.put("pid", s2);
        mAlbumId = s1;
        mPhotoId = s2;
    }

    private void adjustAlbumSize()
    {
        ContentResolver contentresolver = mContext.getContentResolver();
        Uri uri = Uri.withAppendedPath(PhotosProvider.ALBUMS_AID_CONTENT_URI, (new StringBuilder()).append("").append(mAlbumId).toString());
        Cursor cursor = contentresolver.query(uri, AlbumQuery.ALBUM_PROJECTION, null, null, null);
        if(cursor.moveToFirst())
        {
            String s = cursor.getString(1);
            if(s != null && s.equals(mPhotoId))
                mAlbumCoverChanged = true;
            int i = cursor.getInt(0);
            if(i - 1 >= 0)
            {
                ContentValues contentvalues = new ContentValues();
                contentvalues.put("size", Integer.valueOf(i - 1));
                contentresolver.update(uri, contentvalues, null, null);
            }
        }
        cursor.close();
    }

    public boolean hasAlbumCoverChanged()
    {
        return mAlbumCoverChanged;
    }

    protected void parseJSON(JsonParser jsonparser)
    {
        Uri uri = Uri.withAppendedPath(PhotosProvider.PHOTOS_AID_CONTENT_URI, mAlbumId);
        ContentResolver contentresolver = mContext.getContentResolver();
        String as[] = new String[1];
        as[0] = mPhotoId;
        contentresolver.delete(uri, "pid IN(?)", as);
        adjustAlbumSize();
    }

    private boolean mAlbumCoverChanged;
    private final String mAlbumId;
    private final String mPhotoId;
}
