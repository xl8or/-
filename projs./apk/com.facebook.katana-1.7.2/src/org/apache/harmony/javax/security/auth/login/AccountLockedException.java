// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AccountLockedException.java

package org.apache.harmony.javax.security.auth.login;


// Referenced classes of package org.apache.harmony.javax.security.auth.login:
//            AccountException

public class AccountLockedException extends AccountException
{

    public AccountLockedException()
    {
    }

    public AccountLockedException(String s)
    {
        super(s);
    }

    private static final long serialVersionUID = 0x47e569eL;
}
