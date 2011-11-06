// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserUnregisterPushCallback.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.provider.KeyValueManager;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodListener

public class UserUnregisterPushCallback extends ApiMethod
{

    public UserUnregisterPushCallback(Context context, Intent intent, String s, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "POST", "user.unregisterPushCallback", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("call_id", Long.toString(System.currentTimeMillis()));
        mParams.put("session_key", s);
        mSuccess = false;
    }

    public boolean getSuccess()
    {
        return mSuccess;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException
    {
        if(jsonparser.getCurrentToken() == JsonToken.VALUE_TRUE)
        {
            mSuccess = true;
            KeyValueManager.delete(mContext, "key=\"C2DMKey\"", null);
        }
    }

    private static final String METHOD = "user.unregisterPushCallback";
    private boolean mSuccess;
}
