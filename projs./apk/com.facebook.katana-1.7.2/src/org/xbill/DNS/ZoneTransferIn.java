// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ZoneTransferIn.java

package org.xbill.DNS;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package org.xbill.DNS:
//            NameTooLongException, Name, TCPClient, ZoneTransferException, 
//            Message, Header, Rcode, Record, 
//            SOARecord, Options, WireParseException, TSIG, 
//            DClass

public class ZoneTransferIn
{
    public static class Delta
    {

        public List adds;
        public List deletes;
        public long end;
        public long start;

        private Delta()
        {
            adds = new ArrayList();
            deletes = new ArrayList();
        }

    }


    private ZoneTransferIn()
    {
        timeout = 0xdbba0L;
    }

    private ZoneTransferIn(Name name, int i, long l, boolean flag, SocketAddress socketaddress, TSIG tsig1)
    {
        timeout = 0xdbba0L;
        address = socketaddress;
        tsig = tsig1;
        if(name.isAbsolute())
            zname = name;
        else
            try
            {
                zname = Name.concatenate(name, Name.root);
            }
            catch(NameTooLongException nametoolongexception)
            {
                throw new IllegalArgumentException("ZoneTransferIn: name too long");
            }
        qtype = i;
        dclass = 1;
        ixfr_serial = l;
        want_fallback = flag;
        state = 0;
    }

    private void closeConnection()
    {
        if(client != null)
            client.cleanup();
_L2:
        return;
        IOException ioexception;
        ioexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void doxfr()
        throws IOException, ZoneTransferException
    {
        sendQuery();
_L10:
        if(state == 7) goto _L2; else goto _L1
_L1:
        Message message;
        Record arecord[];
        byte abyte0[] = client.recv();
        message = parseMessage(abyte0);
        if(message.getHeader().getRcode() == 0 && verifier != null)
        {
            message.getTSIG();
            if(verifier.verify(message, abyte0) != 0)
                fail("TSIG failure");
        }
        arecord = message.getSectionArray(1);
        if(state != 0) goto _L4; else goto _L3
_L3:
        int j = message.getRcode();
        if(j == 0) goto _L6; else goto _L5
_L5:
        if(qtype != 251 || j != 4) goto _L8; else goto _L7
_L7:
        fallback();
        doxfr();
_L2:
        return;
_L8:
        fail(Rcode.string(j));
_L6:
        Record record = message.getQuestion();
        if(record != null && record.getType() != qtype)
            fail("invalid question section");
        if(arecord.length != 0 || qtype != 251)
            break; /* Loop/switch isn't completed */
        fallback();
        doxfr();
        if(true) goto _L2; else goto _L4
_L4:
        for(int i = 0; i < arecord.length; i++)
            parseRR(arecord[i]);

        if(state == 7 && verifier != null && !message.isVerified())
            fail("last message must be signed");
        if(true) goto _L10; else goto _L9
_L9:
    }

    private void fail(String s)
        throws ZoneTransferException
    {
        throw new ZoneTransferException(s);
    }

    private void fallback()
        throws ZoneTransferException
    {
        if(!want_fallback)
            fail("server doesn't support IXFR");
        logxfr("falling back to AXFR");
        qtype = 252;
        state = 0;
    }

    private long getSOASerial(Record record)
    {
        return ((SOARecord)record).getSerial();
    }

    private void logxfr(String s)
    {
        if(Options.check("verbose"))
            System.out.println((new StringBuilder()).append(zname).append(": ").append(s).toString());
    }

    public static ZoneTransferIn newAXFR(Name name, String s, int i, TSIG tsig1)
        throws UnknownHostException
    {
        int j;
        if(i == 0)
            j = 53;
        else
            j = i;
        return newAXFR(name, ((SocketAddress) (new InetSocketAddress(s, j))), tsig1);
    }

    public static ZoneTransferIn newAXFR(Name name, String s, TSIG tsig1)
        throws UnknownHostException
    {
        return newAXFR(name, s, 0, tsig1);
    }

    public static ZoneTransferIn newAXFR(Name name, SocketAddress socketaddress, TSIG tsig1)
    {
        return new ZoneTransferIn(name, 252, 0L, false, socketaddress, tsig1);
    }

    public static ZoneTransferIn newIXFR(Name name, long l, boolean flag, String s, int i, TSIG tsig1)
        throws UnknownHostException
    {
        int j;
        if(i == 0)
            j = 53;
        else
            j = i;
        return newIXFR(name, l, flag, ((SocketAddress) (new InetSocketAddress(s, j))), tsig1);
    }

    public static ZoneTransferIn newIXFR(Name name, long l, boolean flag, String s, TSIG tsig1)
        throws UnknownHostException
    {
        return newIXFR(name, l, flag, s, 0, tsig1);
    }

    public static ZoneTransferIn newIXFR(Name name, long l, boolean flag, SocketAddress socketaddress, TSIG tsig1)
    {
        return new ZoneTransferIn(name, 251, l, flag, socketaddress, tsig1);
    }

    private void openConnection()
        throws IOException
    {
        client = new TCPClient(System.currentTimeMillis() + timeout);
        if(localAddress != null)
            client.bind(localAddress);
        client.connect(address);
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
            if(ioexception instanceof WireParseException)
                throw (WireParseException)ioexception;
            else
                throw new WireParseException("Error parsing message");
        }
        return message;
    }

