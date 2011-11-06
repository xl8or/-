// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HttpUtils.java

package org.acra.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import org.apache.http.client.ClientProtocolException;

// Referenced classes of package org.acra.util:
//            HttpRequest

public class HttpUtils
{

    public HttpUtils()
    {
    }

    public static void doPost(Map map, URL url, String s, String s1)
        throws ClientProtocolException, IOException
    {
        StringBuilder stringbuilder = new StringBuilder();
        Object obj;
        Object obj1;
        for(Iterator iterator = map.keySet().iterator(); iterator.hasNext(); stringbuilder.append(URLEncoder.encode(obj.toString(), "UTF-8")).append('=').append(URLEncoder.encode(obj1.toString(), "UTF-8")))
        {
            obj = iterator.next();
            if(stringbuilder.length() != 0)
                stringbuilder.append('&');
            obj1 = map.get(obj);
            if(obj1 == null)
                obj1 = "";
        }

        String s2;
        String s3;
        if(isNull(s))
            s2 = null;
        else
            s2 = s;
        if(isNull(s1))
            s3 = null;
        else
            s3 = s1;
        (new HttpRequest(s2, s3)).sendPost(url.toString(), stringbuilder.toString());
    }

    private static boolean isNull(String s)
    {
        boolean flag;
        if(s == null || s == "ACRA-NULL-STRING")
            flag = true;
        else
            flag = false;
        return flag;
    }
}
