// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserPasswordCallbackHandler.java

package org.apache.qpid.management.common.sasl;

import java.io.IOException;
import org.apache.harmony.javax.security.auth.callback.*;

public class UserPasswordCallbackHandler
    implements CallbackHandler
{

    public UserPasswordCallbackHandler(String s, String s1)
    {
        user = s;
        pwchars = s1.toCharArray();
    }

    private void clearPassword()
    {
        if(pwchars != null)
        {
            for(int i = 0; i < pwchars.length; i++)
                pwchars[i] = '\0';

            pwchars = null;
        }
    }

    protected void finalize()
    {
        clearPassword();
    }

    public void handle(Callback acallback[])
        throws IOException, UnsupportedCallbackException
    {
        int i = 0;
        while(i < acallback.length) 
        {
            if(acallback[i] instanceof NameCallback)
                ((NameCallback)acallback[i]).setName(user);
            else
            if(acallback[i] instanceof PasswordCallback)
                ((PasswordCallback)acallback[i]).setPassword(pwchars);
            else
                throw new UnsupportedCallbackException(acallback[i]);
            i++;
        }
    }

    private char pwchars[];
    private String user;
}
