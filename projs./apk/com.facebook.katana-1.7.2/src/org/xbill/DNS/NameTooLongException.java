// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NameTooLongException.java

package org.xbill.DNS;


// Referenced classes of package org.xbill.DNS:
//            WireParseException

public class NameTooLongException extends WireParseException
{

    public NameTooLongException()
    {
    }

    public NameTooLongException(String s)
    {
        WireParseException(s);
    }
}
