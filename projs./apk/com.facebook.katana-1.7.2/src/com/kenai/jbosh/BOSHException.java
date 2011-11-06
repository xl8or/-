// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BOSHException.java

package com.kenai.jbosh;


public class BOSHException extends Exception
{

    public BOSHException(String s)
    {
        super(s);
    }

    public BOSHException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    private static final long serialVersionUID = 1L;
}
