// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AuthorizeCallback.java

package org.apache.harmony.javax.security.sasl;

import java.io.Serializable;
import org.apache.harmony.javax.security.auth.callback.Callback;

public class AuthorizeCallback
    implements Callback, Serializable
{

    public AuthorizeCallback(String s, String s1)
    {
        authenticationID = s;
        authorizationID = s1;
        authorizedID = s1;
    }

    public String getAuthenticationID()
    {
        return authenticationID;
    }

    public String getAuthorizationID()
    {
        return authorizationID;
    }

    public String getAuthorizedID()
    {
        String s;
        if(authorized)
            s = authorizedID;
        else
            s = null;
        return s;
    }

    public boolean isAuthorized()
    {
        return authorized;
    }

    public void setAuthorized(boolean flag)
    {
        authorized = flag;
    }

    public void setAuthorizedID(String s)
    {
        if(s != null)
            authorizedID = s;
    }

    private static final long serialVersionUID = 0x52013e6bL;
    private final String authenticationID;
    private final String authorizationID;
    private boolean authorized;
    private String authorizedID;
}
