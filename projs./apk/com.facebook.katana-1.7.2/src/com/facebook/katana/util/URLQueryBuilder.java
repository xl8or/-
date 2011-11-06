// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   URLQueryBuilder.java

package com.facebook.katana.util;

import android.os.Bundle;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

// Referenced classes of package com.facebook.katana.util:
//            Log

public class URLQueryBuilder
{

    public URLQueryBuilder()
    {
    }

    public static StringBuilder buildQueryString(Bundle bundle)
    {
        TreeMap treemap = new TreeMap();
        String s;
        for(Iterator iterator = bundle.keySet().iterator(); iterator.hasNext(); treemap.put(s, bundle.getString(s)))
            s = (String)iterator.next();

        return buildQueryString(((Map) (treemap)));
    }

    public static StringBuilder buildQueryString(Map map)
    {
        if(map != null) goto _L2; else goto _L1
_L1:
        StringBuilder stringbuilder = new StringBuilder("");
_L6:
        return stringbuilder;
_L2:
        StringBuilder stringbuilder1;
        boolean flag;
        Iterator iterator;
        stringbuilder1 = new StringBuilder();
        flag = true;
        iterator = map.keySet().iterator();
_L3:
        String s2;
        if(!iterator.hasNext())
            break MISSING_BLOCK_LABEL_156;
        String s = (String)iterator.next();
        String s1;
        UnsupportedEncodingException unsupportedencodingexception;
        StringBuilder stringbuilder2;
        if(flag)
            flag = false;
        else
            stringbuilder1.append("&");
        s1 = (String)map.get(s);
        stringbuilder2 = stringbuilder1.append(URLEncoder.encode(s, "UTF-8")).append("=");
        if(s1 == null)
            break MISSING_BLOCK_LABEL_149;
        s2 = URLEncoder.encode(s1, "UTF-8");
_L4:
        stringbuilder2.append(s2);
          goto _L3
        try
        {
            s2 = "";
        }
        // Misplaced declaration of an exception variable
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            Log.e("URLQueryBuilder", "UTF-8 encoding not supported on this system.", unsupportedencodingexception);
            stringbuilder = null;
            continue; /* Loop/switch isn't completed */
        }
          goto _L4
        stringbuilder = stringbuilder1;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public static String buildSignature(String s)
        throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        MessageDigest messagedigest = MessageDigest.getInstance("MD5");
        messagedigest.reset();
        byte abyte0[] = s.getBytes("UTF-8");
        messagedigest.update(abyte0, 0, abyte0.length);
        byte abyte1[] = messagedigest.digest();
        StringBuilder stringbuilder = new StringBuilder(2 * abyte1.length);
        for(int i = 0; i < abyte1.length; i++)
        {
            int j = 0xff & abyte1[i];
            if(j < 16)
                stringbuilder.append("0");
            stringbuilder.append(Integer.toHexString(j).toLowerCase());
        }

        return stringbuilder.toString();
    }

    public static StringBuilder buildSignedQueryString(Bundle bundle, String s)
    {
        TreeMap treemap = new TreeMap();
        String s1;
        for(Iterator iterator = bundle.keySet().iterator(); iterator.hasNext(); treemap.put(s1, bundle.getString(s1)))
            s1 = (String)iterator.next();

        return buildSignedQueryString(((Map) (treemap)), s);
    }

    public static StringBuilder buildSignedQueryString(Map map, String s)
    {
        map.put("sig", buildSignature(signatureString(map, s)));
        StringBuilder stringbuilder = buildQueryString(map);
_L2:
        return stringbuilder;
        Exception exception;
        exception;
        stringbuilder = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    protected static List keysInSignatureOrder(Map map)
    {
        ArrayList arraylist = new ArrayList(new HashSet(map.keySet()));
        Collections.sort(arraylist);
        return arraylist;
    }

    public static Bundle parseQueryString(String s)
    {
        Bundle bundle;
        if(s == null)
        {
            bundle = new Bundle();
        } else
        {
            Bundle bundle1 = new Bundle();
            String as[] = s.split("&");
            int i = as.length;
            int j = 0;
            while(j < i) 
            {
                String as1[] = as[j].split("=");
                String s1 = URLDecoder.decode(as1[0]);
                String s2;
                if(as1.length > 1)
                    s2 = URLDecoder.decode(as1[1]);
                else
                    s2 = "";
                bundle1.putString(s1, s2);
                j++;
            }
            bundle = bundle1;
        }
        return bundle;
    }

    public static String signatureString(Map map, String s)
    {
        StringBuilder stringbuilder = new StringBuilder(256);
        String s1;
        for(Iterator iterator = keysInSignatureOrder(map).iterator(); iterator.hasNext(); stringbuilder.append(s1).append("=").append((String)map.get(s1)))
            s1 = (String)iterator.next();

        stringbuilder.append(s);
        return stringbuilder.toString();
    }

    public static final String AMPERSAND = "&";
    public static final String EQUALS = "=";
}
