// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConfigFetcher.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiLogging, ApiMethodListener

public class ConfigFetcher extends ApiMethod
{

    public ConfigFetcher(Context context, Intent intent, String s, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", "admin.getPrivateConfig", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        mParams.put("session_key", s);
        mParams.put("prop_names", "api_logging_ratio,trx_logging_ratio");
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException
    {
        JsonToken jsontoken = jsonparser.getCurrentToken();
        int i = ApiLogging.API_LOG_RATIO;
        int j = ApiLogging.TRX_LOG_RATIO;
        if(jsontoken == JsonToken.START_OBJECT)
        {
            JsonToken jsontoken1 = jsonparser.nextToken();
            int k = -1;
            String s = null;
            while(jsontoken1 != JsonToken.END_OBJECT) 
            {
                if(jsontoken1 == JsonToken.VALUE_NUMBER_INT)
                {
                    String s1 = jsonparser.getCurrentName();
                    if(s1.equals("api_logging_ratio"))
                        i = jsonparser.getIntValue();
                    else
                    if(s1.equals("trx_logging_ratio"))
                        j = jsonparser.getIntValue();
                    else
                    if(s1.equals("error_code"))
                        k = jsonparser.getIntValue();
                } else
                if(jsontoken1 == JsonToken.VALUE_STRING && jsonparser.getCurrentName().equals("error_msg"))
                    s = jsonparser.getText();
                jsontoken1 = jsonparser.nextToken();
            }
            if(k > 0)
            {
                throw new FacebookApiException(k, s);
            } else
            {
                ApiLogging.updateLogRatios(i, j);
                return;
            }
        } else
        {
            throw new IOException("Malformed JSON");
        }
    }

    private static final String PROP_API_LOGGING_RATIO = "api_logging_ratio";
    private static final String PROP_NAMES_PARAM = "prop_names";
    private static final String PROP_TRX_LOGGING_RATIO = "trx_logging_ratio";
    private static final String mFacebookMethod = "admin.getPrivateConfig";
}
