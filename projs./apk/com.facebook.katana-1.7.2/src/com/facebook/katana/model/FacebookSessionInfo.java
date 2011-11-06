// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookSessionInfo.java

package com.facebook.katana.model;

import com.facebook.katana.util.jsonmirror.*;
import java.io.IOException;
import org.codehaus.jackson.*;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package com.facebook.katana.model:
//            FacebookUser

public class FacebookSessionInfo extends JMCachingDictDestination
{

    protected FacebookSessionInfo()
    {
        username = null;
        sessionKey = null;
        sessionSecret = null;
        oAuthToken = null;
        userId = -1L;
    }

    public FacebookSessionInfo(FacebookSessionInfo facebooksessioninfo, String s)
    {
        username = facebooksessioninfo.username;
        sessionKey = facebooksessioninfo.sessionKey;
        sessionSecret = facebooksessioninfo.sessionSecret;
        oAuthToken = s;
        userId = facebooksessioninfo.userId;
        mFilterKey = facebooksessioninfo.mFilterKey;
        mMyself = facebooksessioninfo.mMyself;
    }

    public static FacebookSessionInfo parseFromJson(String s)
        throws JsonParseException, IOException, JMException
    {
        JsonParser jsonparser = (new JsonFactory()).createJsonParser(s);
        jsonparser.nextToken();
        return (FacebookSessionInfo)JMParser.parseObjectJson(jsonparser, com/facebook/katana/model/FacebookSessionInfo);
    }

    public String getFilterKey()
    {
        return mFilterKey;
    }

    public FacebookUser getProfile()
    {
        return mMyself;
    }

    public void setFilterKey(String s)
    {
        mFilterKey = s;
    }

    public void setProfile(FacebookUser facebookuser)
    {
        mMyself = facebookuser;
    }

    public JSONObject toJSONObject()
    {
        JSONObject jsonobject;
        jsonobject = new JSONObject();
        jsonobject.put("username", username);
        jsonobject.put("uid", userId);
        jsonobject.put("session_key", sessionKey);
        jsonobject.put("secret", sessionSecret);
        jsonobject.put("access_token", oAuthToken);
        if(mMyself != null)
            jsonobject.put("profile", mMyself.toJSONObject());
        if(mFilterKey != null)
            jsonobject.put("filter", mFilterKey);
        JSONObject jsonobject1 = jsonobject;
_L2:
        return jsonobject1;
        JSONException jsonexception;
        jsonexception;
        jsonobject1 = new JSONObject();
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static final String FILTER_KEY = "filter";
    public static final String OAUTH_TOKEN_KEY = "access_token";
    public static final String PROFILE_KEY = "profile";
    public static final String SECRET_KEY = "secret";
    public static final String SESSION_KEY = "session_key";
    public static final String USERNAME_KEY = "username";
    public static final String USER_ID_KEY = "uid";
    private String mFilterKey;
    private FacebookUser mMyself;
    public final String oAuthToken;
    public final String sessionKey;
    public final String sessionSecret;
    public final long userId;
    public final String username;
}
