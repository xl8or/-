// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UnsupportedCallbackException.java

package org.apache.harmony.javax.security.auth.callback;


// Referenced classes of package org.apache.harmony.javax.security.auth.callback:
//            Callback

public class UnsupportedCallbackException extends Exception
{

    public UnsupportedCallbackException(Callback callback1)
    {
        callback = callback1;
    }

    public UnsupportedCallbackException(Callback callback1, String s)
    {
        super(s);
        callback = callback1;
    }

    public Callback getCallback()
    {
        return callback;
    }

    private static final long serialVersionUID = 0xa720b69L;
    private Callback callback;
}
