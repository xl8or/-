// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProxyInfo.java

package org.jivesoftware.smack.proxy;

import javax.net.SocketFactory;

// Referenced classes of package org.jivesoftware.smack.proxy:
//            DirectSocketFactory, HTTPProxySocketFactory, Socks4ProxySocketFactory, Socks5ProxySocketFactory

public class ProxyInfo
{
    public static final class ProxyType extends Enum
    {

        public static ProxyType valueOf(String s)
        {
            return (ProxyType)Enum.valueOf(org/jivesoftware/smack/proxy/ProxyInfo$ProxyType, s);
        }

        public static ProxyType[] values()
        {
            return (ProxyType[])$VALUES.clone();
        }

        private static final ProxyType $VALUES[];
        public static final ProxyType HTTP;
        public static final ProxyType NONE;
        public static final ProxyType SOCKS4;
        public static final ProxyType SOCKS5;

        static 
        {
            NONE = new ProxyType("NONE", 0);
            HTTP = new ProxyType("HTTP", 1);
            SOCKS4 = new ProxyType("SOCKS4", 2);
            SOCKS5 = new ProxyType("SOCKS5", 3);
            ProxyType aproxytype[] = new ProxyType[4];
            aproxytype[0] = NONE;
            aproxytype[1] = HTTP;
            aproxytype[2] = SOCKS4;
            aproxytype[3] = SOCKS5;
            $VALUES = aproxytype;
        }

        private ProxyType(String s, int i)
        {
            super(s, i);
        }
    }


    public ProxyInfo(ProxyType proxytype, String s, int i, String s1, String s2)
    {
        proxyType = proxytype;
        proxyAddress = s;
        proxyPort = i;
        proxyUsername = s1;
        proxyPassword = s2;
    }

    public static ProxyInfo forDefaultProxy()
    {
        return new ProxyInfo(ProxyType.NONE, null, 0, null, null);
    }

    public static ProxyInfo forHttpProxy(String s, int i, String s1, String s2)
    {
        return new ProxyInfo(ProxyType.HTTP, s, i, s1, s2);
    }

    public static ProxyInfo forNoProxy()
    {
        return new ProxyInfo(ProxyType.NONE, null, 0, null, null);
    }

    public static ProxyInfo forSocks4Proxy(String s, int i, String s1, String s2)
    {
        return new ProxyInfo(ProxyType.SOCKS4, s, i, s1, s2);
    }

    public static ProxyInfo forSocks5Proxy(String s, int i, String s1, String s2)
    {
        return new ProxyInfo(ProxyType.SOCKS5, s, i, s1, s2);
    }

    public String getProxyAddress()
    {
        return proxyAddress;
    }

    public String getProxyPassword()
    {
        return proxyPassword;
    }

    public int getProxyPort()
    {
        return proxyPort;
    }

    public ProxyType getProxyType()
    {
        return proxyType;
    }

    public String getProxyUsername()
    {
        return proxyUsername;
    }

    public SocketFactory getSocketFactory()
    {
        Object obj;
        if(proxyType == ProxyType.NONE)
            obj = new DirectSocketFactory();
        else
        if(proxyType == ProxyType.HTTP)
            obj = new HTTPProxySocketFactory(this);
        else
        if(proxyType == ProxyType.SOCKS4)
            obj = new Socks4ProxySocketFactory(this);
        else
        if(proxyType == ProxyType.SOCKS5)
            obj = new Socks5ProxySocketFactory(this);
        else
            obj = null;
        return ((SocketFactory) (obj));
    }

    private String proxyAddress;
    private String proxyPassword;
    private int proxyPort;
    private ProxyType proxyType;
    private String proxyUsername;
}
