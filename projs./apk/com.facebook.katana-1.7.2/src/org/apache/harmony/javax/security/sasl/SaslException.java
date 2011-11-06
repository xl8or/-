// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SaslException.java

package org.apache.harmony.javax.security.sasl;

import java.io.IOException;

public class SaslException extends IOException
{

    public SaslException()
    {
    }

    public SaslException(String s)
    {
        super(s);
    }

    public SaslException(String s, Throwable throwable)
    {
        super(s);
        if(throwable != null)
        {
            super.initCause(throwable);
            _exception = throwable;
        }
    }

    public Throwable getCause()
    {
        return _exception;
    }

    public Throwable initCause(Throwable throwable)
    {
        super.initCause(throwable);
        _exception = throwable;
        return this;
    }

    public String toString()
    {
        String s;
        if(_exception == null)
        {
            s = super.toString();
        } else
        {
            StringBuilder stringbuilder = new StringBuilder(super.toString());
            stringbuilder.append(", caused by: ");
            stringbuilder.append(_exception.toString());
            s = stringbuilder.toString();
        }
        return s;
    }

    private static final long serialVersionUID = 0x912ad08aL;
    private Throwable _exception;
}
