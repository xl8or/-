// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StreamGetFilters.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodListener

public class StreamGetFilters extends ApiMethod
{

    public StreamGetFilters(Context context, Intent intent, String s, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", "stream.getFilters", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("session_key", s);
    }

    private void parseFilter(JsonParser jsonparser)
        throws JsonParseException, IOException
    {
        String s = null;
        String s1 = null;
        JsonToken jsontoken = jsonparser.nextToken();
        while(jsontoken != JsonToken.END_OBJECT) 
        {
            if(jsontoken == JsonToken.VALUE_STRING)
            {
                String s2 = jsonparser.getCurrentName();
                if(s2.equals("name"))
                    s = jsonparser.getText();
                else
                if(s2.equals("filter_key"))
                    s1 = jsonparser.getText();
            }
            jsontoken = jsonparser.nextToken();
        }
        if(s != null && s.equals("News Feed"))
            m_newsFeedFilter = s1;
    }

    public String getNewsFeedFilter()
    {
        return m_newsFeedFilter;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        JsonToken jsontoken = jsonparser.getCurrentToken();
        if(jsontoken == JsonToken.START_OBJECT)
            throw new FacebookApiException(jsonparser);
        if(jsontoken == JsonToken.START_ARRAY)
        {
            for(JsonToken jsontoken1 = jsonparser.nextToken(); jsontoken1 != JsonToken.END_ARRAY; jsontoken1 = jsonparser.nextToken())
                if(jsontoken1 == JsonToken.START_OBJECT)
                    parseFilter(jsonparser);

        } else
        {
            throw new IOException("Malformed JSON");
        }
    }

    private String m_newsFeedFilter;
}
