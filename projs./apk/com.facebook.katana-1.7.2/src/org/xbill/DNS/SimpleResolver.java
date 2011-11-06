// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SimpleResolver.java

package org.xbill.DNS;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.util.Iterator;
import java.util.List;

// Referenced classes of package org.xbill.DNS:
//            Resolver, ResolverConfig, Message, OPTRecord, 
//            WireParseException, Options, ZoneTransferException, Record, 
//            ZoneTransferIn, Header, TSIG, Rcode, 
//            TCPClient, UDPClient, Name, ResolveThread, 
//            ResolverListener

public class SimpleResolver
    implements Resolver
{

    public SimpleResolver()
        throws UnknownHostException
    {
        SimpleResolver(null);
    }

    public SimpleResolver(String s)
        throws UnknownHostException
    {
        timeoutValue = 10000L;
        String s1;
        InetAddress inetaddress;
        if(s == null)
        {
            s1 = ResolverConfig.getCurrentConfig().server();
            if(s1 == null)
                s1 = defaultResolver;
        } else
        {
            s1 = s;
        }
        if(s1.equals("0"))
            inetaddress = InetAddress.getLocalHost();
        else
            inetaddress = InetAddress.getByName(s1);
        address = new InetSocketAddress(inetaddress, 53);
    }

    private void applyEDNS(Message message)
    {
        if(queryOPT != null && message.getOPT() == null)
            message.addRecord(queryOPT, 3);
    }

    private int maxUDPSize(Message message)
    {
        OPTRecord optrecord = message.getOPT();
        int i;
        if(optrecord == null)
            i = 512;
        else
            i = optrecord.getPayloadSize();
        return i;
    }

    private Message parseMessage(byte abyte0[])
        throws WireParseException
    {
        Message message;
        try
        {
            message = new Message(abyte0);
        }
        catch(IOException ioexception)
        {
            if(Options.check("verbose"))
                ioexception.printStackTrace();
            Object obj;
            if(!(ioexception instanceof WireParseException))
                obj = new WireParseException("Error parsing message");
            else
                obj = ioexception;
            throw (WireParseException)obj;
        }
        return message;
    }

    private Message sendAXFR(Message message)
        throws IOException
    {
        ZoneTransferIn zonetransferin = ZoneTransferIn.newAXFR(message.getQuestion().getName(), address, tsig);
        zonetransferin.setTimeout((int)(getTimeout() / 1000L));
        zonetransferin.setLocalAddress(localAddress);
        List list;
        Message message1;
        try
        {
            zonetransferin.run();
        }
        catch(ZoneTransferException zonetransferexception)
        {
            throw new WireParseException(zonetransferexception.getMessage());
        }
        list = zonetransferin.getAXFR();
        message1 = new Message(message.getHeader().getID());
        message1.getHeader().setFlag(5);
        message1.getHeader().setFlag(0);
        message1.addRecord(message.getQuestion(), 0);
        for(Iterator iterator = list.iterator(); iterator.hasNext(); message1.addRecord((Record)iterator.next(), 1));
        return message1;
    }

    public static void setDefaultResolver(String s)
    {
        defaultResolver = s;
    }

    private void verifyTSIG(Message message, Message message1, byte abyte0[], TSIG tsig1)
    {
        if(tsig1 != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        int i = tsig1.verify(message1, abyte0, message.getTSIG());
        if(Options.check("verbose"))
            System.err.println((new StringBuilder()).append("TSIG verify: ").append(Rcode.string(i)).toString());
        if(true) goto _L1; else goto _L3
_L3:
    }

    InetSocketAddress getAddress()
    {
        return address;
    }

    TSIG getTSIGKey()
    {
        return tsig;
    }

    long getTimeout()
    {
        return timeoutValue;
    }

    public Message send(Message message)
        throws IOException
    {
        if(Options.check("verbose"))
            System.err.println((new StringBuilder()).append("Sending to ").append(address.getAddress().getHostAddress()).append(":").append(address.getPort()).toString());
        if(message.getHeader().getOpcode() != 0) goto _L2; else goto _L1
_L1:
        Record record = message.getQuestion();
        if(record == null || record.getType() != 252) goto _L2; else goto _L3
_L3:
        Message message3 = sendAXFR(message);
_L5:
        return message3;
_L2:
        Message message1 = (Message)message.clone();
        applyEDNS(message1);
        if(tsig != null)
            tsig.apply(message1, null);
        byte abyte0[] = message1.toWire(65535);
        int i = maxUDPSize(message1);
        long l = System.currentTimeMillis() + timeoutValue;
        boolean flag = false;
        do
        {
            boolean flag1;
            byte abyte1[];
            int j;
            int k;
            if(useTCP || abyte0.length > i)
                flag1 = true;
            else
                flag1 = flag;
            if(flag1)
                abyte1 = TCPClient.sendrecv(localAddress, address, abyte0, l);
            else
                abyte1 = UDPClient.sendrecv(localAddress, address, abyte0, i, l);
            if(abyte1.length < 12)
                throw new WireParseException("invalid DNS header - too short");
            j = ((0xff & abyte1[0]) << 8) + (0xff & abyte1[1]);
            k = message1.getHeader().getID();
            if(j != k)
            {
                String s = (new StringBuilder()).append("invalid message id: expected ").append(k).append("; got id ").append(j).toString();
                if(flag1)
                    throw new WireParseException(s);
                Message message2;
                if(Options.check("verbose"))
                {
                    System.err.println(s);
                    flag = flag1;
                } else
                {
                    flag = flag1;
                }
            } else
            {
label0:
                {
                    message2 = parseMessage(abyte1);
                    verifyTSIG(message1, message2, abyte1, tsig);
                    if(flag1 || ignoreTruncation || !message2.getHeader().getFlag(6))
                        break label0;
                    flag = true;
                }
            }
        } while(true);
        message3 = message2;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public Object sendAsync(Message message, ResolverListener resolverlistener)
    {
        this;
        JVM INSTR monitorenter ;
        Integer integer;
        int i = uniqueID;
        uniqueID = i + 1;
        integer = new Integer(i);
        this;
        JVM INSTR monitorexit ;
        Record record = message.getQuestion();
        Exception exception;
        String s;
        String s1;
        ResolveThread resolvethread;
        if(record != null)
            s = record.getName().toString();
        else
            s = "(none)";
        s1 = (new StringBuilder()).append(getClass()).append(": ").append(s).toString();
        resolvethread = new ResolveThread(this, message, integer, resolverlistener);
        resolvethread.setName(s1);
        resolvethread.setDaemon(true);
        resolvethread.start();
        return integer;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void setAddress(InetAddress inetaddress)
    {
        address = new InetSocketAddress(inetaddress, address.getPort());
    }

    public void setAddress(InetSocketAddress inetsocketaddress)
    {
        address = inetsocketaddress;
    }

    public void setEDNS(int i)
    {
        setEDNS(i, 0, 0, null);
    }

    public void setEDNS(int i, int j, int k, List list)
    {
        if(i != 0 && i != -1)
            throw new IllegalArgumentException("invalid EDNS level - must be 0 or -1");
        int l;
        if(j == 0)
            l = 1280;
        else
            l = j;
        queryOPT = new OPTRecord(l, 0, i, k, list);
    }

    public void setIgnoreTruncation(boolean flag)
    {
        ignoreTruncation = flag;
    }

    public void setLocalAddress(InetAddress inetaddress)
    {
        localAddress = new InetSocketAddress(inetaddress, 0);
    }

    public void setLocalAddress(InetSocketAddress inetsocketaddress)
    {
        localAddress = inetsocketaddress;
    }

    public void setPort(int i)
    {
        address = new InetSocketAddress(address.getAddress(), i);
    }

    public void setTCP(boolean flag)
    {
        useTCP = flag;
    }

    public void setTSIGKey(TSIG tsig1)
    {
        tsig = tsig1;
    }

    public void setTimeout(int i)
    {
        setTimeout(i, 0);
    }

    public void setTimeout(int i, int j)
    {
        timeoutValue = 1000L * (long)i + (long)j;
    }

    public static final int DEFAULT_EDNS_PAYLOADSIZE = 1280;
    public static final int DEFAULT_PORT = 53;
    private static final short DEFAULT_UDPSIZE = 512;
    private static String defaultResolver = "localhost";
    private static int uniqueID = 0;
    private InetSocketAddress address;
    private boolean ignoreTruncation;
    private InetSocketAddress localAddress;
    private OPTRecord queryOPT;
    private long timeoutValue;
    private TSIG tsig;
    private boolean useTCP;

}
