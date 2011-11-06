// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserUpdateAlertSettings.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.*;
import org.json.JSONException;
import org.json.JSONStringer;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodListener

public class UserUpdateAlertSettings extends ApiMethod
{

    public UserUpdateAlertSettings(Context context, Intent intent, String s, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "POST", "user.updateAlertSettings", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("call_id", Long.toString(System.currentTimeMillis()));
        mParams.put("session_key", s);
        mParams.put("settings", pushSettings());
        mSuccess = false;
    }

    private String pushSettings()
    {
        JSONStringer jsonstringer;
        JSONStringer jsonstringer1;
        jsonstringer = new JSONStringer();
        jsonstringer1 = new JSONStringer();
        jsonstringer.object();
        jsonstringer.key("push");
        jsonstringer1.object();
        java.util.Map.Entry entry;
        for(Iterator iterator = settings.entrySet().iterator(); iterator.hasNext(); jsonstringer1.value(entry.getValue()))
        {
            entry = (java.util.Map.Entry)iterator.next();
            jsonstringer1.key((String)entry.getKey());
        }

          goto _L1
        JSONException jsonexception;
        jsonexception;
        String s = "";
_L3:
        return s;
_L1:
        jsonstringer1.endObject();
        jsonstringer.value(jsonstringer1.toString());
        jsonstringer.endObject();
        s = jsonstringer.toString();
        if(true) goto _L3; else goto _L2
_L2:
    }

    public boolean getSuccess()
    {
        return mSuccess;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException
    {
        if(jsonparser.getCurrentToken() == JsonToken.VALUE_TRUE)
            mSuccess = true;
    }

    private static final String METHOD = "user.updateAlertSettings";
    private static final String PUSH_MEDIUM = "push";
    private static final Map settings = Collections.unmodifiableMap(new HashMap() {

            
            {
                put("msg", Boolean.TRUE);
                put("wall", Boolean.TRUE);
                put("friend", Boolean.TRUE);
                put("friend_confirmed", Boolean.TRUE);
                put("event_cancel", Boolean.TRUE);
                put("event_update", Boolean.TRUE);
                put("event_invite", Boolean.TRUE);
                put("place_checkin_nearby", Boolean.TRUE);
                put("place_checkin_comment", Boolean.TRUE);
                put("like_place_checkin", Boolean.TRUE);
                put("place_tagged_in_checkin", Boolean.TRUE);
                put("place_tagged_in_checkin", Boolean.TRUE);
                put("photo_tag", Boolean.TRUE);
                put("photo_comment", Boolean.TRUE);
                put("photo_comment", Boolean.TRUE);
                put("photo_album_comment", Boolean.TRUE);
                put("photo_album_reply", Boolean.TRUE);
                put("share_comment", Boolean.TRUE);
                put("share_reply", Boolean.TRUE);
                put("video_comment", Boolean.TRUE);
                put("video_reply", Boolean.TRUE);
                put("like", Boolean.TRUE);
                put("feed_comment", Boolean.TRUE);
                put("feed_comment_reply", Boolean.TRUE);
            }
    }
);
    private boolean mSuccess;

}
