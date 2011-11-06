// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NameCallback.java

package org.apache.harmony.javax.security.auth.callback;

import java.io.Serializable;

// Referenced classes of package org.apache.harmony.javax.security.auth.callback:
//            Callback

public class NameCallback
    implements Callback, Serializable
{

    public NameCallback(String s)
    {
        setPrompt(s);
    }

    public NameCallback(String s, String s1)
    {
        setPrompt(s);
        setDefaultName(s1);
    }

    private void setDefaultName(String s)
    {
        if(s == null || s.length() == 0)
        {
            throw new IllegalArgumentException("auth.1E");
        } else
        {
            defaultName = s;
            return;
        }
    }

    private void setPrompt(String s)
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

    public String getDefaultName()
    {
        return defaultName;
    }

    public String getName()
    {
        return inputName;
    }

    public String getPrompt()
    {
        return prompt;
    }

    public void setName(String s)
    {
        inputName = s;
    }

    private static final long serialVersionUID = 0x7eb277dL;
    private String defaultName;
    private String inputName;
    private String prompt;
}
