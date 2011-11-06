// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   APLRecord.java

package org.xbill.DNS;

import java.io.IOException;
import java.net.InetAddress;
import java.util.*;
import org.xbill.DNS.utils.base16;

// Referenced classes of package org.xbill.DNS:
//            Record, WireParseException, Tokenizer, Address, 
//            DNSInput, DNSOutput, Name, Compression

public class APLRecord extends Record
{
    public static class Element
    {

        public boolean equals(Object obj)
        {
            boolean flag;
            if(obj == null || !(obj instanceof Element))
            {
                flag = false;
            } else
            {
                Element element = (Element)obj;
                if(family == element.family && negative == element.negative && prefixLength == element.prefixLength && address.equals(element.address))
                    flag = true;
                else
                    flag = false;
            }
            return flag;
        }

        public int hashCode()
        {
            int i = address.hashCode() + prefixLength;
            int j;
            if(negative)
                j = 1;
            else
                j = 0;
            return i + j;
        }

        public String toString()
        {
            StringBuffer stringbuffer = new StringBuffer();
            if(negative)
                stringbuffer.append("!");
            stringbuffer.append(family);
            stringbuffer.append(":");
            if(family == 1 || family == 2)
                stringbuffer.append(((InetAddress)address).getHostAddress());
            else
                stringbuffer.append(base16.toString((byte[])(byte[])address));
            stringbuffer.append("/");
            stringbuffer.append(prefixLength);
            return stringbuffer.toString();
        }

        public final Object address;
        public final int family;
        public final boolean negative;
        public final int prefixLength;

        private Element(int i, boolean flag, Object obj, int j)
        {
            family = i;
            negative = flag;
            address = obj;
            prefixLength = j;
            if(!APLRecord.validatePrefixLength(i, j))
                throw new IllegalArgumentException("invalid prefix length");
            else
                return;
        }


        public Element(boolean flag, InetAddress inetaddress, int i)
        {
            this(Address.familyOf(inetaddress), flag, inetaddress, i);
        }
    }


    APLRecord()
    {
    }

    public APLRecord(Name name, int i, long l, List list)
    {
        super(name, 42, i, l);
        elements = new ArrayList(list.size());
        Element element;
        for(Iterator iterator = list.iterator(); iterator.hasNext(); elements.add(element))
        {
            Object obj = iterator.next();
            if(!(obj instanceof Element))
                throw new IllegalArgumentException("illegal element");
            element = (Element)obj;
            if(element.family != 1 && element.family != 2)
                throw new IllegalArgumentException("unknown family");
        }

    }

    private static int addressLength(byte abyte0[])
    {
        int i = abyte0.length - 1;
_L3:
        if(i < 0)
            break MISSING_BLOCK_LABEL_27;
        if(abyte0[i] == 0) goto _L2; else goto _L1
_L1:
        int j = i + 1;
_L4:
        return j;
_L2:
        i--;
          goto _L3
        j = 0;
          goto _L4
    }

    private static byte[] parseAddress(byte abyte0[], int i)
        throws WireParseException
    {
        if(abyte0.length > i)
            throw new WireParseException("invalid address length");
        byte abyte1[];
        if(abyte0.length == i)
        {
            abyte1 = abyte0;
        } else
        {
            abyte1 = new byte[i];
            System.arraycopy(abyte0, 0, abyte1, 0, abyte0.length);
        }
        return abyte1;
    }

    private static boolean validatePrefixLength(int i, int j)
    {
        boolean flag;
        if(j < 0 || j >= 256)
            flag = false;
        else
        if(i == 1 && j > 32 || i == 2 && j > 128)
            flag = false;
        else
            flag = true;
        return flag;
    }

    public List getElements()
    {
        return elements;
    }

    Record getObject()
    {
        return new APLRecord();
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        elements = new ArrayList(1);
        do
        {
            Tokenizer.Token token = tokenizer.get();
            if(!token.isString())
            {
                tokenizer.unget();
                return;
            }
            String s = token.value;
            int i;
            boolean flag;
            int j;
            int k;
            String s1;
            String s2;
            String s3;
            int l;
            int i1;
            byte abyte0[];
            InetAddress inetaddress;
            if(s.startsWith("!"))
            {
                i = 1;
                flag = true;
            } else
            {
                i = 0;
                flag = false;
            }
            j = s.indexOf(':', i);
            if(j < 0)
                throw tokenizer.exception("invalid address prefix element");
            k = s.indexOf('/', j);
            if(k < 0)
                throw tokenizer.exception("invalid address prefix element");
            s1 = s.substring(i, j);
            s2 = s.substring(j + 1, k);
            s3 = s.substring(k + 1);
            try
            {
                l = Integer.parseInt(s1);
            }
            catch(NumberFormatException numberformatexception)
            {
                throw tokenizer.exception("invalid family");
            }
            if(l != 1 && l != 2)
                throw tokenizer.exception("unknown family");
            try
            {
                i1 = Integer.parseInt(s3);
            }
            catch(NumberFormatException numberformatexception1)
            {
                throw tokenizer.exception("invalid prefix length");
            }
            if(!validatePrefixLength(l, i1))
                throw tokenizer.exception("invalid prefix length");
            abyte0 = Address.toByteArray(s2, l);
            if(abyte0 == null)
                throw tokenizer.exception((new StringBuilder()).append("invalid IP address ").append(s2).toString());
            inetaddress = InetAddress.getByAddress(abyte0);
            elements.add(new Element(flag, inetaddress, i1));
        } while(true);
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        elements = new ArrayList(1);
        while(dnsinput.remaining() != 0) 
        {
            int i = dnsinput.readU16();
            int j = dnsinput.readU8();
            int k = dnsinput.readU8();
            boolean flag;
            byte abyte0[];
            if((k & 0x80) != 0)
                flag = true;
            else
                flag = false;
            abyte0 = dnsinput.readByteArray(k & 0xffffff7f);
            if(!validatePrefixLength(i, j))
                throw new WireParseException("invalid prefix length");
            Element element;
            if(i == 1 || i == 2)
                element = new Element(flag, InetAddress.getByAddress(parseAddress(abyte0, Address.addressLength(i))), j);
            else
                element = new Element(i, flag, abyte0, j);
            elements.add(element);
        }
    }

    String rrToString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        Iterator iterator = elements.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            stringbuffer.append((Element)iterator.next());
            if(iterator.hasNext())
                stringbuffer.append(" ");
        } while(true);
        return stringbuffer.toString();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        Iterator iterator = elements.iterator();
        while(iterator.hasNext()) 
        {
            Element element = (Element)iterator.next();
            byte abyte0[];
            int i;
            int j;
            if(element.family == 1 || element.family == 2)
            {
                abyte0 = ((InetAddress)element.address).getAddress();
                i = addressLength(abyte0);
            } else
            {
                abyte0 = (byte[])(byte[])element.address;
                i = abyte0.length;
            }
            if(element.negative)
                j = i | 0x80;
            else
                j = i;
            dnsoutput.writeU16(element.family);
            dnsoutput.writeU8(element.prefixLength);
            dnsoutput.writeU8(j);
            dnsoutput.writeByteArray(abyte0, 0, i);
        }
    }

    private static final long serialVersionUID = 0x51758048L;
    private List elements;

}
