// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BatchRun.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import org.codehaus.jackson.*;
import org.json.JSONArray;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodListener

public class BatchRun extends ApiMethod
{

    public BatchRun(Context context, Intent intent, String s, List list, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", "batch.run", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        JSONArray jsonarray;
        NoSuchAlgorithmException nosuchalgorithmexception;
        jsonarray = new JSONArray();
        ApiMethod apimethod;
        for(Iterator iterator = list.iterator(); iterator.hasNext(); jsonarray.put(apimethod.buildQueryString()))
        {
            apimethod = (ApiMethod)iterator.next();
            apimethod.addCommonParameters();
            apimethod.addSignature();
        }

          goto _L1
_L3:
        mMethods = list;
        return;
_L1:
        try
        {
            mParams.put("session_key", s);
            mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
            mParams.put("method_feed", jsonarray.toString());
        }
        // Misplaced declaration of an exception variable
        catch(NoSuchAlgorithmException nosuchalgorithmexception)
        {
            apimethodlistener.onOperationComplete(this, 0, null, nosuchalgorithmexception);
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            apimethodlistener.onOperationComplete(this, 0, null, unsupportedencodingexception);
        }
        if(true) goto _L3; else goto _L2
_L2:
    }

    public void addAuthenticationData(FacebookSessionInfo facebooksessioninfo)
    {
        super.addAuthenticationData(facebooksessioninfo);
        for(Iterator iterator = mMethods.iterator(); iterator.hasNext(); ((ApiMethod)iterator.next()).addAuthenticationData(facebooksessioninfo));
    }

    protected String generateLogParams()
    {
        StringBuilder stringbuilder = new StringBuilder(500);
        stringbuilder.append(",\"method\":\"");
        stringbuilder.append(mFacebookMethod);
        stringbuilder.append("\",\"args\":\"");
        for(Iterator iterator = mMethods.iterator(); iterator.hasNext(); stringbuilder.append(':'))
            stringbuilder.append(((ApiMethod)iterator.next()).mFacebookMethod);

        return stringbuilder.toString();
    }

    public List getMethods()
    {
        return mMethods;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        JsonToken jsontoken = jsonparser.getCurrentToken();
        if(jsontoken == JsonToken.START_OBJECT)
            throw new FacebookApiException(jsonparser);
        if(jsontoken == JsonToken.START_ARRAY)
        {
            JsonToken jsontoken1 = jsonparser.nextToken();
            int i = 0;
            for(; jsontoken1 != JsonToken.END_ARRAY; jsontoken1 = jsonparser.nextToken())
                if(jsontoken1 == JsonToken.VALUE_STRING)
                {
                    if(i >= mMethods.size())
                        throw new IOException((new StringBuilder()).append("Methods index: ").append(i).append(", size: ").append(mMethods.size()).toString());
                    ((ApiMethod)mMethods.get(i)).parseResponse(jsonparser.getText());
                    i++;
                }

        } else
        {
            throw new IOException("Malformed JSON");
        }
    }

    private final List mMethods;
}
