// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetPlaceCheckins.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.binding.*;
import com.facebook.katana.model.*;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlGetStream, ApiMethodCallback, FqlGetProfile, ApiMethodListener

public class FqlGetPlaceCheckins extends FqlGetStream
    implements ApiMethodCallback
{

    protected FqlGetPlaceCheckins(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, long l, long l1, FacebookPlace facebookplace, int i, int j)
    {
        super(context, intent, s, apimethodlistener, buildQueries(context, intent, s, l, l1, facebookplace.mPageId, i));
        mStartTime = l;
        mEndTime = l1;
        mPlace = facebookplace;
        mLimit = i;
        mOpType = j;
    }

    public static String RequestPlaceCheckins(Context context, long l, long l1, FacebookPlace facebookplace, int i, int j)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        FqlGetPlaceCheckins fqlgetplacecheckins = new FqlGetPlaceCheckins(context, null, appsession.getSessionInfo().sessionKey, null, l, l1, facebookplace, i, j);
        Bundle bundle = new Bundle();
        bundle.putLong("subject", facebookplace.mPageId);
        return appsession.postToService(context, fqlgetplacecheckins, 1001, 1020, bundle);
    }

    protected static String buildPostsQuery(long l)
    {
        StringBuilder stringbuilder = new StringBuilder("post_id IN (SELECT post_id FROM checkin WHERE page_id=");
        stringbuilder.append(l);
        stringbuilder.append(") ORDER BY created_time DESC");
        return stringbuilder.toString();
    }

    protected static LinkedHashMap buildQueries(Context context, Intent intent, String s, long l, long l1, long l2, int i)
    {
        LinkedHashMap linkedhashmap = new LinkedHashMap();
        linkedhashmap.put("posts", new FqlGetStream.FqlGetPosts(context, intent, s, null, l, l1, buildPostsQuery(l2), i));
        linkedhashmap.put("profiles", new FqlGetProfile(context, intent, s, null, profileWhereClause));
        return linkedhashmap;
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        FacebookStreamContainer facebookstreamcontainer = null;
        List list = null;
        if(i == 200)
        {
            list = getPosts();
            facebookstreamcontainer = (FacebookStreamContainer)appsession.mPlacesActivityContainerMap.get(Long.valueOf(mPlace.mPageId));
            if(facebookstreamcontainer == null)
            {
                facebookstreamcontainer = new FacebookStreamContainer();
                appsession.mPlacesActivityContainerMap.put(Long.valueOf(mPlace.mPageId), facebookstreamcontainer);
            }
            facebookstreamcontainer.addPosts(list, mLimit, mOpType);
        }
        for(Iterator iterator = appsession.getListeners().iterator(); iterator.hasNext(); ((AppSessionListener)iterator.next()).onStreamGetComplete(appsession, s, i, s1, exception, mPlace.mPageId, mOpType, facebookstreamcontainer, list));
    }

    protected void parseJSON(JsonParser jsonparser, String s)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        super.parseJSON(jsonparser, s);
        Iterator iterator = getPosts().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            FacebookPost facebookpost = (FacebookPost)iterator.next();
            if(facebookpost.getPostType() == 4)
            {
                com.facebook.katana.model.FacebookPost.Attachment attachment = facebookpost.getAttachment();
                if(!$assertionsDisabled && attachment.mCheckinDetails.mPageId != mPlace.mPageId)
                    throw new AssertionError();
                attachment.mCheckinDetails.setPlaceInfo(mPlace);
            }
        } while(true);
    }

    static final boolean $assertionsDisabled = false;
    private static final String TAG = "FqlGetStream";
    public final long mEndTime;
    public final int mLimit;
    public final int mOpType;
    public final FacebookPlace mPlace;
    public final long mStartTime;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/service/method/FqlGetPlaceCheckins.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}
