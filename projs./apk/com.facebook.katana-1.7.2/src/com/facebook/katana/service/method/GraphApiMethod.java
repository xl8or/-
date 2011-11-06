// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GraphApiMethod.java

package com.facebook.katana.service.method;

import android.content.Context;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.URLQueryBuilder;
import java.io.*;
import java.util.Map;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, HttpOperation, ApiMethodListener

public class GraphApiMethod extends ApiMethod
{

    public GraphApiMethod(Context context, String s, String s1, String s2)
    {
        super(context, null, s, s1, s2, null);
    }

    public GraphApiMethod(Context context, String s, String s1, String s2, String s3)
    {
        super(context, null, s1, s2, s3, null);
        mParams.put("access_token", s);
    }

    public void addAuthenticationData(FacebookSessionInfo facebooksessioninfo)
    {
        if(!$assertionsDisabled && facebooksessioninfo == null)
            throw new AssertionError();
        if(!$assertionsDisabled && facebooksessioninfo.oAuthToken == null)
        {
            throw new AssertionError();
        } else
        {
            mParams.put("access_token", facebooksessioninfo.oAuthToken);
            return;
        }
    }

    protected StringBuilder buildQueryString()
    {
        return URLQueryBuilder.buildQueryString(mParams);
    }

    protected String buildUrl(boolean flag, boolean flag1)
        throws UnsupportedEncodingException
    {
        StringBuilder stringbuilder;
        String s;
        StringBuilder stringbuilder1;
        String s1;
        StringBuffer stringbuffer;
        if(flag)
            stringbuilder = new StringBuilder(mBaseUrl);
        else
            stringbuilder = new StringBuilder("/");
        stringbuilder.append(mFacebookMethod);
        if(!flag1) goto _L2; else goto _L1
_L1:
        stringbuilder1 = buildQueryString();
        if(stringbuilder1.length() != 0) goto _L4; else goto _L3
_L3:
        s1 = getClass().getName();
        String s2;
        if("GraphApiMethod" != s1)
        {
            stringbuffer = new StringBuffer("GraphApiMethod");
            stringbuffer.append("(");
            stringbuffer.append(s1);
            stringbuffer.append(")");
            s2 = stringbuffer.toString();
        } else
        {
            s2 = "GraphApiMethod";
        }
        Log.e(s2, "We always should have something in the query (e.g., the signature)");
        s = mBaseUrl;
_L6:
        return s;
_L4:
        stringbuilder.append("?");
        stringbuilder.append(stringbuilder1);
_L2:
        s = stringbuilder.toString();
        if(true) goto _L6; else goto _L5
_L5:
    }

    public void start()
    {
        if(!mHttpMethod.equals("GET")) goto _L2; else goto _L1
_L1:
        mHttpOp = new HttpOperation(mContext, mHttpMethod, buildUrl(true, true), new ByteArrayOutputStream(8192), mHttpListener, true);
_L4:
        mHttpOp.start();
        break MISSING_BLOCK_LABEL_174;
_L2:
        if(!mHttpMethod.equals("POST"))
            break; /* Loop/switch isn't completed */
        ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(buildQueryString().toString().getBytes("UTF-8"));
        mHttpOp = new HttpOperation(mContext, buildUrl(true, false), bytearrayinputstream, new ByteArrayOutputStream(8192), "application/x-www-form-urlencoded", mHttpListener, true);
        if(true) goto _L4; else goto _L3
        Exception exception;
        exception;
        exception.printStackTrace();
        if(mListener != null)
            mListener.onOperationComplete(this, 0, null, exception);
        break MISSING_BLOCK_LABEL_174;
_L3:
        throw new IllegalArgumentException("Unknown HTTP method");
    }

    static final boolean $assertionsDisabled = false;
    public static final String OAUTH_TOKEN_PARAM = "access_token";
    private static final String TAG = "GraphApiMethod";

    static 
    {
        boolean flag;
        if(!com/facebook/katana/service/method/GraphApiMethod.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}
