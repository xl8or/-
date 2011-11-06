// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FailedLoginException.java

package org.apache.harmony.javax.security.auth.login;


// Referenced classes of package org.apache.harmony.javax.security.auth.login:
//            LoginException

public class FailedLoginException extends LoginException
{

    public FailedLoginException()
    {
    }

    public FailedLoginException(String s)
    {
        super(s);
    }

    private static final long serialVersionUID = 0xccfa47deL;
}
