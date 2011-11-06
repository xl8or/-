// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InvalidDClassException.java

package org.xbill.DNS;


public class InvalidDClassException extends IllegalArgumentException
{

    public InvalidDClassException(int i)
    {
        IllegalArgumentException((new StringBuilder()).append("Invalid DNS class: ").append(i).toString());
    }
}
