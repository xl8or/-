// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetNotifications.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookNotification;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlGeneratedQuery, ApiMethodListener

public class FqlGetNotifications extends FqlGeneratedQuery
{

    public FqlGetNotifications(Context context, Intent intent, String s, long l, ApiMethodListener apimethodlistener)
    {
        super(context, intent, s, apimethodlistener, "notification", buildQuery(l), com/facebook/katana/model/FacebookNotification);
    }

    private static String buildQuery(long l)
    {
        return "(recipient_id=%1) AND is_hidden = 0 AND is_mobile_ready AND object_type IN ('stream', 'group', 'friend', 'event', 'photo') ORDER BY created_time DESC LIMIT 50".replaceFirst("%1", (new StringBuilder()).append("").append(l).toString());
    }

    public List getNotifications()
    {
        return mNotifications;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        mNotifications = JMParser.parseObjectListJson(jsonparser, com/facebook/katana/model/FacebookNotification);
    }

    public static final String NOTIFICATIONS_QUERY = "(recipient_id=%1) AND is_hidden = 0 AND is_mobile_ready AND object_type IN ('stream', 'group', 'friend', 'event', 'photo') ORDER BY created_time DESC LIMIT 50";
    private List mNotifications;
}
