// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RelativeNameException.java

package org.xbill.DNS;


// Referenced classes of package org.xbill.DNS:
//            Name

public class RelativeNameException extends IllegalArgumentException
{

    public RelativeNameException(String s)
    {
        IllegalArgumentException(s);
    }

    public RelativeNameException(Name name)
    {
        IllegalArgumentException((new StringBuilder()).append("'").append(name).append("' is not an absolute name").toString());
    }
}
