// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CallbackHandler.java

package org.apache.harmony.javax.security.auth.callback;

import java.io.IOException;

// Referenced classes of package org.apache.harmony.javax.security.auth.callback:
//            UnsupportedCallbackException, Callback

public interface CallbackHandler
{

    public abstract void handle(Callback acallback[])
        throws IOException, UnsupportedCallbackException;
}
