// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Refreshable.java

package org.apache.harmony.javax.security.auth;


// Referenced classes of package org.apache.harmony.javax.security.auth:
//            RefreshFailedException

public interface Refreshable
{

    public abstract boolean isCurrent();

    public abstract void refresh()
        throws RefreshFailedException;
}
