// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GraphBatchRequest.java

package com.facebook.katana.service.method;

import android.content.Context;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.GraphRequestResponse;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.*;
import java.util.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.json.*;

// Referenced classes of package com.facebook.katana.service.method:
//            GraphApiMethod, HttpOperation, ApiMethodListener

public class GraphBatchRequest extends GraphApiMethod
{

    public GraphBatchRequest(Context context, String s, String s1, List list)
    {
        super(context, s, "POST", null, s1);
        mRequests = list;
        mParams.put("batch", constructBatchParam());
    }

    public GraphBatchRequest(Context context, String s, List list)
    {
        this(context, null, s, list);
    }

    protected String constructBatchParam()
    {
        JSONArray jsonarray;
        Iterator iterator;
        jsonarray = new JSONArray();
        iterator = mRequests.iterator();
_L3:
        GraphApiMethod graphapimethod;
        JSONObject jsonobject;
        if(!iterator.hasNext())
            break; /* Loop/switch isn't completed */
        graphapimethod = (GraphApiMethod)iterator.next();
        jsonobject = new JSONObject();
        jsonobject.put("method", graphapimethod.mHttpMethod);
        if(!graphapimethod.mHttpMethod.equals("POST"))
            break MISSING_BLOCK_LABEL_123;
        jsonobject.put("relative_url", graphapimethod.buildUrl(false, false));
        jsonobject.put("body", graphapimethod.buildQueryString().toString());
_L1:
        jsonarray.put(jsonobject);
        continue; /* Loop/switch isn't completed */
        JSONException jsonexception;
        jsonexception;
        Log.e("GraphApiMethod", "error while constructing the batch param", jsonexception);
        continue; /* Loop/switch isn't completed */
        jsonobject.put("relative_url", graphapimethod.buildUrl(false, true));
          goto _L1
        UnsupportedEncodingException unsupportedencodingexception;
        unsupportedencodingexception;
        Log.e("GraphApiMethod", "error encoding something for the batch param", unsupportedencodingexception);
        if(true) goto _L3; else goto _L2
_L2:
        return jsonarray.toString();
    }

    protected void parseResponse(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        responses = JMParser.parseObjectListJson(jsonparser, com/facebook/katana/model/GraphRequestResponse);
    }

    public void start()
    {
        try
        {
            if(mHttpMethod.equals("POST"))
            {
                ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(buildQueryString().toString().getBytes("UTF-8"));
                mHttpOp = new HttpOperation(mContext, buildUrl(true, false), bytearrayinputstream, new ByteArrayOutputStream(8192), "application/x-www-form-urlencoded", mHttpListener, true);
                mHttpOp.start();
            } else
            {
                throw new IllegalArgumentException("HTTP method must be POST for GraphBatchRequest");
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            if(mListener != null)
                mListener.onOperationComplete(this, 0, null, exception);
        }
    }

    private static final String TAG = "GraphApiMethod";
    private final List mRequests;
    protected List responses;
}
