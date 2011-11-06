// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Message.java

package org.xbill.DNS;

import java.io.IOException;
import java.util.*;

// Referenced classes of package org.xbill.DNS:
//            Record, RRset, Header, WireParseException, 
//            DNSInput, SIGRecord, Update, Name, 
//            DNSOutput, TSIG, Compression, TSIGRecord, 
//            OPTRecord, Type, DClass, Section

public class Message
    implements Cloneable
{

    public Message()
    {
        Message(new Header());
    }

    public Message(int i)
    {
        Message(new Header(i));
    }

    Message(DNSInput dnsinput)
        throws IOException
    {
        boolean flag1;
        int i;
        int k;
        Message(new Header(dnsinput));
        boolean flag;
        int j;
        int l;
        Record record;
        if(header.getOpcode() == 5)
            flag = true;
        else
            flag = false;
        flag1 = header.getFlag(6);
        i = 0;
_L6:
        if(i >= 4) goto _L2; else goto _L1
_L1:
        j = header.getCount(i);
        if(j > 0)
            sections[i] = new ArrayList(j);
        break MISSING_BLOCK_LABEL_195;
_L5:
        if(k >= j) goto _L4; else goto _L3
_L3:
        l = dnsinput.current();
        record = Record.fromWire(dnsinput, i, flag);
        sections[i].add(record);
        if(record.getType() == 250)
            tsigstart = l;
        if(record.getType() == 24 && ((SIGRecord)record).getTypeCovered() == 0)
            sig0start = l;
        k++;
          goto _L5
_L4:
        i++;
          goto _L6
        WireParseException wireparseexception;
        wireparseexception;
        if(!flag1)
            throw wireparseexception;
_L2:
        size = dnsinput.current();
        return;
        k = 0;
          goto _L5
    }

    private Message(Header header1)
    {
        sections = new List[4];
        header = header1;
    }

    public Message(byte abyte0[])
        throws IOException
    {
        Message(new DNSInput(abyte0));
    }

    public static Message newQuery(Record record)
    {
        Message message = new Message();
        message.header.setOpcode(0);
        message.header.setFlag(7);
        message.addRecord(record, 0);
        return message;
    }

    public static Message newUpdate(Name name)
    {
        return new Update(name);
    }

    private static boolean sameSet(Record record, Record record1)
    {
        boolean flag;
        if(record.getRRsetType() == record1.getRRsetType() && record.getDClass() == record1.getDClass() && record.getName().equals(record1.getName()))
            flag = true;
        else
            flag = false;
        return flag;
    }

    private int sectionToWire(DNSOutput dnsoutput, int i, Compression compression, int j)
    {
        int k;
        Record record;
        int i1;
        int j1;
        int k1;
        k = sections[i].size();
        int l = dnsoutput.current();
        record = null;
        i1 = 0;
        j1 = l;
        k1 = 0;
_L3:
        if(k1 >= k)
            break MISSING_BLOCK_LABEL_133;
        Record record1 = (Record)sections[i].get(k1);
        int l1;
        int i2;
        int j2;
        if(record != null && !sameSet(record1, record))
        {
            j2 = dnsoutput.current();
            i2 = k1;
        } else
        {
            i2 = i1;
            j2 = j1;
        }
        record1.toWire(dnsoutput, i, compression);
        if(dnsoutput.current() <= j) goto _L2; else goto _L1
_L1:
        dnsoutput.jump(j2);
        l1 = k - i2;
_L4:
        return l1;
_L2:
        k1++;
        j1 = j2;
        i1 = i2;
        record = record1;
          goto _L3
        l1 = 0;
          goto _L4
    }

    private boolean toWire(DNSOutput dnsoutput, int i)
    {
        if(i >= 12) goto _L2; else goto _L1
_L1:
        boolean flag = false;
_L8:
        return flag;
_L2:
        Header header1 = null;
        int j;
        int k;
        Compression compression;
        int l;
        TSIGRecord tsigrecord;
        int i1;
        int j1;
        if(tsigkey != null)
            j = i - tsigkey.recordLength();
        else
            j = i;
        k = dnsoutput.current();
        header.toWire(dnsoutput);
        compression = new Compression();
        l = 0;
        if(l >= 4)
            break MISSING_BLOCK_LABEL_190;
        if(sections[l] != null)
            break; /* Loop/switch isn't completed */
_L6:
        l++;
        if(true) goto _L4; else goto _L3
_L4:
        break MISSING_BLOCK_LABEL_58;
_L3:
        if((i1 = sectionToWire(dnsoutput, l, compression, j)) == 0) goto _L6; else goto _L5
_L5:
        if(l != 3)
        {
            if(header1 == null)
                header1 = (Header)header.clone();
            header1.setFlag(6);
            header1.setCount(l, header1.getCount(l) - i1);
            for(j1 = l + 1; j1 < 4; j1++)
                header1.setCount(j1, 0);

            dnsoutput.save();
            dnsoutput.jump(k);
            header1.toWire(dnsoutput);
            dnsoutput.restore();
        }
        if(tsigkey != null)
        {
            tsigrecord = tsigkey.generate(this, dnsoutput.toByteArray(), tsigerror, querytsig);
            if(header1 == null)
                header1 = (Header)header.clone();
            tsigrecord.toWire(dnsoutput, 3, compression);
            header1.incCount(3);
            dnsoutput.save();
            dnsoutput.jump(k);
            header1.toWire(dnsoutput);
            dnsoutput.restore();
        }
        flag = true;
        if(true) goto _L8; else goto _L7
_L7:
    }

    public void addRecord(Record record, int i)
    {
        if(sections[i] == null)
            sections[i] = new LinkedList();
        header.incCount(i);
        sections[i].add(record);
    }

    public Object clone()
    {
        Message message = new Message();
        for(int i = 0; i < sections.length; i++)
            if(sections[i] != null)
                message.sections[i] = new LinkedList(sections[i]);

        message.header = (Header)header.clone();
        message.size = size;
        return message;
    }

    public boolean findRRset(Name name, int i)
    {
        boolean flag;
        if(findRRset(name, i, 1) || findRRset(name, i, 2) || findRRset(name, i, 3))
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean findRRset(Name name, int i, int j)
    {
        if(sections[j] != null) goto _L2; else goto _L1
_L1:
        boolean flag = false;
_L4:
        return flag;
_L2:
        int k = 0;
        do
        {
            if(k >= sections[j].size())
                break;
            Record record = (Record)sections[j].get(k);
            if(record.getType() == i && name.equals(record.getName()))
            {
                flag = true;
                continue; /* Loop/switch isn't completed */
            }
            k++;
        } while(true);
        flag = false;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public boolean findRecord(Record record)
    {
        int i = 1;
_L3:
        if(i > 3)
            break MISSING_BLOCK_LABEL_41;
        if(sections[i] == null || !sections[i].contains(record)) goto _L2; else goto _L1
_L1:
        boolean flag = true;
_L4:
        return flag;
_L2:
        i++;
          goto _L3
        flag = false;
          goto _L4
    }

    public boolean findRecord(Record record, int i)
    {
        boolean flag;
        if(sections[i] != null && sections[i].contains(record))
            flag = true;
        else
            flag = false;
        return flag;
    }

    public Header getHeader()
    {
        return header;
    }

    public OPTRecord getOPT()
    {
        Record arecord[];
        int i;
        arecord = getSectionArray(3);
        i = 0;
_L3:
        if(i >= arecord.length)
            break MISSING_BLOCK_LABEL_38;
        if(!(arecord[i] instanceof OPTRecord)) goto _L2; else goto _L1
_L1:
        OPTRecord optrecord = (OPTRecord)arecord[i];
_L4:
        return optrecord;
_L2:
        i++;
          goto _L3
        optrecord = null;
          goto _L4
    }

    public Record getQuestion()
    {
        List list = sections[0];
        Record record;
        if(list == null || list.size() == 0)
            record = null;
        else
            record = (Record)list.get(0);
        return record;
    }

    public int getRcode()
    {
        int i = header.getRcode();
        OPTRecord optrecord = getOPT();
        if(optrecord != null)
            i += optrecord.getExtendedRcode() << 4;
        return i;
    }

    public Record[] getSectionArray(int i)
    {
        Record arecord[];
        if(sections[i] == null)
        {
            arecord = emptyRecordArray;
        } else
        {
            List list = sections[i];
            arecord = (Record[])(Record[])list.toArray(new Record[list.size()]);
        }
        return arecord;
    }

    public RRset[] getSectionRRsets(int i)
    {
        if(sections[i] != null) goto _L2; else goto _L1
_L1:
        RRset arrset[] = emptyRRsetArray;
_L9:
        return arrset;
_L2:
        LinkedList linkedlist;
        Record arecord[];
        HashSet hashset;
        int j;
        linkedlist = new LinkedList();
        arecord = getSectionArray(i);
        hashset = new HashSet();
        j = 0;
_L7:
        if(j >= arecord.length) goto _L4; else goto _L3
_L3:
        Name name;
        int k;
        name = arecord[j].getName();
        if(!hashset.contains(name))
            break MISSING_BLOCK_LABEL_225;
        k = linkedlist.size() - 1;
_L8:
        RRset rrset;
        if(k < 0)
            break MISSING_BLOCK_LABEL_225;
        rrset = (RRset)linkedlist.get(k);
        if(rrset.getType() != arecord[j].getRRsetType() || rrset.getDClass() != arecord[j].getDClass() || !rrset.getName().equals(name)) goto _L6; else goto _L5
_L5:
        boolean flag;
        rrset.addRR(arecord[j]);
        flag = false;
_L10:
        if(flag)
        {
            linkedlist.add(new RRset(arecord[j]));
            hashset.add(name);
        }
        j++;
          goto _L7
_L6:
        k--;
          goto _L8
_L4:
        arrset = (RRset[])(RRset[])linkedlist.toArray(new RRset[linkedlist.size()]);
          goto _L9
        flag = true;
          goto _L10
    }

    public TSIGRecord getTSIG()
    {
        int i = header.getCount(3);
        TSIGRecord tsigrecord;
        if(i == 0)
        {
            tsigrecord = null;
        } else
        {
            Record record = (Record)sections[3].get(i - 1);
            if(record.type != 250)
                tsigrecord = null;
            else
                tsigrecord = (TSIGRecord)record;
        }
        return tsigrecord;
    }

    public boolean isSigned()
    {
        boolean flag;
        if(tsigState == 3 || tsigState == 1 || tsigState == 4)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isVerified()
    {
        boolean flag;
        if(tsigState == 1)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public int numBytes()
    {
        return size;
    }

    public void removeAllRecords(int i)
    {
        sections[i] = null;
        header.setCount(i, 0);
    }

    public boolean removeRecord(Record record, int i)
    {
        boolean flag;
        if(sections[i] != null && sections[i].remove(record))
        {
            header.decCount(i);
            flag = true;
        } else
        {
            flag = false;
        }
        return flag;
    }

    public String sectionToString(int i)
    {
        String s;
        if(i > 3)
        {
            s = null;
        } else
        {
            StringBuffer stringbuffer = new StringBuffer();
            Record arecord[] = getSectionArray(i);
            int j = 0;
            while(j < arecord.length) 
            {
                Record record = arecord[j];
                if(i == 0)
                {
                    stringbuffer.append((new StringBuilder()).append(";;\t").append(record.name).toString());
                    stringbuffer.append((new StringBuilder()).append(", type = ").append(Type.string(record.type)).toString());
                    stringbuffer.append((new StringBuilder()).append(", class = ").append(DClass.string(record.dclass)).toString());
                } else
                {
                    stringbuffer.append(record);
                }
                stringbuffer.append("\n");
                j++;
            }
            s = stringbuffer.toString();
        }
        return s;
    }

    public void setHeader(Header header1)
    {
        header = header1;
    }

    public void setTSIG(TSIG tsig, int i, TSIGRecord tsigrecord)
    {
        tsigkey = tsig;
        tsigerror = i;
        querytsig = tsigrecord;
    }

    public String toString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        int i;
        if(getOPT() != null)
            stringbuffer.append((new StringBuilder()).append(header.toStringWithRcode(getRcode())).append("\n").toString());
        else
            stringbuffer.append((new StringBuilder()).append(header).append("\n").toString());
        if(isSigned())
        {
            stringbuffer.append(";; TSIG ");
            if(isVerified())
                stringbuffer.append("ok");
            else
                stringbuffer.append("invalid");
            stringbuffer.append('\n');
        }
        i = 0;
        while(i < 4) 
        {
            if(header.getOpcode() != 5)
                stringbuffer.append((new StringBuilder()).append(";; ").append(Section.longString(i)).append(":\n").toString());
            else
                stringbuffer.append((new StringBuilder()).append(";; ").append(Section.updString(i)).append(":\n").toString());
            stringbuffer.append((new StringBuilder()).append(sectionToString(i)).append("\n").toString());
            i++;
        }
        stringbuffer.append((new StringBuilder()).append(";; Message size: ").append(numBytes()).append(" bytes").toString());
        return stringbuffer.toString();
    }

    void toWire(DNSOutput dnsoutput)
    {
        header.toWire(dnsoutput);
        Compression compression = new Compression();
        int i = 0;
        while(i < 4) 
        {
            if(sections[i] != null)
            {
                int j = 0;
                while(j < sections[i].size()) 
                {
                    ((Record)sections[i].get(j)).toWire(dnsoutput, i, compression);
                    j++;
                }
            }
            i++;
        }
    }

    public byte[] toWire()
    {
        DNSOutput dnsoutput = new DNSOutput();
        toWire(dnsoutput);
        size = dnsoutput.current();
        return dnsoutput.toByteArray();
    }

    public byte[] toWire(int i)
    {
        DNSOutput dnsoutput = new DNSOutput();
        toWire(dnsoutput, i);
        size = dnsoutput.current();
        return dnsoutput.toByteArray();
    }

    public static final int MAXLENGTH = 65535;
    static final int TSIG_FAILED = 4;
    static final int TSIG_INTERMEDIATE = 2;
    static final int TSIG_SIGNED = 3;
    static final int TSIG_UNSIGNED = 0;
    static final int TSIG_VERIFIED = 1;
    private static RRset emptyRRsetArray[] = new RRset[0];
    private static Record emptyRecordArray[] = new Record[0];
    private Header header;
    private TSIGRecord querytsig;
    private List sections[];
    int sig0start;
    private int size;
    int tsigState;
    private int tsigerror;
    private TSIG tsigkey;
    int tsigstart;

}
