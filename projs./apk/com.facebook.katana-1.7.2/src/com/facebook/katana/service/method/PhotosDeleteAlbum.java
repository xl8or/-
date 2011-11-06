// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhotosDeleteAlbum.java

package com.facebook.katana.service.method;

import android.content.*;
import android.net.Uri;
import com.facebook.katana.provider.PhotosProvider;
import java.util.Map;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodListener

public class PhotosDeleteAlbum extends ApiMethod
{

    public PhotosDeleteAlbum(Context context, Intent intent, String s, long l, String s1, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", "photos.deleteAlbum", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mOwnerId = l;
        mParams.put("session_key", s);
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        mParams.put("aid", s1);
    }

    protected void parseJSON(JsonParser jsonparser)
    {
        Uri uri = Uri.withAppendedPath(PhotosProvider.ALBUMS_OWNER_CONTENT_URI, Long.toString(mOwnerId));
        ContentResolver contentresolver = mContext.getContentResolver();
        String as[] = new String[1];
        as[0] = (String)mParams.get("aid");
        contentresolver.delete(uri, "aid IN(?)", as);
    }

    private final long mOwnerId;
}
