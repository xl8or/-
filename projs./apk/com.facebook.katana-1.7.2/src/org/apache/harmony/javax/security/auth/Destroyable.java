// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Destroyable.java

package org.apache.harmony.javax.security.auth;


// Referenced classes of package org.apache.harmony.javax.security.auth:
//            DestroyFailedException

public interface Destroyable
{

    public abstract void destroy()
        throws DestroyFailedException;

    public abstract boolean isDestroyed();
}
