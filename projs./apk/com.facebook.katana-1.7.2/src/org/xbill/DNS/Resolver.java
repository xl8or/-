// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Resolver.java

package org.xbill.DNS;

import java.io.IOException;
import java.util.List;

// Referenced classes of package org.xbill.DNS:
//            Message, ResolverListener, TSIG

public interface Resolver
{

    public abstract Message send(Message message)
        throws IOException;

    public abstract Object sendAsync(Message message, ResolverListener resolverlistener);

    public abstract void setEDNS(int i);

    public abstract void setEDNS(int i, int j, int k, List list);

    public abstract void setIgnoreTruncation(boolean flag);

    public abstract void setPort(int i);

    public abstract void setTCP(boolean flag);

    public abstract void setTSIGKey(TSIG tsig);

    public abstract void setTimeout(int i);

    public abstract void setTimeout(int i, int j);
}
