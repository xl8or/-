// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhotosEditAlbum.java

package com.facebook.katana.service.method;

import android.content.*;
import android.net.Uri;
import com.facebook.katana.provider.PhotosProvider;
import java.util.Map;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodListener

public class PhotosEditAlbum extends ApiMethod
{

    public PhotosEditAlbum(Context context, Intent intent, String s, String s1, String s2, String s3, String s4, 
            String s5, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", "photos.editAlbum", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("session_key", s);
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        mParams.put("name", s2);
        mParams.put("aid", s1);
        mParams.put("location", s3);
        mParams.put("description", s4);
        mVisibility = s5;
        if(s5 != null && !s5.equals("custom"))
            mParams.put("visible", s5);
    }

    protected void parseJSON(JsonParser jsonparser)
    {
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("name", (String)mParams.get("name"));
        contentvalues.put("description", (String)mParams.get("description"));
        contentvalues.put("location", (String)mParams.get("location"));
        contentvalues.put("visibility", mVisibility);
        Uri uri = Uri.withAppendedPath(PhotosProvider.ALBUMS_AID_CONTENT_URI, (String)mParams.get("aid"));
        mContext.getContentResolver().update(uri, contentvalues, null, null);
    }

    protected String mVisibility;
}
