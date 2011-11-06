// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TextOutputCallback.java

package org.apache.harmony.javax.security.auth.callback;

import java.io.Serializable;

// Referenced classes of package org.apache.harmony.javax.security.auth.callback:
//            Callback

public class TextOutputCallback
    implements Callback, Serializable
{

    public TextOutputCallback(int i, String s)
    {
        if(i > 2 || i < 0)
            throw new IllegalArgumentException("auth.16");
        if(s == null || s.length() == 0)
        {
            throw new IllegalArgumentException("auth.1F");
        } else
        {
            messageType = i;
            message = s;
            return;
        }
    }

    public String getMessage()
    {
        return message;
    }

    public int getMessageType()
    {
        return messageType;
    }

    public static final int ERROR = 2;
    public static final int INFORMATION = 0;
    public static final int WARNING = 1;
    private static final long serialVersionUID = 0x7c1bd1feL;
    private String message;
    private int messageType;
}
