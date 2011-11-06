// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TextInputCallback.java

package org.apache.harmony.javax.security.auth.callback;

import java.io.Serializable;

// Referenced classes of package org.apache.harmony.javax.security.auth.callback:
//            Callback

public class TextInputCallback
    implements Callback, Serializable
{

    public TextInputCallback(String s)
    {
        setPrompt(s);
    }

    public TextInputCallback(String s, String s1)
    {
        setPrompt(s);
        setDefaultText(s1);
    }

    private void setDefaultText(String s)
    {
        if(s == null || s.length() == 0)
        {
            throw new IllegalArgumentException("auth.15");
        } else
        {
            defaultText = s;
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

    public String getDefaultText()
    {
        return defaultText;
    }

    public String getPrompt()
    {
        return prompt;
    }

    public String getText()
    {
        return inputText;
    }

    public void setText(String s)
    {
        inputText = s;
    }

    private static final long serialVersionUID = 0xae4213e4L;
    private String defaultText;
    private String inputText;
    private String prompt;
}