    private void parseRR(Record record)
        throws ZoneTransferException
    {
        int i = record.getType();
        state;
        JVM INSTR tableswitch 0 7: default 56
    //                   0 64
    //                   1 137
    //                   2 252
    //                   3 304
    //                   4 368
    //                   5 423
    //                   6 552
    //                   7 594;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9
_L1:
        fail("invalid state");
_L11:
        return;
_L2:
        if(i != 6)
            fail("missing initial SOA");
        initialsoa = record;
        end_serial = getSOASerial(record);
        if(qtype == 251 && end_serial <= ixfr_serial)
        {
            logxfr("up to date");
            state = 7;
        } else
        {
            state = 1;
        }
        continue; /* Loop/switch isn't completed */
_L3:
        if(qtype == 251 && i == 6 && getSOASerial(record) == ixfr_serial)
        {
            rtype = 251;
            ixfr = new ArrayList();
            logxfr("got incremental response");
            state = 2;
        } else
        {
            rtype = 252;
            axfr = new ArrayList();
            axfr.add(initialsoa);
            logxfr("got nonincremental response");
            state = 6;
        }
        parseRR(record);
        continue; /* Loop/switch isn't completed */
_L4:
        Delta delta1 = new Delta();
        ixfr.add(delta1);
        delta1.start = getSOASerial(record);
        delta1.deletes.add(record);
        state = 3;
        continue; /* Loop/switch isn't completed */
_L5:
        if(i == 6)
        {
            current_serial = getSOASerial(record);
            state = 4;
            parseRR(record);
        } else
        {
            ((Delta)ixfr.get(ixfr.size() - 1)).deletes.add(record);
        }
        continue; /* Loop/switch isn't completed */
_L6:
        Delta delta = (Delta)ixfr.get(ixfr.size() - 1);
        delta.end = getSOASerial(record);
        delta.adds.add(record);
        state = 5;
        continue; /* Loop/switch isn't completed */
_L7:
label0:
        {
            if(i == 6)
            {
                long l = getSOASerial(record);
                if(l == end_serial)
                {
                    state = 7;
                    continue; /* Loop/switch isn't completed */
                }
                if(l == current_serial)
                    break label0;
                fail((new StringBuilder()).append("IXFR out of sync: expected serial ").append(current_serial).append(" , got ").append(l).toString());
            }
            ((Delta)ixfr.get(ixfr.size() - 1)).adds.add(record);
            continue; /* Loop/switch isn't completed */
        }
        state = 2;
        parseRR(record);
        continue; /* Loop/switch isn't completed */
_L8:
        if(i != 1 || record.getDClass() == dclass)
        {
            axfr.add(record);
            if(i == 6)
                state = 7;
        }
        continue; /* Loop/switch isn't completed */
_L9:
        fail("extra data");
        if(true) goto _L11; else goto _L10
_L10:
    }

    private void sendQuery()
        throws IOException
    {
        Record record = Record.newRecord(zname, qtype, dclass);
        Message message = new Message();
        message.getHeader().setOpcode(0);
        message.addRecord(record, 0);
        if(qtype == 251)
            message.addRecord(new SOARecord(zname, dclass, 0L, Name.root, Name.root, ixfr_serial, 0L, 0L, 0L, 0L), 2);
        if(tsig != null)
        {
            tsig.apply(message, null);
            verifier = new TSIG.StreamVerifier(tsig, message.getTSIG());
        }
        byte abyte0[] = message.toWire(65535);
        client.send(abyte0);
    }

    public List getAXFR()
    {
        return axfr;
    }

    public List getIXFR()
    {
        return ixfr;
    }

    public Name getName()
    {
        return zname;
    }

    public int getType()
    {
        return qtype;
    }

    public boolean isAXFR()
    {
        boolean flag;
        if(rtype == 252)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isCurrent()
    {
        boolean flag;
        if(axfr == null && ixfr == null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isIXFR()
    {
        boolean flag;
        if(rtype == 251)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public List run()
        throws IOException, ZoneTransferException
    {
        openConnection();
        doxfr();
        closeConnection();
        Exception exception;
        List list;
        if(axfr != null)
            list = axfr;
        else
            list = ixfr;
        return list;
        exception;
        closeConnection();
        throw exception;
    }

    public void setDClass(int i)
    {
        DClass.check(i);
        dclass = i;
    }

    public void setLocalAddress(SocketAddress socketaddress)
    {
        localAddress = socketaddress;
    }

    public void setTimeout(int i)
    {
        if(i < 0)
        {
            throw new IllegalArgumentException("timeout cannot be negative");
        } else
        {
            timeout = 1000L * (long)i;
            return;
        }
    }

    private static final int AXFR = 6;
    private static final int END = 7;
    private static final int FIRSTDATA = 1;
    private static final int INITIALSOA = 0;
    private static final int IXFR_ADD = 5;
    private static final int IXFR_ADDSOA = 4;
    private static final int IXFR_DEL = 3;
    private static final int IXFR_DELSOA = 2;
    private SocketAddress address;
    private List axfr;
    private TCPClient client;
    private long current_serial;
    private int dclass;
    private long end_serial;
    private Record initialsoa;
    private List ixfr;
    private long ixfr_serial;
    private SocketAddress localAddress;
    private int qtype;
    private int rtype;
    private int state;
    private long timeout;
    private TSIG tsig;
    private TSIG.StreamVerifier verifier;
    private boolean want_fallback;
    private Name zname;
}
