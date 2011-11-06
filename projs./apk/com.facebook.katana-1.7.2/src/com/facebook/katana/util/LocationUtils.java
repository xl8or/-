// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LocationUtils.java

package com.facebook.katana.util;

import java.net.URLEncoder;

public class LocationUtils
{

    public LocationUtils()
    {
    }

    public static String generateGeoIntentURI(String s, double d, double d1)
    {
        return (new StringBuilder()).append("geo:0,0?q=").append(URLEncoder.encode(s)).append("@").append(d).append(",").append(d1).toString();
    }

    public static String generateGoogleMapsURL(double d, double d1, int i, int j, int k)
    {
        StringBuilder stringbuilder = new StringBuilder("http://maps.google.com/maps/api/staticmap?");
        stringbuilder.append((new StringBuilder()).append("center=").append(d).append(",").append(d1).toString());
        stringbuilder.append((new StringBuilder()).append("&zoom=").append(i).toString());
        stringbuilder.append((new StringBuilder()).append("&size=").append(k).append("x").append(j).toString());
        stringbuilder.append((new StringBuilder()).append("&markers=").append(d).append(",").append(d1).toString());
        stringbuilder.append("&sensor=false");
        return stringbuilder.toString();
    }
}
