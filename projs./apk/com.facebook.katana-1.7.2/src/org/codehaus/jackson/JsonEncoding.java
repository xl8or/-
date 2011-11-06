// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson;


public final class JsonEncoding extends Enum
{

    private JsonEncoding(String s, int i, String s1, boolean flag)
    {
        super(s, i);
        mJavaName = s1;
        mBigEndian = flag;
    }

    public static JsonEncoding valueOf(String s)
    {
        return (JsonEncoding)Enum.valueOf(org/codehaus/jackson/JsonEncoding, s);
    }

    public static JsonEncoding[] values()
    {
        return (JsonEncoding[])$VALUES.clone();
    }

    public String getJavaName()
    {
        return mJavaName;
    }

    public boolean isBigEndian()
    {
        return mBigEndian;
    }

    private static final JsonEncoding $VALUES[];
    public static final JsonEncoding UTF16_BE;
    public static final JsonEncoding UTF16_LE;
    public static final JsonEncoding UTF32_BE;
    public static final JsonEncoding UTF32_LE;
    public static final JsonEncoding UTF8;
    final boolean mBigEndian;
    final String mJavaName;

    static 
    {
        UTF8 = new JsonEncoding("UTF8", 0, "UTF-8", false);
        UTF16_BE = new JsonEncoding("UTF16_BE", 1, "UTF-16BE", true);
        UTF16_LE = new JsonEncoding("UTF16_LE", 2, "UTF-16LE", false);
        UTF32_BE = new JsonEncoding("UTF32_BE", 3, "UTF-32BE", true);
        UTF32_LE = new JsonEncoding("UTF32_LE", 4, "UTF-32LE", false);
        JsonEncoding ajsonencoding[] = new JsonEncoding[5];
        ajsonencoding[0] = UTF8;
        ajsonencoding[1] = UTF16_BE;
        ajsonencoding[2] = UTF16_LE;
        ajsonencoding[3] = UTF32_BE;
        ajsonencoding[4] = UTF32_LE;
        $VALUES = ajsonencoding;
    }
}
