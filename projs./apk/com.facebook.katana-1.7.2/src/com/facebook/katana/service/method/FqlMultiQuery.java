// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlMultiQuery.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.*;
import org.json.JSONException;
import org.json.JSONStringer;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, FqlQuery, FBJsonFactory, ApiMethodListener

public class FqlMultiQuery extends ApiMethod
{

    public FqlMultiQuery(Context context, Intent intent, String s, LinkedHashMap linkedhashmap, ApiMethodListener apimethodlistener)
    {
        JSONStringer jsonstringer;
        super(context, intent, "GET", "fql.multiquery", com.facebook.katana.Constants.URL.getApiReadUrl(context), apimethodlistener);
        mQueries = linkedhashmap;
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        jsonstringer = new JSONStringer();
        JSONException jsonexception;
        jsonstringer.object();
        java.util.Map.Entry entry;
        for(Iterator iterator = linkedhashmap.entrySet().iterator(); iterator.hasNext(); jsonstringer.value(((FqlQuery)entry.getValue()).getQueryString()))
        {
            entry = (java.util.Map.Entry)iterator.next();
            jsonstringer.key((String)entry.getKey());
        }

          goto _L1
_L3:
        if(s != null)
            mParams.put("session_key", s);
        return;
_L1:
        try
        {
            jsonstringer.endObject();
            mParams.put("queries", jsonstringer.toString());
        }
        // Misplaced declaration of an exception variable
        catch(JSONException jsonexception) { }
        if(true) goto _L3; else goto _L2
_L2:
    }

    public void addAuthenticationData(FacebookSessionInfo facebooksessioninfo)
    {
        super.addAuthenticationData(facebooksessioninfo);
        for(Iterator iterator = mQueries.values().iterator(); iterator.hasNext(); ((FqlQuery)iterator.next()).addAuthenticationData(facebooksessioninfo));
    }

    protected String generateLogParams()
    {
        StringBuilder stringbuilder = new StringBuilder(500);
        stringbuilder.append(",\"method\":\"");
        stringbuilder.append(mFacebookMethod);
        stringbuilder.append("\",\"args\":\"");
        for(Iterator iterator = mQueries.values().iterator(); iterator.hasNext(); stringbuilder.append(':'))
            stringbuilder.append(((FqlQuery)iterator.next()).getSanitizedQueryString());

        return stringbuilder.toString();
    }

    public FqlQuery getQueryByName(String s)
    {
        return (FqlQuery)mQueries.get(s);
    }

    protected void parseJSON(JsonParser jsonparser, String s)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        JsonToken jsontoken = jsonparser.getCurrentToken();
        if(jsontoken == JsonToken.START_OBJECT)
            throw new FacebookApiException(jsonparser);
        if(jsontoken != JsonToken.START_ARRAY)
            throw new IOException("Unexpected JSON response");
        JsonToken jsontoken1 = jsonparser.nextToken();
        do
        {
            if(jsontoken1 == JsonToken.END_ARRAY)
                break;
            if(jsontoken1 != JsonToken.START_OBJECT)
                throw new IOException("Unexpected JSON response");
            JsonLocation jsonlocation = null;
            JsonLocation jsonlocation1 = null;
            FqlQuery fqlquery = null;
            JsonToken jsontoken2 = jsonparser.nextToken();
            while(jsontoken2 != JsonToken.END_OBJECT) 
            {
                if(jsontoken2 == JsonToken.FIELD_NAME)
                {
                    String s1 = jsonparser.getText();
                    jsonparser.nextToken();
                    if(s1.equals("fql_result_set"))
                    {
                        if(fqlquery == null)
                        {
                            jsonlocation = jsonparser.getCurrentLocation();
                            jsonparser.skipChildren();
                            jsonlocation1 = jsonparser.getCurrentLocation();
                        } else
                        {
                            fqlquery.parseJSON(jsonparser);
                        }
                    } else
                    if(s1.equals("name"))
                    {
                        fqlquery = (FqlQuery)mQueries.get(jsonparser.getText());
                        if(jsonlocation != null)
                            fqlquery.parseResponse(s.substring((int)jsonlocation.getCharOffset(), 1 + (int)jsonlocation1.getCharOffset()));
                    }
                }
                jsontoken2 = jsonparser.nextToken();
            }
            jsontoken1 = jsonparser.nextToken();
        } while(true);
    }

    protected void parseResponse(String s)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        printJson(s);
        JsonParser jsonparser = mJsonFactory.createJsonParser(s);
        jsonparser.nextToken();
        parseJSON(jsonparser, s);
    }

    private final Map mQueries;
}
