// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PasswordCallback.java

package org.apache.harmony.javax.security.auth.callback;

import java.io.Serializable;
import java.util.Arrays;

// Referenced classes of package org.apache.harmony.javax.security.auth.callback:
//            Callback

public class PasswordCallback
    implements Callback, Serializable
{

    public PasswordCallback(String s, boolean flag)
    {
        setPrompt(s);
        echoOn = flag;
    }

    private void setPrompt(String s)
        throws IllegalArgumentException
    {
        if(s == null || s.length() == 0)
        {
            throw new IllegalArgumentException("auth.14");
        } else
        {
            prompt = s;
            return;
        }
    }

    public void clearPassword()
    {
        if(inputPassword != null)
            Arrays.fill(inputPassword, '\0');
    }

    public char[] getPassword()
    {
        char ac[];
        if(inputPassword != null)
        {
            ac = new char[inputPassword.length];
            System.arraycopy(inputPassword, 0, ac, 0, ac.length);
        } else
        {
            ac = null;
        }
        return ac;
    }

    public String getPrompt()
    {
        return prompt;
    }

    public boolean isEchoOn()
    {
        return echoOn;
    }

    public void setPassword(char ac[])
    {
        if(ac == null)
        {
            inputPassword = ac;
        } else
        {
            inputPassword = new char[ac.length];
            System.arraycopy(ac, 0, inputPassword, 0, inputPassword.length);
        }
    }

    private static final long serialVersionUID = 0x7139c5e6L;
    boolean echoOn;
    private char inputPassword[];
    private String prompt;
}
