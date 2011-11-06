// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FriendRequestRespond.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodListener

public class FriendRequestRespond extends ApiMethod
{

    public FriendRequestRespond(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, long l, boolean flag)
    {
        super(context, intent, "POST", "facebook.friends.confirm", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        mParams.put("session_key", s);
        mParams.put("uid", Long.toString(l));
        Map map = mParams;
        String s1;
        if(flag)
            s1 = "1";
        else
            s1 = "0";
        map.put("confirm", s1);
        mRequesterUserId = l;
        mSuccess = false;
    }

    public long getRequesterUserId()
    {
        return mRequesterUserId;
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

    private long mRequesterUserId;
    private boolean mSuccess;
}
