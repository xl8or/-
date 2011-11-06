// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NotificationsMarkRead.java

package com.facebook.katana.service.method;

import android.content.*;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.provider.NotificationsProvider;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, FBJsonFactory, ApiMethodListener

public class NotificationsMarkRead extends ApiMethod
{

    public NotificationsMarkRead(Context context, Intent intent, String s, long al[], ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", "notifications.markRead", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        mParams.put("session_key", s);
        StringBuffer stringbuffer = new StringBuffer(64);
        boolean flag = true;
        int i = 0;
        while(i < al.length) 
        {
            if(!flag)
                stringbuffer.append(",");
            else
                flag = false;
            stringbuffer.append(al[i]);
            i++;
        }
        mParams.put("notification_ids", stringbuffer.toString());
    }

    private void updateReadFlag()
    {
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("is_read", Integer.valueOf(1));
        String s = (new StringBuilder()).append("notif_id IN(").append((String)mParams.get("notification_ids")).append(")").toString();
        mContext.getContentResolver().update(NotificationsProvider.CONTENT_URI, contentvalues, s, null);
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
            updateReadFlag();
            return;
        }
    }
}
