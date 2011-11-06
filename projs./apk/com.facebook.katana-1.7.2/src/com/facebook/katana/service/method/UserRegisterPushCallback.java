// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserRegisterPushCallback.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.provider.KeyValueManager;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.*;
import org.json.JSONException;
import org.json.JSONStringer;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodListener

public class UserRegisterPushCallback extends ApiMethod
{

    public UserRegisterPushCallback(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, String s1)
    {
        super(context, intent, "POST", "user.registerPushCallback", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("call_id", Long.toString(System.currentTimeMillis()));
        mParams.put("session_key", s);
        mParams.put("protocol_params", protocolParams(s1));
        mSuccess = false;
        mToken = s1;
    }

    private String protocolParams(String s)
    {
        String s2 = (new JSONStringer()).object().key("token").value(s).endObject().toString();
        String s1 = s2;
_L2:
        return s1;
        JSONException jsonexception;
        jsonexception;
        s1 = "";
        if(true) goto _L2; else goto _L1
_L1:
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
            KeyValueManager.setValue(mContext, "C2DMKey", mToken);
        }
    }

    public static final String KEY_VALUE_REG_ID = "C2DMKey";
    private static final String METHOD = "user.registerPushCallback";
    private static final String TOKEN_PARAM = "token";
    private boolean mSuccess;
    private final String mToken;
}
