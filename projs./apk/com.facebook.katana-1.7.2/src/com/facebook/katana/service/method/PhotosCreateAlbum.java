// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhotosCreateAlbum.java

package com.facebook.katana.service.method;

import android.content.*;
import android.net.Uri;
import com.facebook.katana.model.FacebookAlbum;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.provider.PhotosProvider;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodListener

public class PhotosCreateAlbum extends ApiMethod
{

    public PhotosCreateAlbum(Context context, Intent intent, String s, String s1, String s2, String s3, String s4, 
            ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", "photos.createAlbum", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("session_key", s);
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        mParams.put("name", s1);
        if(s2 != null)
            mParams.put("location", s2);
        if(s3 != null)
            mParams.put("description", s3);
        if(s4 != null)
            mParams.put("visible", s4);
    }

    private static Uri insert(Context context, FacebookAlbum facebookalbum)
    {
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("aid", facebookalbum.getAlbumId());
        contentvalues.put("cover_pid", facebookalbum.getCoverPhotoId());
        contentvalues.put("owner", Long.valueOf(facebookalbum.getOwner()));
        contentvalues.put("name", facebookalbum.getName());
        contentvalues.put("created", Long.valueOf(facebookalbum.getCreated()));
        contentvalues.put("modified", Long.valueOf(facebookalbum.getModified()));
        contentvalues.put("description", facebookalbum.getDescription());
        contentvalues.put("location", facebookalbum.getLocation());
        contentvalues.put("size", Integer.valueOf(facebookalbum.getSize()));
        contentvalues.put("visibility", facebookalbum.getVisibility());
        contentvalues.put("type", facebookalbum.getType());
        return context.getContentResolver().insert(PhotosProvider.ALBUMS_CONTENT_URI, contentvalues);
    }

    public FacebookAlbum getAlbum()
    {
        return mAlbum;
    }

    public Uri getAlbumUri()
    {
        return mAlbumUri;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException
    {
        mAlbum = new FacebookAlbum(jsonparser);
        mAlbumUri = insert(mContext, mAlbum);
    }

    private FacebookAlbum mAlbum;
    private Uri mAlbumUri;
}
