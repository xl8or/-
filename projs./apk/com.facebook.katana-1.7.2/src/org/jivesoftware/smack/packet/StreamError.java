// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StreamError.java

package org.jivesoftware.smack.packet;


public class StreamError
{

    public StreamError(String s)
    {
        code = s;
    }

    public String getCode()
    {
        return code;
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("stream:error (").append(code).append(")");
        return stringbuilder.toString();
    }

    private String code;
}
