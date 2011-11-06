// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DNSUtil.java

package org.jivesoftware.smack.util;

import java.util.Map;
import org.xbill.DNS.*;

// Referenced classes of package org.jivesoftware.smack.util:
//            Cache

public class DNSUtil
{
    public static class HostAddress
    {

        public boolean equals(Object obj)
        {
            boolean flag;
            if(this == obj)
                flag = true;
            else
            if(!(obj instanceof HostAddress))
            {
                flag = false;
            } else
            {
                HostAddress hostaddress = (HostAddress)obj;
                if(!host.equals(hostaddress.host))
                    flag = false;
                else
                if(port == hostaddress.port)
                    flag = true;
                else
                    flag = false;
            }
            return flag;
        }

        public String getHost()
        {
            return host;
        }

        public int getPort()
        {
            return port;
        }

        public String toString()
        {
            return (new StringBuilder()).append(host).append(":").append(port).toString();
        }

        private String host;
        private int port;

        private HostAddress(String s, int i)
        {
            host = s;
            port = i;
        }

    }


    public DNSUtil()
    {
    }

    private static HostAddress resolveSRV(String s)
    {
        int i = -1;
        org.xbill.DNS.Record arecord[] = (new Lookup(s, 33)).run();
        if(arecord != null) goto _L2; else goto _L1
_L1:
        HostAddress hostaddress = null;
          goto _L3
_L2:
        int j = arecord.length;
        int k;
        int l;
        String s2;
        int i1;
        int j1;
        k = 0x7fffffff;
        l = i;
        s2 = null;
        i1 = 0;
        j1 = 0;
_L5:
        String s1;
        String s4;
        if(j1 >= j)
            break MISSING_BLOCK_LABEL_223;
        TextParseException textparseexception;
        NullPointerException nullpointerexception;
        SRVRecord srvrecord;
        int k1;
        int l1;
        String s3;
        NullPointerException nullpointerexception2;
        int i2;
        int j2;
        String s5;
        int k2;
        try
        {
            srvrecord = (SRVRecord)arecord[j1];
            if(srvrecord == null || srvrecord.getTarget() == null)
                break MISSING_BLOCK_LABEL_153;
            k1 = (int)((double)(srvrecord.getWeight() * srvrecord.getWeight()) * Math.random());
            if(srvrecord.getPriority() >= k)
                break; /* Loop/switch isn't completed */
            j2 = srvrecord.getPriority();
            s5 = srvrecord.getTarget().toString();
        }
        catch(TextParseException textparseexception1)
        {
            i = l;
            s1 = s2;
            continue; /* Loop/switch isn't completed */
        }
        catch(NullPointerException nullpointerexception1)
        {
            i = l;
            s1 = s2;
            continue; /* Loop/switch isn't completed */
        }
        s4 = s5;
        k2 = srvrecord.getPort();
        l = k2;
        s2 = s4;
        k = j2;
        i1 = k1;
_L7:
        j1++;
        if(true) goto _L5; else goto _L4
_L4:
        if(srvrecord.getPriority() != k || k1 <= i1) goto _L7; else goto _L6
_L6:
        l1 = srvrecord.getPriority();
        s3 = srvrecord.getTarget().toString();
        s4 = s3;
        i2 = srvrecord.getPort();
        l = i2;
        s2 = s4;
        k = l1;
        i1 = k1;
          goto _L7
        i = l;
        s1 = s2;
_L8:
        if(s1 == null)
        {
            hostaddress = null;
        } else
        {
            if(s1.endsWith("."))
                s1 = s1.substring(0, s1.length() - 1);
            hostaddress = new HostAddress(s1, i);
        }
        break; /* Loop/switch isn't completed */
        nullpointerexception;
        s1 = null;
        continue; /* Loop/switch isn't completed */
        nullpointerexception2;
        i = l;
        s1 = s4;
        continue; /* Loop/switch isn't completed */
        textparseexception;
        s1 = null;
        continue; /* Loop/switch isn't completed */
        TextParseException textparseexception2;
        textparseexception2;
        i = l;
        s1 = s4;
        if(true) goto _L8; else goto _L3
_L3:
        return hostaddress;
    }

    public static HostAddress resolveXMPPDomain(String s)
    {
        HostAddress hostaddress;
        synchronized(ccache)
        {
            if(ccache.containsKey(s))
            {
                hostaddress = (HostAddress)ccache.get(s);
                if(hostaddress != null)
                    break MISSING_BLOCK_LABEL_147;
            }
        }
        hostaddress = resolveSRV((new StringBuilder()).append("_xmpp-client._tcp.").append(s).toString());
        if(hostaddress == null)
            hostaddress = resolveSRV((new StringBuilder()).append("_jabber._tcp.").append(s).toString());
        if(hostaddress == null)
            hostaddress = new HostAddress(s, 5222);
        Map map1 = ccache;
        map1;
        JVM INSTR monitorenter ;
        ccache.put(s, hostaddress);
        break MISSING_BLOCK_LABEL_147;
        exception;
        map;
        JVM INSTR monitorexit ;
        throw exception;
        return hostaddress;
    }

    public static HostAddress resolveXMPPServerDomain(String s)
    {
        HostAddress hostaddress;
        synchronized(scache)
        {
            if(scache.containsKey(s))
            {
                hostaddress = (HostAddress)scache.get(s);
                if(hostaddress != null)
                    break MISSING_BLOCK_LABEL_147;
            }
        }
        hostaddress = resolveSRV((new StringBuilder()).append("_xmpp-server._tcp.").append(s).toString());
        if(hostaddress == null)
            hostaddress = resolveSRV((new StringBuilder()).append("_jabber._tcp.").append(s).toString());
        if(hostaddress == null)
            hostaddress = new HostAddress(s, 5269);
        Map map1 = scache;
        map1;
        JVM INSTR monitorenter ;
        scache.put(s, hostaddress);
        break MISSING_BLOCK_LABEL_147;
        exception;
        map;
        JVM INSTR monitorexit ;
        throw exception;
        return hostaddress;
    }

    private static Map ccache = new Cache(100, 0x927c0L);
    private static Map scache = new Cache(100, 0x927c0L);

}
