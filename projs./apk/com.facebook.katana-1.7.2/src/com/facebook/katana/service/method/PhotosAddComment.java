// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhotosAddComment.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookPhotoComment;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, FBJsonFactory, ApiMethodListener

public class PhotosAddComment extends ApiMethod
{

    public PhotosAddComment(Context context, Intent intent, String s, long l, String s1, String s2, 
            ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", "photos_addcomment", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("session_key", s);
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        mParams.put("pid", s1);
        mParams.put("body", s2);
        mMyUserId = l;
    }

    public FacebookPhotoComment getComment()
    {
        return mComment;
    }

    protected void parseResponse(String s)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        printJson(s);
        if(s.startsWith("{"))
        {
            throw new FacebookApiException(mJsonFactory.createJsonParser(s));
        } else
        {
            long l = Long.parseLong(s.trim());
            mComment = new FacebookPhotoComment((String)mParams.get("pid"), mMyUserId, (String)mParams.get("body"), System.currentTimeMillis() / 1000L, l);
            return;
        }
    }

    private FacebookPhotoComment mComment;
    private final long mMyUserId;
}
