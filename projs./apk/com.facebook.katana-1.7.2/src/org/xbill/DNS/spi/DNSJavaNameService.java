// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DNSJavaNameService.java

package org.xbill.DNS.spi;

import java.io.PrintStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import org.xbill.DNS.*;

public class DNSJavaNameService
    implements InvocationHandler
{

    protected DNSJavaNameService()
    {
        preferV6 = false;
        String s = System.getProperty("sun.net.spi.nameservice.nameservers");
        String s1 = System.getProperty("sun.net.spi.nameservice.domain");
        String s2 = System.getProperty("java.net.preferIPv6Addresses");
        if(s != null)
        {
            StringTokenizer stringtokenizer = new StringTokenizer(s, ",");
            String as[] = new String[stringtokenizer.countTokens()];
            int j;
            for(int i = 0; stringtokenizer.hasMoreTokens(); i = j)
            {
                j = i + 1;
                as[i] = stringtokenizer.nextToken();
            }

            String as1[];
            try
            {
                Lookup.setDefaultResolver(new ExtendedResolver(as));
            }
            catch(UnknownHostException unknownhostexception)
            {
                System.err.println("DNSJavaNameService: invalid sun.net.spi.nameservice.nameservers");
            }
        }
        if(s1 != null)
            try
            {
                as1 = new String[1];
                as1[0] = s1;
                Lookup.setDefaultSearchPath(as1);
            }
            catch(TextParseException textparseexception)
            {
                System.err.println("DNSJavaNameService: invalid sun.net.spi.nameservice.domain");
            }
        if(s2 != null && s2.equalsIgnoreCase("true"))
            preferV6 = true;
    }

    public String getHostByAddr(byte abyte0[])
        throws UnknownHostException
    {
        org.xbill.DNS.Record arecord[] = (new Lookup(ReverseMap.fromAddress(InetAddress.getByAddress(abyte0)), 12)).run();
        if(arecord == null)
            throw new UnknownHostException();
        else
            return ((PTRRecord)arecord[0]).getTarget().toString();
    }

    public Object invoke(Object obj, Method method, Object aobj[])
        throws Throwable
    {
        int i = 0;
        Object obj1;
        int j;
        byte abyte0[][];
        if(method.getName().equals("getHostByAddr"))
        {
            obj1 = getHostByAddr((byte[])(byte[])aobj[0]);
            break MISSING_BLOCK_LABEL_156;
        }
        if(!method.getName().equals("lookupAllHostAddr"))
            break MISSING_BLOCK_LABEL_146;
        obj1 = lookupAllHostAddr((String)aobj[0]);
        Class class1 = method.getReturnType();
        if(class1.equals([Ljava/net/InetAddress;))
            break MISSING_BLOCK_LABEL_156;
        if(!class1.equals([[B))
            break MISSING_BLOCK_LABEL_146;
        j = obj1.length;
        abyte0 = new byte[j][];
_L1:
        if(i >= j)
            break MISSING_BLOCK_LABEL_121;
        abyte0[i] = obj1[i].getAddress();
        i++;
          goto _L1
        obj1 = abyte0;
        break MISSING_BLOCK_LABEL_156;
        Throwable throwable;
        throwable;
        System.err.println("DNSJavaNameService: Unexpected error.");
        throwable.printStackTrace();
        throw throwable;
        throw new IllegalArgumentException("Unknown function name or arguments.");
        return obj1;
    }

    public InetAddress[] lookupAllHostAddr(String s)
        throws UnknownHostException
    {
        Name name;
        org.xbill.DNS.Record arecord[];
        org.xbill.DNS.Record arecord1[];
        try
        {
            name = new Name(s);
        }
        catch(TextParseException textparseexception)
        {
            throw new UnknownHostException(s);
        }
        arecord = null;
        if(preferV6)
            arecord = (new Lookup(name, 28)).run();
        if(arecord == null)
            arecord = (new Lookup(name, 1)).run();
        InetAddress ainetaddress[];
        int i;
        if(arecord == null && !preferV6)
            arecord1 = (new Lookup(name, 28)).run();
        else
            arecord1 = arecord;
        if(arecord1 == null)
            throw new UnknownHostException(s);
        ainetaddress = new InetAddress[arecord1.length];
        i = 0;
        while(i < arecord1.length) 
        {
            org.xbill.DNS.Record _tmp = arecord1[i];
            if(arecord1[i] instanceof ARecord)
                ainetaddress[i] = ((ARecord)arecord1[i]).getAddress();
            else
                ainetaddress[i] = ((AAAARecord)arecord1[i]).getAddress();
            i++;
        }
        return ainetaddress;
    }

    private static final String domainProperty = "sun.net.spi.nameservice.domain";
    private static final String nsProperty = "sun.net.spi.nameservice.nameservers";
    private static final String v6Property = "java.net.preferIPv6Addresses";
    private boolean preferV6;
}
