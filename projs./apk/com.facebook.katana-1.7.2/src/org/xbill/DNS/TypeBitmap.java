// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TypeBitmap.java

package org.xbill.DNS;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeSet;

// Referenced classes of package org.xbill.DNS:
//            WireParseException, DNSInput, Mnemonic, Tokenizer, 
//            Type, DNSOutput

final class TypeBitmap
    implements Serializable
{

    private TypeBitmap()
    {
        types = new TreeSet();
    }

    public TypeBitmap(DNSInput dnsinput)
        throws WireParseException
    {
        this();
        while(dnsinput.remaining() > 0) 
        {
            if(dnsinput.remaining() < 2)
                throw new WireParseException("invalid bitmap descriptor");
            int i = dnsinput.readU8();
            if(i < -1)
                throw new WireParseException("invalid ordering");
            int j = dnsinput.readU8();
            if(j > dnsinput.remaining())
                throw new WireParseException("invalid bitmap");
            int k = 0;
            while(k < j) 
            {
                int l = dnsinput.readU8();
                if(l != 0)
                {
                    int i1 = 0;
                    while(i1 < 8) 
                    {
                        if((l & 1 << 7 - i1) != 0)
                        {
                            int j1 = i1 + (i * 256 + k * 8);
                            types.add(Mnemonic.toInteger(j1));
                        }
                        i1++;
                    }
                }
                k++;
            }
        }
    }

    public TypeBitmap(Tokenizer tokenizer)
        throws IOException
    {
        this();
        do
        {
            Tokenizer.Token token = tokenizer.get();
            if(!token.isString())
            {
                tokenizer.unget();
                return;
            }
            int i = Type.value(token.value);
            if(i < 0)
                throw tokenizer.exception((new StringBuilder()).append("Invalid type: ").append(token.value).toString());
            types.add(Mnemonic.toInteger(i));
        } while(true);
    }

    public TypeBitmap(int ai[])
    {
        this();
        for(int i = 0; i < ai.length; i++)
        {
            Type.check(ai[i]);
            types.add(new Integer(ai[i]));
        }

    }

    private static void mapToWire(DNSOutput dnsoutput, TreeSet treeset, int i)
    {
        int j = 1 + (0xff & ((Integer)treeset.last()).intValue()) / 8;
        int ai[] = new int[j];
        dnsoutput.writeU8(i);
        dnsoutput.writeU8(j);
        for(Iterator iterator = treeset.iterator(); iterator.hasNext();)
        {
            int l = ((Integer)iterator.next()).intValue();
            int i1 = (l & 0xff) / 8;
            ai[i1] = ai[i1] | 1 << 7 - l % 8;
        }

        for(int k = 0; k < j; k++)
            dnsoutput.writeU8(ai[k]);

    }

    public boolean contains(int i)
    {
        return types.contains(Mnemonic.toInteger(i));
    }

    public boolean empty()
    {
        return types.isEmpty();
    }

    public int[] toArray()
    {
        int ai[] = new int[types.size()];
        int i = 0;
        for(Iterator iterator = types.iterator(); iterator.hasNext();)
        {
            int j = i + 1;
            ai[i] = ((Integer)iterator.next()).intValue();
            i = j;
        }

        return ai;
    }

    public String toString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        for(Iterator iterator = types.iterator(); iterator.hasNext(); stringbuffer.append(' '))
            stringbuffer.append(Type.string(((Integer)iterator.next()).intValue()));

        return stringbuffer.substring(0, stringbuffer.length() - 1);
    }

    public void toWire(DNSOutput dnsoutput)
    {
        if(types.size() != 0)
        {
            int i = -1;
            TreeSet treeset = new TreeSet();
            int j;
            for(Iterator iterator = types.iterator(); iterator.hasNext(); treeset.add(new Integer(j)))
            {
                j = ((Integer)iterator.next()).intValue();
                int k = j >> 8;
                if(k == i)
                    continue;
                if(treeset.size() > 0)
                {
                    mapToWire(dnsoutput, treeset, i);
                    treeset.clear();
                }
                i = k;
            }

            mapToWire(dnsoutput, treeset, i);
        }
    }

    private static final long serialVersionUID = 0xd8740b5L;
    private TreeSet types;
}
