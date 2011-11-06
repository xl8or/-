// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AuthenticationException.java

package org.apache.harmony.javax.security.sasl;


// Referenced classes of package org.apache.harmony.javax.security.sasl:
//            SaslException

public class AuthenticationException extends SaslException
{

    public AuthenticationException()
    {
    }

    public AuthenticationException(String s)
    {
        super(s);
    }

    public AuthenticationException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    private static final long serialVersionUID = 0x228acea1L;
}
