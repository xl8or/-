// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExtendedResolver.java

package org.xbill.DNS;

import java.io.*;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package org.xbill.DNS:
//            Resolver, ResolverConfig, SimpleResolver, Message, 
//            ResolverListener, TSIG, Options

public class ExtendedResolver
    implements Resolver
{
    private static class Resolution
        implements ResolverListener
    {

        public void handleException(Object obj, Exception exception)
        {
            if(Options.check("verbose"))
                System.err.println((new StringBuilder()).append("ExtendedResolver: got ").append(exception).toString());
            this;
            JVM INSTR monitorenter ;
            outstanding = outstanding - 1;
            if(!done) goto _L2; else goto _L1
_L21:
            int i;
            if(i < inprogress.length && inprogress[i] != obj) goto _L4; else goto _L3
_L3:
            if(i != inprogress.length) goto _L5; else goto _L1
            Exception exception1;
            exception1;
            throw exception1;
_L4:
            i++;
            continue; /* Loop/switch isn't completed */
_L5:
            if(sent[i] != 1 || i >= resolvers.length - 1) goto _L7; else goto _L6
_L6:
            boolean flag = true;
_L19:
            if(!(exception instanceof InterruptedIOException)) goto _L9; else goto _L8
_L8:
            if(sent[i] < retries)
                send(i);
            if(thrown == null)
                thrown = exception;
_L11:
            if(!done)
                break; /* Loop/switch isn't completed */
            this;
            JVM INSTR monitorexit ;
              goto _L1
_L9:
            if(exception instanceof SocketException)
            {
                if(thrown == null || (thrown instanceof InterruptedIOException))
                    thrown = exception;
            } else
            {
                thrown = exception;
            }
            if(true) goto _L11; else goto _L10
_L10:
            if(flag)
                send(i + 1);
            if(!done) goto _L13; else goto _L12
_L12:
            this;
            JVM INSTR monitorexit ;
              goto _L1
_L13:
            if(outstanding != 0) goto _L15; else goto _L14
_L14:
            done = true;
            if(listener != null) goto _L15; else goto _L16
_L16:
            notifyAll();
            this;
            JVM INSTR monitorexit ;
              goto _L1
_L15:
            if(done) goto _L18; else goto _L17
_L17:
            this;
            JVM INSTR monitorexit ;
              goto _L1
_L18:
            this;
            JVM INSTR monitorexit ;
            if(!(thrown instanceof Exception))
                thrown = new RuntimeException(thrown.getMessage());
            listener.handleException(this, (Exception)thrown);
              goto _L1
_L7:
            flag = false;
              goto _L19
_L1:
            return;
_L2:
            i = 0;
            if(true) goto _L21; else goto _L20
_L20:
        }

        public void receiveMessage(Object obj, Message message)
        {
            if(Options.check("verbose"))
                System.err.println("ExtendedResolver: received message");
            this;
            JVM INSTR monitorenter ;
            if(done)
                break MISSING_BLOCK_LABEL_77;
            response = message;
            done = true;
            if(listener == null)
            {
                notifyAll();
                break MISSING_BLOCK_LABEL_77;
            }
            break MISSING_BLOCK_LABEL_61;
            Exception exception;
            exception;
            throw exception;
            this;
            JVM INSTR monitorexit ;
            listener.receiveMessage(this, response);
        }

        public void send(int i)
        {
            int ai[] = sent;
            ai[i] = 1 + ai[i];
            outstanding = 1 + outstanding;
            inprogress[i] = resolvers[i].sendAsync(query, this);
_L1:
            return;
            Throwable throwable;
            throwable;
            this;
            JVM INSTR monitorenter ;
            thrown = throwable;
            done = true;
            if(listener != null)
                break MISSING_BLOCK_LABEL_82;
            notifyAll();
              goto _L1
            Exception exception;
            exception;
            throw exception;
            this;
            JVM INSTR monitorexit ;
              goto _L1
        }

        public Message start()
            throws IOException
        {
            Message message1;
            int ai[] = sent;
            ai[0] = 1 + ai[0];
            outstanding = 1 + outstanding;
            inprogress[0] = new Object();
            message1 = resolvers[0].send(query);
            Message message = message1;
_L4:
            return message;
            Exception exception;
            exception;
            handleException(inprogress[0], exception);
            this;
            JVM INSTR monitorenter ;
_L2:
            boolean flag = done;
            if(flag)
                break; /* Loop/switch isn't completed */
            try
            {
                wait();
            }
            catch(InterruptedException interruptedexception) { }
            if(true) goto _L2; else goto _L1
_L1:
            this;
            JVM INSTR monitorexit ;
            if(response == null)
                break; /* Loop/switch isn't completed */
            message = response;
            if(true) goto _L4; else goto _L3
            Exception exception1;
            exception1;
            this;
            JVM INSTR monitorexit ;
            throw exception1;
_L3:
            if(thrown instanceof IOException)
                throw (IOException)thrown;
            if(thrown instanceof RuntimeException)
                throw (RuntimeException)thrown;
            if(thrown instanceof Error)
                throw (Error)thrown;
            else
                throw new IllegalStateException("ExtendedResolver failure");
        }

        public void startAsync(ResolverListener resolverlistener)
        {
            listener = resolverlistener;
            send(0);
        }

        boolean done;
        Object inprogress[];
        ResolverListener listener;
        int outstanding;
        Message query;
        Resolver resolvers[];
        Message response;
        int retries;
        int sent[];
        Throwable thrown;

        public Resolution(ExtendedResolver extendedresolver, Message message)
        {
            List list = extendedresolver.resolvers;
            resolvers = (Resolver[])(Resolver[])list.toArray(new Resolver[list.size()]);
            if(extendedresolver.loadBalance)
            {
                int i = resolvers.length;
                int j = int i = 
// JavaClassFileOutputException: get_constant: invalid tag
    }


    public ExtendedResolver()
        throws UnknownHostException
    {
        boolean flag = false;
        Object();
        loadBalance = flag;
        lbStart = ((flag) ? 1 : 0);
        retries = 3;
        init();
        String as[] = ResolverConfig.getCurrentConfig().servers();
        if(as != null)
            for(; flag < as.length; flag++)
            {
                SimpleResolver simpleresolver = new SimpleResolver(as[flag]);
                simpleresolver.setTimeout(5);
                resolvers.add(simpleresolver);
            }

        else
            resolvers.add(new SimpleResolver());
    }

    public ExtendedResolver(String as[])
        throws UnknownHostException
    {
        loadBalance = false;
        lbStart = 0;
        retries = 3;
        init();
        for(int i = 0; i < as.length; i++)
        {
            SimpleResolver simpleresolver = new SimpleResolver(as[i]);
            simpleresolver.setTimeout(5);
            resolvers.add(simpleresolver);
        }

    }

    public ExtendedResolver(Resolver aresolver[])
        throws UnknownHostException
    {
        loadBalance = false;
        lbStart = 0;
        retries = 3;
        init();
        for(int i = 0; i < aresolver.length; i++)
            resolvers.add(aresolver[i]);

    }

    private void init()
    {
        resolvers = new ArrayList();
    }

    public void addResolver(Resolver resolver)
    {
        resolvers.add(resolver);
    }

    public void deleteResolver(Resolver resolver)
    {
        resolvers.remove(resolver);
    }

    public Resolver getResolver(int i)
    {
        Resolver resolver;
        if(i < resolvers.size())
            resolver = (Resolver)resolvers.get(i);
        else
            resolver = null;
        return resolver;
    }

    public Resolver[] getResolvers()
    {
        return (Resolver[])(Resolver[])resolvers.toArray(new Resolver[resolvers.size()]);
    }

    public Message send(Message message)
        throws IOException
    {
        return (new Resolution(this, message)).start();
    }

    public Object sendAsync(Message message, ResolverListener resolverlistener)
    {
        Resolution resolution = new Resolution(this, message);
        resolution.startAsync(resolverlistener);
        return resolution;
    }

    public void setEDNS(int i)
    {
        for(int j = 0; j < resolvers.size(); j++)
            ((Resolver)resolvers.get(j)).setEDNS(i);

    }

    public void setEDNS(int i, int j, int k, List list)
    {
        for(int l = 0; l < resolvers.size(); l++)
            ((Resolver)resolvers.get(l)).setEDNS(i, j, k, list);

    }

    public void setIgnoreTruncation(boolean flag)
    {
        for(int i = 0; i < resolvers.size(); i++)
            ((Resolver)resolvers.get(i)).setIgnoreTruncation(flag);

    }

    public void setLoadBalance(boolean flag)
    {
        loadBalance = flag;
    }

    public void setPort(int i)
    {
        for(int j = 0; j < resolvers.size(); j++)
            ((Resolver)resolvers.get(j)).setPort(i);

    }

    public void setRetries(int i)
    {
        retries = i;
    }

    public void setTCP(boolean flag)
    {
        for(int i = 0; i < resolvers.size(); i++)
            ((Resolver)resolvers.get(i)).setTCP(flag);

    }

    public void setTSIGKey(TSIG tsig)
    {
        for(int i = 0; i < resolvers.size(); i++)
            ((Resolver)resolvers.get(i)).setTSIGKey(tsig);

    }

    public void setTimeout(int i)
    {
        setTimeout(i, 0);
    }

    public void setTimeout(int i, int j)
    {
        for(int k = 0; k < resolvers.size(); k++)
            ((Resolver)resolvers.get(k)).setTimeout(i, j);

    }

    private static final int quantum = 5;
    private int lbStart;
    private boolean loadBalance;
    private List resolvers;
    private int retries;





/*
    static int access$208(ExtendedResolver extendedresolver)
    {
        int i = extendedresolver.lbStart;
        extendedresolver.lbStart = i + 1;
        return i;
    }

*/


/*
    static int access$244(ExtendedResolver extendedresolver, int i)
    {
        int j = extendedresolver.lbStart % i;
        extendedresolver.lbStart = j;
        return j;
    }

*/

}
