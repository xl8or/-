// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Client.java

package org.xbill.DNS;

import java.io.IOException;
import java.io.PrintStream;
import java.net.SocketTimeoutException;
import java.nio.channels.*;
import org.xbill.DNS.utils.hexdump;

// Referenced classes of package org.xbill.DNS:
//            Options

class Client
{

    protected Client(SelectableChannel selectablechannel, long l)
        throws IOException
    {
        endTime = l;
        Selector selector1 = Selector.open();
        selectablechannel.configureBlocking(false);
        key = selectablechannel.register(selector1, 1);
        if(false && selector1 != null)
            selector1.close();
        if(false)
            selectablechannel.close();
        return;
        Exception exception;
        exception;
        Selector selector;
        Exception exception1;
        selector = null;
        exception1 = exception;
_L2:
        if(true && selector != null)
            selector.close();
        if(true)
            selectablechannel.close();
        throw exception1;
        Exception exception2;
        exception2;
        selector = selector1;
        exception1 = exception2;
        if(true) goto _L2; else goto _L1
_L1:
    }

    protected static void blockUntil(SelectionKey selectionkey, long l)
        throws IOException
    {
        long l1 = l - System.currentTimeMillis();
        int i;
        if(l1 > 0L)
            i = selectionkey.selector().select(l1);
        else
        if(l1 == 0L)
            i = selectionkey.selector().selectNow();
        else
            i = 0;
        if(i == 0)
            throw new SocketTimeoutException();
        else
            return;
    }

    protected static void verboseLog(String s, byte abyte0[])
    {
        if(Options.check("verbosemsg"))
            System.err.println(hexdump.dump(s, abyte0));
    }

    void cleanup()
        throws IOException
    {
        key.selector().close();
        key.channel().close();
    }

    protected long endTime;
    protected SelectionKey key;
}
