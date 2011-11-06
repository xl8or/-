// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlacesRemoveTag.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookPost;
import com.facebook.katana.model.FacebookSessionInfo;
import java.util.*;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodCallback, ApiMethodListener

public class PlacesRemoveTag extends ApiMethod
    implements ApiMethodCallback
{

    public PlacesRemoveTag(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, FacebookPost facebookpost, long l)
    {
        super(context, intent, "GET", "places.removeTag", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mPost = facebookpost;
        mUserId = l;
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        mParams.put("session_key", s);
        mParams.put("post_id", mPost.postId);
        mParams.put("tagged_uid", Long.toString(mUserId));
    }

    public static String RemoveTag(AppSession appsession, Context context, FacebookPost facebookpost, long l)
    {
        return appsession.postToService(context, new PlacesRemoveTag(context, null, appsession.getSessionInfo().sessionKey, null, facebookpost, l), 1001, 1020, null);
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        for(Iterator iterator = appsession.getListeners().iterator(); iterator.hasNext(); ((AppSessionListener)iterator.next()).onPlacesRemoveTagComplete(appsession, s, i, s1, exception, mPost, mUserId));
    }

    public FacebookPost mPost;
    protected long mUserId;
}
