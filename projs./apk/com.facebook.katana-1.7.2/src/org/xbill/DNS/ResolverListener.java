// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ResolverListener.java

package org.xbill.DNS;

import java.util.EventListener;

// Referenced classes of package org.xbill.DNS:
//            Message

public interface ResolverListener
    extends EventListener
{

    public abstract void handleException(Object obj, Exception exception);

    public abstract void receiveMessage(Object obj, Message message);
}
