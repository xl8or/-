// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Header.java

package org.xbill.DNS;

import java.io.IOException;
import java.util.Random;

// Referenced classes of package org.xbill.DNS:
//            DNSInput, Flags, Opcode, Rcode, 
//            Section, DNSOutput

public class Header
    implements Cloneable
{

    public Header()
    {
        init();
    }

    public Header(int i)
    {
        init();
        setID(i);
    }

    Header(DNSInput dnsinput)
        throws IOException
    {
        Header(dnsinput.readU16());
        flags = dnsinput.readU16();
        for(int i = 0; i < counts.length; i++)
            counts[i] = dnsinput.readU16();

    }

    public Header(byte abyte0[])
        throws IOException
    {
        Header(new DNSInput(abyte0));
    }

    private static void checkFlag(int i)
    {
        if(!validFlag(i))
            throw new IllegalArgumentException((new StringBuilder()).append("invalid flag bit ").append(i).toString());
        else
            return;
    }

    private void init()
    {
        counts = new int[4];
        flags = 0;
        id = -1;
    }

    private static boolean validFlag(int i)
    {
        boolean flag;
        if(i >= 0 && i <= 15 && Flags.isFlag(i))
            flag = true;
        else
            flag = false;
        return flag;
    }

    public Object clone()
    {
        Header header = new Header();
        header.id = id;
        header.flags = flags;
        System.arraycopy(counts, 0, header.counts, 0, counts.length);
        return header;
    }

    void decCount(int i)
    {
        if(counts[i] == 0)
        {
            throw new IllegalStateException("DNS section count cannot be decremented");
        } else
        {
            int ai[] = counts;
            ai[i] = ai[i] - 1;
            return;
        }
    }

    public int getCount(int i)
    {
        return counts[i];
    }

    public boolean getFlag(int i)
    {
        checkFlag(i);
        boolean flag;
        if((flags & 1 << 15 - i) != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    boolean[] getFlags()
    {
        boolean aflag[] = new boolean[16];
        for(int i = 0; i < aflag.length; i++)
            if(validFlag(i))
                aflag[i] = getFlag(i);

        return aflag;
    }

    public int getID()
    {
        if(id < 0) goto _L2; else goto _L1
_L1:
        int i = id;
_L4:
        return i;
_L2:
        this;
        JVM INSTR monitorenter ;
        if(id < 0)
            id = random.nextInt(65535);
        i = id;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public int getOpcode()
    {
        return 0xf & flags >> 11;
    }

    public int getRcode()
    {
        return 0xf & flags;
    }

    void incCount(int i)
    {
        if(counts[i] == 65535)
        {
            throw new IllegalStateException("DNS section count cannot be incremented");
        } else
        {
            int ai[] = counts;
            ai[i] = 1 + ai[i];
            return;
        }
    }

    public String printFlags()
    {
        StringBuffer stringbuffer = new StringBuffer();
        for(int i = 0; i < 16; i++)
            if(validFlag(i) && getFlag(i))
            {
                stringbuffer.append(Flags.string(i));
                stringbuffer.append(" ");
            }

        return stringbuffer.toString();
    }

    void setCount(int i, int j)
    {
        if(j < 0 || j > 65535)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("DNS section count ").append(j).append(" is out of range").toString());
        } else
        {
            counts[i] = j;
            return;
        }
    }

    public void setFlag(int i)
    {
        checkFlag(i);
        flags = flags | 1 << 15 - i;
    }

    public void setID(int i)
    {
        if(i < 0 || i > 65535)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("DNS message ID ").append(i).append(" is out of range").toString());
        } else
        {
            id = i;
            return;
        }
    }

    public void setOpcode(int i)
    {
        if(i < 0 || i > 15)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("DNS Opcode ").append(i).append("is out of range").toString());
        } else
        {
            flags = 0x87ff & flags;
            flags = flags | i << 11;
            return;
        }
    }

    public void setRcode(int i)
    {
        if(i < 0 || i > 15)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("DNS Rcode ").append(i).append(" is out of range").toString());
        } else
        {
            flags = 0xfffffff0 & flags;
            flags = i | flags;
            return;
        }
    }

    public String toString()
    {
        return toStringWithRcode(getRcode());
    }

    String toStringWithRcode(int i)
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(";; ->>HEADER<<- ");
        stringbuffer.append((new StringBuilder()).append("opcode: ").append(Opcode.string(getOpcode())).toString());
        stringbuffer.append((new StringBuilder()).append(", status: ").append(Rcode.string(i)).toString());
        stringbuffer.append((new StringBuilder()).append(", id: ").append(getID()).toString());
        stringbuffer.append("\n");
        stringbuffer.append((new StringBuilder()).append(";; flags: ").append(printFlags()).toString());
        stringbuffer.append("; ");
        for(int j = 0; j < 4; j++)
            stringbuffer.append((new StringBuilder()).append(Section.string(j)).append(": ").append(getCount(j)).append(" ").toString());

        return stringbuffer.toString();
    }

    void toWire(DNSOutput dnsoutput)
    {
        dnsoutput.writeU16(getID());
        dnsoutput.writeU16(flags);
        for(int i = 0; i < counts.length; i++)
            dnsoutput.writeU16(counts[i]);

    }

    public byte[] toWire()
    {
        DNSOutput dnsoutput = new DNSOutput();
        toWire(dnsoutput);
        return dnsoutput.toByteArray();
    }

    public void unsetFlag(int i)
    {
        checkFlag(i);
        flags = flags & (-1 ^ 1 << 15 - i);
    }

    public static final int LENGTH = 12;
    private static Random random = new Random();
    private int counts[];
    private int flags;
    private int id;

}
