// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ResolveThread.java

package org.xbill.DNS;


// Referenced classes of package org.xbill.DNS:
//            Resolver, ResolverListener, Message

class ResolveThread extends Thread
{

    public ResolveThread(Resolver resolver, Message message, Object obj, ResolverListener resolverlistener)
    {
        res = resolver;
        query = message;
        id = obj;
        listener = resolverlistener;
    }

    public void run()
    {
        Message message = res.send(query);
        listener.receiveMessage(id, message);
_L1:
        return;
        Exception exception;
        exception;
        listener.handleException(id, exception);
          goto _L1
    }

    private Object id;
    private ResolverListener listener;
    private Message query;
    private Resolver res;
}
