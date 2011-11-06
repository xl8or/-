// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StreamGetComments.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodListener, FqlGetProfile

public class StreamGetComments extends ApiMethod
    implements ApiMethodListener
{

    public StreamGetComments(Context context, Intent intent, String s, String s1, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", "stream.getComments", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        mParams.put("session_key", s);
        mParams.put("post_id", s1);
    }

    public List getComments()
    {
        return mComments;
    }

    protected void onHttpComplete(int i, String s, Exception exception)
    {
        if(i == 200)
        {
            HashMap hashmap = new HashMap();
            for(Iterator iterator = mComments.iterator(); iterator.hasNext(); hashmap.put(Long.valueOf(((com.facebook.katana.model.FacebookPost.Comment)iterator.next()).fromId), null));
            (new FqlGetProfile(mContext, mReqIntent, (String)mParams.get("session_key"), this, hashmap)).start();
        } else
        {
            super.onHttpComplete(i, s, exception);
        }
    }

    public void onOperationComplete(ApiMethod apimethod, int i, String s, Exception exception)
    {
        if(i == 200)
        {
            Map map = ((FqlGetProfile)apimethod).getProfiles();
            for(Iterator iterator = mComments.iterator(); iterator.hasNext();)
            {
                com.facebook.katana.model.FacebookPost.Comment comment = (com.facebook.katana.model.FacebookPost.Comment)iterator.next();
                FacebookProfile facebookprofile = (FacebookProfile)map.get(Long.valueOf(comment.fromId));
                if(facebookprofile == null)
                {
                    comment.setProfile(new FacebookProfile(comment.fromId, mContext.getString(0x7f0a0067), null, 0));
                } else
                {
                    String s1 = facebookprofile.mDisplayName;
                    if(s1 == null || s1.length() == 0)
                        mContext.getString(0x7f0a0067);
                    comment.setProfile(facebookprofile);
                }
            }

        }
        onHttpComplete(i, s, exception);
    }

    public void onOperationProgress(ApiMethod apimethod, long l, long l1)
    {
    }

    public void onProcessComplete(ApiMethod apimethod, int i, String s, Exception exception)
    {
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        JsonToken jsontoken = jsonparser.getCurrentToken();
        if(jsontoken == JsonToken.START_OBJECT)
            throw new FacebookApiException(jsonparser);
        if(jsontoken == JsonToken.START_ARRAY)
            for(; jsontoken != JsonToken.END_ARRAY; jsontoken = jsonparser.nextToken())
                if(jsontoken == JsonToken.START_OBJECT)
                    mComments.add(com.facebook.katana.model.FacebookPost.Comment.parseJson(jsonparser));

        else
            throw new IOException("Malformed JSON");
    }

    private final List mComments = new ArrayList();

}
