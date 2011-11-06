// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhotosEditPhoto.java

package com.facebook.katana.service.method;

import android.content.*;
import android.net.Uri;
import com.facebook.katana.provider.PhotosProvider;
import java.util.Map;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodListener

public class PhotosEditPhoto extends ApiMethod
{

    public PhotosEditPhoto(Context context, Intent intent, String s, String s1, String s2, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", "photos.editPhoto", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("session_key", s);
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        mParams.put("pid", s1);
        if(s2 != null)
            mParams.put("caption", s2);
    }

    protected void parseJSON(JsonParser jsonparser)
    {
        Uri uri = Uri.withAppendedPath(PhotosProvider.PHOTOS_PID_CONTENT_URI, (String)mParams.get("pid"));
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("caption", (String)mParams.get("caption"));
        mContext.getContentResolver().update(uri, contentvalues, null, null);
    }
}
