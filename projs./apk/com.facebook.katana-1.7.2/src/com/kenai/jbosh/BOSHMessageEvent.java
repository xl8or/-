// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BOSHMessageEvent.java

package com.kenai.jbosh;

import java.util.EventObject;

// Referenced classes of package com.kenai.jbosh:
//            AbstractBody, BOSHClient

public final class BOSHMessageEvent extends EventObject
{

    private BOSHMessageEvent(Object obj, AbstractBody abstractbody)
    {
        super(obj);
        if(abstractbody == null)
        {
            throw new IllegalArgumentException("message body may not be null");
        } else
        {
            body = abstractbody;
            return;
        }
    }

    static BOSHMessageEvent createRequestSentEvent(BOSHClient boshclient, AbstractBody abstractbody)
    {
        return new BOSHMessageEvent(boshclient, abstractbody);
    }

    static BOSHMessageEvent createResponseReceivedEvent(BOSHClient boshclient, AbstractBody abstractbody)
    {
        return new BOSHMessageEvent(boshclient, abstractbody);
    }

    public AbstractBody getBody()
    {
        return body;
    }

    private static final long serialVersionUID = 1L;
    private final AbstractBody body;
}
