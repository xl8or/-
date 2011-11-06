// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WireParseException.java

package org.xbill.DNS;

import java.io.IOException;

public class WireParseException extends IOException
{

    public WireParseException()
    {
    }

    public WireParseException(String s)
    {
        super(s);
    }
}
