// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LoginException.java

package org.apache.harmony.javax.security.auth.login;

import java.security.GeneralSecurityException;

public class LoginException extends GeneralSecurityException
{

    public LoginException()
    {
    }

    public LoginException(String s)
    {
        super(s);
    }

    private static final long serialVersionUID = 0x75dfd518L;
}
