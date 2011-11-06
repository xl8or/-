// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LoginModule.java

package org.apache.harmony.javax.security.auth.spi;

import java.util.Map;
import org.apache.harmony.javax.security.auth.Subject;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.auth.login.LoginException;

public interface LoginModule
{

    public abstract boolean abort()
        throws LoginException;

    public abstract boolean commit()
        throws LoginException;

    public abstract void initialize(Subject subject, CallbackHandler callbackhandler, Map map, Map map1);

    public abstract boolean login()
        throws LoginException;

    public abstract boolean logout()
        throws LoginException;
}
