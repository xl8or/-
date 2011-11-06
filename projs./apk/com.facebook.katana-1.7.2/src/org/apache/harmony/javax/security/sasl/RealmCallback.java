// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RealmCallback.java

package org.apache.harmony.javax.security.sasl;

import org.apache.harmony.javax.security.auth.callback.TextInputCallback;

public class RealmCallback extends TextInputCallback
{

    public RealmCallback(String s)
    {
        super(s);
    }

    public RealmCallback(String s, String s1)
    {
        super(s, s1);
    }

    private static final long serialVersionUID = 0x9f28cf4L;
}
