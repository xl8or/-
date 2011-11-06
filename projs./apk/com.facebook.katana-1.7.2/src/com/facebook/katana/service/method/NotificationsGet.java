// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NotificationsGet.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookNotifications;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodListener

public class NotificationsGet extends ApiMethod
{

    public NotificationsGet(Context context, Intent intent, String s, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", "notifications.get", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        mParams.put("session_key", s);
    }

    public FacebookNotifications getNotifications()
    {
        return mNotifications;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException
    {
        mNotifications = new FacebookNotifications(jsonparser);
        if(mNotifications.hasNewNotifications())
            FacebookNotifications.save(mContext);
    }

    private FacebookNotifications mNotifications;
}
