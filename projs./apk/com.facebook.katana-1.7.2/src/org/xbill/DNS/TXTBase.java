// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TXTBase.java

package org.xbill.DNS;

import java.io.IOException;
import java.util.*;

// Referenced classes of package org.xbill.DNS:
//            Record, TextParseException, Tokenizer, DNSInput, 
//            DNSOutput, Name, Compression

abstract class TXTBase extends Record
{

    protected TXTBase()
    {
    }

    protected TXTBase(Name name, int i, int j, long l)
    {
        super(name, i, j, l);
    }

    protected TXTBase(Name name, int i, int j, long l, String s)
    {
        this(name, i, j, l, Collections.singletonList(s));
    }

    protected TXTBase(Name name, int i, int j, long l, List list)
    {
        super(name, i, j, l);
        if(list == null)
            throw new IllegalArgumentException("strings must not be null");
        strings = new ArrayList(list.size());
        Iterator iterator = list.iterator();
        try
        {
            while(iterator.hasNext()) 
            {
                String s = (String)iterator.next();
                strings.add(byteArrayFromString(s));
            }
        }
        catch(TextParseException textparseexception)
        {
            throw new IllegalArgumentException(textparseexception.getMessage());
        }
    }

    public List getStrings()
    {
        ArrayList arraylist = new ArrayList(strings.size());
        for(int i = 0; i < strings.size(); i++)
            arraylist.add(byteArrayToString((byte[])(byte[])strings.get(i), false));

        return arraylist;
    }

    public List getStringsAsByteArrays()
    {
        return strings;
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        strings = new ArrayList(2);
        do
        {
            Tokenizer.Token token = tokenizer.get();
            if(!token.isString())
            {
                tokenizer.unget();
                return;
            }
            try
            {
                strings.add(byteArrayFromString(token.value));
            }
            catch(TextParseException textparseexception)
            {
                throw tokenizer.exception(textparseexception.getMessage());
            }
        } while(true);
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        strings = new ArrayList(2);
        byte abyte0[];
        for(; dnsinput.remaining() > 0; strings.add(abyte0))
            abyte0 = dnsinput.readCountedString();

    }

    String rrToString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        Iterator iterator = strings.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            stringbuffer.append(byteArrayToString((byte[])(byte[])iterator.next(), true));
            if(iterator.hasNext())
                stringbuffer.append(" ");
        } while(true);
        return stringbuffer.toString();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        for(Iterator iterator = strings.iterator(); iterator.hasNext(); dnsoutput.writeCountedString((byte[])(byte[])iterator.next()));
    }

    private static final long serialVersionUID = 0x54043d75L;
    protected List strings;
}
