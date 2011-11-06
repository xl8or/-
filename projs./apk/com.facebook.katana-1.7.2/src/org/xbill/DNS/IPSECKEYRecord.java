// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IPSECKEYRecord.java

package org.xbill.DNS;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import org.xbill.DNS.utils.base64;

// Referenced classes of package org.xbill.DNS:
//            Record, Name, Tokenizer, WireParseException, 
//            TextParseException, DNSInput, DNSOutput, Compression

public class IPSECKEYRecord extends Record
{
    public static class Gateway
    {

        public static final int IPv4 = 1;
        public static final int IPv6 = 2;
        public static final int Name = 3;
        public static final int None;

        private Gateway()
        {
        }
    }

    public static class Algorithm
    {

        public static final int DSA = 1;
        public static final int RSA = 2;

        private Algorithm()
        {
        }
    }


    IPSECKEYRecord()
    {
    }

    public IPSECKEYRecord(Name name, int i, long l, int j, int k, int i1, 
            Object obj, byte abyte0[])
    {
        Record(name, 45, i, l);
        precedence = checkU8("precedence", j);
        gatewayType = checkU8("gatewayType", k);
        algorithmType = checkU8("algorithmType", i1);
        k;
        JVM INSTR tableswitch 0 3: default 76
    //                   0 86
    //                   1 98
    //                   2 125
    //                   3 152;
           goto _L1 _L2 _L3 _L4 _L5
_L1:
        throw new IllegalArgumentException("\"gatewayType\" must be between 0 and 3");
_L2:
        gateway = null;
_L7:
        key = abyte0;
        return;
_L3:
        if(!(obj instanceof InetAddress))
            throw new IllegalArgumentException("\"gateway\" must be an IPv4 address");
        gateway = obj;
        continue; /* Loop/switch isn't completed */
_L4:
        if(!(obj instanceof Inet6Address))
            throw new IllegalArgumentException("\"gateway\" must be an IPv6 address");
        gateway = obj;
        continue; /* Loop/switch isn't completed */
_L5:
        if(!(obj instanceof Name))
            throw new IllegalArgumentException("\"gateway\" must be a DNS name");
        gateway = checkName("gateway", (Name)obj);
        if(true) goto _L7; else goto _L6
_L6:
    }

    public int getAlgorithmType()
    {
        return algorithmType;
    }

    public Object getGateway()
    {
        return gateway;
    }

    public int getGatewayType()
    {
        return gatewayType;
    }

    public byte[] getKey()
    {
        return key;
    }

    Record getObject()
    {
        return new IPSECKEYRecord();
    }

    public int getPrecedence()
    {
        return precedence;
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        precedence = tokenizer.getUInt8();
        gatewayType = tokenizer.getUInt8();
        algorithmType = tokenizer.getUInt8();
        gatewayType;
        JVM INSTR tableswitch 0 3: default 60
    //                   0 70
    //                   1 107
    //                   2 119
    //                   3 131;
           goto _L1 _L2 _L3 _L4 _L5
_L1:
        throw new WireParseException("invalid gateway type");
_L2:
        if(!tokenizer.getString().equals("."))
            throw new TextParseException("invalid gateway format");
        gateway = null;
_L7:
        key = tokenizer.getBase64(false);
        return;
_L3:
        gateway = tokenizer.getAddress(1);
        continue; /* Loop/switch isn't completed */
_L4:
        gateway = tokenizer.getAddress(2);
        continue; /* Loop/switch isn't completed */
_L5:
        gateway = tokenizer.getName(name);
        if(true) goto _L7; else goto _L6
_L6:
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        precedence = dnsinput.readU8();
        gatewayType = dnsinput.readU8();
        algorithmType = dnsinput.readU8();
        gatewayType;
        JVM INSTR tableswitch 0 3: default 60
    //                   0 70
    //                   1 91
    //                   2 106
    //                   3 122;
           goto _L1 _L2 _L3 _L4 _L5
_L1:
        throw new WireParseException("invalid gateway type");
_L2:
        gateway = null;
_L7:
        if(dnsinput.remaining() > 0)
            key = dnsinput.readByteArray();
        return;
_L3:
        gateway = InetAddress.getByAddress(dnsinput.readByteArray(4));
        continue; /* Loop/switch isn't completed */
_L4:
        gateway = InetAddress.getByAddress(dnsinput.readByteArray(16));
        continue; /* Loop/switch isn't completed */
_L5:
        gateway = new Name(dnsinput);
        if(true) goto _L7; else goto _L6
_L6:
    }

    String rrToString()
    {
        StringBuffer stringbuffer;
        stringbuffer = new StringBuffer();
        stringbuffer.append(precedence);
        stringbuffer.append(" ");
        stringbuffer.append(gatewayType);
        stringbuffer.append(" ");
        stringbuffer.append(algorithmType);
        stringbuffer.append(" ");
        gatewayType;
        JVM INSTR tableswitch 0 3: default 92
    //                   0 123
    //                   1 133
    //                   2 133
    //                   3 151;
           goto _L1 _L2 _L3 _L3 _L4
_L1:
        if(key != null)
        {
            stringbuffer.append(" ");
            stringbuffer.append(base64.toString(key));
        }
        return stringbuffer.toString();
_L2:
        stringbuffer.append(".");
        continue; /* Loop/switch isn't completed */
_L3:
        stringbuffer.append(((InetAddress)gateway).getHostAddress());
        continue; /* Loop/switch isn't completed */
_L4:
        stringbuffer.append(gateway);
        if(true) goto _L1; else goto _L5
_L5:
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        dnsoutput.writeU8(precedence);
        dnsoutput.writeU8(gatewayType);
        dnsoutput.writeU8(algorithmType);
        gatewayType;
        JVM INSTR tableswitch 0 3: default 60
    //                   0 60
    //                   1 76
    //                   2 76
    //                   3 93;
           goto _L1 _L1 _L2 _L2 _L3
_L1:
        if(key != null)
            dnsoutput.writeByteArray(key);
        return;
_L2:
        dnsoutput.writeByteArray(((InetAddress)gateway).getAddress());
        continue; /* Loop/switch isn't completed */
_L3:
        ((Name)gateway).toWire(dnsoutput, null, flag);
        if(true) goto _L1; else goto _L4
_L4:
    }

    private static final long serialVersionUID = 0xba8ed6b7L;
    private int algorithmType;
    private Object gateway;
    private int gatewayType;
    private byte key[];
    private int precedence;
}
