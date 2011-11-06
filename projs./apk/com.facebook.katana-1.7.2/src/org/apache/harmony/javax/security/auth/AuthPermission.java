// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AuthPermission.java

package org.apache.harmony.javax.security.auth;

import java.security.BasicPermission;

public final class AuthPermission extends BasicPermission
{

    public AuthPermission(String s)
    {
        super(init(s));
    }

    public AuthPermission(String s, String s1)
    {
        super(init(s), s1);
    }

    private static String init(String s)
    {
        if(s == null)
            throw new NullPointerException("auth.13");
        String s1;
        if("createLoginContext".equals(s))
            s1 = "createLoginContext.*";
        else
            s1 = s;
        return s1;
    }

    private static final String CREATE_LOGIN_CONTEXT = "createLoginContext";
    private static final String CREATE_LOGIN_CONTEXT_ANY = "createLoginContext.*";
    private static final long serialVersionUID = 0x6da4d0e6L;
}
