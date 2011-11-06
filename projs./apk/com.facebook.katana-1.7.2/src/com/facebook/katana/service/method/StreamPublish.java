// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StreamPublish.java

package com.facebook.katana.service.method;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.*;
import com.facebook.katana.model.*;
import com.facebook.katana.util.*;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodCallback, FBJsonFactory, ApiMethodListener

public class StreamPublish extends ApiMethod
    implements ApiMethodCallback
{

    public StreamPublish(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, long l, String s1, 
            String s2, Set set, Set set1, Long long1, FacebookProfile facebookprofile, String s3, boolean flag)
    {
        super(context, intent, "GET", "stream.publish", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        targetId = l;
        rawMessage = s1;
        formattedMessage = s2;
        if(set != null)
            tagged = Collections.unmodifiableSet(set);
        else
            tagged = null;
        updateWidget = flag;
        mActor = facebookprofile;
        mParams.put("call_id", String.valueOf(System.currentTimeMillis()));
        mParams.put("session_key", s);
        mParams.put("target_id", String.valueOf(l));
        mParams.put("message", s1);
        if(set1 != null)
            mParams.put("tags", formatTaggedIds(set1));
        if(long1 != null)
            mParams.put("place", String.valueOf(long1));
        if(facebookprofile != null)
            mParams.put("uid", String.valueOf(facebookprofile.mId));
        if(s3 != null)
            mParams.put("privacy", s3);
    }

    public static String Publish(Context context, long l, String s, String s1, Set set, Long long1, String s2, 
            boolean flag, FacebookProfile facebookprofile)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        return appsession.postToService(context, new StreamPublish(context, null, appsession.getSessionInfo().sessionKey, null, l, s, s1, null, set, long1, facebookprofile, s2, flag), 1001, 1020, null);
    }

    public static String Publish(Context context, long l, String s, String s1, Set set, boolean flag, FacebookProfile facebookprofile)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        return appsession.postToService(context, new StreamPublish(context, null, appsession.getSessionInfo().sessionKey, null, l, s, s1, set, null, null, facebookprofile, null, flag), 1001, 1020, null);
    }

    private String formatTaggedIds(Set set)
    {
        StringBuilder stringbuilder = new StringBuilder("[");
        Object aobj[] = new Object[1];
        aobj[0] = set;
        stringbuilder.append(StringUtils.join(",", aobj));
        stringbuilder.append("]");
        return stringbuilder.toString();
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        FacebookSessionInfo facebooksessioninfo = appsession.getSessionInfo();
        String s2 = mPostId;
        long l = facebooksessioninfo.userId;
        long l1;
        FacebookPost facebookpost;
        if(targetId != facebooksessioninfo.userId)
            l1 = targetId;
        else
            l1 = -1L;
        facebookpost = new FacebookPost(s2, 0xa67c8e50L, l, l1, formattedMessage, null, null, tagged, context.getString(0x7f0a029f));
        if(mActor == null)
            facebookpost.setProfile(new FacebookProfile(facebooksessioninfo.userId, facebooksessioninfo.getProfile().mDisplayName, facebooksessioninfo.getProfile().mImageUrl, 0));
        else
            facebookpost.setProfile(mActor);
        if(i == 200)
        {
            if(targetId == appsession.getSessionInfo().userId)
            {
                FacebookStreamContainer facebookstreamcontainer1 = appsession.getStreamContainer(-1L, FacebookStreamType.NEWSFEED_STREAM);
                if(facebookstreamcontainer1 != null)
                    facebookstreamcontainer1.insertFirst(facebookpost);
            }
            FacebookStreamContainer facebookstreamcontainer = appsession.getStreamContainer(targetId, FacebookStreamType.PROFILE_WALL_STREAM);
            if(facebookstreamcontainer != null)
                facebookstreamcontainer.insertFirst(facebookpost);
        }
        for(Iterator iterator = appsession.getListeners().iterator(); iterator.hasNext(); ((AppSessionListener)iterator.next()).onStreamPublishComplete(appsession, s, i, s1, exception, facebookpost));
        if(!updateWidget)
            break MISSING_BLOCK_LABEL_300;
        Intent intent1 = new Intent();
        intent1.setAction("com.facebook.katana.widget.publish.result");
        intent1.putExtra("extra_error_code", i);
        PendingIntent.getBroadcast(context, 0, intent1, 0).send();
_L1:
        return;
        Exception exception1;
        exception1;
        Log.e(TAG, "widget update failed: ", exception1);
          goto _L1
    }

    public String getPostId()
    {
        return mPostId;
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
            mPostId = removeChar(s, '"');
            return;
        }
    }

    public static final String ACTOR_PARAM = "uid";
    public static final String PLACE_PARAM = "place";
    public static final String PRIVACY_PARAM = "privacy";
    private static final String TAG = Utils.getClassName(com/facebook/katana/service/method/StreamPublish);
    public final String formattedMessage;
    private final FacebookProfile mActor;
    private String mPostId;
    public final String rawMessage;
    public final Set tagged;
    public final long targetId;
    public final boolean updateWidget;

}
