// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LanguageCallback.java

package org.apache.harmony.javax.security.auth.callback;

import java.io.Serializable;
import java.util.Locale;

// Referenced classes of package org.apache.harmony.javax.security.auth.callback:
//            Callback

public class LanguageCallback
    implements Callback, Serializable
{

    public LanguageCallback()
    {
    }

    public Locale getLocale()
    {
        return locale;
    }

    public void setLocale(Locale locale1)
    {
        locale = locale1;
    }

    private static final long serialVersionUID = 0xff1bc5adL;
    private Locale locale;
}
