// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BOSHClientConnEvent.java

package com.kenai.jbosh;

import java.util.*;

// Referenced classes of package com.kenai.jbosh:
//            BOSHClient

public final class BOSHClientConnEvent extends EventObject
{

    private BOSHClientConnEvent(BOSHClient boshclient, boolean flag, List list, Throwable throwable)
    {
        super(boshclient);
        connected = flag;
        cause = throwable;
        if(connected)
        {
            if(throwable != null)
                throw new IllegalStateException("Cannot be connected and have a cause");
            if(list != null && list.size() > 0)
                throw new IllegalStateException("Cannot be connected and have outstanding requests");
        }
        if(list == null)
            requests = Collections.emptyList();
        else
            requests = Collections.unmodifiableList(new ArrayList(list));
    }

    static BOSHClientConnEvent createConnectionClosedEvent(BOSHClient boshclient)
    {
        return new BOSHClientConnEvent(boshclient, false, null, null);
    }

    static BOSHClientConnEvent createConnectionClosedOnErrorEvent(BOSHClient boshclient, List list, Throwable throwable)
    {
        return new BOSHClientConnEvent(boshclient, false, list, throwable);
    }

    static BOSHClientConnEvent createConnectionEstablishedEvent(BOSHClient boshclient)
    {
        return new BOSHClientConnEvent(boshclient, true, null, null);
    }

    public BOSHClient getBOSHClient()
    {
        return (BOSHClient)getSource();
    }

    public Throwable getCause()
    {
        return cause;
    }

    public List getOutstandingRequests()
    {
        return requests;
    }

    public boolean isConnected()
    {
        return connected;
    }

    public boolean isError()
    {
        boolean flag;
        if(cause != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private static final long serialVersionUID = 1L;
    private final Throwable cause;
    private final boolean connected;
    private final List requests;
}
