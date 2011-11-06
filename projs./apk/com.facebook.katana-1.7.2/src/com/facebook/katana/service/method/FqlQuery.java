// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlQuery.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import java.util.Map;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodListener

public class FqlQuery extends ApiMethod
{

    public FqlQuery(Context context, Intent intent, String s, String s1, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", "fql.query", com.facebook.katana.Constants.URL.getApiReadUrl(context), apimethodlistener);
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        mParams.put("query", s1);
        if(s != null)
            mParams.put("session_key", s);
    }

    public String generateLogParams()
    {
        StringBuilder stringbuilder = new StringBuilder(500);
        stringbuilder.append(",\"method\":\"");
        stringbuilder.append(mFacebookMethod);
        stringbuilder.append("\",\"args\":\"");
        stringbuilder.append(getSanitizedQueryString());
        return stringbuilder.toString();
    }

    public String getQueryString()
    {
        return (String)mParams.get("query");
    }

    public String getSanitizedQueryString()
    {
        return getQueryString().replaceAll("([='(, ])[0-9]+_?[0-9]*", "$1NULL");
    }
}
