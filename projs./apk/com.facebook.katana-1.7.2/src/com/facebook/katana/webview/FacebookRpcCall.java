// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookRpcCall.java

package com.facebook.katana.webview;

import android.net.Uri;
import java.io.Serializable;
import java.util.*;

public class FacebookRpcCall
{
    static class JsVariable
        implements Serializable
    {

        public String toString()
        {
            return mVarName;
        }

        private static final long serialVersionUID = 0xdaf7cb73L;
        private String mVarName;

        public JsVariable(String s)
        {
            mVarName = s;
        }
    }


    public FacebookRpcCall(Uri uri1)
    {
        uri = uri1;
        List list = uri.getPathSegments();
        if(list.size() == 3)
            uuid = UUID.fromString((String)list.get(1));
        else
            uuid = null;
        method = uri1.getLastPathSegment();
    }

    static String jsComposeFacebookRpcCall(String s, String s1, UUID uuid1, UUID uuid2, String s2, Map map)
    {
        StringBuilder stringbuilder = new StringBuilder();
        Object aobj[] = new Object[2];
        aobj[0] = s;
        aobj[1] = s1;
        stringbuilder.append(String.format("'%s://%s/", aobj));
        if(uuid1 != null)
            stringbuilder.append(uuid1.toString()).append("/");
        if(uuid2 != null)
            stringbuilder.append(uuid2.toString()).append("/");
        stringbuilder.append(s2).append("/'");
        boolean flag = true;
        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            String s3;
            Serializable serializable;
            if(flag)
            {
                stringbuilder.append(" + '?' + ");
                flag = false;
            } else
            {
                stringbuilder.append(" + '&' + ");
            }
            s3 = (String)entry.getKey();
            serializable = (Serializable)entry.getValue();
            stringbuilder.append("'").append(Uri.encode(s3)).append("=' + ");
            if(serializable instanceof JsVariable)
                stringbuilder.append("encodeURIComponent(").append(serializable).append(")");
            else
                stringbuilder.append("'").append(Uri.encode(serializable.toString())).append("'");
        }

        return stringbuilder.toString();
    }

    public String getParameterByName(String s)
    {
        String s1;
        if(uri != null)
            s1 = uri.getQueryParameter(s);
        else
            s1 = null;
        return s1;
    }

    public final String method;
    protected final Uri uri;
    public final UUID uuid;
}
