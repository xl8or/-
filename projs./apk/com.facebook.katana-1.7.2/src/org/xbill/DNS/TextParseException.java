// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TextParseException.java

package org.xbill.DNS;

import java.io.IOException;

public class TextParseException extends IOException
{

    public TextParseException()
    {
    }

    public TextParseException(String s)
    {
        IOException(s);
    }
}
