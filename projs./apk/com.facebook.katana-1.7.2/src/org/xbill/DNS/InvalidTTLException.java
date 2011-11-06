// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InvalidTTLException.java

package org.xbill.DNS;


public class InvalidTTLException extends IllegalArgumentException
{

    public InvalidTTLException(long l)
    {
        IllegalArgumentException((new StringBuilder()).append("Invalid DNS TTL: ").append(l).toString());
    }
}
