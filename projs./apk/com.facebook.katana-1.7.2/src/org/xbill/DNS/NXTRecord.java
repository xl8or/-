// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NXTRecord.java

package org.xbill.DNS;

import java.io.IOException;
import java.util.BitSet;

// Referenced classes of package org.xbill.DNS:
//            Record, Tokenizer, Type, Name, 
//            DNSInput, DNSOutput, Compression

public class NXTRecord extends Record
{

    NXTRecord()
    {
    }

    public NXTRecord(Name name, int i, long l, Name name1, BitSet bitset)
    {
        Record(name, 30, i, l);
        next = checkName("next", name1);
        bitmap = bitset;
    }

    public BitSet getBitmap()
    {
        return bitmap;
    }

    public Name getNext()
    {
        return next;
    }

    Record getObject()
    {
        return new NXTRecord();
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        next = tokenizer.getName(name);
        bitmap = new BitSet();
        do
        {
            Tokenizer.Token token = tokenizer.get();
            if(!token.isString())
            {
                tokenizer.unget();
                return;
            }
            int i = Type.value(token.value, true);
            if(i <= 0 || i > 128)
                throw tokenizer.exception((new StringBuilder()).append("Invalid type: ").append(token.value).toString());
            bitmap.set(i);
        } while(true);
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        next = new Name(dnsinput);
        bitmap = new BitSet();
        int i = dnsinput.remaining();
        for(int j = 0; j < i; j++)
        {
            int k = dnsinput.readU8();
            for(int l = 0; l < 8; l++)
                if((k & 1 << 7 - l) != 0)
                    bitmap.set(l + j * 8);

        }

    }

    String rrToString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(next);
        int i = bitmap.length();
        for(short word0 = 0; word0 < i; word0++)
            if(bitmap.get(word0))
            {
                stringbuffer.append(" ");
                stringbuffer.append(Type.string(word0));
            }

        return stringbuffer.toString();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        next.toWire(dnsoutput, null, flag);
        int i = bitmap.length();
        int j = 0;
        int k = 0;
        while(k < i) 
        {
            int l;
            if(bitmap.get(k))
                l = 1 << 7 - k % 8;
            else
                l = 0;
            j |= l;
            if(k % 8 == 7 || k == i - 1)
            {
                dnsoutput.writeU8(j);
                j = 0;
            }
            k++;
        }
    }

    private static final long serialVersionUID = 0x98cdc840L;
    private BitSet bitmap;
    private Name next;
}
