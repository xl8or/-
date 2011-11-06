// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FriendsAddFriend.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodCallback, FBJsonFactory, ApiMethodListener

public class FriendsAddFriend extends ApiMethod
    implements ApiMethodCallback
{

    protected FriendsAddFriend(Context context, Intent intent, String s, Long long1, String s1, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "POST", "facebook.friends_add", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mUids = new ArrayList();
        mUids.add(long1);
        mParams.put("uid", String.valueOf(long1));
        addCommonParams(s, s1);
    }

    protected FriendsAddFriend(Context context, Intent intent, String s, List list, String s1, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "POST", "facebook.friends_add", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mUids = list;
        Map map = mParams;
        Object aobj[] = new Object[1];
        aobj[0] = list;
        map.put("uids", StringUtils.join(",", aobj));
        addCommonParams(s, s1);
    }

    public static String friendsAddFriend(AppSession appsession, Context context, Long long1, String s)
    {
        return appsession.postToService(context, new FriendsAddFriend(context, null, appsession.getSessionInfo().sessionKey, long1, s, null), 1001, 1020, null);
    }

    public static String friendsAddFriends(AppSession appsession, Context context, List list, String s)
    {
        return appsession.postToService(context, new FriendsAddFriend(context, null, appsession.getSessionInfo().sessionKey, list, s, null), 1001, 1020, null);
    }

    protected void addCommonParams(String s, String s1)
    {
        mParams.put("call_id", Long.toString(System.currentTimeMillis()));
        mParams.put("session_key", s);
        if(s1 != null)
            mParams.put("message", s1);
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        for(Iterator iterator = appsession.getListeners().iterator(); iterator.hasNext(); ((AppSessionListener)iterator.next()).onFriendsAddFriendComplete(appsession, s, i, s1, exception, mUids));
    }

    protected void parseResponse(String s)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        printJson(s);
        if(s.startsWith("{"))
            throw new FacebookApiException(mJsonFactory.createJsonParser(s));
        else
            return;
    }

    protected List mUids;
}
