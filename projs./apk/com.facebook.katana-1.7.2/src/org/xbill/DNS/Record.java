// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Record.java

package org.xbill.DNS;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import org.xbill.DNS.utils.base16;

// Referenced classes of package org.xbill.DNS:
//            Name, RelativeNameException, Type, DClass, 
//            TTL, TextParseException, Tokenizer, DNSInput, 
//            UNKRecord, EmptyRecord, WireParseException, DNSOutput, 
//            RRSIGRecord, Options, Compression

public abstract class Record
    implements Cloneable, Comparable, Serializable
{

    protected Record()
    {
    }

    Record(Name name1, int i, int j, long l)
    {
        if(!name1.isAbsolute())
        {
            throw new RelativeNameException(name1);
        } else
        {
            Type.check(i);
            DClass.check(j);
            TTL.check(l);
            name = name1;
            type = i;
            dclass = j;
            ttl = l;
            return;
        }
    }

    protected static byte[] byteArrayFromString(String s)
        throws TextParseException
    {
        byte abyte0[];
        int i;
        abyte0 = s.getBytes();
        i = 0;
_L6:
        if(i >= abyte0.length)
            break MISSING_BLOCK_LABEL_320;
        if(abyte0[i] != 92) goto _L2; else goto _L1
_L1:
        boolean flag = true;
_L17:
        if(flag) goto _L4; else goto _L3
_L3:
        if(abyte0.length > 255)
            throw new TextParseException("text string too long");
          goto _L5
_L2:
        i++;
          goto _L6
_L4:
        ByteArrayOutputStream bytearrayoutputstream;
        int j;
        int k;
        int l;
        boolean flag1;
        bytearrayoutputstream = new ByteArrayOutputStream();
        j = 0;
        k = 0;
        l = 0;
        flag1 = false;
_L15:
        if(j >= abyte0.length) goto _L8; else goto _L7
_L7:
        byte byte0 = abyte0[j];
        if(!flag1) goto _L10; else goto _L9
_L9:
        if(byte0 < 48 || byte0 > 57 || l >= 3) goto _L12; else goto _L11
_L11:
        l++;
        k = k * 10 + (byte0 - 48);
        if(k > 255)
            throw new TextParseException("bad escape");
        if(l >= 3) goto _L14; else goto _L13
_L13:
        j++;
          goto _L15
_L14:
        int i1;
        int j1;
        int k1;
        int l1 = k;
        i1 = l;
        j1 = k;
        k1 = l1;
_L16:
        bytearrayoutputstream.write(k1);
        k = j1;
        l = i1;
        flag1 = false;
          goto _L13
_L10:
        if(abyte0[j] == 92)
        {
            k = 0;
            l = 0;
            flag1 = true;
        } else
        {
            bytearrayoutputstream.write(abyte0[j]);
        }
          goto _L13
_L8:
        if(l > 0 && l < 3)
            throw new TextParseException("bad escape");
        if(bytearrayoutputstream.toByteArray().length > 255)
            throw new TextParseException("text string too long");
        abyte0 = bytearrayoutputstream.toByteArray();
_L5:
        return abyte0;
_L12:
        if(l > 0 && l < 3)
            throw new TextParseException("bad escape");
        i1 = l;
        j1 = k;
        k1 = byte0;
          goto _L16
        flag = false;
          goto _L17
    }

    protected static String byteArrayToString(byte abyte0[], boolean flag)
    {
        StringBuffer stringbuffer = new StringBuffer();
        if(flag)
            stringbuffer.append('"');
        int i = 0;
        while(i < abyte0.length) 
        {
            int j = 0xff & abyte0[i];
            if(j < 32 || j >= 127)
            {
                stringbuffer.append('\\');
                stringbuffer.append(byteFormat.format(j));
            } else
            if(j == 34 || j == 92)
            {
                stringbuffer.append('\\');
                stringbuffer.append((char)j);
            } else
            {
                stringbuffer.append((char)j);
            }
            i++;
        }
        if(flag)
            stringbuffer.append('"');
        return stringbuffer.toString();
    }

    static Name checkName(String s, Name name1)
    {
        if(!name1.isAbsolute())
            throw new RelativeNameException(name1);
        else
            return name1;
    }

    static int checkU16(String s, int i)
    {
        if(i < 0 || i > 65535)
            throw new IllegalArgumentException((new StringBuilder()).append("\"").append(s).append("\" ").append(i).append(" must be an unsigned 16 ").append("bit value").toString());
        else
            return i;
    }

    static long checkU32(String s, long l)
    {
        if(l < 0L || l > 0xffffffffL)
            throw new IllegalArgumentException((new StringBuilder()).append("\"").append(s).append("\" ").append(l).append(" must be an unsigned 32 ").append("bit value").toString());
        else
            return l;
    }

    static int checkU8(String s, int i)
    {
        if(i < 0 || i > 255)
            throw new IllegalArgumentException((new StringBuilder()).append("\"").append(s).append("\" ").append(i).append(" must be an unsigned 8 ").append("bit value").toString());
        else
            return i;
    }

    public static Record fromString(Name name1, int i, int j, long l, String s, Name name2)
        throws IOException
    {
        return fromString(name1, i, j, l, new Tokenizer(s), name2);
    }

    public static Record fromString(Name name1, int i, int j, long l, Tokenizer tokenizer, Name name2)
        throws IOException
    {
        if(!name1.isAbsolute())
            throw new RelativeNameException(name1);
        Type.check(i);
        DClass.check(j);
        TTL.check(l);
        Tokenizer.Token token = tokenizer.get();
        Record record;
        if(token.type == 3 && token.value.equals("\\#"))
        {
            int k = tokenizer.getUInt16();
            byte abyte0[] = tokenizer.getHex();
            if(abyte0 == null)
                abyte0 = new byte[0];
            if(k != abyte0.length)
                throw tokenizer.exception("invalid unknown RR encoding: length mismatch");
            record = newRecord(name1, i, j, l, k, new DNSInput(abyte0));
        } else
        {
            tokenizer.unget();
            record = getEmptyRecord(name1, i, j, l, true);
            record.rdataFromString(tokenizer, name2);
            Tokenizer.Token token1 = tokenizer.get();
            if(token1.type != 1 && token1.type != 0)
                throw tokenizer.exception("unexpected tokens at end of record");
        }
        return record;
    }

    static Record fromWire(DNSInput dnsinput, int i)
        throws IOException
    {
        return fromWire(dnsinput, i, false);
    }

    static Record fromWire(DNSInput dnsinput, int i, boolean flag)
        throws IOException
    {
        Name name1 = new Name(dnsinput);
        int j = dnsinput.readU16();
        int k = dnsinput.readU16();
        Record record;
        if(i == 0)
        {
            record = newRecord(name1, j, k);
        } else
        {
            long l = dnsinput.readU32();
            int i1 = dnsinput.readU16();
            if(i1 == 0 && flag)
                record = newRecord(name1, j, k, l);
            else
                record = newRecord(name1, j, k, l, i1, dnsinput);
        }
        return record;
    }

    public static Record fromWire(byte abyte0[], int i)
        throws IOException
    {
        return fromWire(new DNSInput(abyte0), i, false);
    }

    private static final Record getEmptyRecord(Name name1, int i, int j, long l, boolean flag)
    {
        Object obj;
        if(flag)
        {
            Record record = Type.getProto(i);
            if(record != null)
                obj = record.getObject();
            else
                obj = new UNKRecord();
        } else
        {
            obj = new EmptyRecord();
        }
        obj.name = name1;
        obj.type = i;
        obj.dclass = j;
        obj.ttl = l;
        return ((Record) (obj));
    }

    public static Record newRecord(Name name1, int i, int j)
    {
        return newRecord(name1, i, j, 0L);
    }

    public static Record newRecord(Name name1, int i, int j, long l)
    {
        if(!name1.isAbsolute())
        {
            throw new RelativeNameException(name1);
        } else
        {
            Type.check(i);
            DClass.check(j);
            TTL.check(l);
            return getEmptyRecord(name1, i, j, l, false);
        }
    }

    private static Record newRecord(Name name1, int i, int j, long l, int k, DNSInput dnsinput)
        throws IOException
    {
        boolean flag;
        Record record;
        if(dnsinput != null)
            flag = true;
        else
            flag = false;
        record = getEmptyRecord(name1, i, j, l, flag);
        if(dnsinput != null)
        {
            if(dnsinput.remaining() < k)
                throw new WireParseException("truncated record");
            dnsinput.setActive(k);
            record.rrFromWire(dnsinput);
            if(dnsinput.remaining() > 0)
                throw new WireParseException("invalid record length");
            dnsinput.clearActive();
        }
        return record;
    }

    public static Record newRecord(Name name1, int i, int j, long l, int k, byte abyte0[])
    {
        Record record;
        if(!name1.isAbsolute())
            throw new RelativeNameException(name1);
        Type.check(i);
        DClass.check(j);
        TTL.check(l);
        DNSInput dnsinput;
        Record record1;
        if(abyte0 != null)
            dnsinput = new DNSInput(abyte0);
        else
            dnsinput = null;
        record1 = newRecord(name1, i, j, l, k, dnsinput);
        record = record1;
_L2:
        return record;
        IOException ioexception;
        ioexception;
        record = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static Record newRecord(Name name1, int i, int j, long l, byte abyte0[])
    {
        return newRecord(name1, i, j, l, abyte0.length, abyte0);
    }

    private void toWireCanonical(DNSOutput dnsoutput, boolean flag)
    {
        name.toWireCanonical(dnsoutput);
        dnsoutput.writeU16(type);
        dnsoutput.writeU16(dclass);
        int i;
        int j;
        if(flag)
            dnsoutput.writeU32(0L);
        else
            dnsoutput.writeU32(ttl);
        i = dnsoutput.current();
        dnsoutput.writeU16(0);
        rrToWire(dnsoutput, null, true);
        j = dnsoutput.current() - i - 2;
        dnsoutput.save();
        dnsoutput.jump(i);
        dnsoutput.writeU16(j);
        dnsoutput.restore();
    }

    private byte[] toWireCanonical(boolean flag)
    {
        DNSOutput dnsoutput = new DNSOutput();
        toWireCanonical(dnsoutput, flag);
        return dnsoutput.toByteArray();
    }

    protected static String unknownToString(byte abyte0[])
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("\\# ");
        stringbuffer.append(abyte0.length);
        stringbuffer.append(" ");
        stringbuffer.append(base16.toString(abyte0));
        return stringbuffer.toString();
    }

    Record cloneRecord()
    {
        Record record;
        try
        {
            record = (Record)clone();
        }
        catch(CloneNotSupportedException clonenotsupportedexception)
        {
            throw new IllegalStateException();
        }
        return record;
    }

    public int compareTo(Object obj)
    {
        int i;
        Record record;
        i = 0;
        record = (Record)obj;
        if(this != record) goto _L2; else goto _L1
_L1:
        int j = i;
_L4:
        return j;
_L2:
        j = name.compareTo(record.name);
        if(j != 0)
            continue; /* Loop/switch isn't completed */
        j = dclass - record.dclass;
        if(j != 0)
            continue; /* Loop/switch isn't completed */
        j = type - record.type;
        if(j != 0)
            continue; /* Loop/switch isn't completed */
        byte abyte0[] = rdataToWireCanonical();
        byte abyte1[] = record.rdataToWireCanonical();
        do
        {
            if(i >= abyte0.length || i >= abyte1.length)
                break;
            int k = (0xff & abyte0[i]) - (0xff & abyte1[i]);
            if(k != 0)
            {
                j = k;
                continue; /* Loop/switch isn't completed */
            }
            i++;
        } while(true);
        j = abyte0.length - abyte1.length;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if(obj == null || !(obj instanceof Record))
        {
            flag = false;
        } else
        {
            Record record = (Record)obj;
            if(type != record.type || dclass != record.dclass || !name.equals(record.name))
                flag = false;
            else
                flag = Arrays.equals(rdataToWireCanonical(), record.rdataToWireCanonical());
        }
        return flag;
    }

    public Name getAdditionalName()
    {
        return null;
    }

    public int getDClass()
    {
        return dclass;
    }

    public Name getName()
    {
        return name;
    }

    abstract Record getObject();

    public int getRRsetType()
    {
        int i;
        if(type == 46)
            i = ((RRSIGRecord)this).getTypeCovered();
        else
            i = type;
        return i;
    }

    public long getTTL()
    {
        return ttl;
    }

    public int getType()
    {
        return type;
    }

    public int hashCode()
    {
        int i = 0;
        byte abyte0[] = toWireCanonical(true);
        int j = i;
        for(; i < abyte0.length; i++)
            j += (j << 3) + (0xff & abyte0[i]);

        return j;
    }

    abstract void rdataFromString(Tokenizer tokenizer, Name name1)
        throws IOException;

    public String rdataToString()
    {
        return rrToString();
    }

    public byte[] rdataToWireCanonical()
    {
        DNSOutput dnsoutput = new DNSOutput();
        rrToWire(dnsoutput, null, true);
        return dnsoutput.toByteArray();
    }

    abstract void rrFromWire(DNSInput dnsinput)
        throws IOException;

    abstract String rrToString();

    abstract void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag);

    public boolean sameRRset(Record record)
    {
        boolean flag;
        if(getRRsetType() == record.getRRsetType() && dclass == record.dclass && name.equals(record.name))
            flag = true;
        else
            flag = false;
        return flag;
    }

    void setTTL(long l)
    {
        ttl = l;
    }

    public String toString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(name);
        if(stringbuffer.length() < 8)
            stringbuffer.append("\t");
        if(stringbuffer.length() < 16)
            stringbuffer.append("\t");
        stringbuffer.append("\t");
        String s;
        if(Options.check("BINDTTL"))
            stringbuffer.append(TTL.format(ttl));
        else
            stringbuffer.append(ttl);
        stringbuffer.append("\t");
        if(dclass != 1 || !Options.check("noPrintIN"))
        {
            stringbuffer.append(DClass.string(dclass));
            stringbuffer.append("\t");
        }
        stringbuffer.append(Type.string(type));
        s = rrToString();
        if(!s.equals(""))
        {
            stringbuffer.append("\t");
            stringbuffer.append(s);
        }
        return stringbuffer.toString();
    }

    void toWire(DNSOutput dnsoutput, int i, Compression compression)
    {
        name.toWire(dnsoutput, compression);
        dnsoutput.writeU16(type);
        dnsoutput.writeU16(dclass);
        if(i != 0)
        {
            dnsoutput.writeU32(ttl);
            int j = dnsoutput.current();
            dnsoutput.writeU16(0);
            rrToWire(dnsoutput, compression, false);
            int k = dnsoutput.current() - j - 2;
            dnsoutput.save();
            dnsoutput.jump(j);
            dnsoutput.writeU16(k);
            dnsoutput.restore();
        }
    }

    public byte[] toWire(int i)
    {
        DNSOutput dnsoutput = new DNSOutput();
        toWire(dnsoutput, i, null);
        return dnsoutput.toByteArray();
    }

    public byte[] toWireCanonical()
    {
        return toWireCanonical(false);
    }

    Record withDClass(int i, long l)
    {
        Record record = cloneRecord();
        record.dclass = i;
        record.ttl = l;
        return record;
    }

    public Record withName(Name name1)
    {
        if(!name1.isAbsolute())
        {
            throw new RelativeNameException(name1);
        } else
        {
            Record record = cloneRecord();
            record.name = name1;
            return record;
        }
    }

    private static final DecimalFormat byteFormat;
    private static final long serialVersionUID = 0x3c372e5aL;
    protected int dclass;
    protected Name name;
    protected long ttl;
    protected int type;

    static 
    {
        byteFormat = new DecimalFormat();
        byteFormat.setMinimumIntegerDigits(3);
    }
}
