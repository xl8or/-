// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HttpMultipartMode.java

package org.apache.http.entity.mime;


public final class HttpMultipartMode extends Enum
{

    private HttpMultipartMode(String s, int i)
    {
        super(s, i);
    }

    public static HttpMultipartMode valueOf(String s)
    {
        return (HttpMultipartMode)Enum.valueOf(org/apache/http/entity/mime/HttpMultipartMode, s);
    }

    public static final HttpMultipartMode[] values()
    {
        return (HttpMultipartMode[])$VALUES.clone();
    }

    private static final HttpMultipartMode $VALUES[];
    public static final HttpMultipartMode BROWSER_COMPATIBLE;
    public static final HttpMultipartMode STRICT;

    static 
    {
        STRICT = new HttpMultipartMode("STRICT", 0);
        BROWSER_COMPATIBLE = new HttpMultipartMode("BROWSER_COMPATIBLE", 1);
        HttpMultipartMode ahttpmultipartmode[] = new HttpMultipartMode[2];
        ahttpmultipartmode[0] = STRICT;
        ahttpmultipartmode[1] = BROWSER_COMPATIBLE;
        $VALUES = ahttpmultipartmode;
    }
}
